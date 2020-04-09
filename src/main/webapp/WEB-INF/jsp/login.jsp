<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="description" content="">
    <meta name="author" content="">
    
    <title><spring:message code="login.title"/></title>

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<c:url var="ie10viewportUrl" value="/css/ie10-viewport-bug-workaround.css"/>
    <link href="${ie10viewportUrl}" rel="stylesheet">

    <!-- Custom styles for this template -->
	<c:url var="signinUrl" value="/css/signin.css"/>
    <link href="${signinUrl}" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>
    <div class="container full">
    	<c:url value="/perform_login" var="loginUrl" />

      	<form class="form-signin" action="${loginUrl}" method='POST'>
	        <c:if test="${param.error != null}">
				<div class="alert alert-danger">
					<p><spring:message code="login.error"/></p>
				</div>
			</c:if>
			
	        <c:if test="${param.logout != null}">
				<div class="alert alert-success">
					<p><spring:message code="login.logout"/></p>
				</div>
	        </c:if>
	        
	        <h2 class="form-signin-heading"><spring:message code="login.heading"/></h2>
	        
	        <spring:message var="userNam" code="login.username"/>        
	        <label for="username" class="sr-only">${userNam}</label>
	        <input type="text" id="username" name="username" class="form-control" placeholder="${userNam}" required autofocus>
	        
	        <spring:message var="passwd" code="login.password"/>
	        <label for="inputPassword" class="sr-only">${passwd}</label>
	        <input type="password" id="password" name="password" class="form-control" placeholder="${passwd}" required>
	        
	        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="login.signin"/></button>
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      	</form>
    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<c:url var="jsIE10viewportUrl" value="/js/ie10-viewport-bug-workaround.js"/>
    <script src="${jsIE10viewportUrl}"></script>
  </body>
</html>
