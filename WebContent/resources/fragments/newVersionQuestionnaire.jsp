<script type="text/javascript">
	jQuery(function($){
	    // Lancement du compte à rebours au chargement de la page
		alert("titi");
	    countdownManager.init('2018-10-18 15:00:00');
	});
</script>





<article id="afficheQuestionnaire">

	<form
		action="<c:url value='/validQuestionnaire?questionnaire=${questionnaire.id}&user=${user.id}'/>"
		method="POST" id="validQuestionnaire"></form>
	<form action="<c:url value='/createPdf'/>" method="POST" id="createPdf"></form>
</article>

<p>Il vous reste <span id="countdown_min"></span> minutes et <span id="countdown_sec"></span> secondes</p>

<article>
	<input type="hidden" name="nbQuestion" form="validQuestionnaire"
		value="${fn:length(questionnaire.questions)}">
	<c:forEach items="${questionnaire.questions}" var="question"
		varStatus="count">


		<h3>${count.count}°.${question.question}</h3>
		<input type="hidden" name="question${count.count}"
			value="${question.question}" form="createPdf" />

		<br />


		<c:if test="${!empty question.image}">


			<br />

			<img src="<c:url value='/resources/img/${question.image }' /> ">
			<br>

		</c:if>

		<c:choose>
			<c:when test="${question.type=='QCM'}">

				<c:forEach items="${question.reponses}" var="reponse">

					<c:if test="${reponse.user == null}">
						<label><input type="radio"
							name="reponseEleve${count.count }" value="${reponse.reponse}"
							form="validQuestionnaire">${reponse.reponse}</label>
					</c:if>
					<input type="hidden" name="question${count.count }"
						form="validQuestionnaire" value="${question.id}">
					<br>
				</c:forEach>

			</c:when>
			<c:when test="${question.type=='QUESTION_SIMPLE'}">
				<input type="text" placeholder="Votre reponse"
					name="reponseEleve${count.count }" form="validQuestionnaire" />
				<input type="hidden" name="question${count.count }"
					form="validQuestionnaire" value="${question.id}">
				<br>
			</c:when>

		</c:choose>



	</c:forEach>

	<input type="submit" value="Valider" form="validQuestionnaire" /> <br />
	<%-- <a title="Acceuil" href='<c:url value="/createPdf" />'></a> --%>
	<c:if test="${user.role == 'Admin'}">
		<input type="button" class="export" value="exporter" />
	</c:if>
</article>

