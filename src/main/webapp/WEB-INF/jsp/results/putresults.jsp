<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!doctype html>
<head>  
	<title><spring:message code="application.title"/></title>  
</head>  
<body>  
	
	<div class="row">Resultados para una jornada (${Texto})</div>
	
	<div id="cabecera">
		<div id="idSeason">
			<label for="season">Season:</label>
			<input type="text" id="season" name="season" value="${currentSeasonID}" maxlength="5" size="10">
		</div>
		<div id="idRace">
			<label for="season">Race:</label>
			<input type="text" id="race" name="race" value="${currentRace}" maxlength="5" size="10">
		</div>
		<div id="results">
			<table>
				<tr>
					<td>
					<div id="manager">
						<label>Manager code (5 characters)</label>
					</div>
					</td>
					<td>
					<div id="racePosition">
						<label>Race position(1 to 40 or leave blank for DNS)</label>
					</div>
					</td>
				</tr>
				<c:forEach items="${managersList}" var="manager" varStatus="status">
				<tr>
					<td>
					<div id="manager">
						<input type="text" value="${manager.codeManager}" id="manager" name="manager" maxlength="5" size="10" disabled></input>
					</div>
					</td>
					<td>
					<div id="racePosition">
						<input type="text" value="" id="racePosition" name="racePosition" maxlength="5" size="10"></input>
					</div>
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	
	<input type="button" id="putresults" value='<spring:message code="label.copa.edit"/>'></input>
		
<script type="text/javascript">

$(document).ready(function() {
	$('#putresults').click(function() {
		$('#cargando').show();
		var race = {};
		race["idSeason"] = $('#season').val();
		race["idRace"] = $('#race').val();
		
		var codManagers = [];
		$("div[id=manager]").each(function() {
			$(this).find("input[id=manager]").each(function() {
				codManagers.push($(this).val());
			});
		});
		
		var racPositions = [];
		$("div[id=racePosition]").each(function() {
			$(this).find("input[id=racePosition]").each(function() {
				racPositions.push($(this).val());
			});
		});
		
		var results = [];
		for (var indice in codManagers) {
			results.push({codeManager : codManagers[indice],
					racePosition : racPositions[indice]});
		}
		
		race["results"] = results;
		
		var jsonString = JSON.stringify(race);
		
		//Peticion PUT
		$.ajax({
		    type: "PUT",
		    //url: "http://localhost:8080/gproapi/managers/results",
		    url: "http://localhost:9080/managers/results",
		    contentType: "application/json",
		    data: jsonString,
		    success: function (data, textStatus, xhr) {
		    	$('#cargando').hide();
		    	alert("OK");
		    },
		    error: function (xhr, textStatus, errorThrown) {
		    	$('#cargando').hide();
		    	alert("ERROR");
		    }
		});
	});
});
	
</script>	
</body>  
</html>  