package fr.insa.batiment.service;

import fr.insa.batiment.model.CatalogueRevetement;
import fr.insa.batiment.model.Devis;
import fr.insa.batiment.model.Mur;
import fr.insa.batiment.model.Ouverture;
import fr.insa.batiment.model.Piece;
import fr.insa.batiment.model.Revetement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DevisService {

    private static int compteurOuverture = 1;

    public Devis calculerDevis(List<Piece> pieces, double hauteur, CatalogueRevetement catalogue) {
        Map<String, Double> surfaces = new HashMap<>();
        for (Piece piece : pieces) {
            for (Map.Entry<String, Double> entree : piece.devisPiece(hauteur).entrySet()) {
                surfaces.merge(entree.getKey(), entree.getValue(), Double::sum);
            }
        }
        Devis devis = new Devis();
        for (Map.Entry<String, Double> e : surfaces.entrySet()) {
            int sep = e.getKey().lastIndexOf('_');
            int idRev = Integer.parseInt(e.getKey().substring(0, sep));
            String support = e.getKey().substring(sep + 1);
            catalogue.trouverParId(idRev).ifPresent(rev ->
                    devis.ajouterLigne(new Devis.LigneDevis(rev, support, e.getValue())));
        }
        return devis;
    }

    public Piece creerPieceRectangulaire(int idPiece, String usage, double largeur, double longueur,
                                          Revetement revMur, Revetement revSol, Revetement revPlafond,
                                          int nbPortes, int nbFenetres) {
        Piece piece = new Piece(idPiece, usage, largeur, longueur);

        Mur.Coin c1 = new Mur.Coin(1, 0, 0);
        Mur.Coin c2 = new Mur.Coin(2, largeur, 0);
        Mur.Coin c3 = new Mur.Coin(3, largeur, longueur);
        Mur.Coin c4 = new Mur.Coin(4, 0, longueur);

        Mur mur1 = new Mur(1, c1, c2, revMur);
        Mur mur2 = new Mur(2, c2, c3, revMur);
        Mur mur3 = new Mur(3, c3, c4, revMur);
        Mur mur4 = new Mur(4, c4, c1, revMur);
        Mur[] murs = {mur1, mur2, mur3, mur4};

        List<Ouverture> ouvertures = new ArrayList<>();
        for (int i = 0; i < nbPortes; i++) {
            ouvertures.add(new Ouverture.Porte(compteurOuverture++));
        }
        for (int i = 0; i < nbFenetres; i++) {
            ouvertures.add(new Ouverture.Fenetre(compteurOuverture++));
        }
        Collections.shuffle(ouvertures);
        repartirOuvertures(murs, ouvertures);

        piece.ajouterMur(mur1);
        piece.ajouterMur(mur2);
        piece.ajouterMur(mur3);
        piece.ajouterMur(mur4);
        piece.setSol(new Piece.Sol(revSol));
        if (revPlafond != null) {
            piece.setPlafond(new Piece.Plafond(revPlafond));
        }
        return piece;
    }

    private void repartirOuvertures(Mur[] murs, List<Ouverture> ouvertures) {
        Random random = new Random();
        for (Ouverture ouverture : ouvertures) {
            boolean placee = false;
            for (int essai = 0; essai < 30 && !placee; essai++) {
                Mur mur = murs[random.nextInt(murs.length)];
                double longueurMur = mur.longueur();
                if (longueurMur < ouverture.getLargeur() + 0.2) {
                    continue;
                }
                double posMax = longueurMur - ouverture.getLargeur();
                double pos = posMax > 0 ? random.nextDouble() * posMax : 0;
                if (!chevauche(mur, pos, ouverture.getLargeur())) {
                    ouverture.setPositionSurMur(pos);
                    mur.ajouterOuverture(ouverture);
                    placee = true;
                }
            }
            if (!placee) {
                Mur mur = murs[0];
                ouverture.setPositionSurMur(0);
                mur.ajouterOuverture(ouverture);
            }
        }
    }

    private boolean chevauche(Mur mur, double pos, double largeur) {
        for (Ouverture existante : mur.getOuvertures()) {
            double fin = existante.getPositionSurMur() + existante.getLargeur();
            if (pos < fin && pos + largeur > existante.getPositionSurMur()) {
                return true;
            }
        }
        return false;
    }
}
