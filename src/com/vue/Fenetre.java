package com.vue;

import com.controleur.AbstractControleur;
import com.entites.Parking;
import com.entites.Vehicule;
import com.entites.associer_entites.Boot;
import com.entites.associer_entites.InfoVehicule;
import com.observateur.Observateur;
import com.outils.Config;
import com.outils.PanelImageFond;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//en fontion du taux de remplissage du parking, le veuticule pourr
public class Fenetre extends JFrame implements Observateur {

    private AbstractControleur controleur;
    private JPanel contenaire;
    private List<InfoVehicule> listeVehicule;
    private List<Parking> listePark;
    private List<JPanel> listePlace[];
    private List<Integer> parkingOcuper;
    private int indiceVehiculCourant;
    private JPanel panelParkingCourant[], panelChat[];
    private JPanel []panelRoadCar;
    private PanelImageFond panelRouteSouth[];
    private JPanel panelMap[];
    private JScrollPane paneldiscutions[];
    private JTabbedPane tab;
    private Font police = null;


    public Fenetre(AbstractControleur controleur) {
        this.controleur = controleur;
        this.setSize(Config.FenetreWidth, Config.FenetreHeight);
        this.setTitle(Config.ProjetTitle);
        this.setLocationRelativeTo(null);
        //this.setIconImage(new ImageIcon("icone.jpg").getImage());
        this.setResizable(false);
        this.initContenaire();
    }

    private void initAI() {
        // TODO Auto-generated method stub
        Boot createEntiter = new Boot();
        if (this.controleur.etablishedConnecte(createEntiter.getListePark())) {
            createEntiter.createVehicules(Config.NombreVehicules);
            createEntiter.createParking(Config.NombrePark);
            this.listeVehicule = createEntiter.getListeVehicule();
            this.listePark = createEntiter.getListePark();
            this.listePlace=new ArrayList[this.listePark.size()];
            this.panelParkingCourant=new JPanel[this.listePark.size()];
            this.panelRoadCar=new JPanel[this.listePark.size()];
           // this.panelRouteMileu=new JPanel[this.listePark.size()];
            this.panelRouteSouth=new PanelImageFond[this.listePark.size()];
            this.panelMap=new JPanel[this.listePark.size()];
            for (int i=0;i<this.listePark.size();i++)
                this.panelMap[i]=new JPanel(new GridBagLayout());
           // this.panelRouteCenter=new JPanel[this.listePark.size()];
            this.paneldiscutions=new JScrollPane[this.listePark.size()];
            this.panelChat=new JPanel[this.listePark.size()];
            this.indiceVehiculCourant = 0;
            this.parkingOcuper= new ArrayList<Integer>();
            this.rechercheParking();
        } else {
            this.dispose();
        }
    }

    private void rechercheParking() {
        while (indiceVehiculCourant<this.listeVehicule.size()) {
            this.negocie(this.listeVehicule.get(indiceVehiculCourant),false);
            indiceVehiculCourant++;
        }
    }
    public void negocie(InfoVehicule vehicule, boolean optimale) {

        SwingWorker swingWorker = new SwingWorker() {
            boolean searchEncours;
            private int indicePark;
            private Parking parking;

            @Override
            protected Object doInBackground() {
                indicePark = 0;
                this.searchEncours = true;
                parking = listePark.get(indicePark);
                if(!optimale){
                    while (searchEncours && controleur.rechercheParking(parking, vehicule) <= 0) {
                        //sortieAlerte("Le prix du parking " + listePark.get(indicePark).getIntituler() + " n'est pas négociable pour "+vehicule.getVehicule().getMarque(), "Négociation", JOptionPane.INFORMATION_MESSAGE);
                        indicePark++;
                        if (indicePark == listePark.size()) {
                            this.searchEncours = false;
                            indicePark=0;
                            // cancel(true);
                            return null;
                        } else //on recherche un autre park
                            parking = listePark.get(indicePark);
                    }
                }else{
                    controleur.rechercheParkingOptimale(listePark, vehicule);
                }
                return null;
            }

            @Override
            public void done() {
                synchronized (this){
                    if(SwingUtilities.isEventDispatchThread()){
                        if (!optimale){
                                //on construt le nouveau parking
                               if (this.searchEncours) {
                                   if(parkingOcuper.contains(this.parking.getId())) {
                                       //si le park est deja construit, alors on met le vehicule a sa place
                                       afterNegociate(vehicule.getVehicule().getIdVehicule(),parking.getId());
                                       initVehiculeOnMap(parking.getId());
                                   } else{
                                    parkingOcuper.add(this.parking.getId());
                                    initPlaceParking(this.parking,  vehicule.getVehicule().getIdVehicule());
                                    initVehiculeOnMap(parking.getId());
                                }
                            }else{
                                   //parking.setDiscution("Echecs negociation: "+vehicule.getVehicule().getPlaque());
                                   // new EditPrixBase(vehicule.getVehicule().getPlaque(), vehicule.getVehicule().getPrixH());
                               }
                        }else{
                            vehicule.getVehicule().triPrixParkingOptimal();
                            double prix=vehicule.getVehicule().getPrixMinimumParking();
                            parking = listePark.get(vehicule.getVehicule().getParkingPrixMinimum(prix));
                            if(parkingOcuper.contains(this.parking.getId())) {
                                //si c'est le parking oû le vehicule est garée,pas besoin de le deplacer
                                if (vehicule.getVehicule().getIdParkRecepteur() != parking.getId()){
                                    //on deplace le vehicule et le met dans le parking dédié
                                    updateParkingVehicule(parking, vehicule.getVehicule());
                                }
                            } else{
                                //construit le nouveau parking
                                updateParkingVehicule(parking, vehicule.getVehicule());
                                initPlaceParking(parking,  vehicule.getVehicule().getIdVehicule());
                                parkingOcuper.add(this.parking.getId());
                            }

                        }

                    }
                }


            }
        };
        swingWorker.execute();
    }

    private void updateParkingVehicule(Parking newParking, Vehicule vehicule) {


       removeFromPark(vehicule);
                //on augmente le taux de remplissage de ce nouveau park
                newParking.setTauxOccupation();
                //le vehicule informe le nouveau parking de son arriver, en lui demanadant une nouvelle place
                vehicule.setPlace(newParking.getNbplaceOccuper());
                vehicule.setIdParkRecepteur(newParking.getId());
                //on l'affiche sur le new park
                if(parkingOcuper.contains(newParking.getId())) this.afterNegociate(vehicule.getIdVehicule(),newParking.getId());

    }

    private void initContenaire() {
        this.contenaire = new JPanel();
        this.contenaire.setLayout(new GridLayout(1,1));
        this.tab=new JTabbedPane(JTabbedPane.TOP);
        try {
            this.police = Font.createFont(Font.TRUETYPE_FONT, new File(Config.Fonts+"font.otf")).deriveFont(Font.BOLD, 30);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.initAI();
        this.contenaire.add(this.tab);
        this.setContentPane(this.contenaire);
        this.setVisible(true);
    }
    private JPanel initChat(int indpark,int indiceVeh){
        JTextArea jta = new JTextArea(Config.ChatWidth/100, Config.ChatHeight/100);
        jta.setEditable(false);
        jta.setForeground(Color.blue);
        jta.setFont(new Font("SansSerif", Font.PLAIN, 50));
        jta.setText("Messagerie");

        this.paneldiscutions[indpark]=new JScrollPane(jta);
        this.paneldiscutions[indpark].setBackground(Color.white);
        this.paneldiscutions[indpark].setPreferredSize(new Dimension(Config.ChatWidth, Config.ChatHeight));
        this.panelChat[indpark]=new JPanel(new BorderLayout());
        this.panelChat[indpark].setPreferredSize(new Dimension(Config.ChatWidth+50, Config.ChatHeight));
        this.panelChat[indpark].add(this.paneldiscutions[indpark], BorderLayout.NORTH);
        this.panelChat[indpark].setBackground(Color.WHITE);
        return this.panelChat[indpark];
    }
    private JPanel initRoad(int indpark,int indiceVeh) {

        JPanel panelRoadChat=new JPanel(new BorderLayout());
        panelRoadChat.setBackground(Color.red);
        this.panelRoadCar[indpark] = new JPanel();
        this.panelRoadCar[indpark].setLayout(new BorderLayout());
        this.panelRoadCar[indpark].setPreferredSize(new Dimension(Config.SizePanelRoad, Config.FenetreHeight-(Config.ChatHeight+Config.SimpleButtonHeight*2)));
        Image image=this.chargeImage(Config.CheminImage+"map.jpg");
        this.panelRouteSouth[indpark] = new PanelImageFond(image);
        this.panelRouteSouth[indpark].setLayout(new GridBagLayout());
        this.panelRouteSouth[indpark].setPreferredSize(new Dimension(Config.RoadWidth, Config.RoadHight));
        this.panelRoadCar[indpark].add(this.panelRouteSouth[indpark], BorderLayout.CENTER);
        panelRoadChat.add(this.panelRoadCar[indpark],BorderLayout.SOUTH);
        panelRoadChat.add(this.initChat(indpark,indiceVeh), BorderLayout.NORTH);
        return panelRoadChat;
    }
    private Image chargeImage(String monFichierImage){
        Image image = null;
        try {
            image = ImageIO.read(new File(monFichierImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
     private void initPlaceParking(Parking parkingCourant, int indiceVehicule){
        int indpark=parkingCourant.getId();
        this.listePlace[indpark] = new ArrayList<JPanel> ();
        int nbColonneParc = parkingCourant.getNbPlace() / Config.nombreEntrerPark;
        GridLayout gridLayout = new GridLayout(10, nbColonneParc);
        gridLayout.setHgap(5);
        gridLayout.setVgap(5);
        this.panelParkingCourant[indpark]=new JPanel();
        this.panelParkingCourant[indpark].setBackground(parkingCourant.getCouleurFond());
        this.panelParkingCourant[indpark].setLayout(gridLayout);
        this.panelParkingCourant[indpark].setPreferredSize(new Dimension(parkingCourant.getDimension().getLargueur(),
                parkingCourant.getDimension().getLongueur()));
        for (int i = 0; i<nbColonneParc * Config.nombreEntrerPark; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.white);
            panel.setLayout(new BorderLayout());
            int width = parkingCourant.getPlace().getLongX();
            int height = parkingCourant.getPlace().getLongY();
            panel.setPreferredSize(new Dimension(width, height));
            JButton button = new JButton("N°" + (i + 1));
            button.setEnabled(false);
            button.setPreferredSize(new Dimension(width, height));
            panel.add(button, BorderLayout.CENTER);
            this.listePlace[indpark].add(i,panel);
            this.listeVehicule.get(indiceVehicule).getVehicule().setPlace(i);
            this.panelParkingCourant[indpark].add(panel);

        }
        this.updateTab(indpark, indiceVehicule);
    }
    private void updateTab(int indpark, int indiceVehicule){
        JPanel panelTab=new JPanel();
        panelTab.setLayout(new BorderLayout());
        this.afterNegociate(indiceVehicule,indpark);
        panelTab.add(this.panelParkingCourant[indpark], BorderLayout.WEST);
        panelTab.add(this.initRoad(indpark, indiceVehicule), BorderLayout.EAST);
        this.tab.add(listePark.get(indpark).getIntituler(),panelTab);
    }
    private void afterNegociate(int indiceVehicule,int indpark) {
        InfoVehicule infoVehiculeCourant = this.listeVehicule.get(indiceVehicule);
        ImageIcon imgVehicule = new ImageIcon(Config.CheminImage + infoVehiculeCourant.getVehicule().getPlaque() + ".png");
        JLabel label = new JLabel(imgVehicule);
        int width = listePark.get(indpark).getPlace().getLongX();
        int height = listePark.get(indpark).getPlace().getLongY();
        label.setPreferredSize(new Dimension(width, height));
        label.addMouseListener(new actionVehicule(indiceVehicule,indpark));
        JPanel panelVehicul=new JPanel();
        panelVehicul.add(label);
        int place=this.listeVehicule.get(indiceVehicule).getVehicule().getPlace();
        JPanel panel=this.listePlace[indpark].get(place);
        panel.removeAll();
        panel.add(panelVehicul);
        panel.revalidate();
    }
    private void initVehiculeOnMap(int indpark){
        DesignVehicule designVehicule=new DesignVehicule(
                listePark.get(indpark).getCouleurFond(),
                listePark.get(indpark).getNewCoordonnerX(),
                listePark.get(indpark).getNewCoordonnerY());
        designVehicule.setPreferredSize(new Dimension(25,25));
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridx=listePark.get(indpark).getNewCoordonnerX();
        gbc.gridy=listePark.get(indpark).getNewCoordonnerY();
        designVehicule.setBackground(listePark.get(indpark).getCouleurFond());
        this.panelRouteSouth[indpark].add(designVehicule,gbc);
    }
    private void removeFromPark(Vehicule vehicule){
        int indiceAncienPark=vehicule.getIdParkRecepteur();
        //if (indiceAncienPark<0)indiceAncienPark=2;
        Parking ancienPark=this.listePark.get(indiceAncienPark);
        ancienPark.diminuTauxOccupation();

        int ancienneplace=vehicule.getPlace();
        JPanel ancienePlace=this.listePlace[indiceAncienPark].get(ancienneplace);

        ancienePlace.removeAll();
        ancienePlace.setLayout(new BorderLayout());
        int width = ancienPark.getPlace().getLongX();
        int height = ancienPark.getPlace().getLongY();
        ancienePlace.setPreferredSize(new Dimension(width, height));
        JButton button = new JButton("N°" + (ancienneplace + 1));
        button.setEnabled(false);
        button.setPreferredSize(new Dimension(width, height));
        ancienePlace.add(button, BorderLayout.CENTER);
        ancienePlace.revalidate();

        JTextArea jta = new JTextArea(Config.ChatWidth/100, Config.ChatHeight/100);
        jta.setEditable(false);
        jta.setText("");
        this.paneldiscutions[indiceAncienPark].removeAll();
        this.paneldiscutions[indiceAncienPark].setViewportView(jta);
        this.panelChat[indiceAncienPark].removeAll();
        this.panelChat[indiceAncienPark].revalidate();
        this.paneldiscutions[indiceAncienPark].revalidate();
    }
    @Override
    public void update(Parking parkingCourant, int indiceVehicule) {
        this.initPlaceParking(parkingCourant, indiceVehicule);
        //on attends un peux avant de publier ce nouveau resulter
		try {
		    Thread.sleep(2000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
    }
    class actionVehicule extends MouseAdapter {

        int indicePark, indiceVeh;
        public actionVehicule(int indiceVeh, int indicePark){
            this.indicePark=indicePark;
            this.indiceVeh=indiceVeh;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            initChat(this.indicePark, this.indiceVeh,1);
        }
    }
    private JPanel initChat(int indpark,int indiceVeh, int option){

        JTextArea jta = new JTextArea(Config.ChatWidth/100, Config.ChatHeight/100);
        jta.setEditable(false);
        jta.setForeground(Color.blue);
        jta.setFont(police);
        String []tabchat=this.listeVehicule.get(indiceVeh).getVehicule().getDiscution().split("\n");
        JPanel[] paneltexte=new JPanel[tabchat.length];
        JPanel paneltextprinc=new JPanel(new GridLayout(tabchat.length*2,1));
        paneltextprinc.setBackground(Color.white);
        for (int i=1;i<tabchat.length;i++){
            JLabel texte=new JLabel();
            texte.setText(tabchat[i].substring(tabchat[i].indexOf(':')+1));
            JPanel paneltextpark=new JPanel(new BorderLayout());

            paneltexte[i]=new JPanel(new BorderLayout());
            paneltexte[i].setPreferredSize(new Dimension(Config.PanelTextMessageWidth, Config.PanelTextMessageHeight));
            JPanel panelhorimarg=new JPanel();
            panelhorimarg.setPreferredSize(new Dimension(Config.PanelTextMessageWidth, Config.PanelTextMessageHeight));
            panelhorimarg.setBackground(Color.white);
            if(tabchat[i].charAt(0)=='P'){
                texte.setForeground(Color.white);
                paneltexte[i].setBackground(Color.lightGray);
                paneltextpark.setBackground(Color.lightGray);

                texte.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.lightGray,Color.lightGray),
                        BorderFactory.createMatteBorder(2,1,2,1,Color.lightGray)));

                String prk=tabchat[i].substring(0,tabchat[i].indexOf(':'));
                ImageIcon imgVehicule =null;
                if(prk.equals("Park0"))
                    imgVehicule = new ImageIcon(Config.CheminImage +"p1.png");
                else if(prk.equals("Park1"))
                    imgVehicule = new ImageIcon(Config.CheminImage +"p2.png");
                else
                    imgVehicule = new ImageIcon(Config.CheminImage +"p3.png");
                JLabel imgv = new JLabel(imgVehicule);
                paneltextpark.add(imgv, BorderLayout.WEST);
                paneltextpark.add(texte, BorderLayout.CENTER);

                paneltexte[i].add(paneltextpark,BorderLayout.WEST);
                paneltexte[i].add(panelhorimarg, BorderLayout.CENTER);
            }else{
                texte.setForeground(Color.white);
                paneltexte[i].setBackground(Color.blue);
                paneltextpark.setBackground(Color.blue);
                texte.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.blue,Color.blue),
                        BorderFactory.createMatteBorder(2,1,2,1,Color.blue)));

                ImageIcon imgVehicule = new ImageIcon(Config.CheminImage + this.listeVehicule.get(indiceVeh).getVehicule().getPlaque() + ".png");
                JLabel imgv = new JLabel(imgVehicule);
                paneltextpark.add(imgv, BorderLayout.EAST);
                paneltextpark.add(texte, BorderLayout.CENTER);

                paneltexte[i].add(paneltextpark,BorderLayout.EAST);
                paneltexte[i].add(panelhorimarg, BorderLayout.CENTER);
            }
            JLabel labelmargintop=new JLabel();
            labelmargintop.setPreferredSize(new Dimension(10,10));
            paneltextprinc.add(labelmargintop);
            paneltextprinc.add(paneltexte[i]);
        }
        this.paneldiscutions[indpark].setViewportView(paneltextprinc);

        JButton rechercheOptimale=new JButton("Rechercher optimale");
        rechercheOptimale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                negocie(listeVehicule.get(indiceVeh),true);
            }
        });
        JPanel panelSouth=new JPanel();
        panelSouth.setPreferredSize(new Dimension(Config.SimpleButtonWidth+10,Config.SimpleButtonHeight+10));
        panelSouth.add(rechercheOptimale);
        panelSouth.setBackground(Color.white);
        this.panelChat[indpark].removeAll();
        this.panelChat[indpark].setBackground(Color.white);
        this.panelChat[indpark].setLayout(new BorderLayout());
        JTabbedPane tabinfos =new JTabbedPane(JTabbedPane.TOP);
        tabinfos.add("Infos",this.describeInfosUser(indiceVeh));
        tabinfos.add("Chat",this.paneldiscutions[indpark]);


        this.panelChat[indpark].add(tabinfos, BorderLayout.CENTER);
        this.panelChat[indpark].add(panelSouth,BorderLayout.NORTH);
        this.panelChat[indpark].setBackground(Color.WHITE);
        this.panelChat[indpark].revalidate();
        return this.panelChat[indpark];
    }
    private JPanel describeInfosUser(int indiceVeh){
        JTextArea jta = new JTextArea(Config.ChatWidth/2, Config.ChatHeight/100);
        jta.setEditable(false);
        jta.setForeground(Color.blue);
        jta.setFont(new Font("SansSerif", Font.ITALIC, 13));
        jta.setText(this.listeVehicule.get(indiceVeh).getVehicule().toString()+
                this.listeVehicule.get(indiceVeh).getUsager().describeUsager());
        JScrollPane jScrollPane =new JScrollPane();
        jScrollPane.setPreferredSize(new Dimension(Config.ChatWidth/2,Config.ChatHeight/100));
        jScrollPane.setViewportView(jta);

        Image image=this.chargeImage(Config.CheminImage + this.listeVehicule.get(indiceVeh).getVehicule().getPlaque() + "o.png");
        PanelImageFond panelImageFond = new PanelImageFond(image);
        panelImageFond.setBackground(Color.red);
        panelImageFond.setForeground(Color.red);
        panelImageFond.setPreferredSize(new Dimension(Config.ChatWidth/2, Config.ChatHeight/100));

        JPanel paneldesc=new JPanel(new BorderLayout());
        paneldesc.add(panelImageFond, BorderLayout.EAST);
        paneldesc.add(jScrollPane,BorderLayout.WEST);

        return paneldesc;
    }

    @Override
    public void sortieAlerte(String message, String titre, int option) {

        // TODO Auto-generated method stub
        new JOptionPane().showMessageDialog(null, message, titre, option, this.getIcon(option));

    }

    private Icon getIcon(int option) {
        // TODO Auto-generated method stub
        if (option == JOptionPane.INFORMATION_MESSAGE) {
            return new ImageIcon("avertissement.jpg");
        }
        return new ImageIcon("avertissement.jpg");
    }
}