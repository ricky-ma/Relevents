package com.relevents.app.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
            ReleventsApp homeWindow = ReleventsApp.getInstance();
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
            DiscoverView discoverWindow = new DiscoverView();
            discoverWindow.start(primaryStage);
        });
        discoverButton.setPrefSize(120,50);
        hbButtons.getChildren().add(discoverButton);

        Button meButton= new Button("Me");
        meButton.setOnAction(e -> {
            MeView meWindow = new MeView();
            meWindow.start(primaryStage);
        });
        meButton.setPrefSize(120,50);
        hbButtons.getChildren().add(meButton);

        hbButtons.setAlignment(Pos.CENTER);
        return hbButtons;
    }

    public static TextField getTextField(GridPane grid, String s0, String s1, int i) {
        Label name = new Label(s0);
        grid.add(name, 0, i,1,1);
        TextField nameTextField = new TextField(s1);
        grid.add(nameTextField, 1, i);
        nameTextField.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches(".*")) ? change : null));
        return nameTextField;
    }

}
