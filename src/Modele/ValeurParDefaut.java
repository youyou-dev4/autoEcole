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
public class ValeurParDefaut {
    private String centreExamen;
    private String permis;
    private double prisSeanceSupplementaire;

    public ValeurParDefaut(String centreExamen, String permis, double prisSeanceSupplementaire) {
        this.centreExamen = centreExamen;
        this.permis = permis;
        this.prisSeanceSupplementaire = prisSeanceSupplementaire;
    }

    public String getCentreExamen() {
        return centreExamen;
    }

    public String getPermis() {
        return permis;
    }

    public double getPrisSeanceSupplementaire() {
        return prisSeanceSupplementaire;
    }
    
    
}
