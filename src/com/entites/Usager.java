package com.entites;

import java.util.UUID;


public class Usager {
    protected UUID idUsager;
    protected String sexe;
    protected String nom;
    protected String prenom;
    protected int age;
    protected int handicap;

    public Usager(String sexe, String nom, String prenom, int age, int handicap){
        this.sexe=sexe;
        this.nom=nom;
        this.prenom=prenom;
        this.age=age;
        this.idUsager=UUID.randomUUID();
        this.handicap=handicap;
    } 



    public String getSexe() {
        return sexe;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() { return age; }

    public UUID getIdUsager() {
        return idUsager;
    }

    public int getHandicap() {
        return this.handicap;
    }

    public void setSexe(String newSexe) {
        sexe=newSexe;
    }

    public void setNom(String newNom) {
        nom=newNom;
    }

    public void setPrenom(String newPrenom) {
        prenom=newPrenom;
    }

    public void setAge(int newAge) {
        age=newAge;
    }

    public String describeUsager(){
        String chainehadicap= (this.getHandicap()==0)? "oui" : "non";
        return "\n\n\n"+
         "Nom: "+getNom()+
         "\nPrenom: "+getPrenom()+
          "\nÂge: "+getAge()+
         "\nGenre: "+getSexe()+
         "\nHandicap: "+chainehadicap;
    }


    //public String getTypeUsager() { return typeUsager; }

}
