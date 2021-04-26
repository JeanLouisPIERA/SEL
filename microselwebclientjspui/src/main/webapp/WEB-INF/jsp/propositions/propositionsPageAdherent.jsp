<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Propositions</title>
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
								<center>Recherche des propositions enregistrées</center>
							</h3>
						</div>

						<div class="panel-body">

							<form:form class="form-inline d-flex bd-highlight"
								action="/propositions/adherent" method="GET"
								modelAttribute="proposition">

								<fieldset class="form-row">

									<fieldset class="form-group">
										<label>Titre de la proposition :</label> <input type="text"
											name="titre" value="${propositionCriteria.titre}" />
									</fieldset>

									<fieldset class="form-group">
										<label>Ville :</label> <input type="text" name="ville"
											value="${propositionCriteria.ville}" />
									</fieldset>


									<fieldset class="form-group">
										<label>Code Postal :</label> <input type="text"
											name="codePostal" value="${propositionCriteria.codePostal}" />
									</fieldset>


									<%-- <fieldset class="form-group">
				 <label>Objet de la proposition :</label>
				 <input type="text" name="nomCategorie" value="${livreCriteria.nomCategorie}"/>
				 </fieldset> --%>

									<fieldset class="form-group">
										<label> Rubrique : </label> <select name="enumCategorie"
											class="form-control">
											<option value="${enumCategorie}">${'Votre Choix'}</option>
											<c:forEach var="enumCategorie" items="${enumCategorieList}">
												<c:if test="${enumCategorie.getCode() != 'INCONNUE'}">
													<option value="${enumCategorie.getCode()}">${enumCategorie.toString()}</option>
												</c:if>
											</c:forEach>
										</select>
									</fieldset>

									<%-- <form:select path="selectEnumCategorie">
				    <form:option value="0" label="Choisissez votre rubrique" />
				    <form:options items="${enumCategorieList}" />
				</form:select> --%>



									<fieldset class="form-group">
										<label>Type de Proposition : </label> <select
											name="enumTradeType" class="form-control">
											<option value="${enumTradeType}">${'Votre Choix'}</option>
											<c:forEach var="enumTradeType" items="${enumTradeTypeList}">
												<c:if test="${enumTradeType.getCode() != 'INCONNU'}">
													<option value="${enumTradeType.getCode()}">${enumTradeType.toString()}</option>
												</c:if>
											</c:forEach>
										</select>
									</fieldset>

									<%-- <form:select path="selectEnumTRadeType">
				    <form:option value="0" label="Choisissez votre type de proposition" />
				    <form:options items="${enumTradeTypeList}" />
				</form:select> --%>

									<fieldset class="form-group">
										<label>Statut de la Proposition : </label> <select
											name="enumStatutProposition" class="form-control">
											<option value="${enumStatutProposition}">${'Votre Choix'}</option>
											<c:forEach var="enumStatutProposition"
												items="${enumStatutPropositionList}">
												<c:if test="${enumStatutProposition.getCode() != 'INCONNU'}">
													<option value="${enumStatutProposition.getCode()}">${enumStatutProposition.toString()}</option>
												</c:if>
											</c:forEach>
										</select>
									</fieldset>

									

									<%-- <form:select path="selectEnumStatutProposition">
				    <form:option value="0" label="Choisissez la situation de la proposition" />
				    <form:options items="${enumStatutPropositionList}" />
				</form:select> --%>

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
									<center> Vos Propositions</center>
								</h3>
							</div>
							<div class="panel-body">
								<table
									class="table table-striped table-condensed table-bordered"
									style="text-align: center">
									<thead>
										<tr>
											<th style="text-align: center">Référence</th>
											<th style="text-align: center">Type</th>
											<th style="text-align: center">Rubrique</th>
											<th style="text-align: center">Titre</th>
											<th style="text-align: center">Valeur</th>
											<th style="text-align: center">Ville</th>
											<th style="text-align: center">Code Postal</th>
											<th style="text-align: center">Pseudo Adhérent</th>
											<th style="text-align: center">Situation</th>
											<th style="text-align: center">Fin de publication</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="proposition" items="${propositions}">
											<tr>
												<td>${proposition.id}</td>
												<td>${proposition.enumTradeType.toString()}</td>
												<td>${proposition.categorie.getName().toString()}</td>
												<td>${proposition.titre}</td>
												<td>${proposition.valeur}</td>
												<td>${proposition.ville}</td>
												<td>${proposition.codePostal}</td>
												<td>${proposition.emetteurUsername}</td>
												<td>${proposition.statut.getCode()}</td>
												<td>${proposition.dateFin}</td>

												<td><a type="button" class="btn btn-primary"
													href="/propositions/reponses/${proposition.id}">Détails</a>
												</td>


												<td><a type="button" class="btn btn-warning"
													href="/propositions/${proposition.id}">MaJ</a></td>

												<c:if test="${proposition.statut.getCode()=='ENCOURS'}">
												<td><a type="button" class="btn btn-danger"
													href="/propositions/close/${proposition.id}">Clôturer</a></td>
												</c:if>	


											</tr>
										</c:forEach>

									</tbody>
								</table>
								<div class="container">
									<!-- div class="row-lg-2" -->
									<c:if test="${propositions.size() > 0 }">
										<!-- ul class="pagination-sm"-->
										<ul class="nav nav-pills">
											<c:forEach begin="0" end="${totalPages-1}" var="page">
												<li class="page-item"><a class="btn btn-info"
													href="propositions/adherent?page=${page}&size=${size}"
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