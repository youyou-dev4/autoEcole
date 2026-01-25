/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.GestionnaireCondidat;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    

public class AjouterCondidatController implements Initializable {

    @FXML
    private ImageView PhotoCondidatAjouter;

    @FXML
    private JFXButton PhotoCondidatAjouterbutton;
    
    @FXML
    private JFXButton PhotoCondidatSupprimerbutton;
    
    private Image defaultImage; // Stocker l'image par défaut
     private boolean imageChoisie = false; // Indique si une image a été choisie
     
     
     //recuperer les elements de formulaire
      @FXML
    private JFXTextField insererNomCondidat;

    @FXML
    private JFXTextField insererPrenomCondidat;

    @FXML
    private JFXDatePicker insererDateNaissanceCondidat;

    @FXML
    private JFXTextField insererLieuNaissanceCondidat;

    @FXML
    private JFXTextField insererTelephoneCondidat;
    
     @FXML
    private JFXComboBox<String> insererGreoupeSainguinCondidat;
     
      @FXML
    private JFXTextArea insererAdresseCondidat;
      
      
       @FXML
    private JFXButton EnregistrerCondidatButton;

    //gere le button de choisir une photo
    private void gererBouton() {
        if (imageChoisie) {
            // Action pour supprimer l'image
            supprimerPhoto();
        } else {
            // Action pour ajouter une image
            choisirEtAfficherImage();
        }
    }
    
    
    //methode pour permettre de choidir une photo et de l'affiche
    private String cheminPhotoChoisie = null;
    private void choisirEtAfficherImage() {
        // Créer un FileChooser pour sélectionner une image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Ouvrir la boîte de dialogue
        File file = fileChooser.showOpenDialog(PhotoCondidatAjouterbutton.getScene().getWindow());
        if (file != null) {
             cheminPhotoChoisie = file.getAbsolutePath(); // Stocker le chemin
            // Charger et afficher l'image sélectionnée
            Image selectedImage = new Image(file.toURI().toString());
            PhotoCondidatAjouter.setImage(selectedImage);

            // Mettre à jour l'état et le texte du bouton
            imageChoisie = true;
            PhotoCondidatAjouterbutton.setText("Supprimer la photo");
        }
    }
    
    //methode pour supprimer la photo choisi
    private void supprimerPhoto() {
        // Réinitialiser à l'image par défaut
        PhotoCondidatAjouter.setImage(defaultImage);

        // Mettre à jour l'état et le texte du bouton
        imageChoisie = false;
        PhotoCondidatAjouterbutton.setText("Ajouter une photo");
    }
    
    
    
    //methode pour verifier que tout les champ sont remplie 
    
    private boolean validerChamps() {
        // Vérifier les champs obligatoires
        if (insererNomCondidat.getText().trim().isEmpty() ||
            insererPrenomCondidat.getText().trim().isEmpty() ||
            insererDateNaissanceCondidat.getValue() == null ||
            insererLieuNaissanceCondidat.getText().trim().isEmpty() ||
            insererTelephoneCondidat.getText().trim().isEmpty() ||
            insererGreoupeSainguinCondidat.getValue() == null ||
            insererAdresseCondidat.getText().trim().isEmpty()) {

            afficherAlerte("Erreur", "Veuillez remplir tous les champs.");
            return false;
        }

        // Valider le numéro de téléphone
        if (!insererTelephoneCondidat.getText().matches("^((\\+213|0)(5|6|7)\\d{8})$")) {
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
            Stage stage = (Stage) PhotoCondidatAjouter.getScene().getWindow(); // Récupérer la fenêtre principale
            stage.close(); // Fermer la fenêtre
        }
    });
}
    
    
    //methode pour sauvegarder la phot de condidat
    private String sauvegarderPhoto() {
    if (cheminPhotoChoisie == null) {
        afficherAlerte("Erreur", "Aucune photo choisie.");
        return null;
    }

    try {
        // Créer un dossier pour stocker les photos
        File dossier = new File("photo_condidat");
        if (!dossier.exists()) {
            dossier.mkdir();
        }

        // Nommer le fichier de manière unique
        File fichierPhoto = new File(dossier, System.currentTimeMillis() + ".png");

        // Copier l'image dans le nouveau fichier
        Files.copy(Paths.get(cheminPhotoChoisie), fichierPhoto.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return fichierPhoto.getAbsolutePath();
    } catch (IOException e) {
        afficherAlerte("Erreur", "Erreur lors de la sauvegarde de la photo.");
        e.printStackTrace();
        return null;
    }
}
    
    
    //methode pour enregistrer les information
    private void enregistrerCondidat() {
    // Vérifier si les champs sont valides
    if (!validerChamps()) {
        return; // Si la validation échoue, on ne continue pas
    }

    // Récupérer les informations saisies par l'utilisateur
    String nom = insererNomCondidat.getText();
    String prenom = insererPrenomCondidat.getText();
    LocalDate dateNaissance = insererDateNaissanceCondidat.getValue();
    String lieuNaissance = insererLieuNaissanceCondidat.getText();
    String telephone = insererTelephoneCondidat.getText();
    String groupeSanguin = insererGreoupeSainguinCondidat.getValue();
    String adresse = insererAdresseCondidat.getText();

    // Vérifier si la photo a été choisie
    String cheminPhoto = null;
    if (cheminPhotoChoisie != null) {
        cheminPhoto = sauvegarderPhoto(); // Appeler la méthode pour sauvegarder la photo
    }

    // Appeler la méthode du gestionnaireCondidat pour enregistrer le candidat
    GestionnaireCondidat gestionnaire = new GestionnaireCondidat();
    boolean success = gestionnaire.enregistrerCondidat(nom, prenom, dateNaissance, lieuNaissance, telephone, groupeSanguin, adresse, cheminPhoto);

    // Vérifier si l'enregistrement a réussi
    if (success) {
        afficherAlerteEtFermerFenetre("Succès", "Le candidat a été enregistré avec succès.", true);
    } else {
        afficherAlerteEtFermerFenetre("Erreur", "Une erreur s'est produite lors de l'enregistrement du candidat.", false);
    }
}
    
    
    //configurer le button effacer 
    
    @FXML
private JFXButton EffacerCondidatButton;

@FXML
private void effacerFormulaire() {
    // Réinitialiser les champs de texte
    insererNomCondidat.setText("");
    insererPrenomCondidat.setText("");
    insererLieuNaissanceCondidat.setText("");
    insererTelephoneCondidat.setText("");
    insererAdresseCondidat.setText("");

    // Réinitialiser la DatePicker
    insererDateNaissanceCondidat.setValue(null);

    // Réinitialiser la ComboBox
    insererGreoupeSainguinCondidat.setValue(null);

    // Réinitialiser l'image (si aucune image n'est choisie, afficher l'image par défaut)
    PhotoCondidatAjouter.setImage(defaultImage);

    // Réinitialiser le bouton de photo
    PhotoCondidatAjouterbutton.setText("Ajouter une photo");

    // Réinitialiser l'état de la photo (si aucune image n'est choisie)
    imageChoisie = false;
    cheminPhotoChoisie = null;
}


    //configurer le button EXIT
    
    @FXML
private JFXButton ExitAjouterCondidatButton;

@FXML
private void fermerFenetre() {
    // Obtenir la fenêtre actuelle et la fermer
    Stage stage = (Stage) ExitAjouterCondidatButton.getScene().getWindow();
    stage.close();
}

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          // Chemin relatif vers la photo par défaut dans le dossier "photo_condidat"
    File fichierPhotoDefaut = new File("photo_condidat/photo_par_defaut.jpg");
    
    if (fichierPhotoDefaut.exists()) {
        // Charger l'image par défaut depuis le dossier
        defaultImage = new Image(fichierPhotoDefaut.toURI().toString());
    } else {
        // Si le fichier n'existe pas, afficher un avertissement et définir une image par défaut alternative
        System.out.println("La photo par défaut n'existe pas dans le dossier photo_condidat.");
        defaultImage = new Image(getClass().getResource("/PhotoCondidat/photo_par_defaut.jpg").toExternalForm());
    }
    
    // Afficher l'image par défaut dans le composant ImageView
    PhotoCondidatAjouter.setImage(defaultImage);

        
       
        
        // Initialiser le texte du bouton
            PhotoCondidatAjouterbutton.setText("Ajouter une photo");
        
        // Configurer l'action du bouton
        PhotoCondidatAjouterbutton.setOnAction(event -> gererBouton());
        
         ObservableList<String> groupesSanguins = FXCollections.observableArrayList(
            "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        );

        // Ajouter les groupes sanguins au ComboBox
        insererGreoupeSainguinCondidat.setItems(groupesSanguins);
        
        
        
        EnregistrerCondidatButton.setOnAction(event -> enregistrerCondidat());
        
        
        
    }    
    
}
