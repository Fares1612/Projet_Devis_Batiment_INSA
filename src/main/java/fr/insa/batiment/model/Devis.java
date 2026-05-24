package fr.insa.batiment.model;

import java.util.ArrayList;
import java.util.List;

public class Devis {

    private List<LigneDevis> lignes;

    public Devis() {
        this.lignes = new ArrayList<>();
    }

    public List<LigneDevis> getLignes() { return lignes; }

    public void ajouterLigne(LigneDevis ligne) {
        lignes.add(ligne);
    }

    public double getTotal() {
        double total = 0;
        for (LigneDevis ligne : lignes) {
            total += ligne.getPrixTotal();
        }
        return total;
    }

    public static class LigneDevis {
        private Revetement revetement;
        private String support;
        private double surfaceTotale;
        private double prixUnitaire;
        private double prixTotal;

        public LigneDevis(Revetement revetement, String support, double surfaceTotale) {
            this.revetement = revetement;
            this.support = support;
            this.surfaceTotale = arrondir2(surfaceTotale);
            this.prixUnitaire = arrondir2(revetement.getPrixUnitaire());
            this.prixTotal = arrondir2(this.surfaceTotale * this.prixUnitaire);
        }

        private static double arrondir2(double v) {
            return Math.round(v * 100.0) / 100.0;
        }

        public Revetement getRevetement() { return revetement; }
        public String getSupport() { return support; }
        public double getSurfaceTotale() { return surfaceTotale; }
        public double getPrixUnitaire() { return prixUnitaire; }
        public double getPrixTotal() { return prixTotal; }
    }
}
