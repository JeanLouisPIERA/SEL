<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>


<head>

<title>Proposition Confirmation</title>
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
   <h5>L'enregistrement de votre proposition a ete valide</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <thead>
        <tr>
            <th colspan="2"> Titre : ${proposition.titre}</th>
        </tr>
    </thead>
    <tbody>
         <tr>
            <td>Identifiant :</td>
            <td>${proposition.id}</td>
        </tr>
        <tr>
            <td>Description :</td>
            <td>${proposition.description}</td>
        </tr>
        <tr>
            <td>Type :</td>
            <td>${proposition.enumTradeType.getCode()}</td>
        </tr>
        <tr>
            <td>Categorie :</td>
            <td>${proposition.categorie.getName().getCode()}</td>
        </tr>
        <tr>
            <td>Valeur :</td>
            <td>${proposition.valeur}</td>
        </tr>
        <tr>
            <td>Fin de publication :</td>
            <td>${proposition.dateFin}</td>
        </tr>
 		
    </tbody>
</table>
  
 </div>
 </div>
 <div>
 <a type="button" class="btn btn-primary btn-md" href="/propositions">Retour au menu</a>
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