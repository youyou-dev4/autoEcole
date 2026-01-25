
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

public class GestionnaireMoniteur {
    
    private BDD_Connexion bddConnexion;

    public GestionnaireMoniteur() {
        bddConnexion = new BDD_Connexion(); // Création d'une nouvelle connexion
    }
    
    
                                    // Récupérer les condidats
    
        public ObservableList<Moniteur> recupererMoniteur() {
            ObservableList<Moniteur> moniteurList = FXCollections.observableArrayList();
            String query = "SELECT id_moniteur, nom, prenom, date_naissance, lieu_naissance, adresse, telephone, groupe_sanguin, date_enregistrement FROM moniteur ORDER BY date_enregistrement DESC";

            try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Moniteur moniteur = new Moniteur(
                        rs.getInt("id_moniteur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("date_naissance"),
                        rs.getString("lieu_naissance"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("groupe_sanguin"),
                        rs.getString("date_enregistrement")
                    );
                    moniteurList.add(moniteur);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return moniteurList;
        }
        
        
        
        
                                                // Récupérer les séances pour un moniteur
        
        public ObservableList<SeanceCondidatInfo> recupererSeancesPourMoniteur(int moniteurId) {
            ObservableList<SeanceCondidatInfo> seancesMoniteurList = FXCollections.observableArrayList();
            String query = "SELECT s.date_seance AS date_seance, s.heure_seance AS heure_seance, "
                    + "s.type AS type_seance,s.epreuve AS epreuve,s.type_permis AS type_permis"
                    + "  from cours s where s.id_moniteur = ?";

            try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
                stmt.setInt(1, moniteurId);  // Remplacer le placeholder par l'ID du candidat
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    SeanceCondidatInfo seance = new SeanceCondidatInfo(
                        rs.getString("date_seance"),    // date_seance
                        rs.getString("heure_seance"),   // heure_seance
                        rs.getString("epreuve"),        // epreuve
                        rs.getString("type_seance"),    // type_seance
                        rs.getString("type_permis")     // type_permis
                    );
                    seancesMoniteurList.add(seance);  // Ajouter l'objet Seance à la liste
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return seancesMoniteurList;  // Retourner la liste des séances
        }
        
        
        
        
        //Methode pour recuperer les examen d'un condidat
            public ObservableList<ExamenMoniteurInfo> recupererExamensPourMoniteur(int moniteurId) {
                ObservableList<ExamenMoniteurInfo> examens = FXCollections.observableArrayList();
                String query = "SELECT \n" +
                                "    e.date_examen AS date,\n" +
                                "    COUNT(c.id_condidat) AS nbr_condidat\n" +
                                "FROM \n" +
                                "    moniteur_encadre_examen me\n" +
                                "JOIN \n" +
                                "    examen e ON me.id_examen = e.id_examen\n" +
                                "LEFT JOIN \n" +
                                "    condidat_passer_examen c ON e.id_examen = c.id_examen\n" +
                                "WHERE \n" +
                                "    me.id_moniteur = ? \n" +
                                "GROUP BY \n" +
                                "    e.date_examen";

                try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
                    stmt.setInt(1, moniteurId);  // Remplacer le placeholder par l'ID du candidat
                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {
                        ExamenMoniteurInfo examen = new ExamenMoniteurInfo(
                            rs.getString("date"),      
                            rs.getInt("nbr_condidat")                  
                        );
                        examens.add(examen);  // Ajouter l'objet Seance à la liste
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return examens;
            }
            
            
            
            //methode pour enregistrer un condidat 
            public boolean enregistrerMoniteur(String nom, String prenom, LocalDate dateNaissance, String lieuNaissance, 
                                                String telephone, String groupeSanguin, String adresse) {
                // SQL pour insérer un candidat dans la base de données
                String query = "INSERT INTO moniteur (nom, prenom, date_naissance, lieu_naissance, telephone, groupe_sanguin, adresse) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
                    // Remplir les valeurs dans le PreparedStatement
                    stmt.setString(1, nom);
                    stmt.setString(2, prenom);
                    stmt.setDate(3, Date.valueOf(dateNaissance));  // LocalDate -> Date
                    stmt.setString(4, lieuNaissance);
                    stmt.setString(5, telephone);
                    stmt.setString(6, groupeSanguin);
                    stmt.setString(7, adresse);

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
            public boolean mettreAJourMoniteur(int id, String nom, String prenom, LocalDate dateNaissance, String lieuNaissance, 
                                               String telephone, String groupeSanguin, String adresse) {
                String requete = "UPDATE moniteur SET nom = ?, prenom = ?, date_naissance = ?, lieu_naissance = ?, " +
                                 "telephone = ?, groupe_sanguin = ?, adresse = ? WHERE id_moniteur = ?";

                try (PreparedStatement statement = bddConnexion.con.prepareStatement(requete)) {

                    statement.setString(1, nom);
                    statement.setString(2, prenom);
                    statement.setDate(3, Date.valueOf(dateNaissance));
                    statement.setString(4, lieuNaissance);
                    statement.setString(5, telephone);
                    statement.setString(6, groupeSanguin);
                    statement.setString(7, adresse);
                    statement.setInt(8, id);

                    int rowsUpdated = statement.executeUpdate();
                    return rowsUpdated > 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            
            
            //methode pour supprimer un condidat 
        public boolean supprimerMoniteur(int id) {
            String query = "DELETE FROM moniteur WHERE id_moniteur = ?";
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
