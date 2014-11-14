var cultural_locations = [
    // Contains list of locations with names, latitude, and longitude.
]

for(var i = 0; i < cultural_locations.length; i++) {
    var location = cultural_locations.get();
    var marker = new google.maps.Marker({
        	position: new google.maps.Latlng(location.getLat(), location.getLon()),
            map: map,
        });
    }