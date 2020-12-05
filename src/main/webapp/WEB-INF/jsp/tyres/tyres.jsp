<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!doctype html>
<head>  
	<title><spring:message code="application.title"/></title>  
</head>  
<body>

<c:url var="previewUrl" value="/tyres/preview.html"/>

<div id="tyres">
<%-- 	<div id="exito-critica-publico" class="alert alert-success" role="alert" style="display:none"><spring:message code="label.result.success"/></div> --%>
<%-- 	<div id="alert-error" class="alert alert-danger" role="alert" style="display:none"><spring:message code="label.result.error"/></div> --%>
<form name="theForm" id="theForm">
	<div class="form-group">
	  <label for="bbcodeinput"><spring:message code="label.tyres.enterbbcode"/>:</label>
	  <textarea class="form-control" rows="15" id="bbcodeinput"></textarea>
	</div>
</form>

<div id="botonazo">
	<button type="button" id="previewbutton" class="btn btn-primary"><spring:message code="label.tyres.preview"/></button>
</div>

<div id="previewhtml">
	<div class="form-group">
	  <label><spring:message code="label.tyres.previewbbcode"/>:</label>
	  <div>
	  	<span class="" id="previewarea">
	  	</span>
	  </div>
	</div>
</div>

<script type="text/javascript">

$(document).ready(function() {
	$('#previewbutton').click(function() {
		$('#cargando').show();
		
		var csrfParameter = '${_csrf.parameterName}';
        var csrfToken = '${_csrf.token}';
		
        var data = {};
        data[csrfParameter] = csrfToken;
        data["text"] = $('#bbcodeinput').val();
        
		//Peticion POST
		$.ajax({
		    type: "POST",
		    url: '${previewUrl}',
		    data: data,
		    success: function (data, textStatus, xhr) {
		    	$('#cargando').hide();
		    	$('#previewarea').html(data);
		    },
		    error: function (xhr, textStatus, errorThrown) {
		    	console.log(errorThrown);
		    	$('#cargando').hide();
		    }
		});
	});
});
	
</script>	

 
</body>  
</html>  