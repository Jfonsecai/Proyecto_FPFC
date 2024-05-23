import Itinerarios._
import Datos._

val GMT = obtenerGMT(aeropuertosCurso, "BOG")
val hora = convertirAHoraAbsoluta(4,50, GMT)

convertirVuelosAHorasAbsolutas(vuelosCurso.head, aeropuertosCurso)


// Ejemplo
val itsCurso = itinerarios(vuelosCurso, aeropuertosCurso )
// 2.1 Aeropuertos incomunicados
val its1 = itsCurso( "MID" , "SVCS" )
val its2 = itsCurso( "CLO" , "SVCS" )
// 4 itinerarios CLO-SVO
val its3 = itsCurso( "CLO" , "SVO" )
//2 itinerarios CLO-MEX
val its4 = itsCurso( "CLO" , "MEX" )
//2 itinerarios CTG-PTY
val its5 = itsCurso( "CTG" , "PTY" )



// Punto 3.2
val itsTiempoCurso = itinerariosTiempo( vuelosCurso , aeropuertosCurso )
// prueba itinerariosTiempo
val itst1 = itsTiempoCurso ( "MID" , "SVCS" )
val itst2 = itsTiempoCurso ( "CLO" , "SVCS" )
val itst3 = itsTiempoCurso ( "CLO" , "SVO" )
val itst4 = itsTiempoCurso ( "CLO" , "MEX" )
val itst5 = itsTiempoCurso ( "CTG" , "PTY" )


calcularTiempoTotalItinerario(its3.head, aeropuertosCurso)
calcularTiempoTotalItinerario(its3.tail.head, aeropuertosCurso)
calcularTiempoTotalItinerario(its3.tail.tail.head, aeropuertosCurso)
calcularTiempoTotalItinerario(its3.tail.tail.tail.head, aeropuertosCurso)

val tiempos = itst3.head.map(vuelo => convertirVuelosAHorasAbsolutas(vuelo, aeropuertosCurso))
val tiempoEspera = tiempos.zip(tiempos.tail).map { case ((_, llegadaActual), (salidaSiguiente, _)) =>
  (salidaSiguiente - llegadaActual).abs }.sum
val tiempoVuelo = tiempos.map { case (salida, llegada) => llegada - salida }.sum

// Punto 3.4
val itsAireCurso = itinerariosAire( vuelosCurso , aeropuertosCurso )
// prueba itineariosAire
val itsa1 = itsAireCurso ( "MID" , "SVCS" )
val itsa2 = itsAireCurso ( "CLO" , "SVCS" )
val itsa3 = itsAireCurso ( "CLO" , "SVO" )
val itsa4 = itsAireCurso ( "CLO" , "MEX" )
val itsa5 = itsAireCurso ( "CTG" , "PTY" )

// Punto 3.5
val itSalidaCurso = itinerariosSalida( vuelosCurso , aeropuertosCurso )
val itsal1 = itSalidaCurso( "CTG", "PTY", 11, 40)
val itsal2 = itSalidaCurso( "CTG", "PTY", 11, 55)
val itsal3 = itSalidaCurso( "CTG", "PTY", 10, 30)
