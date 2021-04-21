<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Détail du Portefeuille d'un Adhérent</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body> 

<div id= "header1">
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>


<div class="row d-flex justify-content-center">

<div class="container-fluid">

<div class="row d-flex justify-content-center">
<div class="container col-md-4 col-md-offset-1">
 <div class="panel panel-primary ">
 
  <div class="panel-heading">
   <h5>Fiche d'information du portfeuille N° ${consultedWallet.id}</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <tbody>
         <tr>
            <td>N° du Titulaire :</td>
            <td>${consultedWallet.titulaireId}</td>
        </tr>
        <tr>
            <td>Solde</td>
            <td>${consultedWallet.soldeWallet}</td>
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
				   <h3><center>Transactions du Portefeuille </center></h3>
				  </div>
  					<div class="panel-body">
					   <table class="table table-striped table-condensed table-bordered" style="text-align:center">
					   		 <thead>		  	  	 
					    			 <tr>
									      <th style="text-align:center">N° de Transaction </th>
									      <th style="text-align:center">Date de Transaction</th>
									      <th style="text-align:center">Montant</th>
									      <th style="text-align:center">Titre Echange </th>
									      <th style="text-align:center">Adherent Proposition</th>
									      <th style="text-align:center">Adherent Reponse</th>
								     </tr>
								     </thead>
								     <tbody>
					   				 <c:forEach var="transaction" items="${transactions}">
					   				 <tr>
								          <td>${transaction.id}</td>
								          <td>${transaction.dateTransaction}</td>
								          <td>${transaction.montant}</td>
								          <td>${transaction.titreEchange}</td>
								          <td>${transaction.emetteurUsername}</td>
								          <td>${transaction.recepteurUsername}</td>
								          
								           
					    				 </tr>
				   					</c:forEach>
				   					
								</tbody>
							</table>			
			   			<div class="container">
			    		<!-- div class="row-lg-2" -->
			    			<c:if test="${transactions.size() > 0 }">
					            <!-- ul class="pagination-sm"-->
					            <ul class="nav nav-pills">
					                <c:forEach begin="0" end="${totalPages-1}" var="page">
					                    <li class="page-item">
					                        <a class="btn btn-info" href="adherent?page=${page}&size=${size}" class="page-target">${page+1}</a>
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
 </div>

 </div>
 
</div>



<div id="footer">
<%@ include file="/WEB-INF/jsp/common/footer1.jspf"%>
</div>


</body>

</html>