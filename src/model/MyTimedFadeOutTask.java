package model;

import javafx.scene.image.ImageView;

import java.util.TimerTask;

public class MyTimedFadeOutTask extends TimerTask {

    private double scaleX;
    private double scaleY;
    private ImageView ship;

    public MyTimedFadeOutTask(double scaleXY, ImageView ship){
        this.scaleX = scaleXY;
        this.scaleY = scaleXY;
        this.ship = ship;
    }

    @Override
    public void run(){
        ship.setScaleX(scaleX);
        ship.setScaleY(scaleY);
    }
}
