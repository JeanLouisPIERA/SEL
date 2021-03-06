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


<div class="container col-md-8 col-md-offset-1">

<div class="wrapper">


 </div>
 </div>


<div class="container col-md-8 col-md-offset-1">

<div class="wrapper">


 </div>
 </div>

<div class="container-fluid">
 <div class="row justify-content-center mb-3">
  <div class="col-md-10 col-md-offset-1 ">
  
   <div class="panel panel-primary">
    <div class="panel-heading">
    <h3><center>Recherche des portefeuilles enregistr�s</center></h3>
    </div>
    
	    <div class="panel-body">
	
				 <form:form class="form-inline d-flex bd-highlight" action="/wallets" method="GET" modelAttribute="wallet">
				 
				 <fieldset class="form-row">
				 
				 <fieldset class="form-group">
				 <label>N� du portefeuille :</label>
				 <input type="text" name="id" value="${walletCriteria.id}"/>
				 </fieldset>
				 
				 <fieldset class="form-group">
				 <label>N� du Titulaire :</label>
				 <input type="text" name="titulaireId" value="${walletCriteria.titulaireId}"/>
				 </fieldset>
				 
				 <fieldset class="form-group">
				 <label>Pseudo du Titulaire :</label>
				 <input type="text" name="titulaireUsername" value="${walletCriteria.titulaireUsername}"/>
				 </fieldset>
				 
				 
				 <fieldset class="form-group">
				 <label>Solde du portefeuille sup�rieur � :</label>
				 <input type="text" name="soldeWallet" value="${walletCriteria.soldeWallet}"/>
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
				   <h3><center>Portefeuilles </center></h3>
				  </div>
  					<div class="panel-body">
					   <table class="table table-striped table-condensed table-bordered" style="text-align:center">
					   		 <thead>		  	  	 
					    			 <tr>
									      <th style="text-align:center">N� du Portefeuille </th>
									      <th style="text-align:center">N� du Titulaire</th>
									      <th style="text-align:center">Nom du Titulaire</th>
									      <th style="text-align:center">Solde</th>
								     </tr>
								     </thead>
								     <tbody>
					   				 <c:forEach var="wallet" items="${wallets}">
					   				 <tr>
								          <td>${wallet.id}</td>
								          <td>${wallet.titulaireId}</td>
								          <td>${wallet.titulaireUsername}</td>
								          <td>${wallet.soldeWallet}</td>
								          
								          <td>
								          	<a type="button"  class="btn btn-primary" 
								        	href="/wallets/transactions/${wallet.id}">D�tails</a>
								          </td>
								       
								          
					    				 </tr>
				   					</c:forEach>
				   					
								</tbody>
							</table>			
			   			<div class="container">
			    		<!-- div class="row-lg-2" -->
			    			<c:if test="${wallets.size() > 0 }">
					            <!-- ul class="pagination-sm"-->
					            <ul class="nav nav-pills">
					                <c:forEach begin="0" end="${totalPages-1}" var="page">
					                    <li class="page-item">
					                        <a class="btn btn-info" href="wallets?page=${page}&size=${size}" class="page-target">${page+1}</a>
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