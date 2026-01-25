

package Modele;


public class CoursInfo {
    private String dateSeance;
    private String heureSeance;
    private String nomMoniteur;
    private String prenomMoniteur;
    private String nomCondidat;
    private String prenomCondidat;

    // Constructeur
    public CoursInfo(String dateSeance, String heureSeance, String nomMoniteur, String prenomMoniteur,
                     String nomCondidat, String prenomCondidat) {
        this.dateSeance = dateSeance;
        this.heureSeance = heureSeance;
        this.nomMoniteur = nomMoniteur;
        this.prenomMoniteur = prenomMoniteur;
        this.nomCondidat = nomCondidat;
        this.prenomCondidat = prenomCondidat;
    }

    // Getters et Setters
    public String getDateSeance() { return dateSeance; }
    public String getHeureSeance() { return heureSeance; }
    public String getNomMoniteur() { return nomMoniteur; }
    public String getPrenomMoniteur() { return prenomMoniteur; }
    public String getNomCondidat() { return nomCondidat; }
    public String getPrenomCondidat() { return prenomCondidat; }
    
    public String getNomCompletMoniteur() {
    return nomMoniteur + " " + prenomMoniteur;
}

    public String getNomCompletCondidat() {
        return nomCondidat + " " + prenomCondidat;
    }
}
