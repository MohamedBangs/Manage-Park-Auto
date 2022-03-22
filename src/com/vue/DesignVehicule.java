package com.vue;

import javax.swing.*;
import java.awt.*;

public class DesignVehicule extends JPanel {

    private Color color;
    private int xmap, ymap;
    DesignVehicule(Color color, int xmap, int ymap) {
        this.color=color;
        this.xmap=xmap;
        this.ymap=ymap;
    }
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(this.color);
            g.fillOval(this.xmap,this.ymap,100,100);
        }
}
