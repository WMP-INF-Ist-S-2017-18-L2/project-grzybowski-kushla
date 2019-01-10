package sample.Java.glowna;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class magazynierController implements Initializable {

    private Scene logowanieScene;
    public void setLogowanieScene(Scene scene){
        logowanieScene = scene;
    }

    public void menuStanMagazynowy(ActionEvent actionEvent) throws IOException {
        FXMLLoader stanMagazynowyLoader = new FXMLLoader(getClass().getResource("/stanMagazynowy.fxml"));
        Parent stanMagazynowy = (Parent) stanMagazynowyLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(stanMagazynowy));;
        stage.show();
    }

    public void menuWyloguj(ActionEvent actionEvent) throws IOException {
        Stage primaryScene = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryScene.setScene(logowanieScene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}