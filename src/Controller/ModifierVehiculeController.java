
package Controller;

import Modele.GestionnaireMoniteur;
import Modele.GestionnaireVehicule;
import Modele.Moniteur;
import Modele.Vehicule;
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


public class ModifierVehiculeController implements Initializable {
                
                private int vehiculeId; // ID du candidat à modifier

    
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
            
            
            //methode pour remplir les champs
    public void initialiserChamps(Vehicule vehicule) {
        
        this.vehiculeId = vehicule.getIdVehicule(); // Stocker l'ID du candidat
        
        insererDateControleVoiture.setValue(vehicule.getDateControle());
        insererDateAssuranceVoiture.setValue(vehicule.getDateAssurance());
        insererTypeAssuranceVoiture.setValue(vehicule.getTypeAssurance());
        insererNumAssuranceVoiture.setText(vehicule.getNumAssurance());
        insererTypeVoiture.setValue(vehicule.getTypeVehicule());
        insererMatriculeVoiture.setText(vehicule.getImmatriculation());
        insererModeleVoiture.setText(vehicule.getModele());
        insererMarqueVoiture.setText(vehicule.getMarque());

   
        insererProprietaireVoiture.setValue(vehicule.getProprietaire());
    
    }
            
            
            
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
    
    
    //methode pour mettre a jour les info 
@FXML
    private JFXButton ModifierVoitureButton;

@FXML
private void modifierVehicule() {
    // Valider les champs avant la mise à jour
    if (!validerChamps()) {
        return;
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
    

    // Appeler la méthode de mise à jour
    GestionnaireVehicule gestionnaireVehicule = new GestionnaireVehicule();
    boolean success = gestionnaireVehicule.mettreAJourVehicule(
        vehiculeId, marque, modele, immatriculation, type, 
        proprietaire, dateControle, dateAssurance, typeAssurance, numAssurance
    );

    
    
    if (success) {
        afficherAlerte("Succès", "Les informations du vehicule ont été mises à jour avec succès.");
        // Fermer la fenêtre après la mise à jour
        Stage stage = (Stage) ModifierVoitureButton.getScene().getWindow();
        stage.close();
    } else {
        afficherAlerte("Erreur", "Une erreur s'est produite lors de la mise à jour des informations.");
    }
}

    
    
    
    //configurer le button EXIT
    
    @FXML
private JFXButton ExitModifierVoitureButton;

@FXML
private void fermerFenetre() {
    // Obtenir la fenêtre actuelle et la fermer
    Stage stage = (Stage) ExitModifierVoitureButton.getScene().getWindow();
    stage.close();
}
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        insererProprietaireVoiture.getItems().add("admin");
        remplirComboBox();
        
                        ModifierVoitureButton.setOnAction(event -> modifierVehicule());
        
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
