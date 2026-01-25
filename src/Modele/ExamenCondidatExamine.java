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
public class ExamenCondidatExamine {
    private int idCondidat;
     private String nomCondidat;
    private String epreuve;
    private String permis;
    private String resultat;
    private String dateNaissance;
    private String numCondidat ;

    public ExamenCondidatExamine(int idCondidat, String numCondidat, String nomCondidat, String dateNaissance, String epreuve, String permis, String resultat) {
        this.idCondidat = idCondidat;
        this.numCondidat = numCondidat;
        this.nomCondidat = nomCondidat;
        this.dateNaissance = dateNaissance;
        this.epreuve = epreuve;
        this.permis = permis;
        this.resultat = resultat;
        
    }

    public String getPermis() {
        return permis;
    }

    public void setPermis(String permis) {
        this.permis = permis;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public String getNumCondidat() {
        return numCondidat;
    }
    
    

    public int getIdCondidat() {
        return idCondidat;
    }

    public String getNomCondidat() {
        return nomCondidat;
    }

    public String getEpreuve() {
        return epreuve;
    }

    public String getResultat() {
        return resultat;
    }

    public void setIdCondidat(int idCondidat) {
        this.idCondidat = idCondidat;
    }

    public void setNomCondidat(String nomCondidat) {
        this.nomCondidat = nomCondidat;
    }

    public void setEpreuve(String epreuve) {
        this.epreuve = epreuve;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
    
    
}
