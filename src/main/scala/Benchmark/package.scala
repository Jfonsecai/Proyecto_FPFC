import Datos._
import Itinerarios._
import ItinerariosPar._
import scala.collection.parallel.CollectionConverters._
import org.scalameter._

package object Benchmark {
r
  type ItinerariosFunc = (List[Vuelo], List[Aeropuerto]) => (String, String) => List[Itinerario]
  type ItinerariosSalidaFunc = (List[Vuelo], List[Aeropuerto]) => (String, String, Int, Int) => Itinerario

  def compararAlgoritmos(algSecuencial: ItinerariosFunc, algParalelo: ItinerariosFunc)
                        (vuelos: List[Vuelo], aeropuertos: List[Aeropuerto], origen: String, destino: String): (Double, Double, Double) = {

    val itSecuencial = algSecuencial(vuelos, aeropuertos)
    val itParalelo = algParalelo(vuelos, aeropuertos)

    val timeSecuencial = config(
      KeyValue(Key.exec.minWarmupRuns -> 20),
      KeyValue(Key.exec.maxWarmupRuns -> 60),
      KeyValue(Key.verbose -> false)
    ) withWarmer(new Warmer.Default) measure {
      itSecuencial(origen, destino)
    }

    val timeParalelo = config(
      KeyValue(Key.exec.minWarmupRuns -> 20),
      KeyValue(Key.exec.maxWarmupRuns -> 60),
      KeyValue(Key.verbose -> false)
    ) withWarmer(new Warmer.Default) measure {
      itParalelo(origen, destino)
    }

    val speedUp = timeSecuencial.value / timeParalelo.value
    (timeSecuencial.value, timeParalelo.value, speedUp)
  }

  def compararAlgoritmosSalida(algSecuencial: ItinerariosSalidaFunc, algParalelo: ItinerariosSalidaFunc)
                              (vuelos: List[Vuelo], aeropuertos: List[Aeropuerto], origen: String, destino: String, hora: Int, minuto: Int):
  (Double, Double, Double) = {

    val itSecuencial = algSecuencial(vuelos, aeropuertos)
    val itParalelo = algParalelo(vuelos, aeropuertos)

    val timeSecuencial = config(
      KeyValue(Key.exec.minWarmupRuns -> 20),
      KeyValue(Key.exec.maxWarmupRuns -> 60),
      KeyValue(Key.verbose -> false)
    ) withWarmer(new Warmer.Default) measure {
      itSecuencial(origen, destino, hora, minuto)
    }

    val timeParalelo = config(
      KeyValue(Key.exec.minWarmupRuns -> 20),
      KeyValue(Key.exec.maxWarmupRuns -> 60),
      KeyValue(Key.verbose -> false)
    ) withWarmer(new Warmer.Default) measure {
      itParalelo(origen, destino, hora, minuto)
    }

    val speedUp = timeSecuencial.value / timeParalelo.value
    (timeSecuencial.value, timeParalelo.value, speedUp)
  }
}
