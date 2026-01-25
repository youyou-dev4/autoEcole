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
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 *
 * @author htw
 */
public class GestionnaireCondidat {
    
    private BDD_Connexion bddConnexion;

    public GestionnaireCondidat() {
        bddConnexion = new BDD_Connexion(); // Création d'une nouvelle connexion
    }
    
    // Récupérer les condidats
public ObservableList<Condidat> recupererCondidat() {
    ObservableList<Condidat> condidatList = FXCollections.observableArrayList();
    String query = "SELECT id_condidat, num_condidat, nom, prenom, date_naissance, lieu_naissance, adresse, telephone, groupe_sanguin, date_inscription, photo FROM condidat ORDER BY date_inscription DESC";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Condidat condidat = new Condidat(
                rs.getInt("id_condidat"),
                rs.getString("num_condidat"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("date_naissance"),
                rs.getString("lieu_naissance"),
                rs.getString("adresse"),
                rs.getString("telephone"),
                rs.getString("groupe_sanguin"),
                rs.getString("date_inscription"),
                rs.getString("photo")
            );
            condidatList.add(condidat);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return condidatList;
}



// Récupérer les séances pour un candidat
public ObservableList<SeanceCondidatInfo> recupererSeancesPourCondidat(int candidatId) {
    ObservableList<SeanceCondidatInfo> seancesList = FXCollections.observableArrayList();
    String query = "SELECT " +
                   "    s.date_seance AS date_seance, " +
                   "    s.heure_seance AS heure_seance, " +
                   "    s.type AS type_seance, " +
                   "    s.epreuve AS epreuve, " +
                   "    s.type_permis AS type_permis " +
                   "FROM " +
                   "    condidat_assister_cours cac " +
                   "JOIN " +
                   "    cours s ON cac.id_cours = s.id_cours " +
                   "JOIN " +
                   "    condidat c ON cac.id_condidat = c.id_condidat " +
                   "WHERE " +
                   "    c.id_condidat = ? " +
                   "ORDER BY " +
                   "    s.date_seance, s.heure_seance";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, candidatId);  // Remplacer le placeholder par l'ID du candidat
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            SeanceCondidatInfo seance = new SeanceCondidatInfo(
                rs.getString("date_seance"),    // date_seance
                rs.getString("heure_seance"),   // heure_seance
                rs.getString("epreuve"),        // epreuve
                rs.getString("type_seance"),    // type_seance
                rs.getString("type_permis")     // type_permis
            );
            seancesList.add(seance);  // Ajouter l'objet Seance à la liste
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return seancesList;  // Retourner la liste des séances
}


//Methode pour recuperer les examen d'un condidat
public ObservableList<ExamenCondidatInfo> recupererExamensPourCondidat(int candidatId) {
    ObservableList<ExamenCondidatInfo> examens = FXCollections.observableArrayList();
    String query = "SELECT e.date_examen AS date, cpe.id_epreuve AS epreuve, cpe.resultat\n" +
                    "FROM examen e\n" +
                    "JOIN condidat_passer_examen cpe ON e.id_examen = cpe.id_examen\n" +
                    "WHERE cpe.id_condidat = ?";
    
    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, candidatId);  // Remplacer le placeholder par l'ID du candidat
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            ExamenCondidatInfo examen = new ExamenCondidatInfo(
                rs.getString("date"),    
                rs.getString("epreuve"),  
                rs.getString("resultat")                  
            );
            examens.add(examen);  // Ajouter l'objet Seance à la liste
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return examens;
}


//Methode pour recuperer les versement d'un condidat
public ObservableList<VersementCondidatInfo> recupererVersementPourCondidat(int candidatId) {
    ObservableList<VersementCondidatInfo> versements = FXCollections.observableArrayList();
    String query = "SELECT v.date_versement,v.montant\n" +
                    "FROM versement v\n" +
                    "WHERE v.id_condidat = ?";
    
    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, candidatId);  // Remplacer le placeholder par l'ID du candidat
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            VersementCondidatInfo versement = new VersementCondidatInfo(
                rs.getString("date_versement"),    
                rs.getDouble("montant")                                
            );
            versements.add(versement);  // Ajouter l'objet Seance à la liste
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return versements;
}



//Methode pour recuperer les inscription d'un condidat
public ObservableList<InscriptionCondidatInfo> recupererInscriptionPourCondidat(int candidatId) {
    ObservableList<InscriptionCondidatInfo> inscriptions = FXCollections.observableArrayList();
    String query = "SELECT c.date_inscription,c.nom_permis,c.montant\n" +
                    "FROM condidat_inscrit_permis c\n" +
                    "WHERE c.id_condidat =  ?";
    
    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        stmt.setInt(1, candidatId);  // Remplacer le placeholder par l'ID du candidat
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            InscriptionCondidatInfo inscription = new InscriptionCondidatInfo(
                rs.getString("date_inscription"), 
                rs.getString("nom_permis"), 
                rs.getDouble("montant")                                
            );
            inscriptions.add(inscription);  // Ajouter l'objet Seance à la liste
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return inscriptions;
}


//methode pour recuperer le montant a payer par un condidat

public MontantCondidat calculerMontants(int idCondidat) {
    double totalAPayer = 0;
    double totalPaye = 0;
        
    try {
        // Calcul du total à payer
        String queryAPayer = "SELECT \n" +
"    COALESCE(\n" +
"        (SELECT SUM(montant) FROM condidat_inscrit_permis WHERE id_condidat = ?), 0\n" +
"    ) + \n" +
"    COALESCE(\n" +
"        (SELECT SUM(montant) FROM cours c JOIN condidat_assister_cours cap ON cap.id_cours = c.id_cours WHERE cap.id_condidat = ?), 0\n" +
"    ) AS totalAPayer";

        PreparedStatement stmtAPayer = bddConnexion.con.prepareStatement(queryAPayer);
        stmtAPayer.setInt(1, idCondidat);
        stmtAPayer.setInt(2, idCondidat);

        ResultSet rsAPayer = stmtAPayer.executeQuery();
        if (rsAPayer.next()) {
            totalAPayer = rsAPayer.getDouble("totalAPayer");
        }

        // Calcul du total payé
        String queryPaye = "SELECT COALESCE(SUM(montant), 0) AS totalPaye FROM versement WHERE id_condidat = ?";
        PreparedStatement stmtPaye = bddConnexion.con.prepareStatement(queryPaye);
        stmtPaye.setInt(1, idCondidat);

        ResultSet rsPaye = stmtPaye.executeQuery();
        if (rsPaye.next()) {
            totalPaye = rsPaye.getDouble("totalPaye");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return new MontantCondidat(totalAPayer, totalPaye);
}



// Récupérer le nombre de candidats par mois
    public ObservableList<XYChart.Data<String, Integer>> recupererNombreCandidatsParMois() {
        ObservableList<XYChart.Data<String, Integer>> data = FXCollections.observableArrayList();
        
        String query = "SELECT MONTH(cond.date_inscription) AS mois, COUNT(DISTINCT cond.id_condidat) AS nb_condidats FROM condidat cond "
            + " WHERE YEAR(cond.date_inscription) = YEAR(CURDATE()) GROUP BY MONTH(cond.date_inscription) ORDER BY mois";
        
        try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String mois = getMonthName(rs.getInt("mois"));  // Méthode pour obtenir le nom du mois
                int nbCandidats = rs.getInt("nb_condidats");
                data.add(new XYChart.Data<>(mois, nbCandidats));
            }
        } catch (SQLException e) {
               System.err.println("Erreur lors de la récupération des candidats : " + e.getMessage());

        }
        
        return data;
    }


// Méthode pour obtenir le nom du mois à partir du numéro (1 -> Janvier, 2 -> Février, ...)
private String getMonthName(int month) {
    switch (month) {
        case 1: return "Janvier";
        case 2: return "Février";
        case 3: return "Mars";
        case 4: return "Avril";
        case 5: return "Mai";
        case 6: return "Juin";
        case 7: return "Juillet";
        case 8: return "Août";
        case 9: return "Septembre";
        case 10: return "Octobre";
        case 11: return "Novembre";
        case 12: return "Décembre";
        default: return "";
    }
}



//methode pour enregistrer un condidat 
public boolean enregistrerCondidat(String nom, String prenom, LocalDate dateNaissance, String lieuNaissance, 
                                    String telephone, String groupeSanguin, String adresse, String cheminPhoto) {
    // SQL pour insérer un candidat dans la base de données
    String query = "INSERT INTO condidat (nom, prenom, date_naissance, lieu_naissance, telephone, groupe_sanguin, adresse, photo) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
        // Remplir les valeurs dans le PreparedStatement
        stmt.setString(1, nom);
        stmt.setString(2, prenom);
        stmt.setDate(3, Date.valueOf(dateNaissance));  // LocalDate -> Date
        stmt.setString(4, lieuNaissance);
        stmt.setString(5, telephone);
        stmt.setString(6, groupeSanguin);
        stmt.setString(7, adresse);
        stmt.setString(8, cheminPhoto);  // Chemin vers la photo du candidat

        // Exécuter la requête
        int rowsAffected = stmt.executeUpdate();
        
        // Vérifier si l'insertion a été effectuée avec succès
        return rowsAffected > 0;  // Retourne vrai si une ligne a été insérée
    } catch (SQLException e) {
        e.printStackTrace();
        return false;  // Retourne faux en cas d'erreur
    }
}


//methode pour modifier un condidat
public boolean mettreAJourCondidat(int id, String numCondidat, String nom, String prenom, LocalDate dateNaissance, String lieuNaissance, 
                                   String telephone, String groupeSanguin, String adresse, String photo) {
    String requete = "UPDATE condidat SET num_condidat = ?, nom = ?, prenom = ?, date_naissance = ?, lieu_naissance = ?, " +
                     "telephone = ?, groupe_sanguin = ?, adresse = ?, photo = ? WHERE id_condidat = ?";

    try (PreparedStatement statement = bddConnexion.con.prepareStatement(requete)) {
        
        statement.setString(1, numCondidat);
        statement.setString(2, nom);
        statement.setString(3, prenom);
        statement.setDate(4, Date.valueOf(dateNaissance));
        statement.setString(5, lieuNaissance);
        statement.setString(6, telephone);
        statement.setString(7, groupeSanguin);
        statement.setString(8, adresse);
        statement.setString(9, photo);
        statement.setInt(10, id);

        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


//methode pour supprimer un condidat 
public boolean supprimerCondidat(int id) {
    String query = "DELETE FROM condidat WHERE id_condidat = ?";
    try (PreparedStatement preparedStatement = bddConnexion.con.prepareStatement(query)) {

        preparedStatement.setInt(1, id);
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLIntegrityConstraintViolationException e) {
        // Cette exception est levée lorsque la suppression viole une contrainte
        System.out.println("Impossible de supprimer ce candidat : lié à d'autres enregistrements.");
        throw new IllegalStateException("Ce candidat est lié à d'autres données et ne peut pas être supprimé.", e);
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    
}
