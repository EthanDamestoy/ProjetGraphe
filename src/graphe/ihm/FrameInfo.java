package graphe.ihm;

import javax.swing.JFrame;

/**
 * Fenêtre secondaire pour afficher les informations du plus court chemin.
 * <p>
 * Cette fenêtre n'est plus utilisée dans la version courante.
 * Les informations sont affichées dans PanelInfo au sein de FrameGraphe.
 * </p>
 * @version 1.0
 * @see PanelInfo
 * @see FrameGraphe
 */
public class FrameInfo extends JFrame {

	private PanelInfo panelInfo;

	public FrameInfo() {
		this.setTitle("Informations - Plus court chemin");
		this.setSize(500, 350);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocation(100, 100);

		this.panelInfo = new PanelInfo();
		this.add(this.panelInfo);

		this.setVisible(true);
	}
}

