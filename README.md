
# Projet Microsel

# Pr�sentation :

Douzi�me et dernier projet du parcours de formation D�veloppeur Java dOpenCLassrooms, il est demand� de mettre au service les comp�tences acquises.
Le choix a �t� fait de d�velopper un syst�me d'information pour les SEL = Syst�mes d'Echanges Local.

Un syst�me d��change local (SEL) met en relation des individus qui cherchent � �tablir des �changes solidaires et volontaires de biens ou de prestations. L��change n�est ni un don, ni du troc, ni du b�n�volat, Il donne lieu � une transaction en monnaie locale non convertible. 
Ce projet propose de structurer les �changes autour d'une bourse d'�changes 

Les adh�rents recherchent la convivialit� et le partage avec le plus grand nombre dans le cadre d��changes faciles, voulus, responsables dans le respect de r�gles de fonctionnement partag�es mais sans la contrainte d�un arbitrage de tiers � leurs �changes. Ils attendent aussi le respect de leur vie priv�e. Ils ont une exigence de transparence entre eux et vis-�-vis des tiers sachant que la tra�abilit� des 
transactions.Ce projet est susceptible de les leur apporter.

# R�f�rences

CONTACT : jeanlouispiera@yahoo.fr
VERSION : 1.1.2-SNAPSHOT0
Janvier-Avril 2021

# Pr�sentation G�n�rale et Technique

Le projet repose sur une architecture microservices mettant en oeuvre des edge services de SPRING CLOUD

o	SPRING CLOUD CONFIG pour faciliter la gestion centralis�e des fichiers de configuration dans un d�p�t Git

o	EUREKA pour enregistrer les services

o	RIBBON pour g�rer la charge entre les instances des microservices

o	ZUUL pour permettre l�acc�s centralis� aux microservices

L�API KEYCLOAK a �t� install�e au niveau de la Web Appli et du Gateway ZUUL pour la gestion s�curis�e des authentifications et autorisations. Le param�trage de sa base de donn�es embarqu�e H2 vers MYSQL a permis d�utiliser les donn�es dans le microservice Utilisateurs avec un grande souplesse dans la gestion des r�les.   

Le broker RABBITMQ (protocole AMQP) permet de mettre en �uvre la gestion asynchrone des �v�nements, en particulier l'envoi des mails tr�s sollicit�s dans les processus automatiques d'arri�re plan.

La biblioth�que LIQUIBASE a �t� utilis�e pour versionner les bases de donn�es avec beaucoup de souplesse au travers un simple fichier yml.

L�API DOCKER permet la conteneurisation des applications et le lancement des conteneurs packag�s avec l�outil docker-compose.

L'application se pr�sente sous la forme de :

o	3 edge services indiqu�s  :zuul-server, eureka-server et config-server qui g�re les fichiers applications.properties d�pos�es sur GitHub �l'adresse publique : https://github.com/JeanLouisPIERA/sel-config-repo

o	1 appli-web front office (microselwebclientjspui)

o	1 micro-service de gestion de la bourse d'�changes (microselbourse)

o	1 micro-service de gestion de la publication des articles et des documents sur le site et le support pour son administration (microselr�f�rentiels)

o	1 micro-service de gestion des utilisateurs enregistr�s en relation avec l'API KEYCLOAK.

Le programme a �t� �crit en Java version 11 avec le framework Springboot et l'outil de gestion MAVEN.


# Installation et lancement

Le package se r�cup�re sur le d�p�t GitHub public 

https://github.com/JeanLouisPIERA/SEL

Docker permet un lancement sans autre pr�requis que celui de son installation : la commmande en ligne de commande 

docker-compose up 

permet de g�n�rer les images et conteneurs. Il rese � se connecter � l'adresse 

http://localhost:8087/accueil pour acc�der � la web-appli. 

La plupart des fonctionnalit�s ne sont accessibles que par KEYCLOAK. Il suffit � l'administrateur de se connecter � la premi�re connexion avec le mot de passe administrateur qui pourra �tre chang� imm�diatement dans la console KEYCLOAK.

L'application peut aussi �tre install�e STANDALONE ce qui n�cessite des pr�-requis d'installation de la plupart des outils pr�sent�s plus haut et un d�ploiement progressif CONFIG-SERVER / EUREKA-SERVER / ZUUL-SERVER / MICROSELUSER / MICROSELBOURSE / MICROSELREFERENTIELS avant le lancement de l'appli Web en front. Le lancement se fait en ligne de commande au point d'installation avec la commande 

mvn spring-boot:run

Un dossier BAT. peut aussi �tre utilis� pour le lancement synchronis� des diff�rents services.

# Param�trage

Les param�trages sont � g�rer sur le d�p�t Git indiqu� plus haut 




