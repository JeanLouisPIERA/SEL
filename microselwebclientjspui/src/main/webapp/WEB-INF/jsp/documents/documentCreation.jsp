<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>

<title>Creation d'un Document</title>
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
    <div class="panel-heading">Fiche d'enregistrement d'un document</div>
    
    <div class="panel-body">
    <br>
       <br>
     <form:form class="form-inline d-flex bd-highlight" method="POST" modelAttribute="documentDTO">
        
       <fieldset class="form-row">
       <fieldset class="form-group">
	       <form:label path="titre" class="col-auto col-form-label"> Titre du document :</form:label>
	       <form:input path="titre" type="text" class="form-control"
	        placeholder="e.g Conditions Générales d'Utilisation " required="required" />
	       <form:errors path="titre" cssClass="text-warning" />
      	</fieldset>
      	</fieldset>
      
      <br>

	   
	   <fieldset class="form-row">     
       <fieldset class="form-group">
	       <form:label path="auteurId" class="col-auto col-form-label">Identifiant de l'auteur de document :</form:label>
	       <form:input path="auteurId" type="text" class="form-control"
	        placeholder="e.g 12" required="required" />
	       <form:errors path="auteurId" cssClass="text-warning" />
      	</fieldset>
      	</fieldset>
      	
      	<br>
      	
      	<fieldset class="form-row">
       <fieldset class="form-group">
	       <form:label path="image" class="col-auto col-form-label"> Illustration :</form:label>
	       <form:input path="image" type="text" class="form-control"
	        placeholder="e.g illustration du texte " required="required" />
	       <form:errors path="image" cssClass="text-warning" />
      	</fieldset>
      	</fieldset>
      
      <br>
      	
      	
      	<fieldset class="form-row">
	       <fieldset class="form-group">
		       <form:textarea path="contenu" rows="6" cols="100"
		        placeholder="Rédiger ici le texte à publier : ne pas dépasser 10000 caractères" required="required" maxlength="10000"/>
		       <form:errors path="contenu" cssClass="text-warning" />
	      	</fieldset>
      	</fieldset>
      	
    <%--  <fieldset class="form-group">
       <form:label path="categorieName" class="col-auto col-form-label">Rubrique :</form:label>
      	<form:select path="categorieName" class="form-control">
	     	 <c:forEach var="enumCategorie" items="${enumCategorie}">
	     	 	<c:if test="${enumCategorie.getCode() != 'INCONNUE'}">
			    <option value="${enumCategorie.getCode()}">${enumCategorie.toString()}</option>
			    </c:if>
			 </c:forEach>
        </form:select>
        </fieldset>
        </fieldset> 	 --%>
      	
      
      <br>
      <fieldset class="form-row">
      <fieldset class="form-group">
       <form:label path="typeDocument" class="col-auto col-form-label">Type de document:</form:label>
      	<form:select path="typeDocument" class="form-control">
	     	 <c:forEach var="typeDocument" items="${typeDocumentsList}">
			    <option value="${typeDocument.getTypeName()}">${typeDocument.getTypeName()}</option>
			 </c:forEach>
        </form:select>
        </fieldset>
        </fieldset>
        
        <%-- <fieldset class="form-group">
	        <form:label path="typeDocument" class="col-auto col-form-label">Type de document :</form:label>
	      	<form:select path="typeDocument">
		     	 <form:option value="-" label="Merci de choisir un type de document"/>
				 <form:options items="${typeDocumentsList}" itemValue="typeName" itemLabel="typeName" />
	        </form:select>
	       </fieldset> --%>
       
      
     <%--   <fieldset class="form-group">
      		<label> Type de document : </label>
	      	<select name="typeName" class="form-control">
	      		<option value="${typeDocument}">${'Votre Choix'}</option>
		     	 <c:forEach var="typeDocument" items="${typeDocumentsList}">
				    <option value="${typeDocument.getTypeName()}">${typeDocument.getTypeName()}</option>
				 </c:forEach>
	        </select>
       	</fieldset> --%>
      	
      	 <br>
      	 
      	
      	 
      	
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