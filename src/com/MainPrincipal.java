package com;

import com.connecte.database.Connexion;
import com.controleur.AbstractControleur;
import com.controleur.Controleur;
import com.model.AbstractModel;
import com.model.ServeurPark;
import com.vue.Fenetre;

import javax.swing.*;

public class MainPrincipal {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

		Connexion connexion=new Connexion();
		AbstractModel model=new ServeurPark(connexion);
		AbstractControleur controleur=new Controleur(model);
		model.addObservateur(new Fenetre(controleur));

    }
}
