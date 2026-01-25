
package Modele;


public class InscriptionCondidatInfo {
    private String date_inscription;
    private String nom_permis;
    private double montant;

    public InscriptionCondidatInfo(String date_inscription, String nom_permis, double montant) {
        this.date_inscription = date_inscription;
        this.nom_permis = nom_permis;
        this.montant = montant;
    }

    public String getDate_inscription() {
        return date_inscription;
    }

    public String getNom_permis() {
        return nom_permis;
    }

    public double getMontant() {
        return montant;
    }

    public void setDate_inscription(String date_inscription) {
        this.date_inscription = date_inscription;
    }

    public void setNom_permis(String nom_permis) {
        this.nom_permis = nom_permis;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
    
    
    
}
