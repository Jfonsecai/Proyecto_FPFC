import ItinerariosPar._
import Datos._

val GMT = obtenerGMT(aeropuertosCurso, "BOG")
val hora = convertirAHoraAbsoluta(4,50, GMT)

convertirVuelosAHorasAbsolutas(vuelosCurso.head, aeropuertosCurso)


// Punto 3.1
val itsCurso = itinerariosPar(vuelosCurso, aeropuertosCurso )
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
val itsTiempoCurso = itinerariosTiempoPar( vuelosCurso , aeropuertosCurso )
// prueba itinerariosTiempo
val itst1 = itsTiempoCurso ( "MID" , "SVCS" )
val itst2 = itsTiempoCurso ( "CLO" , "SVCS" )
val itst3 = itsTiempoCurso ( "CLO" , "SVO" )
val itst4 = itsTiempoCurso ( "CLO" , "MEX" )
val itst5 = itsTiempoCurso ( "CTG" , "PTY" )


// Punto 3.3
val itsEscalasCurso = itinerariosEscalasPar(vuelosCurso, aeropuertosCurso)
val itse1 = itsTiempoCurso ( "MID" , "SVCS" )
val itse2 = itsTiempoCurso ( "CLO" , "SVCS" )
val itse3 = itsTiempoCurso ( "CLO" , "SVO" )
val itse4 = itsTiempoCurso ( "CLO" , "MEX" )
val itse5 = itsTiempoCurso ( "CTG" , "PTY" )

// Punto 3.4
val itsAireCurso = itinerariosAirePar( vuelosCurso , aeropuertosCurso )
// prueba itineariosAire
val itsa1 = itsAireCurso ( "MID" , "SVCS" )
val itsa2 = itsAireCurso ( "CLO" , "SVCS" )
val itsa3 = itsAireCurso ( "CLO" , "SVO" )
val itsa4 = itsAireCurso ( "CLO" , "MEX" )
val itsa5 = itsAireCurso ( "CTG" , "PTY" )

// Punto 3.5
val itSalidaCurso = itinerariosSalidaPar( vuelosCurso , aeropuertosCurso )
val itsal1 = itSalidaCurso( "CTG", "PTY", 11, 40)
val itsal2 = itSalidaCurso( "CTG", "PTY", 11, 55)
val itsal3 = itSalidaCurso( "CTG", "PTY", 10, 30)