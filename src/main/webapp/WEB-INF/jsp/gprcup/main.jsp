<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!doctype html>

<head>  
	<title><spring:message code="label.main.title"/></title>  
</head>
<body> 
<script>
	function reloadSeason(urlAction) {
		document.getElementById("theForm").action = urlAction;
		document.getElementById("theForm").submit();	
	}
</script>

	<!-- Main content area -->
	
	<c:url var="saveStandingsAction" value="/gprcup/saveStandings.html"/>	
	<c:url var="showStandingsAction" value="/gprcup/showStandings.html"/>	
	<c:url var="reloadAction" value="/gprcup/main.html"/>
	
	<c:if test="${(!empty lastSeason) || (!empty lastRace)}">
		<c:set var="seedingAction" scope="request" value="/gprcup/seeding.html?"/>
		<c:set var="statisticsAction" scope="request" value="/gprcup/statistics.html?"/>
		<c:if test="${!empty lastSeason}">
			<c:set var="seedingAction" scope="request" value="${seedingAction}currentSeason=${lastSeason}&"/>
			<c:set var="statisticsAction" scope="request" value="${statisticsAction}currentSeason=${lastSeason}&"/>
		</c:if>
		<c:if test="${!empty lastRace}">
			<c:set var="seedingAction" scope="request" value="${seedingAction}currentRace=${lastRace}&"/>
			<c:set var="statisticsAction" scope="request" value="${statisticsAction}currentRace=${lastRace}&"/>
		</c:if>
	</c:if>
	
	
	<c:url var="seedingActionURL" value="${seedingAction}"/>	
	<c:url var="statisticsActionURL" value="${statisticsAction}"/>	
		

	<form method="post" name="theForm" id="theForm" action="${showStandingsAction}" id="formSaveStandings">
	
	<div id="currentSeasonContainer"> <!-- Season combo -->
		<spring:message code="label.season"/>:
		<select name="currentSeason" id="currentSeason" onchange="reloadSeason('${reloadAction}')">
			<c:forEach items="${seasonList}" var="season" varStatus="status">
				<fmt:formatDate var='formattedDate' value='${race.raceDate}' pattern='dd/MM/yyyy HH:mm:ss z'/>
				<option value="${season.idSeason}" ${lastSeason eq season.idSeason ? 'selected' : ''}>${season.nameSeason}</option>
			</c:forEach>
		</select>
	</div>
	
	<div id="currentRaceContainer"> <!-- Races combo -->
		<spring:message code="label.race"/>:
		<select name="currentRace" id="currentRace">
			<option value="-1" ><spring:message code="label.yettoknow"/></option>
			<c:forEach items="${racesList}" var="race" varStatus="status">
				<fmt:formatDate var='formattedDate' value='${race.raceDate}' pattern='dd/MM/yyyy HH:mm:ss z'/>
				<option value="${race.idRace}" ${lastRace eq race.idRace ? 'selected' : ''}>Season ${race.idSeason}, Race ${race.idRace}: ${formattedDate}</option>
			</c:forEach>
		</select>
	</div>
	
	<br>

	<table class="cup" id="Table2">
	<tbody><tr>
		<th width="20px"><spring:message code="label.seed"/></th>
		<th colspan="2"><spring:message code="label.quarterfinals"/></th>
		<th colspan="2"><spring:message code="label.semifinals"/></th>
		<th colspan="2"><spring:message code="label.finals"/></th>
		<th colspan="2"><spring:message code="label.winner"/></th>
	</tr>
	<tr>
		<td><b>1.</b></td>
		<td align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="qfManager01"/>
				<c:param name="themanagerProp" value="idManagerQf1"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerQf1}" pattern="0.#"/>
		<td class="score">
			<input name="qfManager01Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>

		<td rowspan="2" class="arrow" align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="sfManager01"/>
				<c:param name="themanagerProp" value="idManagerSf1"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerSf1}" pattern="0.#"/>
		<td rowspan="2" class="score">
			<input name="sfManager01Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>

		<td rowspan="4" class="even arrow" align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="fManager01"/>
				<c:param name="themanagerProp" value="idManagerFi1"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerFi1}" pattern="0.#"/>
		<td rowspan="4" class="score">
			<input name="fManager01Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>

		<td rowspan="8" class="even2 arrow" align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="fManagerWinner"/>
				<c:param name="themanagerProp" value="idManagerWinner"/>
			</c:import>
		</td>
		<td rowspan="8" class="score">
			&nbsp;
		</td>

	</tr>
	<tr>
		<td><b>8.</b></td>
		<td align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="qfManager02"/>
				<c:param name="themanagerProp" value="idManagerQf2"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerQf2}" pattern="0.#"/>
		<td class="score">
			<input name="qfManager02Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>
	</tr>
	<tr>
		<td class="even"><b>5.</b></td>
		<td class="even" align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="qfManager03"/>
				<c:param name="themanagerProp" value="idManagerQf3"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerQf3}" pattern="0.#"/>
		<td class="score">
			<input name="qfManager03Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>
		
		<td rowspan="2" class="arrow" align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="sfManager02"/>
				<c:param name="themanagerProp" value="idManagerSf2"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerSf2}" pattern="0.#"/>
		<td rowspan="2" class="score">
			<input name="sfManager02Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>
	</tr>
	<tr>
		<td class="even"><b>4.</b></td>
		<td class="even"align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="qfManager04"/>
				<c:param name="themanagerProp" value="idManagerQf4"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerQf4}" pattern="0.#"/>
		<td class="score">
			<input name="qfManager04Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>
	</tr>
	<tr>
		<td><b>3.</b></td>
		<td align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="qfManager05"/>
				<c:param name="themanagerProp" value="idManagerQf5"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerQf5}" pattern="0.#"/>
		<td class="score">
			<input name="qfManager05Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>
		<td rowspan="2" class="even2 arrow" align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="sfManager03"/>
				<c:param name="themanagerProp" value="idManagerSf3"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerSf3}" pattern="0.#"/>
		<td rowspan="2" class="score">
			<input name="sfManager03Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>
		<td rowspan="4" class="even arrow" align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="fManager02"/>
				<c:param name="themanagerProp" value="idManagerFi2"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerFi2}" pattern="0.#"/>
		<td rowspan="4" class="score">
			<input name="fManager02Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>
	</tr>
	<tr>
		<td><b>6.</b></td>
		<td align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="qfManager06"/>
				<c:param name="themanagerProp" value="idManagerQf6"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerQf6}" pattern="0.#"/>
		<td class="score">
			<input name="qfManager06Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>
	</tr>
	<tr>
		<td class="even"><b>7.</b></td>
		<td class="even" align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="qfManager07"/>
				<c:param name="themanagerProp" value="idManagerQf7"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerQf7}" pattern="0.#"/>
		<td class="score">
			<input name="qfManager07Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>
		<td rowspan="2" class="even2 arrow" align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="sfManager04"/>
				<c:param name="themanagerProp" value="idManagerSf4"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerSf4}" pattern="0.#"/>
		<td rowspan="2" class="score">
			<input name="sfManager04Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>
	</tr>
	<tr>
		<td class="even"><b>2.</b></td>
		<td class="even" align="center">
			<c:import url="/WEB-INF/jsp/gprcup/managerEdit.jsp">
				<c:param name="themanagerID" value="qfManager08"/>
				<c:param name="themanagerProp" value="idManagerQf8"/>
			</c:import>
		</td>
		
		<fmt:formatNumber var="formattedScore" value="${currentCupStandings.scoreManagerQf8}" pattern="0.#"/>
		<td class="score">
			<input name="qfManager08Score" size="3" maxlength="3" value="${formattedScore}"/>
		</td>
	</tr>
	</tbody>
	</table>

	<input type="submit" value="<spring:message code="label.save"/>" formaction="${saveStandingsAction}">
	<input type="submit" value="<spring:message code="label.show"/>">
	<br>
	<br>
	<div>
		<a href="${seedingActionURL}"><spring:message code="label.seeding"/></a>
	</div>
	<div>
		<a href="${statisticsActionURL}"><spring:message code="label.statistics"/></a>
	</div>
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

	</form>
</body>  
</html>  
