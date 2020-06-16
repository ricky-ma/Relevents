package com.relevents.app.ui;

import com.relevents.app.model.Event;
import com.relevents.app.model.Organization;
import com.relevents.app.model.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static com.relevents.app.ui.GUISetup.getTextField;

public class EventView extends Application {

    private final Event event;
    private final boolean manager;

    public EventView(Event event, boolean manager) {
        this.event = event;
        this.manager = manager;
    }

    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane grid = GUISetup.setNewGrid();
        HBox hbButtons = GUISetup.navButtons(primaryStage);
        root.setBottom(hbButtons);
        root.setCenter(grid);

        GUISetup.setSceneTitle(grid, event.getEventName(), FontWeight.EXTRA_BOLD, 2);
        displayEventInfo(grid);
        displayEventAttendees(grid);
        if (manager) { displayDeleteButton(primaryStage, grid); }
        if (manager) { displayEditButton(primaryStage, grid); }
//        displayUserTopics(grid);
//        displayUserFollows(grid);

        Scene scene = new Scene(root, 360, 640);
        primaryStage.setTitle("RELEVENTS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void view(Stage primaryStage, String eventName, HashMap<Integer, String> eventMap, boolean manager) {
        Integer eventID = null;
        for (Map.Entry<Integer, String> is : eventMap.entrySet()) {
            if (eventName.equals(is.getValue())) {
                eventID = is.getKey();
            }
        }

        Event e = ReleventsApp.getInstance().getDbHandler().getOneEventInfo(eventID);
        EventView view = new EventView(e, manager);
        try {
            view.start(primaryStage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void displayEventInfo(GridPane grid) {
        Text start = new Text("Start: " + event.getEventStart());
        start.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        Text end = new Text("End: " + event.getEventEnd());
        end.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        Text website = new Text("Website: " + event.getWebsite());
        website.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));
        Text description = new Text("Description: " + event.getDescription());
        description.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 15));

        grid.add(start, 0, 1, 2, 1);
        grid.add(end, 0, 2, 2, 1);
        grid.add(website, 0, 3, 2, 1);
        grid.add(description, 0, 4, 2, 1);
    }

    private void displayEventAttendees(GridPane grid) {
        ListView<String> list = new ListView<>();
        list.getSelectionModel().clearSelection();
        list.getItems().clear();

        User[] eventAttendees = ReleventsApp.getInstance().getDbHandler().eventUsers(event.getEventID());
        HashMap<String, String> userMap = new HashMap<>();
        for (User u: eventAttendees) {
            userMap.put(u.getEmail(), u.getFname() + " " + u.getLname());
        }
        ObservableList<String> items = FXCollections.observableArrayList(userMap.values());
        list.setItems(items);

        Text myOrganizations = new Text("Attendees");
        myOrganizations.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        grid.add(myOrganizations, 0, 5, 2, 1);
        grid.add(list, 0, 6, 2, 1);
    }

    private void displayDeleteButton(Stage primaryStage, GridPane grid) {
        Button deleteEventBtn = new Button("Delete event");
        deleteEventBtn.setOnAction(e -> {
            ReleventsApp.getInstance().getDbHandler().deleteEvent(event.getEventID());
            Organization org = ReleventsApp.getInstance().getDbHandler().getOneOrgInfo(event.getOrganizationID());
            OrganizationView view = new OrganizationView(org, manager);
            view.start(primaryStage);
        });
        grid.add(deleteEventBtn, 1, 7, 1, 1);
    }

    private void displayEditButton(Stage primaryStage, GridPane grid) {
        Button editEventBtn = new Button("Edit event");
        editEventBtn.setOnAction(e -> editEventView(primaryStage));
        grid.add(editEventBtn, 0, 7, 1, 1);
    }

    private void editEventView(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane grid = GUISetup.setNewGrid();
        HBox hbButtons = GUISetup.navButtons(primaryStage);
        root.setBottom(hbButtons);
        root.setCenter(grid);
        GUISetup.setSceneTitle(grid, event.getEventName(), FontWeight.EXTRA_BOLD, 2);

        TextField nameTextField = getTextField(grid, "Name:", event.getEventName(), 1);
        Label start = new Label("Start:");
        grid.add(start, 0, 2,1,1);
        DatePicker startDate = new DatePicker();
        startDate.setValue(event.getEventStart().toLocalDateTime().toLocalDate());
        grid.add(startDate, 1, 2, 1, 1);

        Label end = new Label("End:");
        grid.add(end, 0, 3,1,1);
        DatePicker endDate = new DatePicker();
        endDate.setValue(event.getEventEnd().toLocalDateTime().toLocalDate());
        grid.add(endDate, 1, 3, 1, 1);

        TextField websiteTextField = getTextField(grid, "Website:", event.getWebsite(),4);
        TextField descriptionTextField = getTextField(grid, "Description:", event.getDescription(),5);

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            Timestamp startTime = Timestamp.valueOf(startDate.getValue().atStartOfDay());
            Timestamp endTime = Timestamp.valueOf(endDate.getValue().atStartOfDay());
            Event updatedEvent = new Event(event.getEventID(), nameTextField.getText(), startTime, endTime,
                    websiteTextField.getText(), descriptionTextField.getText(), 3, event.getOrganizationID());
            ReleventsApp.getInstance().getDbHandler().updateEvent(updatedEvent.getEventID(), updatedEvent);
            EventView view = new EventView(updatedEvent, true);
            view.start(primaryStage);
        });
        grid.add(saveBtn, 0,6,1,1);
        Scene scene = new Scene(root, 360, 640);
        primaryStage.setTitle("RELEVENTS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}






















