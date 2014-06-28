DevStarter
==========

Projet de web avancé 2013 - ISEP Groupe ROSE - Spring ROO - Reverse Engineering


## Conception et spécifications

Un club d’informaticien souhaite promouvoir le développement de logiciels libres par la création d’un outil permettant aux développeurs de vivre de leur passion. 
Afin de les aider dans leur travail, cet outil aura pour vocation de leur permettre de décrire les fonctionnalités des logiciels qu’ils développent afin de percevoir des dons de la part des personnes intéressées par ces projets.

Fonctions réalisées au cours de ce projet :

- Inscription et authentification par email, Facebook et Google.
- Création d'une page de présentation d'un projet
- Recherche simple et avancée d'un ou plusieurs projets
- Listing de projets selon plusieurs critères
- Créditer virtuellement son compte et voir ses transactions
- Consulter, Donner, Suivre et Commenter un projet
- Discuter sur le Forum
- Editer ses projets
- Modifier son profil
	

## Technologies utilisées

Nous avons tout d'abord réalisé un Modèle Conceptuel de Données à l'aide de Power AMC. Ce dernier nous ayant généré le script de création de notre base de données MySQL.
Nous avons ensuite créé un projet Spring ROO sous STS, puis nous avons effectué un Reverse Engineering afin de générer toutes les classes du Modèle.

- Java 6
- MySQL 5
- Spring 3.1.1
- Roo 1.2.3
- Maven 3
- Tomcat 6

## Installation

1.  Dézipper le projet dans le répertoire voulu
2.  Exécuter le script SQL "devstarter_db_samples.sql" du répertoire "doc/bdd/", ce dernier contient également des données d'exemple.
3.  Aller dans le fichier "src/main/ressources/META-INF/spring/database.properties" et modifier les informations de connexion à la base MySQL si nécessaire (user="root", password="", port="8889")
4.  Si vous souhaitez utiliser la fonction de login avec Facebook, l'application doit être lancée sur un serveur dont l'ip et le port sont : localhost:8080
5.  Ouvrir un terminal et se positionner dans le répertoire racine "DevStarter" du projet et exécuter les commandes suivantes :
  
  - mvn clean install
  - mvn tomcat:run

Et le tour est joué !


