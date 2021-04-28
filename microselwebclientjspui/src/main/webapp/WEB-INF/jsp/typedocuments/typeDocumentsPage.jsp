<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Types Document</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body> 

<div id= "header1">
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>


<div class="container col-md-8 col-md-offset-1">

<div class="wrapper">

<div>
 <a type="button" class="btn btn-primary btn-md" href="/typedocuments/newTypeDocument">Créer un nouveau type de document</a>
 <br>
 <br>
 </div>
 </div>
 </div>


<div class="container col-md-8 col-md-offset-1">

<div class="wrapper">


 </div>
 </div>
 
 
<div class="container-fluid">
	<div class="row d-flex justify-content-center">
		<div class="container col-md-10 col-md-offset-1">
			<div class="wrapper">
				 <div class="panel panel-primary">
				  <div class="panel-heading">
				   <h3><center>Types de Document </center></h3>
				  </div>
  					<div class="panel-body">
					   <table class="table table-striped table-condensed table-bordered" style="text-align:center">
					   		 <thead>		  	  	 
					    			 <tr>
					    			      <th style="text-align:center">N°</th>
					    			      <th style="text-align:center">Intitulé</th>
					    			      <th style="text-align:center">Description</th>
									      <th style="text-align:center">Date Création</th>
					    			      <th style="text-align:center">DateArchivage</th>
								     </tr>
								     </thead>
								     <tbody>
					   				 <c:forEach var="typeDocument" items="${typeDocuments}">
					   				 <tr>
								          <td>${typeDocument.id}</td>
								          <td>${typeDocument.typeName}</td>
								          <td>${typeDocument.description}</td>
								          <td>${typeDocument.dateCreation}</td>
								          <td>${typeDocument.dateArchivage}</td>
								        
								         <%--  <td>
								          	<a type="button"  class="btn btn-primary" 
								        	href="/propositions/reponses/${proposition.id}">Archiver</a>
								          </td> --%>
								          								    
								          
					    				 </tr>
				   					</c:forEach>
				   					
								</tbody>
							</table>			
			   			<div class="container">
			    		<!-- div class="row-lg-2" -->
			    			<c:if test="${typeDocuments.size() > 0 }">
					            <!-- ul class="pagination-sm"-->
					            <ul class="nav nav-pills">
					                <c:forEach begin="0" end="${totalPages-1}" var="page">
					                    <li class="page-item">
					                        <a class="btn btn-info" href="typeDocuments?page=${page}&size=${size}" class="page-target">${page+1}</a>
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