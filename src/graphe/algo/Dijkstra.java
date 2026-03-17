package graphe.algo;

import graphe.model.Arc;
import graphe.model.Graphe;
import graphe.model.Sommet;
import java.util.*;

/**
 * Implémentation de l'algorithme de Dijkstra pour trouver le plus court chemin.
 * <p>
 * Cet algorithme :
 * - Ne supporte que les poids positifs
 * - Utilise une approche gloutonnen
 * - Garantit le plus court chemin
 * - Complexité O(V²)
 * </p>
 * @version 1.0
 * @see BellmanFord
 */
public class Dijkstra {

    /**
     * Trouve le plus court chemin entre deux sommets en utilisant Dijkstra.
     * @param graphe le graphe à analyser
     * @param source le sommet de départ
     * @param destination le sommet d'arrivée
     * @return le résultat contenant le chemin et la distance totale
     * @throws IllegalArgumentException si le graphe contient des poids négatifs
     */
    public static ResultatPlusCourtChemin plusCourtChemin(Graphe graphe, Sommet source, Sommet destination) {

        Map<Sommet, Integer> dist = new HashMap<>();
        Map<Sommet, Sommet> predecesseur = new HashMap<>();
        Set<Sommet> visites = new HashSet<>();

        for (Sommet s : graphe.getSommets()) {
            dist.put(s, Integer.MAX_VALUE);
        }
        dist.put(source, 0);

        while (true) {
            Sommet u = null;
            int distanceMin = Integer.MAX_VALUE;
            for (Sommet s : graphe.getSommets()) {
                if (!visites.contains(s) && dist.get(s) < distanceMin) {
                    distanceMin = dist.get(s);
                    u = s;
                }
            }

            if (u == null || u.equals(destination)) break;
            visites.add(u);

            for (Arc arc : graphe.getArcs()) {
                if (!arc.source().equals(u)) continue;

                Sommet v  = arc.destination();
                int poids = arc.poids();

                if (poids < 0) {
                    throw new IllegalArgumentException(
                        "Dijkstra ne supporte pas les poids négatifs ! (arc "
                        + u.nom() + " -> " + v.nom() + " = " + poids + ")"
                    );
                }

                int nouvelleDistance = dist.get(u) + poids;
                if (!visites.contains(v) && nouvelleDistance < dist.get(v)) {
                    dist.put(v, nouvelleDistance);
                    predecesseur.put(v, u);
                }
            }
        }

        if (dist.get(destination) == Integer.MAX_VALUE) {
            return new ResultatPlusCourtChemin(List.of(), Integer.MAX_VALUE);
        }

        List<Sommet> chemin = new ArrayList<>();
        Sommet courant = destination;
        while (courant != null) {
            chemin.add(courant);
            courant = predecesseur.get(courant);
        }
        Collections.reverse(chemin);

        if (chemin.isEmpty() || !chemin.get(0).equals(source)) {
            return new ResultatPlusCourtChemin(List.of(), Integer.MAX_VALUE);
        }

        return new ResultatPlusCourtChemin(chemin, dist.get(destination));
    }

    /**
     * Construit l'historique des distances minimales calculées à chaque itération
     * de l'algorithme de Dijkstra à partir d'un sommet source.
     * <p>
     * Chaque entrée de la liste est un instantané de la table des distances
     * ({@code sommet -> distance}) après une étape de relaxation. La première
     * entrée correspond à l'initialisation (source à 0, autres sommets à
     * {@link Integer#MAX_VALUE}).
     * </p>
     * <p>
     * Comme pour {@link #plusCourtChemin(Graphe, Sommet, Sommet)}, cette méthode
     * suppose des poids d'arcs non négatifs. Un poids négatif peut produire un
     * historique incorrect.
     * </p>
     *
     * @param graphe le graphe sur lequel exécuter l'algorithme
     * @param source le sommet de départ
     * @return la liste des états successifs de la table des distances
     */
    public static List<Map<Sommet, Integer>> historique(Graphe graphe, Sommet source) {
		List<Map<Sommet, Integer>> historique = new ArrayList<>();
		Map<Sommet, Integer> dist = new HashMap<>();
		Set<Sommet> visites = new HashSet<>();
		for (Sommet s : graphe.getSommets()) dist.put(s, Integer.MAX_VALUE);
			dist.put(source, 0);
	historique.add(new HashMap<>(dist));
		while (true) {
			Sommet u = null;
			int min = Integer.MAX_VALUE;
			for (Sommet s : graphe.getSommets())
				if (!visites.contains(s) && dist.get(s) < min) { min = dist.get(s); u = s; }
			if (u == null) break;
			visites.add(u);
			for (Arc arc : graphe.getArcs()) {
				if (!arc.source().equals(u)) continue;
				Sommet v = arc.destination();
				int nd = dist.get(u) + arc.poids();
				if (!visites.contains(v) && nd < dist.get(v)) dist.put(v, nd);
			}
			historique.add(new HashMap<>(dist));
		}
    return historique;
}
}