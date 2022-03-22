package com.model;


import com.entites.Parking;
import com.entites.Vehicule;
import com.entites.associer_entites.InfoVehicule;
import com.outils.Moment;

import javax.swing.*;

public class CanelDiscutionVehicules {

    public CanelDiscutionVehicules() {}
    private void aff(String message,Parking parking, Vehicule vehicule){
        vehicule.setDiscution(message);
        parking.setDiscution(message);
    }

    public double lanceDiscution(Parking parking, InfoVehicule vehicule) {
        aff("Vehicule: Recherche de parking ",
                parking,vehicule.getVehicule());
        if(!(parking.getNbPlace() <= parking.getNbplaceOccuper())){
            //parking plein
            double prixApresDisc = parking.getPrixH(), vehiculePrixH=vehicule.getVehicule().getPrixH(), prixtmp=0;
            aff("Park"+parking.getId()+": Mon prix par heure: "+parking.getPrixH(),
                    parking,vehicule.getVehicule());
            //en fonction du taux de remlissage du park
            aff("Vehicule: Quel est ton taux d'occupation ? ",
                    parking,vehicule.getVehicule());
            aff("Park"+parking.getId()+": Mon taux est actuellement de : "+parking.getTauxOccupation()+"%",
                    parking,vehicule.getVehicule());
            double taux=parking.getTauxOccupation(),
                    prixVehmod=vehicule.getVehicule().setPrixenFonctionTauxRemplissageParking(taux);
            if(prixVehmod!=-1) {
                aff("Infos: reduction automatique prix/h",
                        parking, vehicule.getVehicule());
                aff("Infos: car le parking est remplit à "+parking.getTauxOccupation()+"%",
                        parking, vehicule.getVehicule());
            }else if(prixVehmod==-2){
                aff("Infos: impossible de faire reduction automatique",
                        parking, vehicule.getVehicule());
                aff("Infos: le parking non d'accord "+parking.getTauxOccupation()+"%",
                        parking, vehicule.getVehicule());
            }else{
                aff("Infos: impossible de faire reduction automatique",
                        parking, vehicule.getVehicule());
                aff("Infos: car le parking est à "+parking.getTauxOccupation()+"%",
                        parking, vehicule.getVehicule());
            }
            if(parking.getPrixH() > vehiculePrixH){
                if(vehicule.getUsager().getHandicap()==1){
                    aff("Vehicule: j'ai un handicap",
                            parking,vehicule.getVehicule());
                    aff("Vehicule: pouvez-vous me faire une reduction ? ",
                            parking,vehicule.getVehicule());
                    prixtmp=prixApresDisc -(prixApresDisc * parking.getReductionHandicap());
                    if(parking.getReductionHandicap()>0 && prixtmp >= 1){
                        prixApresDisc = prixtmp;
                        aff("Park"+parking.getId()+" : J'applique une reduction de "+parking.getReductionHandicap(),
                                parking,vehicule.getVehicule());
                        aff("Park"+parking.getId()+" : il vous revient à payer :"+prixApresDisc+"€",
                                parking,vehicule.getVehicule());
                    }else{
                        aff("Park"+parking.getId()+" : désolé je ne peux pas",
                                parking,vehicule.getVehicule());
                    }

                }
                //discution par rapport a l'heure
                if(prixApresDisc> vehiculePrixH){
                    if(vehicule.getVehicule().getHoraireDiscutable().contains(Moment.getInterHeure())){
                        //le vehicule est parametre a discuter en cette heure
                            //si c'est bon pour le parking(en fonction des parametres)
                            aff("Vehicule : "+prixApresDisc+" ne me convient pas", parking,vehicule.getVehicule());
                            aff("Vehicule : faites une réduction, car nous sommes "+
                                    Moment.getStringHoraire(), parking,vehicule.getVehicule());
                        prixtmp=prixApresDisc -(prixApresDisc * parking.getReductionHandicap());
                        if(parking.getTauxOccupation()>50 && prixtmp >=1){
                            prixApresDisc = prixtmp;
                            aff("Park"+parking.getId()+": j'applique une reduction de "+
                                            parking.getReduction()+"%",
                                    parking,vehicule.getVehicule());

                            aff("Park"+parking.getId()+": il vous revient à payer la somme de :"+prixApresDisc+"€",
                                    parking,vehicule.getVehicule());
                        }else{
                            aff("Park"+parking.getId()+": désolé, pas favorable pour moi",
                                    parking,vehicule.getVehicule());
                        }
                    }
                }
                //discution par rapport au jour
                if(prixApresDisc> vehiculePrixH){
                    if(vehicule.getVehicule().getJourDiscutable().contains(Moment.getInterJour())){
                        //le vehicule est parametre a discuter en ce jour
                        aff("Vehicule: "+prixApresDisc+"€ ne me covient pas",
                                parking,vehicule.getVehicule());
                        aff("Vehicule: nous sommes "+Moment.getStringDay()+" faites une réduction",
                                parking,vehicule.getVehicule());
                        prixtmp=prixApresDisc -(prixApresDisc * parking.getReductionHandicap());
                        if(parking.getTauxOccupation()>50 && prixtmp >=1) {
                            prixApresDisc = prixtmp;
                            aff("Park"+parking.getId()+": j'applique une reduction de " +
                                            parking.getReduction() + "%",
                                    parking, vehicule.getVehicule());
                            aff("Park"+parking.getId()+": ce qui vous revient à payer la somme de :" + prixApresDisc + "€",
                                    parking, vehicule.getVehicule());
                        }else{
                            aff("Park"+parking.getId()+" : désolé, pas favorable pour moi",
                                    parking,vehicule.getVehicule());
                        }
                    }
                }

                if(prixApresDisc > vehiculePrixH){
                    aff("Vehicule:"+ prixApresDisc + "€ ne me convient pas !",
                            parking,vehicule.getVehicule());
                    aff("Park"+parking.getId()+": Je vous souhaites une bonne journée ! ",
                            parking,vehicule.getVehicule());
                    return 0;
                }else{
                    aff("Infos: Négociatiation effectuée pour "+ prixApresDisc + "€",parking,vehicule.getVehicule());
                    aff("Infos: Affectation de la place au vehicule "+vehicule.getVehicule().getMarque()+"..",parking,vehicule.getVehicule());

                        parking.setTauxOccupation();
                        vehicule.getVehicule().setIdParkRecepteur(parking.getId());
                        vehicule.getVehicule().setPlace(parking.getNbplaceOccuper());


                    return prixApresDisc;
                }

            }else{
                aff("Infos: Négociatiation effectuée pour "+ prixApresDisc + "€",parking,vehicule.getVehicule());
                aff("Infos: Affectation de place au vehicule "+vehicule.getVehicule().getMarque()+"..",parking,vehicule.getVehicule());

                    parking.setTauxOccupation();
                    vehicule.getVehicule().setIdParkRecepteur(parking.getId());
                    vehicule.getVehicule().setPlace(parking.getNbplaceOccuper());

                return prixApresDisc;
            }
        }else{
            aff("FULL: ECHEC ",
                    parking,vehicule.getVehicule());
            aff("FULL:"+parking.getIntituler()+" est plein",
                    parking,vehicule.getVehicule());
            return -1;
        }

    }
    public void tacheFondDiscutionOptimale(Parking parking, InfoVehicule vehicule, int indicePark){
        SwingWorker swingWorker= new SwingWorker(){
            @Override
            protected Object doInBackground() {
                double prixApresDisc = 0, prixVehmod=vehicule.getVehicule().getPrixH(), prixtmp=0;
                aff("Info:Négociation Optimale",
                        parking,vehicule.getVehicule());

                aff("Park"+parking.getId()+": "+parking.getIntituler()+" prix maximal par heure: "+parking.getPrixH()+"€",
                        parking,vehicule.getVehicule());
                prixApresDisc=parking.getPrixH();

                //en fonction du taux de remlissage du park
                aff("Vehicule: quel est ton taux d'occupation ? ",
                        parking,vehicule.getVehicule());
                aff("Park"+parking.getId()+": "+parking.getIntituler()+" mon taux d'occupation: "+parking.getTauxOccupation()+"%",
                        parking,vehicule.getVehicule());
                double taux=parking.getTauxOccupation();
                prixVehmod=vehicule.getVehicule().setPrixenFonctionTauxRemplissageParking(taux);
                if(prixVehmod!=-1) {
                    aff("Infos: reduction automatique prix/h",
                            parking, vehicule.getVehicule());
                    aff("Infos: car le parking est remplit à "+parking.getTauxOccupation()+"%",
                            parking, vehicule.getVehicule());
                }else if(prixVehmod==-2){
                    aff("Infos: impossible de faire reduction automatique",
                            parking, vehicule.getVehicule());
                    aff("Infos: le parking non d'accord, taux: "+parking.getTauxOccupation()+"%",
                            parking, vehicule.getVehicule());
                }else{
                    aff("Infos: impossible de faire reduction automatique",
                            parking, vehicule.getVehicule());
                    aff("Infos: car le parking est à "+parking.getTauxOccupation()+"%",
                            parking, vehicule.getVehicule());
                }
                if(vehicule.getUsager().getHandicap()==1){
                    aff("Vehicule: j'ai un handicap",
                            parking,vehicule.getVehicule());
                    aff("Vehicule: pouvez-vous me faire une reduction ? ",
                            parking,vehicule.getVehicule());
                    prixtmp=prixApresDisc -(prixApresDisc * parking.getReductionHandicap());
                    if(parking.getReductionHandicap()>0 && prixtmp >=1){
                        prixApresDisc = prixtmp;
                        aff("Park"+parking.getId()+" : J'applique une reduction de "+parking.getReductionHandicap(),
                                parking,vehicule.getVehicule());
                        aff("Park"+parking.getId()+" : il vous revient à payer :"+prixApresDisc+"€",
                                parking,vehicule.getVehicule());
                    }else{
                        aff("Park"+parking.getId()+" : désolé je ne peux pas",
                                parking,vehicule.getVehicule());
                    }

                }
                //discution par rapport a l'heure
                if(vehicule.getVehicule().getHoraireDiscutable().contains(Moment.getInterHeure())){

                    aff("Vehicule : "+prixApresDisc+" ne me convient pas", parking,vehicule.getVehicule());
                    aff("Vehicule : faites une réduction, car nous sommes "+
                            Moment.getStringHoraire(), parking,vehicule.getVehicule());
                    prixtmp=prixApresDisc -(prixApresDisc * parking.getReductionHandicap());
                    if(parking.getTauxOccupation()>50 && prixtmp >=1){
                        prixApresDisc = prixtmp;

                        aff("Park"+parking.getId()+": j'applique une reduction de "+
                                        parking.getReduction()+"%",
                                parking,vehicule.getVehicule());

                        aff("Park"+parking.getId()+": il vous revient à payer la somme de :"+prixApresDisc+"€",
                                parking,vehicule.getVehicule());
                    }else{
                        aff("Park"+parking.getId()+": désolé, pas favorable pour moi",
                                parking,vehicule.getVehicule());
                    }
                }
                //discution par rapport au jour
                if(vehicule.getVehicule().getJourDiscutable().contains(Moment.getInterJour())){
                    //le vehicule est parametre a discuter en ce jour
                    aff("Vehicule: "+prixApresDisc+"€ ne me covient pas",
                            parking,vehicule.getVehicule());
                    aff("Vehicule: nous sommes "+Moment.getStringDay()+" faites une réduction",
                            parking,vehicule.getVehicule());
                    prixtmp=prixApresDisc -(prixApresDisc * parking.getReductionHandicap());
                    if(parking.getTauxOccupation()>50 && prixtmp >=1) {
                        prixApresDisc = prixtmp;
                        aff("Park"+parking.getId()+": j'applique une reduction de " +
                                        parking.getReduction() + "%",
                                parking, vehicule.getVehicule());
                        aff("Park"+parking.getId()+": ce qui vous revient à payer la somme de :" + prixApresDisc + "€",
                                parking, vehicule.getVehicule());
                    }else{
                        aff("Park"+parking.getId()+": désolé, pas favorable pour moi",
                                parking,vehicule.getVehicule());
                    }
                }
                aff("prix final du parking: " + parking.getIntituler()+" "+prixApresDisc+"€",parking,vehicule.getVehicule());

                vehicule.getVehicule().addPrixParkingOptimal(prixApresDisc,indicePark);
                return null;
            }
        };
        swingWorker.execute();
    }
}
