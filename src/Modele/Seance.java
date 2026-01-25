/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;


public class Seance {
    private int id_seance;
    private String date;
    private String heure;
    private String type;
    private double montant;
    private String epreuve;
    private String permis;
    private String nomCondidat;
    private String nomMoniteur;
    private int idCondidat;
    private int idMoniteur;

    public Seance(int id_seance, String date, String heure, String type, double montant, String epreuve, String permis, String nomCondidat, String nomMoniteur, int idCondidat, int idMoniteur) {
        this.id_seance = id_seance;
        this.date = date;
        this.heure = heure;
        this.type = type;
        this.montant = montant;
        this.epreuve = epreuve;
        this.permis = permis;
        this.nomCondidat = nomCondidat;
        this.nomMoniteur = nomMoniteur;
        this.idCondidat = idCondidat;
        this.idMoniteur = idMoniteur;
    }

    public int getId_seance() {
        return id_seance;
    }

    public String getDate() {
        return date;
    }

    public String getHeure() {
        return heure;
    }

    public String getType() {
        return type;
    }

    public double getMontant() {
        return montant;
    }

    public String getEpreuve() {
        return epreuve;
    }

    public String getPermis() {
        return permis;
    }

    public String getNomCondidat() {
        return nomCondidat;
    }

    public String getNomMoniteur() {
        return nomMoniteur;
    }

    public int getIdCondidat() {
        return idCondidat;
    }

    public int getIdMoniteur() {
        return idMoniteur;
    }

    public void setId_seance(int id_seance) {
        this.id_seance = id_seance;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setEpreuve(String epreuve) {
        this.epreuve = epreuve;
    }

    public void setPermis(String permis) {
        this.permis = permis;
    }

    public void setNomCondidat(String nomCondidat) {
        this.nomCondidat = nomCondidat;
    }

    public void setNomMoniteur(String nomMoniteur) {
        this.nomMoniteur = nomMoniteur;
    }

    public void setIdCondidat(int idCondidat) {
        this.idCondidat = idCondidat;
    }

    public void setIdMoniteur(int idMoniteur) {
        this.idMoniteur = idMoniteur;
    }
    
    
    
    
}
