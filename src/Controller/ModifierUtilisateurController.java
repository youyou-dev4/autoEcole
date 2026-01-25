
package Controller;

import Modele.Condidat;
import Modele.GestionnaireMoniteur;
import Modele.GestionnaireUtilisateur;
import Modele.UtilisateurInfo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;


public class ModifierUtilisateurController implements Initializable {

    @FXML
    private JFXTextField modifierNomUtilisateur;

    @FXML
    private JFXComboBox<String> modifierRoleUtilisateur;

    @FXML
    private HBox CondidatInfoSection1;

    @FXML
    private JFXTextField insererAncienMotdepasse;

    @FXML
    private JFXTextField insererNouveauMotdepasse;

    @FXML
    private JFXButton ExitModiferUtilisateurButton;

    @FXML
    private JFXButton ModifierUtilisateurButton;
    
        private int userId; // ID du candidat à modifier
        private String ancienpassword;
 
    
    //methode pour remplir les champs
    public void initialiserChamps(UtilisateurInfo user) {
        
        this.userId = user.getId(); // Stocker l'ID du candidat
        this.ancienpassword = user.getPassword();
        modifierNomUtilisateur.setText(user.getUsername());
       

        // Pré-remplir le ComboBox avec le groupe sanguin
        ObservableList<String> userRole = FXCollections.observableArrayList(
            "admin", "moniteur"
        );
        modifierRoleUtilisateur.setItems(userRole);
        modifierRoleUtilisateur.setValue(user.getRole());

        
    }
    
    
    
    //methode pour configurer le button EXIT 
    

@FXML
private void fermerFenetre() {
    // Obtenir la fenêtre actuelle et la fermer
    Stage stage = (Stage) ExitModiferUtilisateurButton.getScene().getWindow();
    stage.close();
}



//methode pour valider les champs 
private boolean validerChamps() {
    if (modifierNomUtilisateur.getText().trim().isEmpty() ||
        modifierRoleUtilisateur.getValue() == null ||
        insererAncienMotdepasse.getText().trim().isEmpty() ||
        insererNouveauMotdepasse.getText().trim().isEmpty()    ) {

        afficherAlerte("Erreur", "Veuillez remplir tous les champs.");
        return false;
    }

    return true;
}

//methode pour afficher des alerte
private void afficherAlerte(String titre, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(titre);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}


public boolean verifierMotDePasse( String ancienMotDePasse ) {
    if (ancienpassword == null || !ancienpassword.startsWith("$2a$")) {
        afficherAlerte("Erreur", "Le mot de passe actuel est dans un format incorrect.");
        return false;
    }
            if (ancienpassword == null || ancienpassword.isEmpty()) {
        afficherAlerte("Erreur", "L'ancien mot de passe n'est pas disponible.");
        return false;
    }
            return BCrypt.checkpw( ancienMotDePasse, ancienpassword);        
}


@FXML
private void modifierUtilisateur() {
    // Valider les champs avant la mise à jour
    if (!validerChamps()) {
        return;
    }

    // Récupérer les nouvelles valeurs des champs
    String username = modifierNomUtilisateur.getText().trim();   
    String role = modifierRoleUtilisateur.getValue().toString();
    String newPassword = insererNouveauMotdepasse.getText().trim();
    String oldPassword = insererAncienMotdepasse.getText().trim();
    
    if ( !verifierMotDePasse(oldPassword)){
                afficherAlerte("Erreur", "Le mot de passe est incorrecte.");
                return;
    }
    
    
    
    


    // Mettre à jour les informations du candidat dans la base de données
    GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur();
    boolean success = gestionnaire.mettreAJourUtilisateur(userId, username, newPassword, role);

    if (success) {
        afficherAlerte("Succès", "Les informations d'utilisateur ont été mises à jour avec succès.");
        // Fermer la fenêtre après la mise à jour
        Stage stage = (Stage) modifierRoleUtilisateur.getScene().getWindow();
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
