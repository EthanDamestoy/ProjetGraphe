package graphe.ihm;

import java.awt.GridLayout;

import javax.swing.JPanel;

import org.graphstream.graph.Graph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import graphe.Controleur;

/**
 * Panneau d'affichage du graphe.
 * <p>
 * Affiche le graphe courant en utilisant la bibliothèque GraphStream.
 * Le graphe est automatiquement layouté avec un algorithme d'auto-layout.
 * </p>
 * @author Étudiant
 * @version 1.0
 * @see FrameGraphe
 */
public class PanelGraphe extends JPanel
{
	public PanelGraphe()
	{
		this.setLayout(new GridLayout());

		Graph graph = Controleur.get().getGraphe().toGraphStream(false);
		
		SwingViewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
		
		ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false);
		this.add(viewPanel);
	}
	
}
