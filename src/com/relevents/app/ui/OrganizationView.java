package com.relevents.app.ui;

import com.relevents.app.model.Organization;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class OrganizationView extends Application {

    private final Organization organization;


    public OrganizationView(Organization organization) {
        this.organization = organization;
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
//        displayOrganizationInfo(grid);
//        displayOrganizationEvents(grid);

        Scene scene = new Scene(root, 360, 640);

        primaryStage.setTitle("RELEVENTS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
