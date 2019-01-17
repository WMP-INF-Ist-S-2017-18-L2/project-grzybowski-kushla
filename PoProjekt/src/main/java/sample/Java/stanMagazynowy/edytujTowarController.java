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
import sample.Java.dbController;

public class edytujTowarController extends stanMagazynowy implements Initializable {

    public edytujTowarController() throws SQLException {
    }
    @FXML
    public TextField kodTowaruTF;
    public TextField nazwaTF;
    public TextField iloscTF;
    public TextField miejsceTF;
    public TableView<Map> stMag;
    public TableColumn Id;
    public TableColumn kodTowaru;
    public TableColumn nazwa;
    public TableColumn miejsce;
    public TableColumn ilosc;
    public javafx.scene.control.Button wroc;
    ObservableList<String> filtrList = FXCollections.observableArrayList("Kod towaru", "Nazwa", "Miejsce", "Ilość");
    public ChoiceBox<String> filtrBox = new ChoiceBox<String>();
    public TextField filtr;
    public String sFiltr = "";
    public String wybFiltr = "";
    public boolean filtrowana = false;
    ResultSet rs;
    int pId = 0;
    public String sKodTowaru;
    public String sNazwaTowaru;
    public String sMiejsce;
    public int sIlosc;
    public boolean wybrano = false;

    public void loadCustomers() {
        System.out.println(sFiltr.length());
        System.out.println(wybFiltr.length());
        kodTowaru.setCellValueFactory(new MapValueFactory("kodTowaru"));
        nazwa.setCellValueFactory(new MapValueFactory("nazwa"));
        miejsce.setCellValueFactory(new MapValueFactory("miejsce"));
        ilosc.setCellValueFactory(new MapValueFactory("ilosc"));

        stMag.setItems(getCustomerData(wybFiltr, sFiltr));//Which returns a ObservableList<Map>

    }

    public void menuWybierz(ActionEvent actionEvent) throws SQLException {
        Object wybranyTowar = stMag.getSelectionModel().getSelectedItems().get(0);
        if(wybranyTowar!=null){
            String wybKodTowaru = wybranyTowar.toString().split(",")[3].substring(1);
            String wybNazwa = wybranyTowar.toString().split(",")[2].substring(1);
            String wybMiejsce = wybranyTowar.toString().split(",")[0].substring(1);
            String wybIlosc = wybranyTowar.toString().split(",")[1].substring(1);
            wybKodTowaru = wybKodTowaru.substring(10, wybKodTowaru.length() - 1);
            wybNazwa = wybNazwa.substring(6);
            wybMiejsce = wybMiejsce.substring(8);
            wybIlosc = wybIlosc.substring(6);
            kodTowaruTF.setText(wybKodTowaru);
            miejsceTF.setText(wybMiejsce);
            iloscTF.setText(wybIlosc);
            nazwaTF.setText(wybNazwa);

            Connection conn = dbController.connect();
            String sqlSelect = "SELECT * FROM stanmagazynowy WHERE kod_towaru='"+wybKodTowaru+"' AND nazwa='"+wybNazwa+"'AND miejsce='"+wybMiejsce+"'";
            PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
            rs=psSelect.executeQuery();
            if(rs.next()) {
                pId = rs.getInt("id");
                wybrano=true;
            }
        }
    }

    public void menuZatwierdz(ActionEvent actionEvent) throws SQLException {
        if(wybrano){
        Connection conn = dbController.connect();
        sKodTowaru = kodTowaruTF.getText();
        sNazwaTowaru = nazwaTF.getText();
        sMiejsce = miejsceTF.getText();
        sIlosc = Integer.parseInt(iloscTF.getText());
        String sqlUpdate ="UPDATE stanmagazynowy SET kod_towaru='"+sKodTowaru+"',nazwa= '"+sNazwaTowaru+"',miejsce='"+sMiejsce+"',ilosc='"+sIlosc+"'WHERE id="+pId;
        PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
        psUpdate.execute();
            JOptionPane.showMessageDialog(null, "Edytowano towar!");
        }else{
            JOptionPane.showMessageDialog(null, "Nie wybrano towaru!");
        }
    }
    public void menuOdswiez(ActionEvent actionEvent){
        loadCustomers();
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

    }
    public void menuCzysc(ActionEvent actionEvent) {
        filtr.clear();
        loadCustomers();
    }
    public void menuWroc(ActionEvent actionEvent){
        Stage stage = (Stage) wroc.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filtrBox.setValue("Kod towaru");
        filtrBox.setItems(filtrList);

        loadCustomers();
    }
}
