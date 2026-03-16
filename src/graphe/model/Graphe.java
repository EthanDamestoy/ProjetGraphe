package graphe.model;

import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * Représente un graphe orienté pondéré.
 * <p>
 * Un graphe est composé de :
 * - Une liste de sommets (nœuds)
 * - Une liste d'arcs (arêtes orientées avec poids)
 * </p>
 * @author Étudiant
 * @version 1.0
 * @see Sommet
 * @see Arc
 */
public class Graphe {

	/** Liste des sommets du graphe */
	private List<Sommet> sommets;
	
	/** Liste des arcs du graphe */
	private List<Arc> arcs;

	/**
	 * Constructeur du graphe.
	 * @param sommets la liste des sommets
	 * @param arcs la liste des arcs
	 */
	public Graphe(List<Sommet> sommets, List<Arc> arcs)
	{
		this.sommets = sommets;
		this.arcs = arcs;
	}

	/**
	 * Récupère la liste des sommets.
	 * @return la liste des sommets du graphe
	 */
	public List<Sommet> getSommets() {
		return sommets;
	}

	/**
	 * Récupère la liste des arcs.
	 * @return la liste des arcs du graphe
	 */
	public List<Arc> getArcs() {
		return arcs;
	}

	/**
	 * Convertit le graphe en objet GraphStream pour visualisation.
	 * @param displayShortestPath inutilisé (pour évolution future)
	 * @return un graphe GraphStream prêt pour l'affichage
	 */
	public Graph toGraphStream(boolean displayShortestPath)
	{
		Graph graph = new SingleGraph("Graphe");
		graph.setAttribute("ui.stylesheet", "node { size: 40px; fill-color: #4CAF50; text-mode: normal; text-alignment: center; } edge { text-mode: normal; }");
		
		for (Sommet sommet : sommets) {
			Node node = graph.addNode(sommet.nom().toUpperCase());
			node.setAttribute("ui.label", sommet.nom().toUpperCase());
			node.setAttribute("ui.class", "node");
		}
		
		for (Arc arc : arcs) {
			String edgeId = arc.source().nom().toUpperCase() + "-" + arc.destination().nom().toUpperCase();
			Edge edge = graph.addEdge(edgeId, arc.source().nom().toUpperCase(), arc.destination().nom().toUpperCase(), true);
			edge.setAttribute("ui.label", String.valueOf(arc.poids()));
		}
		
		return graph;
	}
}
