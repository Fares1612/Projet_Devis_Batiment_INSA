public class Peinture extends Revetement {
    private String idPeinture;

    public Peinture(String idPeinture, double prixUnitaire) {
        super(prixUnitaire);
        this.idPeinture = idPeinture;
    }

    @Override
    public String toString() {
        return "Peinture ID: " + idPeinture + " (" + prixUnitaire + "€/m²)";
    }
}