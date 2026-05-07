public class Porte extends Ouverture {
    private int idPorte;

    // Constructeur par défaut avec les dimensions standard demandées (0.9m x 2.1m)
    public Porte(int idPorte) {
        super(0.9, 2.1);
        this.idPorte = idPorte;
    }

    // Constructeur personnalisé si besoin
    public Porte(int idPorte, double largeur, double hauteur) {
        super(largeur, hauteur);
        this.idPorte = idPorte;
    }

    @Override
    public String toString() {
        return "Porte " + idPorte + " [" + largeur + "m x " + hauteur + "m]";
    }
}