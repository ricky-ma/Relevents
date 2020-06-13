package com.relevents.app.ui;

import com.relevents.app.model.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MeController extends Application {

    LoginWindow loginInst = LoginWindow.getInstance();
    ReleventsApp appInst = ReleventsApp.getInstance();

    String email = LoginWindow.getInstance().userEmail;
    User userInfo = ReleventsApp.getInstance().getDbHandler().getOneUserInfo(email);


    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane grid = GUISetup.setNewGrid();
        HBox hbButtons = GUISetup.navButtons(primaryStage);
        root.setBottom(hbButtons);
        root.setCenter(grid);

        GUISetup.setSceneTitle(grid, userInfo.getFname() + " " + userInfo.getLname(), FontWeight.EXTRA_BOLD, 2);
        displayUserInfo(grid);

        Scene scene = new Scene(root, 360, 640);

        primaryStage.setTitle("RELEVENTS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayUserInfo(GridPane grid) {
        Text phone = new Text("PHONE: " + userInfo.getPhone());
        phone.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        Text city = new Text("LOCATION: " + userInfo.getCityID());
        city.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        Text birthdate = new Text("BIRTHDATE: " + userInfo.getBirthdate());
        birthdate.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        Text email = new Text("EMAIL: " + userInfo.getEmail());
        email.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        // TODO: use cityID to get cityName

        grid.add(phone, 0, 1, 2, 1);
        grid.add(city, 0, 2, 2, 1);
        grid.add(email, 0, 3, 2, 1);
        grid.add(birthdate, 0, 4, 2, 1);
        grid.add(email, 0, 5, 2, 1);
    }

}
