package com.entites;

import com.entites.associer_entites.Dimension;
import com.entites.associer_entites.Place;

import java.awt.*;


public class Parking {
    private Dimension dimension;
    private Place place;
    private int niveau,id,nbplaceOccuper;
    private int nbPlace;
    private double tauxOccupation;
    private String intituler;
    private double reduction, reductionHandicap;
	private double prixH;
    private Color couleur;
    private String discution;
    private int parkDebutY;
    private int parkDebutX;

    public Parking(Dimension dimension, Place place, int niveau, int nbPlace,
                    float tauxOccupation,String intituler, double reduction, double reductionHandicap,
                   double prixH, Color couleur,int id, int parkDebutX, int parkDebutY){
        this.dimension=dimension;
        this.place=place;
        this.niveau=niveau;
        this.nbPlace=nbPlace;
        this.tauxOccupation=tauxOccupation;
        this.intituler=intituler;
        this.reduction=reduction;
        this.reductionHandicap=reductionHandicap;
        this.prixH=prixH;
        this.couleur=couleur;
        this.id=id;
        this.nbplaceOccuper=-1;
        this.discution="";
        this.parkDebutX=parkDebutX;
        this.parkDebutY=parkDebutY;
    }

	public void setReduction(double reduction) {
    	this.reduction=reduction;
    }
    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }
    public double getReductionHandicap(){
        return this.reductionHandicap;
    }
    public void setReductionHandicap(double reductionHandicap){
        this.reductionHandicap=reductionHandicap;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public void setTauxOccupation() {
        this.nbplaceOccuper++;
        this.tauxOccupation=(this.nbplaceOccuper*100)/this.getNbPlace();
    }
    public void diminuTauxOccupation() {
        this.tauxOccupation=(this.nbplaceOccuper*100)/this.getNbPlace();
    }
    public void setPrixH(double prixH) {
    	this.prixH=prixH;
    }

    public Place getPlace() {
        return place;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public double getTauxOccupation() {
        return tauxOccupation;
    }

    public int getNbPlace() {
        return nbPlace;
    }
    public int getNbplaceOccuper(){return this.nbplaceOccuper;}

    public int getNiveau() {
        return niveau;
    }
    public double getReduction() {
    	return this.reduction;
    }

	public String getIntituler() {
		// TODO Auto-generated method stub
		return this.intituler;
	}
	public double getPrixH() {return this.prixH;}

    public Color getCouleurFond() {
        return this.couleur;
    }
    public void setCouleurFond(Color color) {
        this.couleur=color;
    }

    public int getId() {
        return this.id;
    }
    public void setDiscution(String str){
        this.discution+="<br />"+str;
    }
    public String getDiscution(){
        return this.discution;
    }
    public int getNewCoordonnerX(){
        return (this.parkDebutX+=10);
    }
    public int getNewCoordonnerY(){
        return (this.parkDebutY+=10);
    }

    ///Pour les tesst
    @Override
    public String toString() {
        return "Parking{" +
                "dimension=" + dimension +
                ", place=" + place +
                ", niveau=" + niveau +
                ", id=" + id +
                ", nbplaceOccuper=" + nbplaceOccuper +
                ", nbPlace=" + nbPlace +
                ", tauxOccupation=" + tauxOccupation +
                ", intituler='" + intituler + '\'' +
                ", reduction=" + reduction +
                ", reductionHandicap=" + reductionHandicap +
                ", prixH=" + prixH +
                ", couleur=" + couleur +
                ", discution='" + discution + '\'' +
                '}';
    }

}