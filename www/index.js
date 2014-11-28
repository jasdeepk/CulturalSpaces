require(["jquery", "datatables"], (function ($) {
	console.log("required");
	function fillTable (rows) {
		var $table = $("#table");
		for (var i = 0; i <= rows.length-1; i++)
		{
			var row = rows[i];
			// turn the sites in the web row into actual links
			if (row['web'][0] == "h" && row['web'][1] == "t" && row['web'][2] == "t" && row['web'][3] == "p") {
				var result = row['web'].link(row['web']);
			}
			if (row['web'] == "") {
				var result = "";
			}
			else result = row['web'].link("http://"+row['web']);
			var $row = 
				$("<tr>")
					.append("<td class='star' id='"
						+row['name']
						+"'></td>")
					.append("<td>"+row['lat']+"</td>")
					.append("<td>"+row['lon']+"</td>")
					.append("<td>"+row['name']+"</td>")
					.append("<td>"+row['addr']+"</td>")
					.append("<td>"+result+"</td>")
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
      title: location.name
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
		            "sPaginationType": "full_numbers",
		             "iDisplayLength": 10,
		             "columns": [
				    null,
				    null,
				    null,
				    null,
				    { "width": "10%" },
				    null,
				    null
				  ],
				     "aoColumns": [
  					{ "bSortable": false },
  					null,
  					null,
  					null,
  					null,
 					null,
  					null,
  					null,
  					]
        		} );
			}
		);
    $('html').on('click', '.star', function() {
    	console.log('clicked');
        var id = $(this).attr('id');
        $(this).toggleClass('favorited');
        $.post('/favourites.php', 
               {'isFavorited': $(this).hasClass('favorited'), 'id': id},
                  function(data) { 
                     //some sort of update function here
                  });
        });
     });
}));