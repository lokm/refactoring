<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" href='<c:url value="/resources/css/style.css" />' />
<link href="https://fonts.googleapis.com/css?family=Lato"
	rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.10.2.js"
	type="text/javascript"></script>
<script src='<c:url value="/resources/js/ajax.js" />'
	type="text/javascript"></script>

	<script src='<c:url value="/resources/js/timer.js" />'
	type="text/javascript"></script>

<link rel="stylesheet"
	href="http://kendo.cdn.telerik.com/2018.1.117/styles/kendo.common.min.css" />
<link rel="stylesheet"
	href="http://kendo.cdn.telerik.com/2018.1.117/styles/kendo.blueopal.min.css" />
<script src="http://kendo.cdn.telerik.com/2018.1.117/js/jquery.min.js"></script>
<script
	src="http://kendo.cdn.telerik.com/2018.1.117/js/kendo.all.min.js"></script>
<script src='<c:url value="/resources/js/createPdf.js" />'></script>

<title>Questionnaire</title>
</head>

<body>
	<section id="questionnaire">

		<!-- LA BASE -->
		<article>
		
			<h2 style="background-color:${questionnaire.category.color}">
				<a title="Acceuil" id="acceuil" href='<c:url value="/home" />'>&#11207;</a>${questionnaire.category.name}</h2>
			<c:if test="${user.role == 'Admin'}">
			<div id="formUser">
							<form
								action="<c:url value='/checkReponse?questionnaire=${questionnaire.id}'/>"
								method="POST">
								Choix d'étudiant : <select name="userForReponse">
									<c:forEach items="${questionnaire.users}"
										var="UserQuestionnaire">
										<option value="${UserQuestionnaire.id }">${UserQuestionnaire.firstname}
											${UserQuestionnaire.lastname }</option>

									</c:forEach>
								</select>
								<input type="submit" value="Selectionner">
							</form>
							<c:if test="${!empty userToCheck}">
							<form
								action="<c:url value='/checkReponse?questionnaire=${questionnaire.id}"'/>"
								method="POST">
								<input type="hidden" name="userForReponse"
									value="${userToCheck.id }"> 
									Questionnaire de ${userToCheck.firstname}
								${userToCheck.lastname } : <select name="checkVersion">
									<c:forEach items="${ReponsesUser}" var="reponse"
										varStatus="countreponse">
										<c:if test="${countreponse.first }">
											<c:forEach begin="2" end="${VersionMaxUser +1  }"
												varStatus="loop">


												<option value="${loop.index-1 }">Version ${loop.index -1}</option>
											</c:forEach>
										</c:if>
									</c:forEach>

								</select> <input type="submit" value="Afficher">
							</form>
							<a href='<c:url value="/checkProfil?profil=${userToCheck.id}" />'><input type="button" value="Autres questionnaires de ${userToCheck.firstname} ${userToCheck.lastname }"/></a>
						</c:if>
						</div>
						</c:if>
			<h3>${questionnaire.name}</h3>
			<p>${questionnaire.description}</p>

			




			<c:choose>
				<c:when test="${!empty userToCheck }">



					<c:if test="${user.role == 'Admin'}">
						
						<%-- <a
							href='<c:url value="questionnaire?newUser=true&questionnaire=${questionnaire.id}" />'>Retour
							au questionnaire</a> --%>


				

						<c:set var="point" value="0" scope="page" />
						<c:set var="nbQuestion" value="0" scope="page" />
						<c:forEach items="${ReponsesUser}" var="reponse"
							varStatus="countreponse">
							<c:if test="${countreponse.first }">
								<c:if test="${questionnaire.version != 1 }">
									<h4>Version du questionnaire n° ${reponse.version }</h4>
								</c:if>
							</c:if>
							<c:set var="nbQuestion" value="${nbQuestion + 1}" scope="page" />
							<c:forEach items="${bonneReponsesUser}" var="bonnereponse"
								varStatus="countbonnereponse">
								<c:if test="${reponse.question == bonnereponse.question }">


									<h3>${reponse.question.question}</h3>
									<br />
									<h5>${reponse.reponse}</h5>
									<br />


									<c:if test="${reponse.question.type == 'QCM' }">

										<c:choose>
											<c:when test="${reponse.reponse == bonnereponse.reponse}">
												<c:set var="point" value="${point + 1}" scope="page" />
												<strong style="color: green">&#10004;</strong>
											</c:when>
											<c:when test="${reponse.reponse != bonnereponse.reponse}">
												<strong style="color: red">&#10060;</strong>

											</c:when>
										</c:choose>
									</c:if>
									<c:if test="${reponse.question.type == 'QUESTION_SIMPLE' }">
										<c:set var="ReussitMotCle" value="0" scope="page" />
										<c:set var="Splitreponses"
											value="${fn:split(bonnereponse.reponse, ' ')}" />
					 

					 mots cles attendu : ${bonnereponse.reponse}
			

								<c:forEach items="${Splitreponses}" var="Splitreponse">
											<c:if
												test="${fn:contains(fn:toLowerCase(reponse.reponse),fn:toLowerCase(Splitreponse))}">
												<c:set var="ReussitMotCle" value="${ReussitMotCle + 1}"
													scope="page" />
											</c:if>
										</c:forEach>

										<c:if
											test="${ReussitMotCle * 100 / fn:length(Splitreponses) >= reponse.question.pourcentageNeed }">
											<c:set var="point" value="${point + 1}" scope="page" />
											<strong style="color: green">&#10004;</strong>
										</c:if>

										<c:if
											test="${ReussitMotCle * 100 / fn:length(Splitreponses) <= reponse.question.pourcentageNeed }">
											<strong style="color: red">&#10060;</strong>
										</c:if>


									</c:if>
								</c:if>




								</p>





							</c:forEach>

							<hr />
						</c:forEach>
						<h4>note : ${point * 100 / nbQuestion}%</h4>

						<input type="button" class="export" value="exporter" />
					</c:if>






				</c:when>

				<c:when test="${empty userToCheck }">
					<!-- USER QUI ON FAIT LE QUESTIONNAIRE -->


					

					<!-- QUAND IL EST SUR LA PAGE DE LUSER ET CHOISIT LES VERSIONS  -->




					<!-- 
		QUAND IL A PAS DE VERSION-->

					<c:if test="${ DoneQuestionnary == 'false' }">


						<c:import url="/resources/fragments/firstVersionQuestionnaire.jsp" />


					</c:if>

					<!-- 	SI DEJA REPONDU -->


					<c:if
						test="${ AddNewVersion == 'false' && DoneQuestionnary == 'true'}">

						<c:import url="/resources/fragments/lastReponses.jsp" />

					</c:if>


					<!-- 	SI IL VEUX REFAIRE LE QUESTIONNAIRE -->

					<c:if test="${ AddNewVersion == 'true' }">

						<c:import url="/resources/fragments/newVersionQuestionnaire.jsp" />
					</c:if>

				</c:when>
			</c:choose>
		</article>


	</section>
</body>
<script src='<c:url value="/resources/js/timer.js" />'
	type="text/javascript"></script>
</html>