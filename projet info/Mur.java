import java.util.ArrayList;

public class Mur {
    private int idMur;
    private Coin debut;
    private Coin fin;
    private double hauteur;
    private ArrayList<Ouverture> ouvertures;
    private Revetement revetement;

    public Mur(int idMur, Coin debut, Coin fin, double hauteur) {
        this.idMur = idMur;
        this.debut = debut;
        this.fin = fin;
        this.hauteur = hauteur;
        this.ouvertures = new ArrayList<>();
    }

    public double longueur() {
        return Math.sqrt(Math.pow(fin.getX() - debut.getX(), 2) + Math.pow(fin.getY() - debut.getY(), 2));
    }

    public double surfaceNette() {
        double surfaceOuvertures = 0;
        for (Ouverture o : ouvertures) {
            surfaceOuvertures += o.surface();
        }
        return (longueur() * hauteur) - surfaceOuvertures;
    }

    public void ajouterOuverture(Ouverture o) { this.ouvertures.add(o); }
    public void setRevetement(Revetement r) { this.revetement = r; }
    public Revetement getRevetement() { return this.revetement; }
}