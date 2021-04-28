<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>


<head>

<title>Document Confirmation Modération</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body> 

<div id= "header1">

<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>

<div class="container-fluid">

<div class="row d-flex justify-content-center">

 <div class="container col-md-4 col-md-offset-4">
 <div class="panel panel-primary ">
 
  <div class="panel-heading">
   <h5>Ce document a bien été modéré</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <thead>
        <tr>
            <th colspan="2"> Enregistrement N° : ${id}</th>
        </tr>
    </thead>
    <tbody>
         <tr>
            <td>Titre:</td>
            <td>${article.titre}</td>
        </tr>
        <tr>
            <td>Date de création:</td>
            <td>${article.dateCreation}</td>
        </tr>
        <tr>
            <td>Entete :</td>
            <td>${article.entete}</td>
        </tr>
        <tr>
            <td>Type :</td>
            <td>${article.typeArticle.getTypeName()}</td>
        </tr>
        <tr>
            <td>Statut :</td>
            <td>${article.statutDocument.toString()}</td>
        </tr>
        
 		
 		
    </tbody>
</table>
  
 </div>
 </div>
 <div>
 <a type="button" class="btn btn-primary btn-md" href="/articles">Retour au menu</a>
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