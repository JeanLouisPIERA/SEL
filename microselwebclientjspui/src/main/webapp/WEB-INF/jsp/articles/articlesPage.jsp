<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Articles</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body>

	<div id="header1">
		<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
		<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>


		<div class="container col-md-8 col-md-offset-1">

			<div class="wrapper">

				<div>
					<a type="button" class="btn btn-primary btn-md"
						href="/articles/newArticle">Créer un nouveau article</a> <br>
					<br>
				</div>
			</div>
		</div>


		<div class="container col-md-8 col-md-offset-1">

			<div class="wrapper"></div>
		</div>

		<div class="container-fluid">
			<div class="row justify-content-center mb-3">
				<div class="col-md-10 col-md-offset-1 ">

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3>
								<center>Recherche des articles enregistrés</center>
							</h3>
						</div>

						<div class="panel-body">

							<form:form class="form-inline d-flex bd-highlight"
								action="/articles" method="GET" modelAttribute="article">

								<fieldset class="form-row">

									<fieldset class="form-group">
										<label> Type d'Article : </label> <select name="typeArticle"
											class="form-control">
											<option value="${nomTypeArticle}">${'Votre Choix'}</option>
											<c:forEach var="typeArticle" items="${typeArticlesList}">
													<option value="${typeArticle.typeName}">${typeArticle.typeName.toString()}</option>
											</c:forEach>
										</select>
									</fieldset>

									<fieldset class="form-group">
										<label> Statut de l'article : </label> <select
											name="statutDocument" class="form-control">
											<option value="${enumStatutDocument}">${'Votre Choix'}</option>
											<c:forEach var="enumStatutDocument" items="${enumStatutDocumentList}">
												<c:if test="${enumStatutDocument.getCode() != 'INCONNU'}">
													<option value="${enumStatutDocument.getCode()}">${enumStatutDocument.toString()}</option>
												</c:if>
											</c:forEach>
										</select>
									</fieldset>

									

									<button class="btn-sm btn-primary">Valider</button>
								</fieldset>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>



		<div class="container-fluid">
			<div class="row d-flex justify-content-center">
				<div class="container col-md-10 col-md-offset-1">
					<div class="wrapper">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h3>
									<center>Articles</center>
								</h3>
							</div>
							<div class="panel-body">
								<table
									class="table table-striped table-condensed table-bordered"
									style="text-align: center">
									<thead>
										<tr>
											<th style="text-align: center">Titre</th>
											<th style="text-align: center">Nom de l'auteur</th>
											<th style="text-align: center">Date de Création</th>
											<th style="text-align: center">Date d'Archivage</th>
											<th style="text-align: center">Type de Document</th>
											<th style="text-align: center">Statut</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="article" items="${articles}">
											<tr>
												<td>${article.titre}</td>
												<td>${article.auteurUsername}</td>
												<td>${article.dateCreation}</td>
												<td>${article.dateArchivage}</td>
												<td>${article.typeArticle.getTypeName()}</td>
												<td>${article.statutDocument.getCode()}</td>

												<td><a type="button" class="btn btn-primary"
													href="/articles/${article.id}">Contenu</a></td>
												
												<sec:authorize access="isAuthenticated()"> 	
												<td><a type="button" class="btn btn-success"
													href="/articles/moderation/${article.id}">Modérer</a>
												</td>
												
												<td><a type="button" class="btn btn-warning"
													href="/articles/archivage/${article.id}">Archiver</a>
												</td>
												</sec:authorize>

											</tr>
										</c:forEach>

									</tbody>
								</table>
								<div class="container">
									<!-- div class="row-lg-2" -->
									<c:if test="${articles.size() > 0 }">
										<!-- ul class="pagination-sm"-->
										<ul class="nav nav-pills">
											<c:forEach begin="0" end="${totalPages-1}" var="page">
												<li class="page-item"><a class="btn btn-info"
													href="articles?page=${page}&size=${size}"
													class="page-target">${page+1}</a></li>
											</c:forEach>
										</ul>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


	</div>



	<div id="footer">
		<%@ include file="/WEB-INF/jsp/common/footer1.jspf"%>
	</div>


</body>

</html>