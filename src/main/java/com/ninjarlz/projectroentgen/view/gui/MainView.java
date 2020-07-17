package com.ninjarlz.projectroentgen.view.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ninjarlz.projectroentgen.utils.logs.FileAndConsoleLoggerFactory;

public class MainView implements Initializable {


    public Menu fileMenu;
    public MenuItem loadMenuItem;
    public MenuItem closeMenuItem;
    public BorderPane mainPane;
    public Menu languageMenu;
    public MenuItem englishMenuItem;
    public MenuItem polishMenuItem;
    public AnchorPane anchorPane;
    private FileChooser imageFileChooser = new FileChooser();
    private Stage stage;
    private Locale englishLocale = new Locale("en", "EN");
    private ResourceBundle englishBundle = ResourceBundle.getBundle("i18n.SudokuBundle", englishLocale);
    private ResourceBundle polishBundle = ResourceBundle.getBundle("i18n.SudokuBundle");
    private ResourceBundle currentBundle = englishBundle;
    private final Logger logger = FileAndConsoleLoggerFactory.getConfiguredLogger(MainView.class.getName());

    private enum Language {ENGLISH, POLISH}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG & PNG files (*.jpg, *.png)",
                Arrays.asList("*.jpg", "*.png"));
        imageFileChooser.getExtensionFilters().add(extFilter);
    }

    public void loadImageAction(ActionEvent actionEvent) {
        File imageFile = imageFileChooser.showOpenDialog(stage);
        if (imageFile != null) {
            Image image = new Image(imageFile.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(570);
            imageView.setFitWidth(600);
            mainPane.setCenter(imageView);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void closeAction(ActionEvent actionEvent) {
        Platform.exit();
    }


    public void changeLangauge(Language language) {
        switch (language) {
            case ENGLISH:
                currentBundle = englishBundle;
                break;
            case POLISH:
                currentBundle = polishBundle;
                break;
        }
        fileMenu.setText(currentBundle.getString("file"));
        loadMenuItem.setText(currentBundle.getString("load"));
        closeMenuItem.setText(currentBundle.getString("close"));
        languageMenu.setText(currentBundle.getString("language"));
        englishMenuItem.setText(currentBundle.getString("english"));
        polishMenuItem.setText(currentBundle.getString("polish"));
    }

    public void changeToPolishAction(ActionEvent actionEvent) {
        changeLangauge(Language.POLISH);
    }

    public void changeToEnglishAction(ActionEvent actionEvent) {
        changeLangauge(Language.ENGLISH);
    }
}
