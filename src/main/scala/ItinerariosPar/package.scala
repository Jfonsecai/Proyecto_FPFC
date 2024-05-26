import Datos._
import common._

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
        si = (subItinerarios.join).map(it => vuelo :: it)
      }yield si).flatten
    }

    (cod1: String, cod2: String) => {
      val posiblesItinerarios = encontrarItinerarios(cod1, cod2, vuelos, Set())
      posiblesItinerarios.filter(itinerario => itinerarioValido(itinerario, aeropuertos))
    }
  }
}
