	
	
	<c:if test="${user.role == 'Admin'}">
				<select name="type" id="type" form="formQuestion" >
					<option value="DEFAULT" selected disabled>Choix type
						question</option>
					<option value="QCM">QCM</option>
					<option value="QUESTION_SIMPLE">Question Simple</option>
				</select>
			</c:if>
			
			
			
				<article id="afficheQuestionnaire">
<!-- 					enctype="multipart/form-data" -->
					<form
						action="<c:url value='/addQuestion?questionnaire=${questionnaire.id}'/>"
						method="POST"  enctype = "multipart/form-data" id="formQuestion"></form>
					<form
						action="<c:url value='/editQuestion?question=${questionid}&questionnaire=${questionnaire.id}'/>"
						method="POST" id="editQuestion"></form>
						<form
						action="<c:url value='/validQuestionnaire?questionnaire=${questionnaire.id}&user=${user.id}'/>"
						method="POST" id="validQuestionnaire"></form>
				</article>
			
			
			
				<article>
					<input type="hidden" name="nbQuestion" form="validQuestionnaire" value="${fn:length(questionnaire.questions)}">
					<c:forEach items="${questionnaire.questions}" var="question"
						varStatus="count">
						
						<c:if test="${questionid != question.id}">
		
							<h3>${count.count}°. ${question.question}</h3>
							<c:if test="${user.role == 'Admin'}">
								<a title="Editer"
									href='<c:url value="editQuestion?question=${question.id}&questionnaire=${questionnaire.id}" /> '>&#128393;</a>
								<a title="Supprimer"
									href='<c:url value="/deleteQuestion?question=${question.id}&questionnaire=${questionnaire.id}" />'>&#10006;</a>
							</c:if>
							<br />
							
							<c:if test="${!empty question.image}">
							
				
							<br /> 
						
							<img src="<c:url value='/resources/img/${question.image }' /> "> <br>
							
							</c:if>
							<c:choose>
								<c:when test="${question.type=='QCM'}">
		
									<c:forEach items="${question.reponses}" var="reponse">
								
										<c:if test="${reponse.user == null }">
										<label><input type="radio" name="reponseEleve${count.count }" value="${reponse.reponse}" form="validQuestionnaire" >${reponse.reponse}</label>
										</c:if>	<input type="hidden" name="question${count.count }" form="validQuestionnaire" value="${question.id}">
										<br>
									</c:forEach>
		
								</c:when>
								<c:when test="${question.type=='QUESTION_SIMPLE'}">
									<input type="text" placeholder="Votre reponse" name="reponseEleve${count.count }"  form="validQuestionnaire"/>
										<input type="hidden" name="question${count.count }" form="validQuestionnaire" value="${question.id}">
									<br>
								</c:when>
		
							</c:choose>
		
						</c:if>
						<c:if test="${questionid == question.id}">
						
					
								
					${count.count}°. <br /> <textarea name="question-question" form="editQuestion" >${question.question}</textarea>
							<br />
							<c:choose>
								<c:when test="${question.type=='QCM'}">
		
									
									
									<c:forEach items="${question.reponses}" var="reponse" varStatus="countreponse">
									<input type="text" value="${reponse.reponse}" name="question-reponse${countreponse.count}" 
								form="editQuestion">
										
										<br>
									</c:forEach>
		
								</c:when>
								<c:when test="${question.type=='QUESTION_SIMPLE'}">
									<input type="text" value="${question.bonneReponse.reponse}" name="question-bonnereponse" form="editQuestion"/>
									<br>
								</c:when>
		
							</c:choose>
							<input type="submit" value="Editer" form="editQuestion" /> <br />
						</c:if>
					</c:forEach>
					
					<input type="submit" value="Valider" form="validQuestionnaire" /> <br />
					<%-- <a title="Acceuil" href='<c:url value="/createPdf" />'></a> --%>
			<c:if test="${user.role == 'Admin'}"> <input type="button" class="export" value="exporter" /></c:if>	
				</article>
	
			