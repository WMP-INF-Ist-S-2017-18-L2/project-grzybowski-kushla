package sample.Java;

import java.sql.*;

public class dbController {
    public static Connection connect() throws SQLException {
        Connection conn = null;
        String url = "jdbc:mysql://db4free.net:3306/projektmagazyn?user=projektmag&password=projektmagazyn&useSSL=false";
        return conn = DriverManager.getConnection(url);
    }
}
