package graphe;

import graphe.ihm.FrameGraphe;
import graphe.lecture.Lecture;
import graphe.model.Arc;
import graphe.model.Graphe;
import graphe.model.Sommet;
import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur principal de l'application de visualisation de graphes.
 * <p>
 * Le Controleur gère :
 * - L'instance unique de l'application (singleton)
 * - Le graphe courant
 * - L'interface graphique
 * - Le chargement de graphes depuis des fichiers
 * </p>
 * @author Étudiant
 * @version 1.0
 */
public class Controleur {

	/** Instance unique du contrôleur (pattern singleton) */
	private static Controleur instance;	

	/** Fenêtre principale de l'application */
	private FrameGraphe frameGraphe;
	
	/** Graphe actuellement affiché */
	private Graphe graphe;

	/**
	 * Constructeur du Controleur.
	 * Initialise l'application avec un graphe par défaut et crée l'interface.
	 */
	public Controleur()
	{
		instance = this;
		this.graphe = this.grapheParDefaut();

		this.frameGraphe = new FrameGraphe();
	}

    /**
     * Charge un graphe depuis un fichier.
     * <p>
     * Le fichier doit respecter le format :
     * <pre>
     * # sommets
     * a, b, c, d
     * 
     * # arcs
     * a b 5
     * b c 3
     * </pre>
     * </p>
     * @param cheminFichier le chemin absolu du fichier à charger
     */
    public void chargerGraphe(String cheminFichier) {
        try {
            Graphe grapheCharge = Lecture.lireFichier(cheminFichier);
            if (grapheCharge != null) {
                this.graphe = grapheCharge;
                this.frameGraphe.rafraichir();
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement du graphe : " + e.getMessage());
        }
	}

	/**
	 * Crée un graphe par défaut pour la démonstration.
	 * @return un graphe avec 5 sommets et 10 arcs
	 */
	private Graphe grapheParDefaut()
	{
		System.setProperty("org.graphstream.ui", "swing");

		Sommet s = new Sommet("s");
        Sommet a = new Sommet("a");
        Sommet b = new Sommet("b");
        Sommet c = new Sommet("c");
        Sommet d = new Sommet("d");

        List<Sommet> sommets = new ArrayList<>();
        sommets.add(s);
        sommets.add(a);
        sommets.add(b);
        sommets.add(c);
        sommets.add(d);

        List<Arc> arcs = new ArrayList<>();
        arcs.add(new Arc(s, a, 3));
        arcs.add(new Arc(s, c, 5));
        arcs.add(new Arc(a, c, 1));
        arcs.add(new Arc(c, a, 1));
        arcs.add(new Arc(a, b, 5));
        arcs.add(new Arc(c, b, 5));
        arcs.add(new Arc(c, d, 5));
        arcs.add(new Arc(d, b, 3));
        arcs.add(new Arc(b, d, 1));
        arcs.add(new Arc(d, s, 3));

        return new Graphe(sommets, arcs);
	}

	public static void main(String[] args) {
		new Controleur();	
	}

	/**
	 * Récupère l'instance unique du contrôleur.
	 * @return l'instance du contrôleur
	 */
	public static Controleur get() {
		return instance;
	}

	/**
	 * Récupère la fenêtre principale.
	 * @return la fenêtre de l'application
	 */
	public FrameGraphe getFrameGraphe() {
		return frameGraphe;
	}

	/**
	 * Récupère le graphe courant.
	 * @return le graphe actuellement affiché
	 */
	public Graphe getGraphe() {
		return graphe;
	}
}
