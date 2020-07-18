package com.ninjarlz.projectroentgen.view.gui;

import com.ninjarlz.projectroentgen.model.circle.CircleModel;
import com.ninjarlz.projectroentgen.model.circle.CircleList;
import com.ninjarlz.projectroentgen.utils.languages.LanguageManager;
import com.ninjarlz.projectroentgen.utils.mappers.ColorMapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ninjarlz.projectroentgen.utils.logs.FileAndConsoleLoggerFactory;

public class MainView implements Initializable {


    private final double CIRCLE_RADIUS = 7.5;

    public Menu fileMenu;
    public MenuItem loadMenuItem;
    public MenuItem closeMenuItem;
    public BorderPane mainPane;
    public Menu languageMenu;
    public MenuItem englishMenuItem;
    public MenuItem polishMenuItem;
    public AnchorPane anchorPane;
    public ImageView imageView1;
    public ImageView imageView2;
    public ImageView imageView3;
    public ImageView imageView4;
    public Label loadPictureLabel;
    public AnchorPane anchorImageView1;
    public AnchorPane anchorImageView2;
    public AnchorPane anchorImageView3;
    public AnchorPane anchorImageView4;
    private CircleList circleList = new CircleList();
    public AnchorPane anchorLeft;
    private ImageView[] imageViews = new ImageView[4];
    private AnchorPane[] anchorImageViews = new AnchorPane[4];
    private boolean isImageLoaded = false;
    private FileChooser imageFileChooser = new FileChooser();
    private Stage stage;
    private final LanguageManager languageManager = new LanguageManager();
    private final Logger logger = FileAndConsoleLoggerFactory.getConfiguredLogger(MainView.class.getName());



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG & PNG files (*.jpg, *.png)",
                Arrays.asList("*.jpg", "*.png"));
        imageFileChooser.getExtensionFilters().add(extFilter);
        imageViews[0] = imageView1;
        imageViews[1] = imageView2;
        imageViews[2] = imageView3;
        imageViews[3] = imageView4;
        anchorImageViews[0] = anchorImageView1;
        anchorImageViews[1] = anchorImageView2;
        anchorImageViews[2] = anchorImageView3;
        anchorImageViews[3] = anchorImageView4;
        for (AnchorPane anchorImageView : anchorImageViews) {
            anchorImageView.setOnMouseClicked(this::onAnchorImageClick);
        }
    }


    private void onAnchorImageClick(MouseEvent event) {
        if (isImageLoaded) {
            switch (event.getButton()) {
                case PRIMARY:
                    CircleModel circleModel = circleList.getCircleAt(event.getX(), event.getY());
                    if (circleModel == null) {
                        addCircle(event.getX(), event.getY());
                    } else {
                        deleteCircle(circleModel);
                    }
                    break;
                case SECONDARY:
                    break;
            }
        }
    }

    private void addCircle(double x, double y) {
        CircleModel circleModel = circleList.addCircle(x, y, CIRCLE_RADIUS);
        for (AnchorPane anchorImageView : anchorImageViews) {
            Circle circle = new Circle(x, y, CIRCLE_RADIUS);
            circle.setFill(ColorMapper.mapColorModelToColor(circleModel.getColor()));
            anchorImageView.getChildren().add(circle);
        }
        logger.log(Level.INFO, "Circle added at - x: " + circleModel.getCartesianPoint().getX() + " , y: "
                + circleModel.getCartesianPoint().getY());


    }

    private void deleteCircle(CircleModel circleModel) {
        int index = circleList.getCircleIndex(circleModel);
        circleList.removeCircle(circleModel);
        for (AnchorPane anchorImageView : anchorImageViews) {
            anchorImageView.getChildren().remove(index + 1, index + 2);
        }
        logger.log(Level.INFO, "Circle deleted at - x: " + circleModel.getCartesianPoint().getX() + " , y: "
                + circleModel.getCartesianPoint().getY());

    }

    public void loadImageAction(ActionEvent actionEvent) {
        File imageFile = imageFileChooser.showOpenDialog(stage);
        if (imageFile != null) {
            Image image = new Image(imageFile.toURI().toString());
            for (ImageView imageView : imageViews) {
                imageView.setImage(image);
            }
            anchorLeft.getChildren().remove(loadPictureLabel);
            isImageLoaded = true;
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void closeAction(ActionEvent actionEvent) {
        Platform.exit();
    }


    public void changeLanguage(LanguageManager.Language language) {
        languageManager.changeLanguage(language);
        fileMenu.setText(languageManager.getCurrentBundle().getString("file"));
        loadMenuItem.setText(languageManager.getCurrentBundle().getString("load"));
        closeMenuItem.setText(languageManager.getCurrentBundle().getString("close"));
        languageMenu.setText(languageManager.getCurrentBundle().getString("language"));
        englishMenuItem.setText(languageManager.getCurrentBundle().getString("english"));
        polishMenuItem.setText(languageManager.getCurrentBundle().getString("polish"));
        if (!isImageLoaded) {
            loadPictureLabel.setText(languageManager.getCurrentBundle().getString("load"));
        }
    }

    public void changeToPolishAction(ActionEvent actionEvent) {
        changeLanguage(LanguageManager.Language.POLISH);
    }

    public void changeToEnglishAction(ActionEvent actionEvent) {
        changeLanguage(LanguageManager.Language.ENGLISH);
    }
}
