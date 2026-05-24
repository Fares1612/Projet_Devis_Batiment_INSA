package fr.insa.batiment.service;

import fr.insa.batiment.model.CatalogueRevetement;
import fr.insa.batiment.model.Revetement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CatalogueService {

    public CatalogueRevetement charger(Path chemin) throws IOException {
        List<Revetement> liste = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(chemin, StandardCharsets.UTF_8)) {
            String ligne;
            boolean entete = true;
            while ((ligne = reader.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty()) continue;
                if (entete && ligne.toLowerCase().startsWith("id")) {
                    entete = false;
                    continue;
                }
                liste.add(parseLigne(ligne));
            }
        }
        CatalogueRevetement catalogue = new CatalogueRevetement();
        catalogue.setRevetements(liste);
        return catalogue;
    }

    public void sauvegarder(CatalogueRevetement catalogue, Path chemin) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(chemin, StandardCharsets.UTF_8)) {
            writer.write("idRevetement;designation;pourMur;pourSol;pourPlafond;prixUnitaire");
            writer.newLine();
            for (Revetement r : catalogue.getRevetements()) {
                writer.write(String.format("%d;%s;%d;%d;%d;%.2f",
                        r.getIdRevetement(), r.getDesignation(),
                        r.isPourMur() ? 1 : 0, r.isPourSol() ? 1 : 0, r.isPourPlafond() ? 1 : 0,
                        r.getPrixUnitaire()));
                writer.newLine();
            }
        }
    }

    private Revetement parseLigne(String ligne) {
        String[] p = ligne.split(";");
        return new Revetement(
                Integer.parseInt(p[0].trim()),
                p[1].trim(),
                "1".equals(p[2].trim()),
                "1".equals(p[3].trim()),
                "1".equals(p[4].trim()),
                Double.parseDouble(p[5].trim().replace(',', '.')));
    }
}
