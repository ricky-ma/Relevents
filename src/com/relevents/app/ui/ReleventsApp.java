package com.relevents.app.ui;

import com.relevents.app.database.DatabaseConnectionHandler;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ReleventsApp extends Application{

    private DatabaseConnectionHandler dbHandler = null;

    public ReleventsApp() {
        dbHandler = new DatabaseConnectionHandler();
    }

    public void start(Stage primaryStage) throws Exception {
        HBox hbButtons = navButtons(primaryStage);

        VBox vbCenter = new VBox();
        Label title = new Label("Home");
        vbCenter.getChildren().addAll(title);

        BorderPane root = new BorderPane();
        root.setCenter(vbCenter);
        root.setBottom(hbButtons);

        Scene scene = new Scene(root, 360, 640);
        scene.getStylesheets().add("file:///stylesheet");

        primaryStage.setTitle("RELEVENTS");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
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



    public static void main(String[] args) {
        System.out.println("Hello World, Java app");
//        ReleventsApp app = new ReleventsApp();
        launch(args);
    }

}
