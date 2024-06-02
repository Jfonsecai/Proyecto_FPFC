# Desarrollo del proyecto final del curso: Fundamentos de Programación Funcional y Concurrente 2024-1

## Autores:

- Bayron Jojoa - 2242917 <bayron.jojoa@correounivalle.edu.co>
- Juan David Fonseca - 2323942 <fonseca.juan@correounivalle.edu.co>
- Junior Cantor - 2224949 <junior.cantor@correounivalle.edu.co>

## Paquete Itinerarios

En el paquete itinerarios se encuentra el package object Itinerarios donde se definen las siguientes funciones:

Todas ellas se deben usar acompañadas del paquete de datos donde se pueden definir los vuelos y aeropuertos sobre los cuales usted desee calcular un itinerario.

### itinerarios

La función itinerarios hace uso de 4 funciones auxiliares que hacen exactamente lo que indica su nombre: convertirHoraAbsoluta, obtenerGMT, convertirVuelosAHorasAbsolutas e itinerarioValido. Calcula las posibles rutas o "itinerarios" para ir del origen al destino.

Para hacer uso de la función se debe tener una lista de vuelos y una lista de aeropuertos (clases definidas en el paquete Datos) y un origen y destino de su preferencia.

Primero deberá definir un val con la función pasándole como parámetros los vuelos y los aeropuertos de los que se necesite obtener un itinerario, posteriormente se debe llamar a este valor acompañado de los códigos de origen y destino de la siguiente manera:

    val itsCurso = itinerarios(vuelosCurso, aeropuertosCurso )
    val its1 = itsCurso( "MID" , "SVCS" )

### itinerariosTiempo

La función itinerariosTiempo hace uso de una función auxiliar CalcularTiempoTotalItinerario y de la anterior función para calcular los 3 itinerarios que minimizan el tiempo. Al igual que la anterior, para llamarla se debe primero definir un val con los aeropuertos y vuelos disponibles y posteriormente usar este valor seguido de los códigos de origen y destino:

    val itsTiempoCurso = itinerariosTiempo( vuelosCurso , aeropuertosCurso )
    val itst1 = itsTiempoCurso ( "MID" , "SVCS" )

### itinerariosEscalas

La función itinerariosEscalas hace uso de la primera función y también es de alto orden (primero se define un val y luego se acompaña de los códigos de origen y destino) y se usa para calcular los 3 itinerarios con menos escalas.

    val itsEscalasCurso = itinerariosEscalas(vuelosCurso, aeropuertosCurso)
    val itse1 = itsTiempoCurso ( "MID" , "SVCS" )

### itinerariosAire

Hace uso de Itinerarios y también es de alto orden (primero se define un val y luego se acompaña de los códigos de origen y destino). En este caso se calculan los 3 itinerarios con menor tiempo en el aire.

    val itSalidaCurso = itinerarioSalida( vuelosCurso , aeropuertosCurso )
    val itsal1 = itSalidaCurso( "CTG", "PTY", 11, 40)

### itinerarioSalida

Hace uso de Itinerarios y también es de alto orden (primero se define un val y luego se acompaña de los códigos de origen y destino). Adicionalmente, se debe indicar la hora y minutos de la cita respecto a la que se quieren calcular los itinerarios para obtener los 3 itinerarios que mejor se acomoden al tiempo.

    val itSalidaCurso = itinerarioSalida( vuelosCurso , aeropuertosCurso )
    val itsal1 = itSalidaCurso( "CTG", "PTY", 11, 40)

## Paquete ItinerariosPar

El paquete ItinerariosPar contiene el package object ItinerariosPar donde se definen las versiones paralelas de las 5 funciones presentadas en Itinerarios. Para usarlas se procede exactamente de la misma manera que para las funciones anteriores pero, dado que su estructura cambia levemente para paralelizarlas, se aconseja revisar la conclusión del informe.pdf para saber precisamente desde qué cantidad de datos es más recomendable usar estas versiones.

## Informe PDF

Finalmente, se presenta el informe.pdf donde se describe de manera más detallada el funcionamiento de cada función, las técnicas utilizadas para su construcción, su justificación/corrección y un análisis de ambas versiones donde se concluye sobre las ventajas/desventajas de usar versiones paralelas dependiendo de la cantidad de datos.
