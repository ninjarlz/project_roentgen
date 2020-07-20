package com.ninjarlz.projectroentgen.model.circle;

import java.util.ArrayList;
import java.util.List;


public class CircleService {

    private List<CircleModel> circleList = new ArrayList<>();

    private CircleService() {}

    private static CircleService instance;

    public static CircleService getInstance() {
        if (instance == null) {
            instance = new CircleService();
        }
        return instance;
    }

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

    public boolean checkIfCircleIsAlreadyDefined(double x, double y) {
        return getCircleAt(x, y) != null;
    }

    public int getCircleIndex(CircleModel circleModel) {
        return circleList.indexOf(circleModel);
    }

    public CircleModel addCircle(double x, double y, double radius) {
        if (!checkIfCircleIsAlreadyDefined(x, y)) {
            CircleModel circleModel = new CircleModel(x, y, radius);
            circleList.add(circleModel);
            return circleModel;
        }
        return  null;
    }

    public boolean removeCircle(CircleModel circleModel) {
        return circleList.remove(circleModel);

    }

    public boolean removeCircle(double x, double y) {
        CircleModel circleModel = getCircleAt(x, y);
        if (circleModel != null) {
            circleList.remove(circleModel);
            return  true;
        }
        return false;
    }

    public void moveCircle(CircleModel circle, double x, double y) {
        circle.getCartesianPoint().setX(x);
        circle.getCartesianPoint().setY(y);
    }


    public void clear() {
        circleList.clear();
    }

}
