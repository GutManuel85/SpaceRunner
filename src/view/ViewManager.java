package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.*;
import model.ScoreList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ViewManager {

    public static SHIP chosenShipInfo;
    public static String nameCaptain;
    public static final ScoreList SCORE_LIST = new ScoreList();
    public static final BestList BEST_LIST = new BestList();

    public final Font spaceRunnerFontSmall = setSpaceRunnerFontSmall();

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int MENU_BUTTONS_START_X = 100;
    private static final int MENU_BUTTONS_START_Y = 150;
    private static final String LOGO_TEXT = "SPACERUNNER";
    private final String LOGO_FONT_PATH = "src/model/resources/1942.ttf"; // use "Path From Content Root"

    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private SpaceRunnerSubScene shipChooserSubScene;
    private SpaceRunnerSubScene scoreSubScene;
    private SpaceRunnerSubScene helpSubScene;
    private SpaceRunnerSubScene bestListSubScene;
    private SpaceRunnerSubScene exitSubScene;

    private SpaceRunnerSubScene sceneToHide;

    private List<SpaceRunnerButton> menuButtons = new ArrayList<>();

    private List<ShipPicker> shipList;
    private SHIP chosenShip;

    private Label[] labelArray = new Label[5];
    private VBox scoreVBox;
    private VBox bestListVBox;

    private final String FONT_PATH = "src/model/resources/1942.ttf"; // use "Path From Content Root"

    private Label nameCaptainLabel;
    private TextField nameCaptainTextField;


    public ViewManager() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createButtons();
        createBackground();
        createLogo();
        createSubScenes();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void showSubScene(SpaceRunnerSubScene subScene) {
        if (sceneToHide != null) {
            sceneToHide.moveSubScene(); //hide the shown subScene
        }
        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    private void createSubScenes() {
        exitSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().addAll(exitSubScene);

        createShipChooserSubScene();
        createScoreSubScene();
        createHelpSubScene();
        createBestListSubScene();
    }

    private void createShipChooserSubScene() {
        shipChooserSubScene = new SpaceRunnerSubScene(400, 400);
        mainPane.getChildren().add(shipChooserSubScene);
        InfoLabel chooseShipLabel = new InfoLabel("CAPTAIN CHOOSE YOUR SHIP");
        chooseShipLabel.setLayoutX(30);
        chooseShipLabel.setLayoutY(20);
        nameCaptainLabel = new InfoLabel("CAPTAIN FILL IN YOUR NAME");
        nameCaptainLabel.setLayoutX(30);
        nameCaptainLabel.setLayoutY(220);
        nameCaptainTextField = new TextField();
        nameCaptainTextField.setLayoutX(40);
        nameCaptainTextField.setLayoutY(280);
        nameCaptainTextField.setMinSize(320, 30);
        nameCaptainTextField.setPromptText("YOUR NAME PLEASE");
        try {
            nameCaptainTextField.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 15));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        shipChooserSubScene.getPane().getChildren().addAll(chooseShipLabel, createShipsToChoose(), nameCaptainLabel,
                nameCaptainTextField, createButtonToStart());
    }

    private void createScoreSubScene() {
        scoreSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(scoreSubScene);
        scoreVBox = new VBox();
        InfoLabel infoLabel = new InfoLabel("YOUR SCORE LIST:");
        infoLabel.setLayoutX(30);
        infoLabel.setLayoutY(20);
        scoreSubScene.getPane().getChildren().addAll(scoreVBox, infoLabel);
        scoreVBox.setLayoutX(50);
        scoreVBox.setLayoutY(80);
        scoreVBox.setPrefWidth(300);
        scoreVBox.setPrefHeight(200);
        scoreVBox.setSpacing(10);
    }

    private void createHelpSubScene() {
        helpSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(helpSubScene);
        VBox helpVBox = new VBox();
        helpVBox = new VBox();
        InfoLabel infoLabel = new InfoLabel("HOW TO PLAY:");
        infoLabel.setLayoutX(30);
        infoLabel.setLayoutY(20);
        helpVBox.setLayoutX(50);
        helpVBox.setLayoutY(100);
        helpVBox.setPrefWidth(300);
        helpVBox.setPrefHeight(200);
        helpVBox.setSpacing(10);
        Label labelUp = new Label("UP: Fly straight");
        labelUp.setFont(spaceRunnerFontSmall);
        Label labelLeft = new Label("LEFT: Fly left");
        labelLeft.setFont(spaceRunnerFontSmall);
        Label labelRight = new Label("RIGHT: Fly right");
        labelRight.setFont(spaceRunnerFontSmall);
        Label labelSpace = new Label("SPACE: Shoot");
        labelSpace.setFont(spaceRunnerFontSmall);
        Label labelStar = new Label("STAR: Get points");
        labelStar.setFont(spaceRunnerFontSmall);
        Label labelPill = new Label("PILL: Get life");
        labelPill.setFont(spaceRunnerFontSmall);

        helpVBox.getChildren().addAll(labelUp, labelLeft, labelRight, labelSpace, labelStar, labelPill);
        helpSubScene.getPane().getChildren().addAll(helpVBox, infoLabel);
    }

    private void createBestListSubScene(){
        bestListSubScene = new SpaceRunnerSubScene();
        bestListVBox = new VBox();
        InfoLabel infoLabel = new InfoLabel("BEST CAPTAINS EVER:");
        infoLabel.setLayoutX(30);
        infoLabel.setLayoutY(20);
        bestListSubScene.getPane().getChildren().addAll(bestListVBox, infoLabel);
        bestListVBox.setLayoutX(50);
        bestListVBox.setLayoutY(80);
        bestListVBox.setPrefWidth(300);
        bestListVBox.setPrefHeight(200);
        bestListVBox.setSpacing(10);
        mainPane.getChildren().add(bestListSubScene);

    }


    private SpaceRunnerButton createButtonToStart() {
        SpaceRunnerButton startButton = new SpaceRunnerButton("START");
        startButton.setLayoutX(180);
        startButton.setLayoutY(320);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (chosenShip != null) {
                    if (!nameCaptainTextField.getText().isEmpty()) {
                        nameCaptain = nameCaptainTextField.getText();
                    } else {
                        nameCaptain = "Luke Skywaker";
                    }
                    GameViewManager gameManager = new GameViewManager();
                    gameManager.createNewGame(mainStage, chosenShip, LEVEL.LEVEL1, 0);
                }
            }
        });
        return startButton;
    }

    private HBox createShipsToChoose() { //Todo: Verstehe ich nicht
        shipList = new ArrayList<>();
        HBox box = new HBox();
        box.setSpacing(15);
        for (SHIP ship : SHIP.values()) { //Returns an array containing the constants of this enum type, in the order they're declared
            ShipPicker shipToPick = new ShipPicker(ship);
            shipList.add(shipToPick);
            box.getChildren().add(shipToPick);
            shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for (ShipPicker ship : shipList) {
                        ship.setCircleChosen(false);
                    }
                    shipToPick.setCircleChosen(true);
                    chosenShip = shipToPick.getShip();
                    chosenShipInfo = chosenShip;
                }
            });
        }
        box.setLayoutX(40);
        box.setLayoutY(80);
        return box;
    }

    private void addMenuButton(SpaceRunnerButton button) {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 80); //Number defines the VGap between the buttons
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons() {
        createStartButton();
        createScoresButton();
        createHelpButton();
        createBestListButton();
        createExitButton();
    }

    private void createStartButton() {
        SpaceRunnerButton startButton = new SpaceRunnerButton("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(shipChooserSubScene);
            }
        });
    }

    private void createScoresButton() {
        SpaceRunnerButton scoresButton = new SpaceRunnerButton("SCORES");
        addMenuButton(scoresButton);

        scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scoreVBox.getChildren().clear();
                SCORE_LIST.getRanking();
                int j = 0;
                for (int i = SCORE_LIST.getList().size() - 1; i >= 0; i--) {
                    Label label = new Label(SCORE_LIST.getList().get(i).toString());
                    try {
                        label.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (j < 5) {
                        labelArray[j] = label;
                        scoreVBox.getChildren().add(labelArray[j]);
                    }
                    j++;
                }
                showSubScene(scoreSubScene);
            }
        });
    }

    private void createHelpButton() {
        SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(helpSubScene);
            }
        });
    }


    private void createBestListButton() {
        SpaceRunnerButton bestListButton = new SpaceRunnerButton("BEST LIST");
        addMenuButton(bestListButton);

        bestListButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bestListVBox.getChildren().clear();
                BEST_LIST.readBestListCsvIntoImportList("C:\\JavaProjects\\04_JavaFX\\SpaceRunner\\src\\model\\resources\\BestList.csv");
                ArrayList<ScoreListEntry> array = BEST_LIST.convertImportListToExportList();
                for(int i = 0; i < array.size() && i < 7 ; i++){
                    Label label = new Label(array.get(i).toString());
                    try {
                        label.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bestListVBox.getChildren().add(label);
                }
                showSubScene(bestListSubScene);
            }
        });
    }

    private void createExitButton() {
        SpaceRunnerButton exitButton = new SpaceRunnerButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    private void createBackground() {
        Image backgroundImage = new Image("view/resources/galaxyBaum.png", 800, 600, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, null, null,
                BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

    private void createLogo() {
        Label labelLogo = new Label(LOGO_TEXT);
        labelLogo.setLayoutX(330);
        labelLogo.setLayoutY(30);
        try {
            labelLogo.setFont(Font.loadFont(new FileInputStream(LOGO_FONT_PATH), 60));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        labelLogo.setTextFill(Color.GOLD);
        labelLogo.setEffect(new DropShadow());

        mainPane.getChildren().add(labelLogo);
    }

    private Font setSpaceRunnerFontSmall() {
        Font font;
        try {
            font = Font.loadFont(new FileInputStream("src/model/resources/1942.ttf"), 15);
        } catch (FileNotFoundException e) {
            font = new Font("Arial", 15);
        }
        return font;
    }

    public void play(SHIP chosenShip, LEVEL level, int points) {
        GameViewManager.shotCounter = 10;
        GameViewManager gameManager = new GameViewManager();
        gameManager.chooseMusic();
        gameManager.createNewGame(mainStage, chosenShip, level, points);
    }
}
