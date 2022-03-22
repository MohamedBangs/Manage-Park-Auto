package com.controleur;


import com.entites.Parking;
import com.entites.associer_entites.InfoVehicule;
import com.model.AbstractModel;

import java.util.List;

public class Controleur extends AbstractControleur
{	
	public Controleur(AbstractModel model) {
		super(model);
		// TODO Auto-generated constructor stub
	} 
	public boolean etablishedConnecte(List<Parking> listePark) {
		return this.model.establishedConnecteAI(listePark);
	}
//	@Override
	public double rechercheParking(Parking parking, InfoVehicule infoVehicule) {
		// TODO Auto-generated method stub
			if(parking.getNbPlace()==0) {
				return 0;
			}else {
				return this.model.negociePrix(parking, infoVehicule);
			}
	}

	@Override
	public void rechercheParkingOptimale(List<Parking> listePark, InfoVehicule infoVehicule) {
		this.model.negociePrixOptimale(listePark, infoVehicule);
	}



}
