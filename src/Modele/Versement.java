/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Versement {
    private int id_versement;
     private int id_condidat;
    private String dateVersement;
    private double montant;
    private String nomCondidat;
    
    
     // Format de la date (par exemple, "yyyy-MM-dd")
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    

    public Versement(int id_versement, String dateVersement, double montant, String nomCondidat, int id_condidat) {
        this.id_versement = id_versement;
        this.dateVersement = dateVersement;
        this.montant = montant;
        this.nomCondidat = nomCondidat;
        this.id_condidat = id_condidat;
    }

    // Convertir la chaîne de date en LocalDate
    public LocalDate getDateVersementAsLocalDate() {
        try {
            return LocalDate.parse(dateVersement, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format de date invalide pour " + dateVersement);
        }
    }
    
    
    

    public int getId_condidat() {
        return id_condidat;
    }

    public void setId_condidat(int id_condidat) {
        this.id_condidat = id_condidat;
    }
    
    

    public int getId_versement() {
        return id_versement;
    }

    public String getDateVersement() {
        return dateVersement;
    }

    public double getMontant() {
        return montant;
    }

    public String getNomCondidat() {
        return nomCondidat;
    }

    public void setId_versement(int id_versement) {
        this.id_versement = id_versement;
    }

    public void setDateVersement(String dateVersement) {
        this.dateVersement = dateVersement;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setNomCondidat(String nomCondidat) {
        this.nomCondidat = nomCondidat;
    }

    
    
    
}
