package com.ninjarlz.projectroentgen.model;

import com.ninjarlz.projectroentgen.utils.logs.FileAndConsoleLoggerFactory;
import com.ninjarlz.projectroentgen.model.point.CartesianPoint;
import com.ninjarlz.projectroentgen.model.point.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used to test Point and CartesianPoint implementations.
 */
public class PointTest {

    private static final Logger logger = FileAndConsoleLoggerFactory.getConfiguredLogger(PointTest.class.getName());

    /**
     * Tests whether the distance from origin is properly calculated in the CartesianPoint class.
     */
    @Test
    public void pointDistanceTest() {
        Point point = new CartesianPoint(-3, -4);
        assertEquals(5, point.getDistanceFromOrigin());
    }


    /**
     * Tests whether the Comparable interface is properly implemented in the Point class.
     */
    @Test
    public void pointsCompareTest() {
        Point point1 = new CartesianPoint(-100, 1);
        Point point2 = new CartesianPoint(1, 200);
        Point point3 = new CartesianPoint(1, 1);
        List<Point> points = new ArrayList<>();
        points.add(point1);
        points.add(point2);
        points.add(point3);
        Collections.sort(points);
        points.forEach((Point point) -> logger.log(Level.INFO, point.toString()));
        assertSame(point3, points.get(0));
        assertSame(point1, points.get(1));
        assertSame(point2, points.get(2));
    }

    /**
     * Tests whether the equals method is properly implemented in the CartesianPoint class.
     */
    @Test
    public void pointsEqualsTest() {
        Point point1 = new CartesianPoint(100, 150);
        Point point2 = new CartesianPoint(100, 150);
        Point point3 = new CartesianPoint(200, 1);
        assertEquals(point1, point2);
        assertNotEquals(point1, point3);
        assertNotEquals(point1, null);
    }

    /**
     * Tests whether the distance between two points is properly calculated in the CartesianPoint class.
     */
    @Test
    public void distanceBetweenPointsTest() {
        CartesianPoint point1 = new CartesianPoint(0, 0);
        CartesianPoint point2 = new CartesianPoint(3, -4);
        assertEquals(5, point2.getDistanceFrom(point1));
    }
}
