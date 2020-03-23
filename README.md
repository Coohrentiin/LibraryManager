# LibraryManager
Projet de gestionnaire de location en librairie de Laeticia Chaillou et Corentin Soubeiran dans le cadre du cours d'IN205 à l'Ensta Paris. Mars 2020

Ce projet est guidé par l'équipe pédagogique dans le cadre d'un enseignement au sein de l'Ensta Paris.

## Implementation
#### Language 
Ce projet est essenciellement un projet en code JAVA. Afin de réaliser une IHM sur serveur nous avons également utilisé des ressources XML et JSP.
Le serveur que nous avons utilisé est un serveur Tomcat (7 ou 8 suivant nos utilisation) le tout chapoté par MAVEN.

#### IDE
Afin d'executer notre code nous avons utilisé Eclipse ou VScode en fonction glogalement des caprices d'Eclipse ou ne notre configuration PC.

Ainsi ce code est fonctionnel sur Windows via Eclipse et sous Linux via Eclipse et VScode.

#### Versinning et partage
Afin de gerer le versionning de notre code nous avons utiliser GitHub. Cela nous a permis d'une part de travailler conjointement sur le projet en plus d'utiliser l'option live share de vscode mais également d'implementer progressivement, de revenir à des versions ultérieur ou de faire du dévelopement sur des branches parallèles à la master avant de les "merger".

## Execusion et lancement:
1. Mettre à jours la data base
Pour cela executer le fichier FillDatabase.java du dossier src/utils. 

Vous pourvez soit le lancer directement depuis votre IDE ou lancer la commande
cd "DIR" ; /usr/lib/jvm/java-11-openjdk-amd64/bin/java -Dfile.encoding=UTF-8 @/tmp/cp_1j44djvuzojcnglrncw9fs07q.argfile com.excilys.librarymanager.utils.FillDatabase

où dir est le path de LibraryManager dans votre pc. Pour nous par exemple c'est: 
/mnt/c/Users/csoub/OneDrive/Bureau/My desktop/LibraryManager

2. Lancer le serveur Tomcat
Pour lancer le serveur en local exécuter la commande:
	mvn clean install tomcat7:run
Si cela ne fonctionne pas on peut tenter desepérement un:
	mvn clean install tomcat:run

3. Enjoy
Sur votre navigateur WEB, mettre dans la barre de recherche: 
	http://localhost:8080/ProjetEnsta/
Vous arriverez directement sur la page d'accueil du projet.

## Avancement: 
- Nous avons réalisé l'intégralité des exercices 1 à 4. 
- Dans le dossier src/test vous trouverez les tests unitaires des exercices 1 à 3 qui permettent de tester: 
	- Nos classes model pour ModelTest.java
	- Nos DAO pour DaoTest.java
	- Nos services pour ServiceTest.java 

Maleureusement lors du lancement du serseur la communication avec la DATAbase ne se fait pas correctement et les données ne mettent pas à jours. 

## Conclusion:
Ce projet est celon nous une bonne oportunité pour apprenhender les serveurs, la création d'un SGBD et d'une IHM. Néamoins nous regretons d'avoir eu énormement de difficulter lors de la mise en place. Malgrés les tutoriels détaillé des TD et de nombreuse recherhce de notre coté, beaucoup de choses étaient nouvelles pour nous (comme JSP, XML, tomcat, maven, Eclipse) et même nous somme content de connaitre un peu ces systèmes, ceux ci ont également été trés chronophage. 

Malgrés notre assiduité pendant les TD, nous pensons que nous n'avons pas eu le temps de bien comprendre le TD4 sur la mise en place d'un serveur de Film pour appréhender plus sereinement ce projet d'une echelle bien plus conséquente. En effet nous pensons qu'avoir une semaine de plus lors du td Film nous aurait permis d'arriver avec une vision plus globale sur ce projet ci et avec des idées orienté pour le débuggage. 

Finalement nous allons poursuivre les notions de cet enseignement au delà de se projet. Par exemple nous avons commencé à regarder le codage d'applications mobiles en java et les possibilité que nous pourrions avoir en utilisant les ressources de ce cours. Nous avons notamment vu que nous pourrions utiliser Android studio pour nous eviter/simplifier la partie XLM. 