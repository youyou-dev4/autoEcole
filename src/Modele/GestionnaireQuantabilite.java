
package Modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class GestionnaireQuantabilite {
   
    
    private BDD_Connexion bddConnexion;

    public GestionnaireQuantabilite() {
        bddConnexion = new BDD_Connexion(); // Création d'une nouvelle connexion
    }
    
    // Récupérer les versement
public ObservableList<Versement> recupererVersement() {
    ObservableList<Versement> versementList = FXCollections.observableArrayList();
    String query = "SELECT v.id_versement, v.date_versement, v.montant, CONCAT(c.nom, ' ', c.prenom) AS nom_condidat, v.id_condidat \n" +
"                FROM versement v\n" +
"                INNER JOIN condidat c ON v.id_condidat = c.id_condidat ORDER BY \n" +
"    v.date_versement DESC";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Versement versement = new Versement(
                rs.getInt("id_versement"),
                rs.getString("date_versement"),
                rs.getDouble("montant"),
                rs.getString("nom_condidat"),
                rs.getInt("id_condidat")
            );
            versementList.add(versement);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return versementList;
}


public boolean ajouterVersement(int idCondidat, double montant) {
    String query = "INSERT INTO versement (id_condidat, montant, date_versement) VALUES (?, ?, NOW())";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, idCondidat); // Associe l'ID du candidat
        stmt.setDouble(2, montant); // Associe le montant

        int rowsAffected = stmt.executeUpdate(); // Exécute la requête
        return rowsAffected > 0; // Retourne true si l'insertion a réussi
    } catch (SQLException e) {
        e.printStackTrace(); // Affiche une erreur en cas de problème
        return false; // Retourne false si une exception est levée
    }
}


public boolean modifierVersement(int idVersement, int idCondidat, double montant) {
    String query = "UPDATE versement SET id_condidat = ?, montant = ? WHERE id_versement = ?";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, idCondidat);
        stmt.setDouble(2, montant);
        stmt.setInt(3, idVersement);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0; // Succès si au moins une ligne modifiée
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Échec
    }
}


 //methode pour supprimer un condidat 
public boolean supprimerVersement(int id) {
    String query = "DELETE FROM versement WHERE id_versement = ?";
    try (PreparedStatement preparedStatement = bddConnexion.con.prepareStatement(query)) {

        preparedStatement.setInt(1, id);
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLIntegrityConstraintViolationException e) {
        // Cette exception est levée lorsque la suppression viole une contrainte
        System.out.println("Impossible de supprimer ce versement : lié à d'autres enregistrements.");
        throw new IllegalStateException("Ce versement est lié à d'autres données et ne peut pas être supprimé.", e);
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}



// Récupérer les versement
public ObservableList<Depence> recupererDepence() {
    ObservableList<Depence> depenceList = FXCollections.observableArrayList();
    String query = "SELECT d.id_depence, d.date_depence, d.montant, d.motif\n" +
"                FROM depence d ORDER BY \n" +
"    d.date_depence DESC \n";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Depence depence = new Depence(
                rs.getInt("id_depence"),
                rs.getString("date_depence"),
                rs.getDouble("montant"),
                rs.getString("motif")             
            );
            depenceList.add(depence);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return depenceList;
}



public boolean ajouterDepence( double montant,String motif) {
    String query = "INSERT INTO depence ( montant, motif) VALUES (?, ?)";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setDouble(1, montant); // Associe l'ID du candidat
        stmt.setString(2, motif); // Associe le montant

        int rowsAffected = stmt.executeUpdate(); // Exécute la requête
        return rowsAffected > 0; // Retourne true si l'insertion a réussi
    } catch (SQLException e) {
        e.printStackTrace(); // Affiche une erreur en cas de problème
        return false; // Retourne false si une exception est levée
    }
}


public boolean modifierDepence(int idDepence,  double montant, String motif) {
    String query = "UPDATE depence SET motif = ?, montant = ? WHERE id_depence = ?";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setString(1, motif);
        stmt.setDouble(2, montant);
        stmt.setInt(3, idDepence);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0; // Succès si au moins une ligne modifiée
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Échec
    }
}


//methode pour supprimer un condidat 
public boolean supprimerDepence(int id) {
    String query = "DELETE FROM depence WHERE id_depence = ?";
    try (PreparedStatement preparedStatement = bddConnexion.con.prepareStatement(query)) {

        preparedStatement.setInt(1, id);
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLIntegrityConstraintViolationException e) {
        // Cette exception est levée lorsque la suppression viole une contrainte
        System.out.println("Impossible de supprimer cette depence: lié à d'autres enregistrements.");
        throw new IllegalStateException("Cette depence est lié à d'autres données et ne peut pas être supprimé.", e);
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}



    
    
    
    
}
