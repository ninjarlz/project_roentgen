package com.ninjarlz.projectroentgen.model.circle;

import com.ninjarlz.projectroentgen.model.color.ColorModel;
import com.ninjarlz.projectroentgen.model.point.CartesianPoint;
import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Class representing a circle.
 */
@Data
public class CircleModel implements Comparable<CircleModel> {

    /**
     * Stores coordinates of circle.
     */
    private CartesianPoint cartesianPoint;
    /**
     * Stores the color of circle.
     */
    private ColorModel color;
    /**
     * Stores the radius of circle.
     */
    private double radius;

    private List<Consumer<CircleModel>> onCircleCreated;

    private List<Consumer<CircleModel>> onCircleRemoved;

    private List<Consumer<CircleModel>> onCircleMoved;

    /**
     * Creates a new instance of CircleModel.
     *
     * @param x      x coordinate of the circle.
     * @param y      y coordinate of the circle.
     * @param radius radius of the circle.
     */
    public CircleModel(double x, double y, double radius, List<Consumer<CircleModel>> onCircleCreated,
                       List<Consumer<CircleModel>> onCircleRemoved,
                       List<Consumer<CircleModel>> onCircleMoved) {
        cartesianPoint = new CartesianPoint(x, y);
        this.radius = radius;
        color = ColorModel.getRandomColor();
        this.onCircleCreated = onCircleCreated;
        this.onCircleMoved = onCircleMoved;
        this.onCircleRemoved = onCircleRemoved;
    }

    /**
     * Creates a new instance of CircleModel.
     *
     * @param x      x coordinate of the circle.
     * @param y      y coordinate of the circle.
     * @param radius radius of the circle.
     */
    public CircleModel(double x, double y, double radius) {
        cartesianPoint = new CartesianPoint(x, y);
        this.radius = radius;
        color = ColorModel.getRandomColor();
        onCircleCreated = new ArrayList<>();
        onCircleMoved = new ArrayList<>();
        onCircleRemoved = new ArrayList<>();
    }

    /**
     * Determines whether two circles are equal.
     *
     * @param o the other circle
     * @return boolean determining whether two circles are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CircleModel circle = (CircleModel) o;
        return radius == circle.radius &&
                Objects.equals(cartesianPoint, circle.cartesianPoint) &&
                Objects.equals(color, circle.color);
    }

    /**
     * Gets the hashCode of circle.
     *
     * @return the hashCode of circle.
     */
    @Override
    public int hashCode() {
        return Objects.hash(cartesianPoint, color, radius);
    }

    /**
     * Compares two circles based on their radii.
     *
     * @param o the other circle
     * @return integer representing the order of two circles.
     */
    @Override
    public int compareTo(CircleModel o) {
        if (o == null) {
            return 1;
        } else {
            return Double.compare(radius, o.radius);
        }
    }

    /**
     * Transforms a circle into printable string of characters. Uses
     * apache commons-langs3.
     *
     * @return returns string containing circle parameters.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("cartesianPoint: " + cartesianPoint.toString())
                .append(" color: " + color.toString()).append(" radius" + radius).toString();
    }
}
