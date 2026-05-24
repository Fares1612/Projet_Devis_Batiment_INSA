package fr.insa.batiment.model;

import java.util.ArrayList;
import java.util.List;

public class Batiment {

    private int idBatiment;
    private String typeBatiment;
    private List<Niveau> niveaux;

    public Batiment(int idBatiment, String typeBatiment) {
        this.idBatiment = idBatiment;
        this.typeBatiment = typeBatiment;
        this.niveaux = new ArrayList<>();
    }

    public int getIdBatiment() { return idBatiment; }
    public String getTypeBatiment() { return typeBatiment; }
    public void setTypeBatiment(String typeBatiment) { this.typeBatiment = typeBatiment; }
    public List<Niveau> getNiveaux() { return niveaux; }

    public void ajouterNiveau(Niveau niveau) {
        niveaux.add(niveau);
    }
}
