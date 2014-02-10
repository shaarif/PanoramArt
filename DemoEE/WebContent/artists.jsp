<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="docs-assets/ico/favicon.png">
	     <title>PanoramArt</title>

    <!-- Bootstrap core CSS -->
    <link href="dist/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="navbar-fixed-top.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCJJLnDnIq-VOgBSER-WUKnuFdbayLcoFs&sensor=false"> </script>
   <link rel="stylesheet" href="source/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen" />
 <link rel="stylesheet" href="source/helpers/jquery.fancybox-buttons.css?v=1.0.5" type="text/css" media="screen" />
 <link rel="stylesheet" href="source/helpers/jquery.fancybox-thumbs.css?v=1.0.7" type="text/css" media="screen" />

      </head>

  <body>

    <!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">PanoramArt</a>
        </div>
      </div>
    </div>

    <div class="container">
    <div class ="row">
    <center><p id="heading"></p></center>
    </div>
        <div class ="row">
    	<div class = "col-lg-6">
				<div id="map-canvas">
	     
				</div>
				<select id="cities" style="visibility:hidden;">
    			<c:forEach items="${City}" var="city">
      			  <option value="${city}">
      			      ${city}
     			   </option>
   				 </c:forEach>
				</select>
        		<select id="museumNames" style="visibility:hidden;">
    			<c:forEach items="${Museum}" var="mname">
       				 <option value="${mname}">
       			     ${mname}
      			  </option>
   				 </c:forEach>
				</select>
<label id="query" style="visibility:hidden;">${Artist}</label>
<label id="json" style="visibility:hidden;">${Results}</label>
		</div>
		<div class = "col-lg-6" id="content" style="overflow:scroll; height:100%">
						
		</div>
    </div>
    

   <div class = "footer">
	   <p>&copy; Shaarif Zia Hardik Desai 2013</p>
   </div>
    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript">
    var geocoder;
    var map;
    var markersArray = [];
    var bounds;
    var ddlArray= new Array();
    var museumArray= new Array();
    var query;
    var data;
    var infowindow =  new google.maps.InfoWindow({
        content: ''
    });

    //plot initial point using geocode instead of coordinates (works just fine)
    function initialize() {
        geocoder = new google.maps.Geocoder();
        bounds = new google.maps.LatLngBounds ();
        var myOptions = {
        		center:new google.maps.LatLng(37.0000,-120.0000),
                zoom:7,
                mapTypeId:google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById("map-canvas"), myOptions);     
        var ddl = document.getElementById('cities');
        for (i = 0; i < ddl.options.length; i++) {
           ddlArray[i] = ddl .options[i].value;
        }
        var mus=document.getElementById('museumNames');
        for (i = 0; i < mus.options.length; i++) {
        	museumArray[i] = mus .options[i].value;
         }
        query=document.getElementById('query').innerHTML;
        plotMarkers();
        myFunction();
    }

    function plotMarkers(){
        var i;
        for(i = 0; i < ddlArray.length; i++){
            codeAddresses(ddlArray[i],museumArray[i],query);
        }
    }

    function codeAddresses(address,mname,query){
        geocoder.geocode( { 'address': address}, function(results, status) { 
            if (status == google.maps.GeocoderStatus.OK) {
                marker = new google.maps.Marker({
                    map: map,
                    position: results[0].geometry.location
                });

                google.maps.event.addListener(marker, 'mouseover', function() {
                	infowindow.setContent("<a class='maps' data-fancybox-type='iframe' href='http://localhost:8080/DemoEE/showReport?mname="+mname+"'>"+mname+"</a>");
                    infowindow.open(map, this);
                });
                google.maps.event.addListener(marker, 'click', function() {
                    //map.setZoom(15);
                    //map.setCenter(marker.getPosition());
                    contentchange(mname,query);
                    // perform the inline change
                  });
                bounds.extend(results[0].geometry.location);

                markersArray.push(marker); 
            }
            else{
                alert("Geocode was not successful for the following reason: " + status);
            }

            map.fitBounds(bounds);
        });
    }
    google.maps.event.addDomListener(window, 'load', initialize);
    function contentchange(mname,query)
    { 
    	var xhr = new XMLHttpRequest();
    	var url = "http://localhost:8080/DemoEE/showitemsartist?mname="+mname+"&query="+query;
    	xhr.open("GET",url,true); 
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4) {
                var data = xhr.responseText;
                showcontent(data,mname);
            }
        };
        xhr.send(null);  
    }
    function showcontent(data,mname)
    {
    	document.getElementById('heading').innerHTML="<b>Search results for Artist: <i>"+query+"</i> at '"+mname+"'</b>";
    	var obj=eval ("(" + data + ")");
    	var myTable="<table>";
    	
    	for (var i=0;i<obj.Artworks.length;i++) {
    		myTable=myTable+"<tr>";
    		myTable=myTable+"<td>";
    		myTable=myTable+"<a class='various' data-fancybox-type='iframe'  href='http://localhost:8080/DemoEE/showDetails?image="+obj.Artworks[i].Image+"'><img src='"+obj.Artworks[i].Image+"' height='100' width='100' /></a>";
    		myTable=myTable+"</td>";
    		myTable=myTable+"<td>";
    		myTable+="<p><b>Title:</b> "+obj.Artworks[i].Title+"</p>";
    		myTable+="<p><b>Technique:</b> <a href='http://localhost:8080/DemoEE/showTechnique?technique="+obj.Artworks[i].Technique+"'>"+obj.Artworks[i].Technique+"</a></p>";
    		myTable=myTable+"</td>";
    		myTable=myTable+"</tr>";
    	}
    	myTable=myTable+"</table>";
    	document.getElementById('content').innerHTML = myTable;
    }
    </script>
    <script>
    function myFunction()
    {
    	document.getElementById('heading').innerHTML="<b>Search results for Artist: <i>"+query+"</i></b>";
    	data=document.getElementById('json').innerHTML;
    	var obj=eval ("(" + data + ")");
    	var myTable="<table>";
    	for (var i=0;i<obj.Artworks.length;i++) {
    		myTable=myTable+"<tr>";
    		myTable=myTable+"<td>";
    		myTable=myTable+"<a class='various' data-fancybox-type='iframe'  href='http://localhost:8080/DemoEE/showDetails?image="+obj.Artworks[i].Image+"'><img src='"+obj.Artworks[i].Image+"' height='100' width='100' /></a>";
    		myTable=myTable+"</td>";
    		myTable=myTable+"<td>";
    		myTable+="<p><b>Title:</b> "+obj.Artworks[i].Title+"</p>";
    		myTable+="<p><b>Technique:</b> <a href='http://localhost:8080/DemoEE/showTechnique?technique="+obj.Artworks[i].Technique+"'>"+obj.Artworks[i].Technique+"</a></p>";
    		myTable=myTable+"</td>";
    		myTable=myTable+"</tr>";
    	}
    	myTable=myTable+"</table>";
    	document.getElementById('content').innerHTML = myTable;
    }
    </script>
 <!-- Add jQuery library -->
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>

<!-- Add mousewheel plugin (this is optional) -->
<script type="text/javascript" src="lib/jquery.mousewheel-3.0.6.pack.js"></script>
<script type="text/javascript" src="source/jquery.fancybox.pack.js?v=2.1.5"></script>

<!-- Optionally add helpers - button, thumbnail and/or media -->
<script type="text/javascript" src="source/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
<script type="text/javascript" src="source/helpers/jquery.fancybox-media.js?v=1.0.6"></script>
<script type="text/javascript" src="source/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>   

 
  
      <script type="text/javascript">
      $(document.body).on("click","img",function(){
    		$(".various").fancybox({
    			maxWidth	: 800,
    			maxHeight	: 600,
    			fitToView	: false,
    			width		: '70%',
    			height		: '70%',
    			autoSize	: false,
    			closeClick	: false,
    			openEffect	: 'none',
    			closeEffect	: 'none'
    		});
    	  });

</script>
     
  </body>
</html>
