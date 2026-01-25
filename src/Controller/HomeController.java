/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.Condidat;
import Modele.CoursInfo;
import Modele.Depence;
import Modele.Examen;
import Modele.ExamenCondidatExamine;
import Modele.ExamenCondidatInfo;
import Modele.ExamenMoniteurEncadrant;
import Modele.ExamenMoniteurInfo;
import Modele.GestionnaireCondidat;
import Modele.GestionnaireCours;
import Modele.GestionnaireExamen;
import Modele.GestionnaireInscription;
import Modele.GestionnaireMoniteur;
import Modele.GestionnaireQuantabilite;
import Modele.GestionnaireUtilisateur;
import Modele.GestionnaireValeurParDefaut;
import Modele.GestionnaireVehicule;
import Modele.Inscription;
import Modele.InscriptionCondidatInfo;
import Modele.Moniteur;
import Modele.MontantCondidat;
import Modele.Seance;
import Modele.SeanceCondidatInfo;
import Modele.UtilisateurInfo;
import Modele.Vehicule;
import Modele.Versement;
import Modele.VersementCondidatInfo;
import Modele.ValeurParDefaut;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;

/**
 * FXML Controller class
 *
 * @author htw
 */
public class HomeController implements Initializable {
    
    //Regler le massquage et l'affichage des different section ( acceuille , condidat...etc )
    
                @FXML
                private HBox acceuilSectionButton;

                @FXML
                private HBox condidatSectionButton;
                
                @FXML
                private HBox moniteurSectionButton;
                
                @FXML
                private HBox utilisateurSectionButton;
                
                @FXML
                private HBox vehiculeSectionButton;
                
                @FXML
                private HBox quantabiliteSectionButton;
                
                @FXML
                private HBox InscriptionSectionButton;
                
                @FXML
                private HBox seanceSectionButton;
                
                @FXML
                private HBox examenSectionButton;
                 
                @FXML
                private HBox parametreSectionButton;

                @FXML
                private HBox homeContenu;
     
                @FXML
                private VBox condidatContenu;
                
                @FXML
                private VBox moniteurContenu;
                
                @FXML
                private HBox userContenu;
                
                @FXML
                private HBox inscriptionContenu;
                
                @FXML
                private HBox seanceContenu;
                
                @FXML
                private VBox vehiculeContenu;
                
                @FXML
                private VBox quantabiliteContenu;
                
                @FXML
                private VBox parametreContenu;
                
                 @FXML
                private VBox examenContenu;
                 
                 
                
    // Fonction unique pour gérer les clics sur les button de menu, avec changement de scale et de background
                    @FXML
                    private void handleSectionClick(Event event) {
                        // Masquer toutes les sections
                        homeContenu.setVisible(false);
                        condidatContenu.setVisible(false);
                        moniteurContenu.setVisible(false);
                        userContenu.setVisible(false);
                        vehiculeContenu.setVisible(false);
                        quantabiliteContenu.setVisible(false);
                        inscriptionContenu.setVisible(false);
                        seanceContenu.setVisible(false);
                        examenContenu.setVisible(false);
                        parametreContenu.setVisible(false);

                        // Réinitialiser l'échelle et le fond de tous les boutons (optionnel)
                        resetButtonStyle();

                        // Vérifier quelle source a déclenché l'événement et afficher la section correspondante
                        if (event.getSource() == acceuilSectionButton) {
                            homeContenu.setVisible(true);
                            
                            //Reremplir la berchart
                                loadBarChar();
                            
                            // Récupérer les Cour theorique de jour de la base de données
                           ObservableList<CoursInfo> coursList = gestionnaireCours.recupererCoursCodeAujourdhui();

                           // Ajouter les données au TableView d'accueil des cours theorique d'aujourd'hui
                           home_table1.setItems(coursList);
                            
                            
                            // Appliquer un changement de scale et de fond pour le bouton Accueil
                            acceuilSectionButton.setScaleX(1.1); // Augmenter l'échelle
                            acceuilSectionButton.setScaleY(1.1);
                            acceuilSectionButton.setStyle("-fx-background-color: #000075;"); // Exemple de changement de fond

                            
                        } else if (event.getSource() == condidatSectionButton) {
                            condidatContenu.setVisible(true);
                            initialiserSectionCondidat();
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            condidatSectionButton.setScaleX(1.1);
                            condidatSectionButton.setScaleY(1.1);
                            condidatSectionButton.setStyle("-fx-background-color: #000075;"); // Exemple de changement de fond
                            
                            
                        } else if (event.getSource() == moniteurSectionButton) {
                            moniteurContenu.setVisible(true);
                            initialiserSectionMoniteur();
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            moniteurSectionButton.setScaleX(1.1);
                            moniteurSectionButton.setScaleY(1.1);
                            moniteurSectionButton.setStyle("-fx-background-color: #000075;"); // Exemple de changement de fond
                            
                        }  else if (event.getSource() == utilisateurSectionButton) {
                            userContenu.setVisible(true);
                            initialiserSectionUtilisateur();
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            utilisateurSectionButton.setScaleX(1.1);
                            utilisateurSectionButton.setScaleY(1.1);
                            utilisateurSectionButton.setStyle("-fx-background-color: #000075;"); // Exemple de changement de fond
                        }  else if (event.getSource() == vehiculeSectionButton) {
                            vehiculeContenu.setVisible(true);
                            initialiserSectionVehicule();
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            vehiculeSectionButton.setScaleX(1.1);
                            vehiculeSectionButton.setScaleY(1.1);
                            vehiculeSectionButton.setStyle("-fx-background-color: #000075;"); // Exemple de changement de fond
                        }  else if (event.getSource() == quantabiliteSectionButton) {
                            quantabiliteContenu.setVisible(true);
                            initialiserSectionQuantabilite();
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            quantabiliteSectionButton.setScaleX(1.1);
                            quantabiliteSectionButton.setScaleY(1.1);
                            quantabiliteSectionButton.setStyle("-fx-background-color: #000075;"); // Exemple de changement de fond
                        }  else if (event.getSource() == InscriptionSectionButton) {
                            inscriptionContenu.setVisible(true);
                            initialiserSectionInscription();
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            InscriptionSectionButton.setScaleX(1.1);
                            InscriptionSectionButton.setScaleY(1.1);
                            InscriptionSectionButton.setStyle("-fx-background-color: #000075;"); // Exemple de changement de fond
                        }  else if (event.getSource() == seanceSectionButton) {
                            seanceContenu.setVisible(true);
                            initialiserSectionSeance();
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            seanceSectionButton.setScaleX(1.1);
                            seanceSectionButton.setScaleY(1.1);
                            seanceSectionButton.setStyle("-fx-background-color: #000075;"); // Exemple de changement de fond
                        }   else if (event.getSource() == examenSectionButton) {
                            examenContenu.setVisible(true);
                            initialiserSectionExamen();
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            examenSectionButton.setScaleX(1.1);
                            examenSectionButton.setScaleY(1.1);
                            examenSectionButton.setStyle("-fx-background-color: #000075;"); // Exemple de changement de fond
                        }  else if (event.getSource() == parametreSectionButton) {
                            parametreContenu.setVisible(true);
                                initialiserSectionParametre();
                                // Appliquer un changement de scale et de fond pour le bouton Condidat
                            parametreSectionButton.setScaleX(1.1);
                            parametreSectionButton.setScaleY(1.1);
                            parametreSectionButton.setStyle("-fx-background-color: #000075;"); // Exemple de changement de fond
                        }  
                        
                    }

                    // Réinitialiser les styles des boutons pour revenir à la taille et fond d'origine
                    private void resetButtonStyle() {
                        acceuilSectionButton.setScaleX(1.0);
                        acceuilSectionButton.setScaleY(1.0);
                        acceuilSectionButton.setStyle("-fx-background-color: transparent;");

                        condidatSectionButton.setScaleX(1.0);
                        condidatSectionButton.setScaleY(1.0);
                        condidatSectionButton.setStyle("-fx-background-color: transparent;");
                        
                        moniteurSectionButton.setScaleX(1.0);
                        moniteurSectionButton.setScaleY(1.0);
                        moniteurSectionButton.setStyle("-fx-background-color: transparent;");
                        
                        utilisateurSectionButton.setScaleX(1.0);
                        utilisateurSectionButton.setScaleY(1.0);
                        utilisateurSectionButton.setStyle("-fx-background-color: transparent;");
                        
                        vehiculeSectionButton.setScaleX(1.0);
                        vehiculeSectionButton.setScaleY(1.0);
                        vehiculeSectionButton.setStyle("-fx-background-color: transparent;");
                        
                        quantabiliteSectionButton.setScaleX(1.0);
                        quantabiliteSectionButton.setScaleY(1.0);
                        quantabiliteSectionButton.setStyle("-fx-background-color: transparent;");
                        
                        InscriptionSectionButton.setScaleX(1.0);
                        InscriptionSectionButton.setScaleY(1.0);
                        InscriptionSectionButton.setStyle("-fx-background-color: transparent;");
                        
                        seanceSectionButton.setScaleX(1.0);
                        seanceSectionButton.setScaleY(1.0);
                        seanceSectionButton.setStyle("-fx-background-color: transparent;");
                        
                        examenSectionButton.setScaleX(1.0);
                        examenSectionButton.setScaleY(1.0);
                        examenSectionButton.setStyle("-fx-background-color: transparent;");
                        
                        parametreSectionButton.setScaleX(1.0);
                        parametreSectionButton.setScaleY(1.0);
                        parametreSectionButton.setStyle("-fx-background-color: transparent;");

                    }
                    
                    
                    
                    
                    
                    
                    
                    
                    //methode pour afficher des alerte


                                 private void afficherAlerte(String titre, String message) {
                                     Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                                     alerte.setTitle(titre);
                                     alerte.setHeaderText(null);
                                     alerte.setContentText(message);
                                     alerte.showAndWait();
                                 }
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
           
                    
                    
    
                
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                                             //initialisation des different interface 
                    
                    
                    
                                                  //Regler la section Condidat

    //code pour regler la table des condidat 
    
            private GestionnaireCondidat gestionnaireCondidat;

            @FXML
            private TableView<Condidat> tableCondidat;

            @FXML
            private TableColumn<Condidat, String> tableCondidatNom;

            @FXML
            private TableColumn<Condidat, String> tableCondidatPrenom;

            @FXML
            private TableColumn<Condidat, String> tableCondidatDateNaiss;

            @FXML
            private TableColumn<Condidat, String> tableCondidatLieuNaiss;
    
    
    
    // la bare de recherche de la table des condidat 
            @FXML
            private TextField searchField;

    
    //les composant de la section des information personneles des candidats
            @FXML
            private JFXTextField afficherNomCondidat;

            @FXML
            private JFXTextField afficherPrenomCondidat;

            @FXML
            private JFXTextField afficherDateNaissanceCondidat;

            @FXML
            private JFXTextField afficherLieuNaissanceCondidat;
            
            @FXML
            private JFXTextField afficherNumCondidat;

            @FXML
            private JFXTextField afficherTelephoneCondidat;

            @FXML
            private JFXTextArea aficherAdresseCondidat;

            @FXML
            private ImageView afficherPhotoCondidat;

            @FXML
            private JFXTextField afficherSainguinCondidat;

            @FXML
            private JFXTextField afficherDateInscriptionCondidat;
            
                    
            //methode pour initialiser l'inteface Condidat
                    @FXML
                    private void initialiserSectionCondidat(){
                        
                        // Désélectionner toute ligne sélectionnée à l'initialisation
                            tableCondidat.getSelectionModel().clearSelection();
                            
                        //desactiver les button 
                            CondidatInfoSectionButton.setDisable(true);
                            CondidatSeanceSectionButton.setDisable(true);
                            CondidatExamenSectionButton.setDisable(true);
                            CondidatVersementSectionButton.setDisable(true);
                            
                        //recuperer le background original des button
                            CondidatInfoSectionButton.setStyle("-fx-background-color:   #929081;"); 
                            CondidatSeanceSectionButton.setStyle("-fx-background-color:   #929081;"); 
                            CondidatExamenSectionButton.setStyle("-fx-background-color:   #929081;"); 
                            CondidatVersementSectionButton.setStyle("-fx-background-color:   #929081;"); 

                        
                        //faire apparaitre la section par defaut des condidat ( cacher info perso....)
                        CondidatInfoSection.setVisible(false);
                        CondidatSeanceSection.setVisible(false);
                        CondidatExamenSection.setVisible(false);
                        CondidatVersementSection.setVisible(false);
                        CondidatSectionParDefaut.setVisible(true);
                        
                        // Configuration des colonnes de la table des condidat
                           tableCondidatNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
                           tableCondidatPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                           tableCondidatDateNaiss.setCellValueFactory(new PropertyValueFactory<>("dateNaiss"));
                           tableCondidatLieuNaiss.setCellValueFactory(new PropertyValueFactory<>("lieuNaiss"));
                        
                        // Récupérer les informations des condidat de la base de données
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
                                
                                
                           // Ajouter un listener pour détecter la sélection pour affiche les information de condidat selectionner
                                tableCondidat.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Condidat> observable, Condidat oldValue, Condidat newValue) -> {
                                    if (newValue != null) {
                                        
                                //afficher la section info personelle si 
                                if ( CondidatSectionParDefaut.isVisible()){
                                    CondidatSectionParDefaut.setVisible(false);
                                     CondidatInfoSection.setVisible(true);
                                }
                                                                                
                                //disable pour les button si aucun condidat est selectionner
                                        CondidatInfoSectionButton.setDisable(newValue == null);
                                        CondidatSeanceSectionButton.setDisable(newValue == null);
                                        CondidatExamenSectionButton.setDisable(newValue == null);
                                        CondidatVersementSectionButton.setDisable(newValue == null);
                                        
                                //afficher la section des information personnelles
                                        afficherDetailsCondidat(newValue);
                                        CondidatInfoSectionButton.setStyle("-fx-background-color:   linear-gradient(to top, #c2c3a2, #403B4A);"); // Exemple de changement de fond

                                        
                                //afficher la section des seance
                                        // Récupérer l'ID du candidat sélectionné
                                            int candidatId = newValue.getId(); 

                                            // Récupérer les séances associées à ce candidat
                                             seancesList = gestionnaireCondidat.recupererSeancesPourCondidat(candidatId);

                                            // Mettre à jour la TableView avec les séances récupérées
                                            condidatSeanceTable.setItems(seancesList);
                                            
                                            //initialiser les combobox 
                                            condidatSeanceType.setValue(null);
                                            condidatSeanceEpreuve.setValue(null);
                                            condidatSeancePermis.setValue(null);
                                            
                                            // Mettre à jour les champs de texte avec les données du candidat
                                            updateSeanceFields(seancesList);
                                            
                                            
                                //afficher la section des examen
                                            // Récupérer les examens associés à ce candidat
                                            ObservableList<ExamenCondidatInfo> examensList = gestionnaireCondidat.recupererExamensPourCondidat(candidatId);

                                            // Mettre à jour la TableView avec les examens récupérés
                                            condidatExamenTable.setItems(examensList);
                                            
                                //afficher la section des versement
                                             // Récupérer les versement associés à ce candidat
                                            ObservableList<VersementCondidatInfo> versementList = gestionnaireCondidat.recupererVersementPourCondidat(candidatId);

                                            // Mettre à jour la TableView avec les versements récupérés
                                            condidatVersementTable.setItems(versementList);
                                            
                                            //remplir les champ de credit de condidat
                                            
                                            // Appeler la méthode de calcul dans GestionnaireCondidat
                                            MontantCondidat montants = gestionnaireCondidat.calculerMontants(candidatId);

                                            // Mettre à jour les champs TextField
                                            CondidatTatolAPayer.setText(String.format("%.2f", montants.getTotalAPayer()));
                                            CondidatTatolPayé.setText(String.format("%.2f", montants.getTotalPaye()));
                                            CondidatResteAPayer.setText(String.format("%.2f", montants.getResteAPayer()));
                                            
                                //afficher la section des inscriptions            
                                             // Récupérer les inscription associés à ce candidat
                                            ObservableList<InscriptionCondidatInfo> inscriptionList = gestionnaireCondidat.recupererInscriptionPourCondidat(candidatId);

                                            // Mettre à jour la TableView avec les inscription récupérés
                                            condidatInscriptionTable.setItems(inscriptionList);
                                            
                                
                                
                                    }
                                });
                                
                                
                                // Remplir la ComboBox "SeanceType"
                                    ObservableList<String> seanceTypes = FXCollections.observableArrayList(
                                        "Tous",
                                        "normal",
                                        "supplementaire", 
                                        "perfectionnement"
                                    );
                                    condidatSeanceType.setItems(seanceTypes);

                                    // Remplir la ComboBox "SeanceEpreuve"
                                    ObservableList<String> seanceEpreuves = FXCollections.observableArrayList(
                                        "Tous",
                                        "code", 
                                        "crenaux", 
                                        "circulation"
                                    );
                                    condidatSeanceEpreuve.setItems(seanceEpreuves);

                                    // Remplir la ComboBox "SeancePermis"
                                    ObservableList<String> seancePermis = FXCollections.observableArrayList(
                                        "Tous",
                                        "A1", 
                                        "A2", 
                                        "B",
                                        "C1",
                                        "C2",
                                        "E", 
                                        "D"
                                    );
                                    condidatSeancePermis.setItems(seancePermis);

                                    
                                    // Initialiser les colonnes de la table des séances d'un condidat
                                            condidatSeanceTableColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                                            condidatSeanceTableColHeure.setCellValueFactory(new PropertyValueFactory<>("heure"));
                                            condidatSeanceTableColEpreuve.setCellValueFactory(new PropertyValueFactory<>("epreuve"));
                                            condidatSeanceTableColType.setCellValueFactory(new PropertyValueFactory<>("type"));
                                            condidatSeanceTableColPermis.setCellValueFactory(new PropertyValueFactory<>("permis"));
                                    
                                    
                                    
                                    
                                    // Ajouter des listeners aux ComboBox pour filtrer les données
                                    condidatSeanceType.valueProperty().addListener((observable, oldValue, newValue) -> applyFilter());
                                    condidatSeanceEpreuve.valueProperty().addListener((observable, oldValue, newValue) -> applyFilter());
                                    condidatSeancePermis.valueProperty().addListener((observable, oldValue, newValue) -> applyFilter());
                                    
                                    
                                    
                                    // Configuration des colonnes de la table des examens de condidat
                                    condidatExamenTableColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                                    condidatExamenTableColEpreuve.setCellValueFactory(new PropertyValueFactory<>("epreuve"));
                                    condidatExamenTableColResultat.setCellValueFactory(new PropertyValueFactory<>("resultat"));
                                    
                                    
                                    
                                    
                                    
                                    // Configuration des colonnes de la table des versement de condidat
                                    condidatVersementTableColDate.setCellValueFactory(new PropertyValueFactory<>("date_versement"));
                                    condidatVersementTableColMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
                                                                      
                                    
                                    // Configuration des colonnes de la table des inscription de condidat
                                    condidatInscriptionTableColDate.setCellValueFactory(new PropertyValueFactory<>("date_inscription"));
                                    condidatInscriptionTableColPermis.setCellValueFactory(new PropertyValueFactory<>("nom_permis"));
                                    condidatInscriptionTableColMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
                                             
                    }

    
                    
    //Regler le masquage et l'affichage des section de l'interface candidat ( info perso, seances...)
                     

                    @FXML
                    private HBox CondidatInfoSection;
                    
                    @FXML
                    private JFXButton CondidatInfoSectionButton;
                    
                    @FXML
                    private VBox CondidatSeanceSection;
                    
                    @FXML
                    private JFXButton CondidatSeanceSectionButton;
                    
                    @FXML
                    private VBox CondidatExamenSection;
                    
                    @FXML
                    private JFXButton CondidatExamenSectionButton;
                    
                    @FXML
                    private VBox CondidatVersementSection;
                    
                    @FXML
                    private JFXButton CondidatVersementSectionButton;
                    
                    @FXML
                    private VBox CondidatSectionParDefaut;
                    
                    
        // Fonction unique pour gérer les clics de l'interface condidat, avec changement de scale et de background
                    
                    @FXML
                    private void handleSectionCondidatClick(Event event) {
                        // Masquer toutes les sections
                        CondidatInfoSection.setVisible(false);
                        CondidatSeanceSection.setVisible(false);
                        CondidatExamenSection.setVisible(false);
                        CondidatVersementSection.setVisible(false);
                        CondidatSectionParDefaut.setVisible(false);

                        // Réinitialiser l'échelle et le fond de tous les boutons (optionnel)
                        resetButtonCondidatStyle();

                        // Vérifier quelle source a déclenché l'événement et afficher la section correspondante
                        if (event.getSource() == CondidatInfoSectionButton) {
                            CondidatInfoSection.setVisible(true);
                            // Appliquer un changement de scale et de fond pour le bouton Accueil
                            CondidatInfoSectionButton.setStyle("-fx-background-color:   linear-gradient(to top, #c2c3a2, #403B4A);"); // Exemple de changement de fond
                            
                        } else if (event.getSource() == CondidatSeanceSectionButton) {
                            CondidatSeanceSection.setVisible(true);
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            CondidatSeanceSectionButton.setStyle("-fx-background-color:   linear-gradient(to top, #c2c3a2, #403B4A);"); // Exemple de changement de fond
                           
                        } else if (event.getSource() == CondidatExamenSectionButton) {
                            CondidatExamenSection.setVisible(true);
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            CondidatExamenSectionButton.setStyle("-fx-background-color:   linear-gradient(to top, #c2c3a2, #403B4A);"); // Exemple de changement de fond
                           
                        } else if (event.getSource() == CondidatVersementSectionButton) {
                            CondidatVersementSection.setVisible(true);
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            CondidatVersementSectionButton.setStyle("-fx-background-color:   linear-gradient(to top, #c2c3a2, #403B4A);"); // Exemple de changement de fond
                           
                        } 
                    }
                    
                    
                    
        // Réinitialiser les styles des boutons pour revenir à la taille et fond d'origine
                    private void resetButtonCondidatStyle() {
                        
                        CondidatInfoSectionButton.setStyle("-fx-background-color: #929081;");

                        CondidatSeanceSectionButton.setStyle("-fx-background-color: #929081;");
                        
                        CondidatExamenSectionButton.setStyle("-fx-background-color: #929081;");
                        
                        CondidatVersementSectionButton.setStyle("-fx-background-color: #929081;");
                    }
                    
                    
                    
                    
                     //regler le cliquage sur les button ajouter condidat 
                    @FXML
                    private JFXButton AfficherAjouterCondidatButton;

                    @FXML
                    private void afficherAjouterCondidat() {
                     try {
                    // Charger le fichier FXML de la fenêtre d'ajout de candidat
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ajouterCondidat.fxml"));
                    Parent root = loader.load();

                    // Créer un nouveau Stage pour la fenêtre modale
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL); // Définit la modalité
                    stage.initStyle(StageStyle.UNDECORATED); // Facultatif : pour supprimer la barre de titre
                    stage.setTitle("Ajouter un candidat");

                    // Ajouter le contenu chargé au Stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    // Afficher la fenêtre au centre de la fenêtre principale
                    stage.centerOnScreen();

                    // Afficher la fenêtre modale
                    stage.showAndWait(); // Bloque jusqu'à ce que la fenêtre soit fermée

                     initialiserSectionCondidat();
                    
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    }
                    
                    
                    
                    
                    
                    
        //regler le clic sur le button modifier dans condidat
                    
                    @FXML
                    private JFXButton AfficherModifierCondidat;

                    @FXML
                    private void afficherModifierCondidat() {
                        Condidat selectedCondidat = tableCondidat.getSelectionModel().getSelectedItem();

                        if (selectedCondidat == null) {
                            // Afficher une alerte si aucune ligne n'est sélectionnée
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Avertissement");
                            alert.setHeaderText(null);
                            alert.setContentText("Veuillez sélectionner un candidat pour modifier ses informations.");
                            alert.showAndWait();
                            return;
                        }

                        try {
                            // Charger le fichier FXML de la fenêtre de modification
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modifierCondidat.fxml"));
                            Parent root = loader.load();

                            // Obtenir le contrôleur de la fenêtre de modification
                            ModifierCondidatController controller = loader.getController();

                            // Passer les informations du candidat sélectionné au contrôleur
                            controller.initialiserChamps(selectedCondidat);

                            // Créer un nouveau Stage pour la fenêtre modale
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL); // Fenêtre modale
                            stage.initStyle(StageStyle.UNDECORATED); // Optionnel : retirer la barre de titre
                            stage.setTitle("Modifier un candidat");

                            // Ajouter le contenu chargé au Stage
                            Scene scene = new Scene(root);
                            stage.setScene(scene);

                            // Afficher la fenêtre au centre de l'écran
                            stage.centerOnScreen();
                            stage.showAndWait(); // Bloquer jusqu'à la fermeture

                           initialiserSectionCondidat();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    
        //configurer le clic sur le button supprimer condidat
                    
                    
                    @FXML
                    private JFXButton SupprimerCondidatButton;

                    @FXML
                    private void supprimerCondidat() {
                        // Vérifier si un candidat est sélectionné
                        Condidat candidatSelectionne = tableCondidat.getSelectionModel().getSelectedItem();
                        if (candidatSelectionne == null) {
                            afficherAlerte("Erreur", "Veuillez sélectionner un candidat à supprimer.");
                            return;
                        }
                        
                        // Demander confirmation à l'utilisateur
                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Confirmation de suppression");
                        confirmation.setHeaderText("Voulez-vous vraiment supprimer ce candidat ?");
                        confirmation.setContentText("Nom : " + candidatSelectionne.getNom() + "\nPrénom : " + candidatSelectionne.getPrenom());

                        Optional<ButtonType> result = confirmation.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            GestionnaireCondidat gestionnaire = new GestionnaireCondidat();
                            try {
                                boolean success = gestionnaire.supprimerCondidat(candidatSelectionne.getId());
                                if (success) {
                                    afficherAlerte("Succès", "Le candidat a été supprimé avec succès.");
                                    // Rafraîchir la table après suppression
                                    initialiserSectionCondidat();
                                }
                            } catch (IllegalStateException e) {
                                afficherAlerte("Erreur", "Impossible de supprimer ce candidat car il est lié à d'autres données.");
                            }
                        }
                    }

                    
                    
                
     
            
    //methode pour afficher les informations d'un condidat

            private void afficherDetailsCondidat(Condidat condidat) {
            afficherNomCondidat.setText(condidat.getNom());
            afficherPrenomCondidat.setText(condidat.getPrenom());
            afficherDateNaissanceCondidat.setText(condidat.getDateNaiss());
            afficherLieuNaissanceCondidat.setText(condidat.getLieuNaiss());
            afficherTelephoneCondidat.setText(condidat.getTlphn());
            aficherAdresseCondidat.setText(condidat.getAdr());
            afficherSainguinCondidat.setText(condidat.getGroupe_sanguin());
            afficherDateInscriptionCondidat.setText(condidat.getDateInscription());
            afficherNumCondidat.setText(condidat.getNumCondidat());


            // Si une photo est disponible, la charger
            // Remplacez "path/to/image" par le chemin réel
            String photoPathD = "photo_condidat/photo_par_defaut.jpg";
            String photoPath = condidat.getPhoto();
            Image image;
            if ( photoPath == null )
            image = new Image(new File(photoPathD).toURI().toString());
            else 
            image = new Image(new File(photoPath).toURI().toString());
            afficherPhotoCondidat.setImage(image);
            }
    
            
    //Regler la partie seance de l'interface condidat
            
            //recuperer les comboBox
            @FXML
            private ComboBox<String> condidatSeanceType;

            @FXML
            private ComboBox<String> condidatSeanceEpreuve;

            @FXML
            private ComboBox<String> condidatSeancePermis;
            
            //recuperer la table des seance   

            @FXML
            private TableView<SeanceCondidatInfo> condidatSeanceTable;

            @FXML
            private TableColumn<SeanceCondidatInfo, String> condidatSeanceTableColDate;

            @FXML
            private TableColumn<SeanceCondidatInfo, String> condidatSeanceTableColHeure;

            @FXML
            private TableColumn<SeanceCondidatInfo, String> condidatSeanceTableColEpreuve;

            @FXML
            private TableColumn<SeanceCondidatInfo, String> condidatSeanceTableColType;

            @FXML
            private TableColumn<SeanceCondidatInfo, String> condidatSeanceTableColPermis;


            
    // Méthode pour appliquer le filtre sur la table des seance d'un condidat
            ObservableList<SeanceCondidatInfo> seancesList;
            
            private void applyFilter() {
                String selectedType = condidatSeanceType.getValue();
                String selectedEpreuve = condidatSeanceEpreuve.getValue();
                String selectedPermis = condidatSeancePermis.getValue();

                FilteredList<SeanceCondidatInfo> filteredData = new FilteredList<>(seancesList, seance -> {
                    boolean matchesType = selectedType == null || selectedType.equals("Tous") || seance.getType().equals(selectedType);
                    boolean matchesEpreuve = selectedEpreuve == null || selectedEpreuve.equals("Tous") || seance.getEpreuve().equals(selectedEpreuve);
                    boolean matchesPermis = selectedPermis == null || selectedPermis.equals("Tous") || seance.getPermis().equals(selectedPermis);

                    return matchesType && matchesEpreuve && matchesPermis;
                });

                // Appliquer le filtre à la TableView
                SortedList<SeanceCondidatInfo> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(condidatSeanceTable.comparatorProperty());
                condidatSeanceTable.setItems(sortedData);
            }
            
            
            //remplir les champ des nombre de seance
                @FXML
                private JFXTextField nbrSeanceSupplementaire;

                @FXML
                private JFXTextField nbrSeancePerfectionnement;

                @FXML
                private JFXTextField nbrSeanceNormale;

                @FXML
                private JFXTextField nbrTotaleSeance;


    // Méthode pour calculer et mettre à jour les champs de nbr de seance
                private void updateSeanceFields(ObservableList<SeanceCondidatInfo> seancesList) {
                int nbSeanceSupplementaire = 0;
                int nbSeancePerfectionnement = 0;
                int nbSeanceNormale = 0;

                // Calculer le nombre de séances par type
                for (SeanceCondidatInfo seance : seancesList) {
                    if ("supplementaire".equals(seance.getType())) {
                        nbSeanceSupplementaire++;
                    } else if ("perfectionnement".equals(seance.getType())) {
                        nbSeancePerfectionnement++;
                    } else if ("normal".equals(seance.getType())) {
                        nbSeanceNormale++;
                    }
                }

                // Mettre à jour les champs JFXTextField avec les valeurs calculées
                nbrSeanceSupplementaire.setText(String.valueOf(nbSeanceSupplementaire));
                nbrSeancePerfectionnement.setText(String.valueOf(nbSeancePerfectionnement));
                nbrSeanceNormale.setText(String.valueOf(nbSeanceNormale));

                // Calculer le nombre total de séances et mettre à jour le champ total
                int totalSeances = nbSeanceSupplementaire + nbSeancePerfectionnement + nbSeanceNormale;
                nbrTotaleSeance.setText(String.valueOf(totalSeances));
                }
        
        
        //le section examen de l'interface condidat 

                @FXML
                private TableView<ExamenCondidatInfo> condidatExamenTable;

                @FXML
                private TableColumn<ExamenCondidatInfo, String> condidatExamenTableColDate;

                @FXML
                private TableColumn<ExamenCondidatInfo,  String> condidatExamenTableColEpreuve;

                @FXML
                private TableColumn<ExamenCondidatInfo,  String> condidatExamenTableColResultat;

        
    //le section versement et inscription de l'interface condidat 
  
                //la table versement

                @FXML
                private TableView<VersementCondidatInfo> condidatVersementTable;

                @FXML
                private TableColumn<VersementCondidatInfo, String> condidatVersementTableColDate;

                @FXML
                private TableColumn<VersementCondidatInfo, Double> condidatVersementTableColMontant;
        

        //la table inscription
        
                @FXML
               private TableView<InscriptionCondidatInfo> condidatInscriptionTable;

               @FXML
               private TableColumn<InscriptionCondidatInfo, String> condidatInscriptionTableColDate;

               @FXML
               private TableColumn<InscriptionCondidatInfo, String> condidatInscriptionTableColPermis;

               @FXML
               private TableColumn<InscriptionCondidatInfo, Double> condidatInscriptionTableColMontant;

        
        //les champ de credit de condidat
 
                @FXML
                private JFXTextField CondidatTatolAPayer;

                @FXML
                private JFXTextField CondidatTatolPayé;

                @FXML
                private JFXTextField CondidatResteAPayer;

     
     
                
   
                
                
               

                
                
                
                
                
                
                
             
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                                                        //Regler la section Moniteur

    //code pour regler la table des moniteur
                
                private GestionnaireMoniteur gestionnaireMoniteur;

            @FXML
            private TableView<Moniteur> tableMoniteur;

            @FXML
            private TableColumn<Moniteur, String> tableMoniteurNom;

            @FXML
            private TableColumn<Moniteur, String> tableMoniteurPrenom;

            @FXML
            private TableColumn<Moniteur, String> tableMoniteurDateNaiss;

            @FXML
            private TableColumn<Moniteur, String> tableMoniteurLieuNaiss;
                
                
            //Regler la bare de recherche de la table des moniteur 
            @FXML
            private TextField searchMoniteurField;

    
    //Regler la section des information personneles des moniteur
            @FXML
            private JFXTextField afficherNomMoniteur;

            @FXML
            private JFXTextField afficherPrenomMoniteur;

            @FXML
            private JFXTextField afficherDateNaissanceMoniteur;

            @FXML
            private JFXTextField afficherLieuNaissanceMoniteur;

            @FXML
            private JFXTextField afficherTelephoneMoniteur;

            @FXML
            private JFXTextArea aficherAdresseMoniteur;

            @FXML
            private JFXTextField afficherSainguinMoniteur;

            @FXML
            private JFXTextField afficherDateInscriptionMoniteur;  
            
            
            //le section examen de l'interface moniteur 

                @FXML
                private TableView<ExamenMoniteurInfo> moniteurExamenTable;

                @FXML
                private TableColumn<ExamenMoniteurInfo, String> MoniteurExamenTableColDate;

                @FXML
                private TableColumn<ExamenMoniteurInfo,  Integer> MoniteurExamenTableColNbrCondidat;

                
            //methode pour initialiser l'inteface Moniteur
                    @FXML
                    private void initialiserSectionMoniteur(){
                        
                        // Désélectionner toute ligne sélectionnée à l'initialisation
                            tableMoniteur.getSelectionModel().clearSelection();
                            
                        //desactiver les button 
                            MoniteurInfoSectionButton.setDisable(true);
                            MoniteurSeanceSectionButton.setDisable(true);
                            MoniteurExamenSectionButton.setDisable(true);
                            
                        //recuperer le background original des button
                            MoniteurInfoSectionButton.setStyle("-fx-background-color:   #929081;"); 
                            MoniteurSeanceSectionButton.setStyle("-fx-background-color:   #929081;"); 
                            MoniteurExamenSectionButton.setStyle("-fx-background-color:   #929081;"); 

                        
                        
                        MoniteurInfoSection.setVisible(false);
                        MoniteurSeanceSection.setVisible(false);
                        MoniteurExamenSection.setVisible(false);
                        MoniteurSectionParDefaut.setVisible(true);
                        
                        // Configuration des colonnes de la table des condidat
                           tableMoniteurNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
                           tableMoniteurPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                           tableMoniteurDateNaiss.setCellValueFactory(new PropertyValueFactory<>("dateNaiss"));
                           tableMoniteurLieuNaiss.setCellValueFactory(new PropertyValueFactory<>("lieuNaiss"));
                           
                           
                        // Récupérer les informations des moniteur de la base de données
                           ObservableList<Moniteur> moniteursList = gestionnaireMoniteur.recupererMoniteur();

                           // Ajouter les données au TableView de moniteur
                          tableMoniteur.setItems(moniteursList);

                           // Créez un FilteredList pour la table des moniteur
                            FilteredList<Moniteur> filteredData = new FilteredList<>(moniteursList, p -> true);

                       
                        // Appliquez un listener sur le champ de recherche de la table des moniteur 
                                searchMoniteurField.textProperty().addListener((observable, oldValue, newValue) -> {
                                filteredData.setPredicate(moniteur -> {
                                // Si le champ de recherche est vide, on affiche tous les moniteur
                                if (newValue == null || newValue.isEmpty()) {
                                   return true;
                                }
                                // Convertir le texte de recherche en minuscules pour éviter la casse
                                String lowerCaseFilter = newValue.toLowerCase();

                                // Vérifiez si le nom ou le prénom correspond à la recherche
                                if (moniteur.getNom().toLowerCase().contains(lowerCaseFilter)) {
                                    return true; // Le nom contient le texte de recherche
                                } else if (moniteur.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                                    return true; // Le prénom contient le texte de recherche
                                } else if (moniteur.getDateNaiss().toLowerCase().contains(lowerCaseFilter)) {
                                    return true; // La date de naissance contient le texte de recherche
                                }
                                return false; // Aucun critère trouvé
                                });
                                });

                                // Appliquez le filtre à la TableView
                                SortedList<Moniteur> sortedData = new SortedList<>(filteredData);
                                sortedData.comparatorProperty().bind(tableMoniteur.comparatorProperty());
                                tableMoniteur.setItems(sortedData); 
                                
                                
                                
                                
                                

                                    // Remplir la ComboBox "SeanceEpreuve"
                                    ObservableList<String> seanceEpreuves = FXCollections.observableArrayList(
                                        "Tous",
                                        "code", 
                                        "crenaux", 
                                        "circulation"
                                    );
                                    MoniteurSeanceEpreuve.setItems(seanceEpreuves);

                                    // Remplir la ComboBox "SeancePermis"
                                    ObservableList<String> seancePermis = FXCollections.observableArrayList(
                                        "Tous",
                                        "A1", 
                                        "A2", 
                                        "B",
                                        "C1",
                                        "C2",
                                        "E", 
                                        "D"
                                    );
                                    MoniteurSeancePermis.setItems(seancePermis);

                                    
                                    // Initialiser les colonnes de la table des séances d'un moniteur
                                            MoniteurSeanceTableColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                                            MoniteurSeanceTableColHeure.setCellValueFactory(new PropertyValueFactory<>("heure"));
                                            MoniteurSeanceTableColEpreuve.setCellValueFactory(new PropertyValueFactory<>("epreuve"));
                                            MoniteurSeanceTableColType.setCellValueFactory(new PropertyValueFactory<>("type"));
                                            MoniteurSeanceTableColPermis.setCellValueFactory(new PropertyValueFactory<>("permis"));
                                    
                                    
                                    
                                    
                                    // Ajouter des listeners aux ComboBox pour filtrer les données
                                    MoniteurSeanceEpreuve.valueProperty().addListener((observable, oldValue, newValue) -> applyFilterSeanceMoniteur());
                                    MoniteurSeancePermis.valueProperty().addListener((observable, oldValue, newValue) -> applyFilterSeanceMoniteur());
                                    MoniteurSeanceDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> applyFilterSeanceMoniteur());
                                
                                    
                                    
                                    // Ajouter un listener pour détecter la sélection pour affiche les information de condidat selectionner
                                tableMoniteur.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                                    if (newValue != null) {
                                        
                                        //afficher la section info personelle si 
                                        if ( MoniteurSectionParDefaut.isVisible()){
                                            MoniteurSectionParDefaut.setVisible(false);
                                             MoniteurInfoSection.setVisible(true);
                                             MoniteurInfoSectionButton.setStyle("-fx-background-color:   linear-gradient(to top, #c2c3a2, #403B4A);"); // Exemple de changement de fond

                                        }

                                        //disable pour les button si aucun condidat est selectionner
                                                MoniteurInfoSectionButton.setDisable(newValue == null);
                                                MoniteurSeanceSectionButton.setDisable(newValue == null);
                                                MoniteurExamenSectionButton.setDisable(newValue == null);

                                        //afficher la section des information personnelles
                                                afficherDetailsMoniteur(newValue);

                                        //afficher la section des seance
                                                // Récupérer l'ID du candidat sélectionné
                                            int moniteurId = newValue.getId(); // Supposons que vous ayez un getter pour l'ID du candidat

                                            // Récupérer les séances associées à ce candidat
                                             seancesMoniteurList = gestionnaireMoniteur.recupererSeancesPourMoniteur(moniteurId);

                                            // Mettre à jour la TableView avec les séances récupérées
                                            MoniteurSeanceTable.setItems(seancesMoniteurList);
                                            
                                            moniteurSeanceNbrSeance.setText(String.valueOf(seancesMoniteurList.size()));
                                            
                                            //initialiser les combobox 
                                            MoniteurSeanceEpreuve.setValue(null);
                                            MoniteurSeancePermis.setValue(null);
                                            
                                            
                                            
                                            // Configuration des colonnes de la table des examens de condidat
                                            MoniteurExamenTableColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                                            MoniteurExamenTableColNbrCondidat.setCellValueFactory(new PropertyValueFactory<>("nbr_condidat"));
                                            
                                            
                                            //afficher la section des examen
                                            // Récupérer les examens associés à ce candidat
                                            ObservableList<ExamenMoniteurInfo> examensMoniteurList = gestionnaireMoniteur.recupererExamensPourMoniteur(moniteurId);

                                            // Mettre à jour la TableView avec les examens récupérés
                                            moniteurExamenTable.setItems(examensMoniteurList);
                                            
                                            moniteurExamenNbrExamen.setText(String.valueOf(examensMoniteurList.size()));

                                        }});
                    }
                    
                    
            //Regler le masquage et l'affichage des section de l'interface moniteur
                     

                    @FXML
                    private HBox MoniteurInfoSection;
                    
                    @FXML
                    private JFXButton MoniteurInfoSectionButton;
                    
                    @FXML
                    private VBox MoniteurSeanceSection;
                    
                    @FXML
                    private JFXButton MoniteurSeanceSectionButton;
                    
                    @FXML
                    private VBox MoniteurExamenSection;
                    
                    @FXML
                    private JFXButton MoniteurExamenSectionButton;
                    
                    
                    @FXML
                    private VBox MoniteurSectionParDefaut;
                    
                    
        // Fonction unique pour gérer les clics de l'interface condidat, avec changement de scale et de background
                    
                    @FXML
                    private void handleSectionMoniteurClick(Event event) {
                        // Masquer toutes les sections
                        MoniteurInfoSection.setVisible(false);
                        MoniteurSeanceSection.setVisible(false);
                        MoniteurExamenSection.setVisible(false);
                        MoniteurSectionParDefaut.setVisible(false);

                        // Réinitialiser l'échelle et le fond de tous les boutons (optionnel)
                        resetButtonMoniteurStyle();

                        // Vérifier quelle source a déclenché l'événement et afficher la section correspondante
                        if (event.getSource() == MoniteurInfoSectionButton) {
                            MoniteurInfoSection.setVisible(true);
                            // Appliquer un changement de scale et de fond pour le bouton Accueil
                            MoniteurInfoSectionButton.setStyle("-fx-background-color:   linear-gradient(to top, #c2c3a2, #403B4A);"); // Exemple de changement de fond
                            
                        } else if (event.getSource() == MoniteurSeanceSectionButton) {
                            MoniteurSeanceSection.setVisible(true);
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            MoniteurSeanceSectionButton.setStyle("-fx-background-color:   linear-gradient(to top, #c2c3a2, #403B4A);"); // Exemple de changement de fond
                           
                        } else if (event.getSource() == MoniteurExamenSectionButton) {
                            MoniteurExamenSection.setVisible(true);
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            MoniteurExamenSectionButton.setStyle("-fx-background-color:   linear-gradient(to top, #c2c3a2, #403B4A);"); // Exemple de changement de fond
                           
                        } 
                    }
                    
                    
                    
        // Réinitialiser les styles des boutons pour revenir à la taille et fond d'origine
                    private void resetButtonMoniteurStyle() {
                        
                        MoniteurInfoSectionButton.setStyle("-fx-background-color: #929081;");

                        MoniteurSeanceSectionButton.setStyle("-fx-background-color: #929081;");
                        
                        MoniteurExamenSectionButton.setStyle("-fx-background-color: #929081;");
                        
                    }
            
            
       //methode pour afficher les informations d'un moniteur

            private void afficherDetailsMoniteur(Moniteur moniteur) {
            afficherNomMoniteur.setText(moniteur.getNom());
            afficherPrenomMoniteur.setText(moniteur.getPrenom());
            afficherDateNaissanceMoniteur.setText(moniteur.getDateNaiss());
            afficherLieuNaissanceMoniteur.setText(moniteur.getLieuNaiss());
            afficherTelephoneMoniteur.setText(moniteur.getTlphn());
            aficherAdresseMoniteur.setText(moniteur.getAdr());
            afficherSainguinMoniteur.setText(moniteur.getGroupe_sanguin());
            afficherDateInscriptionMoniteur.setText(moniteur.getDateInscription());
            }     
            
            
            
        //Regler la partie seance de l'interface moniteur
            
            //recuperer les comboBox
            @FXML
            private JFXDatePicker MoniteurSeanceDatePicker;

            @FXML
            private ComboBox<String> MoniteurSeanceEpreuve;

            @FXML
            private ComboBox<String> MoniteurSeancePermis;
            
            //recuperer la table des seance   

            @FXML
            private TableView<SeanceCondidatInfo> MoniteurSeanceTable;

            @FXML
            private TableColumn<SeanceCondidatInfo, String> MoniteurSeanceTableColDate;

            @FXML
            private TableColumn<SeanceCondidatInfo, String> MoniteurSeanceTableColHeure;

            @FXML
            private TableColumn<SeanceCondidatInfo, String> MoniteurSeanceTableColEpreuve;

            @FXML
            private TableColumn<SeanceCondidatInfo, String> MoniteurSeanceTableColType;

            @FXML
            private TableColumn<SeanceCondidatInfo, String> MoniteurSeanceTableColPermis;

            //le nombre de seance 
            @FXML
            private JFXTextField moniteurSeanceNbrSeance;
            
            //le nombre d'examen 
            @FXML
            private JFXTextField moniteurExamenNbrExamen;
            
            
            
    // Méthode pour appliquer le filtre sur la table des seance d'un condidat
            ObservableList<SeanceCondidatInfo> seancesMoniteurList;
            
            private void applyFilterSeanceMoniteur() {
                String selectedEpreuve = MoniteurSeanceEpreuve.getValue();
                String selectedPermis = MoniteurSeancePermis.getValue();
                LocalDate selectedDate = MoniteurSeanceDatePicker.getValue();

                FilteredList<SeanceCondidatInfo> filteredData = new FilteredList<>(seancesMoniteurList, seance -> {
                    boolean matchesEpreuve = selectedEpreuve == null || selectedEpreuve.equals("Tous") || seance.getEpreuve().equals(selectedEpreuve);
                    boolean matchesPermis = selectedPermis == null || selectedPermis.equals("Tous") || seance.getPermis().equals(selectedPermis);

                    // Vérification de la date
                    boolean matchesDate = true; // Par défaut, accepte tout
                    if (selectedDate != null) {
                        try {
                            // Conversion de la date (String) de l'objet SeanceCondidatInfo en LocalDate
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Format des dates dans votre base
                            LocalDate seanceDate = LocalDate.parse(seance.getDate(), formatter);

                            matchesDate = seanceDate.equals(selectedDate);
                        } catch (DateTimeParseException e) {
                            matchesDate = false; // Si la conversion échoue
                        }
                    }

                    return matchesEpreuve && matchesPermis && matchesDate;
                });

                // Appliquer le filtre à la TableView
                SortedList<SeanceCondidatInfo> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(MoniteurSeanceTable.comparatorProperty());
                MoniteurSeanceTable.setItems(sortedData);
            }

            //regler le cliquage sur les button ajouter moniteur 
                    @FXML
                    private JFXButton AfficherAjouterMoniteurButton;

                    @FXML
                    private void afficherAjouterMoniteur() {
                     try {
                    // Charger le fichier FXML de la fenêtre d'ajout de moniteur
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AjouterMoniteur.fxml"));
                    Parent root = loader.load();

                    // Créer un nouveau Stage pour la fenêtre modale
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL); // Définit la modalité
                    stage.initStyle(StageStyle.UNDECORATED); // Facultatif : pour supprimer la barre de titre
                    stage.setTitle("Ajouter un moniteur");

                    // Ajouter le contenu chargé au Stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    // Afficher la fenêtre au centre de la fenêtre principale
                    stage.centerOnScreen();

                    // Afficher la fenêtre modale
                    stage.showAndWait(); // Bloque jusqu'à ce que la fenêtre soit fermée

                     initialiserSectionMoniteur();
                    
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    }
                    
                    //regler le clic sur le button modifier dans condidat
                    
                    @FXML
                    private JFXButton AfficherModifierMoniteur;

                    @FXML
                    private void afficherModifierMoniteur() {
                        Moniteur selectedMoniteur = tableMoniteur.getSelectionModel().getSelectedItem();

                        if (selectedMoniteur == null) {
                            // Afficher une alerte si aucune ligne n'est sélectionnée
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Avertissement");
                            alert.setHeaderText(null);
                            alert.setContentText("Veuillez sélectionner un moniteur pour modifier ses informations.");
                            alert.showAndWait();
                            return;
                        }

                        try {
                            // Charger le fichier FXML de la fenêtre de modification
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modifierMoniteur.fxml"));
                            Parent root = loader.load();

                            // Obtenir le contrôleur de la fenêtre de modification
                            ModifierMoniteurController controller = loader.getController();

                            // Passer les informations du candidat sélectionné au contrôleur
                            controller.initialiserChamps(selectedMoniteur);

                            // Créer un nouveau Stage pour la fenêtre modale
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL); // Fenêtre modale
                            stage.initStyle(StageStyle.UNDECORATED); // Optionnel : retirer la barre de titre
                            stage.setTitle("Modifier un moniteur");

                            // Ajouter le contenu chargé au Stage
                            Scene scene = new Scene(root);
                            stage.setScene(scene);

                            // Afficher la fenêtre au centre de l'écran
                            stage.centerOnScreen();
                            stage.showAndWait(); // Bloquer jusqu'à la fermeture

                           initialiserSectionMoniteur();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    //configurer le clic sur le button supprimer condidat
                    
                    
                    @FXML
                    private JFXButton SupprimerMoniteurButton;

                    @FXML
                    private void supprimerMoniteur() {
                        // Vérifier si un candidat est sélectionné
                        Moniteur moniteurSelectionne = tableMoniteur.getSelectionModel().getSelectedItem();
                        if (moniteurSelectionne == null) {
                            afficherAlerte("Erreur", "Veuillez sélectionner un moniteur à supprimer.");
                            return;
                        }
                        
                        // Demander confirmation à l'utilisateur
                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Confirmation de suppression");
                        confirmation.setHeaderText("Voulez-vous vraiment supprimer ce moniteur ?");
                        confirmation.setContentText("Nom : " + moniteurSelectionne.getNom() + "\nPrénom : " + moniteurSelectionne.getPrenom());

                        Optional<ButtonType> result = confirmation.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            GestionnaireMoniteur gestionnaire = new GestionnaireMoniteur();
                            try {
                                boolean success = gestionnaire.supprimerMoniteur(moniteurSelectionne.getId());
                                if (success) {
                                    afficherAlerte("Succès", "Le moniteur a été supprimé avec succès.");
                                    // Rafraîchir la table après suppression
                                    initialiserSectionMoniteur();
                                }
                            } catch (IllegalStateException e) {
                                afficherAlerte("Erreur", "Impossible de supprimer ce moniteur car il est lié à d'autres données.");
                            }
                        }
                    }

                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                                                        //interface Utilisateur
        
            @FXML
            private TableView<UtilisateurInfo> userTable;

            @FXML
            private TableColumn<UtilisateurInfo, String> userTableColDate;

            @FXML
            private TableColumn<UtilisateurInfo, String> userTableColRole;

            @FXML
            private TableColumn<UtilisateurInfo, String> userTableColUsername;
                    
        
            
                             //methode pour initialiser l'inteface utilisateur
                    @FXML
                    private void initialiserSectionUtilisateur(){
                            
                            
                          // Configuration des colonnes de la table des examens de condidat
                           userTableColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                           userTableColRole.setCellValueFactory(new PropertyValueFactory<>("role"));  
                           userTableColUsername.setCellValueFactory(new PropertyValueFactory<>("username"));  
                           
                           // Récupérer les utilisateur
                           GestionnaireUtilisateur gestionnaireUtilisateur = new GestionnaireUtilisateur();

                             ObservableList<UtilisateurInfo> userList = gestionnaireUtilisateur.recupererUtilisateur();

                                            // Mettre à jour la TableView avec les examens récupérés
                                            userTable.setItems(userList);
                    }
            
            //regler le cliquage sur les button ajouter moniteur 
                    @FXML
                private JFXButton ajouterUser;  

                    @FXML
                    private void afficherAjouterUtilisateur() {
                     try {
                    // Charger le fichier FXML de la fenêtre d'ajout de candidat
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AjouterUtilisateur.fxml"));
                    Parent root = loader.load();

                    // Créer un nouveau Stage pour la fenêtre modale
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL); // Définit la modalité
                    stage.initStyle(StageStyle.UNDECORATED); // Facultatif : pour supprimer la barre de titre
                    stage.setTitle("Ajouter un utilisateur");

                    // Ajouter le contenu chargé au Stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    // Afficher la fenêtre au centre de la fenêtre principale
                    stage.centerOnScreen();

                    // Afficher la fenêtre modale
                    stage.showAndWait(); // Bloque jusqu'à ce que la fenêtre soit fermée

                     initialiserSectionUtilisateur();
                    
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    }
                    
                    
                    //regler le clic sur le button modifier dans utilisateur
                    
                    @FXML
            private JFXButton modifierUser;
                    

                    @FXML
                    private void afficherModifierUtilisateur() {
                        UtilisateurInfo selecteduser = userTable.getSelectionModel().getSelectedItem();

                        if (selecteduser == null) {
                            // Afficher une alerte si aucune ligne n'est sélectionnée
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Avertissement");
                            alert.setHeaderText(null);
                            alert.setContentText("Veuillez sélectionner un utilisateur pour modifier ses informations.");
                            alert.showAndWait();
                            return;
                        }

                        try {
                            // Charger le fichier FXML de la fenêtre de modification
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modifierUtilisateur.fxml"));
                            Parent root = loader.load();

                            // Obtenir le contrôleur de la fenêtre de modification
                            ModifierUtilisateurController controller = loader.getController();

                            // Passer les informations du candidat sélectionné au contrôleur
                            controller.initialiserChamps(selecteduser);

                            // Créer un nouveau Stage pour la fenêtre modale
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL); // Fenêtre modale
                            stage.initStyle(StageStyle.UNDECORATED); // Optionnel : retirer la barre de titre
                            stage.setTitle("Modifier un utilisateur");

                            // Ajouter le contenu chargé au Stage
                            Scene scene = new Scene(root);
                            stage.setScene(scene);

                            // Afficher la fenêtre au centre de l'écran
                            stage.centerOnScreen();
                            stage.showAndWait(); // Bloquer jusqu'à la fermeture

                           initialiserSectionUtilisateur();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    
                    //configurer le clic sur le button supprimer condidat
                    
                    
                    @FXML
                private JFXButton supprimerUser;

                    @FXML
                    private void supprimerUtilisateur() {
                        // Vérifier si un utilisateur est sélectionné
                        UtilisateurInfo selecteduser = userTable.getSelectionModel().getSelectedItem();
                        if (selecteduser == null) {
                            afficherAlerte("Erreur", "Veuillez sélectionner un utilisateur à supprimer.");
                            return;
                        }
                        
                        // Demander confirmation à l'utilisateur
                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Confirmation de suppression");
                        confirmation.setHeaderText("Voulez-vous vraiment supprimer ce utilisateur ?");
                        confirmation.setContentText("Nom d'utilsateur : " + selecteduser.getUsername() );

                        Optional<ButtonType> result = confirmation.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                        GestionnaireUtilisateur gestionnaire = new GestionnaireUtilisateur();
                            try {
                                boolean success = gestionnaire.supprimerUtilisateur(selecteduser.getId());
                                if (success) {
                                    afficherAlerte("Succès", "Le moniteur a été supprimé avec succès.");
                                    // Rafraîchir la table après suppression
                                    initialiserSectionUtilisateur();
                                }
                            } catch (IllegalStateException e) {
                                afficherAlerte("Erreur", "Impossible de supprimer ce moniteur car il est lié à d'autres données.");
                            }
                        }
                    }
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                                                    //Interface Vehicule
         
            @FXML
            private TableView<Vehicule> tableVehicule;

            @FXML
            private TableColumn<Vehicule, String> tableVehiculeMarque;

            @FXML
            private TableColumn<Vehicule, String> tableVehiculeModele;

            @FXML
            private TableColumn<Vehicule, String> tableVehiculeType; 
            
            @FXML
            private TableColumn<Vehicule, String> tableVehiculeImmatriculation; 
            
            @FXML
            private VBox VehiculeSectionParDefaut1;
            
            @FXML
            private HBox vehiculeInfoSection;
            
            
            @FXML
            private JFXTextField afficherProprietaireVoiture;
            
            @FXML
            private JFXTextField afficherNumAssuranceVoiture;
            
            @FXML
            private JFXTextField afficherTypeAssuranceVoiture;
            
            @FXML
            private JFXTextField afficherDateAssuranceVoiture;
            
            @FXML
            private JFXTextField afficherDateControleVoiture;
            
            @FXML
            private JFXTextField afficherMatriculeVoiture;
            
            @FXML
            private JFXTextField afficherTypeVoiture;
            
            @FXML
            private JFXTextField afficherModeleVoiture;
            
            @FXML
            private JFXTextField afficherMarqueVoiture;
                    
                    //methode pour initialiser l'inteface vehicule
                    @FXML
                    private void initialiserSectionVehicule(){
                        
                        VehiculeSectionParDefaut1.setVisible(true);
                                             vehiculeInfoSection.setVisible(false);
                        
                        // Associer les colonnes aux propriétés de l'objet Vehicule
                        tableVehiculeMarque.setCellValueFactory(new PropertyValueFactory<>("marque"));
                        tableVehiculeModele.setCellValueFactory(new PropertyValueFactory<>("modele"));
                        tableVehiculeType.setCellValueFactory(new PropertyValueFactory<>("typeVehicule"));
                        tableVehiculeImmatriculation.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
                        
                        
                        GestionnaireVehicule gestionnaireVehicule = new GestionnaireVehicule();
                        ObservableList<Vehicule> vehicules = gestionnaireVehicule.recupererVehicules();
                        tableVehicule.setItems(vehicules);
                        
                        
                        tableVehicule.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                                    if (newValue != null) {
                                        
                                        //afficher la section info personelle si 
                                        if ( VehiculeSectionParDefaut1.isVisible()){
                                            VehiculeSectionParDefaut1.setVisible(false);
                                             vehiculeInfoSection.setVisible(true);
                                        }

                                        

                                        //afficher la section des information personnelles
                                                afficherDetailsVehicule(newValue);

                                        
                                        }});
                        
                        
                        
                    }
                    
                    //methode pour afficher les informations d'un condidat

            private void afficherDetailsVehicule(Vehicule vehicule) {
            if (vehicule != null) {
                afficherProprietaireVoiture.setText(vehicule.getProprietaire());
                afficherNumAssuranceVoiture.setText(vehicule.getNumAssurance());
                afficherTypeAssuranceVoiture.setText(vehicule.getTypeAssurance());
                afficherDateAssuranceVoiture.setText(vehicule.getDateAssurance() != null ? vehicule.getDateAssurance().toString() : "");
                afficherDateControleVoiture.setText(vehicule.getDateControle() != null ? vehicule.getDateControle().toString() : "");
                afficherMatriculeVoiture.setText(vehicule.getImmatriculation());
                afficherTypeVoiture.setText(vehicule.getTypeVehicule());
                afficherModeleVoiture.setText(vehicule.getModele());
                afficherMarqueVoiture.setText(vehicule.getMarque());
            } else {
                // Si aucun véhicule n'est sélectionné ou si l'objet est null, vider les champs
                afficherProprietaireVoiture.clear();
                afficherNumAssuranceVoiture.clear();
                afficherTypeAssuranceVoiture.clear();
                afficherDateAssuranceVoiture.clear();
                afficherDateControleVoiture.clear();
                afficherMatriculeVoiture.clear();
                afficherTypeVoiture.clear();
                afficherModeleVoiture.clear();
                afficherMarqueVoiture.clear();
            }
            } 
                    
            //regler le cliquage sur les button ajouter Vehicule 
                    @FXML
                private JFXButton AfficherAjouterVoitureButton;  

                    @FXML
                    private void afficherAjouterVehicule() {
                     try {
                    // Charger le fichier FXML de la fenêtre d'ajout de vehicule
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AjouterVoiture.fxml"));
                    Parent root = loader.load();

                    // Créer un nouveau Stage pour la fenêtre modale
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL); // Définit la modalité
                    stage.initStyle(StageStyle.UNDECORATED); // Facultatif : pour supprimer la barre de titre
                    stage.setTitle("Ajouter un vehicule");

                    // Ajouter le contenu chargé au Stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    // Afficher la fenêtre au centre de la fenêtre principale
                    stage.centerOnScreen();

                    // Afficher la fenêtre modale
                    stage.showAndWait(); // Bloque jusqu'à ce que la fenêtre soit fermée

                     initialiserSectionVehicule();
                    
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    }       
                    
              //regler le clic sur le button modifier dans vehicule
                    
                    @FXML
            private JFXButton AfficherModifierVehicule;
                    

                    @FXML
                    private void afficherModifierVehicule() {
                        Vehicule selecteduser = tableVehicule.getSelectionModel().getSelectedItem();

                        if (selecteduser == null) {
                            // Afficher une alerte si aucune ligne n'est sélectionnée
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Avertissement");
                            alert.setHeaderText(null);
                            alert.setContentText("Veuillez sélectionner un vehicule pour modifier ses informations.");
                            alert.showAndWait();
                            return;
                        }

                        try {
                            // Charger le fichier FXML de la fenêtre de modification
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modifierVehicule.fxml"));
                            Parent root = loader.load();

                            // Obtenir le contrôleur de la fenêtre de modification
                            ModifierVehiculeController controller = loader.getController();

                            // Passer les informations du candidat sélectionné au contrôleur
                            controller.initialiserChamps(selecteduser);

                            // Créer un nouveau Stage pour la fenêtre modale
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL); // Fenêtre modale
                            stage.initStyle(StageStyle.UNDECORATED); // Optionnel : retirer la barre de titre
                            stage.setTitle("Modifier un vehicule");

                            // Ajouter le contenu chargé au Stage
                            Scene scene = new Scene(root);
                            stage.setScene(scene);

                            // Afficher la fenêtre au centre de l'écran
                            stage.centerOnScreen();
                            stage.showAndWait(); // Bloquer jusqu'à la fermeture

                           initialiserSectionVehicule();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    
                     //configurer le clic sur le button supprimer vehicule
                    
                    
                    @FXML
                private JFXButton SupprimerVoitureButton;

                    @FXML
                    private void supprimerVehicule() {
                        // Vérifier si un utilisateur est sélectionné
                        Vehicule selecteduser = tableVehicule.getSelectionModel().getSelectedItem();
                        if (selecteduser == null) {
                            afficherAlerte("Erreur", "Veuillez sélectionner un vehicule à supprimer.");
                            return;
                        }
                        
                        // Demander confirmation à l'utilisateur
                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Confirmation de suppression");
                        confirmation.setHeaderText("Voulez-vous vraiment supprimer ce vehicule ?");

                        Optional<ButtonType> result = confirmation.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                        GestionnaireVehicule gestionnaire = new GestionnaireVehicule();
                            try {
                                boolean success = gestionnaire.supprimerVehicule(selecteduser.getIdVehicule());
                                if (success) {
                                    afficherAlerte("Succès", "Le vehicule a été supprimé avec succès.");
                                    // Rafraîchir la table après suppression
                                    initialiserSectionVehicule();
                                }
                            } catch (IllegalStateException e) {
                                afficherAlerte("Erreur", "Impossible de supprimer ce vehicule car il est lié à d'autres données.");
                            }
                        }
                    }
                          
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
            
                                                        //interface comptabilité
                    
                    
            
            
            @FXML
            private TableView<Versement> versementTable;

            @FXML
            private TableColumn<Versement, String> versementTableDate;

            @FXML
            private TableColumn<Versement, Double> versementTableMontant;

            @FXML
            private TableColumn<Versement, String> versementTableCondidat;  
            
            
            @FXML
            private TableView<Depence> depenceTable;

            @FXML
            private TableColumn<Depence, String> depenceTableDate;

            @FXML
            private TableColumn<Depence, Double> depenceTableMontant;

            @FXML
            private TableColumn<Depence, String> depenceTableMotif;
            
            
            @FXML
            private JFXTextField totalVersement;
            
            @FXML
            private JFXTextField totalDepence;
            
            @FXML
            private JFXTextField benificeNet;
            
            @FXML
            private ComboBox<Integer> anneeComptabilite;
            
            @FXML
            private ComboBox<String> moisComptabilite;
            
            
            
            
                                //methode pour initialiser l'inteface Comptabilite
                    @FXML
                    private void initialiserSectionQuantabilite(){
                        
                        
                        
                        
                         // Configurer les colonnes
                        versementTableDate.setCellValueFactory(new PropertyValueFactory<>("dateVersement"));
                        versementTableMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
                        versementTableCondidat.setCellValueFactory(new PropertyValueFactory<>("nomCondidat"));
                        
                        
                        GestionnaireQuantabilite gestionnaireQuantabilite = new GestionnaireQuantabilite();
                        ObservableList<Versement> versements = gestionnaireQuantabilite.recupererVersement();
                        versementTable.setItems(versements);
                        
                        
                         // Calculer et afficher le total des versements
                        double totalVersements = versements.stream()
                                .mapToDouble(Versement::getMontant) // Appel direct à la méthode getter
                                .sum();

                        // Mettre à jour le champ texte avec le total
                        totalVersement.setText(String.format("%.2f", totalVersements)+"  da"); // Affichage formaté à deux  
                        
                        
                        
                         // Configurer les colonnes
                        depenceTableDate.setCellValueFactory(new PropertyValueFactory<>("dateDepence"));
                        depenceTableMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
                        depenceTableMotif.setCellValueFactory(new PropertyValueFactory<>("motif"));
                        
                        
                        ObservableList<Depence> depences = gestionnaireQuantabilite.recupererDepence();
                        depenceTable.setItems(depences);
                        
                        
                         // Calculer et afficher le total des versements
                        double totalDepences = depences.stream()
                                .mapToDouble(Depence::getMontant) // Appel direct à la méthode getter
                                .sum();

                        // Mettre à jour le champ texte avec le total
                        totalDepence.setText(String.format("%.2f", totalDepences)+"  da"); // Affichage formaté à deux  
                        
                        
                        double benifice =  totalVersements - totalDepences;
                        benificeNet.setText(String.format("%.2f", benifice)+"  da"); // Affichage formaté à deux 
                        
                        
                        // Ajoutez un intervalle d'années (par exemple, 2000 à l'année en cours)
                        int currentYear = LocalDate.now().getYear();
                        ObservableList<Integer> years = FXCollections.observableArrayList();
                        for (int year = 2000; year <= currentYear; year++) {
                            years.add(year);
                        }
                        anneeComptabilite.setItems(years);

                        // Définir l'année en cours comme sélection par défaut
                        anneeComptabilite.setValue(currentYear);
                        
                        
                        ObservableList<String> mois = FXCollections.observableArrayList(
                            "Tous", "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", 
                            "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
                        );
                        moisComptabilite.setItems(mois);
                        
                        
                        filtrerParAnneeEtMois();
                        
                    }
                    
                    
                    //methode pour filtrer la table de versement et depence selon l'annee choisi
                     @FXML
                    private void filtrerParAnneeEtMois() {
                        int selectedYear = anneeComptabilite.getValue(); // Récupérer l'année sélectionnée
                        String selectedMonth = moisComptabilite.getValue(); // Récupérer le mois sélectionné

                        GestionnaireQuantabilite gestionnaireQuantabilite = new GestionnaireQuantabilite();

                        // Filtrer les versements
                        ObservableList<Versement> versementsFiltres = gestionnaireQuantabilite.recupererVersement()
                            .stream()
                            .filter(v -> {
                                LocalDate dateVersement = v.getDateVersementAsLocalDate();
                                int year = dateVersement.getYear();
                                int month = dateVersement.getMonthValue();
                                // Vérifie l'année et, si un mois est sélectionné, filtre également par mois
                                return year == selectedYear && 
                                       ( selectedMonth == null || "Tous".equals(selectedMonth) || month == convertirMoisEnChiffre(selectedMonth));
                            })
                            .collect(Collectors.toCollection(FXCollections::observableArrayList));
                        versementTable.setItems(versementsFiltres);

                        // Calculer et afficher le total des versements filtrés
                        double totalVersements = versementsFiltres.stream()
                            .mapToDouble(Versement::getMontant)
                            .sum();
                        totalVersement.setText(String.format("%.2f", totalVersements) + " da");

                        // Filtrer les dépenses
                        ObservableList<Depence> depencesFiltres = gestionnaireQuantabilite.recupererDepence()
                            .stream()
                            .filter(d -> {
                                LocalDate dateDepense = d.getDateDepenceAsLocalDate();
                                int year = dateDepense.getYear();
                                int month = dateDepense.getMonthValue();
                                return year == selectedYear &&
                                       (selectedMonth == null || "Tous".equals(selectedMonth) || month == convertirMoisEnChiffre(selectedMonth));
                            })
                            .collect(Collectors.toCollection(FXCollections::observableArrayList));
                        depenceTable.setItems(depencesFiltres);

                        // Calculer et afficher le total des dépenses filtrées
                        double totalDepences = depencesFiltres.stream()
                            .mapToDouble(Depence::getMontant)
                            .sum();
                        totalDepence.setText(String.format("%.2f", totalDepences) + " da");

                        // Calculer et afficher le bénéfice net
                        double benifice = totalVersements - totalDepences;
                        benificeNet.setText(String.format("%.2f", benifice) + " da");
                    }

                    private int convertirMoisEnChiffre(String mois) {
                        switch (mois) {
                            case "Janvier": return 1;
                            case "Février": return 2;
                            case "Mars": return 3;
                            case "Avril": return 4;
                            case "Mai": return 5;
                            case "Juin": return 6;
                            case "Juillet": return 7;
                            case "Août": return 8;
                            case "Septembre": return 9;
                            case "Octobre": return 10;
                            case "Novembre": return 11;
                            case "Décembre": return 12;
                            default: return -1;
                        }
                    }
                   

            
                   
                    //regler le cliquage sur les button ajouter Versement 
                    @FXML
                private JFXButton AfficherAjouterVersementButton;  

                    @FXML
                    private void afficherAjouterVersement() {
                     try {
                    // Charger le fichier FXML de la fenêtre d'ajout de candidat
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AjouterVersement.fxml"));
                    Parent root = loader.load();

                    // Créer un nouveau Stage pour la fenêtre modale
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL); // Définit la modalité
                    stage.initStyle(StageStyle.UNDECORATED); // Facultatif : pour supprimer la barre de titre
                    stage.setTitle("Ajouter un versement");

                    // Ajouter le contenu chargé au Stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    // Afficher la fenêtre au centre de la fenêtre principale
                    stage.centerOnScreen();

                    // Afficher la fenêtre modale
                    stage.showAndWait(); // Bloque jusqu'à ce que la fenêtre soit fermée

                     initialiserSectionQuantabilite();
                    
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    }       
                    
                     @FXML
            private JFXButton AfficherModifierVersement;
                    

                    @FXML
                    private void AfficherModifierVersement() {
                        Versement selecteduser = versementTable.getSelectionModel().getSelectedItem();

                        if (selecteduser == null) {
                            // Afficher une alerte si aucune ligne n'est sélectionnée
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Avertissement");
                            alert.setHeaderText(null);
                            alert.setContentText("Veuillez sélectionner un versement pour modifier ses informations.");
                            alert.showAndWait();
                            return;
                        }

                        try {
                            // Charger le fichier FXML de la fenêtre de modification
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modifierVersement.fxml"));
                            Parent root = loader.load();

                            // Obtenir le contrôleur de la fenêtre de modification
                            ModifierVersementController controller = loader.getController();

                            // Passer les informations du candidat sélectionné au contrôleur
                            controller.initialiserChamps(selecteduser);

                            // Créer un nouveau Stage pour la fenêtre modale
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL); // Fenêtre modale
                            stage.initStyle(StageStyle.UNDECORATED); // Optionnel : retirer la barre de titre
                            stage.setTitle("Modifier un versement");

                            // Ajouter le contenu chargé au Stage
                            Scene scene = new Scene(root);
                            stage.setScene(scene);

                            // Afficher la fenêtre au centre de l'écran
                            stage.centerOnScreen();
                            stage.showAndWait(); // Bloquer jusqu'à la fermeture

                           initialiserSectionQuantabilite();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    
                     @FXML
                private JFXButton SupprimerVersementButton;

                    @FXML
                    private void supprimerVersement() {
                        // Vérifier si un utilisateur est sélectionné
                        Versement selecteduser = versementTable.getSelectionModel().getSelectedItem();
                        if (selecteduser == null) {
                            afficherAlerte("Erreur", "Veuillez sélectionner un versement à supprimer.");
                            return;
                        }
                        
                        // Demander confirmation à l'utilisateur
                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Confirmation de suppression");
                        confirmation.setHeaderText("Voulez-vous vraiment supprimer ce versement ?");

                        Optional<ButtonType> result = confirmation.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                        GestionnaireQuantabilite gestionnaire = new GestionnaireQuantabilite();
                            try {
                                boolean success = gestionnaire.supprimerVersement(selecteduser.getId_versement());
                                if (success) {
                                    afficherAlerte("Succès", "Le versement a été supprimé avec succès.");
                                    // Rafraîchir la table après suppression
                                    initialiserSectionQuantabilite();
                                }
                            } catch (IllegalStateException e) {
                                afficherAlerte("Erreur", "Impossible de supprimer ce versement car il est lié à d'autres données.");
                            }
                        }
                    }
                    
                    
                    
                    //regler le cliquage sur les button ajouter Vehicule 
                    @FXML
                private JFXButton AfficherAjouterDepenceButton;  

                    @FXML
                    private void afficherAjouterDepence() {
                     try {
                    // Charger le fichier FXML de la fenêtre d'ajout de candidat
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AjouterDepence.fxml"));
                    Parent root = loader.load();

                    // Créer un nouveau Stage pour la fenêtre modale
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL); // Définit la modalité
                    stage.initStyle(StageStyle.UNDECORATED); // Facultatif : pour supprimer la barre de titre
                    stage.setTitle("Ajouter une dépence");

                    // Ajouter le contenu chargé au Stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    // Afficher la fenêtre au centre de la fenêtre principale
                    stage.centerOnScreen();

                    // Afficher la fenêtre modale
                    stage.showAndWait(); // Bloque jusqu'à ce que la fenêtre soit fermée

                     initialiserSectionQuantabilite();
                    
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    }
                    
                    
                     @FXML
            private JFXButton AfficherModifierDepence;
                    

                    @FXML
                    private void AfficherModifierDepence() {
                        Depence selecteduser = depenceTable.getSelectionModel().getSelectedItem();

                        if (selecteduser == null) {
                            // Afficher une alerte si aucune ligne n'est sélectionnée
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Avertissement");
                            alert.setHeaderText(null);
                            alert.setContentText("Veuillez sélectionner une dépence pour modifier ses informations.");
                            alert.showAndWait();
                            return;
                        }

                        try {
                            // Charger le fichier FXML de la fenêtre de modification
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modifierDepence.fxml"));
                            Parent root = loader.load();

                            // Obtenir le contrôleur de la fenêtre de modification
                            ModifierDepenceController controller = loader.getController();

                            // Passer les informations du candidat sélectionné au contrôleur
                            controller.initialiserChamps(selecteduser);

                            // Créer un nouveau Stage pour la fenêtre modale
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL); // Fenêtre modale
                            stage.initStyle(StageStyle.UNDECORATED); // Optionnel : retirer la barre de titre
                            stage.setTitle("Modifier une dépence");

                            // Ajouter le contenu chargé au Stage
                            Scene scene = new Scene(root);
                            stage.setScene(scene);

                            // Afficher la fenêtre au centre de l'écran
                            stage.centerOnScreen();
                            stage.showAndWait(); // Bloquer jusqu'à la fermeture

                           initialiserSectionQuantabilite();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    
                     @FXML
                private JFXButton SupprimerDepenceButton;

                    @FXML
                    private void supprimerDepence() {
                        // Vérifier si un utilisateur est sélectionné
                        Depence selecteduser = depenceTable.getSelectionModel().getSelectedItem();
                        if (selecteduser == null) {
                            afficherAlerte("Erreur", "Veuillez sélectionner une dépence à supprimer.");
                            return;
                        }
                        
                        // Demander confirmation à l'utilisateur
                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Confirmation de suppression");
                        confirmation.setHeaderText("Voulez-vous vraiment supprimer cette dépence ?");

                        Optional<ButtonType> result = confirmation.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                        GestionnaireQuantabilite gestionnaire = new GestionnaireQuantabilite();
                            try {
                                boolean success = gestionnaire.supprimerDepence(selecteduser.getId_depence());
                                if (success) {
                                    afficherAlerte("Succès", "La dépence a été supprimée avec succès.");
                                    // Rafraîchir la table après suppression
                                    initialiserSectionQuantabilite();
                                }
                            } catch (IllegalStateException e) {
                                afficherAlerte("Erreur", "Impossible de supprimer cette dépence car il est lié à d'autres données.");
                            }
                        }
                    }
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                                                    //interface inscription
                    
                    
            @FXML
            private TableView<Inscription> InscriptionTable;

            @FXML
            private TableColumn<Inscription, String> InscriptionTableColDate;

            @FXML
            private TableColumn<Inscription, Double> InscriptionTableColMontant;

            @FXML
            private TableColumn<Inscription, String> InscriptionTableColPermis;       
            
            @FXML
            private TableColumn<Inscription, String> InscriptionTableColCondidat; 
                    
                    
                                @FXML
                    private void initialiserSectionInscription(){
                        
                         // Configurer les colonnes
                        InscriptionTableColDate.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));
                        InscriptionTableColCondidat.setCellValueFactory(new PropertyValueFactory<>("nomCondidat"));
                        InscriptionTableColPermis.setCellValueFactory(new PropertyValueFactory<>("typePermis"));
                        InscriptionTableColMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
                        
                        
                        GestionnaireInscription gestionnaireInscription = new GestionnaireInscription();
                        ObservableList<Inscription> inscriptions = gestionnaireInscription.recupererInscription();
                        InscriptionTable.setItems(inscriptions);
                        
                    }
                    
                     //regler le cliquage sur les button ajouter inscription 
                    @FXML
                private JFXButton ajouterInscription;  

                    @FXML
                    private void afficherAjouterInscription() {
                     try {
                    // Charger le fichier FXML de la fenêtre d'ajout d'une inscription
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AjouterInscription.fxml"));
                    Parent root = loader.load();

                    // Créer un nouveau Stage pour la fenêtre modale
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL); // Définit la modalité
                    stage.initStyle(StageStyle.UNDECORATED); // Facultatif : pour supprimer la barre de titre
                    stage.setTitle("Ajouter une inscription");

                    // Ajouter le contenu chargé au Stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    // Afficher la fenêtre au centre de la fenêtre principale
                    stage.centerOnScreen();

                    // Afficher la fenêtre modale
                    stage.showAndWait(); // Bloque jusqu'à ce que la fenêtre soit fermée

                     initialiserSectionInscription();
                    
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    }   
                    
                    
                    
                     @FXML
            private JFXButton AfficherModifierInscription;
                    

                    @FXML
                    private void AfficherModifierInscription() {
                        Inscription selecteduser = InscriptionTable.getSelectionModel().getSelectedItem();

                        if (selecteduser == null) {
                            // Afficher une alerte si aucune ligne n'est sélectionnée
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Avertissement");
                            alert.setHeaderText(null);
                            alert.setContentText("Veuillez sélectionner une inscription pour modifier ses informations.");
                            alert.showAndWait();
                            return;
                        }

                        try {
                            // Charger le fichier FXML de la fenêtre de modification
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modifierInscription.fxml"));
                            Parent root = loader.load();

                            // Obtenir le contrôleur de la fenêtre de modification
                            ModifierInscriptionController controller = loader.getController();

                            // Passer les informations du candidat sélectionné au contrôleur
                            controller.initialiserChamps(selecteduser);

                            // Créer un nouveau Stage pour la fenêtre modale
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL); // Fenêtre modale
                            stage.initStyle(StageStyle.UNDECORATED); // Optionnel : retirer la barre de titre
                            stage.setTitle("Modifier une inscription");

                            // Ajouter le contenu chargé au Stage
                            Scene scene = new Scene(root);
                            stage.setScene(scene);

                            // Afficher la fenêtre au centre de l'écran
                            stage.centerOnScreen();
                            stage.showAndWait(); // Bloquer jusqu'à la fermeture

                           initialiserSectionInscription();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    
                    @FXML
                private JFXButton SupprimerInscriptionButton;

                    @FXML
                    private void supprimerInscription() {
                        // Vérifier si un utilisateur est sélectionné
                        Inscription selecteduser = InscriptionTable.getSelectionModel().getSelectedItem();
                        if (selecteduser == null) {
                            afficherAlerte("Erreur", "Veuillez sélectionner une inscription à supprimer.");
                            return;
                        }
                        
                        // Demander confirmation à l'utilisateur
                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Confirmation de suppression");
                        confirmation.setHeaderText("Voulez-vous vraiment cette inscription ?");

                        Optional<ButtonType> result = confirmation.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                        GestionnaireInscription gestionnaire = new GestionnaireInscription();
                            try {
                                boolean success = gestionnaire.supprimerInscription(selecteduser.getId_inscription());
                                if (success) {
                                    afficherAlerte("Succès", "L'inscription a été supprimé avec succès.");
                                    // Rafraîchir la table après suppression
                                    initialiserSectionInscription();
                                }
                            } catch (IllegalStateException e) {
                                afficherAlerte("Erreur", "Impossible de supprimer cette inscription car il est lié à d'autres données.");
                            }
                        }
                    }
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                                                //interface Seance
            
            @FXML
            private TableView<Seance> seanceTable;

            @FXML
            private TableColumn<Seance, String> seanceTableColDate;

            @FXML
            private TableColumn<Seance, String> seanceTableColHeure;

            @FXML
            private TableColumn<Seance, String> seanceTableColCondidat;       
            
            @FXML
            private TableColumn<Seance, String> seanceTableColMoniteur; 
            
            @FXML
            private TableColumn<Seance, String> seanceTableColepreuve;

            @FXML
            private TableColumn<Seance, String> seanceTableColType;       
            
            @FXML
            private TableColumn<Seance, Double> seanceTableColMontant; 
            
            @FXML
            private TableColumn<Seance, String> seanceTableColPermis;
            
            @FXML
            private JFXDatePicker SeanceDatePicker;
            
            @FXML
            private ComboBox<String> SeanceMoniteur;
            
             @FXML
            private ComboBox<String> SeanceEpreuve;
             
             @FXML
            private ComboBox<String> SeanceType;
             
             @FXML
            private ComboBox<String> SeancePermis;
             
             //remplir l'agenda des seance 
                @FXML
                private Agenda agendaSeance;
                
                @FXML
                private ICalendarAgenda agendaSeance1;
                
                @FXML
                private JFXButton semaineProchaine; 
                
                @FXML
                private JFXButton semainePrecedante; 
                
                 @FXML
                private JFXButton changerTableauAgenda; 
                 
                 @FXML
                private ImageView agendaSeanceFlechePrece;
                
                @FXML
                private ImageView agendaSeanceFlecheProch;
            
             
             @FXML
            private ObservableList<Seance> agendaSeancesList = FXCollections.observableArrayList();
            
                                
                                @FXML
                    private void initialiserSectionSeance() {
                        SeancePermis.setValue(null);
                        SeanceType.setValue(null);
                        SeanceEpreuve.setValue(null);
                        SeanceMoniteur.setValue(null);

                        // Configurer les colonnes
                        seanceTableColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                        seanceTableColHeure.setCellValueFactory(new PropertyValueFactory<>("heure"));
                        seanceTableColCondidat.setCellValueFactory(new PropertyValueFactory<>("nomCondidat"));
                        seanceTableColMoniteur.setCellValueFactory(new PropertyValueFactory<>("nomMoniteur"));
                        seanceTableColepreuve.setCellValueFactory(new PropertyValueFactory<>("epreuve"));
                        seanceTableColType.setCellValueFactory(new PropertyValueFactory<>("type"));
                        seanceTableColMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
                        seanceTableColPermis.setCellValueFactory(new PropertyValueFactory<>("permis"));

                        GestionnaireCours gestionnaireCours = new GestionnaireCours();
                        ObservableList<Seance> seances = gestionnaireCours.recupererSeances();

                        // Configurer le filtre
                        FilteredList<Seance> filteredSeances = new FilteredList<>(seances, p -> true);

                        // Associer les données filtrées à la table
                        seanceTable.setItems(filteredSeances);

                        // Remplir les ComboBox et configurer les filtres
                        GestionnaireMoniteur gestionnaireMoniteur = new GestionnaireMoniteur();
                        ObservableList<Moniteur> listeMoniteurs = gestionnaireMoniteur.recupererMoniteur();
                        SeanceMoniteur.getItems().clear();
                        SeanceMoniteur.getItems().add("Tout"); // Ajouter l'option "Tout"
                        for (Moniteur moniteur : listeMoniteurs) {
                            String nomComplet = moniteur.getNom() + " " + moniteur.getPrenom();
                            SeanceMoniteur.getItems().add(nomComplet);
                        }

                        ObservableList<String> epreuve = FXCollections.observableArrayList("Tout", "code", "crenaux", "circulation");
                        SeanceEpreuve.setItems(epreuve);

                        ObservableList<String> type = FXCollections.observableArrayList("Tout", "normal", "supplementaire", "perfectionnement");
                        SeanceType.setItems(type);

                        ObservableList<String> permis = FXCollections.observableArrayList("Tout", "A1", "A2", "B", "C1", "C2", "D", "E");
                        SeancePermis.setItems(permis);

                        // Configurer les événements de filtrage
                        SeanceMoniteur.valueProperty().addListener((observable, oldValue, newValue) -> filterSeances(filteredSeances));
                        SeanceEpreuve.valueProperty().addListener((observable, oldValue, newValue) -> filterSeances(filteredSeances));
                        SeanceType.valueProperty().addListener((observable, oldValue, newValue) -> filterSeances(filteredSeances));
                        SeancePermis.valueProperty().addListener((observable, oldValue, newValue) -> filterSeances(filteredSeances));
                        SeanceDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> filterSeances(filteredSeances));
                        
                        
                                
                                seanceTable.setVisible(false);
                                semainePrecedante.setVisible(true);
                                semaineProchaine.setVisible(true);
                                agendaSeance.setVisible(true);
                                agendaSeanceFlecheProch.setVisible(true);
                                agendaSeanceFlechePrece.setVisible(true);

                                changerTableauAgenda.setText( "Tableau");
                        
                         
                                remplirICalendarAgenda(agendaSeance, seances);
                                
                                // Désactiver les interactions utilisateur
                                agendaSeance.setAllowDragging(false);
                                agendaSeance.setAllowResize(false);
                                
                               semainePrecedante.setOnAction(e -> agendaSeance.setDisplayedLocalDateTime(
                                    agendaSeance.getDisplayedLocalDateTime().minusWeeks(1)
                                ));
                                semaineProchaine.setOnAction(e -> agendaSeance.setDisplayedLocalDateTime(
                                    agendaSeance.getDisplayedLocalDateTime().plusWeeks(1)
                                ));
                                 
                                LocalDateTime currentDateTime = LocalDateTime.now();
                                agendaSeance.setDisplayedLocalDateTime(currentDateTime); // semaine en cour
                                changerTableauAgenda.setOnAction(event -> toggleVisibility());
                        
                        
                    }
                    
                    
                    
                    

                    // Méthode pour filtrer les séances
                    private void filterSeances(FilteredList<Seance> filteredSeances) {
                        filteredSeances.setPredicate(seance -> {
                            // Vérifier le filtre Moniteur
                            if (!"Tout".equals(SeanceMoniteur.getValue()) && SeanceMoniteur.getValue() != null) {
                                if (!seance.getNomMoniteur().equals(SeanceMoniteur.getValue())) {
                                    return false;
                                }
                            }

                            // Vérifier le filtre Epreuve
                            if (!"Tout".equals(SeanceEpreuve.getValue()) && SeanceEpreuve.getValue() != null) {
                                if (!seance.getEpreuve().equals(SeanceEpreuve.getValue())) {
                                    return false;
                                }
                            }

                            // Vérifier le filtre Type
                            if (!"Tout".equals(SeanceType.getValue()) && SeanceType.getValue() != null) {
                                if (!seance.getType().equals(SeanceType.getValue())) {
                                    return false;
                                }
                            }

                            // Vérifier le filtre Permis
                            if (!"Tout".equals(SeancePermis.getValue()) && SeancePermis.getValue() != null) {
                                if (!seance.getPermis().equals(SeancePermis.getValue())) {
                                    return false;
                                }
                            }

                            // Vérifier le filtre Date
                            if (SeanceDatePicker.getValue() != null) {
                                // Formater la date sélectionnée au format de la table
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                String formattedDate = SeanceDatePicker.getValue().format(formatter);

                                // Comparer avec la date de la séance
                                if (!seance.getDate().equals(formattedDate)) {
                                    return false;
                                }
                            }

                            return true; // Garder cet élément
                        });
                        
                        // Mettre à jour l'agenda
                        updateAgenda(filteredSeances);
                    }
                    
                    
                    private void updateAgenda(FilteredList<Seance> filteredSeances) {
                    agendaSeancesList.clear();
                    agendaSeancesList.addAll(filteredSeances);

                    // Effacer l'agenda actuel avant de le remplir à nouveau
                    agendaSeance.appointments().clear();

                    // Remplir l'agenda avec les séances filtrées
                    remplirICalendarAgenda(agendaSeance, agendaSeancesList);
                }
                    
                    
                    private void remplirICalendarAgenda(Agenda agenda, ObservableList<Seance> seances) {
                        agenda.appointments().clear();
                    seances.forEach(seance -> {
                        // Crée une entrée pour l'agenda
                        Agenda.AppointmentImplLocal entry = new Agenda.AppointmentImplLocal();

                        // Configurer l'entrée avec les informations de la séance
                        entry.setSummary("Séance de " + seance.getType()+
                                            "\nMoniteur : " + seance.getNomMoniteur() +
                                             "\nCandidat : " + seance.getNomCondidat() +
                                             "\nMontant : " + seance.getMontant() +
                                             "\nÉpreuve : " + seance.getEpreuve());


                        // Conversion de la date et de l'heure en objets LocalDateTime
                        LocalDate date = LocalDate.parse(seance.getDate());
                        LocalTime time = LocalTime.parse(seance.getHeure());

                        // Définir les plages horaires de l'événement
                        entry.setStartLocalDateTime(LocalDateTime.of(date, time));
                        entry.setEndLocalDateTime(LocalDateTime.of(date, time.plusMinutes(30))); // Exemple de durée de 1 heure

                        agenda.appointments().add(entry);
                    });
                }

        
                    private void toggleVisibility() {
                        boolean isTableVisible = seanceTable.isVisible();
                        updateVisibility(!isTableVisible);
                    }

                    private void updateVisibility(boolean showTable) {
                        seanceTable.setVisible(showTable);
                        semainePrecedante.setVisible(!showTable);
                        semaineProchaine.setVisible(!showTable);
                        agendaSeance.setVisible(!showTable);
                        agendaSeanceFlecheProch.setVisible(!showTable);
                        agendaSeanceFlechePrece.setVisible(!showTable);

                        changerTableauAgenda.setText(showTable ? "Agenda" : "Tableau");
                    }    
                    
                    
                     //regler le cliquage sur les button ajouter seance 
                    @FXML
                private JFXButton ajouterSeance;  

                    @FXML
                    private void afficherAjouterSeance() {
                     try {
                    // Charger le fichier FXML de la fenêtre d'ajout de seance
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AjouterSeance.fxml"));
                    Parent root = loader.load();

                    // Créer un nouveau Stage pour la fenêtre modale
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL); // Définit la modalité
                    stage.initStyle(StageStyle.UNDECORATED); // Facultatif : pour supprimer la barre de titre
                    stage.setTitle("Ajouter une seance");

                    // Ajouter le contenu chargé au Stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    // Afficher la fenêtre au centre de la fenêtre principale
                    stage.centerOnScreen();

                    // Afficher la fenêtre modale
                    stage.showAndWait(); // Bloque jusqu'à ce que la fenêtre soit fermée

                     initialiserSectionSeance();
                    
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    } 
                    
                    
                    
                    @FXML
            private JFXButton AfficherModifierSeance;
                    

                    @FXML
                    private void AfficherModifierSeance() {
                        Seance selecteduser = seanceTable.getSelectionModel().getSelectedItem();

                        if (selecteduser == null) {
                            // Afficher une alerte si aucune ligne n'est sélectionnée
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Avertissement");
                            alert.setHeaderText(null);
                            alert.setContentText("Veuillez sélectionner une seance pour modifier ses informations.");
                            alert.showAndWait();
                            return;
                        }

                        try {
                            // Charger le fichier FXML de la fenêtre de modification
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modifierSeance.fxml"));
                            Parent root = loader.load();

                            // Obtenir le contrôleur de la fenêtre de modification
                            ModifierSeanceController controller = loader.getController();

                            // Passer les informations du candidat sélectionné au contrôleur
                            controller.initialiserChamps(selecteduser);

                            // Créer un nouveau Stage pour la fenêtre modale
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL); // Fenêtre modale
                            stage.initStyle(StageStyle.UNDECORATED); // Optionnel : retirer la barre de titre
                            stage.setTitle("Modifier une seance");

                            // Ajouter le contenu chargé au Stage
                            Scene scene = new Scene(root);
                            stage.setScene(scene);

                            // Afficher la fenêtre au centre de l'écran
                            stage.centerOnScreen();
                            stage.showAndWait(); // Bloquer jusqu'à la fermeture

                           initialiserSectionSeance();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    
                     @FXML
                private JFXButton SupprimerSeanceButton;

                    @FXML
                    private void supprimerSeance() {
                        // Vérifier si un utilisateur est sélectionné
                        Seance selecteduser = seanceTable.getSelectionModel().getSelectedItem();
                        if (selecteduser == null) {
                            afficherAlerte("Erreur", "Veuillez sélectionner une seance à supprimer.");
                            return;
                        }
                        
                        // Demander confirmation à l'utilisateur
                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Confirmation de suppression");
                        confirmation.setHeaderText("Voulez-vous vraiment supprimer cette seance ?");

                        Optional<ButtonType> result = confirmation.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                        GestionnaireCours gestionnaire = new GestionnaireCours();
                            try {
                                boolean success = gestionnaire.supprimerSeance(selecteduser.getId_seance());
                                if (success) {
                                    afficherAlerte("Succès", "La seance a été supprimé avec succès.");
                                    // Rafraîchir la table après suppression
                                    initialiserSectionSeance();
                                }
                            } catch (IllegalStateException e) {
                                afficherAlerte("Erreur", "Impossible de supprimer cette seance car elle est lié à d'autres données.");
                            }
                        }
                    }
                    
                    
                    //regler le cliquage sur les button ajouter seance code 
                    @FXML
                private JFXButton ajouterCondidatAuSeance;  

                    @FXML
                    private void ajouterCondidatAuSeance() {
                    Seance selecteduser = seanceTable.getSelectionModel().getSelectedItem();

                        if (selecteduser == null) {
                            // Afficher une alerte si aucune ligne n'est sélectionnée
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Avertissement");
                            alert.setHeaderText(null);
                            alert.setContentText("Veuillez sélectionner une seance de code pour l'ajouter un candidat.");
                            alert.showAndWait();
                            return;
                        }
                        
                        if (!"code".equals(selecteduser.getEpreuve())) {
                            // Afficher une alerte si aucune ligne n'est sélectionnée
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Avertissement");
                            alert.setHeaderText(null);
                            alert.setContentText("Veuillez sélectionner une seance de code.");
                            alert.showAndWait();
                            return;
                        }

                        try {
                            // Charger le fichier FXML de la fenêtre de modification
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AjouterCondidatAuSeanceCode.fxml"));
                            Parent root = loader.load();

                            // Obtenir le contrôleur de la fenêtre de modification
                            AjouterCondidatAuSeanceCodeController controller = loader.getController();

                            // Passer les informations du candidat sélectionné au contrôleur
                            controller.initialiserChamps(selecteduser);

                            // Créer un nouveau Stage pour la fenêtre modale
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL); // Fenêtre modale
                            stage.initStyle(StageStyle.UNDECORATED); // Optionnel : retirer la barre de titre
                            stage.setTitle("Ajouter candidat a la seance");

                            // Ajouter le contenu chargé au Stage
                            Scene scene = new Scene(root);
                            stage.setScene(scene);

                            // Afficher la fenêtre au centre de l'écran
                            stage.centerOnScreen();
                            stage.showAndWait(); // Bloquer jusqu'à la fermeture

                           initialiserSectionSeance();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } 
                    
                    
                     
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                                                //interface examen 
                    
            @FXML
            private TableView<Examen> tableExamen;

            @FXML
            private TableColumn<Examen, String> tableExamenColDate;

            @FXML
            private TableColumn<Examen, String> tableExamenColExaminateur;

            @FXML
            private TableColumn<Examen, Integer> tableExamenColNbrCondidat;            
            
            
            @FXML
            private TableView<ExamenCondidatExamine> tableCondidatExamine;

            @FXML
            private TableColumn<ExamenCondidatExamine, String> tableCondidatExamineNomCondidat;

            @FXML
            private TableColumn<ExamenCondidatExamine, String> tableCondidatExamineEpreuve;
            
            @FXML
            private TableColumn<ExamenCondidatExamine, String> tableCondidatExaminePermis;

            @FXML
            private TableColumn<ExamenCondidatExamine, String> tableCondidatExamineResultat;
            
            @FXML
            private VBox examenSectionParDefaut;
            
            @FXML
            private VBox examenInfoSection;
            
            private StringBuilder contenu = new StringBuilder();

                    
                    
                                @FXML
                    private void initialiserSectionExamen() {
                        
                        
                        
                        examenInfoSection.setVisible(false);
                        examenSectionParDefaut.setVisible(true);

                        // Configurer les colonnes
                        tableExamenColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                        tableExamenColExaminateur.setCellValueFactory(new PropertyValueFactory<>("nomExaminateur"));
                        tableExamenColNbrCondidat.setCellValueFactory(new PropertyValueFactory<>("nbrCondidat"));
                    
                        GestionnaireExamen gestionnaireExamen = new GestionnaireExamen();
                        ObservableList<Examen> examens = gestionnaireExamen.recupererExamen();
                        
                        
                        tableExamen.setItems(examens);
                        
                        
                        tableExamen.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                                    if (newValue != null) {
                                        
                                        contenu.setLength(0);
                                        
                                        //afficher la section info de l'examen 
                                        if ( examenSectionParDefaut.isVisible()){
                                            examenSectionParDefaut.setVisible(false);
                                             examenInfoSection.setVisible(true);
                                        }

                                        

                                        //afficher la liste des candidat concerné par l'examen
                                                afficherCondidatExamine(newValue.getIdExamen());
                                                afficherMoniteurEncadrant(newValue.getIdExamen());
                                                afficherVehiculeExamen(newValue);
                                        
                                        }});
                        
                        
                        imprimerListeCondidatExamine.setOnAction(event -> imprimerListeCondidatExamine());
                    }
                    
                    
                    @FXML
                private JFXButton imprimerListeCondidatExamine; 
                    
                    
                    @FXML
                    private void afficherCondidatExamine(int id){
                        // Configurer les colonnes
                        tableCondidatExamineNomCondidat.setCellValueFactory(new PropertyValueFactory<>("nomCondidat"));
                        tableCondidatExamineEpreuve.setCellValueFactory(new PropertyValueFactory<>("epreuve"));
                        tableCondidatExaminePermis.setCellValueFactory(new PropertyValueFactory<>("permis"));
                        tableCondidatExamineResultat.setCellValueFactory(new PropertyValueFactory<>("resultat"));
                        
                        GestionnaireExamen gestionnaireExamen = new GestionnaireExamen();
                        ObservableList<ExamenCondidatExamine> condidatExamine = gestionnaireExamen.recupererCandidatsExamine(id);
                        
                        tableCondidatExamine.setItems(condidatExamine);
                        
                        
                        
                    }
                    
                    private ObservableList<ExamenMoniteurEncadrant> moniteurEncadrant ;
                    
                    private String vehiculeUtiliseExamen;
                    
                    @FXML
                    private Text moniteurEncadrantList;
                    
                    
                     GestionnaireValeurParDefaut gestionnaireValeurParDefaut = new GestionnaireValeurParDefaut();      
                    
        
        
                    
                    
                    @FXML
                    private void afficherMoniteurEncadrant(int id){
                        
                        GestionnaireExamen gestionnaireExamen = new GestionnaireExamen();
                        moniteurEncadrant = gestionnaireExamen.recupererMoniteursEncadrant(id);
                        
                        // Construction de la chaîne de caractères au format demandé
                     contenu.append("Moniteur Encadrant :");
                    for (ExamenMoniteurEncadrant moniteur : moniteurEncadrant) {
                        contenu.append("\t").append(moniteur.getNomMoniteur()).append("\n");
                    }

                    // Affichage du contenu dans le champ Text
                    moniteurEncadrantList.setText(contenu.toString());
    
                    }
                    
                    @FXML
                    private void afficherVehiculeExamen(Examen examen){
                        
                        vehiculeUtiliseExamen = examen.getVehicule();
                        contenu.append("Voiture :\t"+examen.getVehicule());
                    // Affichage du contenu dans le champ Text
                    moniteurEncadrantList.setText(contenu.toString());
    
                    }
                    
                    
                    
                    
                    private int nbrExamenCode;                   
                    private int nbrExamenCrenaux; 
                    private int nbrExamenCirculation; 
                    
                    
                    //donner l'ordre de l'impression
                   @FXML
                   private void imprimerListeCondidatExamine() {
                       Printer printer = Printer.getDefaultPrinter();
                       PrinterJob job = PrinterJob.createPrinterJob();

                       if (job != null && printer != null) {
                           // Page Layout avec marges précises (~10 pixels)
                           PageLayout pageLayout = printer.createPageLayout(
                               Paper.A4, 
                               PageOrientation.PORTRAIT,
                               4, 4, 4, 4 // Marges gauche, droite, haut, bas en millimètres
                           );

                           job.getJobSettings().setPageLayout(pageLayout);

                           // Première page : Liste complète des candidats
                           Node printableNode = creerTableAvecGridPane();
                           boolean proceed = job.showPrintDialog(tableCondidatExamine.getScene().getWindow());
                           if (proceed) {
                               boolean printed = job.printPage(printableNode);
                               if (printed) {
                                   // Ajoutez un saut de page pour commencer la deuxième page
                                   job.getJobSettings().setPageLayout(pageLayout);
                                   job.printPage(creerTableSansNumInscription());
                                   job.endJob();
                               } else {
                                   afficherAlerte("Erreur", "Erreur lors de l'impression.");
                                   
                               }
                           }
                       }
                   }
                   

                    // Crée une version imprimable de la feuille avec des éléments structurés
                    private Node creerTableAvecGridPane() {
                        VBox container = new VBox(2);
                        container.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
                        container.setPrefWidth(587); // Largeur préférée (ou 100% de la largeur disponible)
                        container.setPrefHeight(822); // Hauteur préférée (ou 100% de la hauteur disponible)
                        container.setPadding(new javafx.geometry.Insets(4,0,4,4));
                         container.setAlignment(Pos.TOP_CENTER);


                        // Section 1: En-tête
                        HBox headerHBox = new HBox();
                        headerHBox.setAlignment(Pos.CENTER);
                        Label headerLabel = new Label("الجمهورية الجزائرية الديموقراطية الشعبية\nوزارة الداخلية و الجماعات المحلية و التهيئة العمرانية");
                        headerLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
                        headerLabel.setAlignment(Pos.CENTER);
                        headerLabel.setTextAlignment(TextAlignment.CENTER);
                        headerHBox.getChildren().addAll(headerLabel);


                        // Ligne suivante avec rectangle vide et texte à droite
                        HBox headerRow = new HBox();
                        HBox headerRowChild1 = new HBox();
                        HBox headerRowChild2 = new HBox();

                        headerRowChild1.setAlignment(Pos.CENTER_LEFT);
                        headerRowChild2.setAlignment(Pos.TOP_RIGHT);

                        Region rect = new Region();
                        rect.setPrefSize(200, 100);
                        rect.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
                        headerRowChild1.getChildren().addAll(rect);

                        Label phraseDroite = new Label("المندوبية الوطنية للامن في الطرق\nالمندوبية الولائية للامن في الطرق لولاية بجاية");
                        phraseDroite.setStyle("-fx-font-size: 10px; -fx-border-color: black; -fx-border-width: 1px;");
                        phraseDroite.setTextAlignment(TextAlignment.RIGHT);
                        phraseDroite.setPadding(new javafx.geometry.Insets(2));
                        headerRowChild2.getChildren().addAll( phraseDroite);

                        headerRow.getChildren().addAll(headerRowChild1, headerRowChild2);
                        HBox.setHgrow(headerRowChild1, Priority.ALWAYS); // Pas nécessaire ici, mais pour clarifier
                        HBox.setHgrow(headerRowChild2, Priority.ALWAYS); // Pas nécessaire ici non plus


                        //hbox pour le lieu et la date de l'examen 
                        ValeurParDefaut valeur = gestionnaireValeurParDefaut.recupererValeurParDefaut();
                        String centreExamen = valeur.getCentreExamen();


                        HBox headerExamenInfo = new HBox();
                        headerExamenInfo.setAlignment(Pos.CENTER_RIGHT);
                        Label phraseDroite2 = new Label("مركز الامتحان:\t"+centreExamen+" \t\tتاريخ الإيداع:\t\t\t\tتاريخ الإمتحان :\t\t\t\t\t\t\nلقب و إسم المفتش: ...........................................");
                        phraseDroite2.setStyle("-fx-font-size: 13px; -fx-border-color: black; -fx-border-width: 1px;  -fx-font-weight: bold;");
                        phraseDroite2.setTextAlignment(TextAlignment.RIGHT);
                        phraseDroite2.setPadding(new javafx.geometry.Insets(1));
                        HBox.setHgrow(phraseDroite2, Priority.ALWAYS); // Pas nécessaire ici non plus
                        headerExamenInfo.getChildren().addAll( phraseDroite2);



                        // Section 2: Titre
                        Label titleLabel = new Label("قائمة المترشحين لامتحان رخصة السياقة");
                        titleLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 1px;");
                        titleLabel.setPadding(new javafx.geometry.Insets(1));
                        titleLabel.setAlignment(Pos.CENTER);


                        // Section 3: Tableau principal
                        HBox sectionPrincipale = new HBox();
                        GridPane mainTable = creerTablePrincipal();

                        StringBuilder contenu = new StringBuilder("ألمركبة الأولى:\n");
                        contenu.append(vehiculeUtiliseExamen+"\n\n");

                        contenu.append("إسم و لقب الممرن:");
                        for (ExamenMoniteurEncadrant moniteur : moniteurEncadrant) {
                        String nomAvecSautDeLigne = moniteur.getNomMoniteur().replace(" ", "\n");
                        contenu.append(nomAvecSautDeLigne);
                        }  

                        Label infoSup = new Label(contenu.toString());
                        infoSup.setWrapText(true);
                        infoSup.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 2px;");
                        infoSup.setTextAlignment(TextAlignment.RIGHT);

                        // Ajouter une marge de 15 pixels en haut
                        HBox.setMargin(infoSup, new javafx.geometry.Insets(15, 0, 0, 0));


                        sectionPrincipale.getChildren().addAll( mainTable, infoSup);


                        // Section 4: Tableau des statistiques
                        GridPane statsTable = creerTableStatistiques();

                        // Section 5: Rectangle vide à la fin
                        HBox rectFin = new HBox();
                        rectFin.setAlignment(Pos.TOP_LEFT);

                        Label rectFinLabel = new Label("ختم و إمضاء المفتش");
                        rectFinLabel.setStyle("-fx-font-size: 7px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 1px;");
                        rectFinLabel.setPadding(new javafx.geometry.Insets(4, 4, 90, 150));

                        rectFin.getChildren().addAll( rectFinLabel);
                        VBox.setVgrow(rectFin, Priority.ALWAYS); // Pas nécessaire ici non plus



                        container.getChildren().addAll(headerHBox, headerRow, titleLabel, headerExamenInfo,  sectionPrincipale, statsTable, rectFin);
                        return container;
                    }
                    
                    
                    
                    //creation de la liste des candidats examine

                private GridPane creerTablePrincipal() {
                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(0); // Espacement horizontal entre les colonnes
                    gridPane.setVgap(0); // Espacement vertical entre les lignes

                    // Définir le sens de l'orientation de la grille
                    gridPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                    // Définir les contraintes de colonnes
                    ColumnConstraints col0 = new ColumnConstraints();
                    col0.setMinWidth(35); // Largeur minimale pour "num ligne"

                    ColumnConstraints col1 = new ColumnConstraints();
                    col1.setMinWidth(65); // Largeur minimale pour "num condidat"

                    ColumnConstraints col2 = new ColumnConstraints();
                    col2.setMinWidth(120); // Largeur minimale pour "Nom Candidat"

                    ColumnConstraints col3 = new ColumnConstraints();
                    col3.setMinWidth(60); // Largeur minimale pour "date naissance Candidat"

                    ColumnConstraints col4 = new ColumnConstraints();
                    col4.setMinWidth(40); // Largeur minimale pour "Permis"

                    ColumnConstraints col5 = new ColumnConstraints();
                    col5.setMinWidth(70); // Largeur minimale pour "Épreuve"

                    ColumnConstraints col6 = new ColumnConstraints();
                    col6.setMinWidth(80); // Largeur minimale pour autre date examen

                    ColumnConstraints col7 = new ColumnConstraints();
                    col7.setMinWidth(60); // Largeur minimale pour "Résultat"

                    gridPane.getColumnConstraints().addAll(col0, col1, col2, col3, col4, col5, col6, col7);

                    // Ajouter les en-têtes de colonnes
                    Label numLigne = new Label("الرقم");
                    Label numCondidat = new Label("رقم التسجيل");
                    Label nomCandidatHeader = new Label("اللقب و الإسم");
                    Label dateNaissCondidat = new Label("تاريخ الميلاد");
                    Label permisHeader = new Label("الصنف");
                    Label epreuveHeader = new Label("طبيعة الإمتحان");
                    Label autreDateExamen = new Label("تاريخ آخر للإمتحان");
                    Label resultatHeader = new Label("النتيجة");

                    gridPane.add(numLigne, 0, 0);
                    gridPane.add(numCondidat, 1, 0);
                    gridPane.add(nomCandidatHeader, 2, 0);
                    gridPane.add(dateNaissCondidat, 3, 0);
                    gridPane.add(permisHeader, 4, 0);
                    gridPane.add(epreuveHeader, 5, 0);
                    gridPane.add(autreDateExamen, 6, 0);
                    gridPane.add(resultatHeader, 7, 0);

                    // Ajouter les données des candidats
                    ObservableList<ExamenCondidatExamine> candidats = tableCondidatExamine.getItems();
                    for (int i = 0; i < candidats.size(); i++) {
                        ExamenCondidatExamine candidat = candidats.get(i);

                        if ("code".equals(candidat.getEpreuve()))
                            nbrExamenCode++;
                        else if ("crenaux".equals(candidat.getEpreuve()))
                            nbrExamenCrenaux++;
                        else if ("circulation".equals(candidat.getEpreuve()))
                            nbrExamenCirculation++;
                        
                        String typePermis = traduireCategorie(candidat.getPermis());
                        String typeEpreuve = traduireTypeEpreuve(candidat.getEpreuve());

                        Label numLigneLabel = new Label(""+(i+1));
                        numLigneLabel.setMinWidth(35); // Largeur minimale pour la cellule "Nun ligne"
                        gridPane.add(numLigneLabel, 0, i + 1);

                        Label numCondidatlabel = new Label(""+candidat.getNumCondidat());
                        numCondidatlabel.setMinWidth(65); // Largeur minimale pour la cellule "Num condidat"
                        gridPane.add(numCondidatlabel, 1, i + 1);

                        Label nomLabel = new Label(candidat.getNomCondidat());
                        nomLabel.setMinWidth(120); // Largeur minimale pour la cellule "Nom Candidat"
                        gridPane.add(nomLabel, 2, i + 1);

                        Label dateNaissanceLabel = new Label(candidat.getDateNaissance());
                        dateNaissanceLabel.setMinWidth(60); // Largeur minimale pour la cellule "date naissance Candidat"
                        gridPane.add(dateNaissanceLabel, 3, i + 1);

                        Label permisLabel = new Label(typePermis);
                        permisLabel.setMinWidth(40); // Largeur minimale pour la cellule "Permis"
                        gridPane.add(permisLabel, 4, i + 1);

                        Label epreuveLabel = new Label(typeEpreuve);
                        epreuveLabel.setMinWidth(70); // Largeur minimale pour la cellule "Épreuve"
                        gridPane.add(epreuveLabel, 5, i + 1);

                        Label autreDateLabel = new Label("");
                        autreDateLabel.setMinWidth(80); // Largeur minimale pour la cellule "autre date"
                        gridPane.add(autreDateLabel, 6, i + 1);

                        Label resultatLabel = new Label("");
                        resultatLabel.setMinWidth(60); // Largeur minimale pour la cellule "Résultat"
                        gridPane.add(resultatLabel, 7, i + 1);
                    }

                    // Appliquer un style aux cellules
                    for (Node node : gridPane.getChildren()) {
                        if (node instanceof Label) {
                            node.setStyle("-fx-font-size: 10px; -fx-border-color: black; -fx-padding: 2px; -fx-alignment: center; -fx-border-width: 0.4px;");
                        }
                    }

                    // Appliquer un style aux en-têtes
                    numLigne.setStyle("-fx-min-width: 35px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");
                    numCondidat.setStyle("-fx-min-width: 65px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");
                    nomCandidatHeader.setStyle("-fx-min-width: 120px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");
                    dateNaissCondidat.setStyle("-fx-min-width: 60px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");
                    permisHeader.setStyle("-fx-min-width: 40px;-fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");
                    epreuveHeader.setStyle("-fx-min-width: 70px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");
                    autreDateExamen.setStyle("-fx-min-width: 80px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");
                    resultatHeader.setStyle("-fx-min-width: 60px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");

                    return gridPane;
                }
                
                
                
                // Cette méthode retourne l'équivalent en arabe de la catégorie de permis en français.
                private String traduireCategorie(String categorieFr) {
                    switch (categorieFr.toLowerCase()) {
                        case "a1":
                            return "أ1";
                        case "a2":
                            return "أ2";
                        case "b":
                            return "ب";
                        case "c":
                            return "ج";
                        case "d":
                            return "د";
                        case "e":
                            return "هـ";
                        case "f":
                            return "و";
                        case "g":
                            return "ز";
                        case "h":
                            return "ح";
                        default:
                            return "Catégorie inconnue";
                    }
                }
                
                
                
                // Cette méthode retourne l'équivalent en arabe de type d'epreuve en français.
                private String traduireTypeEpreuve(String epreuveFr) {
                    switch (epreuveFr.toLowerCase()) {
                        case "code":
                            return "قانون المرور";
                        case "crenaux":
                            return "المناورات";
                        case "circulation":
                            return "السياقة";
                        default:
                            return "type inconnue";
                    }
                }
                
                
                

                    //creation de la table des statistique ( nbr condidat examine...etc)
                    private GridPane creerTableStatistiques() {
                       GridPane gridPane = new GridPane();
                        gridPane.setHgap(0); // Espacement horizontal entre les colonnes
                        gridPane.setVgap(0); // Espacement vertical entre les lignes

                        // Définir le sens de l'orientation de la grille
                        gridPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                        // Définir les contraintes de colonnes
                        ColumnConstraints col0 = new ColumnConstraints();
                        col0.setMinWidth(80); // Largeur minimale pour "epreuve"

                        ColumnConstraints col1 = new ColumnConstraints();
                        col1.setMinWidth(120); // Largeur minimale pour "nbr condidat"

                        ColumnConstraints col2 = new ColumnConstraints();
                        col2.setMinWidth(120); // Largeur minimale pour "nbr Candidat reussi"

                        gridPane.getColumnConstraints().addAll(col0, col1, col2);

                        // Ajouter les en-têtes de colonnes
                        Label epreuve = new Label("طبيعة الإمتحان");
                        Label nbrCondidat = new Label("عدد المترشحين الممتحنين");
                        Label nbrCandidatReussi = new Label("عدد المترشحين الناجحين");

                        gridPane.add(epreuve, 0, 0);
                        gridPane.add(nbrCondidat, 1, 0);
                        gridPane.add(nbrCandidatReussi, 2, 0);

                        //la 1ere ligne
                        Label examenCodeLabel = new Label("قانون المرور");
                        examenCodeLabel.setMinWidth(80); 
                        gridPane.add(examenCodeLabel, 0, 1);

                        Label nbrexamenCodeLabel = new Label(""+nbrExamenCode);
                        nbrexamenCodeLabel.setMinWidth(120); 
                        gridPane.add(nbrexamenCodeLabel, 1, 1);

                        Label nbrexamenCodeReussiLabel = new Label("");
                        nbrexamenCodeReussiLabel.setMinWidth(120); 
                        gridPane.add(nbrexamenCodeReussiLabel, 2, 1);

                        //la 2ere ligne
                        Label examenCrenauxLabel = new Label("المناورات");
                        examenCrenauxLabel.setMinWidth(80); 
                        gridPane.add(examenCrenauxLabel, 0, 2);

                        Label nbrexamenCrenauxLabel = new Label(""+nbrExamenCrenaux);
                        nbrexamenCrenauxLabel.setMinWidth(120); 
                        gridPane.add(nbrexamenCrenauxLabel, 1, 2);

                        Label nbrexamenCrenauxReussiLabel = new Label("");
                        nbrexamenCrenauxReussiLabel.setMinWidth(120); 
                        gridPane.add(nbrexamenCrenauxReussiLabel, 2, 2);

                        //la 3ere ligne
                        Label examenCirculationLabel = new Label("السياقة");
                        examenCirculationLabel.setMinWidth(80); 
                        gridPane.add(examenCirculationLabel, 0, 3);

                        Label nbrexamenCirculationLabel = new Label(""+nbrExamenCirculation);
                        nbrexamenCirculationLabel.setMinWidth(120); 
                        gridPane.add(nbrexamenCirculationLabel, 1, 3);

                        Label nbrexamenCirculationReussiLabel = new Label("");
                        nbrexamenCirculationReussiLabel.setMinWidth(120); 
                        gridPane.add(nbrexamenCirculationReussiLabel, 2, 3);

                        //la 4ere ligne
                        Label nbrTotalLabel = new Label("المجموع");
                        nbrTotalLabel.setMinWidth(80); 
                        gridPane.add(nbrTotalLabel, 0, 4);

                        Label nbrexamenTotalLabel = new Label(""+(nbrExamenCode+nbrExamenCirculation+nbrExamenCrenaux));
                        nbrexamenTotalLabel.setMinWidth(120); 
                        gridPane.add(nbrexamenTotalLabel, 1, 4);

                        Label nbrexamenTotalReussiLabel = new Label("");
                        nbrexamenTotalReussiLabel.setMinWidth(120); 
                        gridPane.add(nbrexamenTotalReussiLabel, 2, 4);

                        // Appliquer un style aux cellules
                        for (Node node : gridPane.getChildren()) {
                            if (node instanceof Label) {
                                node.setStyle("-fx-font-size: 10px; -fx-border-color: black; -fx-padding: 2px; -fx-alignment: center; -fx-border-width: 0.4px;");
                            }
                        }

                        // Appliquer un style aux en-têtes
                        epreuve.setStyle("-fx-min-width: 80px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");
                        nbrCondidat.setStyle("-fx-min-width: 120px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");
                        nbrCandidatReussi.setStyle("-fx-min-width: 120px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");

                        return gridPane;
                    }








// Crée une version imprimable avec les candidats qui n'ont pas de numéro d'inscription
private Node creerTableSansNumInscription() {
    VBox container = new VBox(2);
    container.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
    container.setPrefWidth(587); // Largeur préférée (ou 100% de la largeur disponible)
    container.setPrefHeight(822); // Hauteur préférée (ou 100% de la hauteur disponible)
    container.setPadding(new javafx.geometry.Insets(4,0,4,4));
    container.setAlignment(Pos.TOP_CENTER);

    // Section 1: En-tête
    HBox headerHBox = new HBox();
    headerHBox.setAlignment(Pos.CENTER);
    Label headerLabel = new Label("ملف إرسال");
    headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    headerLabel.setAlignment(Pos.CENTER);
    headerLabel.setTextAlignment(TextAlignment.CENTER);
    headerHBox.getChildren().addAll(headerLabel);
    
    
    // Ligne suivante avec rectangle vide et texte à droite
    HBox headerRow = new HBox();
    HBox headerRowChild1 = new HBox();
    HBox headerRowChild2 = new HBox();
    
    headerRowChild1.setAlignment(Pos.CENTER_LEFT);
    headerRowChild2.setAlignment(Pos.TOP_RIGHT);

    Region rect = new Region();
    rect.setPrefSize(200, 100);
    rect.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
    headerRowChild1.getChildren().addAll(rect);

    Label phraseDroite = new Label("مدرسة تعليم السياقة\nالقصر ولاية بجاية");
    phraseDroite.setStyle("-fx-font-size: 10px;");
    phraseDroite.setTextAlignment(TextAlignment.RIGHT);
    phraseDroite.setPadding(new javafx.geometry.Insets(2));
    headerRowChild2.getChildren().addAll( phraseDroite);
    
    headerRow.getChildren().addAll(headerRowChild1, headerRowChild2);
    HBox.setHgrow(headerRowChild1, Priority.ALWAYS); // Pas nécessaire ici, mais pour clarifier
    HBox.setHgrow(headerRowChild2, Priority.ALWAYS); // Pas nécessaire ici non plus
    
    
    
    
    //hbox pour le lieu et la date de l'examen 
    ValeurParDefaut valeur = gestionnaireValeurParDefaut.recupererValeurParDefaut();
    String centreExamen = valeur.getCentreExamen();
    
    
    HBox headerDocInfo = new HBox();
    headerDocInfo.setAlignment(Pos.CENTER_RIGHT);
    Label phraseDroite2 = new Label(centreExamen+" في:");
    phraseDroite2.setStyle("-fx-font-size: 10px;");
    phraseDroite2.setTextAlignment(TextAlignment.RIGHT);
    phraseDroite2.setPadding(new javafx.geometry.Insets(1));
    HBox.setHgrow(phraseDroite2, Priority.ALWAYS); // Pas nécessaire ici non plus
    headerDocInfo.getChildren().addAll( phraseDroite2);

    HBox headerDocInfo2 = new HBox();
    headerDocInfo2.setAlignment(Pos.CENTER_RIGHT);
    Label phraseDroite3 = new Label("إلى السيد رئيس المندوبية الولائية للأمن في الطرق لولاية بجاية");
    phraseDroite3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
    phraseDroite3.setTextAlignment(TextAlignment.RIGHT);
    phraseDroite3.setPadding(new javafx.geometry.Insets(1,10,1,1));
    HBox.setHgrow(phraseDroite3, Priority.ALWAYS); // Pas nécessaire ici non plus
    headerDocInfo2.getChildren().addAll( phraseDroite3);
    
    
    HBox headerDocInfo3 = new HBox();
    headerDocInfo3.setAlignment(Pos.CENTER_RIGHT);
    Label phraseDroite4 = new Label("يرجى الإطلاع على ملفات رخصة السياقة للمترشحين المرفقة");
    phraseDroite4.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
    phraseDroite4.setTextAlignment(TextAlignment.RIGHT);
    phraseDroite4.setPadding(new javafx.geometry.Insets(1,10,1,1));
    HBox.setHgrow(phraseDroite4, Priority.ALWAYS); // Pas nécessaire ici non plus
    headerDocInfo3.getChildren().addAll( phraseDroite4);
    
    
    
    
    
    
    // Créez le tableau pour les candidats sans numéro d'inscription
    GridPane mainTable = creerTableSansNumInscriptionPrincipal();

    container.getChildren().addAll(headerHBox, headerRow, headerDocInfo, headerDocInfo2,  mainTable);
    return container;
}

// Crée un tableau avec les candidats qui n'ont pas de numéro d'inscription
private GridPane creerTableSansNumInscriptionPrincipal() {
    GridPane gridPane = new GridPane();
    gridPane.setHgap(0); // Espacement horizontal entre les colonnes
    gridPane.setVgap(0); // Espacement vertical entre les lignes
    gridPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

    // Définir les contraintes de colonnes
    ColumnConstraints col0 = new ColumnConstraints();
    col0.setMinWidth(35); // Largeur minimale pour "num ligne"
    
    
    
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setMinWidth(120); // Largeur minimale pour "Nom Candidat"
    
    ColumnConstraints col3 = new ColumnConstraints();
    col3.setMinWidth(60); // Largeur minimale pour "date naissance Candidat"
    
    ColumnConstraints col4 = new ColumnConstraints();
    col4.setMinWidth(40); // Largeur minimale pour "Permis"
    
    ColumnConstraints col5 = new ColumnConstraints();
    col5.setMinWidth(65); // Largeur minimale pour "num condidat"

    gridPane.getColumnConstraints().addAll(col0, col2, col3, col4, col5);

    // Ajouter les en-têtes de colonnes
    Label numLigne = new Label("الرقم");
    Label numCondidat = new Label("الملاحظة");
    Label nomCandidatHeader = new Label("اللقب و الإسم");
    Label dateNaissCondidat = new Label("تاريخ الميلاد");
    Label permisHeader = new Label("الصنف");

    gridPane.add(numLigne, 0, 0);
    
    gridPane.add(nomCandidatHeader, 1, 0);
    gridPane.add(dateNaissCondidat, 2, 0);
    gridPane.add(permisHeader, 3, 0);
    gridPane.add(numCondidat, 4, 0);

    // Ajouter les données des candidats sans numéro d'inscription
    ObservableList<ExamenCondidatExamine> candidats = tableCondidatExamine.getItems();
    int j=0;
    for (int i = 0; i < candidats.size(); i++) {
        ExamenCondidatExamine candidat = candidats.get(i);
        
        if (candidat.getNumCondidat() == null || candidat.getNumCondidat().isEmpty()) {
            Label numLigneLabel = new Label(""+(j+1));
            numLigneLabel.setMinWidth(35); // Largeur minimale pour la cellule "Nun ligne"
            gridPane.add(numLigneLabel, 0, i + 1);
            
            

            Label nomLabel = new Label(candidat.getNomCondidat());
            nomLabel.setMinWidth(120); // Largeur minimale pour la cellule "Nom Candidat"
            gridPane.add(nomLabel, 1, i + 1);
            
            Label dateNaissanceLabel = new Label(candidat.getDateNaissance());
            dateNaissanceLabel.setMinWidth(60); // Largeur minimale pour la cellule "date naissance Candidat"
            gridPane.add(dateNaissanceLabel, 2, i + 1);
            
            Label permisLabel = new Label(candidat.getPermis());
            permisLabel.setMinWidth(40); // Largeur minimale pour la cellule "Permis"
            gridPane.add(permisLabel, 3, i + 1);
            
            Label numCondidatlabel = new Label("");
            numCondidatlabel.setMinWidth(65); // Largeur minimale pour la cellule "Num condidat"
            gridPane.add(numCondidatlabel, 4, i + 1);
            
            j = j+1;
        }
    }

    // Appliquer un style aux cellules
    for (Node node : gridPane.getChildren()) {
        if (node instanceof Label) {
            node.setStyle("-fx-font-size: 10px; -fx-border-color: black; -fx-padding: 2px; -fx-alignment: center; -fx-border-width: 0.4px;");
        }
    }

    // Appliquer un style aux en-têtes
    numLigne.setStyle("-fx-min-width: 35px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");
    numCondidat.setStyle("-fx-min-width: 65px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");
    nomCandidatHeader.setStyle("-fx-min-width: 120px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");
    dateNaissCondidat.setStyle("-fx-min-width: 60px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");
    permisHeader.setStyle("-fx-min-width: 40px;-fx-font-size: 10px; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 0.4px; -fx-alignment: center; -fx-padding: 2px;");

    return gridPane;
}







                    
                    //regler le cliquage sur les button ajouter Examen
                    @FXML
                private JFXButton ajouterExamen;  

                    @FXML
                    private void afficherAjouterExamen() {
                     try {
                    // Charger le fichier FXML de la fenêtre d'ajout de candidat
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AjouterExamen.fxml"));
                    Parent root = loader.load();

                    // Créer un nouveau Stage pour la fenêtre modale
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL); // Définit la modalité
                    stage.initStyle(StageStyle.UNDECORATED); // Facultatif : pour supprimer la barre de titre
                    stage.setTitle("Ajouter un examen");

                    // Ajouter le contenu chargé au Stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    // Afficher la fenêtre au centre de la fenêtre principale
                    stage.centerOnScreen();

                    // Afficher la fenêtre modale
                    stage.showAndWait(); // Bloque jusqu'à ce que la fenêtre soit fermée

                     initialiserSectionExamen();
                    
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    } 
                    
                    
                      @FXML
            private JFXButton AfficherModifierExamen;
                    

                    @FXML
                    private void AfficherModifierExamen() {
                        Examen selecteduser = tableExamen.getSelectionModel().getSelectedItem();

                        if (selecteduser == null) {
                            // Afficher une alerte si aucune ligne n'est sélectionnée
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Avertissement");
                            alert.setHeaderText(null);
                            alert.setContentText("Veuillez sélectionner un examen pour modifier ses informations.");
                            alert.showAndWait();
                            return;
                        }

                        try {
                            // Charger le fichier FXML de la fenêtre de modification
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modofierExamen.fxml"));
                            Parent root = loader.load();

                            // Obtenir le contrôleur de la fenêtre de modification
                            ModofierExamenController controller = loader.getController();

                            // Passer les informations du candidat sélectionné au contrôleur
                            controller.initialiserChamps(selecteduser);

                            // Créer un nouveau Stage pour la fenêtre modale
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL); // Fenêtre modale
                            stage.initStyle(StageStyle.UNDECORATED); // Optionnel : retirer la barre de titre
                            stage.setTitle("Modifier un examen");

                            // Ajouter le contenu chargé au Stage
                            Scene scene = new Scene(root);
                            stage.setScene(scene);

                            // Afficher la fenêtre au centre de l'écran
                            stage.centerOnScreen();
                            stage.showAndWait(); // Bloquer jusqu'à la fermeture

                           initialiserSectionExamen();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    
                     @FXML
                    private void supprimerExamen() {
                        // Vérifier si un utilisateur est sélectionné
                        Examen selectedExamen = tableExamen.getSelectionModel().getSelectedItem();
                        if (selectedExamen == null) {
                            afficherAlerte("Erreur", "Veuillez sélectionner un examen à supprimer.");
                            return;
                        }

                        // Demander confirmation à l'utilisateur
                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Confirmation de suppression");
                        confirmation.setHeaderText("Voulez-vous vraiment supprimer cet examen ?");
                        confirmation.setContentText("Cette action est irréversible.");

                        Optional<ButtonType> result = confirmation.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            GestionnaireExamen gestionnaire = new GestionnaireExamen();
                            try {
                                boolean success = gestionnaire.supprimerExamen(selectedExamen.getIdExamen());
                                if (success) {
                                    afficherAlerte("Succès", "L'examen a été supprimé avec succès.");
                                    // Rafraîchir la table après suppression
                                    initialiserSectionExamen();
                                } else {
                                    afficherAlerte("Erreur", "Une erreur est survenue lors de la suppression de l'examen.");
                                }
                            } catch (IllegalStateException e) {
                                // Afficher une alerte spécifique pour une contrainte de clé étrangère
                                afficherAlerte("Erreur", "Impossible de supprimer cet examen car il est lié à d'autres données, telles que les moniteurs encadrants ou les résultats.");
                            }
                        }
                    }
                    
                    
                     //regler le cliquage sur les button ajouter seance 
                    @FXML
                private JFXButton ajouterCondidatAuExamen;  

                    @FXML
                    private void ajouterCondidatAuExamen() {
                        // Vérifier si un utilisateur est sélectionné
                        Examen selectedExamen = tableExamen.getSelectionModel().getSelectedItem();
                        if (selectedExamen == null) {
                            afficherAlerte("Erreur", "Veuillez sélectionner un examen pour l'ajouter un condidat.");
                            return;
                        }
                        
                        
                        
                        
                        
                     try {
                    // Charger le fichier FXML de la fenêtre d'ajout de candidat
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AjouterCondidatAuExamen.fxml"));
                    Parent root = loader.load();
                    
                     // Obtenir le contrôleur de la fenêtre de modification
                            AjouterCondidatAuExamenController controller = loader.getController();

                            // Passer les informations du candidat sélectionné au contrôleur
                            controller.initialiserChamps(selectedExamen);

                    // Créer un nouveau Stage pour la fenêtre modale
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL); // Définit la modalité
                    stage.initStyle(StageStyle.UNDECORATED); // Facultatif : pour supprimer la barre de titre
                    stage.setTitle("Ajouter un examen");

                    // Ajouter le contenu chargé au Stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    // Afficher la fenêtre au centre de la fenêtre principale
                    stage.centerOnScreen();

                    // Afficher la fenêtre modale
                    stage.showAndWait(); // Bloque jusqu'à ce que la fenêtre soit fermée

                     afficherCondidatExamine(selectedExamen.getIdExamen());
                    
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    } 
                    
                    
                    
                    //regler le cliquage sur les button ajouter seance 
                    @FXML
                private JFXButton modifierCondidatExamine;  

                    @FXML
                    private void modifierCondidatExamine() {
                        // Vérifier si un utilisateur est sélectionné
                        Examen selectedExamen = tableExamen.getSelectionModel().getSelectedItem();
                        
                        
                        // Vérifier si un utilisateur est sélectionné
                        ExamenCondidatExamine selectedCondidatExamen = tableCondidatExamine.getSelectionModel().getSelectedItem();
                        if (selectedCondidatExamen == null) {
                            afficherAlerte("Erreur", "Veuillez sélectionner un condidat pour modifier ces information.");
                            return;
                        }
        
                        
                        try {
                    // Charger le fichier FXML de la fenêtre d'ajout de candidat
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modifierCondidatExamine.fxml"));
                    Parent root = loader.load();
                    
                     // Obtenir le contrôleur de la fenêtre de modification
                            ModifierCondidatExamineController controller = loader.getController();

                            // Passer les informations du candidat sélectionné au contrôleur
                            controller.initialiserChamps(selectedExamen, selectedCondidatExamen);

                    // Créer un nouveau Stage pour la fenêtre modale
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL); // Définit la modalité
                    stage.initStyle(StageStyle.UNDECORATED); // Facultatif : pour supprimer la barre de titre
                    stage.setTitle("modifier condidat examine");

                    // Ajouter le contenu chargé au Stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    // Afficher la fenêtre au centre de la fenêtre principale
                    stage.centerOnScreen();

                    // Afficher la fenêtre modale
                    stage.showAndWait(); // Bloque jusqu'à ce que la fenêtre soit fermée

                     afficherCondidatExamine(selectedExamen.getIdExamen());
                    
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    } 
                    
                    
                    
                     @FXML
                private JFXButton supprimerCondidatExamine;  
                    
                     @FXML
                    private void supprimerCondidatExamine() {
                        
                        Examen selectedExamen = tableExamen.getSelectionModel().getSelectedItem();

                        
                        // Vérifier si un utilisateur est sélectionné
                        // Vérifier si un utilisateur est sélectionné
                        ExamenCondidatExamine selectedCondidatExamen = tableCondidatExamine.getSelectionModel().getSelectedItem();
                        if (selectedCondidatExamen == null) {
                            afficherAlerte("Erreur", "Veuillez sélectionner un condidat pour le supprimer.");
                            return;
                        }

                        // Demander confirmation à l'utilisateur
                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Confirmation de suppression");
                        confirmation.setHeaderText("Voulez-vous vraiment supprimer ce condidat de cette examen ?");
                        confirmation.setContentText("Cette action est irréversible.");

                        Optional<ButtonType> result = confirmation.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            GestionnaireExamen gestionnaire = new GestionnaireExamen();
                            try {
                                boolean success = gestionnaire.supprimerCondidatExamen(selectedExamen.getIdExamen(),selectedCondidatExamen.getIdCondidat() );
                                if (success) {
                                    afficherAlerte("Succès", "Le condidat a été supprimé avec succès.");
                                    // Rafraîchir la table après suppression
                                    afficherCondidatExamine(selectedExamen.getIdExamen());

                                } else {
                                    afficherAlerte("Erreur", "Une erreur est survenue lors de la suppression de condidat de l'examen.");
                                }
                            } catch (IllegalStateException e) {
                                // Afficher une alerte spécifique pour une contrainte de clé étrangère
                                afficherAlerte("Erreur", "Impossible de supprimer cet examen car il est lié à d'autres données, telles que les moniteurs encadrants ou les résultats.");
                            }
                        }
                    }
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                                    //interface parametre 
                    
            @FXML
            private JFXTextField nbrMaxSeanceCrenaux;        
            
            @FXML
            private JFXTextField nbrMaxSeanceCirculation;
            
            @FXML
            private ComboBox<String> permisParDefaut;
            
            @FXML
            private JFXTextField centreExamen;        
            
            @FXML
            private JFXTextField prixSeanceSupplementaire;
            
            @FXML
            private JFXTextField totalVersementParametre;        
            
            @FXML
            private JFXTextField totalDepenseParametre;
            
            @FXML
            private JFXTextField totalCondidat;
            
            @FXML
            private VBox chiffreCleSection;
            
            @FXML
            private VBox aProposSection;
            
            @FXML
            private JFXButton chiffreCleSectionButton;
            
            @FXML
            private JFXButton aProposSectionButton;
            
            
            
                        
                         @FXML
                    private void initialiserSectionParametre() {
                        
                    chiffreCleSectionButton.setStyle("-fx-background-color:   linear-gradient(to top, #c2c3a2, #403B4A);"); // Exemple de changement de fond

                        
                    ValeurParDefaut valeur = gestionnaireValeurParDefaut.recupererValeurParDefaut();
                        
                    int[] heures = gestionnaireValeurParDefaut.recupererNbrHeureMax();
            
                    int[] statistiques = gestionnaireValeurParDefaut.recupererStatistiques();
                        
                         // Remplir la ComboBox "SeancePermis"
                    ObservableList<String> seancePermis = FXCollections.observableArrayList(
                                        "A1", 
                                        "A2", 
                                        "B",
                                        "C", 
                                        "E", 
                                        "D"
                                    );
          
                    permisParDefaut.setItems(seancePermis); 
                        
                        
                        
                       centreExamen.setText(valeur.getCentreExamen());
                       permisParDefaut.setValue(valeur.getPermis());
                       nbrMaxSeanceCrenaux.setText(Integer.toString(heures[0]));
                       nbrMaxSeanceCirculation.setText(Integer.toString(heures[1]));
                       prixSeanceSupplementaire.setText(Double.toString(valeur.getPrisSeanceSupplementaire()));
                       
                       totalCondidat.setText(Integer.toString(statistiques[0]));
                       totalVersementParametre.setText(Double.toString(statistiques[1])+" DA");
                       totalDepenseParametre.setText(Double.toString(statistiques[2])+" DA");

                    }
                    
                    
                    
                   @FXML
    private void mettreAjourEpreuveInfo() {
        // Récupération des valeurs entrées par l'utilisateur
        String maxCrenaux = nbrMaxSeanceCrenaux.getText();
        String maxCirculation = nbrMaxSeanceCirculation.getText();
        String prixSupp = prixSeanceSupplementaire.getText();

        // Vérification que les champs ne sont pas vides
        if (maxCrenaux.isEmpty() || maxCirculation.isEmpty() || prixSupp.isEmpty()) {
            afficherAlerte("Erreur"," Tous les champs doivent être remplis !");
            return;
        }

        try {
            // Conversion des valeurs en double
            int crenaux = Integer.parseInt(maxCrenaux);      
            int circulation = Integer.parseInt(maxCirculation);
            double prix = Double.parseDouble(prixSupp);
            

            // Appel de la méthode de mise à jour
            boolean success = gestionnaireValeurParDefaut.mettreAJourEpreuveInfo(crenaux, circulation, prix);

            if (success) {
                afficherAlerte("Success", "Mise à jour effectuée avec succès !");
                initialiserSectionParametre();
            } else {
                afficherAlerte("Erreur", "Erreur lors de la mise à jour.");
            }

        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "Veuillez entrer des nombres valides !");
        }
    }
    
    
    
        
        @FXML
    private void mettreAjourExamenEtPermisParDefaut() {
        // Récupération des valeurs entrées par l'utilisateur
        String centreExamenDefaut = centreExamen.getText();
        String permis = permisParDefaut.getValue();

        // Vérification que les champs ne sont pas vides
        if (centreExamenDefaut.isEmpty() || permis == null) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        // Appel de la méthode du gestionnaire
        boolean success = gestionnaireValeurParDefaut.mettreAJourExamenEtPermis(centreExamenDefaut, permis);

        if (success) {
            afficherAlerte("Succès", "Les valeurs ont été mises à jour avec succès !");
            initialiserSectionParametre();
        } else {
            afficherAlerte("Erreur", "Une erreur s'est produite lors de la mise à jour.");
        }
    }
    
    
    
                @FXML
                    private void handleSectionParametreClick(Event event) {
                        // Masquer toutes les sections
                        chiffreCleSection.setVisible(false);
                        aProposSection.setVisible(false);
                        

                        // Réinitialiser l'échelle et le fond de tous les boutons (optionnel)
                        resetButtonParametreStyle();

                        // Vérifier quelle source a déclenché l'événement et afficher la section correspondante
                        if (event.getSource() == chiffreCleSectionButton) {
                            chiffreCleSection.setVisible(true);
                            // Appliquer un changement de scale et de fond pour le bouton Accueil
                            chiffreCleSectionButton.setStyle("-fx-background-color:   linear-gradient(to top, #c2c3a2, #403B4A);"); // Exemple de changement de fond
                            
                        } else if (event.getSource() == aProposSectionButton) {
                            aProposSection.setVisible(true);
                            // Appliquer un changement de scale et de fond pour le bouton Condidat
                            aProposSectionButton.setStyle("-fx-background-color:   linear-gradient(to top, #c2c3a2, #403B4A);"); // Exemple de changement de fond
                           
                        } 
                    }
                    
                    @FXML
                     private void resetButtonParametreStyle() {
                        
                        chiffreCleSectionButton.setStyle("-fx-background-color: #929081;");

                        aProposSectionButton.setStyle("-fx-background-color: #929081;");
                        
                     }
               
    
                        
                        
            
            
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
            
                
    
                                                               //Interface Home           
                
    //Recuperer les elements bar barChar et rempli le             
                        
                        @FXML
                        private BarChart home_barChart1;
                        @FXML
                        private CategoryAxis home_xAxis1;
                        @FXML
                        private NumberAxis home_yAxis1;

                        private void loadBarChar() {
                        // Récupérer les données du nombre de candidats par mois
                        ObservableList<XYChart.Data<String, Integer>> candidatsData = gestionnaireCondidat.recupererNombreCandidatsParMois();

                        // Créer une nouvelle série pour afficher les données
                        XYChart.Series<String, Integer> serieCandidats = new XYChart.Series<>();
                        serieCandidats.setName("Nombre de candidats");

                        // Ajouter les données dans la série
                        serieCandidats.getData().addAll(candidatsData);

                        // Ajouter la série au BarChart
                        home_barChart1.getData().clear();  // Vider le BarChart avant d'ajouter les nouvelles données
                        home_barChart1.getData().add(serieCandidats);
                        }

        
        
    
    //regler l'affichage de la date et d'heure
                        
                        
                    @FXML
                    private Text afficherHeure1; // Le texte pour afficher l'heure
                    @FXML
                    private Text afficherDate1;  // Le texte pour afficher la date


                     // Méthode pour obtenir la date actuelle sous forme de chaîne
                    private String getCurrentDate() {
                        LocalDate currentDate = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                        return currentDate.format(formatter);
                    }


                    private void startClock() {
                    Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), event -> {
                        afficherHeure1.setText(getCurrentTime());
                    }));
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.play();
                    }

                    // Méthode pour obtenir l'heure actuelle sous forme de chaîne
                    private String getCurrentTime() {
                        LocalTime currentTime = LocalTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        return currentTime.format(formatter);
                    }
    

    //code pour regle la table de home 
  
            @FXML private TableView<CoursInfo> home_table1;
            @FXML private TableColumn<CoursInfo, String> colDateSeance1;
            @FXML private TableColumn<CoursInfo, String> colHeureSeance1;   
            @FXML private TableColumn<CoursInfo, String> colMoniteur;
            @FXML private TableColumn<CoursInfo, String> colCondidat;


            private GestionnaireCours gestionnaireCours;
            
            
            
            @FXML
            private HBox fermerProgramme;
            
            @FXML
                private JFXButton fermerProgrammetitle; 
            
            @FXML
                private JFXButton minimizeProgramme; 
            
            
            @FXML
                private Text notificationSurVehicule; 
            
            public void verifierAssuranceEtControle() {
                GestionnaireVehicule gestionnaireVehicule = new GestionnaireVehicule();
    ObservableList<Vehicule> vehicules = gestionnaireVehicule.recupererVehicules();

     // Initialiser une variable pour les notifications
    StringBuilder notifications = new StringBuilder();

    // Parcourir les véhicules pour vérifier les dates
    for (Vehicule vehicule : vehicules) {
        LocalDate dateControle = vehicule.getDateControle();
        LocalDate dateAssurance = vehicule.getDateAssurance();

        // Ajouter 1 an à la date de début pour obtenir la date de fin
        LocalDate dateFinControle = dateControle.plusYears(1);
        LocalDate dateFinAssurance = dateAssurance.plusYears(1);

        // Vérification des dates restantes (différence avec la date actuelle)
        long joursRestantsControle = ChronoUnit.DAYS.between(LocalDate.now(), dateFinControle);
        long joursRestantsAssurance = ChronoUnit.DAYS.between(LocalDate.now(), dateFinAssurance);

        // Ajouter des notifications si les dates approchent
        if (joursRestantsControle <= 10) {
            notifications.append("Contrôle technique pour ").append(vehicule.getMarque()).append(" bientôt expiré !\n");
        }
        if (joursRestantsAssurance <= 10) {
            notifications.append("Assurance pour ").append(vehicule.getMarque()).append(" bientôt expirée !\n");
        }
    }

    // Si des notifications existent, afficher dans le champ de texte
    if (notifications.length() > 0) {
        notificationSurVehicule.setText(notifications.toString());
    } 
}
            
            
            
            
             private String userRole;

        public void setUserRole(String role) {
    this.userRole = role;
    if ("moniteur".equals(role)) { // Comparaison correcte des chaînes
        vehiculeSectionButton.setVisible(false);
        quantabiliteSectionButton.setVisible(false);
        utilisateurSectionButton.setVisible(false);
    }
    }
                
    
        
        
     
        
                
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        verifierAssuranceEtControle();
        
                //configurer le button exit        
                        fermerProgramme.setOnMouseClicked(event -> {
        // Créer une alerte de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de fermeture");
        alert.setHeaderText("Voulez-vous vraiment quitter ?");
        alert.setContentText("Confirmez pour fermer le programme.");

        // Afficher l'alerte et attendre la réponse de l'utilisateur
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Fermer l'application
                Platform.exit();
            }
        });
                    });
                        
                        
                    //configurer le button exit        
                        fermerProgrammetitle.setOnAction(event -> {
        // Créer une alerte de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de fermeture");
        alert.setHeaderText("Voulez-vous vraiment quitter ?");
        alert.setContentText("Confirmez pour fermer le programme.");

        // Afficher l'alerte et attendre la réponse de l'utilisateur
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Fermer l'application
                Platform.exit();
            }
        });
                    });
                        
                        
             minimizeProgramme.setOnAction(event -> {
            // Récupérer la fenêtre principale (Stage)
            Stage stage = (Stage) minimizeProgramme.getScene().getWindow();
            // Mettre la fenêtre en état minimisé
            stage.setIconified(true);
        });
                        
                        
                        
  
                           // Mettre à jour la date au début
                       afficherDate1.setText(getCurrentDate());
                       
                        // Démarrer l'horloge
                           startClock();
               
                           // Initialisation de gestionnaireCours
                       gestionnaireCours = new GestionnaireCours(); // Assurez-vous que cet objet est initialisé

                            // Initialisation les colonnes de la table d'acceuil
                           colDateSeance1.setCellValueFactory(new PropertyValueFactory<>("dateSeance"));
                           colHeureSeance1.setCellValueFactory(new PropertyValueFactory<>("heureSeance"));
                           colMoniteur.setCellValueFactory(new PropertyValueFactory<>("nomCompletMoniteur"));
                           colCondidat.setCellValueFactory(new PropertyValueFactory<>("nomCompletCondidat"));

                           // Récupérer les Cour theorique de jour de la base de données
                           ObservableList<CoursInfo> coursList = gestionnaireCours.recupererCoursCodeAujourdhui();

                           // Ajouter les données au TableView d'accueil des cours theorique d'aujourd'hui
                           home_table1.setItems(coursList);


                            // Initialisation de gestionnaireCondidat
                       gestionnaireCondidat = new GestionnaireCondidat(); // Assurez-vous que cet objet est initialisé
                            
                        // Initialisation de gestionnaireMoniteur
                       gestionnaireMoniteur = new GestionnaireMoniteur();

                             //Remplir la berchart
                                loadBarChar();
                                
                                
                               
    }    
    
    
    
    
    
}
