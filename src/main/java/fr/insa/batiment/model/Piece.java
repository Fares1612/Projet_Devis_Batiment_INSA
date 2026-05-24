package fr.insa.batiment.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Piece {

    private int idPiece;
    private String usage;
    private double largeur;
    private double longueur;
    private List<Mur> murs;
    private Sol sol;
    private Plafond plafond;

    public Piece(int idPiece, String usage, double largeur, double longueur) {
        this.idPiece = idPiece;
        this.usage = usage;
        this.largeur = largeur;
        this.longueur = longueur;
        this.murs = new ArrayList<>();
    }

    public int getIdPiece() { return idPiece; }
    public String getUsage() { return usage; }
    public double getLargeur() { return largeur; }
    public double getLongueur() { return longueur; }
    public List<Mur> getMurs() { return murs; }
    public Sol getSol() { return sol; }
    public Plafond getPlafond() { return plafond; }

    public void ajouterMur(Mur mur) { murs.add(mur); }
    public void setSol(Sol sol) { this.sol = sol; }
    public void setPlafond(Plafond plafond) { this.plafond = plafond; }

    public double surfaceSol() { return largeur * longueur; }
    public double surfacePlafond() { return surfaceSol(); }

    public double surfaceMurs(double hauteur) {
        double total = 0;
        for (Mur mur : murs) {
            total += mur.surfaceNette(hauteur);
        }
        return total;
    }

    public int getNombrePortes() {
        int n = 0;
        for (Mur mur : murs) {
            for (Ouverture o : mur.getOuvertures()) {
                if (o instanceof Ouverture.Porte) {
                    n++;
                }
            }
        }
        return n;
    }

    public int getNombreFenetres() {
        int n = 0;
        for (Mur mur : murs) {
            for (Ouverture o : mur.getOuvertures()) {
                if (o instanceof Ouverture.Fenetre) {
                    n++;
                }
            }
        }
        return n;
    }

    public Map<String, Double> devisPiece(double hauteur) {
        Map<String, Double> map = new HashMap<>();
        for (Mur mur : murs) {
            if (mur.getRevetement() != null) {
                String cle = mur.getRevetement().getIdRevetement() + "_Mur";
                map.merge(cle, mur.surfaceNette(hauteur), Double::sum);
            }
        }
        if (sol != null && sol.getRevetement() != null) {
            String cle = sol.getRevetement().getIdRevetement() + "_Sol";
            map.merge(cle, surfaceSol(), Double::sum);
        }
        if (plafond != null && plafond.getRevetement() != null) {
            String cle = plafond.getRevetement().getIdRevetement() + "_Plafond";
            map.merge(cle, surfacePlafond(), Double::sum);
        }
        return map;
    }

    public static class Sol {
        private Revetement revetement;
        public Sol(Revetement revetement) { this.revetement = revetement; }
        public Revetement getRevetement() { return revetement; }
    }

    public static class Plafond {
        private Revetement revetement;
        public Plafond(Revetement revetement) { this.revetement = revetement; }
        public Revetement getRevetement() { return revetement; }
    }
}
