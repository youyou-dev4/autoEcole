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
public class MontantCondidat {
    private double totalAPayer;
    private double totalPaye;
    private double resteAPayer;

    public MontantCondidat(double totalAPayer, double totalPaye) {
        this.totalAPayer = totalAPayer;
        this.totalPaye = totalPaye;
        this.resteAPayer = totalAPayer - totalPaye;
    }

    public double getTotalAPayer() {
        return totalAPayer;
    }

    public double getTotalPaye() {
        return totalPaye;
    }

    public double getResteAPayer() {
        return resteAPayer;
    }
}
