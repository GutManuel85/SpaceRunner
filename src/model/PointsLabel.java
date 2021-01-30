package model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.FileHandler;

public class PointsLabel extends Label {

    protected static final String FONT_PATH = "src/model/resources/1942.ttf"; // use "Path From Content Root"

    public PointsLabel(String text) {
        setPrefWidth(200);
        setPrefHeight(50);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("view/resources/shipChooser/yellow_button13.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10,10,10,10));
        setText(text);
        setLabelFont();
    }

    private void setLabelFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH),15));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
