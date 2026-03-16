# ProjetGraphe - Visualisation et Analyse de Graphes

## 📋 Description

ProjetGraphe est une application Java de visualisation et d'analyse de graphes orientés pondérés. Elle permet de :
- Visualiser des graphes en temps réel avec la bibliothèque GraphStream
- Calculer le plus court chemin entre deux sommets
- Utiliser deux algorithmes différents : **Dijkstra** et **Bellman-Ford**
- Importer des graphes depuis des fichiers texte
- Détailler les étapes du calcul dans une interface claire

## 🏗️ Architecture

Le projet suit une architecture **MVC (Modèle-Vue-Contrôleur)** organisée en packages :

```
graphe/
├── Controleur.java          # Singleton - Gestion de l'app et liaison modèle-vue
├── model/                   # Modèles de données
│   ├── Graphe.java         # Représentation du graphe
│   ├── Sommet.java         # Nœud du graphe
│   └── Arc.java            # Arête dirigée avec poids
├── algo/                    # Algorithmes de plus court chemin
│   ├── Dijkstra.java       # Algorithme de Dijkstra (poids positifs)
│   ├── BellmanFord.java    # Algorithme de Bellman-Ford (flexib)
│   └── ResultatPlusCourtChemin.java  # Résultat encapsulé
├── lecture/                 # Lecture de fichiers
│   └── Lecture.java        # Parser pour les fichiers graphe
└── ihm/                     # Interface graphique
    ├── FrameGraphe.java    # Fenêtre principale + menu
    ├── PanelGraphe.java    # Affichage du graphe (GraphStream)
    └── PanelInfo.java      # Sélection algo + résultats
```

## 🚀 Utilisation

### Lancer l'application avec les scripts (⭐ RECOMMANDÉ)

#### Linux / macOS / WSL
```bash
# Rendre le script exécutable (une seule fois)
chmod +x run.sh

# Compiler et lancer
./run.sh

# Autres options
./run.sh -clean      # Nettoyer et relancer
./run.sh -compile    # Compiler uniquement
./run.sh -javadoc    # Compiler + générer Javadoc + lancer
```

#### Windows
```batch
# Compiler et lancer
run.bat

# Autres options
run.bat -clean       # Nettoyer et relancer
run.bat -compile     # Compiler uniquement
run.bat -javadoc     # Compiler + générer Javadoc + lancer
```

**Avantages des scripts :**
- ✅ Génèrent dynamiquement la liste des fichiers à compiler
- ✅ Gèrent les chemins automatiquement (Windows/Linux/macOS)
- ✅ Vérifient Java et les dépendances
- ✅ Lancent avec un seul commande

**Pour plus d'options et de détails**, voir [SCRIPTS.md](SCRIPTS.md)

### Lancer l'application manuellement
```bash
cd ProjetGraphe
javac -d bin -cp lib/gs-core-2.0.jar:lib/gs-ui-swing-2.0.jar src/graphe/**/*.java src/graphe/*.java
java -cp bin:lib/gs-core-2.0.jar:lib/gs-ui-swing-2.0.jar graphe.Controleur
```

### Importer un graphe
1. Cliquer sur **Fichier → Importer un graphe**
2. Sélectionner un fichier `.txt`
3. Le graphe s'affiche automatiquement

### Format de fichier supporté
```
# sommets
a, b, c, d, e

# arcs
a b 5
a c 2
b d 4
c d 7
d e 1
```

### Calculer le plus court chemin
1. Sélectionner un sommet **source**
2. Sélectionner un sommet **destination**
3. Choisir un **algorithme** (Dijkstra ou Bellman-Ford)
4. Cliquer sur **Calculer**

## 📊 Algorithmes implémentés

### 🔵 Dijkstra
- Complexité : O(V²)
- Supporte : poids positifs uniquement
- Garanti : oui
- Cas d'usage : graphes denses avec poids positifs

### 🔴 Bellman-Ford
- Complexité : O(V × E)
- Supporte : poids négatifs
- Détecte : cycles de poids négatif
- Cas d'usage : graphes avec poids négatifs ou vérification de cycles

## 📁 Fichiers d'exemple

Des fichiers de graphes d'exemple sont fournis dans `src/fichier/` :
- `graphe1.txt`, `graphe2.txt`, etc.

## 🛠️ Technologies utilisées

- **Java 17+**
- **GraphStream 2.0** - Visualisation de graphes
- **Swing** - Interface graphique

## 📝 Javadoc

La documentation complète est disponible en générant la Javadoc :
```bash
javadoc -d docs -sourcepath src -subpackages graphe
```

## 🎯 Améliorations futures possibles

- [ ] Exporter les graphes au format image
- [ ] Support des graphes non orientés
- [ ] Onglets pour plusieurs graphes simultanément
- [ ] Éditeur visuel pour créer/modifier les graphes
- [ ] Algorithmes supplémentaires (Floyd-Warshall, A*)
- [ ] Détection de tous les plus courts chemins (s'il y en a plusieurs)

## 📄 Licence

Projet éducatif - Libre d'usage

