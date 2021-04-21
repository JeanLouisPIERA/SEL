<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Documents</title>
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
						href="/referentiels/newDocument">Créer un nouveau document</a> <br>
					<br>
				</div>
			</div>
		</div>


		<div class="container col-md-8 col-md-offset-1">

			<div class="wrapper"></div>
		</div>




		<div class="container-fluid">
			<div class="row d-flex justify-content-center">
				<div class="container col-md-10 col-md-offset-1">
					<div class="wrapper">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h3>
									<center>Documents</center>
								</h3>
							</div>
							<div class="panel-body">
								<table
									class="table table-striped table-condensed table-bordered"
									style="text-align: center">
									<thead>
										<tr>
											<th style="text-align: center">Titre</th>
											<th style="text-align: center">N° Adhérent de l'auteur</th>
											<th style="text-align: center">Nom de l'auteur</th>
											<th style="text-align: center">Date de Création</th>
											<th style="text-align: center">Dernière Mise à jour</th>
											<th style="text-align: center">Illustration</th>
											<th style="text-align: center">Type de Document</th>
											<th style="text-align: center">Statut</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="document" items="${documents}">
											<tr>
												<td>${document.titre}</td>
												<td>${document.auteurId}</td>
												<td>${document.auteurUsername}</td>
												<td>${document.dateCreation}</td>
												<td>${document.dateLastUpdate}</td>
												<td>${document.image}</td>
												<td>${document.typeDocument.getTypeName()}</td>
												<td>${document.statutDocument.getCode()}</td>

												<td><a type="button" class="btn btn-primary"
													href="/documents/${document.id}">Contenu</a></td>
												<%-- <td>
								          	<a type="button"  class="btn btn-primary" 
								        	href="/propositions/reponses/${proposition.id}">Archiver</a>
								          </td> --%>

											</tr>
										</c:forEach>

									</tbody>
								</table>
								<div class="container">
									<!-- div class="row-lg-2" -->
									<c:if test="${documents.size() > 0 }">
										<!-- ul class="pagination-sm"-->
										<ul class="nav nav-pills">
											<c:forEach begin="0" end="${totalPages-1}" var="page">
												<li class="page-item"><a class="btn btn-info"
													href="documents?page=${page}&size=${size}"
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