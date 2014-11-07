require(["jquery"], (function ($) {
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
			;
			$table.append($row);
		}
	}

	function fillMap(locations) {
   
  var mapOptions = {
    zoom: 4,
    center: myLatlng
  }
  var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

 for (var i = 1; i >= locations.length - 1; i++) {
 	debugger;
 	var location = locations[i];
    var lat = parseInt(location['lat']);
    var lon = parseInt(location['lon']);
    var myLatlng = new google.maps.LatLng(lat,lon);
    var marker = new google.maps.Marker({
      position: myLatlng,
      map: map,
      title: 'Hello World!'
  });
    };

}
	
	$(document).ready(function () {
		console.log("ready");
		var locationRequest = $.getJSON("locations.php");
		locationRequest.done(function (data) {console.log("done");fillTable(data);
											  console.log("done");fillMap(data);});
	});
}));