/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author htw
 */
public class GestionnaireVehicule {
    private BDD_Connexion bddConnexion;

    public GestionnaireVehicule() {
        bddConnexion = new BDD_Connexion(); // Création d'une nouvelle connexion
    }
    
    // Récupérer les véhicules
    public ObservableList<Vehicule> recupererVehicules() {
        ObservableList<Vehicule> vehiculeList = FXCollections.observableArrayList();
        String query = "SELECT id_vehicule, marque, modele, immatriculation, type_vehicule, " +
                       "proprietaire, date_controle, date_assurance, type_assurance, num_assurance " +
                       "FROM vehicule ORDER BY marque";

        try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Vehicule vehicule = new Vehicule(
                    rs.getInt("id_vehicule"),
                    rs.getString("marque"),
                    rs.getString("modele"),
                    rs.getString("immatriculation"),
                    rs.getString("type_vehicule"),
                    rs.getString("proprietaire"),
                    rs.getDate("date_controle").toLocalDate(),
                    rs.getDate("date_assurance").toLocalDate(),
                    rs.getString("type_assurance"),
                    rs.getString("num_assurance")
                );
                vehiculeList.add(vehicule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehiculeList;
    }
    
    
    // Ajouter un véhicule
public boolean ajouterVehicule(String marque, String modele, String immatriculation, String typeVehicule, 
                               String proprietaire, LocalDate dateControle, LocalDate dateAssurance, 
                               String typeAssurance, String numAssurance) {
    String query = "INSERT INTO vehicule (marque, modele, immatriculation, type_vehicule, proprietaire, " +
                   "date_controle, date_assurance, type_assurance, num_assurance) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        // Remplir les paramètres
        stmt.setString(1, marque);
        stmt.setString(2, modele);
        stmt.setString(3, immatriculation);
        stmt.setString(4, typeVehicule);
        stmt.setString(5, proprietaire);
        stmt.setDate(6, Date.valueOf(dateControle));
        stmt.setDate(7, Date.valueOf(dateAssurance));
        stmt.setString(8, (typeAssurance == null || typeAssurance.isEmpty()) ? null : typeAssurance);
        stmt.setString(9, (numAssurance == null || numAssurance.isEmpty()) ? null : numAssurance);

        // Exécuter la requête
        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0; // Retourne vrai si l'insertion a réussi
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Retourne faux en cas d'erreur
    }
}



// Mettre à jour un véhicule
public boolean mettreAJourVehicule(int idVehicule, String marque, String modele, String immatriculation, 
                                   String typeVehicule, String proprietaire, LocalDate dateControle, 
                                   LocalDate dateAssurance, String typeAssurance, String numAssurance) {
    String query = "UPDATE vehicule SET marque = ?, modele = ?, immatriculation = ?, type_vehicule = ?, " +
                   "proprietaire = ?, date_controle = ?, date_assurance = ?, type_assurance = ?, num_assurance = ? " +
                   "WHERE id_vehicule = ?";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        // Remplir les paramètres
        stmt.setString(1, marque);
        stmt.setString(2, modele);
        stmt.setString(3, immatriculation);
        stmt.setString(4, typeVehicule);
        stmt.setString(5, proprietaire);
        stmt.setDate(6, Date.valueOf(dateControle));
        stmt.setDate(7, Date.valueOf(dateAssurance));
        stmt.setString(8, (typeAssurance == null || typeAssurance.isEmpty()) ? null : typeAssurance);
        stmt.setString(9, (numAssurance == null || numAssurance.isEmpty()) ? null : numAssurance);
        stmt.setInt(10, idVehicule); // Identifiant du véhicule à mettre à jour

        // Exécuter la requête
        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated > 0; // Retourne vrai si la mise à jour a réussi
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Retourne faux en cas d'erreur
    }
}


// Supprimer un véhicule
public boolean supprimerVehicule(int idVehicule) {
    String query = "DELETE FROM vehicule WHERE id_vehicule = ?";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        // Remplir le paramètre
        stmt.setInt(1, idVehicule);

        // Exécuter la requête
        int rowsDeleted = stmt.executeUpdate();
        return rowsDeleted > 0; // Retourne vrai si la suppression a réussi
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Retourne faux en cas d'erreur
    }
}

    
    
    
}
