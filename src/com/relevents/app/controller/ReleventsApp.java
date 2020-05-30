package com.relevents.app.controller;

import com.relevents.app.database.DatabaseConnectionHandler;

public class ReleventsApp {
    private DatabaseConnectionHandler dbHandler = null;

    public ReleventsApp() {
        dbHandler = new DatabaseConnectionHandler();
    }

    private void start() {

    }


    public static void main(String[] args) {
        System.out.println("Hello World, Java app");
        ReleventsApp app = new ReleventsApp();
        app.start();
    }

}
