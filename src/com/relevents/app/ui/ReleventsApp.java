package com.relevents.app.ui;

import com.relevents.app.database.DatabaseConnectionHandler;
import com.relevents.app.model.Event;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ReleventsApp extends Application {

    private static volatile ReleventsApp instance;
    private static DatabaseConnectionHandler dbHandler;
    String email = LoginWindow.getInstance().userEmail;

    public ReleventsApp() {
        dbHandler = new DatabaseConnectionHandler();
    }

    public void start(Stage primaryStage) {
        // testing ricky.ma@alumni.ubc.caricky.ma@alumni.ubc.cadatabase connection
        if (dbHandler.getConnection() == null) {
            ReleventsApp app = new ReleventsApp();
            app.getDbHandler().login("ora_rickyma", "a82943424");
            instance = app;
        }

        BorderPane root = new BorderPane();
        GridPane grid = GUISetup.setNewGrid();
        HBox hbButtons = GUISetup.navButtons(primaryStage);
        root.setBottom(hbButtons);
        root.setCenter(grid);

        displayNextEvent(grid);
        displayUserEvents(grid);

        Scene scene = new Scene(root, 360, 640);
        primaryStage.setTitle("RELEVENTS");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public DatabaseConnectionHandler getDbHandler() {
        return dbHandler;
    }

    public static ReleventsApp getInstance() {
        if (instance == null) {
            instance = new ReleventsApp();
        }
        return instance;
    }

    private void displayNextEvent(GridPane grid) {
        Event nextEvent = dbHandler.nextUserEvent(email);

        Text nextEventTxt = new Text("Next Event");
        nextEventTxt.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
        grid.add(nextEventTxt, 0, 0, 2, 1);

        Text eventName = new Text(nextEvent.getEventName());
        eventName.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        Text start = new Text("START: " + nextEvent.getEventStart());
        start.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        Text end = new Text("END: " + nextEvent.getEventEnd());
        end.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        Text website = new Text("WEBSITE: " + nextEvent.getWebsite());
        website.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        Text description = new Text("DESCRIPTION: " + nextEvent.getDescription());
        description.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));

        grid.add(eventName, 0, 1, 2, 1);
        grid.add(start, 0, 2, 2, 1);
        grid.add(end, 0, 3, 2, 1);
        grid.add(website, 0, 4, 2, 1);
        grid.add(description, 0, 5, 2, 1);
    }

    private void displayUserEvents(GridPane grid) {
        ListView<String> list = new ListView<>();
        Event[] eventInfoTable = dbHandler.userEvents(email);
        ArrayList<String> eventNames = new ArrayList<>();
        for (Event event : eventInfoTable) {
            eventNames.add(event.getEventName());
        }
        ObservableList<String> items = FXCollections.observableArrayList(eventNames);
        list.setItems(items);

        Text myEvents = new Text("My Events");
        myEvents.setFont(Font.font("Helvetica", FontWeight.BOLD, 25));
        grid.add(myEvents, 0, 6, 2, 1);
        grid.add(list, 0, 7, 2, 1);
    }

    public static void main(String[] args) {
        System.out.println("Hello World, Java app");
        launch(LoginWindow.class, args);
    }
}
