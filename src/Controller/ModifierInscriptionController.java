
package Controller;

import Modele.Condidat;
import Modele.GestionnaireCondidat;
import Modele.GestionnaireInscription;
import Modele.Inscription;
import Modele.Versement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class ModifierInscriptionController implements Initializable {

    private int id_inscription;
    
    
            @FXML
            private JFXTextField insererMontant;
    
            @FXML
            private TableView<Condidat> tableCondidat;

            @FXML
            private TableColumn<Condidat, Integer> tableCondidatID;

            @FXML
            private TableColumn<Condidat, String> tableCondidatNom;

            @FXML
            private TableColumn<Condidat, String> tableCondidatPrenom;

            @FXML
            private TableColumn<Condidat, String> tableCondidatDateNaiss;

            @FXML
            private TableColumn<Condidat, String> tableCondidatLieuNaiss;
            
            @FXML
            private TextField searchField;
            
            @FXML
            private ComboBox<String> permis;
            
            
            
            //methode pour remplir les champs
    public void initialiserChamps(Inscription inscription) {
        
            id_inscription = inscription.getId_inscription();
            
            permis.setValue(inscription.getTypePermis());
        
            insererMontant.setText(String.format("%.2f", inscription.getMontant()));
            
            // Configuration des colonnes de la table des condidat
                           tableCondidatNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
                           tableCondidatPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                           tableCondidatDateNaiss.setCellValueFactory(new PropertyValueFactory<>("dateNaiss"));
                           tableCondidatLieuNaiss.setCellValueFactory(new PropertyValueFactory<>("lieuNaiss"));
            
            // Récupérer les informations des condidat de la base de données
                            GestionnaireCondidat gestionnaireCondidat = new GestionnaireCondidat();
                           ObservableList<Condidat> condidatsList = gestionnaireCondidat.recupererCondidat();

                           // Ajouter les données au TableView de condidat
                          tableCondidat.setItems(condidatsList);
                          
                          // Créez un FilteredList pour la table des condidat
                            FilteredList<Condidat> filteredData = new FilteredList<>(condidatsList, p -> true);

                       
                        // Appliquez un listener sur le champ de recherche de la table des condidat 
                                searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                                filteredData.setPredicate(condidat -> {
                                // Si le champ de recherche est vide, on affiche tous les candidats
                                if (newValue == null || newValue.isEmpty()) {
                                   return true;
                                }
                                // Convertir le texte de recherche en minuscules pour éviter la casse
                                String lowerCaseFilter = newValue.toLowerCase();

                                // Vérifiez si le nom ou le prénom correspond à la recherche
                                if (condidat.getNom().toLowerCase().contains(lowerCaseFilter)) {
                                    return true; // Le nom contient le texte de recherche
                                } else if (condidat.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                                    return true; // Le prénom contient le texte de recherche
                                } else if (condidat.getDateNaiss().toLowerCase().contains(lowerCaseFilter)) {
                                    return true; // La date de naissance contient le texte de recherche
                                }
                                return false; // Aucun critère trouvé
                                });
                                });

                                // Appliquez le filtre à la TableView
                                SortedList<Condidat> sortedData = new SortedList<>(filteredData);
                                sortedData.comparatorProperty().bind(tableCondidat.comparatorProperty());
                                tableCondidat.setItems(sortedData);
            
                                String nomCondidat = inscription.getNomCondidat();
String[] mots = nomCondidat.split(" "); // Divise la chaîne par les espaces
if (mots.length > 0) {
    searchField.setText(mots[0]); // Met le premier mot dans le champ de recherche
}
                                
            condidatsList.stream()
        .filter(c -> c.getId() == inscription.getId_condidat())
        .findFirst()
        .ifPresent(c -> tableCondidat.getSelectionModel().select(c));
       
    
    }
    
    
    //methode pour verifier que tout les champ sont remplie 
    
    private boolean validerChamps() {
        // Vérifier les champs obligatoires
            Condidat condidatSelectionne = tableCondidat.getSelectionModel().getSelectedItem();
        if (condidatSelectionne == null || insererMontant.getText().isEmpty() || permis.getValue() == null ) {

            afficherAlerte("Erreur", "Veuillez choisir un condidat et un permis et saisir un montant.");
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
    private void enregistrerInscription() {
    // Vérifier si les champs sont valides
    if (!validerChamps()) {
        return; // Si la validation échoue, on ne continue pas
    }

    // Récupérer les informations saisies par l'utilisateur
        Condidat condidatSelectionne = tableCondidat.getSelectionModel().getSelectedItem();
        String montantText = insererMontant.getText().replace(',', '.');
        double     montant = Double.parseDouble(montantText);
        String permisSelectionne = permis.getValue();
    

   // Ajouter le versement à la base de données
    GestionnaireInscription gestionnaireVersement = new GestionnaireInscription();
    boolean success = gestionnaireVersement.modifierInscription(id_inscription ,condidatSelectionne.getId(), montant,permisSelectionne);

    // Vérifier si l'enregistrement a réussi
    if (success) {
        afficherAlerteEtFermerFenetre("Succès", "L'inscription a été modifier avec succès.", true);
    } else {
        afficherAlerteEtFermerFenetre("Erreur", "Une erreur s'est produite lors de la modification d'inscription.", false);
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
        
        // Listener pour formater le montant avec deux décimales
                                insererMontant.setOnMouseClicked(event -> {
                                if (insererMontant.getText().isEmpty()) {
                                    insererMontant.setText("0.00");
                                }
                            });
                                
                                // Éviter que le champ de montant soit sélectionné au démarrage
                            Platform.runLater(() -> {
                                searchField.requestFocus(); // Donne le focus au champ de recherche ou à un autre élément
                            });

        // Remplir la ComboBox "SeancePermis"
          ObservableList<String> seancePermis = FXCollections.observableArrayList(
                                        "A1", 
                                        "A2", 
                                        "B",
                                        "C", 
                                        "E", 
                                        "D"
                                    );
          
          permis.setItems(seancePermis); 
          
          EnregistrerVersementButton.setOnAction(event -> enregistrerInscription());

        
    }    
    
}
