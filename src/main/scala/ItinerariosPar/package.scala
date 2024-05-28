import Datos._
import common._
import scala.collection.parallel.CollectionConverters._

package object ItinerariosPar {
  type Itinerario = List[Vuelo]

  def convertirAHoraAbsoluta(hora: Int, minutos: Int, gmt: Int): Int = {
    (hora + gmt) * 60 + minutos
  }

  def obtenerGMT(aeropuertos: List[Aeropuerto], codigo: String): Int = {
    aeropuertos.find(_.Cod == codigo).map(_.GMT).getOrElse(0)
  }

  def convertirVuelosAHorasAbsolutas(vuelo: Vuelo, aeropuertos: List[Aeropuerto]): (Int, Int) = {
    val gmtOrg = obtenerGMT(aeropuertos, vuelo.Org)
    val gmtDst = obtenerGMT(aeropuertos, vuelo.Dst)
    val horaSalida = convertirAHoraAbsoluta(vuelo.HS, vuelo.MS, gmtOrg)
    val horaLlegada = convertirAHoraAbsoluta(vuelo.HL, vuelo.ML, gmtDst)
    (horaSalida, horaLlegada)
  }

  def itinerarioValido(itinerario: Itinerario, aeropuertos: List[Aeropuerto]): Boolean = {
    itinerario.zip(itinerario.tail).forall { case (vueloActual, vueloSiguiente) =>
      val (_, llegadaActual) = convertirVuelosAHorasAbsolutas(vueloActual, aeropuertos)
      val (salidaSiguiente, _) = convertirVuelosAHorasAbsolutas(vueloSiguiente, aeropuertos)
      llegadaActual < salidaSiguiente
    }
  }
  
  /*
  Funciona similar a la orginal pero se convierte en paralela al implementar task, su rendimiento depende de la cantidad posible
  de vuelos como primera opcion, es decir si en el aeropuerto de origen hay muchas opciones disponibles la funcion sera mucho mas eficaz
  */
  def itinerariosPar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[Itinerario] = {
    def encontrarItinerarios(origen: String, destino: String, vuelosDisponibles: List[Vuelo], visitados: Set[String]): List[Itinerario] = {
      if (origen == destino) {
        return List(Nil)
      }
      (for {
        vuelo <- vuelosDisponibles.filter(_.Org == origen)
        subItinerarios = task(encontrarItinerarios(vuelo.Dst, destino, vuelosDisponibles, visitados + origen))
        si = subItinerarios.join.map(it => vuelo :: it)
      } yield si).flatten
    }

    (cod1: String, cod2: String) => {
      val posiblesItinerarios = encontrarItinerarios(cod1, cod2, vuelos, Set())
      posiblesItinerarios.filter(itinerario => itinerarioValido(itinerario, aeropuertos))
    }
  }

  def calcularTiempoTotalItinerario(itinerario: Itinerario, aeropuertos: List[Aeropuerto]): Int = {
    val tiempos = itinerario.map(vuelo => convertirVuelosAHorasAbsolutas(vuelo, aeropuertos))
    val tiempoEspera = tiempos.zip(tiempos.tail).map { case ((_, llegadaActual), (salidaSiguiente, _)) =>
      (salidaSiguiente - llegadaActual).abs
    }.sum
    val tiempoVuelo = tiempos.map { case (salida, llegada) => (llegada - salida).abs }.sum
    tiempoVuelo + tiempoEspera
  }

  def calcularTiempoVueloItinerario(itinerario: Itinerario, aeropuertos: List[Aeropuerto]): Int = {
    val tiempos = itinerario.map(vuelo => convertirVuelosAHorasAbsolutas(vuelo, aeropuertos))
    val tiempoVuelo = tiempos.map { case (salida, llegada) => (llegada - salida).abs }.sum
    tiempoVuelo
  }

  /*
  La función interna `encontrarItinerarios` implementa la búsqueda de itinerarios usando procesamiento paralelo 
   * con `task`, lo que permite dividir la carga de trabajo entre múltiples núcleos de CPU. El rendimiento de esta 
   * función depende principalmente del número de vuelos disponibles desde el aeropuerto de origen. Si hay muchas 
   * opciones de vuelos desde el aeropuerto de origen, la función será más eficaz al distribuir mejor el trabajo.
   */
  def itinerariosEscalasPar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[Itinerario] = {
    val obtenerItinerarios = itinerariosPar(vuelos, aeropuertos)

    (cod1: String, cod2: String) => {
      val posiblesItinerarios = obtenerItinerarios(cod1, cod2)
      val mejoresItinerarios = task {
        posiblesItinerarios.sortBy(itinerario => itinerario.length - 1).take(3)
      }
      mejoresItinerarios.join
    }
  }

/*
 * La función interna `itinerariosPar` implementa la búsqueda de itinerarios usando `task` para paralelizar la 
 * carga de trabajo. Al aplicar la función paralela `.par.map`, se calcula el tiempo de vuelo de cada itinerario 
 * de manera concurrente, aprovechando múltiples núcleos de CPU para mejorar la eficiencia. La lista resultante 
 * se ordena según el tiempo de vuelo y se seleccionan los 3 itinerarios con menor tiempo.
 */
  def itinerariosAirePar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[Itinerario] = {
    val obtenerItinerarios = itinerariosPar(vuelos, aeropuertos)

    (cod1: String, cod2: String) => {
      val posiblesItinerarios = obtenerItinerarios(cod1, cod2)
      val itinerariosConTiempo = posiblesItinerarios.par.map(it => (it, calcularTiempoVueloItinerario(it, aeropuertos)))
      val itinerariosOrdenados = itinerariosConTiempo.toList.sortBy(_._2).take(3)
      itinerariosOrdenados.map(_._1)
    }
  }

/*
 * La función interna `itinerariosPar` se encarga de encontrar los itinerarios posibles usando `task` para 
 * paralelizar la búsqueda. Posteriormente, se convierte la hora de la cita a hora absoluta y se filtran los 
 * itinerarios que permiten llegar antes de la cita, también usando procesamiento paralelo con `.par.filter`. 
 * Finalmente, se selecciona el itinerario con la hora de salida más tardía que aún permite llegar a tiempo.
 */
  def itinerariosSalidaPar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String, Int, Int) => Itinerario = {
    val obtenerItinerarios = itinerariosPar(vuelos, aeropuertos)

    (cod1: String, cod2: String, hc: Int, mc: Int) => {
      val posiblesItinerarios = obtenerItinerarios(cod1, cod2)
      val citaEnHoraAbsoluta = convertirAHoraAbsoluta(hc, mc, obtenerGMT(aeropuertos, cod2))
      val itinerariosValidos = posiblesItinerarios.par.filter { it =>
        val (_, horaLlegada) = convertirVuelosAHorasAbsolutas(it.last, aeropuertos)
        horaLlegada <= citaEnHoraAbsoluta
      }.toList
      if (itinerariosValidos.isEmpty) List()
      else {
        val mejorItinerario = itinerariosValidos.maxBy { it =>
          val (horaSalida, _) = convertirVuelosAHorasAbsolutas(it.head, aeropuertos)
          horaSalida
        }
        mejorItinerario
      }
    }
  }
}
