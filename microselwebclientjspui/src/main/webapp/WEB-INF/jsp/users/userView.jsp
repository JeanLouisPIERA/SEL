<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Détail d'un Compte Adhérent</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body>

	<div id="header1">
		<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
		<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>

		<div class="container-fluid">
			<div class="row justify-content-center mb-3">
				<div class="col-md-10 col-md-offset-1 ">
					<div
						class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h3>
									<center>Espace Personnel</center>
								</h3>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


		<div class="container-fluid">
			<div class="row d-flex justify-content-center">
				<div class="container col-md-4 col-md-offset-1">
					<div class="panel panel-primary ">

						<div class="panel-heading">
							<h5>Votre Espace Personnel - Informations de votre compte</h5>
						</div>

						<div class="panel-body">
							<table class="table table-striped table-condensed table-bordered">
								<tbody>
									<tr>
										<td>Identifiant :</td>
										<td>${adherent.id}</td>
									</tr>

									<tr>
										<td>Prénom :</td>
										<td>${adherent.firstName}</td>
									</tr>
									<tr>
										<td>Nom :</td>
										<td>${adherent.lastName}</td>
									</tr>
									<tr>
										<td>Pseudo :</td>
										<td>${adherent.username}</td>
									</tr>
									<tr>
										<td>Email :</td>
										<td>${adherent.email}</td>
									</tr>

								</tbody>
							</table>

						</div>
					</div>
				</div>

				<div class="container col-md-6 col-md-offset-0">
					<div class="panel panel-primary ">

						<div class="panel-heading">
							<h5>Vos habilitations :</h5>
						</div>

						<div class="panel-body">
							<table class="table table-striped table-condensed table-bordered"
								style="text-align: center">
								<thead>
									<tr>
										<th style="text-align: center">Liste de vos habiliations</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="role" items="${roles}">
										<tr>
											<%-- <c:if test="${role.name == 'USER' || role.name == 'BUREAU' || role.name == 'ADMIN'}">
											<td>${role.name}</td>
											</c:if> --%>
											<c:if test="${role.name == 'USER'}">
												<td>${'Adhérent'}</td>
											</c:if>
											<c:if test="${role.name == 'BUREAU'}">
												<td>${'Membre du Bureau'}</td>
											</c:if>
											<c:if test="${role.name == 'ADMIN'}">
												<td>${'Administrateur'}</td>
											</c:if>
										</tr>
									</c:forEach>
								</tbody>
							</table>

						</div>
					</div>
				</div>
			</div>

			<div class="col-md-10 col-md-offset-1 ">
				<div class="panel panel-primary">
					<div class="panel-heading">Menu</div>
					<div class="panel-body">
						<a type="button" class="btn btn-primary" href="/propositions/adherent">Consulter</a> Toutes vos propositions <br></br> 
						<a type="button" class="btn btn-primary" href="/echanges/emetteur">Consulter</a> Tous les échanges où vous avez fait une proposition <br></br> 
						<a type="button" class="btn btn-primary"href="/echanges/recepteur">Consulter</a> Tous vos échanges où vous avez répondu à une proposition<br></br> 
						<a type="button" class="btn btn-primary"href="/wallets/adherent">Consulter</a> Le solde et toutes les transaction de votre portefeuille

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