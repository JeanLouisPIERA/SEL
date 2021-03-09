<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>


<head>

<title>Reponse Confirmation</title>
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
   <h5>Votre réponse a été enregistrée avec le N° ${reponse.id} sous le N° Adhérent : ${reponse.recepteurId}</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <thead>
        <tr>
            <th colspan="2"> Titre : ${reponse.titre}</th>
        </tr>
    </thead>
    <tbody>
         <tr>
            <td>Identifiant de la proposition : </td>
            <td>${reponse.proposition.id}</td>
        </tr>
       
        <tr>
            <td>Description de votre réponse :</td>
            <td>${reponse.description}</td>
        </tr>
        <tr>
            <td>Image de votre réponse :</td>
            <td>${reponse.image}</td>
        </tr>
        <tr>
            <td>Type</td>
            <td>${reponse.enumTradeType.getCode()}</td>
        </tr>
        <tr>
            <td>Categorie</td>
            <td>${reponse.proposition.getCategorie().getName().getCode()}</td>
        </tr>
         <tr>
            <td>Ville: </td>
            <td>${reponse.ville}</td>
        </tr>
         <tr>
            <td>Code Postal : </td>
            <td>${reponse.codePostal}</td>
        </tr>
         <tr>
            <td>Valeur : </td>
            <td>${reponse.valeur}</td>
        </tr>
         <tr>
            <td>Date d'échéance : </td>
            <td>${reponse.dateEcheance}</td>
        </tr>
 		
    </tbody>
</table>
  
 </div>
 </div>
 <div>
 <a type="button" class="btn btn-primary btn-md" href="/propositions">Retour au menu </a>
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