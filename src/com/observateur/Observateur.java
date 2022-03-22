package com.observateur;

import com.entites.Parking;


public interface Observateur {
    public void update(Parking parkingCourant, int indiceVehicule);
    void sortieAlerte(String message, String titre, int option);
}
