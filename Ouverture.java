public abstract class Ouverture {
    protected double largeur;
    protected double hauteur;

    public Ouverture(double largeur, double hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public double surface() {
        return largeur * hauteur;
    }
}
