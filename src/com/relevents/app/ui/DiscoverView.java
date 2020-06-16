package com.relevents.app.ui;

import com.relevents.app.model.Event;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashMap;

public class DiscoverView extends Application {

    Event[] events = ReleventsApp.getInstance().getDbHandler().searchEventsByKeyword("");

    // This method, when called, will receive the original primary stage
    // on which a new scene will then be attached
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane grid = GUISetup.setNewGrid();
        HBox hbButtons = GUISetup.navButtons(primaryStage);
        root.setBottom(hbButtons);
        root.setCenter(grid);

        GUISetup.setSceneTitle(grid, "Discover", FontWeight.EXTRA_BOLD, 2);
        displayEventsTable(primaryStage, grid, events);


        Scene scene = new Scene(root, 360, 640);
        primaryStage.setTitle("RELEVENTS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayEventsTable(Stage primaryStage, GridPane grid, Event[] events) {
        TableView<Event> eventTable = new TableView<>();
        TableColumn<Event, String> nameCol = addColumn("Name", "eventName");
        TableColumn<Event, String> startCol = addColumn("Start", "eventStart");
        TableColumn<Event, String> endCol = addColumn("End", "eventEnd");
        TableColumn<Event, String> websiteCol = addColumn("Website", "website");
        TableColumn<Event, String> descriptionCol = addColumn("Description", "description");

        ObservableList<Event> data = FXCollections.observableArrayList(events);
        eventTable.setItems(data);
        eventTable.getColumns().addAll(nameCol, startCol, endCol, websiteCol, descriptionCol);

        updateTableView(events, eventTable);
        displaySearchBox(grid, eventTable);

        HashMap<Integer, String> eventMap = new HashMap<>();
        for (Event e: events) {
            eventMap.put(e.getEventID(), e.getEventName());
        }
        eventTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> EventView.view(primaryStage, newValue.getEventName(), eventMap, false));

        grid.add(eventTable, 0, 2, 2, 1);
    }

    private TableColumn<Event, String> addColumn(String label, String colName) {
        TableColumn<Event, String> column = new TableColumn<>(label);
        column.setCellValueFactory(new PropertyValueFactory<>(colName));
        column.setMinWidth(100);
        return column;
    }

    private void updateTableView(Event[] events, TableView<Event> eventTableView) {
        ObservableList<Event> entries = FXCollections.observableArrayList(events);
        eventTableView.setItems(entries);
    }

    private void handleSearchByKey(String oldVal, String newVal, TableView<Event> tableView) {
        newVal = newVal.toUpperCase();
        Event[] events = ReleventsApp.getInstance().getDbHandler().searchEventsByKeyword(newVal);

        ObservableList<Event> entries = FXCollections.observableArrayList(events);
        tableView.setItems(entries);
        if (oldVal != null && (newVal.length() < oldVal.length())) {
            tableView.setItems(entries);
        }

        ObservableList<Event> subentries = FXCollections.observableArrayList();
        for (Object entry: tableView.getItems()) {
            Event entryEvent = (Event)entry;
            subentries.add(entryEvent);
        }
        tableView.setItems(subentries);


    }

    private void displaySearchBox(GridPane grid, TableView<Event> tableView) {
        TextField txt = new TextField();
        txt.setPromptText("Search");
        txt.textProperty().addListener(
                (observable, oldVal, newVal) -> handleSearchByKey(oldVal, newVal, tableView));
        grid.add(txt, 0, 1, 2, 1);
    }


}
