<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html>


<head>

<title>Creation Evaluation</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>


<body>

	<div id="header1">
		<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
		<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>

		<div class="container-fluid">
			<div class="row justify-content-center mb-3">
				<div class="col-md-6 col-md-offset-3 ">

					<div class="panel panel-primary">
						<div class="panel-heading">Fiche d'enregistrement d'une
							évaluation</div>


						<div class="panel-body">

							<form:form class="form-inline d-flex bd-highlight" method="POST"
								modelAttribute="evaluationDTO">
								<input type="hidden" name="echangeId" value="${echange.id}" />

								<br>

								<fieldset class="form-row">
									<fieldset class="form-group">
										<form:textarea path="commentaire" rows="2" cols="108"
											placeholder="Merci de votre commentaire" required="required"
											maxlength="255" />
										<form:errors path="commentaire" cssClass="text-warning" />
									</fieldset>
								</fieldset>


								<br>


								<fieldset class="form-row">
									<fieldset class="form-group">
										<form:label path="enumNoteEchange"
											class="col-auto col-form-label">Note de l'échange :</form:label>
										<form:select path="enumNoteEchange" class="form-control">
											<c:forEach var="enumNoteEchange" items="${enumNoteEchange}">
												<c:if test="${enumNoteEchange.getCode() != 'INCONNUE'}">
													<option value="${enumNoteEchange.getCode()}">${enumNoteEchange.toString()}</option>
												</c:if>
											</c:forEach>
										</form:select>
									</fieldset>
								</fieldset>


								<br>


								<button type="submit" class="btn-sm btn-success">Validation</button>
							</form:form>
						</div>
					</div>

				</div>


			</div>
		</div>

		<!-- </div> -->

		<div id="footer">
			<%@ include file="/WEB-INF/jsp/common/footer1.jspf"%>
		</div>
	</div>


</body>

</html>