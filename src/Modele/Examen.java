

package Modele;


public class Examen {
    
    private int idExamen;
    private String date;
    private String nomExaminateur;
    private String vehicule;
    private int nbrCondidat;

    public Examen(int idExamen, String date, String nomExaminateur, String vehicule, int nbrCondidat) {
        this.idExamen = idExamen;
        this.date = date;
        this.nomExaminateur = nomExaminateur;
        this.vehicule = vehicule;
        this.nbrCondidat = nbrCondidat;
    }

    public int getIdExamen() {
        return idExamen;
    }

    public String getDate() {
        return date;
    }

    public String getNomExaminateur() {
        return nomExaminateur;
    }

    public String getVehicule() {
        return vehicule;
    }
    
    

    public int getNbrCondidat() {
        return nbrCondidat;
    }

    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNomExaminateur(String nomExaminateur) {
        this.nomExaminateur = nomExaminateur;
    }

    public void setNbrCondidat(int nbrCondidat) {
        this.nbrCondidat = nbrCondidat;
    }
    
    
    
    
}
