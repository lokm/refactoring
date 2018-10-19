
<!--  FORMULAIRE DE CHOIX DES VERSIONS DE REPONSE -->

	
<!-- AFFICHAGE DES RESULTATS -->

<c:set var="point" value="0" scope="page" />
<c:set var="nbQuestion" value="0" scope="page" />

<c:forEach items="${reponses}" var="reponse" varStatus="countreponse">
	<c:if test="${countreponse.first }">
	<c:if test="${questionnaire.version != 1 }">	<h4>Vous avez complété ${reponse.version } fois ce questionnaire <br />Il vous reste ${questionnaire.version - lastVersion} tentatives</h4></c:if>
	</c:if>
	<c:set var="nbQuestion" value="${nbQuestion + 1}" scope="page" />
	<c:forEach items="${bonneReponses}" var="bonnereponse"
		varStatus="countbonnereponse">




		<c:if test="${reponse.question == bonnereponse.question }">
		
		<c:if test="${user.role == 'Admin'}">	
		<p>
 				Question :
 				<h3>${reponse.question.question}</h3>
 				
 			
 					</c:if>
			
				<c:if test="${reponse.question.type == 'QCM' }">
				
							<br />
 					 reponse  :	 <c:if test="${reponse.reponse == 'Pas Repondu' || reponse.reponse != bonnereponse.reponse }"><h5 style="color:red;">${reponse.reponse}</h5></c:if>
 							
 								  <c:if test="${reponse.reponse == bonnereponse.reponse}"><h5 id="Answer">${reponse.reponse}</h5></c:if>
 				<hr />
				
						<c:if test="${reponse.reponse == bonnereponse.reponse}">
							<c:set var="point" value="${point + 1}" scope="page" />
						
						
						</c:if>
						
					
						
				

				</c:if>
				<c:if test="${reponse.question.type == 'QUESTION_SIMPLE' }">

					<c:set var="ReussitMotCle" value="0" scope="page" />

					<c:set var="Splitreponses"
						value="${fn:split(bonnereponse.reponse, ' ')}" />
					 
		</p>

			<c:forEach items="${Splitreponses}" var="Splitreponse">


				<c:if test="${fn:contains(fn:toLowerCase(reponse.reponse),fn:toLowerCase(Splitreponse))}">
					<c:set var="ReussitMotCle"
						value="${ReussitMotCle + 1}" scope="page" />
				</c:if>
			</c:forEach>
		
		<c:choose>
		
		<c:when test="${ReussitMotCle * 100 / fn:length(Splitreponses) >= reponse.question.pourcentageNeed }">
		
		
			<c:set var="point" value="${point + 1}" scope="page" />
		
		
			
							<br />
 					 reponse  :	<h5 id="Answer">${reponse.reponse}</h5>
 					 
 				
 							
		
		</c:when>
		
			<c:otherwise>
			
				 reponse  : <h5 style="color:red;">${reponse.reponse}</h5>
				 
			</c:otherwise>
			
			</c:choose>
		
		
			
 								 
			
			
		
			
		
			
</c:if>




		</c:if>



				
				
				
	
				
					
					
		</c:forEach>
				
				
		</c:forEach>
		<c:set var="resultat" value="${point * 100 / nbQuestion}" scope="page" />
	
						
		<h4>Réussite de la dernière tentative : ${fn:substring(resultat, 0, 5)} %</h4> 
			<c:if test="${user.role == 'Admin'}"> <input type="button" class="export" value="exporter" /> </c:if>
			<br>
				<c:if test="${user.role == 'Admin'}"> 	
				<a title="refaire" href='<c:url value="/questionnaire?nextVersion=${reponse.version +1}&questionnaire=${questionnaire.id}" />'> Refaire le questionnaire</a>
		</c:if>
		<c:if test="${user.role != 'Admin'}">
		<c:if test="${lastVersion < questionnaire.version }">
		
			<a title="refaire" href='<c:url value="/questionnaire?nextVersion=${reponse.version +1}&questionnaire=${questionnaire.id}" />'><input type="button" value="Refaire le questionnaire"/></a>
		</c:if>
		</c:if>
		<c:if test="${questionnaire.version != 1 && user.role == 'Admin'}"><form
		action="<c:url value='/questionnaire?questionnaire=${questionnaire.id}"'/>"
		method="POST" id="SelectVersion"></form>
		
		<input type="submit" value="Chercher une Version" form="SelectVersion" />
		<select name="checkVersion" form="SelectVersion">
			<c:forEach items="${reponses}" var="reponse" varStatus="countreponse">
				<c:if test="${countreponse.first }">
					<c:forEach begin="2" end="${lastVersion +1  }" varStatus="loop">
		
		
						<option value="${loop.index-1 }">${loop.index -1}</option>
					</c:forEach>
				</c:if>
			</c:forEach>
		
	</select>
</c:if>
		