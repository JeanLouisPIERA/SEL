
# Projet Microsel

# Présentation :

Douzième et dernier projet du parcours de formation Développeur Java dOpenCLassrooms, il est demandé de mettre au service les compétences acquises.
Le choix a été fait de développer un système d'information pour les SEL = Systèmes d'Echanges Local.

Un système d’échange local (SEL) met en relation des individus qui cherchent à établir des échanges solidaires et volontaires de biens ou de prestations. L’échange n’est ni un don, ni du troc, ni du bénévolat, Il donne lieu à une transaction en monnaie locale non convertible. 
Ce projet propose de structurer les échanges autour d'une bourse d'échanges 

Les adhérents recherchent la convivialité et le partage avec le plus grand nombre dans le cadre d’échanges faciles, voulus, responsables dans le respect de règles de fonctionnement partagées mais sans la contrainte d’un arbitrage de tiers à leurs échanges. Ils attendent aussi le respect de leur vie privée. Ils ont une exigence de transparence entre eux et vis-à-vis des tiers sachant que la traçabilité des 
transactions.Ce projet est susceptible de les leur apporter.

# Références

CONTACT : jeanlouispiera@yahoo.fr
VERSION : 1.1.2-SNAPSHOT0
Janvier-Avril 2021

# Présentation Générale et Technique

Le projet repose sur une architecture microservices mettant en oeuvre des edge services de SPRING CLOUD

o	SPRING CLOUD CONFIG pour faciliter la gestion centralisée des fichiers de configuration dans un dépôt Git

o	EUREKA pour enregistrer les services

o	RIBBON pour gérer la charge entre les instances des microservices

o	ZUUL pour permettre l’accès centralisé aux microservices

L’API KEYCLOAK a été installée au niveau de la Web Appli et du Gateway ZUUL pour la gestion sécurisée des authentifications et autorisations. Le paramétrage de sa base de données embarquée H2 vers MYSQL a permis d’utiliser les données dans le microservice Utilisateurs avec un grande souplesse dans la gestion des rôles.   

Le broker RABBITMQ (protocole AMQP) permet de mettre en œuvre la gestion asynchrone des évènements, en particulier l'envoi des mails trés sollicités dans les processus automatiques d'arrière plan.

La bibliothèque LIQUIBASE a été utilisée pour versionner les bases de données avec beaucoup de souplesse au travers un simple fichier yml.

L’API DOCKER permet la conteneurisation des applications et le lancement des conteneurs packagés avec l’outil docker-compose.

L'application se présente sous la forme de :

o	3 edge services indiqués  :zuul-server, eureka-server et config-server qui gère les fichiers applications.properties déposées sur GitHub àl'adresse publique : https://github.com/JeanLouisPIERA/sel-config-repo

o	1 appli-web front office (microselwebclientjspui)

o	1 micro-service de gestion de la bourse d'échanges (microselbourse)

o	1 micro-service de gestion de la publication des articles et des documents sur le site et le support pour son administration (microselréférentiels)

o	1 micro-service de gestion des utilisateurs enregistrés en relation avec l'API KEYCLOAK.

Le programme a été écrit en Java version 11 avec le framework Springboot et l'outil de gestion MAVEN.


# Installation et lancement

Le package se récupère sur le dépôt GitHub public 

https://github.com/JeanLouisPIERA/SEL

Docker permet un lancement sans autre prérequis que celui de son installation : la commmande en ligne de commande 

docker-compose up 

permet de générer les images et conteneurs. Il rese à se connecter à l'adresse 

http://localhost:8087/accueil pour accéder à la web-appli. 

La plupart des fonctionnalités ne sont accessibles que par KEYCLOAK. Il suffit à l'administrateur de se connecter à la première connexion avec le mot de passe administrateur qui pourra être changé immédiatement dans la console KEYCLOAK.

L'application peut aussi être installée STANDALONE ce qui nécessite des pré-requis d'installation de la plupart des outils présentés plus haut et un déploiement progressif CONFIG-SERVER / EUREKA-SERVER / ZUUL-SERVER / MICROSELUSER / MICROSELBOURSE / MICROSELREFERENTIELS avant le lancement de l'appli Web en front. Le lancement se fait en ligne de commande au point d'installation avec la commande 

mvn spring-boot:run

Un dossier BAT. peut aussi être utilisé pour le lancement synchronisé des différents services.

# Paramétrage

Les paramétrages sont à gérer sur le dépôt Git indiqué plus haut 




