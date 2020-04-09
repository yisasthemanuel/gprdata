<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!doctype html>
<head>  
	<title><spring:message code="application.title"/></title>  
</head>  
<body> 
<c:url var="vendorWidget" value="/js/vendor/jquery.ui.widget.js"/>
<c:url var="jqueryIframe" value="/js/jquery.iframe-transport.js"/>
<c:url var="jqueryFileupload" value="/js/jquery.fileupload.js"/>

<c:url var="uploadFile" value="/calendar/uploadingFile.html"/>

<input id="fileupload" type="file" name="files[]" data-url="${uploadFile}" multiple>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<script src="${vendorWidget}"></script>
<script src="${jqueryIframe}"></script>
<script src="${jqueryFileupload}"></script>

<style type="text/css">
#dropzone {
    background: palegreen;
    width: 150px;
    height: 50px;
    line-height: 50px;
    text-align: center;
    font-weight: bold;
}
#dropzone.in {
    width: 600px;
    height: 200px;
    line-height: 200px;
    font-size: larger;
}
#dropzone.hover {
    background: lawngreen;
}
#dropzone.fade {
    -webkit-transition: all 0.3s ease-out;
    -moz-transition: all 0.3s ease-out;
    -ms-transition: all 0.3s ease-out;
    -o-transition: all 0.3s ease-out;
    transition: all 0.3s ease-out;
    opacity: 1;
}
</style>

<script>
$(function () {
    $('#fileupload').fileupload({
        dataType: 'json',
        dropZone: $('#dropzone'),
        done: function (e, data) {
            $.each(data.result.files, function (index, file) {
                $('<p/>').text(file.name).appendTo(document.body);
            });
        }
    });
});

$(document).bind('dragover', function (e) {
    var dropZone = $('#dropzone'),
        timeout = window.dropZoneTimeout;
    if (!timeout) {
        dropZone.addClass('in');
    } else {
        clearTimeout(timeout);
    }
    var found = false,
        node = e.target;
    do {
        if (node === dropZone[0]) {
            found = true;
            break;
        }
        node = node.parentNode;
    } while (node != null);
    if (found) {
        dropZone.addClass('hover');
    } else {
        dropZone.removeClass('hover');
    }
    window.dropZoneTimeout = setTimeout(function () {
        window.dropZoneTimeout = null;
        dropZone.removeClass('in hover');
    }, 100);
});
</script>
	

	<div id="dropzone" class="fade well">Drop files here</div>
	
	<c:url value="/perform_logout" var="logoutUrl" />
	<form id="logout" action="${logoutUrl}" method="post" >
  		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<button class="btn btn-link" type="submit">Logout (${pageContext.request.userPrincipal.name})
				<span class="glyphicon glyphicon-star" aria-hidden="true"></span>
			</button>
		</c:if>	
	</form>
</body>  
</html>  