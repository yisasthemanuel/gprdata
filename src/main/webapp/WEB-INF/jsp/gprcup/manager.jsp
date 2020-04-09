<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:set var="manager" scope="request" value="${requestScope[param.themanager]}"/>

<c:set var="cuptitles" scope="request" value="${manager.cupWins}"/>

<c:choose>
    <c:when test="${param.round eq 'WI'}">
       <c:set var="mwidth" scope="request" value="80"/>
       <c:set var="mheight" scope="request" value="80"/>
       <c:set var="cuptitles" scope="request" value="${cuptitles + 1}"/>       
    </c:when>
    <c:when test="${param.round eq 'FN'}">
       <c:set var="mwidth" scope="request" value="70"/>
       <c:set var="mheight" scope="request" value="70"/>
    </c:when>
    <c:when test="${param.round eq 'SF'}">
       <c:set var="mwidth" scope="request" value="60"/>
       <c:set var="mheight" scope="request" value="60"/>
    </c:when>
    <c:otherwise>
       <c:set var="mwidth" scope="request" value="40"/>
       <c:set var="mheight" scope="request" value="40"/>
    </c:otherwise>
</c:choose>
<div align="center">
	<c:if test="${!empty manager}">
		<c:url var="managerUrl" value="${manager.avatarPath}" scope="request"/>
 		<c:url var="showImageUrl" value="/image/manager/${manager.idManager}/show.html" scope="request"/>
		<img src="${showImageUrl}" width="${mwidth}" height="${mheight}">
	    <c:if test="${(manager.cupWins > 0) || (param.round eq 'WI')}">
	      <span class="cup">${cuptitles}</span>
	    </c:if>
	</c:if>
	<c:if test="${empty manager}">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</c:if>
</div>