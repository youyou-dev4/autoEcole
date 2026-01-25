/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.Condidat;
import Modele.GestionnaireCondidat;
import Modele.GestionnaireCours;
import Modele.Seance;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author htw
 */
public class AjouterCondidatAuSeanceCodeController implements Initializable {

    private int idSeance;
    
      @FXML
            private TableView<Condidat> tableCondidat;

            @FXML
            private TableColumn<Condidat, Integer> tableCondidatID;

            @FXML
            private TableColumn<Condidat, String> tableCondidatNom;

            @FXML
            private TableColumn<Condidat, String> tableCondidatPrenom;

            @FXML
            private TableColumn<Condidat, String> tableCondidatDateNaiss;

            @FXML
            private TableColumn<Condidat, String> tableCondidatLieuNaiss;
            
            @FXML
            private TextField searchField;
    
    
    
    public void initialiserChamps(Seance seance) {
        idSeance = seance.getId_seance();
    }
    
    
    
     //methode pour verifier que tout les champ sont remplie 
    private boolean validerChamps() {
                // Vérifier les champs obligatoires
                    Condidat condidatSelectionne = tableCondidat.getSelectionModel().getSelectedItem();
                if (condidatSelectionne == null   ) {

                    afficherAlerte("Erreur", "Veuillez selectionner un condidat.");
                    return false;
                }

               

                return true;
        }
    
    
    private boolean seancePlein() {
            GestionnaireCours gestionCours = new GestionnaireCours();
            int nombreMaxCandidats = 4;
            int nombreInscrits = gestionCours.obtenirNombreCandidatsInscrits(idSeance);

             if (nombreInscrits == -1) {
                    afficherAlerte("Erreur", "Erreur lors de la vérification du nombre de candidats inscrits.");
                    return false;
            } else if ( nombreMaxCandidats <=  nombreInscrits) {
                 afficherAlerte("Erreur", "Le nombre maximal de candidats pour cette séance a été atteint.");
                 return false;

            }

                return true;
        }
    
        //methode pour afficher les message d'erreur 
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    
    
    @FXML
        private void enregistrerSeance() {
            
             if (!validerChamps()) {
        return; // Si la validation échoue, on ne continue pas
        }
             if (!seancePlein()) {
        return; // Si la validation échoue, on ne continue pas
        }
            
            
            // Récupérer les valeurs des champs
            
            Condidat condidatSelectionne = tableCondidat.getSelectionModel().getSelectedItem();
            
            

            

            // Instancier le gestionnaire de cours
            GestionnaireCours gestionCours = new GestionnaireCours();

            // Appeler la méthode pour ajouter la séance
            boolean success = gestionCours.ajouterCondidatAuSeanceCode(idSeance,  condidatSelectionne.getId());

            // Gérer le résultat de l'insertion
            if (success) {
        afficherAlerteEtFermerFenetre("Succès", "La seance a été enregistré avec succès.", true);                // Vous pouvez aussi afficher une alerte ou un message à l'utilisateur
            } else {
        afficherAlerteEtFermerFenetre("Erreur", "Le candidat est déjà inscrit à cette séance ou une autre erreur est survenue.", false);
                // Afficher un message d'erreur
            }
        }
        
        
        @FXML
private JFXButton EnregistrerSeanceButton;
    
    // Méthode pour afficher l'alerte et fermer la fenêtre en fonction du résultat
private void afficherAlerteEtFermerFenetre(String titre, String message, boolean isSuccess) {
    // Créer l'alerte
    Alert alert = new Alert(isSuccess ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
    alert.setTitle(titre);
    alert.setHeaderText(null);
    alert.setContentText(message);

    // Ajouter un gestionnaire d'événements pour l'action sur le bouton "OK"
    alert.showAndWait().ifPresent(response -> {
        // Si l'ajout est réussi, fermer la fenêtre
        if (isSuccess) {
            Stage stage = (Stage) EnregistrerSeanceButton.getScene().getWindow(); // Récupérer la fenêtre principale
            stage.close(); // Fermer la fenêtre
        }
    });
}

    
    
    
    
             
             
         
        @FXML
private JFXButton ExitAjouterVersementButton;

@FXML
private void fermerFenetre() {
    // Obtenir la fenêtre actuelle et la fermer
    Stage stage = (Stage) ExitAjouterVersementButton.getScene().getWindow();
    stage.close();
}
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configuration des colonnes de la table des condidat
                           tableCondidatNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
                           tableCondidatPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                           tableCondidatDateNaiss.setCellValueFactory(new PropertyValueFactory<>("dateNaiss"));
                           tableCondidatLieuNaiss.setCellValueFactory(new PropertyValueFactory<>("lieuNaiss"));
                       
                        // Récupérer les informations des condidat de la base de données
                            GestionnaireCondidat gestionnaireCondidat = new GestionnaireCondidat();
                           ObservableList<Condidat> condidatsList = gestionnaireCondidat.recupererCondidat();

                           // Ajouter les données au TableView de condidat
                          tableCondidat.setItems(condidatsList);

                           // Créez un FilteredList pour la table des condidat
                            FilteredList<Condidat> filteredData = new FilteredList<>(condidatsList, p -> true);

                       
                        // Appliquez un listener sur le champ de recherche de la table des condidat 
                                searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                                filteredData.setPredicate(condidat -> {
                                // Si le champ de recherche est vide, on affiche tous les candidats
                                if (newValue == null || newValue.isEmpty()) {
                                   return true;
                                }
                                // Convertir le texte de recherche en minuscules pour éviter la casse
                                String lowerCaseFilter = newValue.toLowerCase();

                                // Vérifiez si le nom ou le prénom correspond à la recherche
                                if (condidat.getNom().toLowerCase().contains(lowerCaseFilter)) {
                                    return true; // Le nom contient le texte de recherche
                                } else if (condidat.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                                    return true; // Le prénom contient le texte de recherche
                                } else if (condidat.getDateNaiss().toLowerCase().contains(lowerCaseFilter)) {
                                    return true; // La date de naissance contient le texte de recherche
                                }
                                return false; // Aucun critère trouvé
                                });
                                });

                                // Appliquez le filtre à la TableView
                                SortedList<Condidat> sortedData = new SortedList<>(filteredData);
                                sortedData.comparatorProperty().bind(tableCondidat.comparatorProperty());
                                tableCondidat.setItems(sortedData);
                                
                                
                              EnregistrerSeanceButton.setOnAction(event -> enregistrerSeance());

    }    
    
}
