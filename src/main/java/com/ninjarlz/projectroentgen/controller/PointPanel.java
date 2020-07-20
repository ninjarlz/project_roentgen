package com.ninjarlz.projectroentgen.controller;

import com.ninjarlz.projectroentgen.utils.languages.LanguageManager;
import com.ninjarlz.projectroentgen.utils.logs.FileAndConsoleLoggerFactory;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PointPanel {

    private static final Logger logger = FileAndConsoleLoggerFactory.getConfiguredLogger(PointPanel.class.getName());

    public static AnchorPane getPointPanel(Color color, EventHandler<ActionEvent> onRemoveButton,
                                           EventHandler<KeyEvent> onKeyReleasedTextFieldX,
                                           ChangeListener<Boolean> onFocusLostX,
                                           EventHandler<KeyEvent> onKeyReleasedTextFieldY,
                                           ChangeListener<Boolean> onFocusLostY) {
        URL location = PointPanel.class.getResource("/com/ninjarlz/projectroentgen/view/PointPanel.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        try {
            AnchorPane pointPanel = fxmlLoader.load();
            pointPanel.setStyle("-fx-border-color: rgb(" + color.getRed() * 255 + "," + color.getGreen() * 255 +
                    "," + color.getBlue() * 255 +
                    "); -fx-border-width:1px 1px 1px 1px");
            LanguageManager languageManager = LanguageManager.getInstance();
            ObservableList<Node> children = pointPanel.getChildren();
            Button removeButton = (Button) children.get(0);
            removeButton.setOnAction(onRemoveButton);
            removeButton.setText(languageManager.getCurrentBundle().getString("remove"));
            Label pointLabel = (Label) children.get(3);
            TextField textFieldX = (TextField) pointPanel.getChildren().get(1);
            textFieldX.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().
                    matches("([0-9][0-9]*)?") ? change : null));
            TextField textFieldY = (TextField) pointPanel.getChildren().get(5);
            textFieldY.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().
                    matches("([0-9][0-9]*)?") ? change : null));
            pointLabel.setTextFill(color);
            textFieldX.setOnKeyReleased(onKeyReleasedTextFieldX);
            textFieldX.focusedProperty().addListener(onFocusLostX);
            textFieldY.setOnKeyReleased(onKeyReleasedTextFieldY);
            textFieldY.focusedProperty().addListener(onFocusLostY);
            pointLabel.setText(languageManager.getCurrentBundle().getString("point"));
            return pointPanel;
        } catch (IOException ioex) {
            logger.log(Level.SEVERE, "The pointPanel cannot be instantiated: " + ioex.getMessage());
            logger.log(Level.SEVERE, ioex.toString());
            return null;
        }
    }

    public static void setTextFields(AnchorPane pointPanel, double x, double y) {
        TextField textFieldX = (TextField) pointPanel.getChildren().get(1);
        TextField textFieldY = (TextField) pointPanel.getChildren().get(5);
        textFieldX.setText(Integer.toString((int) x));
        textFieldY.setText(Integer.toString((int) y));
    }

    public static void changeLanguage(AnchorPane anchorPane) {
        LanguageManager languageManager = LanguageManager.getInstance();
        ((Label) anchorPane.getChildren().get(3)).setText(languageManager.
                getCurrentBundle().getString("point"));
        ((Button) anchorPane.getChildren().get(0)).setText(languageManager.
                getCurrentBundle().getString("remove"));
    }
}
