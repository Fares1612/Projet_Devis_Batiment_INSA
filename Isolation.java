public class Isolation extends Revetement {
    private String idIsolant;

    public Isolation(String idIsolant, double prixUnitaire) {
        super(prixUnitaire);
        this.idIsolant = idIsolant;
    }

    @Override
    public String toString() {
        return "Isolation ID: " + idIsolant + " (" + prixUnitaire + "€/m²)";
    }
}