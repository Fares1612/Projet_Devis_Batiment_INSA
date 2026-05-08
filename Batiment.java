Vpublic abstract class Batiment {
    
    // Attributs privés selon le diagramme UML
    private String idBatiment;   // Identifiant unique 
    private String typeBatiment; // Maison ou Immeuble [cite: 18]
    private int nbreNiveaux;     // Nombre d'étages [cite: 18]

    /**
     * Constructeur pour initialiser les bases du bâtiment.
     */
    public Batiment(String idBatiment, String typeBatiment, int nbreNiveaux) {
        this.idBatiment = idBatiment;
        this.typeBatiment = typeBatiment;
        this.nbreNiveaux = nbreNiveaux;
    }

    // --- MÉTHODES ABSTRAITES ---
    // Ces méthodes doivent être implémentées dans Maison et Immeuble.

    /**
     * Calcule le devis estimatif total du bâtiment[cite: 8, 52].
     */
    public abstract double devisBatiment();

    /**
     * Calcule la surface totale couverte par un revêtement spécifique.
     */
    public abstract double surfaceRevetement(int idRevetement);

    /**
     * Calcule le montant total (prix) pour un type de revêtement donné[cite: 55].
     */
    public abstract double devisRevetement(int idRevetement);

    /**
     * Méthode pour la visualisation graphique (Etape 2/3 du projet)[cite: 62].
     */
    public abstract void dessiner();

    // --- MÉTHODES CONCRÈTES ---

    /**
     * Affiche les informations générales du bâtiment dans la console.
     */
    public void afficher() {
        System.out.println("=== Fiche Bâtiment ===");
        System.out.println("ID : " + this.idBatiment);
        System.out.println("Type : " + this.typeBatiment);
        System.out.println("Niveaux : " + this.nbreNiveaux);
    }

    // --- GETTERS & SETTERS ---
    // Essentiels pour que les autres membres du groupe accèdent aux données.

    public String getIdBatiment() { return idBatiment; }
    public void setIdBatiment(String idBatiment) { this.idBatiment = idBatiment; }

    public String getTypeBatiment() { return typeBatiment; }
    public void setTypeBatiment(String typeBatiment) { this.typeBatiment = typeBatiment; }

    public int getNbreNiveaux() { return nbreNiveaux; }
    public void setNbreNiveaux(int nbreNiveaux) { this.nbreNiveaux = nbreNiveaux; }
}
