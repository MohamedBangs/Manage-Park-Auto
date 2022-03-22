package com.connecte.database;

import java.sql.*;

public class Connexion {
    private Connection connexion=null;
    private Statement statement=null;
    private ResultSet resultat=null;

    public Connexion() {}

    public int etablissedConnexion() {
        // TODO Auto-generated method stub
        String url="jdbc:mysql://localhost:3306/AIDATABASE",user="root",pass="",driver="com.mysql.jdbc.Driver";
        try
        {
            Class.forName(driver);
        }catch(ClassNotFoundException e)
        { //creer une classe generique pour les alertes vers la vue
            // e.printStackTrace();
            return 0;
        }
        try
        {
            this.connexion= DriverManager.getConnection(url,user,pass);
            this.statement=this.connexion.createStatement();
        }catch(SQLException e)
        {
            // e.printStackTrace();
            return 1;
        }
        return 2;
    }
}
