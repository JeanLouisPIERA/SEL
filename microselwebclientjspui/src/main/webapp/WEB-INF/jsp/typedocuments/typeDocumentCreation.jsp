<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html>


<head>

<title>Creation Type de Document</title>
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
    <div class="panel-heading">Fiche d'enregistrement d'un type de document</div>
    
    <div class="panel-body">
    <br>
       <br>
     <form:form class="form-inline d-flex bd-highlight" method="POST" modelAttribute="typeDocumentDTO">
        
       <fieldset class="form-row">
       <fieldset class="form-group">
	       <form:label path="typeName" class="col-auto col-form-label"> Nom du type de document :</form:label>
	       <form:input path="typeName" type="text" class="form-control"
	        placeholder="e.g CGU " required="required" />
	       <form:errors path="typeName" cssClass="text-warning" />
      	</fieldset>
      	</fieldset>
      
      <br>

	
      	
      	<fieldset class="form-row">
	       <fieldset class="form-group">
		       <form:textarea path="description" rows="6" cols="100"
		        placeholder="DESCRIPTION :Décrire ici le type de document à créer : ne pas dépasser 10000 caractères" required="required" maxlength="10000"/>
		       <form:errors path="description" cssClass="text-warning" />
	      	</fieldset>
      	</fieldset> 
      	
      	<br>
      	
      	<fieldset class="form-row">
	       <fieldset class="form-group">
		       <form:textarea path="entete" rows="6" cols="100"
		        placeholder="ENTETE : Décrire ici en résumé le type de document à créer : ne pas dépasser 255 caractères" required="required" maxlength="255"/>
		       <form:errors path="entete" cssClass="text-warning" />
	      	</fieldset>
      	</fieldset> 
      	
      	<br>
      	
      	
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