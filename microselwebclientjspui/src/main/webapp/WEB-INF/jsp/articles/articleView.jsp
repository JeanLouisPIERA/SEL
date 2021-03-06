<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Vue d'un article</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body> 

<div id= "header1">
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>


<div class="row d-flex justify-content-center">

<div class="container-fluid">

<div class="row d-flex justify-content-center">
<div class="container col-md-6 col-md-offset-3">
 <div class="panel panel-primary ">
 
  <div class="panel-heading">
   <h5>${article.titre}</h5>
  </div>
  
  <div class="panel-heading">
   <span>Date de cr�ation : ${article.dateCreation} Post� par :${article.auteurUsername} </span>
   <!-- <h7></h7>
    </div>
  <div class="panel-heading">
   <span> </span>
   <h7></h7> -->
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <tbody>
        <tr>
            <td>${article.contenu}</td>
        </tr>
       
	    </tbody>
	</table>
  
</div>
 </div>
 </div>

   
 

  </div>
 </div>

 </div>
 
</div>



<div id="footer">
<%@ include file="/WEB-INF/jsp/common/footer1.jspf"%>
</div>


</body>

</html>