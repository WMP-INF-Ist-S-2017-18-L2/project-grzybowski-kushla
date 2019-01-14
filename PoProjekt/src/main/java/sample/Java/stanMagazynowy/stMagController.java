package sample.Java.stanMagazynowy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Java.dbController;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Map;
import java.util.ResourceBundle;

public class stMagController extends stanMagazynowy implements Initializable {

    @FXML
    public TableView<Map> stMag;
    @FXML
    public TableColumn Id;
    @FXML
    public TableColumn kodTowaru;
    @FXML
    public TableColumn nazwa;
    @FXML
    public TableColumn miejsce;
    @FXML
    public TableColumn ilosc;
    @FXML
    ObservableList<String> filtrList = FXCollections.observableArrayList("Kod towaru", "Nazwa", "Miejsce", "Ilość");
    public ChoiceBox<String> filtrBox = new ChoiceBox<String>();
    @FXML
    public TextField filtr;
    public javafx.scene.control.Button wroc;

    public String sFiltr = "";
    public String wybFiltr = "";
    public boolean filtrowana = false;

    Connection conn = dbController.connect();

    public stMagController() throws SQLException { }
    public void loadCustomers() {
        System.out.println(sFiltr.length());
        System.out.println(wybFiltr.length());
        kodTowaru.setCellValueFactory(new MapValueFactory("kodTowaru"));
        nazwa.setCellValueFactory(new MapValueFactory("nazwa"));
        miejsce.setCellValueFactory(new MapValueFactory("miejsce"));
        ilosc.setCellValueFactory(new MapValueFactory("ilosc"));

        stMag.setItems(getCustomerData(wybFiltr, sFiltr));//Which returns a ObservableList<Map>

    }

    public void getRow() {

    }

    public void menuSzukaj(ActionEvent actionEvent) {
        switch (filtrBox.getValue()){
            case "Kod towaru":{
                wybFiltr = "kod_towaru";
                break;
            }
            case "Nazwa":{
                wybFiltr = "nazwa";
                break;
            }
            case "Miejsce":{
                wybFiltr = "miejsce";
                break;
            }
            case "Ilość":{
                wybFiltr = "ilosc";
                break;
            }
            default:{
                wybFiltr = "nazwa";
                break;
            }
        }
        sFiltr = filtr.getText();
        loadCustomers();
        sFiltr = "";
        wybFiltr = "";
        filtr.clear();
    }

    public void menuCzysc(ActionEvent actionEvent) {
        loadCustomers();
    }

    public void menuWroc(ActionEvent actionEvent){
        Stage stage = (Stage) wroc.getScene().getWindow();
        stage.close();
    }

    public void menuOdswiez(ActionEvent actionEvent){
        loadCustomers();
    }

    public void menuSkasuj(ActionEvent actionEvent) throws SQLException {
        Object wybranyTowar = stMag.getSelectionModel().getSelectedItems().get(0);
        if(wybranyTowar!=null){
        String wybMiejsce = wybranyTowar.toString().split(",")[0].substring(1);
        wybMiejsce = wybMiejsce.substring(8);
            System.out.println(wybMiejsce);
        String wybKodTowaru = wybranyTowar.toString().split(",")[3].substring(1);
        wybKodTowaru = wybKodTowaru.substring(10, wybKodTowaru.length() - 1);

        String sqlDelete = "DELETE FROM stanmagazynowy WHERE kod_towaru='"+wybKodTowaru+"' AND miejsce ='"+wybMiejsce+"'";
        PreparedStatement ps = conn.prepareStatement(sqlDelete);
        stMag.getItems().remove(wybranyTowar);
        ps.execute();

        System.out.println(wybKodTowaru);}else{
            JOptionPane.showMessageDialog(null, "Nie wybrano towaru!");
        }
    }

    public void menuDodaj(ActionEvent actionEvent) throws IOException {
        FXMLLoader stanMagazynowyLoader = new FXMLLoader(getClass().getResource("/dodajTowar.fxml"));
        Parent stanMagazynowy = (Parent) stanMagazynowyLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(stanMagazynowy));
        stage.show();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filtrBox.setValue("Kod towaru");
        filtrBox.setItems(filtrList);

        loadCustomers();
    }
}