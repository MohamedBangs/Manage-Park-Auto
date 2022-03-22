package com.model;

import com.connecte.database.Connexion;
import com.entites.Parking;
import com.entites.Vehicule;
import com.entites.associer_entites.InfoVehicule;
import com.observateur.Observateur;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ServeurPark extends AbstractModel {

	public ServeurPark(Connexion connexion) {
		// TODO Auto-generated constructor stub
		this.connexion = connexion;
		this.listObservateur = new ArrayList<Observateur> ();
	}


	@Override
	public boolean establishedConnecteAI(List<Parking> listePark) {
		// TODO Auto-generated method stub
		int connecteOK = this.connexion.etablissedConnexion();
		if (connecteOK == 0) {
			this.alerteObservateur("ERREUR DU DRIVER", "ERREUR DU DRIVER", JOptionPane.INFORMATION_MESSAGE);
			return true; //return false;
		} else if (connecteOK == 1) {
			this.alerteObservateur("ERREUR L'ORS DE LA CONNEXION A LA BASE DE DONNEE", "ERREUR DE CONNEXION", JOptionPane.INFORMATION_MESSAGE);
			return true; //return false;
		} else {
			return true;
		}
	}

	@Override
	public double negociePrix(Parking parking, InfoVehicule vehicule) {
		// TODO Auto-generated method stub
		double prixApresDisc = new CanelDiscutionVehicules().lanceDiscution(parking,vehicule);
		return prixApresDisc;
	}

	@Override
	public void negociePrixOptimale(List<Parking> listePark, InfoVehicule vehicule) {
		int indicePark=0;
		while (indicePark<listePark.size()){
			Parking parking=listePark.get(indicePark);
			new CanelDiscutionVehicules().tacheFondDiscutionOptimale(parking,vehicule,indicePark);
			indicePark++;
		}
	}

}