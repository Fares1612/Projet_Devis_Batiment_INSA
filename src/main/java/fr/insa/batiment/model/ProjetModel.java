package fr.insa.batiment.model;

import java.util.List;

public class ProjetModel {

    private final CatalogueRevetement catalogue;
    private final Batiment batiment;
    private Devis devisCourant;

    public ProjetModel() {
        catalogue = new CatalogueRevetement();
        batiment = new Batiment(1, "");
        devisCourant = new Devis();
    }

    public void initialiserProjet(String typeBatiment) {
        batiment.setTypeBatiment(typeBatiment);
        batiment.getNiveaux().clear();
        if ("Immeuble".equals(typeBatiment)) {
            Niveau rdc = new Niveau(1, 2.50);
            rdc.ajouterAppartement(new Appartement(1));
            Niveau etage = new Niveau(2, 2.50);
            etage.ajouterAppartement(new Appartement(2));
            batiment.ajouterNiveau(rdc);
            batiment.ajouterNiveau(etage);
        } else {
            Niveau niveau = new Niveau(1, 2.50);
            niveau.ajouterAppartement(new Appartement(1));
            batiment.ajouterNiveau(niveau);
        }
        devisCourant = new Devis();
    }

    public CatalogueRevetement getCatalogue() { return catalogue; }
    public Batiment getBatiment() { return batiment; }

    public Niveau getNiveauPrincipal() {
        return batiment.getNiveaux().get(0);
    }

    public Appartement getAppartementPrincipal() {
        return getNiveauPrincipal().getAppartements().get(0);
    }

    public List<Piece> getPieces() {
        return getAppartementPrincipal().getPieces();
    }

    public Devis getDevisCourant() { return devisCourant; }

    public void setDevisCourant(Devis devisCourant) {
        this.devisCourant = devisCourant;
    }
}
