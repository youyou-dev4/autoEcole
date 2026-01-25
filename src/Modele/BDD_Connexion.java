package Modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BDD_Connexion {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/auto_ecole";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public Connection con;

    public BDD_Connexion() {
        try {
            this.con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection réussie!");
        } catch (SQLException ex) {
            System.out.println("Connection failed! " + ex.getMessage());
        }
    }

    public int checkLogin(String username, String password) {
        if (con == null) {
            return -1;
        }
        
        String sql = "SELECT * FROM utilisateur WHERE username=? AND password=?";
        try (PreparedStatement prest = con.prepareStatement(sql)) {
            prest.setString(1, username);
            prest.setString(2, password);
            
            try (ResultSet rs = prest.executeQuery()) {
                if (rs.next()) {
                    return 0; // Utilisateur trouvé
                }
            }
        } catch (SQLException se) {
            System.out.println("SQL Error: " + se.getMessage());
        }
        
        return 1; // Utilisateur non trouvé
    }

    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
