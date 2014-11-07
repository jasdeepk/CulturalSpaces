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
			$table.append(row);
		}
	}
	
	$(document).ready(function () {
		console.log("ready");
		debugger;
		var locationRequest = $.getJSON("locations.php");
		locationRequest.done(function (data) {fillTable(data);});
	});
}));