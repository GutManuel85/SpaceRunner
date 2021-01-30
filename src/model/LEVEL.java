package model;

public enum LEVEL {

    LEVEL1(5, "view/resources/stars.png", 5, 7, 6, 7,
            8, 1, 4, "LEVEL 1", "view/resources/fire00.png",
            "StarWars.mp3", 10000),
    LEVEL2(6, "view/resources/stars_green.png", 6, 13, 7, 13,
            9, 2, 5, "LEVEL 2", "view/resources/fire19.png",
            "RogueOne.mp3", 5000),
    LEVEL3(7, "view/resources/stars_red.png", 7, 20, 8, 20,
            10, 3, 6, "LEVEL 3", "view/resources/fire18.png",
            "BecomeNow.mp3", 3000);

    private int speedBackground;
    private String backgroundImagePath;
    private int speedWormhole;
    private int amountGreyMeteor;
    private int speedGreyMeteor;
    private int amountBrownMeteor;
    private int speedBrownMeteor;
    private int pointsPerStar;
    private int speedShip;
    private String nameLevel;
    private String pathShot;
    private String pathMusic;
    private int distancePill;

    LEVEL(int speedBackground, String backgroundImagePath, int speedWormhole, int amountGreyMeteor, int speedGreyMeteor,
          int amountBrownMeteor, int speedBrownMeteor, int pointsPerStar, int speedShip, String nameLevel, String nameShot,
          String pathMusic, int distancePill) {
        this.speedBackground = speedBackground;
        this.backgroundImagePath = backgroundImagePath;
        this.speedWormhole = speedWormhole;
        this.amountGreyMeteor = amountGreyMeteor;
        this.speedGreyMeteor = speedGreyMeteor;
        this.amountBrownMeteor = amountBrownMeteor;
        this.speedBrownMeteor = speedBrownMeteor;
        this.pointsPerStar = pointsPerStar;
        this.speedShip = speedShip;
        this.nameLevel = nameLevel;
        this.pathShot = nameShot;
        this.pathMusic = pathMusic;
        this.distancePill = distancePill;
    }

    public int getSpeedBackground() {
        return speedBackground;
    }

    public int getSpeedWormhole() {
        return speedWormhole;
    }

    public int getAmountGreyMeteor() {
        return amountGreyMeteor;
    }

    public int getSpeedGreyMeteor() {
        return speedGreyMeteor;
    }

    public int getAmountBrownMeteor() {
        return amountBrownMeteor;
    }

    public int getSpeedBrownMeteor() {
        return speedBrownMeteor;
    }

    public int getPointsPerStar() {
        return pointsPerStar;
    }

    public int getSpeedShip() {
        return speedShip;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public String getNameLevel() {
        return nameLevel;
    }

    public String getPathShot() {
        return pathShot;
    }

    public String getPathMusic() {
        return pathMusic;
    }

    public int getDistancePill(){
        return distancePill;
    }
}
