
package Controller;

import Modele.Condidat;
import Modele.GestionnaireCondidat;
import Modele.GestionnaireMoniteur;
import Modele.Moniteur;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class ModifierMoniteurController implements Initializable {

    private int moniteurId; // ID du candidat à modifier

    @FXML
    private JFXTextField modifierNomMoniteur;

    @FXML
    private JFXTextField modifierPrenomMoniteur;

    @FXML
    private JFXDatePicker modifierDateNaissanceMoniteur;

    @FXML
    private JFXTextField modifierLieuNaissanceMoniteur;

    @FXML
    private JFXTextField modifierTelephoneMoniteur;
  

    @FXML
    private JFXComboBox<String> modifierGreoupeSainguinMoniteur;

    @FXML
    private JFXTextArea modifierAdresseMoniteur;
    
    
    //methode pour remplir les champs
    public void initialiserChamps(Moniteur moniteur) {
        
        this.moniteurId = moniteur.getId(); // Stocker l'ID du candidat
        
        modifierNomMoniteur.setText(moniteur.getNom());
        modifierPrenomMoniteur.setText(moniteur.getPrenom());
        modifierDateNaissanceMoniteur.setValue(LocalDate.parse(moniteur.getDateNaiss()));
        modifierLieuNaissanceMoniteur.setText(moniteur.getLieuNaiss());
        modifierTelephoneMoniteur.setText(moniteur.getTlphn());
        modifierAdresseMoniteur.setText(moniteur.getAdr());

        // Pré-remplir le ComboBox avec le groupe sanguin
        ObservableList<String> groupesSanguins = FXCollections.observableArrayList(
            "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        );
        modifierGreoupeSainguinMoniteur.setItems(groupesSanguins);
        modifierGreoupeSainguinMoniteur.setValue(moniteur.getGroupe_sanguin());
    
    }
    
    
     //methode pour configurer le button EXIT 
     @FXML
private JFXButton ExitModifierMoniteurButton;

@FXML
private void fermerFenetre() {
    // Obtenir la fenêtre actuelle et la fermer
    Stage stage = (Stage) ExitModifierMoniteurButton.getScene().getWindow();
    stage.close();
}
    


//methode pour afficher des alerte
private void afficherAlerte(String titre, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(titre);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}


//methode pour valider les champs 
private boolean validerChamps() {
    if (modifierNomMoniteur.getText().trim().isEmpty() ||
        modifierPrenomMoniteur.getText().trim().isEmpty() ||
        modifierDateNaissanceMoniteur.getValue() == null ||
        modifierLieuNaissanceMoniteur.getText().trim().isEmpty() ||
        modifierTelephoneMoniteur.getText().trim().isEmpty() ||
        modifierGreoupeSainguinMoniteur.getValue() == null ||
        modifierAdresseMoniteur.getText().trim().isEmpty()) {

        afficherAlerte("Erreur", "Veuillez remplir tous les champs obligatoires.");
        return false;
    }

    if (!modifierTelephoneMoniteur.getText().matches("^((\\+213|0)(5|6|7)\\d{8})$")) {
        afficherAlerte("Erreur", "Le numéro de téléphone est invalide.");
        return false;
    }

    return true;
}



//methode pour mettre a jour les info 
@FXML
    private JFXButton ModifierMoniteurButton;

@FXML
private void modifierCondidat() {
    // Valider les champs avant la mise à jour
    if (!validerChamps()) {
        return;
    }

    // Récupérer les nouvelles valeurs des champs
    String nom = modifierNomMoniteur.getText().trim();
    String prenom = modifierPrenomMoniteur.getText().trim();
    LocalDate dateNaissance = modifierDateNaissanceMoniteur.getValue();
    String lieuNaissance = modifierLieuNaissanceMoniteur.getText().trim();
    String telephone = modifierTelephoneMoniteur.getText().trim();
    String adresse = modifierAdresseMoniteur.getText().trim();
    String groupeSanguin = modifierGreoupeSainguinMoniteur.getValue().toString();

    

    // Mettre à jour les informations du candidat dans la base de données
    GestionnaireMoniteur gestionnaire = new GestionnaireMoniteur();
    boolean success = gestionnaire.mettreAJourMoniteur(moniteurId, nom, prenom, dateNaissance, lieuNaissance, telephone, groupeSanguin, adresse);

    if (success) {
        afficherAlerte("Succès", "Les informations du candidat ont été mises à jour avec succès.");
        // Fermer la fenêtre après la mise à jour
        Stage stage = (Stage) ModifierMoniteurButton.getScene().getWindow();
        stage.close();
    } else {
        afficherAlerte("Erreur", "Une erreur s'est produite lors de la mise à jour des informations.");
    }
}







    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
