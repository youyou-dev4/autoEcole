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
public class SeanceCondidatInfo {
    private String date;
    private String heure;
    private String epreuve;
    private String type;
    private String permis;

    public SeanceCondidatInfo(String date, String heure, String epreuve, String type, String permis) {
        this.date = date;
        this.heure = heure;
        this.epreuve = epreuve;
        this.type = type;
        this.permis = permis;
    }

    
    
    
    
    public String getDate() {
        return date;
    }

    public String getHeure() {
        return heure;
    }

    public String getEpreuve() {
        return epreuve;
    }

    public String getType() {
        return type;
    }

    public String getPermis() {
        return permis;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public void setEpreuve(String epreuve) {
        this.epreuve = epreuve;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPermis(String permis) {
        this.permis = permis;
    }
    
    
}
