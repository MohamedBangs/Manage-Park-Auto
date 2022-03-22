package com.entites.associer_entites;


public class Dimension {
    protected int longueur;
    protected int largueur;

    public Dimension (int longueur, int largueur){
        this.longueur=longueur;
        this.largueur=largueur;
    }
    public int getLargueur() {
        return largueur;
    }

    public int getLongueur() {
        return longueur;
    }

    public void setLargueur(int largueur) {
        this.largueur = largueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }
}
