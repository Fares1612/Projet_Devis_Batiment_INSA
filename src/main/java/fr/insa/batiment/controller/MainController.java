package fr.insa.batiment.controller;

import fr.insa.batiment.model.Devis;
import fr.insa.batiment.model.Ouverture;
import fr.insa.batiment.model.Mur;
import fr.insa.batiment.model.Piece;
import fr.insa.batiment.model.ProjetModel;
import fr.insa.batiment.model.Revetement;
import fr.insa.batiment.service.CatalogueService;
import fr.insa.batiment.service.DevisService;
import fr.insa.batiment.service.SauvegardeService;
import fr.insa.batiment.view.MainView;
import javafx.collections.FXCollections;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainController {

    private final ProjetModel model;
    private final MainView view;
    private final CatalogueService catalogueService = new CatalogueService();
    private final DevisService devisService = new DevisService();
    private final SauvegardeService sauvegardeService = new SauvegardeService();

    public MainController(ProjetModel model, MainView view) {
        this.model = model;
        this.view = view;
        connecterAccueil();
        connecterEvenements();
    }

    private void connecterAccueil() {
        view.getBtnMaison().setOnAction(e -> demarrerProjet("Maison"));
        view.getBtnImmeuble().setOnAction(e -> demarrerProjet("Immeuble"));
    }

    private void demarrerProjet(String typeBatiment) {
        model.initialiserProjet(typeBatiment);
        view.afficherApplication();
        chargerCatalogueInitial();
        rafraichirComboBox();
        view.getBarreEtat().setText("Projet " + typeBatiment + " — catalogue pret.");
    }

    private void connecterEvenements() {
        view.getBtnChargerCatalogue().setOnAction(e -> chargerCatalogue());
        view.getBtnSauverCatalogue().setOnAction(e -> sauvegarderCatalogue());
        view.getBtnAjouterPiece().setOnAction(e -> ajouterPiece());
        view.getBtnCalculer().setOnAction(e -> calculerDevis());
        view.getBtnExporter().setOnAction(e -> exporterDevis());
        view.getBtnSauvegarderProjet().setOnAction(e -> sauvegarderProjet());
        view.getTabPane().getSelectionModel().selectedItemProperty().addListener((obs, a, n) -> {
            if (n != null) {
                view.getBarreEtat().setText("Onglet : " + n.getText());
                if ("Plan 2D".equals(n.getText())) {
                    rafraichirPlan();
                }
            }
        });
    }

    private void chargerCatalogueInitial() {
        try {
            Path chemin = Paths.get(getClass().getResource("/catalogue_revetements.txt").toURI());
            model.getCatalogue().setRevetements(catalogueService.charger(chemin).getRevetements());
            view.getTableCatalogue().getItems().setAll(model.getCatalogue().getRevetements());
            String type = model.getBatiment().getTypeBatiment();
            view.getBarreEtat().setText("Projet " + type + " — catalogue charge.");
        } catch (URISyntaxException | NullPointerException | IOException e) {
            view.getBarreEtat().setText("Chargez le catalogue depuis un fichier.");
        }
    }

    private void chargerCatalogue() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Charger le catalogue");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));
        var f = fc.showOpenDialog(view.getRoot().getScene().getWindow());
        if (f == null) return;
        try {
            model.getCatalogue().setRevetements(catalogueService.charger(f.toPath()).getRevetements());
            view.getTableCatalogue().getItems().setAll(model.getCatalogue().getRevetements());
            rafraichirComboBox();
            view.getBarreEtat().setText("Catalogue charge.");
        } catch (IOException ex) {
            alerte(Alert.AlertType.ERROR, ex.getMessage());
        }
    }

    private void sauvegarderCatalogue() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Sauvegarder le catalogue");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));
        var f = fc.showSaveDialog(view.getRoot().getScene().getWindow());
        if (f == null) return;
        try {
            catalogueService.sauvegarder(model.getCatalogue(), f.toPath());
            view.getBarreEtat().setText("Catalogue sauvegarde.");
        } catch (IOException ex) {
            alerte(Alert.AlertType.ERROR, ex.getMessage());
        }
    }

    private void rafraichirComboBox() {
        view.getCbMur().setItems(FXCollections.observableArrayList(model.getCatalogue().getRevetementsPourMur()));
        view.getCbSol().setItems(FXCollections.observableArrayList(model.getCatalogue().getRevetementsPourSol()));
        view.getCbPlafond().setItems(FXCollections.observableArrayList(model.getCatalogue().getRevetementsPourPlafond()));
        if (!view.getCbMur().getItems().isEmpty()) view.getCbMur().getSelectionModel().selectFirst();
        if (!view.getCbSol().getItems().isEmpty()) view.getCbSol().getSelectionModel().selectFirst();
        if (!view.getCbPlafond().getItems().isEmpty()) view.getCbPlafond().getSelectionModel().selectFirst();
    }

    private void ajouterPiece() {
        try {
            int id = Integer.parseInt(view.getTfId().getText().trim());
            String usage = view.getCbUsage().getValue();
            double largeur = Double.parseDouble(view.getTfLargeur().getText().trim().replace(',', '.'));
            double longueur = Double.parseDouble(view.getTfLongueur().getText().trim().replace(',', '.'));
            double hauteur = Double.parseDouble(view.getTfHauteur().getText().trim().replace(',', '.'));
            int nbPortes = Integer.parseInt(view.getTfPortes().getText().trim());
            int nbFenetres = Integer.parseInt(view.getTfFenetres().getText().trim());
            Revetement revMur = view.getCbMur().getValue();
            Revetement revSol = view.getCbSol().getValue();
            Revetement revPlafond = view.getCbPlafond().getValue();

            if (usage == null || usage.isEmpty() || revMur == null || revSol == null) {
                view.getLblMessagePiece().setStyle("-fx-text-fill: #aa0000;");
                view.getLblMessagePiece().setText("Champs obligatoires manquants.");
                return;
            }

            model.getNiveauPrincipal().setHauteurSousPlafond(hauteur);
            Piece piece = devisService.creerPieceRectangulaire(
                    id, usage, largeur, longueur, revMur, revSol, revPlafond, nbPortes, nbFenetres);
            model.getAppartementPrincipal().ajouterPiece(piece);

            view.getLblMessagePiece().setStyle("-fx-text-fill: #006600;");
            view.getLblMessagePiece().setText(String.format(
                    "Piece \"%s\" ajoutee (%d porte(s), %d fenetre(s)).",
                    usage, piece.getNombrePortes(), piece.getNombreFenetres()));
            view.getBarreEtat().setText(model.getPieces().size() + " piece(s) dans le projet.");
            rafraichirPlan();
        } catch (NumberFormatException ex) {
            view.getLblMessagePiece().setStyle("-fx-text-fill: #aa0000;");
            view.getLblMessagePiece().setText("Valeurs numeriques invalides.");
        }
    }

    private void calculerDevis() {
        if (model.getPieces().isEmpty()) {
            view.getLblTotal().setText("Aucune piece : ajoutez des pieces d'abord.");
            view.getTableDevis().getItems().clear();
            return;
        }
        Devis devis = devisService.calculerDevis(
                model.getPieces(),
                model.getNiveauPrincipal().getHauteurSousPlafond(),
                model.getCatalogue());
        model.setDevisCourant(devis);
        view.getTableDevis().getItems().setAll(devis.getLignes());
        view.getLblTotal().setText(String.format("Total general : %.2f EUR",
                Math.round(devis.getTotal() * 100.0) / 100.0));
    }

    private void sauvegarderProjet() {
        if (model.getBatiment().getTypeBatiment() == null || model.getBatiment().getTypeBatiment().isEmpty()) {
            alerte(Alert.AlertType.WARNING, "Choisissez d'abord Maison ou Immeuble.");
            return;
        }
        FileChooser fc = new FileChooser();
        fc.setTitle("Sauvegarder le projet");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));
        fc.setInitialFileName("projet.txt");
        var f = fc.showSaveDialog(view.getRoot().getScene().getWindow());
        if (f == null) return;
        try {
            sauvegardeService.sauvegarderProjet(model, f.toPath());
            view.getBarreEtat().setText("Projet sauvegarde : " + f.getName());
        } catch (IOException ex) {
            alerte(Alert.AlertType.ERROR, ex.getMessage());
        }
    }

    private void exporterDevis() {
        if (model.getDevisCourant().getLignes().isEmpty()) {
            alerte(Alert.AlertType.WARNING, "Calculez d'abord le devis.");
            return;
        }
        FileChooser fc = new FileChooser();
        fc.setTitle("Exporter le devis");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));
        fc.setInitialFileName("devis.txt");
        var f = fc.showSaveDialog(view.getRoot().getScene().getWindow());
        if (f == null) return;
        try {
            sauvegardeService.sauvegarderDevis(model.getDevisCourant(), f.toPath());
            view.getBarreEtat().setText("Devis exporte.");
        } catch (IOException ex) {
            alerte(Alert.AlertType.ERROR, ex.getMessage());
        }
    }

    private void rafraichirPlan() {
        Canvas canvas = view.getCanvasPlan();
        List<Piece> pieces = model.getPieces();
        final double marge = 40;
        final double gap = 20;
        final double echelleBase = 20;
        final double tailleMaxPiecePx = 280;
        final Color fondCanvas = Color.web("#f0f4f8");
        final Color fondPiece = Color.web("#dce4ef");
        final Color contourPiece = Color.web("#3d4f5f");

        if (pieces.isEmpty()) {
            view.getLblPlanInfo().setText("Ajoutez des pieces dans l'onglet Creation piece.");
            canvas.setWidth(800);
            canvas.setHeight(480);
            GraphicsContext gcVide = canvas.getGraphicsContext2D();
            gcVide.clearRect(0, 0, 800, 480);
            gcVide.setFill(fondCanvas);
            gcVide.fillRect(0, 0, 800, 480);
            return;
        }

        double echelle = echelleBase;
        for (Piece p : pieces) {
            double maxCote = Math.max(p.getLargeur(), p.getLongueur());
            if (maxCote > 0) {
                echelle = Math.min(echelle, tailleMaxPiecePx / maxCote);
            }
        }

        double sommeLargeursM = 0;
        double maxLongueurM = 0;
        for (Piece p : pieces) {
            sommeLargeursM += p.getLargeur();
            maxLongueurM = Math.max(maxLongueurM, p.getLongueur());
        }
        double zoneUtileW = 720;
        double largeurRequise = sommeLargeursM * echelle + (pieces.size() - 1) * gap;
        if (largeurRequise > zoneUtileW && sommeLargeursM > 0) {
            echelle = (zoneUtileW - (pieces.size() - 1) * gap) / sommeLargeursM;
            for (Piece p : pieces) {
                double maxCote = Math.max(p.getLargeur(), p.getLongueur());
                if (maxCote > 0) {
                    echelle = Math.min(echelle, tailleMaxPiecePx / maxCote);
                }
            }
        }

        double hauteurRangeePx = maxLongueurM * echelle;
        double canvasW = Math.max(800, marge * 2 + sommeLargeursM * echelle + (pieces.size() - 1) * gap);
        double canvasH = Math.max(460, marge + hauteurRangeePx + marge + 28);
        canvas.setWidth(canvasW);
        canvas.setHeight(canvasH);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvasW, canvasH);
        gc.setFill(fondCanvas);
        gc.fillRect(0, 0, canvasW, canvasH);

        int totalPortes = 0;
        int totalFenetres = 0;
        for (Piece p : pieces) {
            totalPortes += p.getNombrePortes();
            totalFenetres += p.getNombreFenetres();
        }
        view.getLblPlanInfo().setText(pieces.size() + " piece(s) | "
                + totalPortes + " porte(s) | " + totalFenetres + " fenetre(s)");

        double offsetX = marge;
        double offsetY = marge;

        for (Piece p : pieces) {
            double px = p.getLargeur() * echelle;
            double py = p.getLongueur() * echelle;

            dessinerPiecePlan(gc, p, offsetX, offsetY, px, py, echelle, fondPiece, fondCanvas, contourPiece);
            dessinerTextePiece(gc, p, offsetX, offsetY, px, py);

            offsetX += px + gap;
        }

        gc.setFill(Color.web("#6b7c8f"));
        gc.setFont(Font.font("Segoe UI", 11));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.BOTTOM);
        gc.fillText("Echelle : 1 m = " + String.format("%.0f", echelle) + " px", marge, canvasH - 12);
        gc.fillText("Trou = porte   Bleu = fenetre", marge + 220, canvasH - 12);
    }

    private void dessinerPiecePlan(GraphicsContext gc, Piece piece, double x, double y, double px, double py,
                                   double echelle, Color fondPiece, Color fondCanvas, Color contour) {
        gc.setFill(fondPiece);
        gc.fillRect(x, y, px, py);

        if (piece.getMurs().size() < 4) {
            gc.setStroke(contour);
            gc.setLineWidth(1.5);
            gc.strokeRect(x + 0.5, y + 0.5, px - 1, py - 1);
            return;
        }

        dessinerMurAvecTrous(gc, x, y, x + px, y, piece.getMurs().get(0), echelle, fondCanvas, contour, true);
        dessinerMurAvecTrous(gc, x + px, y, x + px, y + py, piece.getMurs().get(1), echelle, fondCanvas, contour, false);
        dessinerMurAvecTrous(gc, x + px, y + py, x, y + py, piece.getMurs().get(2), echelle, fondCanvas, contour, true);
        dessinerMurAvecTrous(gc, x, y + py, x, y, piece.getMurs().get(3), echelle, fondCanvas, contour, false);
    }

    private void dessinerMurAvecTrous(GraphicsContext gc, double x1, double y1, double x2, double y2,
                                      Mur mur, double echelle, Color fondCanvas, Color contour,
                                      boolean horizontal) {
        double longueur = mur.longueur();
        if (longueur <= 0) {
            return;
        }

        double dx = (x2 - x1) / longueur;
        double dy = (y2 - y1) / longueur;
        double epaisseur = 7;
        double murLenPx = Math.hypot(x2 - x1, y2 - y1);

        List<Ouverture> ouvertures = new ArrayList<>(mur.getOuvertures());
        ouvertures.sort(Comparator.comparingDouble(Ouverture::getPositionSurMur));

        gc.setStroke(contour);
        gc.setLineWidth(2);

        double posFinSegment = 0;
        for (Ouverture o : ouvertures) {
            double pos = o.getPositionSurMur();
            double fin = pos + o.getLargeur();
            boolean porte = o instanceof Ouverture.Porte;

            gc.strokeLine(
                    x1 + dx * posFinSegment, y1 + dy * posFinSegment,
                    x1 + dx * pos, y1 + dy * pos);

            double gapPxReel = o.getLargeur() * echelle;
            double gapPxVisuel = porte
                    ? Math.max(gapPxReel * 2.0, 32)
                    : Math.max(gapPxReel * 2.0, 26);
            double posPx = (pos / longueur) * murLenPx;
            double debutGapPx = posPx + (gapPxReel - gapPxVisuel) / 2;
            double finGapPx = debutGapPx + gapPxVisuel;

            double gx1 = x1 + (x2 - x1) * debutGapPx / murLenPx;
            double gy1 = y1 + (y2 - y1) * debutGapPx / murLenPx;
            double gx2 = x1 + (x2 - x1) * finGapPx / murLenPx;
            double gy2 = y1 + (y2 - y1) * finGapPx / murLenPx;
            double gapPx = Math.hypot(gx2 - gx1, gy2 - gy1);

            gc.setFill(fondCanvas);
            if (horizontal) {
                gc.fillRect(gx1, gy1 - epaisseur, gapPx, epaisseur * 2);
            } else {
                gc.fillRect(gx1 - epaisseur, gy1, epaisseur * 2, gapPx);
            }

            if (!porte) {
                gc.setFill(Color.web("#a8d4f0"));
                if (horizontal) {
                    gc.fillRect(gx1 + 2, gy1 - epaisseur + 2, Math.max(4, gapPx - 4), epaisseur * 2 - 4);
                } else {
                    gc.fillRect(gx1 - epaisseur + 2, gy1 + 2, epaisseur * 2 - 4, Math.max(4, gapPx - 4));
                }
            }

            posFinSegment = fin;
        }

        gc.setStroke(contour);
        gc.strokeLine(
                x1 + dx * posFinSegment, y1 + dy * posFinSegment,
                x2, y2);
    }

    private void dessinerTextePiece(GraphicsContext gc, Piece p, double x, double y, double px, double py) {
        double cx = x + px / 2;
        double cy = y + py / 2;
        double taille = Math.max(11, Math.min(15, Math.min(px, py) / 6));
        double interligne = taille * 1.25;

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFill(Color.web("#2c3e50"));
        gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, taille));
        gc.fillText("Piece " + p.getIdPiece(), cx, cy - interligne);
        gc.setFont(Font.font("Segoe UI", taille));
        gc.fillText(p.getUsage(), cx, cy);
        if (p.getNombrePortes() > 0 || p.getNombreFenetres() > 0) {
            gc.setFont(Font.font("Segoe UI", Math.max(9, taille - 2)));
            gc.setFill(Color.web("#4a5a6a"));
            gc.fillText(p.getNombrePortes() + " porte(s), " + p.getNombreFenetres() + " fenetre(s)",
                    cx, cy + interligne);
        }
    }

    private void alerte(Alert.AlertType type, String msg) {
        Alert a = new Alert(type);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
