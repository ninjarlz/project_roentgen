package com.ninjarlz.projectroentgen.model.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * Class representing a point in Cartesian space.
 */
@Data
@AllArgsConstructor
public class CartesianPoint extends Point {

    /**
     * Stores a x coordinate of the point.
     */
    private int x;
    /**
     * Stores a y coordinate of the point.
     */
    private int y;

    /**
     * Gets the distance from the origin of space.
     *
     * @return distance from origin of space.
     */
    @Override
    public double getDistanceFromOrigin() {
        return Math.sqrt((x * x) + (y * y));
    }

    /**
     * Gets the distance from the other Cartesian point.
     *
     * @param otherPoint the other Cartesian point
     * @return the distance from the other Cartesian point.
     */
    public double getDistanceFrom(CartesianPoint otherPoint) {
        return Math.sqrt(Math.pow((otherPoint.x - x), 2) + Math.pow((otherPoint.y - y), 2));
    }

    /**
     * Determines whether two Cartesian points are equal.
     *
     * @param o the other Cartesian point
     * @return boolean determining whether two Cartesian points are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartesianPoint otherPoint = (CartesianPoint) o;
        return x == otherPoint.x &&
                y == otherPoint.y;
    }

    /**
     * Gets the hashCode of Cartesian point.
     *
     * @return the hashCode of Cartesian point.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Transforms a Cartesian point into printable string of characters. Uses
     * apache commons-langs3.
     *
     * @return returns string containing the Cartesian point's values.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("distance: " + getDistanceFromOrigin()).append(" x: " + x).append(" y: " + y).
                toString();
    }
}
