package sample.Java.glowna;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Java.logowanie.logowanieController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader logowanieLoader = new FXMLLoader(getClass().getResource("/Logowanie.fxml"));
        Parent logowanie = logowanieLoader.load();
        Scene logowanieScene = new Scene(logowanie);

        FXMLLoader kierownikLoader = new FXMLLoader(getClass().getResource("/kierownik.fxml"));
        Parent kierownik = kierownikLoader.load();
        Scene kierownikScene = new Scene(kierownik);

        FXMLLoader magazynierLoader = new FXMLLoader(getClass().getResource("/magazynier.fxml"));
        Parent magazynier = magazynierLoader.load();
        Scene magazynierScene = new Scene(magazynier);

        logowanieController logowanieController = logowanieLoader.getController();
        logowanieController.setKierownikScene(kierownikScene);
        logowanieController.setMagazynierScene(magazynierScene);

        kierownikController kierownikController = kierownikLoader.getController();
        kierownikController.setLogowanieScene(logowanieScene);

        magazynierController magazynierController = magazynierLoader.getController();
        magazynierController.setLogowanieScene(logowanieScene);

        primaryStage.setScene(logowanieScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }{}
    //TODO Poprawic dodawanie i rejestracje, warunki
}