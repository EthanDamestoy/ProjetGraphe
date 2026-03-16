package graphe.model;

/**
 * Représente un arc (arête orientée) entre deux sommets.
 * <p>
 * Un arc est caractérisé par :
 * - Un sommet source
 * - Un sommet destination
 * - Un poids (can be negative for some algorithms)
 * </p>
 * @version 1.0
 * @param source le sommet de départ de l'arc
 * @param destination le sommet d'arrivée de l'arc
 * @param poids le poids associé à l'arc
 * @see Sommet
 */
public record Arc(Sommet source, Sommet destination, int poids) {}
