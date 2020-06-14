package com.relevents.app.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DiscoverView extends Application {

    // This method, when called, will receive the original primary stage
    // on which a new scene will then be attached
    public void start(Stage primaryStage) {
        HBox hbButtons = GUISetup.navButtons(primaryStage);

        VBox vbCenter = new VBox();
        Label title = new Label("Discover");
        vbCenter.getChildren().addAll(title);

        BorderPane root = new BorderPane();
        root.setCenter(vbCenter);
        root.setBottom(hbButtons);

        Scene scene = new Scene(root, 360, 640);

        primaryStage.setTitle("RELEVENTS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
