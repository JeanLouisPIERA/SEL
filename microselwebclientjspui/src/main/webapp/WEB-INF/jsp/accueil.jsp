<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>

<head>

<title>Accueil</title>
<%@ include file="common/header1.jspf"%>
</head>

<body>

	<div id="header1">

		<%@ include file="common/navigation.jspf"%>

		<!-- <div class="container col-md-10 col-md-offset-1">
<div class="wrapper">
</div>
</div> -->

		<div class="container-fluid">
			<div class="row justify-content-center mb-3">
				<div class="col-md-10 col-md-offset-1 ">
					<div
						class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h3>
									<center>MicroSel : votre Système d'Echanges Local -
										Bienvenue</center>
								</h3>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="container col-md-8 col-md-offset-1">
				<!-- <div class="wrapper"> -->
					<section
						class="container col-md-12 col-md-offset-0 justify-content:stretch">
						<div class="panel panel-primary ">
						<div class="panel-heading">
							<h2>Le Bureau vous informe :</h2>
						</div>
						<p>Le ${infoBureau.dateCreation} par
							${infoBureau.auteurUsername}</p>
							</div>
						<article>
							<p>
								<a href="/documents/static/${infoBureau.typeDocument.id}">Lire
									tout l'article</a>
							</p>
							<p>${infoBureau.entete}</p>

						</article>
					</section>

				</div>
			

			<aside
				class="container col-md-2 col-md-offset-0 justify-content:right">
				<ul>
					<br></br>
					<h4>Articles & Annonces</h4>
					<a href="/articles">Consulter</a>
					<br></br>
					<a href="/articles/newArticle">Publier</a>
					<br></br>
				</ul>

			</aside>

		</div>
	
		<div class="row">
			<section
				class="container col-md-2 col-md-offset-1 justify-content:stretch">
				<div class="panel panel-primary ">
					<div class="panel-heading">
						<h1>${article1.titre}</h1>
						<p>Le ${article1.dateCreation} par ${article1.auteurUsername}</p>
						<p>Rubrique : ${article1.typeArticle.getTypeName()}</p>
					</div>
				</div>
				<article>
					<li><a href="/articles/${article1.id}">Lire tout l'article</a></li>
					<p>${article1.entete}</p>
				</article>
			</section>

			<section
				class="container col-md-2 col-md-offset-0 justify-content:stretch">
				<div class="panel panel-primary ">
					<div class="panel-heading">
						<h1>${article2.titre}</h1>
						<p>Le ${article2.dateCreation} par ${article2.auteurUsername}</p>
					</div>
				</div>
				<article>
					<li><a href="/articles/${article2.id}">Lire tout l'article</a></li>
					<p>${article2.entete}</p>
				</article>
			</section>

			<section
				class="container col-md-2 col-md-offset-0 justify-content:stretch">
				<div class="panel panel-primary ">
					<div class="panel-heading">
						<h1>${article3.titre}</h1>
						<p>Le ${article3.dateCreation} par ${article3.auteurUsername}</p>
					</div>
				</div>
				<article>
					<li><a href="/articles/${article3.id}">Lire tout l'article</a></li>
					<p>${article3.entete}</p>
				</article>
			</section>

			<section
				class="container col-md-2 col-md-offset-0 justify-content:stretch">
				<div class="panel panel-primary ">
					<div class="panel-heading">
						<h1>${article4.titre}</h1>
						<p>Le ${article4.dateCreation} par ${article4.auteurUsername}</p>
					</div>
				</div>
				<article>
					<li><a href="/articles/${article4.id}">Lire tout l'article</a></li>
					<p>${article4.entete}</p>
				</article>
			</section>

			<aside
				class="container col-md-2 col-md-offset-0 justify-content:right">

				<ul>
					<h4>Renseignez-vous</h4>
					<a href="/documents/static/${defSel.typeDocument.id}">${defSel.titre}
					</a>
					<br></br>
					<a href="/documents/static/${defBourse.typeDocument.id}">${defBourse.titre}</a>
					<br></br>
					<a href="/documents/static/${defReseau.typeDocument.id}">${defReseau.titre}</a>
					<br></br>
					<a href="/documents/static/${cgu.typeDocument.id}">${cgu.titre}</a>
					<br></br>
					<a href="/documents/static/${infosLegales.typeDocument.id}">${infosLegales.titre}</a>
					<br></br>
					<a href="/documents/static/${faq.typeDocument.id}">${faq.titre}</a>
					<br></br>
				</ul>
				<ul>
					<h4>Adhérez</h4>

					<a
						href=http://localhost:8180/auth/realms/microsel-realm/login-actions/registration?execution=67ce5a8c-f1e2-4c65-ba71-56e80f445044&client_id=microselwebclientjspui&tab_id=rEFP63wWIDY>Enregistrez-vous
					</a>
				</ul>
			</aside>
		</div>






		<div id="footer">
			<%@ include file="common/footer1.jspf"%>
		</div>
	</div>

</body>

</html>