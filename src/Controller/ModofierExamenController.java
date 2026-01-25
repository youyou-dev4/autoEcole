/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.Examen;
import Modele.ExamenMoniteurEncadrant;
import Modele.GestionnaireExamen;
import Modele.GestionnaireMoniteur;
import Modele.GestionnaireVehicule;
import Modele.Moniteur;
import Modele.Vehicule;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;


public class ModofierExamenController implements Initializable {

    
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
    
    private int idExamen;
    
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
    
    
    
    @FXML
    public void initialiserChamps(Examen examen) {
    idExamen = examen.getIdExamen();
    // 1. Remplir la date de l'examen
    ExamenDatePicker.setValue(LocalDate.parse(examen.getDate()));

    // 2. Remplir le champ du nom de l'examinateur
    insererNomExaminateur.setText(examen.getNomExaminateur());

    // 3. Récupérer les moniteurs encadrants liés à cet examen
    GestionnaireExamen gestionnaireExamen = new GestionnaireExamen();
    ObservableList<ExamenMoniteurEncadrant> moniteursEncadrants = gestionnaireExamen.recupererMoniteursEncadrant(examen.getIdExamen());

    // 4. Identifier les noms complets des moniteurs encadrants
    ObservableList<String> nomsCompletsSelectionnes = FXCollections.observableArrayList();
    for (ExamenMoniteurEncadrant encadrant : moniteursEncadrants) {
        nomsCompletsSelectionnes.add(encadrant.getNomMoniteur());
    }

    // 5. Sélectionner les éléments correspondants dans la ListView
    MoniteursEncadrantList.getSelectionModel().clearSelection();
    for (String nom : nomsCompletsSelectionnes) {
        MoniteursEncadrantList.getSelectionModel().select(nom);
    }
    
    vehiculeUtiliseList.getSelectionModel().select(examen.getVehicule());
    
    
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
private void mettreAJourExamen() {
    // Vérifier les champs (par exemple, validation des champs comme dans `validerChamps`)
    if (!validerChamps()) {
        return; // Arrêter si les champs ne sont pas valides
    }

    // Récupérer les données de l'interface utilisateur
    String dateExamen = ExamenDatePicker.getValue().toString();
    String nomExaminateur = insererNomExaminateur.getText();
    String vehicule = vehiculeUtiliseList.getSelectionModel().getSelectedItem();


    ObservableList<String> selection = MoniteursEncadrantList.getSelectionModel().getSelectedItems();
    ObservableList<Integer> idsMoniteurs = FXCollections.observableArrayList();

    // Associer les noms complets aux IDs
    for (String nomComplet : selection) {
        for (Moniteur moniteur : moniteurList) {
            if (moniteur.getNomComplet().equals(nomComplet)) {
                idsMoniteurs.add(moniteur.getId());
                break;
            }
        }
    }

    // Mettre à jour l'examen et ses moniteurs encadrants
    GestionnaireExamen gestionnaireExamen = new GestionnaireExamen();
    boolean examenMisAJour = gestionnaireExamen.mettreAJourExamen(idExamen, dateExamen, nomExaminateur, vehicule);
    boolean moniteursMisAJour = gestionnaireExamen.mettreAJourMoniteursEncadrants(idExamen, idsMoniteurs);

    // Afficher le résultat
    if (examenMisAJour && moniteursMisAJour) {
        afficherAlerte("Succès", "L'examen a été mis à jour avec succès !");
        fermerFenetre(); // Fermer la fenêtre après mise à jour
    } else {
        afficherAlerte("Erreur", "Échec de la mise à jour de l'examen.");
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
        EnregistrerExamenButton.setOnAction(event -> mettreAJourExamen());
        
        chargerVehicule();
    }    
    
}
