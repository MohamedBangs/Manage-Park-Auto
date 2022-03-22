package com.observer;

import com.entites.Parking;
import com.observateur.Observateur;


public interface Observer {
    public void addObservateur( Observateur obs);
    public void removeObservateur();
    public void updateObservateur(Parking parkingCourant, int indiceVehicule);
    public void alerteObservateur(String message, String titre, int option);
}
