package sample.Java.stanMagazynowy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class stanMagazynowy {

    public static final String TABLE_NAME = "stanmagazynowy";
    QueryClass qc = new QueryClass();

    public stanMagazynowy() throws SQLException { }

    public ObservableList<Map> getCustomerData( String wybFiltr, String filtr) {
        ObservableList<Map> stMag = FXCollections.observableArrayList();
        try {

            if((wybFiltr.length()==0) || (filtr.length()==0)) {

                qc.setPs(qc.getConn().prepareStatement("SELECT * FROM " + TABLE_NAME));
            }else{
                System.out.println(filtr);
                System.out.println(wybFiltr);
                qc.setPs(qc.getConn().prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE " + wybFiltr + " LIKE '" + filtr +"'"));
            }

            qc.setRs(qc.getPs().executeQuery());
            while (qc.getRs().next()) {
                Map<String, String> towar = new HashMap<>();
                //product.put("id", String.valueOf(qc.getRs().getInt("id")));
                towar.put("kodTowaru", qc.getRs().getString("kod_towaru"));
                towar.put("nazwa", qc.getRs().getString("nazwa"));
                towar.put("miejsce", qc.getRs().getString("miejsce"));
                towar.put("ilosc", qc.getRs().getString("ilosc"));
                stMag.add(towar);

            }
            return stMag;
        } catch (SQLException ex) {
            return null;
        }
    }
}
