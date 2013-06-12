DevStarter
==========

Projet de web avancé 2013 - Spring ROO - Reverse Engineering

Florian GUERIN - Edouard VAN YEN - Pierre LE TEXIER - Gustave NGUYEN - Narjisse HIMMI

## Objectifs

Un club d’informaticien souhaite promouvoir le développement de logiciels libres par la création d’un outil permettant aux développeurs de vivre de leur passion. 
Afin de les aider dans leur travail, cet outil aura pour vocation de leur permettre de décrire les fonctionnalités des logiciels qu’ils développent afin de percevoir des dons de la part des personnes intéressées par ces projets.

## Pré-requis

- Java 6
- MySQL 5
- Spring 3.1.1
- Roo 1.2.3
- Maven 3
- Tomcat 6

## Installation

1.  Dézipper le projet dans le répertoire voulu
2.  Exécuter le script SQL "devstarter_db_samples.sql" du répertoire "doc/bdd/"
3.  Aller dans le fichier "src/main/ressources/META-INF/spring/database.properties" et modifier les informations de connexion à la base MySQL si nécessaire (user="root", password="", port="8889")
4.  Ouvrir un terminal et se positionner dans le répertoire racine "DevStarter" du projet et exécuter les commandes suivantes :
  
  - mvn clean install
  - mvn tomcat:run

Et le tour est joué !


