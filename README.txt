Ce qui suit est une description des différentes composantes du projet:


- Fichiers de la librairie fournie utilisés:

Complexe.java
ComplexeCartesien.java
ComplexePolaire.java
FFTCplx.java
Son.java


- Fichiers .java crées:

Cetaces.java

    -> Définition des différentes espèces sur lequelles la reconnaissance va être faite.


Activation.java

    -> Classe abstraite servant à pouvoir utiliser de manière générique les fonctions d'activation.


Heaviside.java

    -> Définition de la fonction d'activation Seuil.


Sigmoid.java

    -> Définition de la fonction d'activation Sigmoïde.


Precision.java

    -> Définit une égalité à un epsilon fixé près, afin de limiter l'influence des erreurs de calculs se propageant à cause des nombres à virgule flottante.


Matrix.java

    -> Fonctions de base sur les matrices (e.g affichage, somme, multiplication, comparaison, etc).


Propagation.java

    -> Fonctions pour la propagation des entrées dans le réseau de neurones. Modification des matrices de poids et biais.


Sampling.java

    -> Import d'un fichier son, découpage en tronçons de taille une puissance de 2, et application de la FFT sur chaque tronçons, afin de les fournir comme entrées au réseau de neurones.


TestMatrix.java

    -> Vérification des fonctions de bases sur les matrices.


SavingBinary.java

    -> Savegarde et chargement de matrices de double dans des fichiers binaires. Utile pour sauvegarder/charger les matrices de poids et de biais, ainsi que les questions apprises avec leur réponse.


SavingPlainText.java

    -> Obsolète, fait la même chose que SavingBinary.java en fichiers .txt, ce qui crée des fichiers de plus grande taille, et est plus lent à fonctionner.


NeuronNetwork.java

    -> Création d'un réseau de neurones ayant les paramètres donnés (e.g nombre de neurones, fonction d'activation, vitesse d'apprentissage, etc). Contient aussi les méthodes d'apprentissage, reconnaissance, sauvegarde et de chargement d'un réseau de neurones.


TestNeurons.java

    -> Vérification du bon fonctionnement d'un réseau neurone avec un perceptron. Application aux portes logiques AND, OR et XOR. Sauvegarde et chargements aussi vérifiés.


SaveBeluga.java

    -> Apprentissage et sauvegarde d'un perceptron cherchant à reconnaître les Bélugas. Peut prendre environ 15s pour s'exécuter.


SaveAllSpecies.java

    -> Apprentissage et sauvegarde d'un réseau de 7 neurones cherchant à reconnaître chaque espèce définie. Peut prendre environ 30s pour s'exécuter.


LoadBeluga.java

    -> Chargement du réseau de neurone enregistré lors d'une exécution de SaveBeluga.java, et vérification sur des exemples.


LoadAllSpecies.java

    -> Chargement du réseau de neurone enregistré lors d'une exécution de SaveAllSpecies.java, et vérification sur des exemples.


