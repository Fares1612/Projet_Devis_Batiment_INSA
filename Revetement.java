public abstract class Revetement {
    private String nom;
    private double prixUnitaire; // Prix au m²

    public Revetement(String nom, double prixUnitaire) {
        this.nom = nom;
        this.prixUnitaire = prixUnitaire;
    }

    public double calculerPrix(double surface) {
        return surface * prixUnitaire;
    }

    public String getNom() { return nom; }
    public double getPrixUnitaire() { return prixUnitaire; }
}