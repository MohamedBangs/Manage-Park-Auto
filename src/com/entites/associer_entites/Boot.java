package com.entites.associer_entites;

import com.entites.*;
import com.outils.Config;
import com.outils.Moment;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Boot {

    private List<InfoVehicule> listeVehicule;
    private List<Parking> listePark;
    private Random random;

    public Boot(){

        random = new Random();
        this.listePark=new ArrayList<>();
        this.listeVehicule = new ArrayList<>();

    }

    public void createVehicules(int size) {

        String[] sexe = {"M","F"};
        String[] names = {"Sophia","Liam","Olivia","Noah","Riley","Jackson","Emma","Aiden",
                "Ava","Elijah","Isabella","Grayson","Aria","Lucas","Aaliyah","Oliver",
                "Amelia","Caden","Mia","Mateo","Layla","Muhammad","Zoe","Mason"};
        String[] manufacturers = {"Audi","BMW","Toyota","Fiat","Mercedes"};
        String[] carNumbers = {"AZ","FC","DS","QV", "BA","BE", "BZ"};
        String[] truckNumbers = {"AV", "AD", "BT", "BY"};
        String[] motoNumbers = {"MT", "MD","BO", "BR", "BU"};
        String[] listPlaques = {"MT", "MD","BO", "AV", "AD", "BT", "BY", "BR", "BU","AZ","FC","DS","QV", "BA","BE", "BZ"};
        HashMap<String, String> vehPlaque=new HashMap<>();
        for (int i=0;i<carNumbers.length;i++)
             vehPlaque.put(carNumbers[i], "voiture");
        for (int i=0;i<truckNumbers.length;i++)
            vehPlaque.put(truckNumbers[i], "camion");
        for (int i=0;i<motoNumbers.length;i++)
            vehPlaque.put(motoNumbers[i], "moto");

        HashMap<Integer, String[]> models = new HashMap<>();

        //On match les models avec les marques
        models.put(0, new String[]{"ETron","A1","A2","A3","A4","A5"});
        models.put(1, new String[]{"F40","F44","G01","G06","G07"});
        models.put(2, new String[]{"Corolla","Prius","Yaris","Camry","Avanza"});
        models.put(3, new String[]{"Panda","Freemont", "Gucci", "Steyr"});
        models.put(4, new String[]{"A-Class", "AMG", "CLA", "C-Class", "CLS", "E-Class", "G-Class"});
        int plaqueCourant=0;

        for(int i = 0; i < size; i++) {
            Usager u = new Usager(sexe[random.nextInt(sexe.length)],
                    names[random.nextInt(names.length)], names[random.nextInt(names.length)],
                    20 + random.nextInt(10),random.nextInt(2));
            List<Moment.PORTIONHEURE> portionheures =new ArrayList<Moment.PORTIONHEURE>();
            portionheures.add(Moment.PORTIONHEURE.values()[random.nextInt(Moment.PORTIONHEURE.values().length)]);
            List<Moment.PORTIONMOI> portionmois =new ArrayList<Moment.PORTIONMOI>();
            portionmois.add(Moment.PORTIONMOI.values()[random.nextInt(Moment.PORTIONMOI.values().length)]);
            Vehicule v = null;
            int manufactureId = random.nextInt(manufacturers.length);
            String[] model = models.get(manufactureId);
            String plaqueChoisi=this.gestionPlaque(listPlaques, plaqueCourant);
            switch (random.nextInt(3)){
                case 0:
                    v = new Voiture(manufacturers[manufactureId],
                            plaqueChoisi,
                            model[random.nextInt(model.length)], random.nextInt(15) + 5, i, portionheures, portionmois, vehPlaque.get(plaqueChoisi));
                    break;
                case 1:
                    v = new Moto(manufacturers[manufactureId],
                            plaqueChoisi,
                            model[random.nextInt(model.length)], random.nextInt(15) + 5, i, portionheures, portionmois, vehPlaque.get(plaqueChoisi));
                    break;
                case 2:
                    v = new Camion(manufacturers[manufactureId],
                            plaqueChoisi,
                            model[random.nextInt(model.length)], random.nextInt(15)+5, i,portionheures, portionmois, vehPlaque.get(plaqueChoisi));
                    break;
            }
            Duree d = new Duree();
            this.listeVehicule.add(new InfoVehicule(v, u, d));
            plaqueCourant++;
        }

    }
    private String gestionPlaque(String[] carNumbers, int plaqueCourant){
        if(plaqueCourant>=carNumbers.length)
            return "AD"; //ce qui n'arrive jamais//ici pour les raisons de sécurité d'execution du code
        else
           return carNumbers[plaqueCourant];
    }
    public void createParking(int size) {

        String[] placesTypes = {"Moto", "Voiture", "Camion"};
        int[] nbplaces = {20,30,40};
        Color[] colors = {Color.BLUE, Color.MAGENTA, Color.red, Color.YELLOW, Color.PINK, Color.CYAN, Color.GREEN};
        for(int i = 0; i < size; i++) {


            int dimensionLong=200 + random.nextInt(300),
                    dimensionLarg=200 + random.nextInt(300),
                    coordX=0, coordY=0;
            if(i==0){
                coordX=Config.park1DebutX;
                coordY=Config.park1DebutY;
            }else if(i==1){
                coordX=Config.park2DebutX;
                coordY=Config.park2DebutY;
            }else{
                coordX=Config.park3DebutX;
                coordY=Config.park3DebutY;
            }


            this.listePark.add(new Parking(
                    new Dimension(dimensionLong, dimensionLarg),
                    new Place(placesTypes[random.nextInt(placesTypes.length)], Config.dimensionButtonWidth,
                            Config.dimensionButtonHeight), random.nextInt(3), nbplaces[random.nextInt(nbplaces.length)],
                    0, "PARK" + (i + 1), random.nextDouble(), random.nextDouble(),
                    random.nextInt(20)+5, colors[random.nextInt(colors.length)], i,coordX,coordY));

        }

    }

    public List<InfoVehicule> getListeVehicule(){
        return this.listeVehicule;
    }
    public List<Parking> getListePark(){
        return this.listePark;
    }

}
