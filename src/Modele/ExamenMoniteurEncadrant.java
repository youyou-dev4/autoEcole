
package Modele;


public class ExamenMoniteurEncadrant {
    private int idMoniteur;
    private String nomMoniteur;

    public ExamenMoniteurEncadrant(int idMoniteur, String nomMoniteur) {
        this.idMoniteur = idMoniteur;
        this.nomMoniteur = nomMoniteur;
    }

    public int getIdMoniteur() {
        return idMoniteur;
    }

    public String getNomMoniteur() {
        return nomMoniteur;
    }

    public void setIdMoniteur(int idMoniteur) {
        this.idMoniteur = idMoniteur;
    }

    public void setNomMoniteur(String nomMoniteur) {
        this.nomMoniteur = nomMoniteur;
    }
    
    
}
