
package Modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class GestionnaireExamen {
    
    private BDD_Connexion bddConnexion;

    public GestionnaireExamen() {
        bddConnexion = new BDD_Connexion(); // Création d'une nouvelle connexion
    }
    
    public ObservableList<Examen> recupererExamen() {
    ObservableList<Examen> examenList = FXCollections.observableArrayList();
    String query = "SELECT " +
                   "e.id_examen , " +
                   "e.date_examen AS date, " +
                   "e.nom_examinateur , " + 
                   "e.vehicule ,"+
                   "COUNT(cp.id_condidat) AS nbr_candidats " +
                   "FROM examen e " +
                   "LEFT JOIN condidat_passer_examen cp ON e.id_examen = cp.id_examen " +
                   "GROUP BY e.id_examen, e.date_examen, e.nom_examinateur";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Examen examenInfo = new Examen(
                    rs.getInt("id_examen"),
                    rs.getDate("date").toString(),
                    rs.getString("nom_examinateur"),
                    rs.getString("vehicule"),
                    rs.getInt("nbr_candidats")
            );
            examenList.add(examenInfo);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return examenList;
}
    
    
    public ObservableList<ExamenCondidatExamine> recupererCandidatsExamine(int idExamen) {
    ObservableList<ExamenCondidatExamine> candidatList = FXCollections.observableArrayList();
    String query = "SELECT c.id_condidat, c.num_condidat, CONCAT(c.nom, ' ', c.prenom) AS nom_complet, c.date_naissance, cp.id_epreuve, cp.permis, cp.resultat " +
                   "FROM Condidat_Passer_Examen cp " +
                   "JOIN Condidat c ON cp.id_condidat = c.id_condidat " +
                   "WHERE cp.id_examen = ?";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, idExamen);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ExamenCondidatExamine candidat = new ExamenCondidatExamine(
                    rs.getInt("id_condidat"),
                    rs.getString("num_condidat"),
                    rs.getString("nom_complet"),
                    rs.getString("date_naissance"),                
                    rs.getString("id_epreuve"),
                    rs.getString("permis"),
                    rs.getString("resultat")
            );
            candidatList.add(candidat);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return candidatList;
}
    
    
    public ObservableList<ExamenMoniteurEncadrant> recupererMoniteursEncadrant(int idExamen) {
    ObservableList<ExamenMoniteurEncadrant> moniteurList = FXCollections.observableArrayList();
    String query = "SELECT m.id_moniteur, CONCAT(m.nom, ' ', m.prenom) AS nom_complet " +
                   "FROM Moniteur_Encadre_Examen me " +
                   "JOIN Moniteur m ON me.id_moniteur = m.id_moniteur " +
                   "WHERE me.id_examen = ?";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, idExamen);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ExamenMoniteurEncadrant moniteur = new ExamenMoniteurEncadrant(
                    rs.getInt("id_moniteur"),
                    rs.getString("nom_complet")
            );
            moniteurList.add(moniteur);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return moniteurList;
}
    
    
    public int insererExamen(String dateExamen, String nomExaminateur, String vehicule) {
    String query = "INSERT INTO Examen (date_examen, nom_examinateur, vehicule) VALUES (?, ?, ?)";
    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, dateExamen);
        stmt.setString(2, nomExaminateur);
        stmt.setString(3, vehicule);
        stmt.executeUpdate();
        
        // Récupérer l'ID généré
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); // Retourne l'ID de l'examen
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // En cas d'échec
}
    
    
    public void insererMoniteursPourExamen(int idExamen, ObservableList<Integer> idsMoniteurs) {
    String query = "INSERT INTO Moniteur_Encadre_Examen (id_moniteur, id_examen) VALUES (?, ?)";
    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        for (int idMoniteur : idsMoniteurs) {
            stmt.setInt(1, idMoniteur);
            stmt.setInt(2, idExamen);
            stmt.addBatch(); // Ajoute à la batch pour optimiser les insertions
        }
        stmt.executeBatch(); // Exécute toutes les insertions
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    
    public boolean mettreAJourExamen(int idExamen, String dateExamen, String nomExaminateur, String vehicule) {
    String query = "UPDATE Examen SET date_examen = ?, nom_examinateur = ?, vehicule = ? WHERE id_examen = ?";
    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setString(1, dateExamen);
        stmt.setString(2, nomExaminateur);
        stmt.setString(3, vehicule);
        stmt.setInt(4, idExamen);

        return stmt.executeUpdate() > 0; // Retourne true si au moins une ligne est mise à jour
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    
    
    public boolean mettreAJourMoniteursEncadrants(int idExamen, ObservableList<Integer> idsMoniteurs) {
    String deleteQuery = "DELETE FROM Moniteur_Encadre_Examen WHERE id_examen = ?";
    String insertQuery = "INSERT INTO Moniteur_Encadre_Examen (id_examen, id_moniteur) VALUES (?, ?)";

    try {
        // Suppression des anciens moniteurs encadrants
        try (PreparedStatement deleteStmt = bddConnexion.con.prepareStatement(deleteQuery)) {
            deleteStmt.setInt(1, idExamen);
            deleteStmt.executeUpdate();
        }

        // Insertion des nouveaux moniteurs encadrants
        try (PreparedStatement insertStmt = bddConnexion.con.prepareStatement(insertQuery)) {
            for (int idMoniteur : idsMoniteurs) {
                insertStmt.setInt(1, idExamen);
                insertStmt.setInt(2, idMoniteur);
                insertStmt.addBatch();
            }
            insertStmt.executeBatch(); // Exécution en batch pour meilleure performance
        }

        return true; // Retourne true si tout s'est bien passé
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    
    public boolean supprimerExamen(int idExamen) {
    String query = "DELETE FROM examen WHERE id_examen = ?";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        // Remplir le paramètre
        stmt.setInt(1, idExamen);

        // Exécuter la requête
        int rowsDeleted = stmt.executeUpdate();
        return rowsDeleted > 0; // Retourne vrai si la suppression a réussi
    } catch (SQLException e) {
        // Vérifier si l'erreur est liée à une contrainte de clé étrangère
        if (e.getSQLState().equals("23000")) { // SQLState 23000 indique une contrainte d'intégrité
            throw new IllegalStateException("Cet examen est lié à d'autres données et ne peut pas être supprimé.", e);
        } else {
            e.printStackTrace();
            return false; // Retourne faux pour d'autres erreurs SQL
        }
    }
}
    
    
    public boolean insererCondidatAuExamen(int idExamen, int idCondidat, String epreuve, String permis, String resultat) {
    String query = "INSERT INTO condidat_passer_examen (id_examen, id_condidat, id_epreuve, permis, resultat) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        
        stmt.setInt(1, idExamen);
        stmt.setInt(2, idCondidat);
        stmt.setString(3, epreuve);
        stmt.setString(4, permis);
        stmt.setString(5, resultat);
        
        int rowsAffected = stmt.executeUpdate(); // Exécute la requête
        return rowsAffected > 0; // Retourne true si l'insertion a réussi
        
        } catch (SQLException e) {
        if (e.getSQLState().equals("23000")) {
            return false;
        } else {
            e.printStackTrace();
            return false; // Retourne faux pour d'autres erreurs SQL
        }
    }
}

    
    public boolean modifierCondidatAuExamen(int idExamen, int idCondidat, String epreuve, String permis, String resultat) {
    String query = "UPDATE condidat_passer_examen SET id_epreuve = ?,  permis = ?, resultat = ? where id_examen = ? AND id_condidat = ?  ";
    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        
        stmt.setString(1, epreuve);
        stmt.setString(2, permis);
        stmt.setString(3, resultat);
        stmt.setInt(4, idExamen);
        stmt.setInt(5, idCondidat);
        
        return stmt.executeUpdate() > 0; // Retourne true si au moins une ligne est mise à jour
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    
     public boolean supprimerCondidatExamen(int idExamen, int idCondidat) {
    String query = "DELETE FROM condidat_passer_examen WHERE id_examen = ? AND id_condidat = ?";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        // Remplir le paramètre
        stmt.setInt(1, idExamen);
        stmt.setInt(2, idCondidat);
                
        // Exécuter la requête
        int rowsDeleted = stmt.executeUpdate();
        return rowsDeleted > 0; // Retourne vrai si la suppression a réussi
    } catch (SQLException e) {
        // Vérifier si l'erreur est liée à une contrainte de clé étrangère
        if (e.getSQLState().equals("23000")) { // SQLState 23000 indique une contrainte d'intégrité
            throw new IllegalStateException("Cet examen est lié à d'autres données et ne peut pas être supprimé.", e);
        } else {
            e.printStackTrace();
            return false; // Retourne faux pour d'autres erreurs SQL
        }
    }
}

    
    
    
}
