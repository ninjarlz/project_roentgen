package com.ninjarlz.projectroentgen.controller;

import com.ninjarlz.projectroentgen.model.circle.CircleModel;
import com.ninjarlz.projectroentgen.model.circle.CircleService;
import com.ninjarlz.projectroentgen.model.point.CartesianPoint;
import com.ninjarlz.projectroentgen.utils.languages.LanguageManager;
import com.ninjarlz.projectroentgen.utils.mappers.ColorMapper;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ninjarlz.projectroentgen.utils.logs.FileAndConsoleLoggerFactory;
import lombok.Setter;

public class MainView implements Initializable {

    private final double CIRCLE_RADIUS = 7.5;
    public Menu fileMenu;
    public MenuItem loadMenuItem;
    public MenuItem resetPointsItem;
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
    public VBox scrollBox;
    public Label validationMsgLabel;
    private CircleService circleService = CircleService.getInstance();
    private ImageView[] imageViews;
    private AnchorPane[] anchorImageViews;
    private FileChooser imageFileChooser = new FileChooser();
    @Setter
    private Stage stage;
    private CartesianPoint dragDelta = new CartesianPoint(0, 0);
    private final LanguageManager languageManager = LanguageManager.getInstance();
    private final Logger logger = FileAndConsoleLoggerFactory.getConfiguredLogger(MainView.class.getName());
    private double widthBound;
    private double heightBound;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG & PNG files (*.jpg, *.png)",
                Arrays.asList("*.jpg", "*.png"));
        imageFileChooser.getExtensionFilters().add(extFilter);
        imageViews = new ImageView[]{imageView1, imageView2, imageView3, imageView4};
        anchorImageViews = new AnchorPane[]{anchorImageView1, anchorImageView2, anchorImageView3, anchorImageView4};
        mainPane.setOnMouseReleased(this::onMouseReleased);
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
            createCircle(event.getX(), event.getY());
        }
    }

    private void onMouseReleased(MouseEvent event) {
        if (!circleService.checkIfCircleIsAlreadyDefined(event.getX(), event.getY())) {
            mainPane.getScene().setCursor(Cursor.DEFAULT);
        }
    }

    private void onMousePressedOnCircle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Circle circle = (Circle) event.getSource();
            dragDelta.setX(circle.getCenterX() - event.getX());
            dragDelta.setY(circle.getCenterY() - event.getY());
            mainPane.getScene().setCursor(Cursor.MOVE);
        } else if (event.getButton() == MouseButton.SECONDARY) {
            circleService.removeCircle(event.getX(), event.getY());
        }
        validationMsgLabel.setText("");
    }

    private void onMouseReleasedCircle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            mainPane.getScene().setCursor(Cursor.HAND);
        }
    }

    private void onMouseDraggedCircle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Circle circle = (Circle) event.getSource();
            double newPosX;
            double newPosY;
            if (event.getX() + dragDelta.getX() <= 0) {
                newPosX = 0;
            } else if (event.getX() + dragDelta.getX() >= widthBound) {
                newPosX = widthBound;
            } else {
                newPosX = event.getX() + dragDelta.getX();
            }
            if (event.getY() + dragDelta.getY() <= 0) {
                newPosY = 0;
            } else if (event.getY() + dragDelta.getY() >= heightBound) {
                newPosY = heightBound;
            } else {
                newPosY = event.getY() + dragDelta.getY();
            }
            circleService.moveCircle(circle.getCenterX(), circle.getCenterY(), newPosX, newPosY);
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

    private void onCircleMoved(CircleModel circleModel) {
        int index = circleService.getCircleIndex(circleModel);
        AnchorPane pointPanel = (AnchorPane) scrollBox.getChildren().get(index);
        PointPanel.setTextFields(pointPanel, circleModel.getCartesianPoint().getX(),
                circleModel.getCartesianPoint().getY());
        for (AnchorPane anchorImageView : anchorImageViews) {
            Circle circleOnPanel = (Circle) anchorImageView.getChildren().get(index + 1);
            circleOnPanel.setCenterX(circleModel.getCartesianPoint().getX());
            circleOnPanel.setCenterY(circleModel.getCartesianPoint().getY());
        }
    }

    private void onCircleRemoved(CircleModel circleModel) {
        int index = circleService.getCircleIndex(circleModel);
        System.out.println("INDEX:" + index);
        for (AnchorPane anchorImageView : anchorImageViews) {

            anchorImageView.getChildren().remove(index + 1, index + 2);

        }
        scrollBox.getChildren().remove(index, index + 1);
        System.out.println("a");
        logger.log(Level.INFO, "Circle deleted at - x: " + circleModel.getCartesianPoint().getX() + " , y: "
                + circleModel.getCartesianPoint().getY());
    }

    private void onCircleCreated(CircleModel circleModel) {
        Color color = ColorMapper.mapColorModelToColor(circleModel.getColor());
        for (AnchorPane anchorImageView : anchorImageViews) {
            Circle circle = new Circle(circleModel.getCartesianPoint().getX(),
                    circleModel.getCartesianPoint().getY(), circleModel.getRadius());
            circle.setFill(color);
            circle.setOnMousePressed(this::onMousePressedOnCircle);
            circle.setOnMouseReleased(this::onMouseReleasedCircle);
            circle.setOnMouseDragged(this::onMouseDraggedCircle);
            circle.setOnMouseExited(this::onMouseExitedCircle);
            circle.setOnMouseEntered(this::onMouseEnteredCircle);
            anchorImageView.getChildren().add(circle);
        }
        AnchorPane pointPanel = PointPanel.getPointPanel(color, (ActionEvent actionEvent) ->
                        circleService.removeCircle(circleModel),
                (event) -> onKeyReleasedTextFieldX(circleModel, event),
                ((observable, oldValue, newValue) -> onFocusLostTextFieldX(circleModel, newValue, observable)),
                (event) -> onKeyReleasedTextFieldY(circleModel, event),
                ((observable, oldValue, newValue) -> onFocusLostTextFieldY(circleModel, newValue, observable)));
        if (pointPanel != null) {
            PointPanel.setTextFields(pointPanel, circleModel.getCartesianPoint().getX(),
                    circleModel.getCartesianPoint().getY());
            scrollBox.getChildren().add(pointPanel);
        }
        validationMsgLabel.setText("");
        logger.log(Level.INFO, "Circle added at - x: " + circleModel.getCartesianPoint().getX() + " , y: "
                + circleModel.getCartesianPoint().getY());
    }

    private void createCircle(double x, double y) {
        circleService.createCircle(x, y, CIRCLE_RADIUS, Collections.singletonList(this::onCircleCreated),
                Collections.singletonList(this::onCircleRemoved), Collections.singletonList(this::onCircleMoved));
    }

    private void onFocusLostTextFieldX(CircleModel circleModel, boolean focus, Observable observable) {
        if (!focus) {
            ReadOnlyBooleanProperty readOnlyBooleanProperty = (ReadOnlyBooleanProperty) observable;
            logger.log(Level.INFO, "Focus lost on X input field for circle at - "
                    + circleModel.getCartesianPoint().getX() + ", " + circleModel.getCartesianPoint().getY());
            TextField textField = (TextField) readOnlyBooleanProperty.getBean();
            if (textField.getText().isEmpty()) {
                textField.setText(Integer.toString((int) circleModel.getCartesianPoint().getX()));
            }
        }
    }

    private void onFocusLostTextFieldY(CircleModel circleModel, boolean focus, Observable observable) {
        if (!focus) {
            ReadOnlyBooleanProperty readOnlyBooleanProperty = (ReadOnlyBooleanProperty) observable;
            logger.log(Level.INFO, "Focus lost on Y input field for circle at - "
                    + circleModel.getCartesianPoint().getX() + ", " + circleModel.getCartesianPoint().getY());
            TextField textField = (TextField) readOnlyBooleanProperty.getBean();
            if (textField.getText().isEmpty()) {
                textField.setText(Integer.toString((int) circleModel.getCartesianPoint().getY()));
            }
        }
    }

    private void onKeyReleasedTextFieldX(CircleModel circleModel, KeyEvent keyEvent) {
        TextField textField = (TextField) keyEvent.getSource();
        if (textField.getText().isEmpty()) {
            return;
        }
        double newValueDouble = Double.parseDouble(textField.getText());
        if (newValueDouble < 0 || newValueDouble > widthBound) {
            String validationMsg = languageManager.getCurrentBundle().getString("valueShouldBeX")
                    + " " + (int) widthBound + ".";
            validationMsgLabel.setText(validationMsg);
            logger.log(Level.INFO, validationMsg);
            String oldValue = Integer.toString((int) circleModel.getCartesianPoint().getX());
            textField.setText(oldValue);
            textField.positionCaret(oldValue.length());
            return;
        }
        CircleModel otherCircleModel =
                circleService.getCircleAt(newValueDouble, circleModel.getCartesianPoint().getY());
        if (otherCircleModel != null && otherCircleModel != circleModel) {
            String validationMsg = languageManager.getCurrentBundle().getString("alreadyDefinedX");
            String oldValue = Integer.toString((int) circleModel.getCartesianPoint().getX());
            logger.log(Level.INFO, validationMsg);
            validationMsgLabel.setText(validationMsg);
            textField.setText(oldValue);
            textField.positionCaret(oldValue.length());
            return;
        }
        circleService.moveCircle(circleModel, newValueDouble, circleModel.getCartesianPoint().getY());
        validationMsgLabel.setText("");
    }

    private void onKeyReleasedTextFieldY(CircleModel circleModel, KeyEvent keyEvent) {
        TextField textField = (TextField) keyEvent.getSource();
        if (textField.getText().isEmpty()) {
            return;
        }
        double newValueDouble = Double.parseDouble(textField.getText());
        if (newValueDouble < 0 || newValueDouble > heightBound) {
            String validationMsg = languageManager.getCurrentBundle().getString("valueShouldBeY")
                    + " " + (int) heightBound + ".";
            logger.log(Level.INFO, validationMsg);
            validationMsgLabel.setText(validationMsg);
            String oldValue = Integer.toString((int) circleModel.getCartesianPoint().getY());
            textField.setText(oldValue);
            textField.positionCaret(oldValue.length());
            return;
        }
        CircleModel otherCircleModel =
                circleService.getCircleAt(circleModel.getCartesianPoint().getX(), newValueDouble);
        if (otherCircleModel != null && otherCircleModel != circleModel) {
            String oldValue = Integer.toString((int) circleModel.getCartesianPoint().getY());
            textField.setText(oldValue);
            textField.positionCaret(oldValue.length());
            String validationMsg = languageManager.getCurrentBundle().getString("alreadyDefinedY");
            logger.log(Level.INFO, validationMsg);
            validationMsgLabel.setText(validationMsg);
            return;
        }
        circleService.moveCircle(circleModel, circleModel.getCartesianPoint().getX(), newValueDouble);
        validationMsgLabel.setText("");
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

    public void closeAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void resetPoints(ActionEvent actionEvent) {
        circleService.clear();
    }

    private void changeLanguage(LanguageManager.Language language) {
        languageManager.changeLanguage(language);
        fileMenu.setText(languageManager.getCurrentBundle().getString("file"));
        loadMenuItem.setText(languageManager.getCurrentBundle().getString("load"));
        resetPointsItem.setText(languageManager.getCurrentBundle().getString("reset"));
        closeMenuItem.setText(languageManager.getCurrentBundle().getString("close"));
        languageMenu.setText(languageManager.getCurrentBundle().getString("language"));
        englishMenuItem.setText(languageManager.getCurrentBundle().getString("english"));
        polishMenuItem.setText(languageManager.getCurrentBundle().getString("polish"));
        scrollBox.getChildren().forEach((Node node) -> PointPanel.changeLanguage((AnchorPane) node));
    }

    public void changeToPolishAction(ActionEvent actionEvent) {
        changeLanguage(LanguageManager.Language.POLISH);
    }

    public void changeToEnglishAction(ActionEvent actionEvent) {
        changeLanguage(LanguageManager.Language.ENGLISH);
    }
}
