package com.entites;

import com.outils.Moment;

import java.util.*;


public abstract class Vehicule
{
    protected int xmap, ymap;
    protected int idVehicule,place, idParkRecepteur;
    protected String marque;
    protected String plaque;
    protected String modele;
    protected String type;
    protected double prixH;
    protected List<Moment.PORTIONHEURE> horaireDiscutable;
    protected List<Moment.PORTIONMOI> jourDiscutable;
    protected String discution;
    protected Map<Double, Integer> prixParkingOptimal;
    private double reduction50_70,reductionplus_70;

    public Vehicule(String marque, String plaque, String modele, String type, double prixH, int idVehicule,
                    List<Moment.PORTIONHEURE> horaireDiscutable, List<Moment.PORTIONMOI> jourDiscutable){
        this.marque=marque;
        this.plaque=plaque;
        this.modele=modele;
        this.type=type;
        this.prixH=prixH;
        this.idVehicule = idVehicule;
        this.jourDiscutable=jourDiscutable;
        this.horaireDiscutable=horaireDiscutable;
        this.discution="";
        this.prixParkingOptimal=new HashMap<>();
        //initialement on connait pas le parking recepteur
        this.idParkRecepteur=-1;
        this.reduction50_70=this.monRandom(10,1);
        this.reductionplus_70=this.monRandom(20,1);
    }

    public List<Moment.PORTIONHEURE> getHoraireDiscutable() {
        return this.horaireDiscutable;
    }
    public List<Moment.PORTIONMOI> getJourDiscutable() {
        return this.jourDiscutable;
    }

    public void setPrixH(double prixH) {
    	this.prixH=prixH;
    }
    public void setMarque(String newMarque) {
        marque=newMarque;
    }

    public void setPlaque(String newPlaque) {
        plaque=newPlaque;
    }

    public void setModele(String newModele) {
        modele=newModele;
    }

    public String getMarque() {
        return marque;
    }

    public String getPlaque() {
        return plaque;
    }

    public String getModele() {
        return modele;
    }

    public String getType() { return type; }
    public double getPrixH() {return this.prixH;}
    public void setXmap(int x){
        this.xmap=x;
    }
    public void setYmap(int y){
        this.ymap=y;
    }
    public int getXmap(){
        return this.xmap;
    }
    public int getYmap(){
        return this.ymap;
    }

    public int getIdVehicule() {
        return this.idVehicule;
    }


    public void setPlace(int place){
        this.place=place;
    }
    public int getPlace() {
        return this.place;
    }
    public void setDiscution(String str){
        this.discution+="\n"+str;
    }
    public String getDiscution(){
        return this.discution;
    }
    public void addPrixParkingOptimal(double prix, int idPark){
        this.prixParkingOptimal.put(prix,idPark);
    }
    public void triPrixParkingOptimal(){
        List<Map.Entry<Double, Integer>> list =
                new LinkedList<Map.Entry<Double, Integer>>(this.prixParkingOptimal.entrySet() );

        Collections.sort( list, new Comparator<Map.Entry<Double, Integer>>(){
            @Override
            public int compare(Map.Entry<Double, Integer> o1, Map.Entry<Double, Integer> o2 ){
                return (o1.getValue()).compareTo( o2.getValue());
            }
        });

        HashMap<Double, Integer> map_apres = new LinkedHashMap<Double, Integer>();
        for(Map.Entry<Double, Integer> entry : list)
            map_apres.put( entry.getKey(), entry.getValue() );
        this.prixParkingOptimal=map_apres;
    }

    public Map<Double, Integer> getPrixParkingOptimal(){
        return this.prixParkingOptimal;
    }
    public Double getPrixMinimumParking(){
        Iterator it = this.prixParkingOptimal.keySet().iterator();
        int idPark = 0;
        double prix=0;
        while (it.hasNext()) {
            prix= (double) it.next();
            idPark= this.prixParkingOptimal.get(prix);
        }
        return prix;
    }
    public int getParkingPrixMinimum(double prix){
        return this.prixParkingOptimal.get(prix);
    }

    public void setIdParkRecepteur(int idParkRecepteur) {
        this.idParkRecepteur = idParkRecepteur;
    }
    public int getIdParkRecepteur(){
        return this.idParkRecepteur;
    }
    public double setPrixenFonctionTauxRemplissageParking(double taux){
        double prixtmp=0;
        if(taux >= 50 && taux <70){
            prixtmp=this.prixH - (this.prixH * this.reduction50_70);
            if(prixtmp>=1){
                this.prixH=prixtmp;
                return this.prixH;
            }else return -2;
        }else if(taux >= 70){
            prixtmp=this.prixH - (this.prixH * this.reductionplus_70);
            if(prixtmp>=1){
                this.prixH=prixtmp;
                return this.prixH;
            }else return -2;
        }
        return -1;
    }

    public int monRandom(int max, int min){
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;
        return rand;
    }

    // Pour les test
    @Override
    public String toString() {
        return
                "MARQUE: " + marque +
                "\nPLACE: " + place +
                "\nPLAQUE: " + plaque +
                "\nMODELE: " + modele  +
                "\nTYPE VEHICULE: " + type +
                "\nPRIX/HEURE: " + prixH+" €" +
                "\nHORAIRE FAVORABLE: " + this.getStringJour() +
                "\nJOUR FAVORABLE: " + this.getStringMois();
    }
    private String getStringJour(){
        if (this.horaireDiscutable.contains(Moment.PORTIONHEURE.MATIN)){
            return "le matin";
        }else if (this.horaireDiscutable.contains(Moment.PORTIONHEURE.APRESMIDI)){
            return "l'après midi";
        }else{
            return "le soir";
        }
    }
    private String getStringMois(){
        if (this.jourDiscutable.contains(Moment.PORTIONMOI.DEBUT)){
            return "debut du mois";
        }else if (this.jourDiscutable.contains(Moment.PORTIONMOI.MILIEU)){
            return "milieu du mois";
        }else{
            return "fin du mois";
        }
    }
}
