package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private static final String url = "jdbc:mysql://localhost:3306/GestionReservation";
    private static final String user = "root";
    private static final String password = "";
    private static Connection cnx;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver OK");
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion établie");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getCnx() {
        return cnx;
    }

    public static void closeCnx() {
        if (cnx != null) {
            try {
                cnx.close();
                System.out.println("Connexion fermée");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}