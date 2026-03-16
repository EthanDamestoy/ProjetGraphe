package graphe.algo;

import graphe.model.Sommet;
import java.util.List;

/**
 * Encapsule le résultat d'un algorithme de plus court chemin.
 * <p>
 * Contient :
 * - Le chemin (liste ordonnée de sommets)
 * - La distance totale du chemin
 * </p>
 * @author Étudiant
 * @version 1.0
 * @param chemin la liste des sommets formant le plus court chemin
 * @param distanceTotale la somme des poids des arcs du chemin
 * @see Dijkstra
 * @see BellmanFord
 */
public record ResultatPlusCourtChemin(List<Sommet> chemin, int distanceTotale) {}
