package sample.Java.stanMagazynowy;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
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
    public TableColumn kod_towaru;
    @FXML
    public TableColumn nazwa;
    @FXML
    public TableColumn miejsce;

    public stMagController() throws SQLException { }

    public void loadCustomers() {

        kod_towaru.setCellValueFactory(new MapValueFactory("kod_towaru"));
        nazwa.setCellValueFactory(new MapValueFactory("nazwa"));
        miejsce.setCellValueFactory(new MapValueFactory("miejsce"));

        stMag.setItems(getCustomerData());//Which returns a ObservableList<Map>
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCustomers();
    }
}