package sample.Java.rejestracja;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sample.Java.dbController;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class rejestracjaController implements Initializable {

    @FXML
    ObservableList<String> stanowiskoList = FXCollections.observableArrayList("Kierownik", "Magazyn", "Pakownia", "Wysylka");
    public ChoiceBox<String> stanowiskoBox = new ChoiceBox<String>();
    @FXML
    ObservableList<String> miesiacList = FXCollections.observableArrayList("Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień");
    public ChoiceBox<String> miesiacBox = new ChoiceBox<String>();
    @FXML
    public TextField login;
    public TextField haslo;
    public TextField imie;
    public TextField nazwisko;
    public TextField pesel;
    public TextField dzien_ur;
    public TextField rok_ur;
    public TextField miasto;
    public TextField nrdomu;
    public TextField nrmieszkania;

    public void buttonRejestracja(ActionEvent actionEvent) throws SQLException {
        String loginGet = login.getText();
        String hasloGet = haslo.getText();
        String stanowiskoGet = stanowiskoBox.getValue();
        String imieGet = imie.getText();
        String nazwiskoGet = nazwisko.getText();
        int peselGet = Integer.parseInt(pesel.getText());
        int dzien_urGet = Integer.parseInt(dzien_ur.getText());
        String miesiac_urGet = miesiacBox.getValue();
        int rok_urGet = Integer.parseInt(rok_ur.getText());
        String miastoGet = miasto.getText();
        int nrdomuGet = Integer.parseInt(nrdomu.getText());
        int nrmieszkaniaGet = Integer.parseInt(nrmieszkania.getText());

        Connection conn = dbController.connect();

        String sql = "INSERT INTO pracownicy(login, haslo, stanowisko, imie, nazwisko, pesel, dzien_ur, miesiac_ur, rok_ur, miasto, nr_domu, nr_mieszkania) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, loginGet);
        pstmt.setString(2, hasloGet);
        pstmt.setString(3, stanowiskoGet);
        pstmt.setString(4, imieGet);
        pstmt.setString(5, nazwiskoGet);
        pstmt.setInt(6, peselGet);
        pstmt.setInt(7, dzien_urGet);
        pstmt.setString(8, miesiac_urGet);
        pstmt.setInt(9, rok_urGet);
        pstmt.setString(10, miastoGet);
        pstmt.setInt(11, nrdomuGet);
        pstmt.setInt(12, nrmieszkaniaGet);
        pstmt.execute();

        conn.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //stanowiskoBox.setValue("Kierownik");
        stanowiskoBox.setItems(stanowiskoList);
        miesiacBox.setItems(miesiacList);
    }
}