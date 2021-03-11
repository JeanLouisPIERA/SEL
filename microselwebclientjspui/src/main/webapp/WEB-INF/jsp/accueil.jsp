<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>

<head>

<title>Accueil</title>
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
    <h3><center>MicroSel : votre Système d'Echanges Local - Bienvenue</center></h3>
    </div>
</div>    
</div>
</div>
</div>
</div>

<div class="row">
<div class="container col-md-10 col-md-offset-1">
<div class="wrapper">
<section class="container col-md-10 col-md-offset-1 justify-content:stretch">
  <h2>Le Bureau vous informe :</h2>
   <p>Le xx-xx-2021 par Bureau</p>
  <article>
  <li><a href="#">Lire tout l'article</a></li>
  <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas fermentum velit mauris. Donec in dui congue, varius dui nec, efficitur nibh. Morbi ullamcorper nisl quis nulla ornare lacinia. Cras tincidunt diam ac mi tempus feugiat. Curabitur tristique at metus eget malesuada. Ut vestibulum neque at lacus placerat elementum. Maecenas semper enim scelerisque enim consequat vulputate. Vivamus laoreet efficitur ex, nec tristique diam malesuada non. Sed eu neque id nibh lacinia viverra. 
  </p>
</article>
</section>

</div>
</div>
</div>

<div class="row">
<section class="container col-md-2 col-md-offset-2 justify-content:stretch">
  <h1>Titre Article</h1>
   <p>Le xx-xx-2021 par Pseudo1</p>
  <article>
  <li><a href="#">Lire tout l'article</a></li>
  <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas fermentum velit mauris. Donec in dui congue, varius dui nec, efficitur nibh. Morbi ullamcorper nisl quis nulla ornare lacinia. Cras tincidunt diam ac mi tempus feugiat. Curabitur tristique at metus eget malesuada. Ut vestibulum neque at lacus placerat elementum. Maecenas semper enim scelerisque enim consequat vulputate. Vivamus laoreet efficitur ex, nec tristique diam malesuada non. Sed eu neque id nibh lacinia viverra. 
  </p>
</article>
</section>

<section class="container col-md-2 col-md-offset-0 justify-content:stretch">
  <h1>Titre Article</h1>
  <p>Le xx-xx-2021 par Pseudo2</p>
  <article>
  <li><a href="#">Lire tout l'article</a></li>
  <p>Proin vitae risus auctor, eleifend nisl vel, auctor lacus. Nunc molestie pellentesque tellus, vitae commodo lectus mollis in. Praesent ac purus a mi vehicula faucibus. Nam laoreet varius eros sit amet viverra. Quisque a venenatis nulla. Ut at ultrices tortor. Fusce non condimentum risus. Donec sollicitudin est aliquet, tristique tortor nec, interdum purus. Nunc vehicula velit vitae venenatis ornare. Suspendisse vitae eleifend ante. Nunc vestibulum nibh nisi, sit amet vehicula felis fermentum sed. Nulla molestie vestibulum velit
  </p>
  </article>
</section>

<section class="container col-md-2 col-md-offset-0 justify-content:stretch">
  <h1>Titre Article</h1>
   <p>Le xx-xx-2021 par Pseudo3</p>
  <article>
  <li><a href="#">Lire tout l'article</a></li>
  <p>Duis tristique gravida nisi, eu fringilla dui molestie non. Vestibulum et hendrerit nunc. Fusce commodo interdum eros, pharetra dictum nunc pellentesque et. Pellentesque aliquam, ante sed tincidunt pharetra, libero nisl ullamcorper sapien, dignissim euismod tellus odio at tortor. Nullam at fringilla urna. Nam tincidunt, mi interdum consequat tincidunt, lectus turpis imperdiet ex, et condimentum est ligula sed erat. Vivamus elit justo, ultricies dictum elit eu, aliquam ultricies metus.</p>
</article>
</section>

<section class="container col-md-2 col-md-offset-0 justify-content:stretch">
  <h1>Titre Article</h1>
   <p>Le xx-xx-2021 par Pseudo4</p>
  <article>
  <li><a href="#">Lire tout l'article</a></li>
  <p>Fusce commodo interdum eros, pharetra dictum nunc pellentesque et. Pellentesque aliquam, ante sed tincidunt pharetra, libero nisl ullamcorper sapien, dignissim euismod tellus odio at tortor. Nullam at fringilla urna. Nam tincidunt, mi interdum consequat tincidunt, lectus turpis imperdiet ex, et condimentum est ligula sed erat. Vivamus elit justo, ultricies dictum elit eu, aliquam ultricies metus.Duis tristique gravida nisi, eu fringilla dui molestie non. Vestibulum et hendrerit nunc. </p>
</article>
</section>

<aside class="container col-md-2 col-md-offset-0 justify-content:right">
  <ul>
  <br></br>
  <h4>Déjà parus</h4>
  	<a href="/accueil">Article 1</a> 
  	<br></br> <a href="/accueil">Article 2</a> 
  	<br></br> <a href="/accueil">Article 3</a> 
  	<br></br>
  </ul>
  <ul>
  <h4>Renseignez-vous</h4>
    <a href="/accueil">Qu'est ce qu'un SEL ? </a> 
  	<br></br> <a href="/accueil">Les Bourses d'échanges</a> 
  	<br></br> <a href="/accueil">Le réseau des SEL</a> 
  	<br></br>
  </ul>
  <ul>
  <h4>Adhérez</h4>
  <!-- <ul>
    <li><a th:href="@{/registration}" href="#">Sign-Up </a></li>
  </ul> -->
  </ul>
</aside>  
</div>
 
 
 
 
 
 
<div id="footer">
<%@ include file="common/footer1.jspf"%>
</div>
</div>

</body>

</html>