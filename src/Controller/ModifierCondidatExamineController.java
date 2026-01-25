/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.Condidat;
import Modele.Examen;
import Modele.ExamenCondidatExamine;
import Modele.GestionnaireExamen;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author htw
 */
public class ModifierCondidatExamineController implements Initializable {

    
    @FXML
            private ComboBox<String> permis;
            
            @FXML
            private ComboBox<String> epreuve;
            
            @FXML
            private ComboBox<String> resultat;
            
            @FXML
            private Text nomCondidat;
            
            private int idExamen ;
            
            private int idCondidat;
            
            
            
            public void initialiserChamps(Examen examen ,ExamenCondidatExamine condidat ){
                idExamen = examen.getIdExamen();
                idCondidat = condidat.getIdCondidat();
                permis.setValue(condidat.getPermis());
                epreuve.setValue(condidat.getEpreuve());
                resultat.setValue(condidat.getResultat());
                nomCondidat.setText(condidat.getNomCondidat());
            }
            
            private boolean validerChamps() {
        // Vérifier les champs obligatoires
        if ( epreuve.getValue() == null || permis.getValue() == null || resultat.getValue() == null ) {

            afficherAlerte("Erreur", "Veuillez remplir tout les case.");
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
    private void enregistrerCondidatAuExamen() {
    // Vérifier si les champs sont valides
    if (!validerChamps()) {
        return; // Si la validation échoue, on ne continue pas
    }

    // Récupérer les informations saisies par l'utilisateur
        String permisSelectionne = permis.getValue();
        String epreuveSelectionne = epreuve.getValue();
        String resultatSelectionne = resultat.getValue();
        
    

   // Ajouter le versement à la base de données
    GestionnaireExamen gestionnaireExamen = new GestionnaireExamen();
    boolean success = gestionnaireExamen.modifierCondidatAuExamen(idExamen ,idCondidat,epreuveSelectionne, permisSelectionne,resultatSelectionne );

    // Vérifier si l'enregistrement a réussi
    if (success) {
        afficherAlerteEtFermerFenetre("Succès", "Le condidat a été modifier avec succes avec succès.", true);
    } else {
        afficherAlerteEtFermerFenetre("Erreur", "Une erreur s'est produite lors de la modification de condidat.", false);
    }
}
    
    @FXML
private JFXButton EnregistrerVersementButton;
    
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
            Stage stage = (Stage) EnregistrerVersementButton.getScene().getWindow(); // Récupérer la fenêtre principale
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
        ObservableList<String> permisList = FXCollections.observableArrayList(
                                        "A1", 
                                        "A2", 
                                        "B",
                                        "C", 
                                        "E", 
                                        "D"
                                    );
          
          permis.setItems(permisList); 
          
          
           ObservableList<String> epreuveList = FXCollections.observableArrayList(
                                        "code", 
                                        "crenaux", 
                                        "circulation"                                       
                                    );
          
          epreuve.setItems(epreuveList); 
          
          
          ObservableList<String> resultatList = FXCollections.observableArrayList(
                                        "en attente", 
                                        "réussi", 
                                        "échoué"                                       
                                    );
          
          resultat.setItems(resultatList); 
          
          
          EnregistrerVersementButton.setOnAction(event -> enregistrerCondidatAuExamen());

    }    
    
}
