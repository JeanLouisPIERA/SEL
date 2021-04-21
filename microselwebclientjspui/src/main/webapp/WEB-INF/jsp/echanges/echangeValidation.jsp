<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>


<head>

<title>Echange Confirmation</title>
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
   <h5>Votre décision de valider cet échange a bien été enregistrée</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <thead>
        <tr>
            <th colspan="2"> Titre : ${echange.titre}</th>
        </tr>
    </thead>
    <tbody>
         <tr>
            <td>N° :</td>
            <td>${echange.id}</td>
        </tr>
        <tr>
            <td>Statut:</td>
            <td>${echange.statutEchange.getCode()}</td>
        </tr>
       
    </tbody>
</table>
  
 </div>
 </div>
	   <td>
         	<a type="button"  class="btn btn-success" 
        	href="/evaluations/echange/${echange.id}">Evaluer l'échange</a>
        </td>
 
 </div>
 
</div>
</div>

<div id="footer">
<%@ include file="/WEB-INF/jsp/common/footer1.jspf"%>
</div>

</div>

</body>


</html>