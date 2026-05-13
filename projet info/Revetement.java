public abstract class Revetement {
    protected int idRevetement;
    protected String designation;
    protected double prixUnitaire;
    protected boolean pourMur;
    protected boolean pourSol;
    protected boolean pourPlafond;

    public Revetement(int id, String nom, double prix, boolean m, boolean s, boolean p) {
        this.idRevetement = id;
        this.designation = nom;
        this.prixUnitaire = prix;
        this.pourMur = m;
        this.pourSol = s;
        this.pourPlafond = p;
    }

    public double getPrixUnitaire() { return prixUnitaire; }
    public String getDesignation() { return designation; }
}