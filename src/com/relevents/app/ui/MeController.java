package com.relevents.app.ui;

import com.relevents.app.model.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MeController extends Application {

    LoginWindow loginInst = LoginWindow.getInstance();
    ReleventsApp appInst = ReleventsApp.getInstance();

    String email = LoginWindow.getInstance().userEmail;

    User userInfo = ReleventsApp.getInstance().getDbHandler().getOneUserInfo(email);


    public void start(Stage primaryStage) {
        HBox hbButtons = ReleventsApp.navButtons(primaryStage);

        VBox vbCenter = new VBox();
        Label title = new Label("Me");
        vbCenter.getChildren().addAll(title);

        BorderPane root = new BorderPane();
        root.setCenter(vbCenter);
        root.setBottom(hbButtons);

        Scene scene = new Scene(root, 360, 640);

        primaryStage.setTitle("RELEVENTS");
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println(userInfo.getFname() + " " + userInfo.getLname());

    }

}
