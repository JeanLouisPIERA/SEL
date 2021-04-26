<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Détail d'un Echange</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body> 

<div id= "header1">
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>


<div class="row d-flex justify-content-center">

<div class="container-fluid">

<div class="row d-flex justify-content-center">
<div class="container col-md-5 col-md-offset-1">
 <div class="panel panel-primary ">
 
  <div class="panel-heading">
   <h5>Fiche d'information de l'échange N° ${readEchange.id}</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <tbody>
         <tr>
            <td>Titre de l'échange :</td>
            <td>${readEchange.titre}</td>
        </tr>
        <tr>
            <td>Date d'enregistrement :</td>
            <td>${readEchange.dateEnregistrement}</td>
        </tr>
        <tr>
            <td>Statut :</td>
            <td>${readEchange.statutEchange}</td>
        </tr>
         <tr>
            <td>Date d'archivage:</td>
            <td>${readEchange.dateFin}</td>
        </tr>
        
       
	    </tbody>
	</table>
  
</div>
</div>
 </div>
 
 <div class="container col-md-5 col-md-offset-0">
 <div class="panel panel-primary ">
 
  <div class="panel-heading">
   <h5>Autres infos :</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <tbody>
    	 <tr>
            <td>Adhérent Proposition:</td>
            <td>${readEchange.emetteurUsername}</td>
        </tr>
        
         <tr>
            <td>Adhérent Répondant :</td>
            <td>${readEchange.recepteurUsername}</td>
        </tr>
        
        <tr>
            <td>Date de confirmation:</td>
            <td>${readEchange.dateConfirmation}</td>
        </tr>
        
        <tr>
            <td>Date d'annulation:</td>
            <td>${readEchange.dateAnnulation}</td>
        </tr>
               
	    </tbody>
	</table>
  
</div>
 </div>
 </div>
  

   
 <div class="container-fluid">
	<div class="row d-flex justify-content-center">
		<div class="container col-md-10 col-md-offset-1">
			<div class="wrapper">
				 <div class="panel panel-primary">
				  <div class="panel-heading">
				   <h3><center>Evaluations de l'échange </center></h3>
				  </div>
  					<div class="panel-body">
					   <table class="table table-striped table-condensed table-bordered" style="text-align:center">
					   		 <thead>		  	  	 
					    			 <tr>
									      <th style="text-align:center">N° de l'évaluation </th>
									      <th style="text-align:center">Identifiant Adherent</th>
									      <th style="text-align:center">Nom Adherent</th>
									      <th style="text-align:center">Date Evaluation</th>
									      <th style="text-align:center">Note </th>
									      <th style="text-align:center">Commentaire</th>
								     </tr>
								     </thead>
								     <tbody>
					   				 <c:forEach var="evaluation" items="${evaluations}">
					   				 <tr>
								          <td>${evaluation.id}</td>
								          <td>${evaluation.adherentId}</td>
								          <td>${evaluation.adherentUsername}</td>
								          <td>${evaluation.dateEvaluation}</td>
								          <td>${evaluation.enumNoteEchange.toString()}</td>
								          <td>${evaluation.commentaire}</td>
								          
					    				 </tr>
				   					</c:forEach>
				   					
								</tbody>
							</table>			
			    		</div>
		    		</div>	
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