package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.GameViewManager;


public class Shot {

    private ImageView shotImageView;
    private boolean isFiredStraight;
    private boolean isFiredRight;
    private boolean isFiredLeft;

    public Shot(GameViewManager gameViewManager){
        shotImageView = new ImageView(new Image(gameViewManager.getActualLevel().getPathShot()));
        isFiredLeft = gameViewManager.isLeftKeyPressed();
        isFiredRight = gameViewManager.isRightKeyPressed();
        if(!(isFiredRight || isFiredLeft)){
            isFiredStraight = true;
        }
    }

    public ImageView getShotImageView() {
        return shotImageView;
    }

    public boolean isFiredStraight() {
        return isFiredStraight;
    }

    public boolean isFiredRight() {
        return isFiredRight;
    }

    public boolean isFiredLeft() {
        return isFiredLeft;
    }
}
