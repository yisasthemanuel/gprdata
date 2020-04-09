<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!doctype html>  
<html>  
<head>  
	<title><spring:message code="label.main.title"/></title>  
</head>  
<body> 
	<!-- Main content area -->
	<c:url var="backToMainAction" value="/gprcup/main.html" scope="request"/>
	<form method="post" action="${backToMainAction}" id="formShowStandings">

	<table cellpadding="0" cellspacing="0" class="cup" id="Table2">
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
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="QF01"/>
			</c:import>
		</td>
		<td class="${currentCupStandings.idManagerQf1 eq currentCupStandings.idManagerSf1 ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerQf1}" pattern="0.#"/></td>

		<td rowspan="2" class="arrow" align="center">
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="SF01"/>
				<c:param name="round" value="SF"/>
			</c:import>
		</td>
		<td rowspan="2" class="${currentCupStandings.idManagerSf1 eq currentCupStandings.idManagerFi1 ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerSf1}" pattern="0.#"/></td>

		<td rowspan="4" class="even arrow" align="center">
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="FI01"/>
				<c:param name="round" value="FN"/>
			</c:import>		
		</td>
		<td rowspan="4" class="${currentCupStandings.idManagerFi1 eq currentCupStandings.idManagerWinner ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerFi1}" pattern="0.#"/></td>

		<td rowspan="8" class="even2 arrow" align="center">
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="WI01"/>
				<c:param name="round" value="WI"/>
			</c:import>
		</td>
		<td rowspan="8" class="score">
			<c:if test="${!empty currentCupStandings.idManagerWinner}">
				<c:url var="cupUrl" value="/images/scup.gif"/>
				<img src="${cupUrl}" width="12" height="12">
			</c:if>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td><b>8.</b></td>
		<td align="center">
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="QF02"/>
			</c:import>
		</td>
		<td class="${currentCupStandings.idManagerQf2 eq currentCupStandings.idManagerSf1 ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerQf2}" pattern="0.#"/></td>
	</tr>
	<tr>
		<td class="even"><b>5.</b></td>
		<td class="even">
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="QF03"/>
			</c:import>
		</td>
		<td class="${currentCupStandings.idManagerQf3 eq currentCupStandings.idManagerSf2 ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerQf3}" pattern="0.#"/></td>

		<td rowspan="2" class="arrow">
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="SF02"/>
				<c:param name="round" value="SF"/>
			</c:import>
		</td>
		<td rowspan="2" class="${currentCupStandings.idManagerSf2 eq currentCupStandings.idManagerFi1 ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerSf2}" pattern="0.#"/></td>
	</tr>
	<tr>
		<td class="even"><b>4.</b></td>
		<td class="even">
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="QF04"/>
			</c:import>
		</td>
		<td class="${currentCupStandings.idManagerQf4 eq currentCupStandings.idManagerSf2 ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerQf4}" pattern="0.#"/></td>

<!--
CUP
<img src="/gprdata/images/scup.gif" align="top">
-->
	</tr>
	<tr>
		<td><b>3.</b></td>
		<td>
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="QF05"/>
			</c:import>
		</td>
		<td class="${currentCupStandings.idManagerQf5 eq currentCupStandings.idManagerSf3 ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerQf5}" pattern="0.#"/></td>

		<td rowspan="2" class="even2 arrow">
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="SF03"/>
				<c:param name="round" value="SF"/>
			</c:import>
		</td>
		<td rowspan="2" class="${currentCupStandings.idManagerSf3 eq currentCupStandings.idManagerFi2 ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerSf3}" pattern="0.#"/></td>

		<td rowspan="4" class="even arrow">
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="FI02"/>
				<c:param name="round" value="FN"/>
			</c:import>
		</td>
		<td rowspan="4" class="${currentCupStandings.idManagerFi2 eq currentCupStandings.idManagerWinner ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerFi2}" pattern="0.#"/></td>
	</tr>
	<tr>
		<td><b>6.</b></td>
		<td>
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="QF06"/>
			</c:import>
		</td>
		<td class="${currentCupStandings.idManagerQf6 eq currentCupStandings.idManagerSf3 ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerQf6}" pattern="0.#"/></td>
	</tr>
	<tr>
		<td class="even"><b>7.</b></td>
		<td class="even">
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="QF07"/>
			</c:import>
		</td>
		<td class="${currentCupStandings.idManagerQf7 eq currentCupStandings.idManagerSf4 ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerQf7}" pattern="0.#"/></td>

		<td rowspan="2" class="even2 arrow">
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="SF04"/>
				<c:param name="round" value="SF"/>
			</c:import>
		</td>
		<td rowspan="2" class="${currentCupStandings.idManagerSf4 eq currentCupStandings.idManagerFi2 ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerSf4}" pattern="0.#"/></td>
	</tr>
	<tr>
		<td class="even"><b>2.</b></td>
		<td class="even">
			<c:import url="/WEB-INF/jsp/gprcup/manager.jsp">
				<c:param name="themanager" value="QF08"/>
			</c:import>
		</td>
		<td class="${currentCupStandings.idManagerQf8 eq currentCupStandings.idManagerSf4 ? 'scoreW' : 'score'}"><fmt:formatNumber value="${currentCupStandings.scoreManagerQf8}" pattern="0.#"/></td>
	</tr>
	</tbody>
	</table>
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<input type="hidden" name="currentSeason" value="${currentCupStandings.idSeason}"/>	
	<br>
	<br>
	<input type="submit" value="<spring:message code="label.copa.edit"/>">
	
	</form>

</body>  
</html>  