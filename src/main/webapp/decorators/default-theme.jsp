<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>  
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    
	<spring:message var="titlePage" code="default.theme.title"/>
	<title><sitemesh:write property="title"/></title>
	  
	<c:url var="faviconUrl" value="/favicon.ico"/>
	<c:url var="gprdataCSS" value="/css/gprdata.css"/>
	
    <!-- Bootstrap core CSS -->
	<c:url var="bootstrapMinUrl" value="/css/bootstrap.min.css"/>
    <link href="${bootstrapMinUrl}" rel="stylesheet">

    <link rel="icon" type="image/x-icon" href="${faviconUrl}"/>
	<link rel="stylesheet" href="${gprdataCSS}" type="text/css" />
	
	<!-- jQuery stuff -->
	<c:url var="ctx" value="/"/>
	
	<script type="text/javascript" src="${ctx}js/jquery.min.js"></script>
	
	<c:url var="mainAction" value="/main.html"/>
	<a href="${mainAction}"><spring:message code="label.home"/></a>
	
	
	<sitemesh:write property="head"/>
</head>  
<body class="container full">
	<header>  
<!-- 		<h1>Main page</h1>   -->
	</header>  
	<nav>
		<!-- Navigation -->
<!-- 		<h2>Navigation Main page</h2> -->
<!-- 		<p>Navigation Main page</p> -->
	</nav>  
	<section id="intro">
		<!-- Introduction -->
<!-- 		<h2>Introduction Main page</h2> -->
<!-- 		<p>Introduction Main page</p> -->
	</section>  
	<section id="principal">
		<div id="theprincipal">
			<sitemesh:write property="body"/>
			<div id="cargando" style="display:none">
				<p><spring:message code="general.cargando" /><img title="<spring:message code="general.cargando" />" src='${ctx}images/ajax-loader.gif' /></p>
			</div>
		</div>
	</section>  
	<aside>  
		<!-- Sidebar   -->
<!-- 		<h2>Sidebar</h2> -->
<!-- 		<p>Sidebar</p> -->
	</aside>  
	<footer>  
		<!-- Footer -->
<!-- 		<h2>Footer</h2>   -->
<!-- 		<p>Footer</p> -->
	</footer>    
</body>  
</html>  