
package Modele;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mindrot.jbcrypt.BCrypt;


public class GestionnaireUtilisateur {
    
    private BDD_Connexion bddConnexion;

    public GestionnaireUtilisateur() {
        bddConnexion = new BDD_Connexion(); // Création d'une nouvelle connexion
    }
    
    public boolean enregistrerUtilisateur(String username, String password, String role) {
        // SQL pour insérer un utilisateur
        String query = "INSERT INTO utilisateur (username, password, role) VALUES (?, ?, ?)";

        // Hacher le mot de passe avec BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
            // Remplir les valeurs dans le PreparedStatement
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, role);

            // Exécuter la requête
            int rowsAffected = stmt.executeUpdate();

            // Retourner vrai si l'insertion a réussi
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
    // Récupérer les condidats
        public ObservableList<UtilisateurInfo> recupererUtilisateur() {
            ObservableList<UtilisateurInfo> userList = FXCollections.observableArrayList();
            String query = "SELECT  username,  role, date_creation As date, id_utilisateur As id, password FROM utilisateur";
            
            try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    UtilisateurInfo user = new UtilisateurInfo(                        
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("date"),
                        rs.getInt("id"),
                        rs.getString("password")

                        
                    );
                    userList.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return userList;
        }
        
        
        //methode pour modifier un utilisateur
        public boolean mettreAJourUtilisateur(int id, String username, String password,   String role) {
            String requete = "UPDATE utilisateur SET username = ?, password = ?, role = ? WHERE id_utilisateur = ?";
            
            // Hacher le mot de passe avec BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
            
            try (PreparedStatement statement = bddConnexion.con.prepareStatement(requete)) {

                statement.setString(1, username);
                statement.setString(2, hashedPassword);
                statement.setString(3, role);
                statement.setInt(4, id);

                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        
        
        //methode pour supprimer un condidat 
public boolean supprimerUtilisateur(int id) {
    String query = "DELETE FROM utilisateur WHERE id_utilisateur = ?";
    try (PreparedStatement preparedStatement = bddConnexion.con.prepareStatement(query)) {

        preparedStatement.setInt(1, id);
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLIntegrityConstraintViolationException e) {
        // Cette exception est levée lorsque la suppression viole une contrainte
        System.out.println("Impossible de supprimer cet utilisateur : lié à d'autres enregistrements.");
        throw new IllegalStateException("Cet utilisateur est lié à d'autres données et ne peut pas être supprimé.", e);
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
}
