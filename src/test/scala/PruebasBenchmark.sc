import Datos._
import Itinerarios._
import ItinerariosPar._
import Benchmark._


val pruebas = List(
  ("ABQ", "TPA"),
  ("ATL", "HOU"),
  ("SFO", "DEN"),
  ("MIA", "MSP"),
  ("BOS", "PHX")
)

val pruebasSalida = List(
  ("ABQ", "TPA", 11, 55),
  ("ATL", "HOU", 23, 40),
  ("SFO", "DEN", 10, 0),
  ("MIA", "MSP", 6, 20),
  ("BOS", "PHX", 14, 30)
)

// A partir de la línea 2080 del paquete datos, se definen las siguientes listas de vuelos:

// ---- Longitud 15 ----
// vuelosA1
// vuelosA2
// vuelosA3
// vuelosA4
// vuelosA5

// ---- Longitud 40 ----
// vuelosB1
// vuelosB2
// vuelosB3
// vuelosB4
// vuelosB5

// ---- Longitud 100 ----
// vuelosC1
// vuelosC2
// vuelosC3
// vuelosC4
// vuelosC5

// ---- Longitud 500 ----
// vuelosD1
// vuelosD2
// vuelosD3

// Seleccionar la lista de vuelos de acuerdo a la longitud que se quiere probar
val listaVuelos = vuelosC2++vuelosC3

// itinerarios
val resultadosItinerarios = pruebas.map { case (origen, destino) =>
  val (tSec, tPar, speedUp) = compararAlgoritmos(itinerarios, itinerariosPar)(listaVuelos, aeropuertos, origen, destino)
  (origen, destino, tSec, tPar, speedUp)
}
// itinerariosTiempo
val resultadosTiempo = pruebas.map { case (origen, destino) =>
  val (tSec, tPar, speedUp) = compararAlgoritmos(itinerariosTiempo, itinerariosTiempoPar)(listaVuelos, aeropuertos, origen, destino)
  (origen, destino, tSec, tPar, speedUp)
}
// itinerariosEscalas
val resultadosEscalas = pruebas.map { case (origen, destino) =>
  val (tSec, tPar, speedUp) = compararAlgoritmos(itinerariosEscalas, itinerariosEscalasPar)(listaVuelos, aeropuertos, origen, destino)
  (origen, destino, tSec, tPar, speedUp)
}
// itinerariosAire
val resultadosAire = pruebas.map { case (origen, destino) =>
  val (tSec, tPar, speedUp) = compararAlgoritmos(itinerariosAire, itinerariosAirePar)(listaVuelos, aeropuertos, origen, destino)
  (origen, destino, tSec, tPar, speedUp)
}
// itinearioSalida
val resultadosSalida = pruebasSalida.map { case (origen, destino, hora, minuto) =>
  val (tSec, tPar, speedUp) = compararAlgoritmosSalida(itinerarioSalida, itinerarioSalidaPar)(listaVuelos, aeropuertos, origen, destino, hora, minuto)
  (origen, destino, tSec, tPar, speedUp)
}


/*
resultadosItinerarios.foreach { case (origen, destino, tSec, tPar, speedUp) =>
  println(s"Comparando itinerarios de $origen a $destino")
  println(f"Tiempo Secuencial: $tSec%.2f ms")
  println(f"Tiempo Paralelo: $tPar%.2f ms")
  println(f"Aceleración: $speedUp%.2f")
}


resultadosTiempo.foreach { case (origen, destino, tSec, tPar, speedUp) =>
  println(s"Comparando itinerariosTiempo de $origen a $destino")
  println(f"Tiempo Secuencial: $tSec%.2f ms")
  println(f"Tiempo Paralelo: $tPar%.2f ms")
  println(f"Aceleración: $speedUp%.2f")
}


resultadosEscalas.foreach { case (origen, destino, tSec, tPar, speedUp) =>
  println(s"Comparando itinerariosEscalas de $origen a $destino")
  println(f"Tiempo Secuencial: $tSec%.2f ms")
  println(f"Tiempo Paralelo: $tPar%.2f ms")
  println(f"Aceleración: $speedUp%.2f")
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


resultadosSalida.foreach { case (origen, destino, hora, minuto, tSec, tPar, speedUp) =>
  println(s"Comparando itinerariosSalida de $origen a $destino a las $hora:$minuto")
  println(f"Tiempo Secuencial: $tSec%.2f ms")
  println(f"Tiempo Paralelo: $tPar%.2f ms")
  println(f"Aceleración: $speedUp%.2f")
}


val its15A1 = itinerarios(vuelosA1,aeropuertos)
val itsTpo15A1 = itinerariosTiempo(vuelosA1,aeropuertos)
val itsEsc15A1 = itinerariosEscalas(vuelosA1,aeropuertos)
val itsAir15A1 = itinerariosAire(vuelosA1,aeropuertos)
val itsSal15A1 = itinerarioSalida(vuelosA1,aeropuertos)
its15A1("HOU","BNA")
itsTpo15A1("HOU","BNA")
itsEsc15A1("HOU","BNA")
itsAir15A1("HOU","BNA")
itsSal15A1("HOU","BNA", 18, 30)

val its40B1 = itinerarios(vuelosB1,aeropuertos)
val itsTpo40B1 = itinerariosTiempo(vuelosB1,aeropuertos)
val itsEsc40B1 = itinerariosEscalas(vuelosB1,aeropuertos)
val itsAir40B1 = itinerariosAire(vuelosB1,aeropuertos)
val itsSal40B1 = itinerarioSalida(vuelosB1,aeropuertos)
its40B1("DFW","ORD")
itsTpo40B1("DFW","ORD")
itsEsc40B1("DFW","ORD")
itsAir40B1("DFW","ORD")
itsSal40B1("DFW","ORD", 18, 30)


val its100C1 = itinerarios(vuelosC1,aeropuertos)
val itsTpo100C1 = itinerariosTiempo(vuelosC1,aeropuertos)
val itsEsc100C1 = itinerariosEscalas(vuelosC1,aeropuertos)
val itsAir100C1 = itinerariosAire(vuelosC1,aeropuertos)
val itsSal100C1 = itinerarioSalida(vuelosC1,aeropuertos)
its100C1("ORD","TPA")
itsTpo100C1("ORD","TPA")
itsEsc100C1("ORD","TPA")
itsAir100C1("ORD","TPA")
itsSal100C1("ORD","TPA", 18, 30)


val its200C = itinerarios(vuelosC1++vuelosC2, aeropuertos)
//val itsTpo200C = itinerariosTiempo(vuelosC1++vuelosC2, aeropuertos)
//val itsEsc200C = itinerariosEscalas(vuelosC1++vuelosC2, aeropuertos)
//val itsAir200C = itinerariosAire(vuelosC1++vuelosC2, aeropuertos)
//val itsSal200C = itinerarioSalida(vuelosC1++vuelosC2, aeropuertos)
its200C("ORD","TPA")
//itsTpo200C("ORD","TPA")
//itsEsc200C("ORD","TPA")
//itsAir200C("ORD","TPA")
//itsSal200C("ORD","TPA", 18, 30)
*/
