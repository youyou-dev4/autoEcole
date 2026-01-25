/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Depence {
    private int id_depence;
    private String dateDepence;
    private double montant;
    private String motif;
    
    // Format de la date (par exemple, "yyyy-MM-dd")
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public Depence(int id_depence, String dateDepence, double montant, String motif) {
        this.id_depence = id_depence;
        this.dateDepence = dateDepence;
        this.montant = montant;
        this.motif = motif;
    }

    // Convertir la chaîne de date en LocalDate
    public LocalDate getDateDepenceAsLocalDate() {
        try {
            return LocalDate.parse(dateDepence, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format de date invalide pour " + dateDepence);
        }
    }
    
    

    public int getId_depence() {
        return id_depence;
    }

    public String getDateDepence() {
        return dateDepence;
    }

    public double getMontant() {
        return montant;
    }

    public String getMotif() {
        return motif;
    }

    public void setId_depence(int id_depence) {
        this.id_depence = id_depence;
    }

    public void setDateDepence(String dateDepence) {
        this.dateDepence = dateDepence;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
    
    
}
