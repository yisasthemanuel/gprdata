<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:set var="managerEdit" scope="request" value="${currentCupStandings[param.themanagerProp]}"/>

<select name="${param.themanagerID}" id="${param.themanagerID}">
	<option value="NONE" ${empty managerEdit ? 'selected' : ''}><spring:message code="label.yettoknow"/></option>
	<c:forEach items="${managersList}" var="manager" varStatus="status">
		<option value="${manager.idManager}" ${managerEdit eq manager.idManager ? 'selected' : ''}>${manager.firstName} ${manager.lastName} (${manager.cupWins})</option>
	</c:forEach>
</select>