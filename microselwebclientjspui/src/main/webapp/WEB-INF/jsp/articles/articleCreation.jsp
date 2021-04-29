<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>

<title>Creation d'un Article</title>
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
    <div class="panel-heading">Fiche d'enregistrement d'un article</div>
    
    <div class="panel-body">
    <br>
       <br>
     <form:form class="form-inline d-flex bd-highlight" method="POST" modelAttribute="articleDTO">
        
   <%--     <fieldset class="form-row">
       <fieldset class="form-group">
	       <form:label path="auteurUsername" class="col-auto col-form-label"> Pseudo :</form:label>
	       <form:input path="auteurUsername" type="text" class="form-control"
	        placeholder="saisissez votre pseudo ici " required="required" />
	       <form:errors path="auteurUsername" cssClass="text-warning" />
      	</fieldset>
      	</fieldset>
      
      <br> --%>
       
       
       
       <fieldset class="form-row">
       <fieldset class="form-group">
	       <form:label path="titre" class="col-auto col-form-label"> Titre du document :</form:label>
	       <form:input path="titre" type="text" class="form-control"
	        placeholder="e.g titre de mon article " required="required" />
	       <form:errors path="titre" cssClass="text-warning" />
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
		       <form:textarea path="entete" rows="3" cols="100"
		        placeholder="Rédiger ici un résumé du texte à publier : ne pas dépasser 255 caractères" required="required" maxlength="255"/>
		       <form:errors path="entete" cssClass="text-warning" />
	      	</fieldset>
      	</fieldset>
      	
      	
      	
      	<fieldset class="form-row">
	       <fieldset class="form-group">
		       <form:textarea path="contenu" rows="6" cols="100"
		        placeholder="Rédiger ici le texte à publier : ne pas dépasser 1000 caractères" required="required" maxlength="10000"/>
		       <form:errors path="contenu" cssClass="text-warning" />
	      	</fieldset>
      	</fieldset>
      	
   
      	
      
      <br>
      <fieldset class="form-row">
      <fieldset class="form-group">
       <form:label path="typeArticle" class="col-auto col-form-label">Type d'Article:</form:label>
      	<form:select path="typeArticle" class="form-control">
	     	 <c:forEach var="typeArticle" items="${typeArticlesList}">
			    <option value="${typeArticle.getTypeName()}">${typeArticle.getTypeName()}</option>
			 </c:forEach>
        </form:select>
        </fieldset>
        </fieldset>
        
      	
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