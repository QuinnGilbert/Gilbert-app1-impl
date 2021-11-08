/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Quinn Gilbert
 */
package baseline;

import javafx.application.Application;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.stage.Stage;


public class TodoListApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("viewList.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("To-Do List Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
