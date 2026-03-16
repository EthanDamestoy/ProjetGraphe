package graphe.ihm;

import graphe.Controleur;
import graphe.algo.BellmanFord;
import graphe.algo.Dijkstra;
import graphe.algo.ResultatPlusCourtChemin;
import graphe.model.Arc;
import graphe.model.Graphe;
import graphe.model.Sommet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Optional;
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
 * @author Étudiant
 * @version 1.0
 * @see Dijkstra
 * @see BellmanFord
 */
public class PanelInfo extends JPanel {

	private final JComboBox<Sommet> cbSource;
	private final JComboBox<Sommet> cbDestination;
	private final JComboBox<String> cbAlgo;
	private final JTextArea zoneResultat;
	private final DefaultTableModel tableModel;

	public PanelInfo() {
		this.setLayout(new BorderLayout(10, 10));

		Graphe graphe = Controleur.get().getGraphe();
		List<Sommet> sommets = graphe.getSommets();

		JPanel panelControles = new JPanel(new GridLayout(4, 2, 8, 8));

		panelControles.add(new JLabel("Sommet source :"));
		this.cbSource = new JComboBox<>(new DefaultComboBoxModel<>(sommets.toArray(new Sommet[0])));
		this.cbSource.setRenderer((list, value, index, isSelected, cellHasFocus) ->
			new JLabel(value == null ? "" : value.nom().toUpperCase())
		);
		panelControles.add(this.cbSource);

		panelControles.add(new JLabel("Sommet destination :"));
		this.cbDestination = new JComboBox<>(new DefaultComboBoxModel<>(sommets.toArray(new Sommet[0])));
		this.cbDestination.setRenderer((list, value, index, isSelected, cellHasFocus) ->
			new JLabel(value == null ? "" : value.nom().toUpperCase())
		);
		panelControles.add(this.cbDestination);

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

		this.tableModel = new DefaultTableModel(
			new Object[][] {},
			new String[] { "Étape", "Sommet", "Distance", "Arc" }
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable tableInfo = new JTable(this.tableModel);
		tableInfo.setRowHeight(26);
		tableInfo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableInfo.getColumnModel().getColumn(0).setPreferredWidth(60);
		tableInfo.getColumnModel().getColumn(1).setPreferredWidth(80);
		tableInfo.getColumnModel().getColumn(2).setPreferredWidth(80);
		tableInfo.getColumnModel().getColumn(3).setPreferredWidth(300);
		
		JScrollPane scrollTable = new JScrollPane(tableInfo);
		scrollTable.setPreferredSize(new Dimension(520, 280));
		this.add(scrollTable, BorderLayout.SOUTH);
	}

	private void calculerPlusCourtChemin() {
		Graphe graphe = Controleur.get().getGraphe();
		Sommet source = (Sommet) this.cbSource.getSelectedItem();
		Sommet destination = (Sommet) this.cbDestination.getSelectedItem();
		String algo = (String) this.cbAlgo.getSelectedItem();

		if (source == null || destination == null || algo == null) {
			this.zoneResultat.setText("Sélection incomplète.");
			this.tableModel.setRowCount(0);
			return;
		}

		if (source.equals(destination)) {
			String sommetNom = source.nom().toUpperCase();
			this.zoneResultat.setText(
				"Algorithme : " + algo + "\n"
				+ "Source : " + sommetNom + "\n"
				+ "Destination : " + sommetNom + "\n\n"
				+ "Distance totale : 0\n"
				+ "Chemin : " + sommetNom
			);

			this.mettreAJourTableChemin(graphe, algo, List.of(source));
			return;
		}

		try {
			ResultatPlusCourtChemin resultat;

			if ("Dijkstra".equals(algo)) {
				resultat = Dijkstra.plusCourtChemin(graphe, source, destination);
			} else {
				resultat = BellmanFord.plusCourtChemin(graphe, source, destination);
			}

			String sourceNom = source.nom().toUpperCase();
			String destinationNom = destination.nom().toUpperCase();

			if (resultat.distanceTotale() == Integer.MAX_VALUE || resultat.chemin().isEmpty()) {
				this.zoneResultat.setText(
					"Algorithme : " + algo + "\n"
					+ "Source : " + sourceNom + "\n"
					+ "Destination : " + destinationNom + "\n\n"
					+ "Aucun chemin trouvé."
				);

				this.tableModel.setRowCount(0);
				return;
			}

			String cheminLisible = resultat.chemin().stream()
				.map(s -> s.nom().toUpperCase())
				.collect(Collectors.joining(" → "));

			this.zoneResultat.setText(
				"Algorithme : " + algo + "\n"
				+ "Source : " + sourceNom + "\n"
				+ "Destination : " + destinationNom + "\n\n"
				+ "Distance totale : " + resultat.distanceTotale() + "\n"
				+ "Chemin : " + cheminLisible
			);

			this.mettreAJourTableChemin(graphe, algo, resultat.chemin());
		} catch (IllegalArgumentException | IllegalStateException ex) {
			this.zoneResultat.setText("Erreur : " + ex.getMessage());
			this.tableModel.setRowCount(0);
		}
	}

	private void mettreAJourTableChemin(
		Graphe graphe,
		String algo,
		List<Sommet> chemin
	) {
		this.tableModel.setRowCount(0);

		int distanceCumulee = 0;
		for (int i = 0; i < chemin.size(); i++) {
			Sommet courant = chemin.get(i);
			String arcTexte = "-";

			if (i > 0) {
				Sommet precedent = chemin.get(i - 1);
				Optional<Arc> arcTrouve = graphe.getArcs().stream()
					.filter(a -> a.source().equals(precedent) && a.destination().equals(courant))
					.findFirst();

				if (arcTrouve.isPresent()) {
					Arc arc = arcTrouve.get();
					distanceCumulee += arc.poids();
					arcTexte = precedent.nom().toUpperCase() + " → " + courant.nom().toUpperCase() + " (" + arc.poids() + ")";
				} else {
					arcTexte = precedent.nom().toUpperCase() + " → " + courant.nom().toUpperCase();
				}
			}

			this.tableModel.addRow(new Object[] {
				i + 1,
				courant.nom().toUpperCase(),
				distanceCumulee,
				arcTexte
			});
		}
	}
}
