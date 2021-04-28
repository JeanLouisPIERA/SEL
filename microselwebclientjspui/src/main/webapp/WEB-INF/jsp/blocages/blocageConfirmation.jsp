<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>


<head>

<title>Confirmation Annulation Blocage</title>
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
   <h5>Votre décision d'annuler le blocage du compte de cet adhérent a bien été enregistrée</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <thead>
        <tr>
            <th colspan="2"> Annulation du blocage du compte de l'adhérent : ${blocage.adherentUsername}</th>
        </tr>
    </thead>
    <tbody>
         <tr>
            <td>N° :</td>
            <td>${blocage.id}</td>
        </tr>
         <tr>
            <td>Identifiant de l'adhérent:</td>
            <td>${blocage.adherentId}</td>
        </tr>
        <tr>
            <td>Date de début :</td>
            <td>${blocage.dateDebutBlocage}</td>
        </tr>
        <tr>
            <td>Date de fin :</td>
            <td>${blocage.dateFinBlocage}</td>
        </tr>
         <tr>
            <td>Statut :</td>
            <td>${blocage.statutBlocage.toString()}</td>
        </tr>
       
    </tbody>
</table>
  
 </div>
 </div>
 <div>
 <a type="button" class="btn btn-primary btn-md" href="/blocages">Retour au menu</a>
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