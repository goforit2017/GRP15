/* Created by GRP team 15 XIN LIN(20030603)*/
package com.example.grp15;

import android.util.Log;

/**
 * this class is to generate the position of user
 * 3 float value x, y, z stand for the deviation angle in 3 axes.
 * double value phi stands for the value of roll angle
 */
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

    /**
     * this method is used to calculate the roll angle value and set phi
     */
    public void roll() {
        this.phi = Math.atan2(this.z, this.y) * 57.3;
        Log.d("TEST","what is the value of roll"+ this.phi);
    }

    /**
     * this method is used to compare current position with the standard position
     * @param z the angle value in z axis for current position
     * @param y the angle value in y axis for current position
     * @return
     */
    public double compare(float z, float y){
        double temp = Math.atan2(z, y) * 57.3;
        return this.phi-temp;
    }
}
