<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html>


<head>

<title>Creation Reponse</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>


<body> 

<div id= "header1">
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>

<div class="container-fluid">
 <div class="row justify-content-center mb-3">
  <div class="col-md-6 col-md-offset-3 ">
  
   <div class="panel panel-primary">
    <div class="panel-heading">Fiche d'enregistrement d'une r�ponse</div>
    
    <div class="panel-body">
   
     
     <form:form class="form-inline d-flex bd-highlight" method="POST" modelAttribute="reponseDTO">
     <input type="hidden" name="id" value="${proposition.id}" />
        
      
      <br>
	   <fieldset class="form-row">     
       <fieldset class="form-group">
	       <form:label path="titre" class="col-auto col-form-label">Titre :</form:label>
	       <form:input path="titre" type="text" class="form-control" value ="${proposition.titre}"
	        placeholder="" required="required" />
	       <form:errors path="titre" cssClass="text-warning" />
      	</fieldset>
      	
      	<br><br>
      	
      	 <fieldset class="form-group">
	       <form:label path="description" class="col-auto col-form-label">Description :</form:label>
	       <form:input path="description" type="text" class="form-control" value ="${proposition.description}"
	        placeholder="e.g travaux electriques" required="required" />
	       <form:errors path="description" cssClass="text-warning" />
      	</fieldset>
      	
      	
      	<br><br>
      	
      	<fieldset class="form-row">       	
      	<fieldset class="form-group">
	       <form:label path="ville" class="col-auto col-form-label">Ville :</form:label>
	       <form:input path="ville" type="text" class="form-control" value ="${proposition.ville}"
	        placeholder="e.g Paris " required="required" />
	       <form:errors path="ville" cssClass="text-warning" />
      	</fieldset>
      	
      	<fieldset class="form-group">
	       <form:label path="codePostal" class="col-auto col-form-label">Code Postal :</form:label>
	       <form:input path="codePostal" type="text" class="form-control" value ="${proposition.codePostal}"
	        placeholder="e.g 75000 " required="required" />
	       <form:errors path="codePostal" cssClass="text-warning" />
      	</fieldset>
      	</fieldset>
      	
      	<br>
      	
      	<fieldset class="form-row">  
      	<fieldset class="form-group">
	       <form:label path="valeur" class="col-auto col-form-label">Valeur :</form:label>
	       <form:input path="valeur" type="text" class="form-control" value ="${proposition.valeur}"
	        placeholder="e.g 75 " required="required" />
	       <form:errors path="valeur" cssClass="text-warning" />
      	</fieldset>
      	</fieldset>
      	
      	<br>
      	
      	<fieldset class="form-group">
	       <form:label path="dateEcheance" class="col-auto col-form-label">A r�aliser avant le :</form:label>
	       <form:input path="dateEcheance" type="date" min="2021-01-01" max="2100-01-01" value ="${proposition.dateEcheance}"
	       />
	       <form:errors path="dateEcheance" cssClass="text-warning" />
      	</fieldset>
      	
      	
      	
       <br>
       <br>	      	
   	   <button type="submit" class="btn btn-success">Valider</button>
   	   
     </form:form>
    </div>
   </div>
  </div>
 </div>
</div>

<div id="footer">
<%@ include file="/WEB-INF/jsp/common/footer1.jspf"%>
</div>
</div>


</body>

</html>