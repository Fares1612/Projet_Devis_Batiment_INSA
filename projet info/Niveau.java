import java.util.ArrayList;

public class Niveau {
    private int idNiveau;
    private double hauteurSousPlafond;
    private ArrayList<Appartement> appartements;
    private ArrayList<Tremie> tremies;

    public Niveau(int idNiveau, double hauteurSousPlafond) {
        this.idNiveau = idNiveau;
        this.hauteurSousPlafond = hauteurSousPlafond;
        this.appartements = new ArrayList<>();
        this.tremies = new ArrayList<>();
    }

    public void ajouterAppartement(Appartement a) { this.appartements.add(a); }
    public void ajouterTremie(Tremie t) { this.tremies.add(t); }

    public double devisNiveau() {
        double total = 0;
        for (Appartement a : appartements) {
            total += a.devisAppartement();
        }
        return total;
    }
}