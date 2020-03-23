# LibraryManager
Projet de gestionnaire de location en librairie de Laetitia Chaillou et Corentin Soubeiran dans le cadre du cours d'IN205 à l'Ensta Paris. Mars 2020

Ce projet est guidé par l'équipe pédagogique dans le cadre d'un enseignement au sein de l'Ensta Paris.

![Dashboard](/image_readme/image_dashboard.png "Dashboard")
## Implementation
#### Language 
Ce projet est essentiellement un projet en code JAVA. Afin de réaliser une IHM sur serveur nous avons également utilisé des ressources XML et JSP.
Le serveur que nous avons utilisé est un serveur Tomcat (7 ou 8 suivant nos utilisation) le tout chapoté par MAVEN.

#### IDE
Afin d'executer notre code nous avons utilisé Eclipse ou VScode en fonction globalement des caprices d'Eclipse ou de notre configuration PC.

Ainsi ce code est fonctionnel sur Windows via Eclipse et sous Linux via Eclipse et VScode.

#### Versinning et partage
Afin de gerer le versionning de notre code nous avons utilisé GitHub. Cela nous a permis d'une part de travailler conjointement sur le projet en plus d'utiliser l'option live share de vscode mais également d'implementer progressivement, de revenir à des versions ultérieur ou de faire du développement sur des branches parallèles à la master avant de les "merger".

## Execusion et lancement:
 Mettre à jours la data base
Pour cela executer le fichier FillDatabase.java du dossier src/utils. 

Vous pouvez soit le lancer directement depuis votre IDE ou lancer la commande
cd "DIR" ; /usr/lib/jvm/java-11-openjdk-amd64/bin/java -Dfile.encoding=UTF-8 @/tmp/cp_1j44djvuzojcnglrncw9fs07q.argfile com.excilys.librarymanager.utils.FillDatabase

où dir est le path de LibraryManager dans votre pc. Pour nous par exemple c'est: 
/mnt/c/Users/csoub/OneDrive/Bureau/My desktop/LibraryManager

### 1ere possiblilité
1. Lancer le serveur Tomcat
Pour lancer le serveur en local exécuter la commande:
	mvn clean install tomcat7:run
Si cela ne fonctionne pas on peut tenter desepérement un:
	mvn clean install tomcat:run

2. Enjoy
Sur votre navigateur WEB, mettre dans la barre de recherche: 
	http://localhost:8080/ProjetEnsta/
Vous arriverez directement sur la page d'accueil du projet.

### 2e possibilité
1. installer l'extension VSCode tomcat for java
2. ajouter (grâce au "+" dans l'onglet tomcat servers de vscode) le server tomcat téléchargé
3. dans le terminal lancer les commandes "mvn clean" puis "mvn install"
4. dans le dossier "target" qui vient d'apparaitre, faire clique droit sur le fichier *.war, puis "run on tomcat server"
5. dans l'onglet "tomcat servers" de VSCode faire clique droit sur l'application disponible puis "open in browser"
6. si une erreur 404 s'affiche, rafraichir la page jusqu'à obtenir l'affichage attendu

## Navigation

Afin de représenter la navigation au sein du serveur nous avons construit un diagramme d'utilisation: 

![DiagrammeUtilisation](/image_readme/diagrame_utilisation.png "Diagramme d'utilisation")
## Avancement: 
- Nous avons réalisé l'intégralité des exercices 1 à 4. 
- Dans le dossier src/test vous trouverez les tests unitaires des exercices 1 à 3 qui permettent de tester: 
	- Nos classes model pour ModelTest.java
	- Nos DAO pour DaoTest.java
	- Nos services pour ServiceTest.java 

Nous avons généré la Javadoc, celle ci est disponible dans le dossier "doc" à la racine du projet.
Maleureusement lors du lancement du serveur la communication avec la DATAbase ne se fait pas correctement et les données ne se mettent pas à jour (Problème lors de la récupération de la liste des membres error : Database may be already in use: null. Possible solutions: close all other connection(s); use the server mode [90020-197]). 

## Conclusion:
Ce projet est selon nous une bonne opportunité pour apprenhender les serveurs, la création d'un SGBD et d'une IHM. Néamoins nous regretons d'avoir eu énormement de difficultés lors de la mise en place. Malgré les tutoriels détaillés des TD et de nombreuses recherches de notre coté, beaucoup de choses étaient nouvelles pour nous (comme JSP, XML, tomcat, maven, Eclipse) et même si nous sommes content de connaitre un peu ces systèmes, ceux ci ont également été trés chronophages. 

Malgré notre assiduité pendant les TD, nous pensons que nous n'avons pas eu le temps de bien comprendre le TD4 sur la mise en place d'un serveur de Film pour appréhender plus sereinement ce projet d'une echelle bien plus conséquente. En effet nous pensons qu'avoir une semaine de plus lors du td Film nous aurait permis d'arriver avec une vision plus globale sur ce projet ci et avec des idées orientées pour le débuggage. 

Finalement nous allons poursuivre les notions de cet enseignement au delà de ce projet. Par exemple nous avons commencé à regarder le codage d'applications mobiles en java et les possibilité que nous pourrions avoir en utilisant les ressources de ce cours. Nous avons notamment vu que nous pourrions utiliser Android studio pour nous eviter/simplifier la partie XLM. 