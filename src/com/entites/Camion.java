package com.entites;

import com.outils.Moment;

import java.util.List;

public class Camion extends Vehicule {
    public String type;

    public Camion(String marque, String plaque, String modele, double prixH, int idVehicule,
                  List<Moment.PORTIONHEURE> horaireDiscutable, List<Moment.PORTIONMOI> jourDiscutable, String type) {
        super(marque, plaque, modele, type, prixH,idVehicule,horaireDiscutable,jourDiscutable);
    }
}
