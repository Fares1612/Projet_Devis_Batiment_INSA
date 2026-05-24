package fr.insa.batiment.model;

import java.util.ArrayList;
import java.util.List;

public class Niveau {

    private int idNiveau;
    private double hauteurSousPlafond;
    private List<Appartement> appartements;

    public Niveau(int idNiveau, double hauteurSousPlafond) {
        this.idNiveau = idNiveau;
        this.hauteurSousPlafond = hauteurSousPlafond;
        this.appartements = new ArrayList<>();
    }

    public int getIdNiveau() { return idNiveau; }
    public double getHauteurSousPlafond() { return hauteurSousPlafond; }
    public List<Appartement> getAppartements() { return appartements; }

    public void setHauteurSousPlafond(double hauteurSousPlafond) {
        this.hauteurSousPlafond = hauteurSousPlafond;
    }

    public void ajouterAppartement(Appartement appartement) {
        appartements.add(appartement);
    }
}
