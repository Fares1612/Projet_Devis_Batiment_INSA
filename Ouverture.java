public abstract class Ouverture {
    private double largeur;
    private double hauteur;

    public Ouverture(double largeur, double hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public double surface() {
        return largeur * hauteur;
    }

    public double getLargeur() { return largeur; }
    public double getHauteur() { return hauteur; }
}

