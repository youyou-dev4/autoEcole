package Modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionnaireValeurParDefaut {
    private BDD_Connexion bddConnexion;

    public GestionnaireValeurParDefaut() {
        bddConnexion = new BDD_Connexion();
        if (bddConnexion.con == null) {
            System.out.println("Erreur : Connexion à la base de données non établie.");
        }
    }

    public ValeurParDefaut recupererValeurParDefaut() {
        String query = "SELECT centre_examen, permis, prix_seance_supp FROM valeur_par_defaut LIMIT 1";
        ValeurParDefaut valeur = null;

        try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) { // On récupère la première (et unique) ligne
                valeur = new ValeurParDefaut(
                        rs.getString("centre_examen"),
                        rs.getString("permis"),
                        rs.getDouble("prix_seance_supp")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return valeur;
    }
    
    
    // Récupère nbrHeureMax pour "creneaux" et "circulation"
    public int[] recupererNbrHeureMax() {
        String query = "SELECT nom_epreuve, nbr_heures FROM epreuve WHERE nom_epreuve IN ('crenaux', 'circulation')";
        int heureCreneaux = 0;
        int heureCirculation = 0;

        try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nomEpreuve = rs.getString("nom_epreuve");
                int nbrHeureMax = rs.getInt("nbr_heures");

                if ("crenaux".equalsIgnoreCase(nomEpreuve)) {
                    heureCreneaux = nbrHeureMax;
                } else if ("circulation".equalsIgnoreCase(nomEpreuve)) {
                    heureCirculation = nbrHeureMax;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new int[]{heureCreneaux, heureCirculation};
    }
    
    
    
    public int[] recupererStatistiques() {
    int totalCondidats = 0;
    int totalVersements = 0;
    int totalDepenses = 0;

    String queryCondidat = "SELECT COUNT(*) AS total FROM condidat";
    String queryVersement = "SELECT SUM(montant) AS total FROM versement";
    String queryDepenses = "SELECT SUM(montant) AS total FROM depence";

    try {
        // Récupérer le nombre total de candidats
        try (PreparedStatement stmt = bddConnexion.con.prepareStatement(queryCondidat);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                totalCondidats = rs.getInt("total");
            }
        }

        // Récupérer le total des versements
        try (PreparedStatement stmt = bddConnexion.con.prepareStatement(queryVersement);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                totalVersements = rs.getInt("total"); // SUM retourne NULL si aucun enregistrement, on gère ça en Java
            }
        }

        // Récupérer le total des dépenses
        try (PreparedStatement stmt = bddConnexion.con.prepareStatement(queryDepenses);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                totalDepenses = rs.getInt("total");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return new int[]{totalCondidats, totalVersements, totalDepenses};
}
    
    
    
    public boolean mettreAJourEpreuveInfo(int maxCrenaux, int maxCirculation, double prixSupp) {
        String queryCrenaux = "UPDATE epreuve SET nbr_heures = ? WHERE nom_epreuve = 'crenaux'";
        String queryCirculation = "UPDATE epreuve SET nbr_heures = ? WHERE nom_epreuve = 'circulation'";
        String queryValeurParDefaut = "UPDATE valeur_par_defaut SET prix_seance_supp = ?";

        try {
            // Mise à jour de `epreuve` pour `creneaux`
            try (PreparedStatement stmt = bddConnexion.con.prepareStatement(queryCrenaux)) {
                stmt.setInt(1, maxCrenaux);
                stmt.executeUpdate();
            }

            // Mise à jour de `epreuve` pour `circulation`
            try (PreparedStatement stmt = bddConnexion.con.prepareStatement(queryCirculation)) {
                stmt.setInt(1, maxCirculation);
                stmt.executeUpdate();
            }

            // Mise à jour de `valeur_par_defaut`
            try (PreparedStatement stmt = bddConnexion.con.prepareStatement(queryValeurParDefaut)) {
                stmt.setDouble(1, prixSupp);
                stmt.executeUpdate();
            }

            System.out.println("Mise à jour réussie !");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean mettreAJourExamenEtPermis(String centreExamen, String permis) {
        String query = "UPDATE valeur_par_defaut SET centre_examen = ?, permis = ?";

        try (PreparedStatement stmt = bddConnexion.con.prepareStatement(query)) {
            stmt.setString(1, centreExamen);
            stmt.setString(2, permis);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Mise à jour réussie !");
                return true;
            } else {
                System.out.println("Aucune ligne mise à jour.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
}
