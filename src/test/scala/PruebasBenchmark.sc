import Datos._
import Itinerarios._
import ItinerariosPar._
import Benchmark._


val vuelos = vuelos
val aeropuertos = aeropuertosCurso

val pruebas = List(
  ("MID", "SVCS"),
  ("CLO", "SVCS"),
  ("CLO", "SVO"),
  ("CLO", "MEX"),
  ("CTG", "PTY")
)

val resultadosItinerarios = pruebas.map { case (origen, destino) =>
  val (tSec, tPar, speedUp) = compararAlgoritmos(itinerarios, itinerariosPar)(vuelos, aeropuertos, origen, destino)
  (origen, destino, tSec, tPar, speedUp)
}

resultadosItinerarios.foreach { case (origen, destino, tSec, tPar, speedUp) =>
  println(s"Comparando itinerarios de $origen a $destino")
  println(f"Tiempo Secuencial: $tSec%.2f ms")
  println(f"Tiempo Paralelo: $tPar%.2f ms")
  println(f"Aceleración: $speedUp%.2f")
}

val resultadosTiempo = pruebas.map { case (origen, destino) =>
  val (tSec, tPar, speedUp) = compararAlgoritmos(itinerariosTiempo, itinerariosTiempoPar)(vuelos, aeropuertos, origen, destino)
  (origen, destino, tSec, tPar, speedUp)
}

resultadosTiempo.foreach { case (origen, destino, tSec, tPar, speedUp) =>
  println(s"Comparando itinerariosTiempo de $origen a $destino")
  println(f"Tiempo Secuencial: $tSec%.2f ms")
  println(f"Tiempo Paralelo: $tPar%.2f ms")
  println(f"Aceleración: $speedUp%.2f")
}

val resultadosTiempo = pruebas.map { case (origen, destino) =>
  val (tSec, tPar, speedUp) = compararAlgoritmos(itinerariosTiempo, itinerariosTiempoPar)(vuelos, aeropuertos, origen, destino)
  (origen, destino, tSec, tPar, speedUp)
}

resultadosTiempo.foreach { case (origen, destino, tSec, tPar, speedUp) =>
  println(s"Comparando itinerariosTiempo de $origen a $destino")
  println(f"Tiempo Secuencial: $tSec%.2f ms")
  println(f"Tiempo Paralelo: $tPar%.2f ms")
  println(f"Aceleración: $speedUp%.2f")
}

// Pruebas para itinerariosEscalas
val resultadosEscalas = pruebas.map { case (origen, destino) =>
  val (tSec, tPar, speedUp) = compararAlgoritmos(itinerariosEscalas, itinerariosEscalasPar)(vuelos, aeropuertos, origen, destino)
  (origen, destino, tSec, tPar, speedUp)
}

resultadosEscalas.foreach { case (origen, destino, tSec, tPar, speedUp) =>
  println(s"Comparando itinerariosEscalas de $origen a $destino")
  println(f"Tiempo Secuencial: $tSec%.2f ms")
  println(f"Tiempo Paralelo: $tPar%.2f ms")
  println(f"Aceleración: $speedUp%.2f")
}

// Pruebas para itinerariosAire
val resultadosAire = pruebas.map { case (origen, destino) =>
  val (tSec, tPar, speedUp) = compararAlgoritmos(itinerariosAire, itinerariosAirePar)(vuelos, aeropuertos, origen, destino)
  (origen, destino, tSec, tPar, speedUp)
}

resultadosAire.foreach { case (origen, destino, tSec, tPar, speedUp) =>
  println(s"Comparando itinerariosAire de $origen a $destino")
  println(f"Tiempo Secuencial: $tSec%.2f ms")
  println(f"Tiempo Paralelo: $tPar%.2f ms")
  println(f"Aceleración: $speedUp%.2f")
}

// Pruebas para itinerariosSalida
val pruebasSalida = List(
  ("CTG", "PTY", 11, 40),
  ("CTG", "PTY", 11, 55),
  ("CTG", "PTY", 10, 30)
)

val resultadosSalida = pruebasSalida.map { case (origen, destino, hora, minuto) =>
  val (tSec, tPar, speedUp) = compararAlgoritmosSalida(itinerarioSalida, itinerarioSalidaPar)(vuelos, aeropuertos, origen, destino, hora, minuto)
  (origen, destino, hora, minuto, tSec, tPar, speedUp)
}

resultadosSalida.foreach { case (origen, destino, hora, minuto, tSec, tPar, speedUp) =>
  println(s"Comparando itinerariosSalida de $origen a $destino a las $hora:$minuto")
  println(f"Tiempo Secuencial: $tSec%.2f ms")
  println(f"Tiempo Paralelo: $tPar%.2f ms")
  println(f"Aceleración: $speedUp%.2f")
}
