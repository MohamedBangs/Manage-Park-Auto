package com.entites.associer_entites;

import com.entites.Usager;
import com.entites.Vehicule;

public class InfoVehicule {
    protected Vehicule vehicule;
    protected Usager usager;
    protected Duree duree;


    public InfoVehicule(Vehicule vehicule, Usager usager, Duree duree) {
        this.vehicule = vehicule;
        this.usager = usager;
        this.duree = duree;

    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public Usager getUsager() {
        return usager;
    }

    public Duree getDuree() {
        return duree;
    }


}