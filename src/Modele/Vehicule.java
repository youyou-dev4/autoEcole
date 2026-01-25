/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.time.LocalDate;

 


          public class Vehicule {
    private int idVehicule;
    private String marque;
    private String modele;
    private String immatriculation;
    private String typeVehicule;
    private String proprietaire;
    private LocalDate dateControle;
    private LocalDate dateAssurance;
    private String typeAssurance;
    private String numAssurance;

    public Vehicule(int idVehicule, String marque, String modele, String immatriculation, String typeVehicule, 
                    String proprietaire, LocalDate dateControle, LocalDate dateAssurance, 
                    String typeAssurance, String numAssurance) {
        this.idVehicule = idVehicule;
        this.marque = marque;
        this.modele = modele;
        this.immatriculation = immatriculation;
        this.typeVehicule = typeVehicule;
        this.proprietaire = proprietaire;
        this.dateControle = dateControle;
        this.dateAssurance = dateAssurance;
        this.typeAssurance = typeAssurance;
        this.numAssurance = numAssurance;
    }

    // Getters et Setters
    public int getIdVehicule() {
        return idVehicule;
    }
    
    public String getNomComplet() {
    return marque + " " + modele;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getTypeVehicule() {
        return typeVehicule;
    }

    public void setTypeVehicule(String typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public LocalDate getDateControle() {
        return dateControle;
    }

    public void setDateControle(LocalDate dateControle) {
        this.dateControle = dateControle;
    }

    public LocalDate getDateAssurance() {
        return dateAssurance;
    }

    public void setDateAssurance(LocalDate dateAssurance) {
        this.dateAssurance = dateAssurance;
    }

    public String getTypeAssurance() {
        return typeAssurance;
    }

    public void setTypeAssurance(String typeAssurance) {
        this.typeAssurance = typeAssurance;
    }

    public String getNumAssurance() {
        return numAssurance;
    }

    public void setNumAssurance(String numAssurance) {
        this.numAssurance = numAssurance;
    }
   
}
