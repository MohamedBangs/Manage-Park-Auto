package com.entites.associer_entites;

import java.util.Calendar;
import java.util.Scanner;

public class Duree {

    protected long tempsDepart=0;
    protected long tempsFin=0;
    protected long pauseDepart=0;
    protected long pauseFin=0;
    protected long duree=0;
    protected char action;
    protected Scanner scan = new Scanner(System.in);

    public Duree(){

    }

    public void start()
    {
        tempsDepart=System.currentTimeMillis();
        tempsFin=0;
        pauseDepart=0;
        pauseFin=0;
        duree=0;
    }

    public void pause()
    {
        if(tempsDepart==0) {return;}
        pauseDepart=System.currentTimeMillis();
    }

    public void resume()
    {
        if(tempsDepart==0) {return;}
        if(pauseDepart==0) {return;}
        pauseFin=System.currentTimeMillis();
        tempsDepart=tempsDepart+pauseFin-pauseDepart;
        tempsFin=0;
        pauseDepart=0;
        pauseFin=0;
        duree=0;
    }

    public void stop()
    {
        if(action=='O') {
            if (tempsDepart == 0) {
                return;
            }
            tempsFin = System.currentTimeMillis();
            duree = (tempsFin - tempsDepart) - (pauseFin - pauseDepart);
            tempsDepart = 0;
            tempsFin = 0;
            pauseDepart = 0;
            pauseFin = 0;
        }
        else System.out.println("\nErreur pour de calcul de temps");
    }

    public int setTempsDepart() {
        System.out.print("\nVoulez voulez quitter le parking ?");
        return action=scan.next().charAt(0);

    }

    public long getDureeSec()
    {
        return duree/1000;
    }

    public long getDureeMs()
    {
        return duree;
    }

    public String getDureeTxt()
    {
        return timeToHMS(getDureeSec());
    }

    public static String timeToHMS(long tempsS) {

        // IN : (long) temps en secondes
        // OUT : (String) temps au format texte : "1 h 26 min 3 s"

        int h = (int) (tempsS / 3600);
        int m = (int) ((tempsS % 3600) / 60);
        int s = (int) (tempsS % 60);

        String r="";

        if(h>0) {r+=h+" h ";}
        if(m>0) {r+=m+" min ";}
        if(s>0) {r+=s+" s";}
        if(h<=0 && m<=0 && s<=0) {r="0 s";}

        return r;
    }

    public String getHeure(){
        Calendar now = Calendar.getInstance();
        String heureActuelle=now.get(Calendar.HOUR_OF_DAY)+"h:"+now.get(Calendar.MINUTE)+"m:"+now.get(Calendar.SECOND)+"s";
        return heureActuelle;
    }

}