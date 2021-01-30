package model;

public enum SHIP {

    BLUE("view/resources/shipChooser/playerShip1_blue.png"),
    RED("view/resources/shipChooser/playerShip2_red.png"),
    GREEN("view/resources/shipChooser/playerShip3_green.png"),
    YELLOW("view/resources/shipChooser/ufoYellow.png");

    String urlShip;

    private SHIP(String urlShip){
        this.urlShip = urlShip;
    }

    public String getUrlShip() {
        return urlShip;
    }
}
