package com.relevents.app.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUISetup {

    public static void setSceneTitle(GridPane grid, String s, FontWeight bold, int i) {
        Text sceneTitle = new Text(s);
        sceneTitle.setFont(Font.font("Helvetica", bold, 30));
        grid.add(sceneTitle, 0, 0, i, 1);
    }

    public static Scene setNewScene(GridPane grid, int width, int height) {
        Scene scene = new Scene(grid, width, height);
        return scene;
    }

    public static GridPane setNewGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        return grid;
    }

    public static HBox navButtons(Stage primaryStage) {
        HBox hbButtons = new HBox();

        Button homeButton= new Button("Home");
        homeButton.setOnAction(e -> {
            ReleventsApp homeWindow = new ReleventsApp();
            try {
                homeWindow.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        homeButton.setPrefSize(120,50);
        hbButtons.getChildren().add(homeButton);

        Button discoverButton= new Button("Discover");
        discoverButton.setOnAction(e -> {
            DiscoverController discoverWindow = new DiscoverController();
            discoverWindow.start(primaryStage);
        });
        discoverButton.setPrefSize(120,50);
        hbButtons.getChildren().add(discoverButton);

        Button meButton= new Button("Me");
        meButton.setOnAction(e -> {
            MeController meWindow = new MeController();
            meWindow.start(primaryStage);
        });
        meButton.setPrefSize(120,50);
        hbButtons.getChildren().add(meButton);

        hbButtons.setAlignment(Pos.CENTER);
        return hbButtons;
    }

}