package sample.Java.logowanie;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sample.Java.dbController;
import javax.swing.*;

public class logowanieController implements Initializable {

    private Scene kierownikScene;
    private Scene magazynierScene;

    @FXML
    public javafx.scene.control.Button wyjdz;
    @FXML
    public TextField loginTF;
    @FXML
    public TextField hasloTF;

    Connection conn = dbController.connect();
    ResultSet rs;

    public logowanieController() throws SQLException { }

    public void setKierownikScene(Scene scene){
        kierownikScene = scene;
    }
    public void setMagazynierScene(Scene scene){
        magazynierScene = scene;
    }
    public void openScene(ActionEvent actionEvent) throws SQLException, IOException {

         if(loginTF.getLength()==0 || hasloTF.getLength()==0) {
             JOptionPane.showMessageDialog(null, "Nie wpisano loginu lub hasła!");
         }else{
             String queryLH = "Select stanowisko from pracownicy Where login='" + loginTF.getText() + "' and haslo='" + hasloTF.getText() + "'";
             PreparedStatement ps = conn.prepareStatement(queryLH);

             rs = ps.executeQuery();

             if(rs.next()){
                  switch (rs.getString("stanowisko")) {
                     case "Kierownik" : {
                         Stage primaryScene = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                         primaryScene.setScene(kierownikScene);
                         loginTF.clear();
                         hasloTF.clear();
                         break;
                 }
                      case "Magazyn" : {
                          Stage primaryScene = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                          primaryScene.setScene(magazynierScene);
                          loginTF.clear();
                          hasloTF.clear();
                          break;

                      }
                  }
             }else{
                 JOptionPane.showMessageDialog(null,"Zły login lub hasło!");
             }
}
    }

    public void menuWyjdz(ActionEvent actionEvent){
        Stage stage = (Stage) wyjdz.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


}

