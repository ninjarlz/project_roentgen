package com.ninjarlz.projectroentgen.model.circle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class responsible for managing the set of circles.
 */
public class CircleService {

    /**
     * Stores a list of circles.
     */
    private List<CircleModel> circleList = new ArrayList<>();

    private CircleService() {}

    private static CircleService instance;

    public static CircleService getInstance() {
        if (instance == null) {
            instance = new CircleService();
        }
        return instance;
    }

    /**
     * Checks whether there is a circle at given position. If so, returns
     * the reference to that circle.
     *
     * @param x x coordinate of the circle.
     * @param y y coordinate of the circle.
     * @return circle at given position.
     */
    public CircleModel getCircleAt(double x, double y) {
        for (CircleModel circle : circleList) {
            if (x <= circle.getCartesianPoint().getX() + circle.getRadius() &&
                    x >= circle.getCartesianPoint().getX() - circle.getRadius() &&
                    y <= circle.getCartesianPoint().getY() + circle.getRadius() &&
                    y >= circle.getCartesianPoint().getY() - circle.getRadius()) {
                return circle;
            }
        }
        return null;
    }

    /**
     * Checks whether there is a circle at given position.
     *
     * @param x x coordinate of the circle.
     * @param y y coordinate of the circle.
     * @return boolean determining whether there is a circle at given position.
     */
    public boolean checkIfCircleIsAlreadyDefined(double x, double y) {
        return getCircleAt(x, y) != null;
    }

    /**
     * Returns the index of circle at the circleList.
     *
     * @param circleModel checked circle.
     * @return index of circle at circleList.
     */
    public int getCircleIndex(CircleModel circleModel) {
        return circleList.indexOf(circleModel);
    }

    /**
     * Instantiates a new instance of CircleModel and add it to the circleList.
     *
     * @param x x coordinate of the circle.
     * @param y y coordinate of the circle.
     * @param radius radius of the circle.
     * @param onCircleCreated callbacks executed when the circle is created.
     * @param onCircleRemoved callbacks executed when the circle is removed.
     * @param onCircleMoved callbacks executed when the circle is moved.
     * @return added circle.
     */
    public CircleModel createCircle(double x, double y, double radius, List<Consumer<CircleModel>> onCircleCreated,
                                    List<Consumer<CircleModel>> onCircleRemoved,
                                    List<Consumer<CircleModel>> onCircleMoved) {
        if (!checkIfCircleIsAlreadyDefined(x, y)) {
            CircleModel circleModel = new CircleModel(x, y, radius, onCircleCreated, onCircleRemoved, onCircleMoved);
            circleList.add(circleModel);
            circleModel.getOnCircleCreated().forEach(consumer -> consumer.accept(circleModel));
            return circleModel;
        }
        return  null;
    }

    /**
     * Removes the circle from the circleList.
     *
     * @param circleModel removed circle.
     * @return boolean determining whether the circle was removed successfully.
     */
    public boolean removeCircle(CircleModel circleModel) {
        circleModel.getOnCircleRemoved().forEach(consumer -> consumer.accept(circleModel));
        return circleList.remove(circleModel);
    }

    /**
     * Removes the circle at the given position from the circleList.
     *
     * @param x x coordinate of the circle.
     * @param y y coordinate of the circle.
     * @return boolean determining whether the circle was removed successfully.
     */
    public boolean removeCircle(double x, double y) {
        CircleModel circleModel = getCircleAt(x, y);
        if (circleModel != null) {
            return removeCircle(circleModel);
        }
        return false;
    }


    /**
     * Moves the circle to the given position.
     *
     * @param oldX x coordinate of the circle.
     * @param oldY new y coordinate of the circle.
     * @param newX new x coordinate of the circle.
     * @param newY new y coordinate of the circle.
     */
    public void moveCircle(double oldX, double oldY, double newX, double newY) {
        if (!checkIfCircleIsAlreadyDefined(newX, newY)) {
            CircleModel circle = getCircleAt(oldX, oldY);
            moveCircle(circle, newX, newY);
        }
    }


    /**
     * Moves the circle to the given position.
     *
     * @param circle moved circle.
     * @param newX new x coordinate of the circle.
     * @param newY new y coordinate of the circle.
     */
    public void moveCircle(CircleModel circle, double newX, double newY) {
        circle.getCartesianPoint().setX(newX);
        circle.getCartesianPoint().setY(newY);
        circle.getOnCircleMoved().forEach(consumer -> consumer.accept(circle));
    }


    /**
     * Clears the circleList.
     */
    public void clear() {
        while(!circleList.isEmpty()) {
            removeCircle(circleList.get(0));
        }
    }

}
