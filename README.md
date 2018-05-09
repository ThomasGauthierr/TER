# TER

## Configuration

1. Installation de la librairie __RXTX__ :
    * télécharger l'archive [ici](http://fizzed.com/oss/rxtx-for-java) ;
    *

## Dataset ##
[Cliquer ici](https://public.opendatasoft.com/explore/dataset/donnees-synop-essentielles-omm/export/?q=nice)

## SerialPortEvent ##

### BI : Break interrupt ###
The serial port generates a break interrupt when the received data has been in an inactive state longer than the transmission time for one character.

### CD : Carrier Detect ###
Thrown when the low-levels protocols between the computer and the arduino have been succefully negociated.

### CTS : Clear to send ###
Mechanism used to reduce frame collisions introduced by the [hidden node problem](https://en.wikipedia.org/wiki/Hidden_node_problem).

### DSR : Data Set Ready ###
Indicates that the device is switched on, connected and ready.

### DATA_AVAILABLE ###
Thrown when data are received.

### FE : Framing error ###
Thrown when a data frame is read at the wrong starting point.

### Overrun error ###
Thrown when a young data arrive before an older one.

### PE : Parity error ###
Results from irregular changes to data, as it is recorded when it is entered in memory.

## TODO:

On pose un contrat sur un contexte

Un contexte c'est :
* des capteurs ;
* des actionneurs ;
* un nom (salle ?)
* des données météo ?

Lorsque qu'un contrat est violé, celui-ci donne un ViolatedContext avec les fautifs énumérés dans une brique de choix de décision.

Une brique de choix de décision c'est :
* un Context en entrée (violé ou pas ? ça dépend, que faire quand tout va bien = même question ue quoi faire quand tt va pas bien) ;
* une réponse en sortie -> un objet indiquant quoi faire

Optionnel:
lorsque la décision est donnée, on étudie si elle est pertinente avec les données de learning :
* ouvrir la fenêtre pour réduire un peu la chaleur alors qu'on est en hiver ? pas une très bonne idée
* jsais pas, d'autres exemples quoi