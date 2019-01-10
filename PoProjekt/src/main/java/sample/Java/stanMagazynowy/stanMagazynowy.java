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

    public ObservableList<Map> getCustomerData() {
        ObservableList<Map> stMag = FXCollections.observableArrayList();
        try {
            qc.setPs(qc.getConn().prepareStatement("SELECT * FROM " + TABLE_NAME));
            qc.setRs(qc.getPs().executeQuery());
            while (qc.getRs().next()) {
                Map<String, String> product = new HashMap<>();
                //product.put("id", String.valueOf(qc.getRs().getInt("id")));
                product.put("kod_towaru", qc.getRs().getString("kod_towaru"));
                product.put("nazwa", qc.getRs().getString("nazwa"));
                product.put("miejsce", qc.getRs().getString("miejsce"));
                stMag.add(product);
            }
            return stMag;
        } catch (SQLException ex) {
            return null;
        }
    }
}
