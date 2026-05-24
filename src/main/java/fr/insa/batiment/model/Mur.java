package fr.insa.batiment.model;

import java.util.ArrayList;
import java.util.List;

public class Mur {

    private int idMur;
    private Coin debut;
    private Coin fin;
    private Revetement revetement;
    private List<Ouverture> ouvertures;

    public Mur(int idMur, Coin debut, Coin fin, Revetement revetement) {
        this.idMur = idMur;
        this.debut = debut;
        this.fin = fin;
        this.revetement = revetement;
        this.ouvertures = new ArrayList<>();
    }

    public int getIdMur() { return idMur; }
    public Coin getDebut() { return debut; }
    public Coin getFin() { return fin; }
    public Revetement getRevetement() { return revetement; }
    public List<Ouverture> getOuvertures() { return ouvertures; }

    public void ajouterOuverture(Ouverture ouverture) {
        ouvertures.add(ouverture);
    }

    public double longueur() {
        double dx = fin.getX() - debut.getX();
        double dy = fin.getY() - debut.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double surface(double hauteur) {
        return longueur() * hauteur;
    }

    // Surface nette : ouvertures deduites
    public double surfaceNette(double hauteur) {
        double nette = surface(hauteur);
        for (Ouverture o : ouvertures) {
            nette -= o.surface();
        }
        return Math.max(0, nette);
    }

    public static class Coin {
        private int idCoin;
        private double x;
        private double y;

        public Coin(int idCoin, double x, double y) {
            this.idCoin = idCoin;
            this.x = x;
            this.y = y;
        }

        public int getIdCoin() { return idCoin; }
        public double getX() { return x; }
        public double getY() { return y; }
    }
}
