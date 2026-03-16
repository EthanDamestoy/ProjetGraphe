package graphe.ihm;

import graphe.Controleur;
import java.awt.*;
import javax.swing.*;

/**
 * Fenêtre principale de l'application.
 * <p>
 * La fenêtre contient :
 * - Un menu "Fichier" pour importer des graphes
 * - Un panneau de visualisation du graphe (PanelGraphe)
 * - Un panneau d'information et de calcul (PanelInfo)
 * </p>
 * @version 1.0
 * @see PanelGraphe
 * @see PanelInfo
 */
public class FrameGraphe extends JFrame {

    private PanelGraphe panelGraphe;
    private PanelInfo   panelInfo;

    public FrameGraphe() {
        this.setTitle("Graphe");
        this.setSize(1850, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        this.setJMenuBar(construireMenuBar());

        this.panelGraphe = new PanelGraphe();
        this.add(this.panelGraphe, BorderLayout.CENTER);

        this.panelInfo = new PanelInfo();
        this.panelInfo.setPreferredSize(new Dimension(620, 1000));
        this.add(this.panelInfo, BorderLayout.EAST);

        this.setVisible(true);
    }
	
    public void rafraichir() {
        this.remove(this.panelGraphe);
        this.remove(this.panelInfo);

        this.panelGraphe = new PanelGraphe();
        this.add(this.panelGraphe, BorderLayout.CENTER);

        this.panelInfo = new PanelInfo();
        this.panelInfo.setPreferredSize(new Dimension(620, 1000));
        this.add(this.panelInfo, BorderLayout.EAST);

        this.revalidate();
        this.repaint();
    }

    private JMenuBar construireMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFichier = new JMenu("Fichier");

        JMenuItem itemImporter = new JMenuItem("Importer un graphe…");
        itemImporter.addActionListener(e -> importerGraphe());
        menuFichier.add(itemImporter);

        menuBar.add(menuFichier);
        return menuBar;
    }

    private void importerGraphe() {
        JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File(System.getProperty("user.dir")));

        fc.setDialogTitle("Choisir un fichier de graphe (.graphe)");
        if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        Controleur.get().chargerGraphe(fc.getSelectedFile().getAbsolutePath());
    }
}