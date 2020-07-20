package com.ninjarlz.projectroentgen.model.circle;

import java.util.ArrayList;
import java.util.List;

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
     * @return added circle.
     */
    public CircleModel addCircle(double x, double y, double radius) {
        if (!checkIfCircleIsAlreadyDefined(x, y)) {
            CircleModel circleModel = new CircleModel(x, y, radius);
            circleList.add(circleModel);
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
            circleList.remove(circleModel);
            return  true;
        }
        return false;
    }


    /**
     * Moves the circle to the given position.
     *
     * @param circle moved circle.
     * @param x new x coordinate of the circle.
     * @param y new y coordinate of the circle.
     */
    public void moveCircle(CircleModel circle, double x, double y) {
        circle.getCartesianPoint().setX(x);
        circle.getCartesianPoint().setY(y);
    }


    /**
     * Clears the circleList.
     */
    public void clear() {
        circleList.clear();
    }

}
