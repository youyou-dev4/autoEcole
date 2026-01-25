/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;



public class Inscription {
    private int id_inscription;
     private int id_condidat;
     private String typePermis;
    private String dateInscription;   
    private double montant;
    private String nomCondidat;

    public Inscription(int id_inscription, int id_condidat, String typePermis, String dateInscription, double montant, String nomCondidat) {
        this.id_inscription = id_inscription;
        this.id_condidat = id_condidat;
        this.typePermis = typePermis;
        this.dateInscription = dateInscription;
        this.montant = montant;
        this.nomCondidat = nomCondidat;
    }

    public int getId_inscription() {
        return id_inscription;
    }

    public int getId_condidat() {
        return id_condidat;
    }

    public String getTypePermis() {
        return typePermis;
    }

    public String getDateInscription() {
        return dateInscription;
    }

    public double getMontant() {
        return montant;
    }

    public String getNomCondidat() {
        return nomCondidat;
    }

    public void setId_inscription(int id_inscription) {
        this.id_inscription = id_inscription;
    }

    public void setId_condidat(int id_condidat) {
        this.id_condidat = id_condidat;
    }

    public void setTypePermis(String typePermis) {
        this.typePermis = typePermis;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setNomCondidat(String nomCondidat) {
        this.nomCondidat = nomCondidat;
    }

    
    
    
    
}
