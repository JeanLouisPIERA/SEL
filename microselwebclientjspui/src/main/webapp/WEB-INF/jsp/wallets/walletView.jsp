<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Portefeuilles</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body> 

<div id= "header1">
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>



<div class="container-fluid">

<div class="row d-flex justify-content-center">
<div class="container col-md-4 col-md-offset-0">
 <div class="panel panel-primary ">
 
  <div class="panel-heading">
   <h5>Fiche d'information du portfeuille N° ${readWallet.id}</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <tbody>
         <tr>
            <td>N° du Titulaire :</td>
            <td>${readWallet.titulaireId}</td>
        </tr>
        <tr>
            <td>Solde</td>
            <td>${readWallet.soldeWallet}</td>
        </tr>
       
	    </tbody>
	</table>
  
</div>
 </div>
 </div>

   
 <div class="container col-md-5 col-md-offset-0" >
    
 <div class="panel panel-primary ">
 
  <div class="panel-heading">
   <h5>Transactions du Portefeuille</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <thead>		  	  	 
    			 <tr>
				      
				      <th>N° de Transaction</th>
				      <th>Date de la Transaction</th>
				      <th>Montant</th>
			     </tr>
			     </thead>
			     <tbody>
   				 <c:forEach var="transaction" items="${transactions}">
   				 <tr>
			          <td class="col-md-1">${transaction.id}</td>
			          <td class="col-md-2">${transaction.dateTransaction}</td>
			          <td class="col-md-1">${transaction.montant}</td>
			        
			         
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

<div id="footer">
<%@ include file="/WEB-INF/jsp/common/footer1.jspf"%>
</div>


</body>

</html>