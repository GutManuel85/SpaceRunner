package view;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.*;
import model.MyTimedFadeOutTask;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class GameViewManager {

    public static LEVEL actualLevel = LEVEL.LEVEL1;
    public static int points = 0;
    public static int shotCounter = 10;

    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 650;

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private Stage menuStage;
    private ImageView ship;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private int angle;
    private AnimationTimer gameTimer;

    private GridPane gridPane1;
    private GridPane gridPane2;

    private ImageView[] brownMeteors1;
    private ImageView[] greyMeteors1;
    private ImageView[] stars;
    Random randomPositionGenerator;

    private ImageView pill;
    private ImageView wormwhole;
    private PointsLabel pointsLabel;
    private ImageView[] playerLives;
    private int playerLife;
    private ImageView lifeImage;
    private boolean isInWormhole = false;

    private static final String METEOR_BROWN_IMAGE1 = "view/resources/meteorBrown_small1.png";
    private static final String METEOR_GREY_IMAGE1 = "view/resources/meteorGrey_med1.png";
    private static final String GOLD_STAR_PATH = "view/resources/star_gold.png";
    private static final String PILL_PATH = "view/resources/pill_yellow.png";
    private static final String WORMHOLE_PATH = "view/resources/wormholeGraphik.png";

    private static final int STAR_RADIUS = 12;
    private static final int PILL_RADIUS = 12;
    private static final int SHIP_RADIUS = 27;
    private static final int METEOR_BROWN_1_RADIUS = 15;
    private static final int METEOR_GREY_1_RADIUS = 25;
    private static final int WORMHOLE_RADIUS = 300;
    private static final int SHOT_RADIUS = 3;

    private URL resourceStarWarsAudio;
    private MediaPlayer mediaPlayer;

    private final URL resourceStarImpactAudio = getClass().getResource(AUDIO.STAR_IMPACT.getPath());
    private MediaPlayer mediaPlayerStartImpact;

    private final URL resourcePillImpactAudio = getClass().getResource(AUDIO.PILL_IMPACT.getPath());
    private MediaPlayer mediaPlayerPillImpact;

    private final URL resourceMeteorImpactAudio = getClass().getResource(AUDIO.METEOR_IMPACT.getPath());
    private MediaPlayer mediaPlayerMeteorImpact;

    private final String FONT_PATH = "src/model/resources/1942.ttf"; // use "Path From Content Root"

    private boolean isGameFinished = false;

    private SpaceRunnerSubScene nextLevelSubScene;
    private SpaceRunnerButton nextLevelButton;
    private int shipInWormholeRotation = 5;

    private LEVEL nextLevel;

    private ArrayList<Shot> shots = new ArrayList<>();
    PointsLabel shotsLeftLabel;
    ImageView shotsLeft;

    public GameViewManager() {
        initializeStage();
        createKeyListeners();
        randomPositionGenerator = new Random();
    }

    public void createNewGame(Stage menuStage, SHIP chosenShip, LEVEL level, int newPoints) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        actualLevel = level;
        createBackground(); //Wichtig: Muss vor der Methode createShip() stehen, da man sonst Schiff nicht sieht.
        createGameElementWormhole();
        createGameElements(chosenShip);
        createShip(chosenShip);
        createGameLoop();
        points = newPoints;
        chooseMusic();
        this.gameStage.show();
    }

    public void chooseMusic() {
        resourceStarWarsAudio = getClass().getResource(actualLevel.getPathMusic());
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = new MediaPlayer(new Media(resourceStarWarsAudio.toString()));
        mediaPlayer.play();

    }

    public boolean isLeftKeyPressed() {
        return isLeftKeyPressed;
    }

    public boolean isRightKeyPressed() {
        return isRightKeyPressed;
    }

    public int getAngle() {
        return angle;
    }

    public LEVEL getActualLevel() {
        return actualLevel;
    }

    private void createGameElementWormhole() {
        wormwhole = new ImageView(new Image(WORMHOLE_PATH));
        wormwhole.setFitWidth(500);
        wormwhole.setFitHeight(500);
        setNewWormholePosition(wormwhole);
        gamePane.getChildren().addAll(wormwhole);
    }

    private void createGameElements(SHIP chosenShip) {
        playerLife = 3;

        pill = new ImageView(new Image(PILL_PATH));
        setNewPillPosition(pill);
        gamePane.getChildren().addAll(pill);

        brownMeteors1 = new ImageView[actualLevel.getAmountBrownMeteor()];

        for (int i = 0; i < brownMeteors1.length; i++) {
            brownMeteors1[i] = new ImageView((METEOR_BROWN_IMAGE1));
            setNewElementPosition(brownMeteors1[i]);
            gamePane.getChildren().add(brownMeteors1[i]);
        }

        greyMeteors1 = new ImageView[actualLevel.getSpeedGreyMeteor()];
        for (int i = 0; i < greyMeteors1.length; i++) {
            greyMeteors1[i] = new ImageView((METEOR_GREY_IMAGE1));
            setNewElementPosition(greyMeteors1[i]);
            gamePane.getChildren().add(greyMeteors1[i]);
        }

        stars = new ImageView[5];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new ImageView(GOLD_STAR_PATH);
            setNewElementPosition(stars[i]);
            gamePane.getChildren().add(stars[i]);
        }

        pointsLabel = new PointsLabel("POINTS: " + points);
        pointsLabel.setLayoutX(400);
        pointsLabel.setLayoutY(20);
        gamePane.getChildren().add(pointsLabel); //has to be after ...add(meteors / star / pill) so that it is in the front

        playerLives = new ImageView[playerLife];
        for (int i = 0; i < playerLives.length; i++) {
            lifeImage = new ImageView(chosenShip.getUrlShip());
            lifeImage.setFitHeight(50);
            lifeImage.setFitWidth(50);
            playerLives[i] = lifeImage;
            playerLives[i].setLayoutX(215 + (i * 60));
            playerLives[i].setLayoutY(20);
            gamePane.getChildren().add(playerLives[i]); //has to be after ...add(meteors / star / pill) so that it is in the front
        }

        shotsLeftLabel = new PointsLabel("Shots: " + shotCounter + "x");
        shotsLeftLabel.setLayoutX(10);
        shotsLeftLabel.setLayoutY(20);
        shotsLeft = new ImageView(new Image(actualLevel.getPathShot()));
        shotsLeft.setFitHeight(30);
        shotsLeft.setLayoutX(120);
        shotsLeft.setLayoutY(28);
        gamePane.getChildren().addAll(shotsLeftLabel, shotsLeft);

    }


    private void createKeyListeners() {
        GameViewManager thisGameViewManager = this;
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;
                    isRightKeyPressed = false;
                } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = true;
                    isLeftKeyPressed = false;
                } else if (keyEvent.getCode() == KeyCode.UP) {
                    isRightKeyPressed = false;
                    isLeftKeyPressed = false;
                } else if (keyEvent.getCode() == KeyCode.SPACE) {
                    shoot(new Shot(thisGameViewManager));
                }
            }
        });
    }

    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    private void createShip(SHIP chosenShip) {
        ship = new ImageView(chosenShip.getUrlShip());
        ship.setLayoutX(GAME_WIDTH / 2.0 - 50);
        ship.setLayoutY(GAME_HEIGHT - 150);
        gamePane.getChildren().add(ship);
    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (!isGameFinished) {
                    moveBackground();
                    moveShots();
                    moveShip();
                    moveGameElement();
                    checkIfElementsAreBehindTheShipAndRelocate();
                    checkIfElementsCollide();
                } else {
                    moveBackground();
                }
            }
        };
        gameTimer.start();
    }

    private void moveShip() {
        if (!isInWormhole) {
            if (!isRightKeyPressed && isLeftKeyPressed) {
                if (angle > -30) { //Boarder for rotation to the left
                    angle -= 5;
                }
                ship.setRotate(angle);
                if (ship.getLayoutX() > -20) { //Boarder for movement to the left
                    ship.setLayoutX(ship.getLayoutX() - actualLevel.getSpeedShip());
                }

            }
            if (isRightKeyPressed && !isLeftKeyPressed) {
                if (angle < 30) { //Boarder for rotation to the right
                    angle += 5;
                }
                ship.setRotate(angle);
                if (ship.getLayoutX() < GAME_WIDTH - (GAME_WIDTH / 2.0 - 50) + 170) { //Boarder for movement to the right
                    ship.setLayoutX(ship.getLayoutX() + actualLevel.getSpeedShip());
                }
            }

            if (isRightKeyPressed && isLeftKeyPressed) {
                if (angle < 0) {
                    angle += 5;
                } else if (angle > 0) {
                    angle -= 5;
                }
                ship.setRotate(angle);

            }
            if (!isRightKeyPressed && !isLeftKeyPressed) {
                if (angle < 0) {
                    angle += 5;
                } else if (angle > 0) {
                    angle -= 5;
                }
                ship.setRotate(angle);
            }
        } else {
            shipInWormholeRotation += 5;
            ship.setRotate(shipInWormholeRotation);
        }
    }

    private void shoot(Shot shot) {
        if (shotCounter > 0) {
            shots.add(shot);
            if(shot.isFiredStraight()) {
                shot.getShotImageView().setLayoutX(ship.getLayoutX() + 40);
                shot.getShotImageView().setLayoutY(ship.getLayoutY() - 35);
            }
            if(shot.isFiredLeft()){
                shot.getShotImageView().setLayoutX(ship.getLayoutX() + 8);
                shot.getShotImageView().setLayoutY(ship.getLayoutY() - 35);
                shot.getShotImageView().setRotate(-30);
            }
            if(shot.isFiredRight()){
                shot.getShotImageView().setLayoutX(ship.getLayoutX() + 72);
                shot.getShotImageView().setLayoutY(ship.getLayoutY() - 35);
                shot.getShotImageView().setRotate(30);
            }
            gamePane.getChildren().add(shot.getShotImageView());
            shotCounter--;
            shotsLeftLabel.setText("Shots: " + shotCounter + "x");
        }
    }

    private void moveShots() {
        for (Shot shot : shots) {
            if(shot.isFiredStraight()) {
                shot.getShotImageView().setLayoutY(shot.getShotImageView().getLayoutY() - 15);
            }
            if(shot.isFiredLeft()){
                shot.getShotImageView().setLayoutY(shot.getShotImageView().getLayoutY() - 15*Math.sin(Math.toRadians(50)));
                shot.getShotImageView().setLayoutX(shot.getShotImageView().getLayoutX() - 15*Math.cos(Math.toRadians(50)));
            }
            if(shot.isFiredRight()){
                shot.getShotImageView().setLayoutY(shot.getShotImageView().getLayoutY() - 15*Math.sin(Math.toRadians(50)));
                shot.getShotImageView().setLayoutX(shot.getShotImageView().getLayoutX() + 15*Math.cos(Math.toRadians(50)));
            }
        }
    }

    private void createBackground() {
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();

        for (int i = 0; i < 12; i++) {
            ImageView backgroundImage1 = new ImageView(actualLevel.getBackgroundImagePath());
            ImageView backgroundImage2 = new ImageView(actualLevel.getBackgroundImagePath());
            backgroundImage1.setFitHeight(800);
            backgroundImage2.setFitHeight(800);

            GridPane.setConstraints(backgroundImage1, i % 3, i / 3);
            GridPane.setConstraints(backgroundImage2, i % 3, i / 3);
            gridPane1.getChildren().add(backgroundImage1);
            gridPane2.getChildren().add(backgroundImage2);
        }
        gridPane2.setLayoutY(-800);
        gamePane.getChildren().addAll(gridPane1, gridPane2);
    }

    private void moveBackground() {
        int backGroundSpeed = actualLevel.getSpeedBackground();

        if (isInWormhole) {
            backGroundSpeed = 2;
        }

        gridPane1.setLayoutY(gridPane1.getLayoutY() + backGroundSpeed);
        gridPane2.setLayoutY(gridPane2.getLayoutY() + backGroundSpeed);

        if (gridPane1.getLayoutY() >= 800) {
            gridPane1.setLayoutY(-800);
        }
        if (gridPane2.getLayoutY() >= 800) {
            gridPane2.setLayoutY(-800);
        }
    }

    private void removeLife() {
        gamePane.getChildren().remove(playerLives[playerLife - 1]);
        playerLife--;
        if (playerLife <= 0) {
            finishGame();
        }
    }

    private void finishGame() {
        isGameFinished = true;
        createScoreSubScene();
        ScoreListEntry scoreListEntry = new ScoreListEntry(points, ViewManager.nameCaptain);
        ViewManager.SCORE_LIST.addToScoreList(scoreListEntry);
        ViewManager.BEST_LIST.addToBestListCsv(scoreListEntry,"C:\\JavaProjects\\04_JavaFX\\SpaceRunner\\src\\model\\resources\\BestList.csv");
        gamePane.getChildren().removeAll(stars);
        gamePane.getChildren().removeAll(brownMeteors1);
        gamePane.getChildren().removeAll(greyMeteors1);
        gamePane.getChildren().removeAll(pill, pointsLabel, ship, wormwhole);
        gamePane.getChildren().removeAll(shots);
        gamePane.getChildren().removeAll(shotsLeftLabel, shotsLeft);
    }

    private void addLife() {
        if (playerLife < 3) {
            playerLife++;
            int i = playerLife - 1;
            playerLives[i].setLayoutX(215 + (i * 60));
            playerLives[i].setLayoutY(20);
            gamePane.getChildren().add(playerLives[i]);
        }
    }

    private void checkIfElementsCollide() { //ToDo: Die +37 +49 +15 verstehe ich noch nicht ganz
        if (!isInWormhole) {
            if (SHIP_RADIUS + PILL_RADIUS > calculateDistance(ship.getLayoutX() + 49, pill.getLayoutX() + 15,
                    ship.getLayoutY() + 37, pill.getLayoutY() + 15)) {
                setNewElementPosition(pill);
                addLife();
                mediaPlayerPillImpact = new MediaPlayer(new Media(resourcePillImpactAudio.toString()));
                mediaPlayerPillImpact.play();
            }

            for (int i = 0; i < brownMeteors1.length; i++) {
                if (SHIP_RADIUS + METEOR_BROWN_1_RADIUS > calculateDistance(
                        ship.getLayoutX() + 49, brownMeteors1[i].getLayoutX() + 15,
                        ship.getLayoutY() + 37, brownMeteors1[i].getLayoutY() + 15)) {
                    setNewElementPosition(brownMeteors1[i]);
                    removeLife();
                    mediaPlayerMeteorImpact = new MediaPlayer(new Media(resourceMeteorImpactAudio.toString()));
                    mediaPlayerMeteorImpact.play();
                }
            }

            for (int i = 0; i < greyMeteors1.length; i++) {
                if (SHIP_RADIUS + METEOR_GREY_1_RADIUS > calculateDistance(
                        ship.getLayoutX() + 49, greyMeteors1[i].getLayoutX() + 20,
                        ship.getLayoutY() + 37, greyMeteors1[i].getLayoutY() + 20)) {
                    setNewElementPosition(greyMeteors1[i]);
                    removeLife();
                    mediaPlayerMeteorImpact = new MediaPlayer(new Media(resourceMeteorImpactAudio.toString()));
                    mediaPlayerMeteorImpact.play();
                }
            }

            for (int i = 0; i < stars.length; i++) {
                if (SHIP_RADIUS + STAR_RADIUS > calculateDistance(ship.getLayoutX() + 49, stars[i].getLayoutX() + 15,
                        ship.getLayoutY() + 37, stars[i].getLayoutY() + 15)) {
                    points = points + actualLevel.getPointsPerStar();
                    setNewElementPosition(stars[i]);
                    pointsLabel.setText("POINTS: " + points);
                    mediaPlayerStartImpact = new MediaPlayer(new Media(resourceStarImpactAudio.toString()));
                    mediaPlayerStartImpact.play();
                }
            }
            for (int i = 0; i < shots.size(); i++) {
                for (ImageView meteor : brownMeteors1) {
                    if (SHOT_RADIUS + METEOR_BROWN_1_RADIUS > calculateDistance(shots.get(i).getShotImageView().getLayoutX() + 10,
                            meteor.getLayoutX() + 15, shots.get(i).getShotImageView().getLayoutY() + 10,
                            meteor.getLayoutY() + 15)) {
                        setNewElementPosition(meteor);
                        shots.get(i).getShotImageView().setLayoutX(-100);
                        gamePane.getChildren().remove(shots.get(i));
                    }
                }
            }

            for (int i = 0; i < shots.size(); i++) {
                for (ImageView meteor : greyMeteors1) {
                    if (SHOT_RADIUS + METEOR_GREY_1_RADIUS > calculateDistance(shots.get(i).getShotImageView().getLayoutX() + 10,
                            meteor.getLayoutX() + 20, shots.get(i).getShotImageView().getLayoutY() + 10,
                            meteor.getLayoutY() + 20)) {
                        setNewElementPosition(meteor);
                        shots.get(i).getShotImageView().setLayoutX(-100);
                        gamePane.getChildren().remove(shots.get(i));
                    }
                }
            }

            if (SHIP_RADIUS + WORMHOLE_RADIUS > calculateDistance(ship.getLayoutX() + 49, wormwhole.getLayoutX() + 300,
                    ship.getLayoutY() + 37, wormwhole.getLayoutY() + 200)) {
                isInWormhole = true;
                for (int i = 10; i > 0; i--) {
                    Timer timer = new Timer();
                    MyTimedFadeOutTask timerTask = new MyTimedFadeOutTask(i / 10d - 0.1, ship);
                    timer.schedule(timerTask, (11 - i) * 100);
                }
                mediaPlayerMeteorImpact = new MediaPlayer(new Media(resourceMeteorImpactAudio.toString()));
                mediaPlayerMeteorImpact.play();
                gamePane.getChildren().remove(brownMeteors1);
                gamePane.getChildren().remove(greyMeteors1);
                gamePane.getChildren().remove(stars);
                gamePane.getChildren().remove(pill);
                createNextLevelSubScene();
            }
        }

    }

    private double calculateDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)); //Pythagoras for calculating the distance between to points
    }

    private void moveGameElement() {
        pill.setLayoutY(pill.getLayoutY() + 6);
        pill.setRotate(pill.getRotate() + 7);

        wormwhole.setLayoutY(wormwhole.getLayoutY() + actualLevel.getSpeedWormhole());

        for (int i = 0; i < stars.length; i++) {
            stars[i].setLayoutY(stars[i].getLayoutY() + 6);
            stars[i].setRotate(stars[i].getRotate() + 7);
        }

        for (int i = 0; i < brownMeteors1.length; i++) {
            brownMeteors1[i].setLayoutY(brownMeteors1[i].getLayoutY() + actualLevel.getSpeedBrownMeteor());
            brownMeteors1[i].setRotate(brownMeteors1[i].getRotate() + 7);
        }
        for (int i = 0; i < greyMeteors1.length; i++) {
            greyMeteors1[i].setLayoutY(greyMeteors1[i].getLayoutY() + actualLevel.getSpeedGreyMeteor());
            greyMeteors1[i].setRotate(greyMeteors1[i].getRotate() + 4);
        }
    }

    private void checkIfElementsAreBehindTheShipAndRelocate() {

        for (int i = 0; i < stars.length; i++) {
            if (stars[i].getLayoutY() > 900) {
                setNewElementPosition(stars[i]);
            }
        }

        if (pill.getLayoutY() > 900) {
            setNewPillPosition(pill);
        }

        if (wormwhole.getLayoutY() > 900) {
            setNewWormholePosition(wormwhole);
        }

        for (int i = 0; i < brownMeteors1.length; i++) {
            if (brownMeteors1[i].getLayoutY() > 900) {
                setNewElementPosition(brownMeteors1[i]);
            }
        }
        for (int i = 0; i < greyMeteors1.length; i++) {
            if (greyMeteors1[i].getLayoutY() > 900) {
                setNewElementPosition(greyMeteors1[i]);
            }
        }
    }

    private void setNewElementPosition(ImageView imageView) {
        imageView.setLayoutX(600 - randomPositionGenerator.nextInt(500) - 50);
        imageView.setLayoutY(-(randomPositionGenerator.nextInt(3200 + GAME_HEIGHT)));
    }

    private void setNewPillPosition(ImageView imageView) {
        imageView.setLayoutX(600 - randomPositionGenerator.nextInt(500) - 50);
        imageView.setLayoutY(-(actualLevel.getDistancePill() + GAME_HEIGHT));
    }

    private void setNewWormholePosition(ImageView imageView) {
        if (actualLevel != LEVEL.LEVEL3) //Level 3 is endless
            imageView.setLayoutX(50);
        imageView.setLayoutY(-(10_000 + GAME_HEIGHT));
    }


    private void createScoreSubScene() {
        SpaceRunnerSubScene scoreSubScene = new SpaceRunnerSubScene();
        gamePane.getChildren().add(scoreSubScene);
        Label scoreLabel = new Label("YOUR SCORE: " + points);
        try {
            scoreLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scoreLabel.setPrefSize(350, 100);
        scoreLabel.setLayoutX(25);
        scoreLabel.setLayoutY(40);
        scoreLabel.setAlignment(Pos.CENTER);
        scoreSubScene.getPane().getChildren().add(scoreLabel);

        scoreSubScene.setLayoutX(100);
        scoreSubScene.setLayoutY(200);

        SpaceRunnerButton backToMainButton = new SpaceRunnerButton("RETURN");
        backToMainButton.setLayoutX(100);
        backToMainButton.setLayoutY(125);
        scoreSubScene.getPane().getChildren().add(backToMainButton);

        backToMainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backToMain();
            }
        });

        SpaceRunnerButton playAgainButton = new SpaceRunnerButton("PLAY AGAIN");
        playAgainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actualLevel = LEVEL.LEVEL1;
                points = 0;
                playAgain(actualLevel);
            }
        });

        playAgainButton.setLayoutX(100);
        playAgainButton.setLayoutY(200);
        scoreSubScene.getPane().getChildren().add(playAgainButton);
    }

    private void createNextLevelSubScene() {

        nextLevel = LEVEL.LEVEL1;
        if (actualLevel == LEVEL.LEVEL1) {
            nextLevel = LEVEL.LEVEL2;
        }
        if (actualLevel == LEVEL.LEVEL2) {
            nextLevel = LEVEL.LEVEL3;
        }
        if (actualLevel == LEVEL.LEVEL3) {
            nextLevel = LEVEL.LEVEL3;
        }

        nextLevelSubScene = new SpaceRunnerSubScene();
        gamePane.getChildren().add(nextLevelSubScene);
        nextLevelSubScene.setLayoutX(100);
        nextLevelSubScene.setLayoutY(200);
        InfoLabel wellDoneLabel = new InfoLabel("WELL DONE " + ViewManager.nameCaptain);
        wellDoneLabel.setLayoutX(25);
        wellDoneLabel.setLayoutY(40);
        InfoLabel goToNextLevelLabel = new InfoLabel("YOU ARE READY FOR " + nextLevel.getNameLevel());
        goToNextLevelLabel.setLayoutX(25);
        goToNextLevelLabel.setLayoutY(100);
        nextLevelButton = new SpaceRunnerButton(nextLevel.getNameLevel());
        nextLevelButton.setLayoutX(100);
        nextLevelButton.setLayoutY(180);
        nextLevelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actualLevel = nextLevel;
                mediaPlayer.stop();
                playAgain(actualLevel);
            }
        });
        nextLevelSubScene.getPane().getChildren().addAll(wellDoneLabel, goToNextLevelLabel, nextLevelButton);

    }

    private void backToMain() {
        gameStage.close();
        menuStage.show();
        mediaPlayer.stop();
    }

    private void playAgain(LEVEL level) {
        gameStage.close();
        mediaPlayer.stop();
        ViewManager viewManager = new ViewManager();
        shotCounter = 10;
        addLife();
        addLife();
        if (!isInWormhole) {
            viewManager.play(ViewManager.chosenShipInfo, level, 0);
        } else {
            viewManager.play(ViewManager.chosenShipInfo, level, points);
        }
    }
}
