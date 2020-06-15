package com.relevents.app.ui;

import com.relevents.app.model.Organization;
import com.relevents.app.model.User;
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
import java.util.HashMap;

public class MeView extends Application {

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
        displayUserOrganizations(primaryStage, grid);
        displayUserTopics(grid);
        displayUserFollows(grid);

        Scene scene = new Scene(root, 360, 640);
        primaryStage.setTitle("RELEVENTS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayUserInfo(GridPane grid) {
        Text phone = new Text("PHONE: " + userInfo.getPhone());
        phone.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        // TODO: use cityID to get cityName
        Text city = new Text("LOCATION: " + userInfo.getCityID());
        city.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        Text birthdate = new Text("BIRTHDATE: " + userInfo.getBirthdate());
        birthdate.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        Text email = new Text("EMAIL: " + userInfo.getEmail());
        email.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));

        grid.add(phone, 0, 1, 2, 1);
        grid.add(city, 0, 2, 2, 1);
        grid.add(email, 0, 3, 2, 1);
        grid.add(birthdate, 0, 4, 2, 1);
    }

    private void displayUserOrganizations(Stage primaryStage, GridPane grid) {
        ListView<String> list = new ListView<>();
        list.getSelectionModel().clearSelection();
        list.getItems().clear();


        Organization[] userOrgs = ReleventsApp.getInstance().getDbHandler().userOrganizations(email);

        HashMap<Integer, String> orgMap = new HashMap<>();
        for (Organization org : userOrgs) {
            orgMap.put(org.getOrganizationID(), org.getOrgName());
        }
        ObservableList<String> items = FXCollections.observableArrayList(orgMap.values());

        list.setItems(items);
        list.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> OrganizationView.view(primaryStage, newValue, orgMap, true));

        Text myOrganizations = new Text("MY ORGANIZATIONS");
        myOrganizations.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        grid.add(myOrganizations, 0, 5, 2, 1);
        grid.add(list, 0, 6, 2, 1);
    }

    private void displayUserTopics(GridPane grid) {
        ListView<String> list = new ListView<>();
        String[] userTopics = ReleventsApp.getInstance().getDbHandler().userTopics(email);
        ObservableList<String> items = FXCollections.observableArrayList(userTopics);
        list.setItems(items);

        Text myOrganizations = new Text("MY INTERESTS");
        myOrganizations.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        grid.add(myOrganizations, 0, 7, 2, 1);
        grid.add(list, 0, 8, 2, 1);
    }

    private void displayUserFollows(GridPane grid) {
        ListView<String> list = new ListView<>();
        Organization[] userOrgs = ReleventsApp.getInstance().getDbHandler().userFollows(email);
        ArrayList<String> orgNames = new ArrayList<>();
        for (Organization org : userOrgs) {
            orgNames.add(org.getOrgName());
        }
        ObservableList<String> items = FXCollections.observableArrayList(orgNames);
        list.setItems(items);

        Text myOrganizations = new Text("FOLLOWING");
        myOrganizations.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        grid.add(myOrganizations, 0, 9, 2, 1);
        grid.add(list, 0, 10, 2, 1);
    }
}
