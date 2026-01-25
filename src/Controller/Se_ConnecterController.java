package Controller;

import Modele.BDD_Connexion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javafx.stage.StageStyle;
import org.mindrot.jbcrypt.BCrypt;

public class Se_ConnecterController implements Initializable {

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    private BDD_Connexion bddConnexion; // Déclaration de l'objet BDD_Connexion

    @FXML
private void handleLoginClick(ActionEvent event) {
    if (usernameField == null || passwordField == null) {
        showAlert("Erreur", "Champs non initialisés. Vérifiez votre FXML.", AlertType.ERROR);
        return;
    }

    String username = usernameField.getText();
    String password = passwordField.getText();

    if (username.isEmpty() || password.isEmpty()) {
        showAlert("Erreur", "Veuillez remplir tous les champs.", AlertType.ERROR);
        return;
    }

    bddConnexion = new BDD_Connexion();

    String role = checkLogin(username.trim().toLowerCase(), password);

    if (role != null) {
        try {
            // Charger la vue home
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/home.fxml"));
            Parent root = loader.load();

            // Passer les données au contrôleur de home
            HomeController homeController = loader.getController();
            homeController.setUserRole(role);

            // Créer une nouvelle fenêtre maximisée
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED); // Conserver la barre de titre
            stage.setScene(new Scene(root));

            // Maximiser la fenêtre
            stage.setMaximized(true);

            // Fermer la fenêtre actuelle (optionnel)
            ((Stage) usernameField.getScene().getWindow()).close();

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Se_ConnecterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        showAlert("Erreur", "Nom d'utilisateur ou mot de passe incorrect", AlertType.ERROR);
    }
}


   private String checkLogin(String username, String password) {
    if (bddConnexion == null) {
        return null; // La connexion n'a pas pu être établie
    }

    String sql = "SELECT password, role FROM utilisateur WHERE username=?";
    try (PreparedStatement prest = bddConnexion.con.prepareStatement(sql)) {
        prest.setString(1, username);

        ResultSet rs = prest.executeQuery();
        if (rs.next()) {
            String hashedPassword = rs.getString("password");

            // Vérification du mot de passe avec BCrypt
            if (BCrypt.checkpw(password, hashedPassword)) {
                return rs.getString("role"); // Retourne le rôle si connexion réussie
            }
        }
    } catch (SQLException se) {
        se.printStackTrace();
    }

    return null; // Identifiants incorrects
}

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // Écouteur d'événements pour le champ de texte du nom d'utilisateur
    usernameField.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
            handleLoginClick(new ActionEvent());
        }
    });

    // Écouteur d'événements pour le champ de mot de passe
    passwordField.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
            handleLoginClick(new ActionEvent());
        }
    });
        
        // TODO : Ajoute des logiques d'initialisation si nécessaire
        System.out.println("Se_ConnecterController initialized");
    }
}
