package graphe.lecture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import graphe.model.Arc;
import graphe.model.Graphe;
import graphe.model.Sommet;

/**
 * Utilitaire pour lire et charger des graphes depuis des fichiers.
 * <p>
 * Format de fichier attendu :
 * <pre>
 * # sommets
 * a, b, c, d
 * 
 * # arcs
 * a b 5
 * b c 3
 * </pre>
 * </p>
 * @version 1.0
 */
public class Lecture {

    /**
     * Lit un graphe depuis un fichier.
     * <p>
     * Retourne null en cas d'erreur (fichier non trouvé, format invalide, etc.)
     * Les erreurs sont affichées dans la console.
     * </p>
     * @param cheminFichier le chemin du fichier à lire
     * @return le graphe lu, ou null si erreur
     */
    public static Graphe lireFichier(String cheminFichier) {

        List<Sommet> sommets = new ArrayList<>();
        List<Arc> arcs = new ArrayList<>();
        Map<String, Sommet> indexSommets = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {

            String ligne;
            String section = null;

            while ((ligne = reader.readLine()) != null) {
                ligne = ligne.trim();

                if (ligne.isEmpty() || ligne.startsWith("#")) {
                    if (ligne.toLowerCase().contains("sommet"))
                        section = "sommets";
                    else if (ligne.toLowerCase().contains("arc"))
                        section = "arcs";
                    continue;
                }

                if ("sommets".equals(section)) {

                    for (String nom : ligne.split(",")) {
                        nom = nom.trim().toLowerCase();

                        if (!nom.isEmpty()) {
                            Sommet s = new Sommet(nom);
                            sommets.add(s);
                            indexSommets.put(nom, s);
                        }
                    }

                } else if ("arcs".equals(section)) {

                    String[] parts = ligne.split("\\s+");

                    if (parts.length != 3) {
                        System.out.println("Arc invalide : \"" + ligne + "\"");
                        continue;
                    }

                    String nomSrc = parts[0].toLowerCase();
                    String nomDst = parts[1].toLowerCase();

                    int poids;

                    try {
                        poids = Integer.parseInt(parts[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("Poids non entier : \"" + parts[2] + "\"");
                        continue;
                    }

                    Sommet src = indexSommets.get(nomSrc);
                    Sommet dst = indexSommets.get(nomDst);

                    if (src == null) {
                        System.out.println("Sommet source inconnu : " + nomSrc);
                        continue;
                    }

                    if (dst == null) {
                        System.out.println("Sommet destination inconnu : " + nomDst);
                        continue;
                    }

                    arcs.add(new Arc(src, dst, poids));
                }
            }

            if (sommets.isEmpty()) {
                System.out.println("Erreur : Le fichier ne contient aucun sommet");
                return null;
            }
            
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            return null;
        }

        return new Graphe(sommets, arcs);
    }
}
