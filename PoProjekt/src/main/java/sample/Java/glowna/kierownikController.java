package sample.Java.glowna;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.lang.*;

public class kierownikController implements Initializable {

    private Scene logowanieScene;
    public void setLogowanieScene(Scene scene){
        logowanieScene = scene;
    }

    public void menuRejestracja(ActionEvent actionEvent) throws IOException {
        FXMLLoader rejestracjaLoader = new FXMLLoader(getClass().getResource("/rejestracja.fxml"));
        Parent rejestracja = (Parent) rejestracjaLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(rejestracja));
        stage.show();
    }

    public void menuStanMagazynowy(ActionEvent actionEvent) throws IOException {
        FXMLLoader stanMagazynowyLoader = new FXMLLoader(getClass().getResource("/stanMagazynowy.fxml"));
        Parent stanMagazynowy = (Parent) stanMagazynowyLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(stanMagazynowy));
        stage.show();
    }

    public void menuWyloguj(ActionEvent actionEvent) throws IOException {
        Stage primaryScene = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryScene.setScene(logowanieScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}