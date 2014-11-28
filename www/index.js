require(["jquery", "datatables"], function ($) {
	setTimeout(loadUserDependentData, 1000)
	function loadUserDependentData ()
	{
		gapi.client.plus.people.get({'userId':'me'}).then(function(res) {
			//shorthand
			var userID = res.result.id;
			console.log("User ID: "+userID);
			
			function fillTable (rows, favourites) {
				var $table = $("#table");
				for (var i = 0; i <= rows.length-1; i++)
				{
					var row = rows[i];
					
					//check to see if the current row describes a favourite location
					var isFavourite = false;
					var locationID = row['name'];
					for (var j = 0; isFavourite === false && j <= favourites.length-1; j++)
					{
						var favouriteID = favourites[j]['locationID'];
						if (locationID === favouriteID) isFavourite = true;
					}

					// turn the sites in the web row into actual links
					if (row['web'][0] == "h" && row['web'][1] == "t" && row['web'][2] == "t" && row['web'][3] == "p") {
						var result = row['web'].link(row['web']);
					}
					if (row['web'] == "") {
						var result = "";
					}
					else result = row['web'].link("http://"+row['web']);
					var $row = 
						$("<tr data-locationid='"+row['name']+"'>")
							.append("<td class='star"+(isFavourite? " favorited":"")+"' id='"
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
				var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

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
				var locationsRequest = $.getJSON("locations.php");
				var favouritesRequest = $.getJSON("favourites.php", {userID: userID});
				locationsRequest.then(function (locations) {
					favouritesRequest.then(function (favourites) {
						fillTable(locations, favourites);

						fillMap(locations);

						$("#table").DataTable({
				            "sPaginationType": "full_numbers",
				             "iDisplayLength": 4,
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
		        		});
					})
				});
			    $('html').on('click', '.star', function() {
			    	console.log('clicked');
			        var id = $(this).attr('id');
			        $(this).toggleClass('favorited');
			        var postData = {'isFavourited': $(this).hasClass('favorited').toString(), 'userID': userID.toString(), 'locationID': $(this).parent().data('locationid').toString()};
			        $.post('/favourites.php'
			        	, postData
			        	, function(data) { 
			            	//some sort of update function here
			        	}
			        );
			    });
			});
		});
	}
});