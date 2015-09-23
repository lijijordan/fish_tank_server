<html>
<head>
  <script type="text/javascript" src="https://www.google.com/jsapi"></script>
  <script type="text/javascript" src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
  
  <!-- Datepicker -->
<!--   
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
   -->
  <script src="/fisher-web/resources/jquery.js"></script>
  <link rel="stylesheet" href="/fisher-web/resources/jquery.datetimepicker.css">
  <script src="/fisher-web/resources/jquery.datetimepicker.js"></script>
  <script>
	  jQuery('#start').datetimepicker({
		  format:'d.m.Y H:i',
		  inline:true,
		  lang:'ru'
		});
	  alert(1);
  </script>
   <!-- Datepicker -->
  
  <script type="text/javascript">
    google.load('visualization', '1.1', {packages: ['line']});
    google.setOnLoadCallback(drawChart);

    function drawChart() {
    	 var dataTable = new google.visualization.DataTable();
    	 //dataTable.addColumn('number', 'Day');
    	 dataTable.addColumn('datetime', 'Time');
    	 dataTable.addColumn('number', 'PH of my fish tank');
    	$.get( "/fisher-web/ph/list?size=2000", function( data ) {
		    var array = [];
    		jQuery.each(data, function(index, itemData) {
    			 //console.log(itemData.value);
    			 var a1 = [];
    			// a1.push(itemData.id);
    			 a1.push(new Date(itemData.date));
    			 a1.push(itemData.value);
    			 array.push(a1);
    			});
    		dataTable.addRows(array);
    		var options = {
    		        chart: {
    		          title: 'Box Office Earnings in First Two Weeks of Opening',
    		          subtitle: 'in millions of dollars (USD)'
    		        },
    		        width: 1500,
    		        height: 600
    		      };

    		      var chart = new google.charts.Line(document.getElementById('linechart_material'));

    		      chart.draw(dataTable, options);
    		}, "json" );

    	
     
      //data.addColumn('number', 'The Avengers');
      //data.addColumn('number', 'Transformers: Age of Extinction');


        /**
        data.addRows([
		[1,  24.625],
		[2,  24.625],
		[3,  24.625],
		[4,  24.8125],
		[5,  24.875],
		[6,  24.9375],
		[7,  26.9375],
        [8,  24.9375]
      ]);

      var options = {
        chart: {
          title: 'Box Office Earnings in First Two Weeks of Opening',
          subtitle: 'in millions of dollars (USD)'
        },
        width: 900,
        height: 500
      };

      var chart = new google.charts.Line(document.getElementById('linechart_material'));

      chart.draw(data, options);
        **/
    }
  </script>
</head>
<body>
<p>start: <input type="text" id="start">end: <input type="text" id="end"></p>
  <div id="linechart_material"></div>
</body>
</html>