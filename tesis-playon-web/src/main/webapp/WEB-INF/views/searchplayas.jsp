<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

	<style type="text/css">
    	#map
	    {
	        width: 600px; 
	        height: 500px;
	    }
	    #location
	    {
	        width: 400px;
	    }
    
	    #map
	    {
	        width: 600px; 
	        height: 500px;
	    }
    </style>
    
	<!--     CARGA EL MAPA EN LA PAGINA 	-->
	<script type="text/javascript"
    	src="http://maps.google.com/maps/api/js?sensor=false&language=es">
    </script>
    
    <script type="text/javascript">	
    
    /**
     * The Google Geocoding API: http://code.google.com/apis/maps/documentation/geocoding/index.html
     * Geocoding service: http://code.google.com/apis/maps/documentation/javascript/services.html#Geocoding
     */

    // Variables globales

    var map, geocoder;

    // Instancia del geocodificador

    geocoder = new google.maps.Geocoder();

    // Propiedades iniciales del mapa

    window.onload = function() {
        var options = {
            zoom: 12,
            center: new google.maps.LatLng(-31.409912,-64.182129),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        // Instancia del mapa

        map = new google.maps.Map(document.getElementById('map'), options);
        
        var marker = new google.maps.Marker({position:new google.maps.LatLng(-31.409912,-64.182129), map: map, title:"T�tulo del mapa"});
        marker.setMap(map);
        
        // Relaci�n del evento de clic en el bot�n con el 
        // procedimiento de georreferenciaci�n
        
        document.getElementById('search').onclick = function() {
            // Obtiene la ubicaci�n (string) a georreferenciar
            
            var location = document.getElementById('location').value;

            // Inicia el proceso de georreferenciaci�n (as�ncrono)

            processGeocoding(location, addMarkers);

            // Detiene el procesamiento del evento

            return false;
        }
    }

    /**
     * Georreferenciar una ubicaci�n geogr�fica
     *
     * @param   location    Ubicaci�n geogr�fica
     * @param   callback    Funci�n a ejecutarse despu�s de una georrefereciaci�n exitosa
     */

    function processGeocoding(location, callback)
    {
        // Propiedades de la georreferenciaci�n
        
        var request = {
            address: location
        }
        
        // Invocaci�n a la georreferenciaci�n (proceso as�ncrono)
        
        geocoder.geocode(request, function(results, status) {
            
            /*
             * OK
             * ZERO_RESULTS
             * OVER_QUERY_LIMIT
             * REQUEST_DENIED
             * INVALID_REQUEST 
             */

            // Notifica al usuario el resultado obtenido

            document.getElementById('message').innerHTML = "Respuesta obtenida: " + status;

            // En caso de terminarse exitosamente el proyecto ...

            if(status == google.maps.GeocoderStatus.OK)
            {
                // Invoca la funci�n de callback
                
                callback (results);
                
                // Retorna los resultados obtenidos
                
                return results;
            }
                
            // En caso de error retorna el estado
                
            return status;
        });
    }
    
    /**
     * Agregar las ubicaciones georreferenciadas al mapa (marcadores)
     *
     * @param   geocodes    Listado de ubicaciones georreferenciadas
     */

    function addMarkers(geocodes)
    {
        for(var i=0; i<geocodes.length; i++)
        {
            // Centra el mapa en la nueva ubicaci�n
            
            map.setCenter(geocodes[i].geometry.location);

            // Valores iniciales del marcador

            var marker = new google.maps.Marker({
                map: map,
                title: geocodes[i].formatted_address
            });
            
            // Establece la ubicaci�n del marcador

            marker.setPosition(geocodes[i].geometry.location);
            
            // Establece el contenido de la ventana de informaci�n
            
            var infoWindow = new google.maps.InfoWindow();

            content = "Ubicaci�n: " + geocodes[i].formatted_address + "<br />" +
                             "Tipo: " + geocodes[i].types + "<br />" +
                             "Latitud: " + geocodes[i].geometry.location.lat() + "<br />" +
                             "Longitud: " + geocodes[i].geometry.location.lng();
            
            infoWindow.setContent(content);

            // Asocia el evento de clic sobre el marcador con el despliegue
            // de la ventana de informaci�n

            google.maps.event.addListener(marker, 'click', function(event) {
                infoWindow.open(map, marker);
            });

            // infowindow.open(map, marker);
        }
    }

    </script>
</head>
<body>
	<div>
		<label for="location">Domicilio:</label>
		<input type="text" id="location" name="location" value="" size="40" />
		<input type="button" id="search" name="search" value="Buscar" />
		<div id="message"></div> 
	</div>
	<div id="map" style="width:25cm; height:18cm"> </div>
</body>
</html>