
package Modele;


public class Moniteur {
    private int id;
    private String nom,prenom,dateNaiss,lieuNaiss,adr,tlphn,groupe_sanguin;
    private String dateEnregistrement;

    public Moniteur(int id, String nom, String prenom, String dateNaiss, String lieuNaiss, String adr, String tlphn, String groupe_sanguin, String dateInscription) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaiss = dateNaiss;
        this.lieuNaiss = lieuNaiss;
        this.adr = adr;
        this.tlphn = tlphn;
        this.groupe_sanguin = groupe_sanguin;
        this.dateEnregistrement = dateInscription;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDateNaiss() {
        return dateNaiss;
    }

    public String getLieuNaiss() {
        return lieuNaiss;
    }

    public String getAdr() {
        return adr;
    }

    public String getTlphn() {
        return tlphn;
    }

    public String getGroupe_sanguin() {
        return groupe_sanguin;
    }

    public String getDateInscription() {
        return dateEnregistrement;
    }
    
    public String getNomComplet() {
    return nom + " " + prenom;
    }

    

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateNaiss(String dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public void setTlphn(String tlphn) {
        this.tlphn = tlphn;
    }

    public void setGroupe_sanguin(String groupe_sanguin) {
        this.groupe_sanguin = groupe_sanguin;
    }

    public void setDateInscription(String dateInscription) {
        this.dateEnregistrement = dateInscription;
    }

   
    
    
}
