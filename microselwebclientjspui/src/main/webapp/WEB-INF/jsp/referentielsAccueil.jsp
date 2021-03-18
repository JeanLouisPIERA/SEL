<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>

<head>

<title>Accueil Référentiels</title>
<%@ include file="common/header1.jspf"%>
</head>

<body> 

<div id= "header1">

<%@ include file="common/navigation.jspf"%>

<div class="container col-md-8 col-md-offset-1">
<div class="wrapper">
</div>
</div>

 <div class="container-fluid">
 <div class="row justify-content-center mb-3">
  <div class="col-md-10 col-md-offset-1 ">
 <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
<div class="panel panel-primary">
    <div class="panel-heading">
    <h3><center>MicroSel : votre Système d'Echanges Local - Gestion des Référentiels</center></h3>
    </div>
</div>    
</div>
</div>
</div>
</div>

<div class="col-md-10 col-md-offset-1 ">
<div class="panel panel-primary">
     <div class="panel-heading">Menu</div>
        <div class="panel-body">
           Bonjour ${pageContext.request.userPrincipal.name}
           <br></br> <a href="/referentiels/typepropositions">Cliquer ici</a> pour gérer les types de propositions.
           <br></br> <a href="/referentiels/typedocuments">Cliquer ici</a> pour gérer les types de documents. 
           <br></br> <a href="/referentiels/documents">Cliquer ici</a> pour gérer les documents. 
                     
        </div>
     </div>
 </div>
</div> 
 
 
<div id="footer">
<%@ include file="common/footer1.jspf"%>
</div>


</body>

</html>