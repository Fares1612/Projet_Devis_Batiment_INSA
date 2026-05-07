public abstract class Revetement {
    protected double prixUnitaire; // Prix au m²

    public Revetement(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    // Calcule le montant total pour une surface donnée
    public double montant(double surface) {
        return surface * prixUnitaire;
    }
}