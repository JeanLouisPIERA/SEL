<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>


<head>

<title>Document Confirmation Archivage </title>
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
   <h5>${document.auteurUsername}, ce document a bien été archivé</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <thead>
        <tr>
            <th colspan="2"> Enregistrement N° : ${id}</th> sous le N° Administrateur ${document.auteurId}</th>
        </tr>
    </thead>
    <tbody>
         <tr>
            <td>Titre:</td>
            <td>${document.titre}</td>
        </tr>
        <tr>
            <td>Date de création:</td>
            <td>${document.dateCreation}</td>
        </tr>
        <tr>
            <td>Entete:</td>
            <td>${document.entete}</td>
        </tr>
        <tr>
            <td>Type :</td>
            <td>${document.typeDocument.getTypeName()}</td>
        </tr>
        <tr>
            <td>Statut :</td>
            <td>${document.statutDocument.toString()}</td>
        </tr>
        
 		
 		
    </tbody>
</table>
  
 </div>
 </div>
 <div>
 <a type="button" class="btn btn-primary btn-md" href="/documents">Retour au menu</a>
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