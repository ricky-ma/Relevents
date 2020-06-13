package com.relevents.app.ui;

import com.relevents.app.database.DatabaseConnectionHandler;
import com.relevents.app.model.CityUser;
import com.relevents.app.model.Event;
import com.relevents.app.model.Location;
import com.relevents.app.model.Organization;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.util.Arrays;

public class ReleventsApp extends Application {

    private static volatile ReleventsApp instance;
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
        primaryStage.show();


        // testing database connection
        boolean didConnect = dbHandler.login("ora_brucecui", "a13412151");
        if (didConnect) {


            Timestamp t1 = new Timestamp(2020,6,20,14,0,0,0);
            Timestamp t2 = new Timestamp(2020,6,21,14,0,0,0);
            Event e1 = new Event(13, "party", t1, t2, "www.events.com", "description", 1);
            dbHandler.insertEvent(e1);

            Event[] eventInfoTable = dbHandler.getEventInfo();
            System.out.println(Arrays.toString(eventInfoTable));

            dbHandler.deleteEvent(13);
            Event[] eventInfoTable2 = dbHandler.getEventInfo();
            System.out.println(Arrays.toString(eventInfoTable2));

            Event[] eventInfoTable3 = dbHandler.earliestEvent();
            System.out.println(Arrays.toString(eventInfoTable3));

            Location[] locationInfoTable = dbHandler.getLocationInfo();
            System.out.println(Arrays.toString(locationInfoTable));

            Organization[] organizationInfoTable = dbHandler.getOrganizationInfo();
            System.out.println(Arrays.toString(organizationInfoTable));

            CityUser[] cityUserInfoTable = dbHandler.cityUsers();
            System.out.println(Arrays.toString(cityUserInfoTable));

        }
        ReleventsApp app = new ReleventsApp();
        app.dbHandler = dbHandler;
        instance = app;

//        dbHandler.close();
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

    public DatabaseConnectionHandler getDbHandler() {
        return dbHandler;
    }

    public static ReleventsApp getInstance() {
        if (instance == null ) {
            instance = new ReleventsApp();
        }
        return instance;
    }



    public static void main(String[] args) throws Exception{
        System.out.println("Hello World, Java app");
//        ReleventsApp app = new ReleventsApp();
        launch(LoginWindow.class, args);

    }

}
