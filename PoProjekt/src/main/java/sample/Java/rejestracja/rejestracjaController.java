package sample.Java.rejestracja;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sample.Java.dbController;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class rejestracjaController implements Initializable {

    boolean dodany = true;
    boolean czyPesel = false;

    @FXML
    ObservableList<String> stanowiskoList = FXCollections.observableArrayList("Kierownik", "Magazyn", "Wysylka");
    public ChoiceBox<String> stanowiskoBox = new ChoiceBox<String>();
    @FXML
    ObservableList<String> miesiacList = FXCollections.observableArrayList("Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień");
    public ChoiceBox<String> miesiacBox = new ChoiceBox<String>();
    @FXML
    public TextField login;
    public TextField haslo;
    public TextField pHaslo;
    public TextField imie;
    public TextField nazwisko;
    public TextField pesel;
    public TextField dzien_ur;
    public TextField rok_ur;
    public TextField miasto;
    public TextField ulica;
    public TextField nrdomu;
    public TextField nrmieszkania;


    public void buttonRejestracja(ActionEvent actionEvent) throws SQLException {
        dodany = true;

        String loginGet = login.getText();
            if(loginGet.length()<1 || loginGet.length()>16){
                JOptionPane.showMessageDialog(null, "Zły wprowadzony login, wprowadź od 1-16 znaków!");
                dodany=false;
            }
        String hasloGet = haslo.getText();
            if(hasloGet.length()<1 || hasloGet.length()>16){
                JOptionPane.showMessageDialog(null, "Złe wprowadzone hasło, wprowadź od 1-16 znaków!");
                dodany=false;
            }
        String pHasloGet = pHaslo.getText();
            if(!hasloGet.equals(pHasloGet)){
                JOptionPane.showMessageDialog(null, "Hasła się nie zgadzają!");
                dodany=false;
            }
        String stanowiskoGet = stanowiskoBox.getValue();
            if(stanowiskoGet==null){
                JOptionPane.showMessageDialog(null, "Nie wybrano stanowiska!");
                dodany=false;
            }
        String imieGet = imie.getText();
            if(imieGet.length()==0){
                JOptionPane.showMessageDialog(null, "Nie wpisano imienia!");
                dodany=false;
            }
        String nazwiskoGet = nazwisko.getText();
            if(nazwiskoGet.length()==0){
                JOptionPane.showMessageDialog(null, "Nie wpisano nazwiska!");
                dodany=false;
            }
        String peselGet = pesel.getText();
            System.out.println(peselGet);
            if(sprPesel(peselGet)){
                JOptionPane.showMessageDialog(null, "Pesel znajduję się już w bazię danych!");
                dodany=false;
            }
            if(peselGet.length()!=11 || peselGet.equals("00000000000")){
                JOptionPane.showMessageDialog(null, "Błędny pesel!");
                dodany=false;
            }else{
            if(!sumaKontrolna(peselGet)){
                JOptionPane.showMessageDialog(null, "Błędna suma kontrolna numeru pesel!");
                dodany=false;
            }}

        int dzien_urGet = Integer.parseInt(dzien_ur.getText());
            if(dzien_urGet>0 || dzien_urGet<=31){
                if(miesiacBox.getValue()=="Luty" && dzien_urGet>=29){
                    JOptionPane.showMessageDialog(null, "Luty ma 28-29 dni!");
                    dodany=false;
                }
                if(dzien_urGet>=30 && (miesiacBox.getValue()=="Kwiecień" || miesiacBox.getValue()=="Czerwiec" || miesiacBox.getValue()=="Wrzesień" || miesiacBox.getValue()=="Listopad")){
                    JOptionPane.showMessageDialog(null, "Ten miesiąc ma 30 dni");
                    dodany=false;
                }
            }else{
                JOptionPane.showMessageDialog(null, "Błędny dzień urodzenia, 1-31!");
                dodany=false;
            }

        String miesiac_urGet = miesiacBox.getValue();
            if(miesiac_urGet==null){
                JOptionPane.showMessageDialog(null, "Nie wybrano miesiąca!");
                dodany=false;
            }

        int rok_urGet = Integer.parseInt(rok_ur.getText());
            if(rok_urGet<1919 || rok_urGet>2001){
                JOptionPane.showMessageDialog(null, "Błędny rok urodzenia!");
                dodany=false;
            }

        String miastoGet = miasto.getText();
            if(miastoGet.length()==0){
                JOptionPane.showMessageDialog(null, "Nie podano miasta!");
                dodany=false;
            }

        String ulicaGet = ulica.getText();
            if(ulicaGet.length()==0){
                JOptionPane.showMessageDialog(null, "Nie podano ulicy!");
                dodany=false;
            }


        //int nrmieszkaniaGet;



        Connection conn = dbController.connect();

        String sql = "INSERT INTO pracownicy(login, haslo, stanowisko, imie, nazwisko, pesel, dzien_ur, miesiac_ur, rok_ur, miasto, ulica, nr_domu, nr_mieszkania) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        if (dodany) {
            pstmt.setString(1, loginGet);
            pstmt.setString(2, hasloGet);
            pstmt.setString(3, stanowiskoGet);
            pstmt.setString(4, imieGet);
            pstmt.setString(5, nazwiskoGet);
            pstmt.setString(6, peselGet);
            pstmt.setInt(7, dzien_urGet);
            pstmt.setString(8, miesiac_urGet);
            pstmt.setInt(9, rok_urGet);
            pstmt.setString(10, miastoGet);
            pstmt.setString(11, ulicaGet);
            pstmt.setInt(12, nrDomu());
            pstmt.setInt(13, nrMieszkania());

            //pstmt.execute();

            conn.close();
        }else{
            JOptionPane.showMessageDialog(null, "Nie udało się dodać pracownika!");
        }
    }
//NumberFormatException

    public boolean sumaKontrolna(String pesel){
        int a = Character.getNumericValue(pesel.charAt(0));
        int b = Character.getNumericValue(pesel.charAt(1));
        int c = Character.getNumericValue(pesel.charAt(2));
        int d = Character.getNumericValue(pesel.charAt(3));
        int e = Character.getNumericValue(pesel.charAt(4));
        int f = Character.getNumericValue(pesel.charAt(5));
        int g = Character.getNumericValue(pesel.charAt(6));
        int h = Character.getNumericValue(pesel.charAt(7));
        int i = Character.getNumericValue(pesel.charAt(8));
        int j = Character.getNumericValue(pesel.charAt(9));
        int sK = ((a*9) + (b*7) + (c*3) + (d*1) + (e*9) + (f*7) + (g*3) + (h*1) + (i*9) + (j*7)) % 10;

        if(sK == Character.getNumericValue(pesel.charAt(10))){
            return true;
        }else{
            return false;
        }
    }
    public int nrMieszkania(){
        int nrmieszkaniaGet= Integer.parseInt(null);
        try {
            nrmieszkaniaGet = Integer.parseInt(nrmieszkania.getText());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Wpisano tekst!");
            dodany=false;
        }
        return nrmieszkaniaGet;
    }

    public int nrDomu(){
        int nrdomuGet = Integer.parseInt(null);
        try{
            nrdomuGet = Integer.parseInt(nrdomu.getText());
            if(nrdomuGet==0){
                JOptionPane.showMessageDialog(null, "Błędny numer domu!");
                dodany=false;
            }}catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Wpisano tekst!");
            dodany=false;
        }
        return nrdomuGet;
    }

    public boolean sprPesel(String pesel) throws SQLException {
        String sqlSelect = "SELECT * FROM pracownicy WHERE pesel='"+pesel+"'";
        Connection conn = dbController.connect();
        PreparedStatement ps = conn.prepareStatement(sqlSelect);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            czyPesel = true;
        }
        return czyPesel;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //stanowiskoBox.setValue("Kierownik");
        stanowiskoBox.setItems(stanowiskoList);
        miesiacBox.setItems(miesiacList);
    }
}