package com.ninjarlz.projectroentgen.view.gui;

import com.ninjarlz.projectroentgen.model.circle.CircleModel;
import com.ninjarlz.projectroentgen.model.circle.CircleService;
import com.ninjarlz.projectroentgen.model.point.CartesianPoint;
import com.ninjarlz.projectroentgen.utils.languages.LanguageManager;
import com.ninjarlz.projectroentgen.utils.mappers.ColorMapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
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
    public AnchorPane anchorImageView1;
    public AnchorPane anchorImageView2;
    public AnchorPane anchorImageView3;
    public AnchorPane anchorImageView4;
    private CircleService circleService = new CircleService();
    public AnchorPane anchorLeft;
    private ImageView[] imageViews = new ImageView[4];
    private AnchorPane[] anchorImageViews = new AnchorPane[4];
    private FileChooser imageFileChooser = new FileChooser();
    private Stage stage;
    private CartesianPoint delta = new CartesianPoint(0, 0);
    private final LanguageManager languageManager = new LanguageManager();
    private final Logger logger = FileAndConsoleLoggerFactory.getConfiguredLogger(MainView.class.getName());
    private double widthBound;
    private double heightBound;


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
        widthBound = anchorImageView1.getMinWidth();
        heightBound = anchorImageView1.getMinHeight();
        Image image = new Image(getClass().getResource("/exemplaryImage/exemplaryImage.jpg").toString());
        for (ImageView imageView : imageViews) {
            imageView.setImage(image);
        }
        for (AnchorPane anchorImageView : anchorImageViews) {
            anchorImageView.setOnMousePressed(this::onAnchorImagePressed);
        }

    }


    private void onAnchorImagePressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            addCircle(event.getX(), event.getY());
        }
    }

    private void onMouseClickedOnCircle(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            deleteCircle(event.getX(), event.getY());
        }
    }

    private void onMousePressedOnCircle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Circle circle = (Circle) event.getSource();
            delta.setX(circle.getCenterX() - event.getX());
            delta.setY(circle.getCenterY() - event.getY());
            mainPane.getScene().setCursor(Cursor.MOVE);
        }
    }

    private void onMouseReleasedCircle(MouseEvent event) {
        mainPane.getScene().setCursor(Cursor.HAND);
    }

    private void onMouseDraggedCircle(MouseEvent event) {
        Circle circle = (Circle) event.getSource();
        double newPosX;
        double newPosY;
        if (event.getX() + delta.getX() <= 0) {
            newPosX = 0;
        } else if (event.getX() + delta.getX() >= widthBound) {
            newPosX = widthBound;
        } else {
            newPosX = event.getX() + delta.getX();
        }
        if (event.getY() + delta.getY() <= 0) {
            newPosY = 0;
        } else if (event.getY() + delta.getY() >= heightBound) {
            newPosY = heightBound;
        } else {
            newPosY = event.getY() + delta.getY();
        }
        if (!circleService.checkIfCircleIsAlreadyDefined(newPosX, newPosY)) {
            CircleModel circleModel = circleService.getCircleAt(circle.getCenterX(), circle.getCenterY());
            int index = circleService.getCircleIndex(circleModel);
            circleService.moveCircle(circleModel, newPosX, newPosY);
            for (AnchorPane anchorImageView : anchorImageViews) {
                Circle circleOnPanel = (Circle) anchorImageView.getChildren().get(index + 1);
                circleOnPanel.setCenterX(newPosX);
                circleOnPanel.setCenterY(newPosY);
            }
        }
    }

    private void onMouseEnteredCircle(MouseEvent event) {
        if (!event.isPrimaryButtonDown()) {
            mainPane.getScene().setCursor(Cursor.HAND);
        }
    }

    private void onMouseExitedCircle(MouseEvent event) {
        if (!event.isPrimaryButtonDown()) {
            mainPane.getScene().setCursor(Cursor.DEFAULT);
        }
    }

    private void addCircle(double x, double y) {
        CircleModel circleModel = circleService.addCircle(x, y, CIRCLE_RADIUS);
        if (circleModel != null) {
            for (AnchorPane anchorImageView : anchorImageViews) {
                Circle circle = new Circle(x, y, CIRCLE_RADIUS);
                circle.setFill(ColorMapper.mapColorModelToColor(circleModel.getColor()));
                circle.setOnMouseClicked(this::onMouseClickedOnCircle);
                circle.setOnMousePressed(this::onMousePressedOnCircle);
                circle.setOnMouseReleased(this::onMouseReleasedCircle);
                circle.setOnMouseDragged(this::onMouseDraggedCircle);
                circle.setOnMouseExited(this::onMouseExitedCircle);
                circle.setOnMouseEntered(this::onMouseEnteredCircle);
                anchorImageView.getChildren().add(circle);
            }
            logger.log(Level.INFO, "Circle added at - x: " + circleModel.getCartesianPoint().getX() + " , y: "
                    + circleModel.getCartesianPoint().getY());
        }
    }

    private void deleteCircle(double x, double y) {
        CircleModel circleModel = circleService.getCircleAt(x, y);
        if (circleModel != null) {
            int index = circleService.getCircleIndex(circleModel);
            circleService.removeCircle(circleModel);
            for (AnchorPane anchorImageView : anchorImageViews) {
                anchorImageView.getChildren().remove(index + 1, index + 2);
            }
            logger.log(Level.INFO, "Circle deleted at - x: " + circleModel.getCartesianPoint().getX() + " , y: "
                    + circleModel.getCartesianPoint().getY());
        }
    }

    public void loadImageAction(ActionEvent actionEvent) {
        File imageFile = imageFileChooser.showOpenDialog(stage);
        if (imageFile != null) {
            Image image = new Image(imageFile.toURI().toString());
            for (ImageView imageView : imageViews) {
                imageView.setImage(image);
            }
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
    }

    public void changeToPolishAction(ActionEvent actionEvent) {
        changeLanguage(LanguageManager.Language.POLISH);
    }

    public void changeToEnglishAction(ActionEvent actionEvent) {
        changeLanguage(LanguageManager.Language.ENGLISH);
    }
}
