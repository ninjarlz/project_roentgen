package com.ninjarlz.projectroentgen.model.point;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Interface representing a point in space.
 */
public abstract class Point implements Comparable<Point> {

    /**
     * Gets the distance from the origin of space.
     *
     * @return distance from the origin of space.
     */
    public abstract double getDistanceFromOrigin();

    /**
     * Compares two points based on their distance from the origin of space.
     *
     * @param o the other point
     * @return integer representing the order of two points.
     */
    @Override
    public int compareTo(Point o) {
        if (o == null) {
            return 1;
        } else {
            return Double.compare(getDistanceFromOrigin(), o.getDistanceFromOrigin());
        }
    }

    /**
     * Transforms a point into printable string of characters. Uses
     * apache commons-langs3.
     *
     * @return returns string containing the point's distance from the origin of space.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("distance: " + getDistanceFromOrigin()).toString();
    }
}
