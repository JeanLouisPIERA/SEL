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
   <h5>Votre �valuation a �t� enregistr�e</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <thead>
        <tr>
            <th colspan="2"> Titre de l'�change: ${evaluation.echange.titre}</th>
        </tr>
    </thead>
    <tbody>
         <tr>
            <td>Identifiant de l'�change : </td>
            <td>${evaluation.echange.id}</td>
        </tr>
        <tr>
            <td>Identifiant de votre evaluation : </td>
            <td>${evaluation.id}</td>
        </tr>
        <tr>
            <td>Date de votre �valuation : </td>
            <td>${evaluation.dateEvaluation}</td>
        </tr>
        <tr>
            <td>Note donn�e dans votre �valuation :</td>
            <td>${evaluation.enumNoteEchange.toString()}</td>
        </tr>
        <tr>
            <td>Commentaire de votre �valuation :</td>
            <td>${evaluation.commentaire}</td>
        </tr>
        
 		
    </tbody>
</table>
  
 </div>
 </div>
 <div>
 <a type="button" class="btn btn-primary btn-md" href="/echanges">Retour au menu </a>
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