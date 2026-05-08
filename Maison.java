import java.util.ArrayList;

/**
 * Représente une maison individuelle.
 * Une maison est un bâtiment qui contient au moins un niveau[cite: 9, 18].
 */
public class Maison extends Batiment {
    
    // Attribut spécifique à la classe Maison selon l'UML
    private int idMaison;
    
    // Une maison contient un ou plusieurs niveaux (relation "contient" 1..*)
    private ArrayList<Niveau> listeNiveaux;

    /**
     * Constructeur de la classe Maison.
     * @param idBatiment Identifiant générique du bâtiment [cite: 17]
     * @param idMaison Identifiant spécifique de la maison
     * @param nbreNiveaux Nombre de niveaux [cite: 18]
     */
    public Maison(String idBatiment, int idMaison, int nbreNiveaux) {
        // Appel au constructeur de la classe mère Batiment
        super(idBatiment, "Maison", nbreNiveaux); 
        this.idMaison = idMaison;
        this.listeNiveaux = new ArrayList<>();
    }

    /**
     * Ajoute un niveau à la maison.
     */
    public void ajouterNiveau(Niveau niveau) {
        this.listeNiveaux.add(niveau);
    }

    /**
     * Calcule la surface totale de la maison en additionnant la surface de chaque niveau.
     * @return La surface totale en m²
     */
    public double surface() {
        double surfaceTotale = 0;
        for (Niveau n : listeNiveaux) {
            // Ici, Idrique appellera la méthode de calcul de surface du niveau
            // surfaceTotale += n.surfaceNiveau(); 
        }
        return surfaceTotale;
    }

    /**
     * Implémentation du calcul du devis pour une maison[cite: 8, 52].
     * Somme les devis de tous les niveaux qui la composent.
     */
    @Override
    public double devisBatiment() {
        double total = 0;
        for (Niveau n : listeNiveaux) {
            // total += n.devisNiveau();
        }
        return total;
    }

    @Override
    public double surfaceRevetement(int idRevetement) {
        double surfaceTotaleRevetement = 0;
        for (Niveau n : listeNiveaux) {
            // surfaceTotaleRevetement += n.surfaceRevetementNiveau(idRevetement);
        }
        return surfaceTotaleRevetement;
    }

    @Override
    public double devisRevetement(int idRevetement) {
        double prixTotalRevetement = 0;
        for (Niveau n : listeNiveaux) {
            // prixTotalRevetement += n.devisRevetementNiveau(idRevetement);
        }
        return prixTotalRevetement;
    }

    /**
     * Affiche les détails de la maison.
     */
    @Override
    public void afficher() {
        super.afficher(); // Affiche les infos de Batiment
        System.out.println("ID Maison : " + this.idMaison);
        System.out.println("Nombre de niveaux réels : " + this.listeNiveaux.size());
    }

    @Override
    public void dessiner() {
        System.out.println("Dessin de la maison ID " + idMaison);
        // Logique de dessin 2D pour l'étape 2 [cite: 62]
    }

    // Getter et Setter pour idMaison
    public int getIdMaison() { return idMaison; }
    public void setIdMaison(int idMaison) { this.idMaison = idMaison; }
}

    
