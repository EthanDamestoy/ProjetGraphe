package graphe.ihm;

import graphe.Controleur;
import graphe.algo.BellmanFord;
import graphe.algo.Dijkstra;
import graphe.algo.ResultatPlusCourtChemin;
import graphe.model.Graphe;
import graphe.model.Sommet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 * Panneau d'information et de calcul du plus court chemin.
 * <p>
 * Ce panneau permet :
 * - De sélectionner source et destination
 * - De choisir un algorithme (Dijkstra ou Bellman-Ford)
 * - De calculer et afficher le plus court chemin
 * - De visualiser les étapes du chemin dans un tableau
 * </p>
 * @version 1.0
 * @see Dijkstra
 * @see BellmanFord
 */
public class PanelInfo extends JPanel {

	private final JComboBox<Sommet> cbSource;
	private final JComboBox<String> cbAlgo;
	private final JTextArea zoneResultat;
	private final DefaultTableModel tableModel;
	private final JScrollPane scrollTable;

	public PanelInfo() {
		this.setLayout(new BorderLayout(10, 10));

		Graphe graphe = Controleur.get().getGraphe();
		List<Sommet> sommets = graphe.getSommets();

		JPanel panelControles = new JPanel(new GridLayout(3, 2, 8, 8));

		panelControles.add(new JLabel("Sommet source :"));
		this.cbSource = new JComboBox<>(new DefaultComboBoxModel<>(sommets.toArray(new Sommet[0])));
		this.cbSource.setRenderer((list, value, index, isSelected, cellHasFocus) ->
			new JLabel(value == null ? "" : value.nom().toUpperCase())
		);
		panelControles.add(this.cbSource);

		panelControles.add(new JLabel("Algorithme :"));
		this.cbAlgo = new JComboBox<>(new String[] { "Bellman-Ford", "Dijkstra" });
		panelControles.add(this.cbAlgo);

		JButton btCalculer = new JButton("Calculer");
		btCalculer.addActionListener(e -> this.calculerPlusCourtChemin());
		panelControles.add(btCalculer);
		panelControles.add(new JLabel(""));

		this.add(panelControles, BorderLayout.NORTH);

		this.zoneResultat = new JTextArea();
		this.zoneResultat.setEditable(false);
		this.add(new JScrollPane(this.zoneResultat), BorderLayout.CENTER);

		this.tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};

		JTable tableInfo = new JTable(this.tableModel);
		tableInfo.setRowHeight(26);
		tableInfo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		this.scrollTable = new JScrollPane(tableInfo);
		this.scrollTable.setPreferredSize(new Dimension(520, 280));
		this.add(this.scrollTable, BorderLayout.SOUTH);
	}

	private void calculerPlusCourtChemin() {
		Graphe graphe = Controleur.get().getGraphe();
		Sommet source = (Sommet) this.cbSource.getSelectedItem();
		String algo = (String) this.cbAlgo.getSelectedItem();

		if (source == null || algo == null) {
			this.zoneResultat.setText("Sélection incomplète.");
			this.tableModel.setRowCount(0);
			this.scrollTable.setVisible(false);
			return;
		}

		try {
			String sourceNom = source.nom().toUpperCase();
			StringBuilder texte = new StringBuilder();
			texte.append("Algorithme : ").append(algo).append("\n");
			texte.append("Source : ").append(sourceNom).append("\n\n");
			texte.append("Plus courts chemins vers les autres sommets :\n");

			boolean auMoinsUnChemin = false;
			for (Sommet destination : graphe.getSommets()) {
				if (destination.equals(source)) continue;

				ResultatPlusCourtChemin resultat = "Dijkstra".equals(algo)
					? Dijkstra.plusCourtChemin(graphe, source, destination)
					: BellmanFord.plusCourtChemin(graphe, source, destination);

				String destinationNom = destination.nom().toUpperCase();
				if (resultat.distanceTotale() == Integer.MAX_VALUE || resultat.chemin().isEmpty()) {
					texte.append("- Vers ").append(destinationNom).append(" : aucun chemin\n");
					continue;
				}

				auMoinsUnChemin = true;
				String cheminLisible = resultat.chemin().stream()
					.map(s -> s.nom().toUpperCase())
					.collect(Collectors.joining(" → "));
				texte.append("- Vers ").append(destinationNom)
					.append(" : distance ").append(resultat.distanceTotale())
					.append(" | chemin ").append(cheminLisible)
					.append("\n");
			}

			if (!auMoinsUnChemin) {
				texte.append("Aucun chemin trouvé depuis la source.\n");
			}

			this.zoneResultat.setText(texte.toString());

			this.mettreAJourTableChemin(graphe, algo);
			this.scrollTable.setVisible(true);
		} catch (IllegalArgumentException | IllegalStateException ex) {
			this.zoneResultat.setText("Erreur : " + ex.getMessage());
			this.tableModel.setRowCount(0);
			this.scrollTable.setVisible(false);
		}
	}

	private void mettreAJourTableChemin(Graphe graphe, String algo) {
		this.tableModel.setRowCount(0);
		while (this.tableModel.getColumnCount() > 0)
			this.tableModel.setColumnCount(this.tableModel.getColumnCount() - 1);

		Sommet source = (Sommet) this.cbSource.getSelectedItem();
		List<Map<Sommet, Integer>> hist = "Dijkstra".equals(algo)
			? Dijkstra.historique(graphe, source)
			: BellmanFord.historique(graphe, source);

		List<Sommet> sommets = graphe.getSommets();
		this.tableModel.addColumn("");
		for (Sommet s : sommets) this.tableModel.addColumn("d(" + s.nom().toUpperCase() + ")");

		for (int i = 0; i < hist.size(); i++) {
			Object[] row = new Object[sommets.size() + 1];
			row[0] = (i == 0) ? "Initialisation" : "Itération " + i;
			for (int j = 0; j < sommets.size(); j++) {
				int val = hist.get(i).getOrDefault(sommets.get(j), Integer.MAX_VALUE);
				row[j + 1] = (val == Integer.MAX_VALUE) ? "+∞" : val;
			}
			this.tableModel.addRow(row);
		}
	}
}
