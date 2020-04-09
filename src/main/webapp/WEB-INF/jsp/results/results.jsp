<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!doctype html>
<head>  
	<title><spring:message code="application.title"/></title>  
</head>  
<body>  
	
	<div class="row">Lo que en un futuro serán los resultados del equipo. Se mostrará el equipo (no editable), la temporada (desplegable)
	y una tabla con las carreras de la temporada en las columnas y los managers en las filas. En cada cuadro la posición que han ocupado
	incluyendo el DNS (no participó)
	</div>
	
	<div class="row">Por ahora mostramos el equipo sobre el que se realizarán los resultados: <p class="lead">${team.teamName}</p></div>
	
	<div class="row">
		<div class="form-group">
			<label for="selectedSeason">Season</label>
			<select class="form-control" name="selectedSeason" id="selectedSeason">
				<c:forEach items="${seasonsList}" var="season" varStatus="status">
					<option value="${season.idSeason}" ${currentSeason.idSeason eq season.idSeason ? 'selected' : ''}>${season.nameSeason}</option>
				</c:forEach>
			</select>
			<label for="selectedRace">Race</label>
			<select class="form-control" name="selectedRace" id="selectedRace">
				<c:forEach items="${racesList}" var="race" varStatus="status">
					<option value="${race.codeRace}" ${currentRace.codeRace eq race.codeRace ? 'selected' : ''}>${race.descriptionRace}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<c:if test="${empty currentRace}">
		<div class="col-sm-4">
			<table class="table table-striped table-hover table-condensed">
				<thead>
					<tr>
						<th>Manager</th>
						<th class="text-right">Position</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${managersList}" var="manager" varStatus="statusRow">
						<tr>
							<td class="col-sm-3">${manager.firstName} ${manager.lastName}</td>
							<c:set var="cellName" scope="request" value="position_${manager.idManager}"/>
							<td class="col-sm-1">
								<select class="form-control" name="${cellName}" id="${cellName}">
									<c:forEach var="pos" begin="0" end="41" varStatus="statusPos">
										<c:choose>
											<c:when test="${statusPos.last}">
												<option value="-1">DNS</option>
											</c:when>
											<c:when test="${statusPos.first}">
												<option value=""></option>
											</c:when>
											<c:otherwise>
												<option value="${pos}">${pos}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>							
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		
		</div>
	</c:if>
	
<c:url var='reloadRaces' value='/results/allraces.html'/>
	
<script type="text/javascript">
$(document).ready(function() {
	
	//onchange season
	$('#selectedSeason').change(function() {
		$('#cargando').show();
		
		codSeason = $('#selectedSeason').val();
		
		$.ajax({
			url : '${reloadRaces}',
			contentType : 'application/json',
			type : 'GET',
			dataType : 'json',
			statusCode : {406 : function() {alert("406!")}},
			data : 'codSeason=' + codSeason,
			success : function(data) {
				alert(data);
				$('#cargando').hide();
			}
		});
	});
	
	//onchange race
	$('#selectedRace').change(function() {
		$('#cargando').show();
		setTimeout(function() {
			$('#cargando').hide();
	    }, 2000);
		
	});
});
</script>	
</body>  
</html>  