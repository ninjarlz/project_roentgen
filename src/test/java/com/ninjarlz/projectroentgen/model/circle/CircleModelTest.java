package com.ninjarlz.projectroentgen.model.circle;

import com.ninjarlz.projectroentgen.model.color.ColorModel;
import com.ninjarlz.projectroentgen.utils.logs.FileAndConsoleLoggerFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class used to test CircleModel implementation.
 */
public class CircleModelTest {

    private static final Logger logger = FileAndConsoleLoggerFactory.getConfiguredLogger(CircleModelTest.class.getName());


    /**
     * Tests whether the Comparable interface is properly implemented in the CircleModel class.
     * Circles should be ordered based on their radii.
     */
    @Test
    public void pointsCompareTest() {
        CircleModel circleModel1 = new CircleModel(1, 1, 100);
        CircleModel circleModel2 = new CircleModel(1, 10000, 200);
        CircleModel circleModel3 = new CircleModel(1, 15, 1);
        List<CircleModel> circles = new ArrayList<>();
        circles.add(circleModel1);
        circles.add(circleModel2);
        circles.add(circleModel3);
        Collections.sort(circles);
        circles.forEach((CircleModel circleModel) -> logger.log(Level.INFO, circleModel.toString()));
        assertSame(circleModel3, circles.get(0));
        assertSame(circleModel1, circles.get(1));
        assertSame(circleModel2, circles.get(2));
    }

    /**
     * Tests whether the equals method is properly implemented in the CircleModel class.
     */
    @Test
    public void pointsEqualsTest() {
        CircleModel circleModel1 = new CircleModel(1, 1, 100);
        circleModel1.setColor(new ColorModel(0, 0, 0));
        CircleModel circleModel2 = new CircleModel(1, 1, 100);
        circleModel2.setColor(new ColorModel(0, 0, 0));
        CircleModel circleModel3 = new CircleModel(1, 15, 1);
        assertEquals(circleModel1, circleModel2);
        assertNotEquals(circleModel1, circleModel3);
        assertNotEquals(circleModel1, null);
    }
}
