<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>

<head>

<title>Accueil R�f�rentiels</title>
<%@ include file="common/header1.jspf"%>
</head>

<body>

	<div id="header1">

		<%@ include file="common/navigation.jspf"%>

		<div class="container col-md-8 col-md-offset-1">
			<div class="wrapper"></div>
		</div>

		<div class="container-fluid">
			<div class="row justify-content-center mb-3">
				<div class="col-md-10 col-md-offset-1 ">
					<div
						class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h3>
									<center>Espace Administrateur</center>
								</h3>
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

					<a type="button" class="btn btn-primary" href="/accounts">Consulter</a> Tous les comptes adh�rents <br></br> 
					<a type="button" class="btn btn-primary" href="/blocages"> Consulter </a> Tous les comptes adh�rents bloqu�s<br></br>
					<a type="button" class="btn btn-primary" href="/wallets">Consulter</a> Tous les portefeuilles, leur solde et le d�tail de toutes leurs transactions <br></br> 
					<a type="button" class="btn btn-warning" href="/typepropositions">   G�rer   </a> Les diff�rents types de propositions <br></br> 
					<a type="button" class="btn btn-warning" href="/typedocuments"> G�rer </a> Tous les types de document <br></br> 
					<a type="button" class="btn btn-warning" href="/documents"> G�rer </a> Tous les documents<br></br> 
					<a type="button" class="btn btn-warning" href="/typearticles"> G�rer </a> Tous les types d'article <br></br>
					<a type="button" class="btn btn-danger" href="/articles"> Mod�rer </a> les articles
					
					
				</div>
			</div>
		</div>
	</div>


	<div id="footer">
		<%@ include file="common/footer1.jspf"%>
	</div>


</body>

</html>