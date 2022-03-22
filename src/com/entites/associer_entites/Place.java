package com.entites.associer_entites;

public class Place {
    protected String typePlace;
    protected int x;
    protected int y;

    public Place(String typePlace, int x, int y){
        this.typePlace=typePlace;
        this.x=x;
        this.y=y;
    }

    public int getLongX() {
        return x;
    }

    public int getLongY() {
        return y;
    }

    public String getTypePlace() {
        return typePlace;
    }

    public void setLongX(int x) {
        this.x = x;
    }

    public void setLongY(int y) {
        this.y = y;
    }

    public void setTypePlace(String typePlace) {
        this.typePlace = typePlace;
    }
}
