Structure de Code: 
	package :
	-Couche : qui contient l'ensemble des couches à analysé ( Ethernet, Ip (Ipv4), UDP, DHCP, DNS) avec les sous classes de certain couches (OptionDHCP, DnsRR, DnsQuestion etc..) qui permet à factoriser le code. Toutes ces couches implémente l'interface ICouches, et une class trame qui contient une liste de ICouches
	
	-gui : package qui contient l'ensemble des class pour gérer et lancer l'interface graphique, avec un fichier Analyseur.FXML, qui permet de donner la squelette de l'interface graphique. MainController.java pertmet de gérer les différents actions sur l'interface graphique comme ouvrir un fichier, sauvegarder un fichier , sélectionner une trame, etc. La class Analyse.java contient le main qui permet de lancer l'application.
	-pobj.lecture : contient une class FileReader.java, qui permet de lire un fichier .txt qui contient la trame, puis de filtrer les informations non pertinantes et de générer une liste de trame à partir du fichier lu.
	-pobj.lecture.test : TestFileReader qui test le fonctionnement de FileReader.java
	-pobj.output : contient une class Output.java, qui permet d'écrire les résultats obtenue dans un fichier .txt
	-pobj.tools : contient une class Tools.java qui elle contient que des fonctions static de convertissement.
	
