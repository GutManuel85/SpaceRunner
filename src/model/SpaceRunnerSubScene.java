package model;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.util.Duration;

public class SpaceRunnerSubScene extends SubScene {

    private static final String BACKGROUND_IMAGE = "model/resources/yellow_panel.png";

    private boolean isHidden = true;

    public SpaceRunnerSubScene() {
        this(400, 300);
    }

    public SpaceRunnerSubScene(int prefWidth, int prefHight){
        super(new AnchorPane(), prefWidth, prefHight);
        prefWidth(prefWidth);
        prefHeight(prefHight);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, prefWidth, prefHight, false, true),
                null, null, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setBackground(new Background(image));
        setLayoutX(800);
        setLayoutY(150);

    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);
        if (isHidden) {
            transition.setToX(-450);
            isHidden = false;
        } else {
            transition.setToX(0); //set back to the start position
            isHidden = true;
        }
        transition.play();
    }

    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }
}
