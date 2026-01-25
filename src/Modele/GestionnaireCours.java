package Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class GestionnaireCours {

    private BDD_Connexion bddConnexion;

    public GestionnaireCours() {
        bddConnexion = new BDD_Connexion(); // Création d'une nouvelle connexion
    }
    
    
    
    

    // Récupérer les cours du jour (code)
    public ObservableList<CoursInfo> recupererCoursCodeAujourdhui() {
        ObservableList<CoursInfo> coursList = FXCollections.observableArrayList();
        String query = "SELECT c.date_seance, c.heure_seance, m.nom AS nom_moniteur, m.prenom AS prenom_moniteur, " +
                       "cond.nom AS nom_condidat, cond.prenom AS prenom_condidat " +
                       "FROM cours c " +
                       "JOIN moniteur m ON c.id_moniteur = m.id_moniteur " +
                       "JOIN condidat_assister_cours cac ON cac.id_cours = c.id_cours " +
                       "JOIN condidat cond ON cac.id_condidat = cond.id_condidat " +
                       "JOIN epreuve e ON e.nom_epreuve = 'code' " +
                       "WHERE c.date_seance = CURDATE()";
        
        try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CoursInfo coursInfo = new CoursInfo(
                        rs.getDate("date_seance").toString(), 
                        rs.getTime("heure_seance").toString(), 
                        rs.getString("nom_moniteur"),
                        rs.getString("prenom_moniteur"),
                        rs.getString("nom_condidat"),
                        rs.getString("prenom_condidat")
                );
                coursList.add(coursInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coursList;
    }
    
    
    public ObservableList<Seance> recupererSeances() {
    ObservableList<Seance> seanceList = FXCollections.observableArrayList();
    String query = "SELECT " +
                   "C.id_cours AS id_seance, " +
                   "C.date_seance AS date, " +
                   "C.heure_seance AS heure, " +
                   "C.type AS type, " +
                   "C.montant AS montant, " +
                   "C.epreuve AS epreuve, " +
                   "C.type_permis AS permis, " +
                   "COALESCE(CONCAT(Cond.nom, ' ', Cond.prenom), 'Inconnu') AS nomCondidat, " +
                   "COALESCE(CONCAT(Mon.nom, ' ', Mon.prenom), 'Inconnu') AS nomMoniteur, " +
                   "Cond.id_condidat AS idCondidat, " +
                   "C.id_moniteur AS idMoniteur " +
                   "FROM Cours C " +
                   "LEFT JOIN Condidat_Assister_Cours CAC ON C.id_cours = CAC.id_cours " +
                   "LEFT JOIN Condidat Cond ON CAC.id_condidat = Cond.id_condidat " +
                   "LEFT JOIN Moniteur Mon ON C.id_moniteur = Mon.id_moniteur " +
                   "ORDER BY C.date_seance DESC, C.heure_seance DESC";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Seance seanceInfo = new Seance(
                    rs.getInt("id_seance"),
                    rs.getDate("date").toString(),
                    rs.getTime("heure").toString(),
                    rs.getString("type"),
                    rs.getDouble("montant"),
                    rs.getString("epreuve"),
                    rs.getString("permis"),
                    rs.getString("nomCondidat"),
                    rs.getString("nomMoniteur"),
                    rs.getInt("idCondidat"),
                    rs.getInt("idMoniteur")
            );
            seanceList.add(seanceInfo);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return seanceList;
}
    
    
   
    
    
    
    public boolean ajouterSeance(String date, String heure, String type, double montant, String epreuve, String permis, int idMoniteur, int idCondidat) {
    String queryCours = "INSERT INTO Cours (date_seance, heure_seance, type, montant, epreuve, type_permis, id_moniteur) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String queryCondidatSeance = "INSERT INTO condidat_assister_cours (id_condidat, id_cours) VALUES (?, ?)";

    try {
        // Démarrer une transaction
        bddConnexion.con.setAutoCommit(false);

        // Insérer la séance
        try (PreparedStatement stmtCours = bddConnexion.con.prepareStatement(queryCours, Statement.RETURN_GENERATED_KEYS)) {
            stmtCours.setDate(1, java.sql.Date.valueOf(date));
            stmtCours.setTime(2, java.sql.Time.valueOf(heure));
            stmtCours.setString(3, type);
            stmtCours.setDouble(4, montant);
            stmtCours.setString(5, epreuve);
            stmtCours.setString(6, permis);
            stmtCours.setInt(7, idMoniteur);

            int rowsAffectedCours = stmtCours.executeUpdate();
            if (rowsAffectedCours > 0) {
                // Récupérer l'id généré pour la séance
                ResultSet generatedKeys = stmtCours.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idSeance = generatedKeys.getInt(1);

                    // Insérer dans la table condidat_passer_seance
                    try (PreparedStatement stmtCondidatSeance = bddConnexion.con.prepareStatement(queryCondidatSeance)) {
                        stmtCondidatSeance.setInt(1, idCondidat);
                        stmtCondidatSeance.setInt(2, idSeance);

                        int rowsAffectedCondidatSeance = stmtCondidatSeance.executeUpdate();
                        if (rowsAffectedCondidatSeance > 0) {
                            // Commit de la transaction
                            bddConnexion.con.commit();
                            return true;
                        }
                    }
                }
            }

            // Si l'insertion échoue, rollback
            bddConnexion.con.rollback();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        try {
            bddConnexion.con.rollback(); // Annuler les modifications en cas d'erreur
        } catch (SQLException rollbackEx) {
            rollbackEx.printStackTrace();
        }
    } finally {
        try {
            bddConnexion.con.setAutoCommit(true); // Réactiver l'auto-commit
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return false; // Retourne false en cas d'échec
}
    
    
    
    public boolean verifierDisponibiliteMoniteur(int idMoniteur, String date, String heure) {
    String query = "SELECT COUNT(*) FROM Cours WHERE id_moniteur = ? AND date_seance = ? AND heure_seance = ?";
    
    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, idMoniteur);
        stmt.setDate(2, java.sql.Date.valueOf(date));
        stmt.setTime(3, java.sql.Time.valueOf(heure));

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) == 0; // Retourne true si aucune séance n'est trouvée
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Retourne false en cas d'erreur ou si une séance est trouvée
}
    
    
    //methode pour recuperer le nombre de seance faite pour un condidat
    public int compterSeancesParEpreuve(int idCondidat,  String epreuve, String permis) {
    String query = "SELECT COUNT(*) FROM Cours c " +
                   "JOIN condidat_assister_cours cps ON c.id_cours = cps.id_cours " +
                   "WHERE cps.id_condidat = ? AND c.type = 'normal' AND c.epreuve = ? AND c.type_permis = ?";
    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, idCondidat);
        stmt.setString(2, epreuve);
        stmt.setString(3, permis);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Retourne -1 en cas d'erreur
}
    
    
    //methode pour obtenir le nbr maximale des seance pour une epreuve 
    public int obtenirNbrMaxSeances(String epreuve) {
    String query = "SELECT nbr_heures FROM Epreuve WHERE nom_epreuve = ?";
    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setString(1, epreuve);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Retourne -1 en cas d'erreur
}
    
    
    
    
    public boolean modifierSeance(int idSeance, String date, String heure, String type, double montant, String epreuve, String permis, int idMoniteur, int idCondidat) {
    String queryUpdateCours = "UPDATE Cours SET date_seance = ?, heure_seance = ?, type = ?, montant = ?, epreuve = ?, type_permis = ?, id_moniteur = ? WHERE id_cours = ?";
    String queryUpdateCondidatSeance = "UPDATE condidat_assister_cours SET id_condidat = ? WHERE id_cours = ?";

    try {
        // Démarrer une transaction
        bddConnexion.con.setAutoCommit(false);

        // Mise à jour de la séance
        try (PreparedStatement stmtCours = bddConnexion.con.prepareStatement(queryUpdateCours)) {
            stmtCours.setDate(1, java.sql.Date.valueOf(date));
            stmtCours.setTime(2, java.sql.Time.valueOf(heure));
            stmtCours.setString(3, type);
            stmtCours.setDouble(4, montant);
            stmtCours.setString(5, epreuve);
            stmtCours.setString(6, permis);
            stmtCours.setInt(7, idMoniteur);
            stmtCours.setInt(8, idSeance);

            int rowsAffectedCours = stmtCours.executeUpdate();
            if (rowsAffectedCours > 0) {
                // Mise à jour dans la table condidat_assister_cours
                try (PreparedStatement stmtCondidatSeance = bddConnexion.con.prepareStatement(queryUpdateCondidatSeance)) {
                    stmtCondidatSeance.setInt(1, idCondidat);
                    stmtCondidatSeance.setInt(2, idSeance);

                    int rowsAffectedCondidatSeance = stmtCondidatSeance.executeUpdate();
                    if (rowsAffectedCondidatSeance > 0) {
                        // Commit de la transaction
                        bddConnexion.con.commit();
                        return true;
                    }
                }
            }

            // Si la mise à jour échoue, rollback
            bddConnexion.con.rollback();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        try {
            bddConnexion.con.rollback(); // Annuler les modifications en cas d'erreur
        } catch (SQLException rollbackEx) {
            rollbackEx.printStackTrace();
        }
    } finally {
        try {
            bddConnexion.con.setAutoCommit(true); // Réactiver l'auto-commit
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return false; // Retourne false en cas d'échec
}
    
    
    // Supprimer une seance
public boolean supprimerSeance(int idSeance) {
    String query = "DELETE FROM cours WHERE id_cours = ?";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        // Remplir le paramètre
        stmt.setInt(1, idSeance);

        // Exécuter la requête
        int rowsDeleted = stmt.executeUpdate();
        return rowsDeleted > 0; // Retourne vrai si la suppression a réussi
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Retourne faux en cas d'erreur
    }
}


public boolean ajouterCondidatAuSeanceCode(int idCours, int idCondidat) {
    String query = "INSERT INTO condidat_assister_cours (id_cours, id_condidat) VALUES (?, ?)";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, idCours); // Associe l'ID du candidat
        stmt.setInt(2, idCondidat); // Associe le montant

        int rowsAffected = stmt.executeUpdate(); // Exécute la requête
        return rowsAffected > 0; // Retourne true si l'insertion a réussi
    } catch (SQLException e) {
        e.printStackTrace(); // Affiche une erreur en cas de problème
        return false; // Retourne false si une exception est levée
    }
}



public int obtenirNombreCandidatsInscrits(int idCours) {
    String query = "SELECT COUNT(*) AS nombre_inscrits FROM condidat_assister_cours WHERE id_cours = ?";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, idCours);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("nombre_inscrits"); // Retourne le nombre d'inscriptions
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return -1; // Retourne -1 en cas d'erreur
}



    
    
    



}
