package fr.insa.batiment.model;

public abstract class Ouverture {

    protected int idOuverture;
    protected double largeur;
    protected double hauteur;
    protected double positionSurMur;

    public int getIdOuverture() { return idOuverture; }
    public double getLargeur() { return largeur; }
    public double getHauteur() { return hauteur; }
    public double getPositionSurMur() { return positionSurMur; }

    public void setPositionSurMur(double positionSurMur) {
        this.positionSurMur = positionSurMur;
    }

    public double surface() {
        return largeur * hauteur;
    }

    public static class Porte extends Ouverture {
        public Porte(int idOuverture) {
            this.idOuverture = idOuverture;
            this.largeur = 0.90;
            this.hauteur = 2.10;
        }
    }

    public static class Fenetre extends Ouverture {
        public Fenetre(int idOuverture) {
            this.idOuverture = idOuverture;
            this.largeur = 1.20;
            this.hauteur = 1.20;
        }
    }
}
