# ProjetGraphe

Application Java de visualisation et d'analyse de graphes orientés pondérés.  
Implémente Dijkstra et Bellman-Ford avec une interface Swing et rendu GraphStream.

## Lancer

**Linux / macOS**
```bash
chmod +x run.sh && ./run.sh
```

**Windows**
```batch
run.bat
```

## Utilisation

1. **Importer un graphe** : Fichier → Importer un graphe → sélectionner un `.txt`
2. **Calculer un chemin** : choisir source, destination, algorithme, puis Calculer
3. Le chemin s'affiche dans la zone de texte et dans le tableau étape par étape (sommet, distance cumulée, arc emprunté)

### Format de fichier
```
# sommets
a, b, c, d

# arcs
a b 5
b c 3
```

Les noms de sommets sont insensibles à la casse. Les poids sont des entiers (négatifs acceptés pour Bellman-Ford).

## Algorithmes

| Algorithme | Complexité | Poids négatifs | Détecte cycle négatif |
|---|---|---|---|
| Dijkstra | O(V²) | ✗ | ✗ |
| Bellman-Ford | O(V × E) | ✓ | ✓ |

## Architecture (MVC)

```
graphe/
├── Controleur.java          # Singleton — orchestre modèle et vue
├── model/                   # Données
│   ├── Graphe.java          # Liste de sommets + arcs, export GraphStream
│   ├── Sommet.java          # Record — identifié par son nom
│   └── Arc.java             # Record — source, destination, poids
├── algo/                    # Algorithmes
│   ├── Dijkstra.java
│   ├── BellmanFord.java
│   └── ResultatPlusCourtChemin.java  # Record — chemin + distance totale
├── lecture/
│   └── Lecture.java         # Parser fichier texte
└── ihm/
    ├── FrameGraphe.java     # Fenêtre principale + menu import
    ├── PanelGraphe.java     # Rendu GraphStream (auto-layout)
    └── PanelInfo.java       # Sélection algo, résultat, tableau étapes
```

## Javadoc

```bash
# Linux / macOS
javadoc -d docs -sourcepath src -subpackages graphe \
  -cp "lib/gs-core-2.0.jar:lib/gs-ui-swing-2.0.jar" \
  -encoding UTF-8 -charset UTF-8 \
  -windowtitle "ProjetGraphe" -doctitle "ProjetGraphe - Visualisation de Graphes"

# Windows (remplacer : par ; dans -cp)
javadoc -d docs -sourcepath src -subpackages graphe ^
  -cp "lib/gs-core-2.0.jar;lib/gs-ui-swing-2.0.jar" ^
  -encoding UTF-8 -charset UTF-8 ^
  -windowtitle "ProjetGraphe" -doctitle "ProjetGraphe - Visualisation de Graphes"
```

La doc générée se trouve dans `docs/index.html`.

## Stack

Java 17+ · GraphStream 2.0 · Swing