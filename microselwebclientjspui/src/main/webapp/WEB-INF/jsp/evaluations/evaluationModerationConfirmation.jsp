<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>


<head>

<title>Evaluation Confirmation Modération</title>
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
   <h5>Cette évaluation a bien été modérée</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <thead>
        <tr>
            <th colspan="2"> Enregistrement N° : ${evaluation.id.toString()}</th>
        </tr>
    </thead>
    <tbody>
         <tr>
            <td>Nom de l'adhérent:</td>
            <td>${evaluation.adherentUsername}</td>
        </tr>
        <tr>
            <td>Référence de l'échange évalué:</td>
            <td>${evaluation.echange.getId()}</td>
        </tr>
        <tr>
            <td>Commentaire modéré :</td>
            <td>${evaluation.commentaire}</td>
        </tr>
      
 		
    </tbody>
</table>
  
 </div>
 </div>
 <div>
 <a type="button" class="btn btn-primary btn-md" href="/evaluations">Retour au menu</a>
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