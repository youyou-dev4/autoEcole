/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.Condidat;
import Modele.GestionnaireCondidat;
import Modele.GestionnaireCours;
import Modele.GestionnaireMoniteur;
import Modele.Moniteur;
import Modele.Seance;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
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


public class ModifierSeanceController implements Initializable {
    
    
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
            
            @FXML
            private ComboBox<String> epreuve;
            
            @FXML
            private ComboBox<String> type;
            
            @FXML
            private ComboBox<String> moniteur;
            
            @FXML
            private JFXDatePicker date;

            @FXML
            private JFXTimePicker heure;
            
            private int  idMoniteur = -1;
            
            private double montantParDefault = 0.00;
            
            private int idSeance ;
    
    
    
    
    public void initialiserChamps(Seance seance) {
        
        idSeance = seance.getId_seance();
        idMoniteur = seance.getIdMoniteur();

    // Remplir les champs avec les données de la séance
    date.setValue(LocalDate.parse(seance.getDate())); // Assurez-vous que la date est au format ISO
    heure.setValue(LocalTime.parse(seance.getHeure()));
    type.setValue(seance.getType());
    epreuve.setValue(seance.getEpreuve());
    permis.setValue(seance.getPermis());
    moniteur.setValue(seance.getNomMoniteur());
    insererMontant.setText(String.format("%.2f", seance.getMontant())); // Format à deux décimales
    
    
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
            
                                String nomCondidat = seance.getNomCondidat();
                                String[] mots = nomCondidat.split(" "); // Divise la chaîne par les espaces
                                if (mots.length > 0) {
                                    searchField.setText(mots[0]); // Met le premier mot dans le champ de recherche
                                }

                                            condidatsList.stream()
                                        .filter(c -> c.getId() == seance.getIdCondidat())
                                        .findFirst()
                                        .ifPresent(c -> tableCondidat.getSelectionModel().select(c));
    


    // Afficher ou masquer le champ de montant selon le type
    insererMontant.setVisible(!"normal".equals(seance.getType()));
}
    
    
    
   
    
    
    
    
    private void remplirComboBoxMoniteurs() {
                    // Instanciez le gestionnaire de moniteurs
                    GestionnaireMoniteur gestionnaireMoniteur = new GestionnaireMoniteur();
                    ObservableList<Moniteur> moniteurList = gestionnaireMoniteur.recupererMoniteur();

                    // Remplir le ComboBox avec les noms complets
                    for (Moniteur moniteur : moniteurList) {
                        String nomComplet = moniteur.getNom() + " " + moniteur.getPrenom();
                        this.moniteur.getItems().add(nomComplet);
                    }

                    // Gérer la sélection pour obtenir l'ID du moniteur choisi
                    this.moniteur.setOnAction(event -> {
                        String selectedMoniteur = this.moniteur.getValue();
                        for (Moniteur moniteur : moniteurList) {
                            if ((moniteur.getNom() + " " + moniteur.getPrenom()).equals(selectedMoniteur)) {
                                 idMoniteur = moniteur.getId(); // Récupérer l'ID
                                System.out.println("ID du moniteur choisi: " + idMoniteur);
                                // Vous pouvez maintenant utiliser cet ID pour d'autres opérations
                                break;
                            }
                        }
                    });
            }
    
    
    
    
             //methode pour verifier que tout les champ sont remplie 
    private boolean validerChamps() {
                // Vérifier les champs obligatoires
                    Condidat condidatSelectionne = tableCondidat.getSelectionModel().getSelectedItem();
                if (condidatSelectionne == null  || permis.getValue() == null ||
                     type.getValue() == null || moniteur.getValue() == null || date.getValue() == null ||
                     epreuve.getValue() == null || heure.getValue() == null  ) {

                    afficherAlerte("Erreur", "Veuillez remplir tout les champs.");
                    return false;
                }

                if (!insererMontant.getText().isEmpty()){
                    double montant;
            try {
              String montantText = insererMontant.getText().replace(',', '.');
                montant = Double.parseDouble(montantText);
            } catch (NumberFormatException e) {
                afficherAlerte("Erreur","Le montant doit être un nombre valide !");
                return false;
            }
                }

                return true;
        }
    
    
    
    
    //methode pour verifier la disponibilite de moniteur
     private boolean moniteurLibre(int id, String d, String H){
            GestionnaireCours gestionCours = new GestionnaireCours();
            if(!gestionCours.verifierDisponibiliteMoniteur(id, d, H)){
                       afficherAlerte("Erreur","Le moniteur n'est pas libre a ce moment !");
                       return false;
            }
            return true;
     }
        
     
     
     //methode pour verifier le nbr des seance fait par le condidat
     private boolean seancePermise(int id ,  String epreuve, String permis){
             GestionnaireCours gestionCours = new GestionnaireCours();
             if ( !"code".equals(epreuve)){
                 int seancesRealisees = gestionCours.compterSeancesParEpreuve(id,  epreuve, permis);
        int seancesMax = gestionCours.obtenirNbrMaxSeances(epreuve);

        if (seancesRealisees >= seancesMax) {
            afficherAlerte("Limite atteinte", 
                "Vous avez atteint le nombre maximum de séances normales pour cette épreuve. " +
                "Nous vous recommandons de sélectionner une séance supplémentaire.");
            type.setValue("supplementaire");
            return false; // Arrêter l'enregistrement si l'utilisateur doit changer le type
        } 
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
private void enregistrerSeance() {
    if (!validerChamps()) {
        return; // Si la validation échoue, on ne continue pas
    }

    // Récupérer les valeurs des champs
    String dateS = date.getValue().toString();
    String heureS = heure.getValue().format(DateTimeFormatter.ofPattern("HH:mm:ss")); // Format correct
    String typeS = type.getValue();
    String epreuveS = epreuve.getValue();
    String permisS = permis.getValue();
    Condidat condidatSelectionne = tableCondidat.getSelectionModel().getSelectedItem();

    if (!moniteurLibre(idMoniteur, dateS, heureS)) {
        return; // Si la validation échoue, on ne continue pas
    }

    if ("normal".equals(typeS)) {
        if (!seancePermise(condidatSelectionne.getId(), epreuveS, permisS)) {
            return; // Si la validation échoue, on ne continue pas
        }
    }

    double montant = montantParDefault;
    if (!insererMontant.getText().isEmpty()) {
        String montantText = insererMontant.getText().replace(',', '.');
        montant = Double.parseDouble(montantText);
    }

    // Instancier le gestionnaire de cours
    GestionnaireCours gestionCours = new GestionnaireCours();

    // Appeler la méthode pour ajouter la séance
    boolean success = gestionCours.modifierSeance(idSeance, dateS, heureS, typeS, montant, epreuveS, permisS, idMoniteur, condidatSelectionne.getId());

    // Gérer le résultat de l'insertion
    if (success) {
        afficherAlerteEtFermerFenetre("Succès", "La seance a été enregistrée avec succès.", true);
    } else {
        afficherAlerteEtFermerFenetre("Erreur", "Une erreur s'est produite lors de l'enregistrement de la séance.", false);
    }
}
        
        
        @FXML
private JFXButton EnregistrerSeanceButton;
    
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
            Stage stage = (Stage) EnregistrerSeanceButton.getScene().getWindow(); // Récupérer la fenêtre principale
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
        
        remplirComboBoxMoniteurs();
        
        // Remplir la ComboBox "Permis"
          ObservableList<String> seancePermis = FXCollections.observableArrayList(
                                        "A1", 
                                        "A2", 
                                        "B",
                                        "C", 
                                        "E", 
                                        "D"
                                    );
          
          permis.setItems(seancePermis); 
        
          
          ObservableList<String> epreuves = FXCollections.observableArrayList( "code", "crenaux", "circulation");
          epreuve.setItems(epreuves);
          
          ObservableList<String> types = FXCollections.observableArrayList( "normal", "supplementaire", "perfectionnement");
          type.setItems(types);
          
          type.setOnAction(event -> {
        String selectedType = type.getValue();
        if ("normal".equals(selectedType)) {
            insererMontant.setVisible(false); // Masquer le TextField
        } else {
            insererMontant.setVisible(true); // Afficher le TextField
        }
        });
          
          
        EnregistrerSeanceButton.setOnAction(event -> enregistrerSeance());

        
        
        
    }    
    
}
