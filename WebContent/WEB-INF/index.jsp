<!DOCTYPE html>
<html lang="fr">
<head>
<link rel="stylesheet" href='<c:url value="/resources/css/style.css" />' />
<link href="https://fonts.googleapis.com/css?family=Lato"
	rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Questionnaire</title>
</head>
<body>
	<section id="home">

		<aside>
			<!-- RAJOUT DE CATEGORY -->
			<c:choose>


				<c:when test="${empty user}">
					<%-- <c:import url='<c:url value="/resources/fragments/login.jsp" />' /> --%>
					<c:if test="${actionName != 'checkProfil'}">
						<c:import url="/resources/fragments/login.jsp" />
						<a href="<c:url value='/signIn'/>"> <input type="button"
							value="Inscription" /></a>

					</c:if>
					<c:if test="${actionName == 'signIn'}">
						<c:import url="/resources/fragments/signin.jsp" />
					</c:if>


				</c:when>
				<c:otherwise>
					<c:if test="${!empty user }">
					Bienvenue ${user.firstname} ${user.lastname}
				
					<a href="<c:url value='/logOut'/>"><input type="button"
							value="Deconnection" /></a>
					</c:if>
				
				
				
				<!-- AFFICHAGE SI REFUS D'enregister reponse pour cause de temps depassé -->
				
				
						<c:if test="${!empty validateToLate}"> la !! ${validateToLate }</c:if>	



					<c:if test="${user.role == 'Admin'}">

						<c:if test="${ user.role == 'Admin' }">
							<c:import url="/resources/fragments/addCategory.jsp" />
						</c:if>
						<c:import url="/resources/fragments/addQuestionnaire.jsp" />
					</c:if>
				</c:otherwise>
			</c:choose>
			<!-- RAJOUT DE QUESTIONNAIRE   -->
		</aside>
		<nav>

			<article style="background-color: rgb(75, 45, 162)">
				<a href='<c:url value="/home" />'>Tout voir</a>
			</article>

			<c:forEach items="${categories}" var="category">
				<c:choose>
					<c:when test="${categoryid == category.id}">
						<article>
							<form method="post"
								action="<c:url value='/editCategory?id=${categoryid }'/>">
								<select name="category-color" required>
									<option value="" selected disabled>Couleur</option>
									<option style="background-color: rgb(59, 153, 217)"
										value="rgb(59, 153, 217)">bleu</option>
									<option style="background-color: rgb(56, 202, 117)"
										value="rgb(56, 202, 117)">vert</option>
									<option style="background-color: rgb(213, 72, 62)"
										value="rgb(213, 72, 62)">rouge</option>
									<option style="background-color: rgb(223, 123, 47)"
										value="rgb(223, 123, 47)">orange</option>
									<option style="background-color: rgb(253, 203, 46)"
										value="rgb(253, 203, 46)">jaune</option>
									<option style="background-color: rgb(145, 86, 168)"
										value="rgb(145, 86, 168)">magenta</option>
									<option style="background-color: rgb(119, 48, 44)"
										value="rgb(119, 48, 44)">marron</option>
									<option style="background-color: rgb(41, 41, 41)"
										value="rgb(41, 41, 41)">noir</option>
									<input type="text" value="${category.name}"
									name="category-name" required />
								</select> <input type="submit" value="Editer" />
							</form>
						</article>
					</c:when>
					<c:otherwise>
						<article style="background-color:${category.color}">
							<a href='<c:url value="categorie?categorie=${category.id}" />'>${category.name}</a>

							<c:if test="${user.role == 'Admin' }">
								<a title="Editer"
									href='<c:url value="editCategory?categorie=${category.id}" /> '>&#128393;</a>
								<a title="Supprimer"
									href='<c:url value="deleteCategory?categorie=${category.id}" /> '>&#10006;</a>
							</c:if>
						</article>
					</c:otherwise>
				</c:choose>
			</c:forEach>

		</nav>

	</section>
	
	<c:if test="${actionName != 'checkProfil'}">
		<section id="listCategorie">
			<form method="POST"
				action="<c:url value='/editQuestionnaire?id=${questionnaireid}'/>"
				id="form-editquestionnaire"></form>


			<!-- RECHERCHE PAR CATEGORIE -->
			<c:choose>

				<c:when test="${ !empty categorie}">
					<c:forEach items="${categorie.questionnaires}" var="questionnaire">
						<c:if test="${questionnaireid != questionnaire.id}">
							<article>
								<h2 style="background-color:${categorie.color}">${categorie.name}

									<c:if
										test="${user.role == 'Admin' || user.role == 'Admin'}">
										<a title="Supprimer"
											href="<c:url value='deleteQuestionnaire?id=${questionnaire.id}'/>">&#10006;</a>
										<a title="Editer"
											href="<c:url value='editQuestionnaire?id=${questionnaire.id}'/>">&#128393;</a>

									</c:if>

								</h2>
								<a
									href='<c:url value="questionnaire?questionnaire=${questionnaire.id}" />'>
									<h3>${questionnaire.name}</h3>
									<p>${questionnaire.description}</p> <c:forEach
										items="${questionnaire.users}" var="userquestionnaire"
										varStatus="theCount">

										<c:if test="${userquestionnaire.id == user.id}">
											<h4>Questionnaire Effectué</h4>

										</c:if>

									</c:forEach>

								</a>



							</article>
						</c:if>
						<c:if test="${questionnaireid == questionnaire.id}">
							<article>
								<input type="submit" value="Editer"
									form="form-editquestionnaire" /> <select
									name="questionnaire-category" id="questionnaire-category"
									form="form-editquestionnaire" required>
									<option value="" selected disabled>Cat&eacute;gorie</option>
									<c:forEach items="${categories}" var="category">
										<option style="background-color:${category.color}"
											value="${category.id}">${category.name}</option>
									</c:forEach>
								</select> <input type="text" value="${questionnaire.name}"
									name="questionnaire-name" form="form-editquestionnaire"
									required /> <input type="text"
									value="${questionnaire.description}"
									name="questionnaire-description" form="form-editquestionnaire"
									required />

							</article>
						</c:if>
					</c:forEach>
				</c:when>



				<c:otherwise>
					<!-- TOUT LES CATEGORIES -->
					<c:forEach items="${categories}" var="category">
						<c:forEach items="${category.questionnaires}" var="questionnaire">
							<c:if test="${questionnaireid != questionnaire.id}">
								<article>
									<h2 style="background-color:${category.color}">${category.name}

										<c:if
											test="${user.role == 'Admin'}">
											<a title="Supprimer"
												href="<c:url value='deleteQuestionnaire?id=${questionnaire.id}'/>">&#10006;</a>
											<a title="Editer"
												href="<c:url value='editQuestionnaire?id=${questionnaire.id}'/>">&#128393;</a>

										</c:if>
									</h2>
									<a
										href='<c:url value="questionnaire?questionnaire=${questionnaire.id}" />'>
										<h3>${questionnaire.name}</h3>
										<p>${questionnaire.description}</p> <c:forEach
											items="${questionnaire.users}" var="userquestionnaire"
											varStatus="theCount">

											<c:if test="${userquestionnaire.id == user.id}">
												<h4>Questionnaire Effectu&eacute;</h4>
											</c:if>

										</c:forEach>

									</a>
								</article>
							</c:if>
							<c:if test="${questionnaireid == questionnaire.id}">
								<article class="edit">
									<select name="questionnaire-category"
										id="questionnaire-category" form="form-editquestionnaire">
										<option value="" selected disabled>Cat&eacute;gorie</option>
										<c:forEach items="${categories}" var="category">
											<option style="background-color:${category.color}"
												value="${category.id}">${category.name}</option>
										</c:forEach>
									</select> <input type="text" value="${questionnaire.name}"
										name="questionnaire-name" form="form-editquestionnaire" /> <input
										type="text" value="${questionnaire.description}"
										name="questionnaire-description" form="form-editquestionnaire" /><br />
									${editquestionnaireerror}<br> <input type="submit"
										value="Editer" form="form-editquestionnaire" />
								</article>
							</c:if>
						</c:forEach>
					</c:forEach>
				</c:otherwise>
			</c:choose>


		</section>
	</c:if>

	<c:if test="${actionName == 'checkProfil' }">
		<c:if test="${user.role == 'Admin'}">
		
		Questionnaire effectu&eacute; par ${usertocheck.firstname } ${usertocheck.lastname } : 
			
			<section id="listCategorie">
				<c:forEach items="${usertocheck.questionnaires}" var="questionnaire">

					<article>
						<h2 style="background-color:${questionnaire.category.color}">${questionnaire.category.name}</h2>

						<a href='<c:url value="questionnaire?questionnaire=${questionnaire.id}" />'>
							<h3>${questionnaire.name}</h3>
							<p>${questionnaire.description}</p>
						</a>
					</article>

				</c:forEach>
			</section>
		
		</c:if>
	</c:if>







</body>
</html>