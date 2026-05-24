package fr.insa.batiment.view;

import fr.insa.batiment.model.Devis;
import fr.insa.batiment.model.Revetement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainView {

    private static final Insets MARGES_ONGLET = new Insets(16);

    private final StackPane root = new StackPane();
    private final VBox paneAccueil = new VBox();
    private final BorderPane paneApplication = new BorderPane();
    private final Button btnMaison = new Button("Maison");
    private final Button btnImmeuble = new Button("Immeuble");
    private final TabPane tabPane = new TabPane();
    private final Label barreEtat = new Label("Choisissez le type de batiment.");

    private final TableView<Revetement> tableCatalogue = new TableView<>();
    private final Button btnChargerCatalogue = new Button("Charger catalogue");
    private final Button btnSauverCatalogue = new Button("Sauvegarder catalogue");

    private final TextField tfId = new TextField();
    private final ComboBox<String> cbUsage = new ComboBox<>();
    private final TextField tfLargeur = new TextField();
    private final TextField tfLongueur = new TextField();
    private final TextField tfHauteur = new TextField("2.50");
    private final ComboBox<Revetement> cbMur = new ComboBox<>();
    private final ComboBox<Revetement> cbSol = new ComboBox<>();
    private final ComboBox<Revetement> cbPlafond = new ComboBox<>();
    private final TextField tfPortes = new TextField("0");
    private final TextField tfFenetres = new TextField("0");
    private final Button btnAjouterPiece = new Button("Ajouter piece");
    private final Label lblMessagePiece = new Label();

    private final TableView<Devis.LigneDevis> tableDevis = new TableView<>();
    private final Label lblTotal = new Label("Total general : 0,00 EUR");
    private final Button btnCalculer = new Button("Calculer devis");
    private final Button btnExporter = new Button("Exporter devis en fichier texte");
    private final Button btnSauvegarderProjet = new Button("Sauvegarder projet (.txt)");

    private final Canvas canvasPlan = new Canvas(800, 500);
    private final Label lblPlanTitre = new Label("Visualisation simplifiee du batiment");
    private final Label lblPlanInfo = new Label("Aucune piece a afficher.");

    public MainView() {
        cbUsage.getItems().addAll(
                "Chambre", "Salon", "Cuisine", "Salle de bain", "Couloir", "Dégagement", "Bureau");
        cbUsage.getSelectionModel().selectFirst();
        construire();
    }

    private void construire() {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(
                new Tab("Catalogue", creerOngletCatalogue()),
                new Tab("Creation piece", creerOngletPiece()),
                new Tab("Devis", creerOngletDevis()),
                new Tab("Plan 2D", creerOngletPlan()));

        barreEtat.setStyle("-fx-padding: 8 12; -fx-background-color: #eef2f6; -fx-border-color: #ccd;");
        barreEtat.setMaxWidth(Double.MAX_VALUE);
        paneApplication.setCenter(tabPane);
        paneApplication.setBottom(barreEtat);

        construireAccueil();
        root.getChildren().addAll(paneApplication, paneAccueil);
        afficherAccueil();
    }

    private void construireAccueil() {
        Label titre = new Label("Projet Info");
        titre.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        Label sousTitre = new Label("Selectionnez le type de batiment pour commencer");
        sousTitre.setStyle("-fx-font-size: 14px; -fx-text-fill: #5a6a7a;");

        btnMaison.setPrefWidth(160);
        btnMaison.setPrefHeight(40);
        btnImmeuble.setPrefWidth(160);
        btnImmeuble.setPrefHeight(40);
        btnMaison.setStyle("-fx-font-size: 13px;");
        btnImmeuble.setStyle("-fx-font-size: 13px;");

        HBox boutons = new HBox(20, btnMaison, btnImmeuble);
        boutons.setAlignment(Pos.CENTER);

        paneAccueil.setAlignment(Pos.CENTER);
        paneAccueil.setSpacing(18);
        paneAccueil.setPadding(new Insets(40));
        paneAccueil.setStyle("-fx-background-color: #f0f4f8;");
        paneAccueil.getChildren().addAll(titre, sousTitre, boutons);
    }

    public void afficherAccueil() {
        paneAccueil.setVisible(true);
        paneApplication.setVisible(false);
    }

    public void afficherApplication() {
        paneAccueil.setVisible(false);
        paneApplication.setVisible(true);
    }

    private Label creerTitreOnglet(String texte) {
        Label titre = new Label(texte);
        titre.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        return titre;
    }

    private HBox creerBarreBoutons(Button... boutons) {
        HBox barre = new HBox(12, boutons);
        barre.setAlignment(Pos.CENTER_LEFT);
        barre.setPadding(new Insets(12, 0, 0, 0));
        return barre;
    }

    private BorderPane creerOngletCatalogue() {
        TableColumn<Revetement, Integer> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(new PropertyValueFactory<>("idRevetement"));
        TableColumn<Revetement, String> colDes = new TableColumn<>("Designation");
        colDes.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colDes.setPrefWidth(180);
        TableColumn<Revetement, String> colMur = new TableColumn<>("Mur");
        colMur.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().isPourMur() ? "Oui" : "Non"));
        TableColumn<Revetement, String> colSol = new TableColumn<>("Sol");
        colSol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().isPourSol() ? "Oui" : "Non"));
        TableColumn<Revetement, String> colPlaf = new TableColumn<>("Plafond");
        colPlaf.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().isPourPlafond() ? "Oui" : "Non"));
        TableColumn<Revetement, Double> colPrix = new TableColumn<>("Prix unitaire");
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        formaterColonneDouble(colPrix);
        tableCatalogue.getColumns().addAll(colId, colDes, colMur, colSol, colPlaf, colPrix);

        BorderPane pane = new BorderPane();
        pane.setTop(creerTitreOnglet("Catalogue des revetements"));
        pane.setCenter(tableCatalogue);
        pane.setBottom(creerBarreBoutons(btnChargerCatalogue, btnSauverCatalogue));
        BorderPane.setMargin(pane.getTop(), new Insets(0, 0, 10, 0));
        BorderPane.setMargin(tableCatalogue, new Insets(0, 0, 0, 0));
        VBox contenu = new VBox(pane);
        contenu.setPadding(MARGES_ONGLET);
        BorderPane wrapper = new BorderPane(contenu);
        return wrapper;
    }

    private VBox creerOngletPiece() {
        GridPane g = new GridPane();
        g.setHgap(12);
        g.setVgap(10);
        g.setPadding(new Insets(8, 0, 0, 0));
        int r = 0;
        g.addRow(r++, new Label("Id piece :"), tfId);
        g.addRow(r++, new Label("Type de piece :"), cbUsage);
        g.addRow(r++, new Label("Largeur (m) :"), tfLargeur);
        g.addRow(r++, new Label("Longueur (m) :"), tfLongueur);
        g.addRow(r++, new Label("Hauteur sous plafond (m) :"), tfHauteur);
        g.addRow(r++, new Label("Revetement mur :"), cbMur);
        g.addRow(r++, new Label("Revetement sol :"), cbSol);
        g.addRow(r++, new Label("Revetement plafond :"), cbPlafond);
        g.addRow(r++, new Label("Nombre de portes :"), tfPortes);
        g.addRow(r, new Label("Nombre de fenetres :"), tfFenetres);

        lblMessagePiece.setStyle("-fx-text-fill: #006600;");
        VBox box = new VBox(12,
                creerTitreOnglet("Creation d'une piece rectangulaire"),
                g,
                creerBarreBoutons(btnAjouterPiece),
                lblMessagePiece);
        box.setPadding(MARGES_ONGLET);
        return box;
    }

    private BorderPane creerOngletDevis() {
        TableColumn<Devis.LigneDevis, String> colRev = new TableColumn<>("Revetement");
        colRev.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getRevetement().getDesignation()));
        colRev.setPrefWidth(180);

        TableColumn<Devis.LigneDevis, String> colSupport = new TableColumn<>("Support");
        colSupport.setCellValueFactory(new PropertyValueFactory<>("support"));
        colSupport.setPrefWidth(90);

        TableColumn<Devis.LigneDevis, Double> colSurf = new TableColumn<>("Surface totale (m2)");
        colSurf.setCellValueFactory(new PropertyValueFactory<>("surfaceTotale"));
        formaterColonneDouble(colSurf);

        TableColumn<Devis.LigneDevis, Double> colPu = new TableColumn<>("Prix unitaire");
        colPu.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        formaterColonneDouble(colPu);

        TableColumn<Devis.LigneDevis, Double> colPt = new TableColumn<>("Prix total");
        colPt.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));
        formaterColonneDouble(colPt);

        tableDevis.getColumns().addAll(colRev, colSupport, colSurf, colPu, colPt);

        lblTotal.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        VBox bas = new VBox(12, lblTotal, creerBarreBoutons(btnCalculer, btnExporter, btnSauvegarderProjet));

        BorderPane pane = new BorderPane();
        pane.setTop(creerTitreOnglet("Devis estimatif"));
        pane.setCenter(tableDevis);
        pane.setBottom(bas);
        BorderPane.setMargin(pane.getTop(), new Insets(0, 0, 10, 0));
        BorderPane.setMargin(tableDevis, new Insets(0, 0, 8, 0));

        BorderPane wrapper = new BorderPane(new VBox(pane));
        wrapper.setPadding(MARGES_ONGLET);
        return wrapper;
    }

    private BorderPane creerOngletPlan() {
        ScrollPane scroll = new ScrollPane(canvasPlan);
        scroll.setFitToWidth(false);
        scroll.setPannable(true);
        scroll.setStyle("-fx-background-color: #f0f4f8;");

        lblPlanTitre.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        lblPlanInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #5a6a7a;");

        BorderPane pane = new BorderPane(scroll);
        VBox haut = new VBox(8, lblPlanTitre, lblPlanInfo);
        haut.setPadding(new Insets(0, 0, 10, 0));
        pane.setTop(haut);

        BorderPane wrapper = new BorderPane(pane);
        wrapper.setPadding(MARGES_ONGLET);
        return wrapper;
    }

    private <T> void formaterColonneDouble(TableColumn<T, Double> colonne) {
        colonne.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double valeur, boolean vide) {
                super.updateItem(valeur, vide);
                setText(vide || valeur == null ? null : String.format("%.2f", valeur));
            }
        });
        colonne.setStyle("-fx-alignment: CENTER-RIGHT;");
    }

    public Region getRoot() { return root; }
    public Button getBtnMaison() { return btnMaison; }
    public Button getBtnImmeuble() { return btnImmeuble; }
    public TabPane getTabPane() { return tabPane; }
    public Label getBarreEtat() { return barreEtat; }
    public TableView<Revetement> getTableCatalogue() { return tableCatalogue; }
    public Button getBtnChargerCatalogue() { return btnChargerCatalogue; }
    public Button getBtnSauverCatalogue() { return btnSauverCatalogue; }
    public TextField getTfId() { return tfId; }
    public ComboBox<String> getCbUsage() { return cbUsage; }
    public TextField getTfLargeur() { return tfLargeur; }
    public TextField getTfLongueur() { return tfLongueur; }
    public TextField getTfHauteur() { return tfHauteur; }
    public ComboBox<Revetement> getCbMur() { return cbMur; }
    public ComboBox<Revetement> getCbSol() { return cbSol; }
    public ComboBox<Revetement> getCbPlafond() { return cbPlafond; }
    public TextField getTfPortes() { return tfPortes; }
    public TextField getTfFenetres() { return tfFenetres; }
    public Button getBtnAjouterPiece() { return btnAjouterPiece; }
    public Label getLblMessagePiece() { return lblMessagePiece; }
    public TableView<Devis.LigneDevis> getTableDevis() { return tableDevis; }
    public Label getLblTotal() { return lblTotal; }
    public Button getBtnCalculer() { return btnCalculer; }
    public Button getBtnExporter() { return btnExporter; }
    public Button getBtnSauvegarderProjet() { return btnSauvegarderProjet; }
    public Canvas getCanvasPlan() { return canvasPlan; }
    public Label getLblPlanTitre() { return lblPlanTitre; }
    public Label getLblPlanInfo() { return lblPlanInfo; }
}
