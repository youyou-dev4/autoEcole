/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.Condidat;
import Modele.GestionnaireQuantabilite;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class AjouterDepenceController implements Initializable {
    
    @FXML
            private JFXTextField insererMontant;
    @FXML
            private JFXTextArea motif;
    
     private boolean validerChamps() {
        // Vérifier les champs obligatoires
        if (motif.getText().isEmpty() || insererMontant.getText().isEmpty()) {

            afficherAlerte("Erreur", "Veuillez remplir tout les champ.");
            return false;
        }
        
        double montant;
    try {
      String montantText = insererMontant.getText().replace(',', '.');
        montant = Double.parseDouble(montantText);
    } catch (NumberFormatException e) {
        afficherAlerte("Erreur","Le montant doit être un nombre valide !");
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
    private void enregistrerDepence() {
    // Vérifier si les champs sont valides
    if (!validerChamps()) {
        return; // Si la validation échoue, on ne continue pas
    }

    // Récupérer les informations saisies par l'utilisateur
        String motifValue = motif.getText();
        String montantText = insererMontant.getText().replace(',', '.');
        double     montant = Double.parseDouble(montantText);
    

   // Ajouter le versement à la base de données
    GestionnaireQuantabilite gestionnaireVersement = new GestionnaireQuantabilite();
    boolean success = gestionnaireVersement.ajouterDepence( montant,motifValue);

    // Vérifier si l'enregistrement a réussi
    if (success) {
        afficherAlerteEtFermerFenetre("Succès", "La depence a été enregistré avec succès.", true);
    } else {
        afficherAlerteEtFermerFenetre("Erreur", "Une erreur s'est produite lors de l'enregistrement du depence.", false);
    }
}
    
    @FXML
private JFXButton EnregistrerDepenceButton;
    
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
            Stage stage = (Stage) EnregistrerDepenceButton.getScene().getWindow(); // Récupérer la fenêtre principale
            stage.close(); // Fermer la fenêtre
        }
    });
}
    
    
      @FXML
private JFXButton ExitAjouterDepenceButton;

@FXML
private void fermerFenetre() {
    // Obtenir la fenêtre actuelle et la fermer
    Stage stage = (Stage) ExitAjouterDepenceButton.getScene().getWindow();
    stage.close();
}
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             EnregistrerDepenceButton.setOnAction(event -> enregistrerDepence());
             
              // Listener pour formater le montant avec deux décimales
                                insererMontant.setOnMouseClicked(event -> {
                                if (insererMontant.getText().isEmpty()) {
                                    insererMontant.setText("0.00");
                                }
                            });
                                
                                   // Éviter que le champ de montant soit sélectionné au démarrage
                            Platform.runLater(() -> {
                                motif.requestFocus(); // Donne le focus au champ de recherche ou à un autre élément
                            });
    }    
    
}
