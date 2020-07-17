package com.ninjarlz.projectroentgen.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL location = getClass().getResource("/com/ninjarlz/projectroentgen/view/gui/MainView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        fxmlLoader.setResources(ResourceBundle.getBundle("i18n.SudokuBundle", new Locale("en", "EN")));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("App");
        stage.setScene(scene);
        MainView controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
