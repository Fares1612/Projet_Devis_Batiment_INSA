public class Carrelage extends Revetement {
    private int idCarrelage;

    public Carrelage(int idCarrelage, double prixUnitaire) {
        super(prixUnitaire);
        this.idCarrelage = idCarrelage;
    }

    @Override
    public String toString() {
        return "Carrelage ID: " + idCarrelage + " (" + prixUnitaire + "€/m²)";
    }
}