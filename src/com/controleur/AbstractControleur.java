package com.controleur;


import com.entites.Parking;
import com.entites.associer_entites.InfoVehicule;
import com.model.AbstractModel;

import java.util.List;

public abstract class AbstractControleur 
{
    protected AbstractModel model;
    public AbstractControleur(AbstractModel model)
    {
    	this.model=model;
    }
    public abstract boolean etablishedConnecte(List<Parking> listePark);
    public abstract double rechercheParking(Parking parking, InfoVehicule infoVehicule);
    public abstract void rechercheParkingOptimale(List<Parking> listePark, InfoVehicule infoVehicule);
 
}
