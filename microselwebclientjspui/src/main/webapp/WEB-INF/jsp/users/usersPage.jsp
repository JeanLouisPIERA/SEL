<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Adherents</title>
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
								<center>Recherche des adhérents enregistrés</center>
							</h3>
						</div>

						<div class="panel-body">

							<form:form class="form-inline d-flex bd-highlight"
								action="/accounts" method="GET" modelAttribute="user">

								<fieldset class="form-row">


									<fieldset class="form-group">
										<label>Email :</label> <input type="text" name="email"
											value="${userCriteria.email}" />
									</fieldset>

									<fieldset class="form-group">
										<label>Prénom :</label> <input type="text" name="firsName"
											value="${userCriteria.firstName}" />
									</fieldset>


									<fieldset class="form-group">
										<label>Nom :</label> <input type="text" name="lastName"
											value="${userCriteria.lastName}" />
									</fieldset>

									<fieldset class="form-group">
										<label>Pseudo :</label> <input type="text" name="username"
											value="${userCriteria.username}" />
									</fieldset>

									<button class="btn-sm btn-primary">Valider</button>
								</fieldset>

								<h4>Recherche par niveau d'autorisation</h4>
								<fieldset>
									<fieldset class="form-group">
										<label>Role : </label> <select name="role"
											class="form-control">
											<option value="${role}">${'Votre choix'}</option>

											<c:forEach var="role" items="${roles}">
												<c:if
													test="${role.name == 'USER' || role.name == 'BUREAU' || role.name == 'ADMIN'}">
													<option value="${role.name}">${role.name}</option>
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
									<center>Liste des adhérents</center>
								</h3>
							</div>
							<div class="panel-body">

								<table
									class="table table-striped table-condensed table-bordered"
									style="text-align: center">

									<thead>


										<tr>
											<th style="text-align: center">Référence</th>
											<th style="text-align: center">Pseudo</th>
											<th style="text-align: center">Email</th>
											<th style="text-align: center">Prénom</th>
											<th style="text-align: center">Nom</th>
										</tr>
									</thead>

									<tbody>

										<c:forEach var="user" items="${users}">

											<tr>

												<td>${user.id}</td>
												<td>${user.username}</td>
												<td>${user.email}</td>
												<td>${user.firstName}</td>
												<td>${user.lastName}</td>

												<td><a type="button" class="btn btn-primary"
													href="/accounts/${user.id}">Détails</a></td>

											</tr>

										</c:forEach>

									</tbody>

								</table>



								<div class="container">
									<!-- div class="row-lg-2" -->
									<c:if test="${users.size() > 0 }">
										<!-- ul class="pagination-sm"-->
										<ul class="nav nav-pills">
											<c:forEach begin="0" end="${totalPages-1}" var="page">
												<li class="page-item"><a class="btn btn-info"
													href="accounts?page=${page}&size=${size}"
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