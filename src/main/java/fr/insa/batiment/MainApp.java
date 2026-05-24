package fr.insa.batiment;

import fr.insa.batiment.controller.MainController;
import fr.insa.batiment.model.ProjetModel;
import fr.insa.batiment.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        ProjetModel model = new ProjetModel();
        MainView view = new MainView();
        new MainController(model, view);
        stage.setTitle("Projet Info");
        stage.setScene(new Scene(view.getRoot(), 1000, 700));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
