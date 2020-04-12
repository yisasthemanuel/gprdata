<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!doctype html>
<head>  
	<title><spring:message code="application.title"/></title>  

<!-- Para quitarle el borde a la tabla -->
<style>
  .table-borderless tr, .table-borderless td, .table-borderless th {
    border: none !important;
   }
</style>	
</head>  
<body>

<script>
function reloadSeason(urlAction) {
	document.getElementById("theForm").action = urlAction;
	document.getElementById("theForm").submit();	
}
</script>

<c:url var="reloadAction" value="/results/results.html"/>
  
<form class="form-group" name="theForm" id="theForm">
	
	<div id="cabecera">
		<!-- Season combo -->
		<div id="currentSeasonContainer"> 
			<label for="currentSeason"><spring:message code="label.season"/>:</label>
			<select class="form-control form-control-sm" name="currentSeason" id="currentSeason" onchange="reloadSeason('${reloadAction}')">
				<c:forEach items="${seasonList}" var="season" varStatus="status">
					<option value="${season.idSeason}" ${currentSeasonID eq season.idSeason ? 'selected' : ''}>${season.nameSeason}</option>
				</c:forEach>
			</select>
		</div>
		<!-- Races combo -->
		<div id="currentRaceContainer">
			<label for="currentRace"><spring:message code="label.race"/>:</label>
			<select class="form-control form-control-sm" name="currentRace" id="currentRace" onchange="reloadSeason('${reloadAction}')">
				<c:forEach items="${racesList}" var="race" varStatus="status">
					<option value="${race.idRace}" ${currentRaceID eq race.idRace ? 'selected' : ''}>Season ${race.idSeason}, Race ${race.idRace}</option>
				</c:forEach>
			</select>
		</div>
	</div>
</form>		
	<div id="results">
		<div id="exito-critica-publico" class="alert alert-success" role="alert" style="display:none"><spring:message code="label.result.success"/></div>
		<div id="alert-error" class="alert alert-danger" role="alert" style="display:none"><spring:message code="label.result.error"/></div>
		
		<div class="table-responsive">
		<table class="table table-sm table-hover table-borderless">
			<thead>
				<tr>
					<th scope="col"><spring:message code="label.table.manager"/></th>
					<th scope="col"><spring:message code="label.table.raceposition"/></th>
					<th scope="col"><spring:message code="label.table.gridposition"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${managersList}" var="manager" varStatus="status">
				<tr>
					<td>
					<div id="manager">
						<input type="text" value="${manager.codeManager}" id="manager" name="manager" maxlength="5" size="10" disabled></input>
					</div>
					</td>
					<td>
					<div id="racePosition">
						<input type="text" value="${manager.racePosition}" id="racePosition" name="racePosition" maxlength="5" size="10"></input>
					</div>
					</td>
					<td>
					<div id="gridPosition">
						<input type="text" value="${manager.gridPosition}" id="gridPosition" name="gridPosition" maxlength="5" size="10"></input>
					</div>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
	</div>
	
	<button type="button" id="putresults" class="btn btn-primary"><spring:message code="label.result.update"/></button>
	
<script type="text/javascript">

$(document).ready(function() {
	$('#putresults').click(function() {
		$('#exito-critica-publico').hide();
		$('#alert-error').hide();
		$('#cargando').show();
		
		var race = {};
		race["idSeason"] = $('#currentSeason').val();
		race["idRace"] = $('#currentRace').val();
		
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
		
		var griPositions = [];
		$("div[id=gridPosition]").each(function() {
			$(this).find("input[id=gridPosition]").each(function() {
				griPositions.push($(this).val());
			});
		});
		
		var results = [];
		for (var indice in codManagers) {
			results.push({codeManager : codManagers[indice],
					racePosition : racPositions[indice],
					gridPosition : griPositions[indice]});
		}
		
		race["results"] = results;
		
		var jsonString = JSON.stringify(race);
		
		//Peticion PUT
		$.ajax({
		    type: "PUT",
		    //url: "http://localhost:8080/gproapi/managers/results",
		    //url: "http://localhost:9080/managers/results",
		    url: "${gproresultsUrlUpdate}",
		    contentType: "application/json",
		    data: jsonString,
		    success: function (data, textStatus, xhr) {
		    	$('#cargando').hide();
		    	$('#exito-critica-publico').show();
		    	$('#alert-error').hide();
		    },
		    error: function (xhr, textStatus, errorThrown) {
		    	$('#cargando').hide();
		    	$('#alert-error').show();
		    	$('#exito-critica-publico').hide();
		    }
		});
	});
});
	
</script>	
</body>  
</html>  