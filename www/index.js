require(["jquery", "datatables"], (function ($) {
	console.log("required");
	function fillTable (rows) {
		var $table = $("#table");
		for (var i = 0; i <= rows.length-1; i++)
		{
			var row = rows[i];
			var $row = 
				$("<tr>")
					.append("<td>"+row['lat']+"</td>")
					.append("<td>"+row['lon']+"</td>")
					.append("<td>"+row['name']+"</td>")
					.append("<td>"+row['addr']+"</td>")
					.append("<td>"+row['web']+"</td>")
					.append("<td>"+row['type']+"</td>")
					.append("<td>"+row['hood']+"</td>")
			;
			$table.append($row);
		}
	}

	function fillMap(locations) {
   
  var mapOptions = {
          center: { lat: 49.2500, lng: -123.1000},
          zoom: 12
        };
 var map = new google.maps.Map(document.getElementById('map-canvas'),
            mapOptions);

 for (var i = 0; i <= locations.length - 1; i++) {
 	var location = locations[i];
    var lat = parseFloat(location['lat']);
    var lon = parseFloat(location['lon']);
    var myLatlng = new google.maps.LatLng(lat,lon);
    var marker = new google.maps.Marker({
      position: myLatlng,
      map: map,
      title: String.fromCharCode(locations[2])
  });
    };

}
	
	$(document).ready(function () {
		console.log("ready");
		var locationRequest = $.getJSON("locations.php");
		locationRequest.done(
			function (data) {
				console.log("done");
				fillTable(data);
				fillMap(data);
				$("#table").DataTable({
            "sPaginationType": "full_numbers"
        } );
				
			}
		);
	});
}));