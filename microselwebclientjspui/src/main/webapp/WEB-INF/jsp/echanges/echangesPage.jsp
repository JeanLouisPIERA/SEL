<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Echanges</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body> 

<div id= "header1">
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>


<div class="container col-md-8 col-md-offset-1">
<div class="wrapper">

 </div>
 </div>

<div class="container-fluid">
 <div class="row justify-content-center mb-3">
  <div class="col-md-10 col-md-offset-1 ">
  
   <div class="panel panel-primary">
    <div class="panel-heading">
    <h3><center>Recherche des échanges enregistrés</center></h3>
    </div>
    
	    <div class="panel-body">
	
				 <form:form class="form-inline d-flex bd-highlight" action="/echanges" method="GET" modelAttribute="echange">
				 
				 <fieldset class="form-row">
				 
				 <fieldset class="form-group">
				 <label>N° de l'échange :</label>
				 <input type="text" name="id" value="${echangeCriteria.id}"/>
				 </fieldset>
				 
				 <fieldset class="form-group">
				 <label>Titre :</label>
				 <input type="text" name="titre" value="${echangeCriteria.titre}"/>
				 </fieldset>
				  
				 <fieldset class="form-group">
				 <label>Adherent proposant :</label>
				 <input type="text" name="emetteurUsername" value="${echangeCriteria.emetteurUsername}"/>
				 </fieldset>
				 
				 <fieldset class="form-group">
				 <label>Adherent repondant:</label>
				 <input type="text" name="recepteurUsername" value="${echangeCriteria.recepteurUsername}"/>
				 </fieldset>
				 
				 
				 <fieldset class="form-group">
		      		<label> Statut : </label>
			      	<select name="statutEchange" class="form-control">
			      		<option value="${statutEchange}">${'Votre Choix'}</option>
				     	 <c:forEach var="enumStatutEchange" items="${enumStatutEchangeList}">
				     	 	<c:if test="${enumStatutEchange.getCode() != 'INCONNUE'}">
						    <option value="${enumStatutEchange.getCode()}">${enumStatutEchange.toString()}</option>
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
				   <h3><center>Echanges </center></h3>
				  </div>
  					<div class="panel-body">
					   <table class="table table-striped table-condensed table-bordered" style="text-align:center">
					   		 <thead>		  	  	 
					    			 <tr>
									      <th style="text-align:center">N° Echange</th>
									      <th style="text-align:center">Titre</th>
									      <th style="text-align:center">Enregistré le</th>
									      <th style="text-align:center">Adherent Proposant</th>
									      <th style="text-align:center">Adherent Repondant</th>
									      <th style="text-align:center">Echeance</th>
									      <th style="text-align:center">Statut</th>
								     </tr>
								     </thead>
								     <tbody>
					   				 <c:forEach var="echange" items="${echanges}">
					   				 <tr>
								          <td>${echange.id}</td>
								          <td>${echange.titre}</td>
								          <td>${echange.dateEnregistrement}</td>
								          <td>${echange.emetteurUsername}</td>
								          <td>${echange.recepteurUsername}</td>
								          <td>${echange.dateEcheance}</td>					          
								          <td>${echange.statutEchange.getCode()}</td>
								          
								          <td>
								          	<a type="button"  class="btn btn-primary "
								        	href="/echanges/evaluations/${echange.id}">Détails</a>
								          </td>	
								          
								          <%--  <c:if test="${echange.statutEchange.getCode() == 'CLOTURE' 
								          || echange.statutEchange.getCode() == 'LITIGE'
								          || echange.statutEchange.getCode() == 'CONFLIT' }">
								          <td>
								          	<a type="button"  class="btn btn-info "
								        	href="/echanges/evaluations/${echange.id}">Voir Evaluations</a>
								          </td>	
								          </c:if> --%>
								          
								         
								         <sec:authorize access="hasAuthority('USER')">
								         <c:if test="${echange.statutEchange.getCode() == 'ENREGISTRE'}">
								          <td>
								          	<a type="button"  class="btn btn-success" 
								        	href="/echanges/confirmation/${echange.id}">Confirmer</a>
								          </td>
								          <td>
								          	<a type="button"  class="btn btn-warning" 
								        	href="/echanges/annulation/${echange.id}">Annuler</a>
								          </td>
								          </c:if>
								         
								          
								          <c:if test="${echange.statutEchange.getCode() == 'CONFIRME'}">
								          <td>
								          	<a type="button"  class="btn btn-success" 
								        	href="/echanges/emetteurValidation/${echange.id}">Avis + (E)</a>
								          </td>
								          <td>
								          	<a type="button"  class="btn btn-danger" 
								        	href="/echanges/emetteurRefus/${echange.id}">Avis - (E)</a>
								          </td>
								          <td>
								          	<a type="button"  class="btn btn-success" 
								        	href="/echanges/recepteurValidation/${echange.id}">Avis + (R)</a>
								          </td>
								          <td>
								          	<a type="button"  class="btn btn-danger" 
								        	href="/echanges/recepteurRefus/${echange.id}">Avis - (R)</a>
								          </td>
								          </c:if>
								          </sec:authorize>
								          
								          
					    				 </tr>
				   					</c:forEach>
				   					
								</tbody>
							</table>			
			   			<div class="container">
			    		<!-- div class="row-lg-2" -->
			    			<c:if test="${echanges.size() > 0 }">
					            <!-- ul class="pagination-sm"-->
					            <ul class="nav nav-pills">
					                <c:forEach begin="0" end="${totalPages-1}" var="page">
					                    <li class="page-item">
					                        <a class="btn btn-info" href="echanges?page=${page}&size=${size}" class="page-target">${page+1}</a>
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