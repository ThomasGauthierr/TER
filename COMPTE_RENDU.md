# Hiérarchie du compte-rendu

## Sommaire

## Introduction

> Le problème à résoudre est défini et placé dans son contexte: bref aperçu des écrits antérieurs,
avantages que peut apporter la résolution du problème. Le lecteur prend conscience du
problème à résoudre. L'introduction doit aller droit au but: c'est à ce stade que le lecteur doit
pouvoir déterminer si le rapport peut lui être utile ou non.

Description du sujet [ici](http://www.i3s.unice.fr/~malapert/org/teaching/ter18/ter09.pdf).
On apporte une application mère qui communique avec des capteurs et des actionneurs dont les communications sont bien définies (à définir plus tard justement)
From scratch, on avait juste le pb et on propose une solution qui va : s'adapter aux différentes configuration ? Totalement indépendante des contraintes des capteurs / actionneurs que l'on veut ajouter ? Evolutive et maintenable ?

Système d'interaction intelligent par contrat -> définir ce qu'est un contrat ? Brièvement, et présenter le contrat en détail plus tard et expliquer pourquoi une telle hiérarchie de contrat sur contrat/contexte existe et qu'est-ce qu'elle propose et POURQUOI !
On explique seulement le problème

### Présentation du groupe

### Présentation du sujet

### Etat de l'art

## Hypothèse

> Définition du point de départ, étude du ou des modèles dont on fera usage, description des
bases de la théorie utilisée et des approximations nécessaires.

On présente notre POC et pourquoi une POC sur des éléments du modèle est applicable à l'ensemble du problème !

## Exécution

> Description détaillée de la résolution du problème, éventuellement par plusieurs méthodes.
Pour faciliter la lecture du texte, il est souvent souhaitable de renvoyer à la fin (appendices)
certains développements mathématiques complexes, programmes d'ordinateur, procédés de
mesure et autres thèses annexes. Ces éléments ont tous leur importance, toutefois leur
présence assez lourde dans le texte risque de faire perdre le fil de la présentation. Le lecteur
pressé doit pouvoir comprendre votre rapport sans consulter les appendices ni les références.

^appendices mettre par exemple les diagrammes de classes ? Et ne pas les détailler dans cette partie ?
On doit expliquer concrètement l'éxecution de la résolution du pb.
Donc ne pas oublier au préalable d'avoir présenter/introduit tous les concepts à utiliser : ex les contrats, et aussi à avoir redéfini le sujet par rapport à celui énnoncé sur le TER.
Ne pas oublier de décrire POURQUOI et non COMMENT /!\ très important.
Apporter des schémas complémentaires tels que ceux établis lors des séances avec les coordinateurs.

### Travail effectué

#### Technologies utilisées

Java, Maven, SerialPort (? Pas vraiment utile de le préciser), C/Arduino

#### Algorithmes

Decison making, machine learning, création de contrat

### Gestion de projet

> vous devrez présenter tout ce qui a permis, au sens large, de faire fonctionner votre équipe. Le travail effectué par chacun des membres du groupe devra être détaillé. Si vous avez été amené à utiliser des logiciels de gestion de source (CVS, SVN…) ou des outils de test, vous pouvez les présenter ici (ou en 3 si vous préférez).

#### Gestionnaire de version

#### Tests unitaires java

### Déroulement

## Résultats

> Présentation des valeurs obtenues par calcul ou par mesure, des formules mises au point, etc.,
avec éventuellement quelques commentaires ou remarques. La présentation des résultats est
un des éléments essentiels de tout rapport: le lecteur doit pouvoir comprendre facilement leur
signification. Une présentation sous forme graphique (pour autant que les échelles soient
clairement indiquées) est souhaitable, de préférence à des tableaux de chiffres ou d'équations
qui requièrent un effort de compréhension supplémentaire.

Montrer différents scénarios, tels que :

* Temperature (avec fenêtre ouverte, chauffage, clime, etc. et une période de l'année utilisant le ML pour raisonner par ex: hiver vs été !)
* Lumière ?

Ne pas évaluer les résultats ! (voir en dessous)

## Evaluation

> Analyse objective des résultats obtenus et mise en évidence des effets nouveaux, inattendus
ou intéressants. Détermination des erreurs commises, des manières de les corriger ou de les
compenser et de la confiance que l'on peut accorder à la méthode employée. Détermination de
ses limites éventuelles. Confrontation entre plusieurs méthodes.

Expliquer pourquoi notre exemple est applicable à un environnement plus grand/est transposable à plus grande échelle.

Montrer l'éco d'énérgie ?
Montrer l'utilisation du ML ?

## Conclusion

> Le but poursuivi a-t-il été atteint? Totalement, ou partiellement? Si non, pourquoi? Soyez bref
et précis, ne répétez pas ici ce qui a été décrit précédemment dans le rapport.

Je sais pas T.T

## Recommandations

> Il se peut que votre rapport serve de base pour un travail ultérieur; vous pouvez ici donner
quelques conseils à vos successeurs, éventuellement proposer des méthodes susceptibles de
donner de meilleurs résultats.

Pour la suite en stage d'été

## Appendices

> Compléments d'information précisant des aspects du traitement utilisé, dont l'insertion dans la
section exécution aurait par trop alourdi le texte, mais qui sont pourtant nécessaires à titre de
justification.

## Références

> Liste des articles et ouvrages dont vous avez fait usage dans votre travail. Donnez toute
l'information nécessaire: nom de l'auteur, titre, provenance (livre ou revue), date, page.

## Glossaire

> Dans un rapport ou un ouvrage relativement important, il sera utile de regrouper tous les
symboles employés et d'en donner la signification précise. Ceci évite au lecteur de devoir
péniblement rechercher dans le texte le sens d'un symbole particulier, mais ne dispense pas
l'auteur de définir chaque symbole au moment où il est introduit dans le texte.

## Table des matières

> Avec les numéros des pages.

## Table des illustrations

> Avec les numéros des pages.