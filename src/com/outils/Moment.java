package com.outils;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Moment {
    public enum PORTIONHEURE{
        MATIN,
        APRESMIDI,
        SOIR
    } ;
    public enum PORTIONMOI{
        DEBUT,
        MILIEU,
        FIN
    };
    private static int heure;
    private static int jour;
    public Moment(){
        SimpleDateFormat s = new SimpleDateFormat("dd");
        Date date = new Date();
        this.jour= Integer.parseInt(s.format(date));
        s = new SimpleDateFormat("HH");
        date = new Date();
        this.heure= Integer.parseInt(s.format(date));
    }
    public static PORTIONHEURE getInterHeure(){
        if(heure <= 12 && heure >= 6)
             return PORTIONHEURE.MATIN;
        else if (heure > 12 && heure <= 18)
            return PORTIONHEURE.APRESMIDI;
        else return PORTIONHEURE.SOIR;
    }
    public static PORTIONMOI getInterJour(){
        if(jour <= 10 && jour >= 1)
            return PORTIONMOI.DEBUT;
        else if (jour > 10 && jour <= 20)
            return  PORTIONMOI.MILIEU;
        else return  PORTIONMOI.FIN;
    }
    public static String getStringHoraire(){
        if(heure <= 12 && heure >= 6)
            return "le matin";
        else if (heure > 12 && heure <= 18)
            return "dans l'après midi";
        else return "le soir";
    }
    public static String getStringDay(){
        if(jour <= 10 && jour >= 1)
            return "en debut du mois";
        else if (jour > 10 && jour <= 20)
            return  "a la moitié du mois";
        else return  "à la fin du mois";
    }
}
