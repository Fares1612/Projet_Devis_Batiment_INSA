package fr.insa.batiment.service;

import fr.insa.batiment.model.Devis;
import fr.insa.batiment.model.Piece;
import fr.insa.batiment.model.ProjetModel;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SauvegardeService {

    public void sauvegarderDevis(Devis devis, Path chemin) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(chemin, StandardCharsets.UTF_8)) {
            w.write("=== DEVIS ESTIMATIF - CATALOGUE REVETEMENT ===");
            w.newLine();
            w.write("Date : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            w.newLine();
            w.newLine();
            w.write(String.format("%-28s %-10s %12s %12s %12s%n",
                    "Revetement", "Support", "Surface m2", "Prix unit.", "Prix total"));
            w.write("-".repeat(78));
            w.newLine();
            for (Devis.LigneDevis ligne : devis.getLignes()) {
                w.write(String.format("%-28s %-10s %12.2f %12.2f %12.2f%n",
                        ligne.getRevetement().getDesignation(),
                        ligne.getSupport(),
                        ligne.getSurfaceTotale(),
                        ligne.getPrixUnitaire(),
                        ligne.getPrixTotal()));
            }
            w.newLine();
            w.write(String.format("TOTAL GENERAL : %.2f EUR", Math.round(devis.getTotal() * 100.0) / 100.0));
            w.newLine();
        }
    }

    public void sauvegarderProjet(ProjetModel model, Path chemin) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(chemin, StandardCharsets.UTF_8)) {
            w.write("=== SAUVEGARDE PROJET ===");
            w.newLine();
            w.write("Date : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            w.newLine();
            w.write("Type batiment : " + model.getBatiment().getTypeBatiment());
            w.newLine();
            w.write(String.format("Hauteur sous plafond : %.2f m",
                    model.getNiveauPrincipal().getHauteurSousPlafond()));
            w.newLine();
            w.newLine();
            w.write("LISTE DES PIECES");
            w.newLine();
            w.write("Id;Usage;Largeur;Longueur;Portes;Fenetres;Revetement mur;Revetement sol;Revetement plafond");
            w.newLine();

            for (Piece p : model.getPieces()) {
                w.write(String.format("%d;%s;%.2f;%.2f;%d;%d;%s;%s;%s%n",
                        p.getIdPiece(),
                        p.getUsage(),
                        p.getLargeur(),
                        p.getLongueur(),
                        p.getNombrePortes(),
                        p.getNombreFenetres(),
                        nomRevetementMur(p),
                        nomRevetement(p.getSol() != null ? p.getSol().getRevetement() : null),
                        nomRevetement(p.getPlafond() != null ? p.getPlafond().getRevetement() : null)));
            }

            if (!model.getDevisCourant().getLignes().isEmpty()) {
                w.newLine();
                w.write("DEVIS CALCULE");
                w.newLine();
                for (Devis.LigneDevis ligne : model.getDevisCourant().getLignes()) {
                    w.write(String.format("%s ; %s ; %.2f m2 ; %.2f EUR%n",
                            ligne.getRevetement().getDesignation(),
                            ligne.getSupport(),
                            ligne.getSurfaceTotale(),
                            ligne.getPrixTotal()));
                }
                w.write(String.format("Total : %.2f EUR", Math.round(model.getDevisCourant().getTotal() * 100.0) / 100.0));
                w.newLine();
            }
        }
    }

    private String nomRevetementMur(Piece piece) {
        if (piece.getMurs().isEmpty() || piece.getMurs().get(0).getRevetement() == null) {
            return "-";
        }
        return piece.getMurs().get(0).getRevetement().getDesignation();
    }

    private String nomRevetement(fr.insa.batiment.model.Revetement rev) {
        return rev == null ? "-" : rev.getDesignation();
    }
}
