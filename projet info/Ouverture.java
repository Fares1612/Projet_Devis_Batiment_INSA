public abstract class Ouverture {
    protected int idOuverture;
    protected double largeur;
    protected double hauteur;

    public Ouverture(int idOuverture, double largeur, double hauteur) {
        this.idOuverture = idOuverture;
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public double surface() {
        return largeur * hauteur;
    }
}