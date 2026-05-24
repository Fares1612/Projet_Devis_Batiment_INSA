package fr.insa.batiment.model;

public class Revetement {

    private int idRevetement;
    private String designation;
    private boolean pourMur;
    private boolean pourSol;
    private boolean pourPlafond;
    private double prixUnitaire;

    public Revetement(int idRevetement, String designation, boolean pourMur,
                      boolean pourSol, boolean pourPlafond, double prixUnitaire) {
        this.idRevetement = idRevetement;
        this.designation = designation;
        this.pourMur = pourMur;
        this.pourSol = pourSol;
        this.pourPlafond = pourPlafond;
        this.prixUnitaire = prixUnitaire;
    }

    public int getIdRevetement() { return idRevetement; }
    public String getDesignation() { return designation; }
    public boolean isPourMur() { return pourMur; }
    public boolean isPourSol() { return pourSol; }
    public boolean isPourPlafond() { return pourPlafond; }
    public double getPrixUnitaire() { return prixUnitaire; }

    @Override
    public String toString() {
        return idRevetement + " - " + designation;
    }
}
