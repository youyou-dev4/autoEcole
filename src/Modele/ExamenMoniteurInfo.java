
package Modele;


public class ExamenMoniteurInfo {
    private String date;
    private int nbr_condidat;

    public ExamenMoniteurInfo(String date, int nbr_condidat) {
        this.date = date;
        this.nbr_condidat = nbr_condidat;
    }

    public String getDate() {
        return date;
    }

    public int getNbr_condidat() {
        return nbr_condidat;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNbr_condidat(int nbr_condidat) {
        this.nbr_condidat = nbr_condidat;
    }
    
    
}
