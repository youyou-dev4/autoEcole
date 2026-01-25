/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author htw
 */
public class GestionnaireInscription {
    
    private BDD_Connexion bddConnexion;

    public GestionnaireInscription() {
        bddConnexion = new BDD_Connexion(); // Création d'une nouvelle connexion
    }
    
    // Récupérer les versement
public ObservableList<Inscription> recupererInscription() {
    ObservableList<Inscription> inscriptionList = FXCollections.observableArrayList();
    String query = "SELECT i.id_inscription, i.id_condidat, i.nom_permis, i.date_inscription, i.montant, CONCAT(c.nom, ' ', c.prenom) AS nom_condidat \n" +
"                FROM condidat_inscrit_permis i\n" +
"                INNER JOIN condidat c ON i.id_condidat = c.id_condidat ORDER BY \n" +
"    i.date_inscription DESC";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Inscription inscription = new Inscription(
                rs.getInt("id_inscription"),
                rs.getInt("id_condidat"),
                rs.getString("nom_permis"),
                rs.getString("date_inscription"),
                rs.getDouble("montant"),
                rs.getString("nom_condidat")
                
            );
            inscriptionList.add(inscription);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return inscriptionList;
}


public boolean ajouterInscription(int idCondidat, double montant, String permis) {
    String query = "INSERT INTO condidat_inscrit_permis (id_condidat, montant, nom_permis) VALUES (?, ?, ?)";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, idCondidat); // Associe l'ID du candidat
        stmt.setDouble(2, montant); // Associe le montant
        stmt.setString(3, permis); // Associe le montant

        int rowsAffected = stmt.executeUpdate(); // Exécute la requête
        return rowsAffected > 0; // Retourne true si l'insertion a réussi
    } catch (SQLException e) {
        e.printStackTrace(); // Affiche une erreur en cas de problème
        return false; // Retourne false si une exception est levée
    }
}


public boolean modifierInscription(int idInscription, int idCondidat, double montant, String permis) {
    String query = "UPDATE condidat_inscrit_permis SET id_condidat = ?, montant = ?, nom_permis = ? WHERE id_inscription = ?";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, idCondidat);
        stmt.setDouble(2, montant);
        stmt.setString(3, permis);
        stmt.setInt(4, idInscription);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0; // Succès si au moins une ligne modifiée
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Échec
    }
}

//methode pour supprimer un condidat 
public boolean supprimerInscription(int id) {
    String query = "DELETE FROM condidat_inscrit_permis  WHERE id_inscription = ?";
    try (PreparedStatement preparedStatement = bddConnexion.con.prepareStatement(query)) {

        preparedStatement.setInt(1, id);
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLIntegrityConstraintViolationException e) {
        // Cette exception est levée lorsque la suppression viole une contrainte
        System.out.println("Impossible de supprimer cette inscription : lié à d'autres enregistrements.");
        throw new IllegalStateException("Cette inscription est lié à d'autres données et ne peut pas être supprimé.", e);
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    
    
}
