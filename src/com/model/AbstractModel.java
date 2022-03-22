package com.model;

import com.connecte.database.Connexion;
import com.entites.Parking;
import com.entites.associer_entites.InfoVehicule;
import com.observateur.Observateur;
import com.observer.Observer;

import java.util.ArrayList;
import java.util.List;

  

public abstract class AbstractModel implements Observer 
{ 

   protected List<Observateur> listObservateur;
   protected Connexion connexion;
   
	public void addObservateur(Observateur obs) 
	{
		listObservateur.add(obs);
	}

	public void removeObservateur() 
	{
		this.listObservateur=new ArrayList<Observateur>();
	}

	public void updateObservateur(Parking parkingCourant, int indiceVehicule)
	{
		for (Observateur ob:listObservateur)
		{
			ob.update(parkingCourant, indiceVehicule);
		}
		
	}
	public void alerteObservateur(String message, String titre, int option) {
		for (Observateur ob:listObservateur)
		{
			ob.sortieAlerte(message, titre, option);
		}
	}
	public abstract boolean establishedConnecteAI(List<Parking> listePark);
	public abstract double negociePrix(Parking parking, InfoVehicule vehicule);

    public abstract void negociePrixOptimale(List<Parking> listePark, InfoVehicule infoVehicule);
}
