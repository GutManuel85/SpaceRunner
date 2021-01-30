package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;

public class ShipPicker extends VBox {

    private ImageView circleImage;
    private ImageView shipImage;

    private String circleNotChosen = "view/resources/shipChooser/grey_circle.png";
    private String circleChosen = "view/resources/shipChooser/yellow_boxTick.png";

    private SHIP ship;

    private boolean isCircleChosen;

    public ShipPicker(SHIP ship) {
        circleImage = new ImageView(circleNotChosen);
        shipImage = new ImageView(ship.getUrlShip());
        shipImage.setFitWidth(70);
        shipImage.setFitHeight(70);
        this.ship = ship;
        isCircleChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.getChildren().addAll(circleImage,shipImage);
    }

    public SHIP getShip() {
        return ship;
    }

    public boolean isCircleChosen() {
        return isCircleChosen;
    }

    public void setCircleChosen(boolean isCircleChosen) {
        this.isCircleChosen = isCircleChosen;
        String imageToSet = this.isCircleChosen ? circleChosen : circleNotChosen;
        circleImage.setImage(new Image(imageToSet));
    }
}
