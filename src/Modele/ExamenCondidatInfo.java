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
public class ExamenCondidatInfo {
    private String date;
    private String epreuve;
    private String resultat;

    public ExamenCondidatInfo(String date, String epreuve, String resultat) {
        this.date = date;
        this.epreuve = epreuve;
        this.resultat = resultat;
    }

    public String getDate() {
        return date;
    }

    public String getEpreuve() {
        return epreuve;
    }

    public String getResultat() {
        return resultat;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEpreuve(String epreuve) {
        this.epreuve = epreuve;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
    
    
}
