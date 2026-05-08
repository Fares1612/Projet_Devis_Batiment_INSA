import java.util.ArrayList;

/**
 * Représente un étage du bâtiment.
 * Cette classe fait le lien entre la structure d'Idrique et les appartements de Farès.
 */
public class Niveau {
    
    // Attributs selon le diagramme UML
    private int idNiveau;
    private int nbreAppartements;
    private double hauteurSousPlafond; // Hauteur commune à toutes les pièces de l'étage
    
    // Liste des appartements présents à cet étage (Relation "contient")
    private ArrayList<Appartement> listeAppartements;

    /**
     * Constructeur d'un niveau.
     */
    public Niveau(int idNiveau, int nbreAppartements, double hauteurSousPlafond) {
        this.idNiveau = idNiveau;
        this.nbreAppartements = nbreAppartements;
        this.hauteurSousPlafond = hauteurSousPlafond;
        this.listeAppartements = new ArrayList<>();
    }

    /**
     * Ajoute un appartement à l'étage.
     */
    public void ajouterAppartement(Appartement app) {
        this.listeAppartements.add(app);
    }

    /**
     * Calcule le montant total du devis pour ce niveau.
     * Somme les devis de chaque appartement.
     */
    public double devisNiveau() {
        double total = 0;
        for (Appartement app : listeAppartements) {
            // Ici, on appelle la méthode de l'étudiant 2 (Farès)
            // total += app.devisAppartement();
        }
        return total;
    }

    /**
     * Calcule la surface d'un revêtement spécifique sur tout l'étage.
     */
    public double surfaceRevetementNiveau(int idRevetement) {
        double surfaceTotale = 0;
        for (Appartement app : listeAppartements) {
            // surfaceTotale += app.surfaceRevetementAppart(idRevetement);
        }
        return surfaceTotale;
    }

    /**
     * Affiche les détails de l'étage.
     */
    public void afficher() {
        System.out.println("  --> Niveau n°" + idNiveau + " [Hauteur: " + hauteurSousPlafond + "m]");
        System.out.println("      Nombre d'appartements enregistrés : " + listeAppartements.size());
    }

    // --- GETTERS & SETTERS ---

    public int getIdNiveau() { return idNiveau; }
    public void setIdNiveau(int idNiveau) { this.idNiveau = idNiveau; }

    public double getHauteurSousPlafond() { return hauteurSousPlafond; }
    public void setHauteurSousPlafond(double hauteur) { this.hauteurSousPlafond = hauteur; }

    public ArrayList<Appartement> getListeAppartements() { return listeAppartements; }
}
