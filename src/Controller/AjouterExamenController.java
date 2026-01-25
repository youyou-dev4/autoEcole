/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.GestionnaireExamen;
import Modele.GestionnaireMoniteur;
import Modele.GestionnaireValeurParDefaut;
import Modele.GestionnaireVehicule;
import Modele.Moniteur;
import Modele.ValeurParDefaut;
import Modele.Vehicule;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author htw
 */
public class AjouterExamenController implements Initializable {

     @FXML
    private JFXDatePicker ExamenDatePicker;

    @FXML
    private JFXTextField insererNomExaminateur;

    @FXML
    private ListView<String> MoniteursEncadrantList;
    
    @FXML
    private ListView<String> vehiculeUtiliseList;

    @FXML
    private JFXButton ExitAjouterExamenButton;

    @FXML
    private JFXButton EnregistrerExamenButton;
    
    private GestionnaireMoniteur gestionnaireMoniteur = new GestionnaireMoniteur();
    private ObservableList<Moniteur> moniteurList;
    
    private GestionnaireVehicule gestionnaireVehicule = new GestionnaireVehicule();
    private ObservableList<Vehicule> vehiculeList;

    @FXML
    private void chargerMoniteurs() {
    // Récupérer les moniteurs depuis la base de données
    moniteurList = gestionnaireMoniteur.recupererMoniteur();

    // Ajouter les noms complets dans la ListView
    ObservableList<String> nomsComplets = FXCollections.observableArrayList();
    for (Moniteur moniteur : moniteurList) {
        nomsComplets.add(moniteur.getNomComplet());
    }
    MoniteursEncadrantList.setItems(nomsComplets);
    
    
    }
    
    @FXML
    private void chargerVehicule() {
    // Récupérer les moniteurs depuis la base de données
    vehiculeList = gestionnaireVehicule.recupererVehicules();

    // Ajouter les noms complets dans la ListView
    ObservableList<String> nomsComplets = FXCollections.observableArrayList();
    for (Vehicule vehicule : vehiculeList) {
        nomsComplets.add(vehicule.getNomComplet());
    }
    vehiculeUtiliseList.setItems(nomsComplets);
 
    }
    
    //methode pour afficher les message d'erreur 
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }       
    
    
    private boolean validerChamps() {
    // Vérifier les champs obligatoires
    if (ExamenDatePicker.getValue() == null || MoniteursEncadrantList.getSelectionModel().getSelectedItems().isEmpty() 
            || vehiculeUtiliseList.getSelectionModel().getSelectedItems().isEmpty()) {
        afficherAlerte("Erreur", "Veuillez remplir toutes les champs !");
        return false;
    }

    

    return true;
}
    
    
    
    @FXML
private void enregistrerExamen() {
    
    // Vérifier si les champs sont valides
    if (!validerChamps()) {
        return; // Si la validation échoue, on ne continue pas
    }
    
    
    GestionnaireExamen gestionnaireExamen = new GestionnaireExamen();

    // Récupérer les informations de l'interface utilisateur
    String dateExamen = ExamenDatePicker.getValue().toString(); // Assurez-vous que la DatePicker n'est pas null
    String nomExaminateur = insererNomExaminateur.getText();
    String vehicule = vehiculeUtiliseList.getSelectionModel().getSelectedItem();
    ObservableList<String> selection = MoniteursEncadrantList.getSelectionModel().getSelectedItems();

   

    // Récupérer les IDs des moniteurs sélectionnés
    ObservableList<Integer> idsMoniteurs = FXCollections.observableArrayList();
    for (String nomComplet : selection) {
        for (Moniteur moniteur : moniteurList) {
            if (moniteur.getNomComplet().equals(nomComplet)) {
                idsMoniteurs.add(moniteur.getId());
                break;
            }
        }
    }

    // Insérer l'examen et récupérer son ID
    int idExamen = gestionnaireExamen.insererExamen(dateExamen, nomExaminateur, vehicule);
    if (idExamen != -1) {
        // Insérer les moniteurs liés à l'examen
        gestionnaireExamen.insererMoniteursPourExamen(idExamen, idsMoniteurs);
        afficherAlerteEtFermerFenetre("Succès", "Examen enregistré avec succès !", true);
    } else {
        afficherAlerteEtFermerFenetre("Erreur", "Échec de l'enregistrement de l'examen.", false);
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
            Stage stage = (Stage) EnregistrerExamenButton.getScene().getWindow(); // Récupérer la fenêtre principale
            stage.close(); // Fermer la fenêtre
        }
    });
}
    
    
    


@FXML
private void fermerFenetre() {
    // Obtenir la fenêtre actuelle et la fermer
    Stage stage = (Stage) ExitAjouterExamenButton.getScene().getWindow();
    stage.close();
}


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        
        
        MoniteursEncadrantList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);                  
        chargerMoniteurs();
        
        chargerVehicule();
        
        EnregistrerExamenButton.setOnAction(event -> enregistrerExamen());
        
        
        
      }    
    
}
