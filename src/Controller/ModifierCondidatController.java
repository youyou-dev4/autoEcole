/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.Condidat;
import Modele.GestionnaireCondidat;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author htw
 */
public class ModifierCondidatController implements Initializable {
    
    private int candidatId; // ID du candidat à modifier

    @FXML
    private JFXTextField modifierNomCondidat;

    @FXML
    private JFXTextField modifierPrenomCondidat;

    @FXML
    private JFXDatePicker modifierDateNaissanceCondidat;

    @FXML
    private JFXTextField modifierLieuNaissanceCondidat;

    @FXML
    private JFXTextField modifierTelephoneCondidat;

    @FXML
    private ImageView PhotoCondidatModifier;

    @FXML
    private JFXComboBox<String> modifierGreoupeSainguinCondidat;

    @FXML
    private JFXTextArea modifierAdresseCondidat;
    
    @FXML
    private JFXTextField modifierNumCondidat;
    
    @FXML
private JFXButton PhotoCondidatModifierbutton;



private String photoPath; // Chemin de la photo actuelle du candidat

//methode pour remplir les champs
    public void initialiserChamps(Condidat condidat) {
        
        this.candidatId = condidat.getId(); // Stocker l'ID du candidat
        
        modifierNomCondidat.setText(condidat.getNom());
        modifierPrenomCondidat.setText(condidat.getPrenom());
        modifierDateNaissanceCondidat.setValue(LocalDate.parse(condidat.getDateNaiss()));
        modifierLieuNaissanceCondidat.setText(condidat.getLieuNaiss());
        modifierTelephoneCondidat.setText(condidat.getTlphn());
        modifierAdresseCondidat.setText(condidat.getAdr());
        modifierNumCondidat.setText(condidat.getNumCondidat());

        // Pré-remplir le ComboBox avec le groupe sanguin
        ObservableList<String> groupesSanguins = FXCollections.observableArrayList(
            "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        );
        modifierGreoupeSainguinCondidat.setItems(groupesSanguins);
        modifierGreoupeSainguinCondidat.setValue(condidat.getGroupe_sanguin());

        // Charger l'image du candidat
        photoPath = condidat.getPhoto();
    if (photoPath == null || photoPath.isEmpty()) {
        // Définir l'image par défaut si aucune photo n'existe
        Image defaultImage = new Image(getClass().getResource("/PhotoCondidat/photo_par_defaut.jpg").toExternalForm());
        PhotoCondidatModifier.setImage(defaultImage);
        PhotoCondidatModifierbutton.setText("Ajouter photo");
    } else {
        // Charger l'image existante
        Image candidateImage = new Image(new File(photoPath).toURI().toString());
        PhotoCondidatModifier.setImage(candidateImage);
        PhotoCondidatModifierbutton.setText("Supprimer photo");
    }
    }
    
    
    
    //methode pour configurer le button EXIT 
     @FXML
private JFXButton ExitAjouterCondidatButton;

@FXML
private void fermerFenetre() {
    // Obtenir la fenêtre actuelle et la fermer
    Stage stage = (Stage) ExitAjouterCondidatButton.getScene().getWindow();
    stage.close();
}



//gere le button d'ajouter une photo
@FXML
private void gererPhotoCondidat() {
    if ("Ajouter photo".equals(PhotoCondidatModifierbutton.getText())) {
        // Ouvrir un sélecteur de fichier pour ajouter une photo
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une photo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(PhotoCondidatModifierbutton.getScene().getWindow());
        if (selectedFile != null) {
            photoPath = selectedFile.getAbsolutePath();
            Image newImage = new Image(selectedFile.toURI().toString());
            PhotoCondidatModifier.setImage(newImage);
            PhotoCondidatModifierbutton.setText("Supprimer photo");
        }
    } else if ("Supprimer photo".equals(PhotoCondidatModifierbutton.getText())) {
        // Réinitialiser à l'image par défaut
        photoPath = null;
        Image defaultImage = new Image(getClass().getResource("/PhotoCondidat/photo_par_defaut.jpg").toExternalForm());
        PhotoCondidatModifier.setImage(defaultImage);
        PhotoCondidatModifierbutton.setText("Ajouter photo");
    }
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
    if (modifierNomCondidat.getText().trim().isEmpty() ||
        modifierPrenomCondidat.getText().trim().isEmpty() ||
        modifierDateNaissanceCondidat.getValue() == null ||
        modifierLieuNaissanceCondidat.getText().trim().isEmpty() ||
        modifierTelephoneCondidat.getText().trim().isEmpty() ||
        modifierGreoupeSainguinCondidat.getValue() == null ||
        modifierAdresseCondidat.getText().trim().isEmpty()) {

        afficherAlerte("Erreur", "Veuillez remplir tous les champs obligatoires.");
        return false;
    }

    if (!modifierTelephoneCondidat.getText().matches("^((\\+213|0)(5|6|7)\\d{8})$")) {
        afficherAlerte("Erreur", "Le numéro de téléphone est invalide.");
        return false;
    }

    return true;
}


//methode pour mettre a jour les info 
@FXML
    private JFXButton ModifierCondidatButton;

@FXML
private void modifierCondidat() {
    // Valider les champs avant la mise à jour
    if (!validerChamps()) {
        return;
    }

    // Récupérer les nouvelles valeurs des champs
    String nom = modifierNomCondidat.getText().trim();
    String prenom = modifierPrenomCondidat.getText().trim();
    LocalDate dateNaissance = modifierDateNaissanceCondidat.getValue();
    String lieuNaissance = modifierLieuNaissanceCondidat.getText().trim();
    String telephone = modifierTelephoneCondidat.getText().trim();
    String numCondidat = modifierNumCondidat.getText().trim();
    String adresse = modifierAdresseCondidat.getText().trim();
    String groupeSanguin = modifierGreoupeSainguinCondidat.getValue().toString();

    // Vérifier si une photo est sélectionnée
    String nouvellePhoto = null;
    if (photoPath != null) {
        nouvellePhoto = photoPath; // Utilisez le chemin de la nouvelle photo sélectionnée
    }

    // Mettre à jour les informations du candidat dans la base de données
    GestionnaireCondidat gestionnaire = new GestionnaireCondidat();
    boolean success = gestionnaire.mettreAJourCondidat(candidatId, numCondidat, nom, prenom, dateNaissance, lieuNaissance, telephone, groupeSanguin, adresse, nouvellePhoto);

    if (success) {
        afficherAlerte("Succès", "Les informations du candidat ont été mises à jour avec succès.");
        // Fermer la fenêtre après la mise à jour
        Stage stage = (Stage) ModifierCondidatButton.getScene().getWindow();
        stage.close();
    } else {
        afficherAlerte("Erreur", "Une erreur s'est produite lors de la mise à jour des informations.");
    }
}



    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
