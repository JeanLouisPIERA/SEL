<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Blocages</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body>

	<div id="header1">
		<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
		<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>

		<div class="container col-md-8 col-md-offset-1">

			<div class="wrapper"></div>
		</div>

		<div class="container-fluid">
			<div class="row justify-content-center mb-3">
				<div class="col-md-10 col-md-offset-1 ">

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3>
								<center>Recherche des blocages enregistrés</center>
							</h3>
						</div>

						<div class="panel-body">

							<form:form class="form-inline d-flex bd-highlight" action="/blocages" method="GET" modelAttribute="blocage">

								<fieldset class="form-row">
								
									<fieldset class="form-group">
										<label>N° du blocage :</label> <input type="text"
											name="id" value="${blocage.id}" />
									</fieldset>

									<fieldset class="form-group">
										<label>Identifiant de l'adhérent :</label> <input type="text"
											name="adherentId" value="${blocage.adherentId}" />
									</fieldset>

									<fieldset class="form-group">
										<label>Nom de l'adhérent :</label> <input type="text" name="adherentUsername"
											value="${blocage.adherentUsername}" />
									</fieldset>

									<fieldset class="form-group">
										<label> Statut : </label> <select name="statutBlocage"
											class="form-control">
											<option value="${statutBlocage}">${'Votre Choix'}</option>
											<c:forEach var="enumStatutBlocage" items="${enumStatutBlocageList}">
												<c:if test="${enumStatutBlocage.getCode() != 'INCONNU'}">
													<option value="${enumStatutBlocage.getCode()}">${enumStatutBlocage.toString()}</option>
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
									<center>Liste des comptes d'adhérents bloqués</center>
								</h3>
							</div>
							<div class="panel-body">
								<table
									class="table table-striped table-condensed table-bordered"
									style="text-align: center">
									<thead>
										<tr>
		
											<th style="text-align: center">Référence</th>
											<th style="text-align: center">Identifiant de l'adhérent</th>
											<th style="text-align: center">Nom de l'adhérent</th>
											<th style="text-align: center">Date début de blocage</th>
											<th style="text-align: center">Date de fin de blocage</th>
											<th style="text-align: center">Situation</th>
											<th style="text-align: center">N° de l'échange à l'origine du blocage</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="blocage" items="${blocages}">
											<tr>											
												<td>${blocage.id}</td>
												<td>${blocage.adherentId}</td>
												<td>${blocage.adherentUsername}</td>
												<td>${blocage.dateDebutBlocage}</td>
												<td>${blocage.dateFinBlocage}</td>
												<td>${blocage.statutBlocage.toString()}</td>
												<td>${blocage.echange.getId()}</td>
												
												<c:if test="${blocage.statutBlocage.getCode()=='ENCOURS'}">
													<td><a type="button" class="btn btn-success"
														href="/blocages/close/${blocage.id}">Annuler</a>
													</td>
												</c:if>


											</tr>
										</c:forEach>

									</tbody>
								</table>
								<div class="container">
									<!-- div class="row-lg-2" -->
									<c:if test="${blocages.size() > 0 }">
										<!-- ul class="pagination-sm"-->
										<ul class="nav nav-pills">
											<c:forEach begin="0" end="${totalPages-1}" var="page">
												<li class="page-item"><a class="btn btn-info"
													href="blocages?page=${page}&size=${size}"
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