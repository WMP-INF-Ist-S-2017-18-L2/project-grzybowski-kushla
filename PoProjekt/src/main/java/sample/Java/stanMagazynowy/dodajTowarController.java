package sample.Java.stanMagazynowy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Java.dbController;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dodajTowarController {
    @FXML
    public TextField kodTowaru;
    public TextField nazwaTowaru;
    public TextField miejsce;
    public TextField ilosc;
    public javafx.scene.control.Button wroc;

    public String sKodTowaru;
    public String sNazwaTowaru;
    public String sMiejsce;
    public int sIlosc;
    ResultSet rs;
    public int pIlosc;
    public int pId;


    public void menuCzysc(ActionEvent actionEvent) {
        kodTowaru.clear();
        nazwaTowaru.clear();
        miejsce.clear();
        ilosc.clear();
    }

    public void menuDodaj(ActionEvent actionEvent) throws SQLException {
        sKodTowaru = kodTowaru.getText();
        sNazwaTowaru = nazwaTowaru.getText();
        sMiejsce = miejsce.getText();
        sIlosc = Integer.parseInt(ilosc.getText());

        Connection conn = dbController.connect();
        String sqlSelect = "SELECT * FROM stanmagazynowy WHERE kod_towaru='"+sKodTowaru+"' AND nazwa='"+sNazwaTowaru+"'AND miejsce='"+sMiejsce+"'";
        String sqlInsert = "INSERT INTO stanmagazynowy(kod_towaru, nazwa, miejsce, ilosc) VALUES (?, ?, ?, ?);";
        PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
        rs=psSelect.executeQuery();
        System.out.println(rs);
        if(rs.next()){
            pIlosc = rs.getInt("ilosc");
            pId = rs.getInt("id");
            pIlosc=pIlosc+sIlosc;
            String sqlUpdate ="UPDATE stanmagazynowy SET ilosc='"+pIlosc+"'WHERE id="+pId;
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.execute();
            JOptionPane.showMessageDialog(null, "Dodano do istniejącego!");

            //System.out.println(pId+"|"+pIlosc);
        }else{

        PreparedStatement pstmt = conn.prepareStatement(sqlInsert);

        pstmt.setString(1, sKodTowaru);
        pstmt.setString(2, sNazwaTowaru);
        pstmt.setString(3, sMiejsce);
        pstmt.setInt(4, sIlosc);

        try {
            pstmt.execute();
            JOptionPane.showMessageDialog(null, "Dodano towar!");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Nie udało sie dodać towaru!");
        }

        conn.close();


    }}
    public void menuWroc(ActionEvent actionEvent){
        Stage stage = (Stage) wroc.getScene().getWindow();
        stage.close();
    }

}