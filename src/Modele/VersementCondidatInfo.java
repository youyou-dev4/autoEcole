/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author htw
 */
public class VersementCondidatInfo {
    private String date_versement;
    private double montant;

    public VersementCondidatInfo(String date_versement, double montant) {
        this.date_versement = date_versement;
        this.montant = montant;
    }

    public String getDate_versement() {
        return date_versement;
    }

    public double getMontant() {
        return montant;
    }

    public void setDate_versement(String date_versement) {
        this.date_versement = date_versement;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
    
    
}
