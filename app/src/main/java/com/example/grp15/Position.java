package com.example.grp15;

import android.util.Log;

public class Position {
    private float x, y, z;
    private double phi;

    public Position(float valueX, float valueY, float valueZ){
        this.setX(valueX);
        this.setY(valueY);
        this.setZ(valueZ);
        this.roll();
    }

    public void setX(float value){
        this.x = value;
    }

    public void setY(float value){
        this.y = value;
    }

    public void setZ(float value){
        this.z = value;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public float getZ(){
        return this.z;
    }

    public double getPhi(){
        return this.phi;
    }

    public void roll() {
        this.phi = Math.atan2(this.z, this.y) * 57.3;
        Log.d("TEST","what is the value of roll"+ this.phi);
    }

    public double compare(float z, float y){
        double temp = Math.atan2(z, y) * 57.3;
        return this.phi-temp;
    }
}
