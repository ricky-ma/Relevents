package com.relevents.app.ui;

import com.relevents.app.model.CityUser;
import com.relevents.app.model.Event;
import com.relevents.app.model.Organization;
import com.relevents.app.model.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class OrganizationView extends Application {

    private final Organization organization;
    private final boolean manager;


    public OrganizationView(Organization organization, boolean manager) {
        this.organization = organization;
        this.manager = manager;
    }

    // This method, when called, will receive the original primary stage
    // on which a new scene will then be attached
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane grid = GUISetup.setNewGrid();
        HBox hbButtons = GUISetup.navButtons(primaryStage);
        root.setBottom(hbButtons);
        root.setCenter(grid);

        GUISetup.setSceneTitle(grid, organization.getOrgName(), FontWeight.EXTRA_BOLD, 2);
        displayOrganizationInfo(grid);
        displayOrganizationEvents(primaryStage, grid);
        if (manager) { addEventButton(primaryStage, grid); }
        if (manager) { analyticsBtn(primaryStage, grid); }

        Scene scene = new Scene(root, 360, 640);
        primaryStage.setTitle("RELEVENTS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void view(Stage primaryStage, String orgName, HashMap<Integer, String> orgMap, boolean manager) {
        Integer orgID = null;
        for (Map.Entry<Integer, String> is : orgMap.entrySet()) {
            if (orgName.equals(is.getValue())) {
                orgID = is.getKey();
            }
        }

        Organization o = ReleventsApp.getInstance().getDbHandler().getOneOrgInfo(orgID);
        OrganizationView view = new OrganizationView(o, manager);
        try {
            view.start(primaryStage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void displayOrganizationInfo(GridPane grid) {
        Text description = new Text("DESCRIPTION: " + organization.getDescription());
        description.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        Text email = new Text("EMAIL: " + organization.getEmail());
        email.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        Text website = new Text("WEBSITE: " + organization.getWebsite());
        website.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));

        grid.add(description, 0, 1, 2, 1);
        grid.add(email, 0, 2, 2, 1);
        grid.add(website, 0, 3, 2, 1);
    }

    private void displayOrganizationEvents(Stage primaryStage, GridPane grid) {
        ListView<String> list = new ListView<>();
        list.getSelectionModel().clearSelection();
        list.getItems().clear();


        Event[] orgEvents = ReleventsApp.getInstance().getDbHandler().getOrgEvents(organization.getOrganizationID());
        HashMap<Integer, String> eventMap = new HashMap<>();
        for (Event e: orgEvents) {
            eventMap.put(e.getEventID(), e.getEventName());
        }
        ObservableList<String> items = FXCollections.observableArrayList(eventMap.values());

        list.setItems(items);
        list.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> EventView.view(primaryStage, newValue, eventMap, manager));

        Text myOrganizations = new Text("EVENTS");
        myOrganizations.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        grid.add(myOrganizations, 0, 4, 2, 1);
        grid.add(list, 0, 5, 2, 1);
    }

    private void addEventButton(Stage primaryStage, GridPane grid) {
        Button addEvent= new Button("Add event");
        addEvent.setOnAction(e -> newEvent(primaryStage));
        grid.add(addEvent, 0, 6, 1, 1);
    }

    private void newEvent(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane grid = GUISetup.setNewGrid();
        HBox hbButtons = GUISetup.navButtons(primaryStage);
        root.setBottom(hbButtons);
        root.setCenter(grid);

        GUISetup.setSceneTitle(grid, "INPUT EVENT INFO", FontWeight.EXTRA_BOLD, 2);
        TextField nameTextField = GUISetup.getTextField(grid, "Name:", "",1);
        Label start = new Label("Start:");
        grid.add(start, 0, 2,1,1);
        DatePicker startDate = new DatePicker();
        grid.add(startDate, 1, 2, 1, 1);

        Label end = new Label("End:");
        grid.add(end, 0, 3,1,1);
        DatePicker endDate = new DatePicker();
        grid.add(endDate, 1, 3, 1, 1);

        TextField websiteTextField = GUISetup.getTextField(grid, "Website:", "",4);
        TextField descriptionTextField = GUISetup.getTextField(grid, "Description:", "",5);

        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> {
            Timestamp startTime = Timestamp.valueOf(startDate.getValue().atStartOfDay());
            Timestamp endTime = Timestamp.valueOf(endDate.getValue().atStartOfDay());
            ReleventsApp.getInstance().getDbHandler().insertEvent(nameTextField.getText(), startTime, endTime,
                    websiteTextField.getText(), descriptionTextField.getText(), 3, organization.getOrganizationID());
            start(primaryStage);
        });
        grid.add(addBtn, 0, 6, 1, 1);

        Scene scene = new Scene(root, 360, 640);
        primaryStage.setTitle("RELEVENTS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void analyticsBtn(Stage primaryStage, GridPane grid) {
        Button analytics= new Button("Analytics");
        analytics.setOnAction(e -> analyticsView(primaryStage));
        grid.add(analytics, 1, 6, 1, 1);
    }

    @SuppressWarnings("unchecked")
    private void analyticsView(Stage primaryStage) {
        CityUser[] cityUsers = ReleventsApp.getInstance().getDbHandler().getCityUsers();
        User[] veryActiveUsers = ReleventsApp.getInstance().getDbHandler().usersAttendedAllEvents();

        BorderPane root = new BorderPane();
        GridPane grid = GUISetup.setNewGrid();
        HBox hbButtons = GUISetup.navButtons(primaryStage);
        root.setBottom(hbButtons);
        root.setCenter(grid);
        GUISetup.setSceneTitle(grid, "ANALYTICS", FontWeight.EXTRA_BOLD, 2);

        Text title1 = new Text("Number of users per city");
        title1.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        grid.add(title1, 0, 1, 2, 1);

        TableColumn<CityUser, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        cityCol.setMinWidth(155);
        TableColumn<CityUser, Integer> numUserCol = new TableColumn<>("Number of users");
        numUserCol.setCellValueFactory(new PropertyValueFactory<>("numUsers"));
        numUserCol.setMinWidth(153);

        TableView<CityUser> table = new TableView<>();
        ObservableList<CityUser> data = FXCollections.observableArrayList(cityUsers);
        table.setItems(data);
        table.getColumns().addAll(cityCol, numUserCol);
        grid.add(table, 0, 2, 2, 1);

        Text title2 = new Text("Very active users");
        title2.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        grid.add(title2, 0, 3, 2, 1);

        ListView<String> list = new ListView<>();
        list.getSelectionModel().clearSelection();
        list.getItems().clear();
        HashMap<String, String> userMap = new HashMap<>();
        for (User u: veryActiveUsers) {
            userMap.put(u.getEmail(), u.getFname() + " " + u.getLname());
        }
        ObservableList<String> items = FXCollections.observableArrayList(userMap.values());
        list.setItems(items);
        grid.add(list, 0, 4, 2, 1);

        Scene scene = new Scene(root, 360, 640);
        primaryStage.setTitle("RELEVENTS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
