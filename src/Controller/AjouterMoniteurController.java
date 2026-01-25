/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.GestionnaireCondidat;
import Modele.GestionnaireMoniteur;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author htw
 */
public class AjouterMoniteurController implements Initializable {

    //recuperer les elements de formulaire
      @FXML
    private JFXTextField insererNomMoniteur;

    @FXML
    private JFXTextField insererPrenomMoniteur;

    @FXML
    private JFXDatePicker insererDateNaissanceMoniteur;

    @FXML
    private JFXTextField insererLieuNaissanceMoniteur;

    @FXML
    private JFXTextField insererTelephoneMoniteur;
    
     @FXML
    private JFXComboBox<String> insererGreoupeSainguinMoniteur;
     
      @FXML
    private JFXTextArea insererAdresseMoniteur;
      
      
       @FXML
    private JFXButton EnregistrerMoniteurButton;
    
    
       //methode pour verifier que tout les champ sont remplie 
    
    private boolean validerChamps() {
        // Vérifier les champs obligatoires
        if (insererNomMoniteur.getText().trim().isEmpty() ||
            insererPrenomMoniteur.getText().trim().isEmpty() ||
            insererDateNaissanceMoniteur.getValue() == null ||
            insererLieuNaissanceMoniteur.getText().trim().isEmpty() ||
            insererTelephoneMoniteur.getText().trim().isEmpty() ||
            insererGreoupeSainguinMoniteur.getValue() == null ||
            insererAdresseMoniteur.getText().trim().isEmpty()) {

            afficherAlerte("Erreur", "Veuillez remplir tous les champs obligatoires.");
            return false;
        }

        // Valider le numéro de téléphone
        if (!insererTelephoneMoniteur.getText().matches("^((\\+213|0)(5|6|7)\\d{8})$")) {
            afficherAlerte("Erreur", "Le numéro de téléphone est invalide.");
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
    
    
    
     //methode pour enregistrer les information
    private void enregistrerMoniteur() {
    // Vérifier si les champs sont valides
    if (!validerChamps()) {
        return; // Si la validation échoue, on ne continue pas
    }

    // Récupérer les informations saisies par l'utilisateur
    String nom = insererNomMoniteur.getText();
    String prenom = insererPrenomMoniteur.getText();
    LocalDate dateNaissance = insererDateNaissanceMoniteur.getValue();
    String lieuNaissance = insererLieuNaissanceMoniteur.getText();
    String telephone = insererTelephoneMoniteur.getText();
    String groupeSanguin = insererGreoupeSainguinMoniteur.getValue();
    String adresse = insererAdresseMoniteur.getText();

    

    // Appeler la méthode du gestionnaireCondidat pour enregistrer le candidat
    GestionnaireMoniteur gestionnaire = new GestionnaireMoniteur();
    boolean success = gestionnaire.enregistrerMoniteur(nom, prenom, dateNaissance, lieuNaissance, telephone, groupeSanguin, adresse);

    // Vérifier si l'enregistrement a réussi
    if (success) {
        afficherAlerteEtFermerFenetre("Succès", "Le moniteur a été enregistré avec succès.", true);
    } else {
        afficherAlerteEtFermerFenetre("Erreur", "Une erreur s'est produite lors de l'enregistrement du moniteur.", false);
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
            Stage stage = (Stage) EnregistrerMoniteurButton.getScene().getWindow(); // Récupérer la fenêtre principale
            stage.close(); // Fermer la fenêtre
        }
    });
}





//configurer le button effacer 
    
    @FXML
private JFXButton EffacerMoniteurButton;

@FXML
private void effacerFormulaire() {
    // Réinitialiser les champs de texte
    insererNomMoniteur.setText("");
    insererPrenomMoniteur.setText("");
    insererLieuNaissanceMoniteur.setText("");
    insererTelephoneMoniteur.setText("");
    insererAdresseMoniteur.setText("");

    // Réinitialiser la DatePicker
    insererDateNaissanceMoniteur.setValue(null);

    // Réinitialiser la ComboBox
    insererGreoupeSainguinMoniteur.setValue(null);

    
}


    //configurer le button EXIT
    
    @FXML
private JFXButton ExitAjouterMoniteurButton;

@FXML
private void fermerFenetre() {
    // Obtenir la fenêtre actuelle et la fermer
    Stage stage = (Stage) ExitAjouterMoniteurButton.getScene().getWindow();
    stage.close();
}



    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<String> groupesSanguins = FXCollections.observableArrayList(
            "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        );

        // Ajouter les groupes sanguins au ComboBox
        insererGreoupeSainguinMoniteur.setItems(groupesSanguins);
        
                EnregistrerMoniteurButton.setOnAction(event -> enregistrerMoniteur());

    }    
    
}
