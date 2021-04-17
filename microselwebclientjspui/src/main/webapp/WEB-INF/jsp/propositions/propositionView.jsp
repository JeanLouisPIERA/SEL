<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Détail d'une Proposition</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body> 

<div id= "header1">
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>


<!-- <div class="row d-flex justify-content-center">
 -->
<div class="container-fluid"> 
<div class="row d-flex justify-content-center">
<div class="container col-md-4 col-md-offset-1">
 <div class="panel panel-primary ">
 
  <div class="panel-heading">
   <h5>Fiche d'information de la proposition N° ${readProposition.id} avec le N° Adhérent N° ${readProposition.emetteurId}</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <tbody>
        <tr>
            <td>Titre:</td>
            <td>${proposition.titre}</td>
        </tr>
       
        <tr>
            <td>Type :</td>
            <td>${proposition.enumTradeType.getCode()}</td>
        </tr>
        <tr>
            <td>Categorie :</td>
            <td>${proposition.categorie.getName().getCode()}</td>
        </tr>
        <tr>
            <td>Valeur :</td>
            <td>${proposition.valeur}</td>
        </tr>
        <tr>
            <td>Ville :</td>
            <td>${proposition.ville}</td>
        </tr>
        <tr>
            <td>Code Postal :</td>
            <td>${proposition.codePostal}</td>
        </tr>
        <tr>
            <td>Fin de publication :</td>
            <td>${proposition.dateFin}</td>
        </tr>
 		<tr>
            <td>Echange à réaliser avant le :</td>
            <td>${proposition.dateEcheance}</td>
        </tr>
        
	    </tbody>
	</table>
  
  	<c:if test="${proposition.statut.getCode()=='ENCOURS'}"> 
      <td>
     	<a type="button"  class="btn btn-success" 
	   	href="/reponses/newReponse/${proposition.id}">Répondre</a>
	  </td>
    </c:if>
  
</div>
 </div>
 </div>

<div class="container col-md-6 col-md-offset-0">
 <div class="panel panel-primary ">
 
  <div class="panel-heading">
   <h5>Autres infos :</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <tbody>
    	 <tr>
            <td>Illustration:</td>
            <td>${proposition.image}</td>
        </tr>
        
         <tr>
            <td>date de publication :</td>
            <td>${proposition.dateDebut}</td>
        </tr>
        
        <tr>
            <td>Description :</td>
            <td>${proposition.description}</td>
        </tr>
               
	    </tbody>
	</table>
  
</div>
 </div>
 </div>
    </div>
 <!--    </div>
    </div> -->
    
 <div class="container-fluid">
	<div class="row d-flex justify-content-center">
		<div class="container col-md-10 col-md-offset-1">
			<div class="wrapper">
				 <div class="panel panel-primary">
				  <div class="panel-heading">
				   <h3><center>Réponses à la proposition </center></h3>
				  </div>
  					<div class="panel-body">
					   <table class="table table-striped table-condensed table-bordered" style="text-align:center">
					   		 <thead>		  	  	 
					    			 <tr>
									      <th style="text-align:center">N° Réponse</th>
									      <th style="text-align:center">Pseudo Adhérent</th>
									      <th style="text-align:center">Type</th>
									      <th style="text-align:center">Rubrique</th>
									      <th style="text-align:center">Date Réponse</th>
									      <th style="text-align:center">Valeur</th>
									      <th style="text-align:center">Ville</th>
									      <th style="text-align:center">Code Postal</th>
									      <th style="text-align:center">Echéance</th>
									      <th style="text-align:center">N° Echange</th>
									      <th style="text-align:center">Statut Echange</th>
									      
								     </tr>
								     </thead>
								     <tbody>
					   				 <c:forEach var="reponse" items="${reponses}">
					   				 <tr>
								          <td>${reponse.id}</td>
								          <td>${reponse.recepteurUsername}</td>
								          <td>${reponse.enumTradeType}</td>
								          <td>${reponse.titre}</td>
								          <td>${reponse.dateReponse}</td>
								          <td>${reponse.valeur}</td>
								          <td>${reponse.ville}</td>
								          <td>${reponse.codePostal}</td>
								          <td>${reponse.dateEcheance}</td>
								          <td>${reponse.echange.getId()}</td>
								          <td>${reponse.echange.statutEchange.toString()}</td>
								         
								       
								         <c:if test="${reponse.echange.statutEchange.getCode()=='ENREGISTRE'}"> 
								          <td>
								          	<a type="button"  class="btn btn-success" 
								        	href="/echanges/confirmation/${reponse.echange.getId()}">Confirmer</a>
								          </td>
								          <td>
								          	<a type="button"  class="btn btn-warning" 
								        	href="/echanges/annulation/${reponse.echange.getId()}">Annuler</a>
								          </td>
								          </c:if>
								          
								          <c:if test="${reponse.echange.statutEchange.getCode()!='ENREGISTRE'
								          && not empty reponse.echange.statutEchange.getCode()}"> 
								          <td>
								          	<a type="button"  class="btn btn-primary" 
								        	href="/echanges/evaluations/${reponse.echange.getId()}">Consulter Echange</a>
								          </td>
								          </c:if>
								          
					    				 </tr>
				   					</c:forEach>
				   					
								</tbody>
							</table>	
							<div class="container">
			    			<!-- div class="row-lg-2" -->
			    			<c:if test="${reponses.size() > 0 }">
					            <!-- ul class="pagination-sm"-->
					            <ul class="nav nav-pills">
					                <c:forEach begin="0" end="${totalPages-1}" var="page">
					                    <li class="page-item">
					                        <a class="btn btn-info" href="${proposition.id}?page=${page}&size=${size}" class="page-target">${page+1}</a>
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