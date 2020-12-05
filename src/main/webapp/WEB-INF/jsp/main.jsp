<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!doctype html>
<head>  
	<title><spring:message code="application.title"/></title>  
</head>  
<body>  
	

	<c:url var="gprCupLink" value="/gprcup/main.html"/>	
	<c:url var="gprCupIcon" value="/images/trophy.png"/>
	
	<c:url var="gprResultsLink" value="/results/results.html"/>	
	<c:url var="gprResultsIcon" value="/images/results.png"/>
	
	<c:url var="gprTyresLink" value="/tyres/tyres.html"/>	
	<c:url var="gprTyresIcon" value="/images/tyres.png"/>
	
	
	<div class="row">
		<security:authorize access="hasAuthority('Admin')">
		  <div class="col-xs-6 col-md-3">
		    <a href="${gprCupLink}" class="thumbnail">
		      <img src="${gprCupIcon}" title="<spring:message code="label.copa"/>" width="171" height="180">
		    </a>
		  </div>
		  <div class="col-xs-6 col-md-3">
		    <a href="${gprResultsLink}" class="thumbnail">
		      <img src="${gprResultsIcon}" title="<spring:message code="label.resultados"/>" width="171" height="180">
		    </a>
		  </div>
		</security:authorize>
		  <div class="col-xs-6 col-md-3">
		    <a href="${gprTyresLink}" class="thumbnail">
		      <img src="${gprTyresIcon}" title="<spring:message code="label.neumaticos"/>" width="171" height="180">
		    </a>
		  </div>
	</div>	

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