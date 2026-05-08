import java.util.ArrayList;

/**
 * Représente un bâtiment de type Immeuble.
 * Un immeuble est composé de plusieurs niveaux (étages).
 */
public class Immeuble extends Batiment {

    // Attribut spécifique selon le diagramme UML
    private int idImmeuble;

    // Relation "contient" : Liste des niveaux de l'immeuble
    private ArrayList<Niveau> listeNiveaux;

    /**
     * Constructeur de l'Immeuble.
     * @param idBatiment Identifiant général (String)
     * @param idImmeuble Identifiant spécifique (int)
     * @param nbreNiveaux Nombre théorique de niveaux
     */
    public Immeuble(String idBatiment, int idImmeuble, int nbreNiveaux) {
        // Appel au constructeur parent Batiment avec le type "Immeuble"
        super(idBatiment, "Immeuble", nbreNiveaux);
        this.idImmeuble = idImmeuble;
        this.listeNiveaux = new ArrayList<>();
    }

    /**
     * Permet d'ajouter un niveau à l'immeuble.
     */
    public void ajouterNiveau(Niveau niveau) {
        this.listeNiveaux.add(niveau);
    }

    /**
     * Calcule le devis estimatif total de l'immeuble en sommant le devis de chaque niveau.
     * @return Le montant total en euros.
     */
    @Override
    public double devisBatiment() {
        double total = 0;
        for (Niveau n : listeNiveaux) {
            // Idrique appelle ici la méthode de calcul du niveau (réalisée par lui-même ou Farès)
            // total += n.devisNiveau(); 
        }
        return total;
    }

    /**
     * Calcule la surface totale d'un revêtement spécifique pour tout l'immeuble.
     */
    @Override
    public double surfaceRevetement(int idRevetement) {
        double surfaceTotale = 0;
        for (Niveau n : listeNiveaux) {
            // surfaceTotale += n.surfaceRevetementNiveau(idRevetement);
        }
        return surfaceTotale;
    }

    /**
     * Calcule le prix total d'un revêtement pour tout l'immeuble.
     */
    @Override
    public double devisRevetement(int idRevetement) {
        double prixTotal = 0;
        for (Niveau n : listeNiveaux) {
            // prixTotal += n.devisRevetementNiveau(idRevetement);
        }
        return prixTotal;
    }

    /**
     * Affiche les informations de l'immeuble.
     */
    @Override
    public void afficher() {
        super.afficher(); // Infos de la classe Batiment
        System.out.println("ID Immeuble : " + this.idImmeuble);
        System.out.println("Nombre d'étages enregistrés : " + this.listeNiveaux.size());
    }

    /**
     * Méthode pour la visualisation 2D (Etape 2 du projet).
     */
    @Override
    public void dessiner() {
        System.out.println("Dessin de l'immeuble ID " + idImmeuble);
        // Cette méthode pourra appeler le dessin de chaque niveau
    }

    // Getters et Setters
    public int getIdImmeuble() { return idImme
