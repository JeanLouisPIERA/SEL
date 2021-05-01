<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Evaluations</title>
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
								<center>Recherche des évaluations enregistrées</center>
							</h3>
						</div>

						<div class="panel-body">

							<form:form class="form-inline d-flex bd-highlight"
								action="/evaluations" method="GET" modelAttribute="evaluation">

								<fieldset class="form-row">

									<fieldset class="form-group">
										<label>N° de l'évaluation :</label> <input type="text"
											name="id" value="${evaluationCriteria.id}" />
									</fieldset>

									<fieldset class="form-group">
										<label>Nom de l'adhérent :</label> <input type="text"
											name="adherentUsername"
											value="${evaluationCriteria.adherentUsername}" />
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
									<center>Evaluations</center>
								</h3>
							</div>
							<div class="panel-body">
								<table
									class="table table-striped table-condensed table-bordered"
									style="text-align: center">
									<thead>
										<tr>

											<th style="text-align: center">N° Evaluation</th>
											<th style="text-align: center">Nom Adhérent</th>
											<th style="text-align: center">Commentaire</th>
											<th style="text-align: center">Note de l'échange</th>
											<th style="text-align: center">Date de l'évaluation</th>
											<th style="text-align: center">Déjà modéré</th>
											<th style="text-align: center">N° de l'échange</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="evaluation" items="${evaluations}">
											<tr>

												<td>${evaluation.id}</td>
												<td>${evaluation.adherentUsername}</td>
												<td>${evaluation.commentaire}</td>
												<td>${evaluation.enumNoteEchange.toString()}</td>
												<td>${evaluation.dateEvaluation}</td>
												<td>${evaluation.isModerated}</td>
												<td>${evaluation.echange.getId()}</td>



												<td><a type="button" class="btn btn-warning"
													href="/evaluations/moderation/${evaluation.id}">Moderer</a>
												</td>


											</tr>
										</c:forEach>

									</tbody>
								</table>
								
								<div class="container">
			    		<!-- div class="row-lg-2" -->
			    			<c:if test="${evaluations.size() > 0 }">
					            <!-- ul class="pagination-sm"-->
					            <ul class="nav nav-pills">
					                <c:forEach begin="0" end="${totalPages-1}" var="page">
					                    <li class="page-item">
					                        <a class="btn btn-info" href="evaluations?page=${page}&size=${size}" class="page-target">${page+1}</a>
					                    </li>
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