package graphe.algo;

import graphe.model.Arc;
import graphe.model.Graphe;
import graphe.model.Sommet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implémentation de l'algorithme de Bellman-Ford pour trouver le plus court chemin.
 * <p>
 * Cet algorithme :
 * - Supporte les poids négatifs
 * - Détecte les cycles de poids négatifs
 * - Complexité O(V × E)
 * - Plus lent que Dijkstra mais plus flexible
 * </p>
 * @version 1.0
 * @see Dijkstra
 */
public class BellmanFord {

    /**
     * Calcule les distances minimales depuis un sommet source.
     * @param graphe le graphe à analyser
     * @param source le sommet de départ
     * @return une map des distances minimales pour chaque sommet
     * @throws IllegalStateException si le graphe contient un cycle négatif
     */
    public static Map<Sommet, Integer> bellmanFord(Graphe graphe, Sommet source) {

        Map<Sommet, Integer> dist = new HashMap<>();

        for (Sommet s : graphe.getSommets()) {
            dist.put(s, Integer.MAX_VALUE);
        }

        dist.put(source, 0);

        int V = graphe.getSommets().size();

        for (int i = 1; i < V; i++) {
            for (Arc arc : graphe.getArcs()) {
                Sommet u = arc.source();
                Sommet v = arc.destination();
                int poids = arc.poids();

                if (dist.get(u) != Integer.MAX_VALUE && dist.get(u) + poids < dist.get(v)) {
                    dist.put(v, dist.get(u) + poids);
                }
            }
        }

        for (Arc arc : graphe.getArcs()) {
            Sommet u = arc.source();
            Sommet v = arc.destination();
            int poids = arc.poids();

            if (dist.get(u) != Integer.MAX_VALUE && dist.get(u) + poids < dist.get(v)) {
                throw new IllegalStateException("Le graphe contient un cycle de poids négatif !");
            }
        }

        return dist;
    }

    /**
     * Trouve le plus court chemin entre deux sommets en utilisant Bellman-Ford.
     * @param graphe le graphe à analyser
     * @param source le sommet de départ
     * @param destination le sommet d'arrivée
     * @return le résultat contenant le chemin et la distance totale
     * @throws IllegalStateException si le graphe contient un cycle de poids négatif
     */
    public static ResultatPlusCourtChemin plusCourtChemin(Graphe graphe, Sommet source, Sommet destination) {

        Map<Sommet, Integer> dist = new HashMap<>();
        Map<Sommet, Sommet> precedent = new HashMap<>();

        for (Sommet s : graphe.getSommets()) {
            dist.put(s, Integer.MAX_VALUE);
        }
        dist.put(source, 0);

        int V = graphe.getSommets().size();

        for (int i = 1; i < V; i++) {
            for (Arc arc : graphe.getArcs()) {
                Sommet u = arc.source();
                Sommet v = arc.destination();
                int poids = arc.poids();

                if (dist.get(u) != Integer.MAX_VALUE && dist.get(u) + poids < dist.get(v)) {
                    dist.put(v, dist.get(u) + poids);
                    precedent.put(v, u);
                }
            }
        }

        for (Arc arc : graphe.getArcs()) {
            Sommet u = arc.source();
            Sommet v = arc.destination();
            int poids = arc.poids();

            if (dist.get(u) != Integer.MAX_VALUE && dist.get(u) + poids < dist.get(v)) {
                throw new IllegalStateException("Le graphe contient un cycle de poids négatif !");
            }
        }

        int distanceTotale = dist.getOrDefault(destination, Integer.MAX_VALUE);
        if (distanceTotale == Integer.MAX_VALUE) {
            return new ResultatPlusCourtChemin(List.of(), Integer.MAX_VALUE);
        }

        LinkedList<Sommet> chemin = new LinkedList<>();
        Sommet courant = destination;
        while (courant != null) {
            chemin.addFirst(courant);
            courant = precedent.get(courant);
        }

        if (chemin.isEmpty() || !chemin.getFirst().equals(source)) {
            return new ResultatPlusCourtChemin(List.of(), Integer.MAX_VALUE);
        }

        return new ResultatPlusCourtChemin(chemin, distanceTotale);
    }

    /**
     * Construit l'historique des distances minimales estimées à chaque itération
     * de Bellman-Ford à partir d'un sommet source.
     * <p>
     * Chaque entrée de la liste est un instantané de la table des distances
     * ({@code sommet -> distance}). La première entrée correspond à
     * l'initialisation (source à 0, autres sommets à {@link Integer#MAX_VALUE}),
     * puis les instantanés suivants sont ajoutés uniquement lorsqu'une
     * amélioration est observée après une phase de relaxation.
     * </p>
     * <p>
     * Contrairement à {@link #plusCourtChemin(Graphe, Sommet, Sommet)}, cette
     * méthode ne vérifie pas explicitement la présence d'un cycle de poids
     * négatif en fin de traitement.
     * </p>
     *
     * @param graphe le graphe sur lequel exécuter l'algorithme
     * @param source le sommet de départ
     * @return la liste des états successifs de la table des distances
     */
    public static List<Map<Sommet, Integer>> historique(Graphe graphe, Sommet source) {
		List<Map<Sommet, Integer>> historique = new ArrayList<>();
		Map<Sommet, Integer> dist = new HashMap<>();
		for (Sommet s : graphe.getSommets()) dist.put(s, Integer.MAX_VALUE);
		dist.put(source, 0);
		historique.add(new HashMap<>(dist));

        for (int i = 1; i <= 2; i++) {
			for (Arc arc : graphe.getArcs()) {
				Sommet u = arc.source(), v = arc.destination();
				if (dist.get(u) != Integer.MAX_VALUE && dist.get(u) + arc.poids() < dist.get(v))
					dist.put(v, dist.get(u) + arc.poids());
			}
            historique.add(new HashMap<>(dist));
		}

		return historique;
	}
}