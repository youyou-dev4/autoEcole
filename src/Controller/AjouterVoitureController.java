
package Controller;

import Modele.GestionnaireMoniteur;
import Modele.GestionnaireVehicule;
import Modele.Moniteur;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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


public class AjouterVoitureController implements Initializable {
    
            
            @FXML
            private JFXTextField insererNumAssuranceVoiture;
            
            @FXML
            private JFXComboBox<String> insererTypeAssuranceVoiture;
            
            @FXML
            private JFXDatePicker insererDateAssuranceVoiture;

            @FXML
            private JFXDatePicker insererDateControleVoiture;
            
            @FXML
            private JFXTextField insererMatriculeVoiture;
            
            @FXML
            private JFXComboBox<String> insererTypeVoiture;
            
            @FXML
            private JFXTextField insererModeleVoiture;
            
            @FXML
            private JFXTextField insererMarqueVoiture;
    
            @FXML
            private JFXComboBox<String> insererProprietaireVoiture;
            
            
            // Méthode pour remplir le comboBox avec les noms complets des moniteurs
        public void remplirComboBox() {
            GestionnaireMoniteur gestionnaireMoniteur = new GestionnaireMoniteur(); // Instance de votre gestionnaire
            ObservableList<Moniteur> listeMoniteurs = gestionnaireMoniteur.recupererMoniteur();

            for (Moniteur moniteur : listeMoniteurs) {
                String nomComplet = moniteur.getNom() + " " + moniteur.getPrenom(); // Concaténation nom et prénom
                insererProprietaireVoiture.getItems().add(nomComplet);
            }
        }
        
        
        //methode pour verifier que tout les champ sont remplie 
    
    private boolean validerChamps() {
        // Vérifier les champs obligatoires
        if (insererMarqueVoiture.getText().trim().isEmpty() ||
            insererModeleVoiture.getText().trim().isEmpty() ||
            insererProprietaireVoiture.getValue() == null ||
            insererTypeVoiture.getValue() == null ||
            insererMatriculeVoiture.getText().trim().isEmpty() ||
            insererDateControleVoiture.getValue() == null || 
            insererDateAssuranceVoiture.getValue() == null ||
            insererTypeAssuranceVoiture.getValue() == null ||
            insererNumAssuranceVoiture.getText().trim().isEmpty() ) {        

            afficherAlerte("Erreur", "Veuillez remplir tous les champs obligatoires.");
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
    private JFXButton EnregistrerVoitureButton;
    
    //methode pour enregistrer les information
    private void enregistrerVehicule() {
    // Vérifier si les champs sont valides
    if (!validerChamps()) {
        return; // Si la validation échoue, on ne continue pas
    }

    // Récupérer les informations saisies par l'utilisateur
    // Récupérer les valeurs des champs
    String marque = insererMarqueVoiture.getText().trim();
    String modele = insererModeleVoiture.getText().trim();
    String immatriculation = insererMatriculeVoiture.getText().trim();
    String type = insererTypeVoiture.getValue();
    String proprietaire = insererProprietaireVoiture.getValue(); // Récupère la valeur sélectionnée
    String numAssurance = insererNumAssuranceVoiture.getText().trim();
    String typeAssurance = insererTypeAssuranceVoiture.getValue();
    LocalDate dateAssurance = insererDateAssuranceVoiture.getValue();
    LocalDate dateControle = insererDateControleVoiture.getValue();

    

    // Appeler la méthode d'ajout
    GestionnaireVehicule gestionnaireVehicule = new GestionnaireVehicule();
    boolean success = gestionnaireVehicule.ajouterVehicule(
        marque, modele, immatriculation, type, proprietaire,
        dateControle, dateAssurance, typeAssurance, numAssurance
    );


    // Vérifier si l'enregistrement a réussi
    if (success) {
        afficherAlerteEtFermerFenetre("Succès", "Le vehicule a été enregistré avec succès.", true);
    } else {
        afficherAlerteEtFermerFenetre("Erreur", "Une erreur s'est produite lors de l'enregistrement du vehicule.", false);
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
            Stage stage = (Stage) insererMarqueVoiture.getScene().getWindow(); // Récupérer la fenêtre principale
            stage.close(); // Fermer la fenêtre
        }
    });
}



    //configurer le button effacer 
    
    @FXML
private JFXButton EffacerVoitureButton;

@FXML
private void effacerFormulaire() {
    // Réinitialiser les champs de texte
    insererDateControleVoiture.setValue(null);
    insererDateAssuranceVoiture.setValue(null);
    insererTypeAssuranceVoiture.setValue(null);
    insererNumAssuranceVoiture.setText("");
    insererTypeVoiture.setValue(null);
    insererMatriculeVoiture.setText("");
    insererModeleVoiture.setText("");
    insererMarqueVoiture.setText("");

   
    insererProprietaireVoiture.setValue(null);
  
}


    //configurer le button EXIT
    
    @FXML
private JFXButton ExitAjouterVoitureButton;

@FXML
private void fermerFenetre() {
    // Obtenir la fenêtre actuelle et la fermer
    Stage stage = (Stage) ExitAjouterVoitureButton.getScene().getWindow();
    stage.close();
}


        
        
            
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        insererProprietaireVoiture.getItems().add("admin");
        remplirComboBox();
        
                        EnregistrerVoitureButton.setOnAction(event -> enregistrerVehicule());
        
         ObservableList<String> groupesAssurance = FXCollections.observableArrayList(
            "tous risques", "au tiers", "intermediaire"
        );

        // Ajouter les type d'assurance au ComboBox
        insererTypeAssuranceVoiture.setItems(groupesAssurance);               
        
        
        
        ObservableList<String> groupesVehicule = FXCollections.observableArrayList(
            "voiture", "moto", "camion", "utilitaire", "autre"
        );

        // Ajouter les type vehicule au ComboBox
        insererTypeVoiture.setItems(groupesVehicule);    
    }    
    
}
