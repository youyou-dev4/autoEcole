/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.GestionnaireUtilisateur;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class AjouterUtilisateurController implements Initializable {

    
    @FXML
    private HBox CondidatInfoSection;

    @FXML
    private JFXTextField insererNomUtilisateur;

    @FXML
    private JFXTextField insererMotdepasseUtilisateur;

    @FXML
    private JFXComboBox<String> insererRoleUtilisateur;

    @FXML
    private JFXButton ExitAjouterUtilisateurButton;

    @FXML
    private JFXButton EffacerUtilisateurButton;

    @FXML
    private JFXButton EnregistrerUtilisateurButton;
    
    
    
    @FXML
    void quitterApplication(ActionEvent event) {
        // Obtenir la fenêtre actuelle et la fermer
    Stage stage = (Stage) ExitAjouterUtilisateurButton.getScene().getWindow();
    stage.close();
    }

    // Méthode pour effacer les champs
    @FXML
    void effacerChamps(ActionEvent event) {
        insererNomUtilisateur.clear();
        insererMotdepasseUtilisateur.clear();
        insererRoleUtilisateur.getSelectionModel().clearSelection();
    }
    
    
    @FXML
    void enregistrerUtilisateur(ActionEvent event) {
        String username = insererNomUtilisateur.getText();
        String password = insererMotdepasseUtilisateur.getText();
        String role = insererRoleUtilisateur.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs Manquants");
            alert.setHeaderText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {
            GestionnaireUtilisateur gestionnaireUtilisateur = new GestionnaireUtilisateur();
            boolean succes = gestionnaireUtilisateur.enregistrerUtilisateur(username, password, role);

            // Vérifier si l'enregistrement a réussi
    if (succes) {
        afficherAlerteEtFermerFenetre("Succès", "L'utilisateur a été enregistré avec succès.", true);
    } else {
        afficherAlerteEtFermerFenetre("Erreur", "Une erreur s'est produite lors de l'enregistrement d'utilisateur.", false);
    }
        }
    }
    
    
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
            Stage stage = (Stage) insererNomUtilisateur.getScene().getWindow(); // Récupérer la fenêtre principale
            stage.close(); // Fermer la fenêtre
        }
    });
}
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<String> roleList = FXCollections.observableArrayList(
            "admin", "moniteur"
        );
        
        // Ajouter les role au ComboBox
        insererRoleUtilisateur.setItems(roleList);
        
    }    
    
}
