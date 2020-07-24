package com.ninjarlz.projectroentgen.model.circle;

import com.ninjarlz.projectroentgen.model.point.CartesianPoint;
import com.ninjarlz.projectroentgen.utils.logs.FileAndConsoleLoggerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class used to test CircleService implementation.
 */
public class CircleServiceTest {

    private static final Logger logger = FileAndConsoleLoggerFactory.getConfiguredLogger(CircleServiceTest.class.getName());

    private CircleService circleService = CircleService.getInstance();

    /**
     * Prepares circleService before each performed test.
     */
    @BeforeEach
    public void clearService() {
        circleService.clear();
    }

    /**
     * Tests whether 'getCircleAt' method is properly implemented.
     */
    @Test
    public void getCircleAtTest() {
        circleService.createCircle(10, 10, 3, Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList());
        assertNotNull(circleService.getCircleAt(10, 10));
        assertNull(circleService.getCircleAt(-10, 10));
        circleService.createCircle(200, 200, 5, Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList());
        assertNotNull(circleService.getCircleAt(200, 198));
    }

    /**
     * Tests whether 'checkIfCircleIsAlreadyDefined' method is properly implemented.
     */
    @Test
    public void checkIfCircleIsAlreadyDefinedTest() {
        circleService.createCircle(40, 40, 3, Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList());
        assertTrue(circleService.checkIfCircleIsAlreadyDefined(40, 39));
        assertFalse(circleService.checkIfCircleIsAlreadyDefined(140, 40));
    }

    /**
     * Tests whether 'clear' method is properly implemented.
     */
    @Test
    public void clearTest() {
        circleService.createCircle(1, 1, 3, Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList());
        circleService.createCircle(100, 100, 5, Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList());
        circleService.clear();
        assertNull(circleService.getCircleAt(1, 1));
        assertNull(circleService.getCircleAt(100, 100));
    }

    /**
     * Tests whether 'getCircleIndex' method is properly implemented.
     */
    @Test
    public void getCircleIndexTest() {
        CircleModel circleModel1 = circleService.createCircle(10, 10, 3, Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        logger.log(Level.INFO, circleModel1.toString());
        CircleModel circleModel2 = circleService.createCircle(200, 200, 5, Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
        logger.log(Level.INFO, circleModel2.toString());
        assertEquals(0, circleService.getCircleIndex(circleModel1));
        assertEquals(1, circleService.getCircleIndex(circleModel2));
    }

    /**
     * Tests whether 'createCircle' and 'deleteCircle' methods are properly implemented.
     */
    @Test
    public void addDeleteCircleTest() {
        CircleModel circleModel1 = circleService.createCircle(10, 10, 3, Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
        logger.log(Level.INFO, circleModel1.toString());
        CircleModel circleModel2 = circleService.createCircle(200, 200, 5, Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
        logger.log(Level.INFO, circleModel2.toString());
        CircleModel circleModel3 = circleService.createCircle(400, 200, 5, Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
        logger.log(Level.INFO, circleModel3.toString());
        assertTrue(circleService.removeCircle(circleModel1));
        assertFalse(circleService.removeCircle(circleModel1));
        assertTrue(circleService.removeCircle(200, 200));
        assertFalse(circleService.removeCircle(2000, 200));
        assertEquals(0, circleService.getCircleIndex(circleModel3));
    }

    /**
     * Tests whether 'moveCircle' method is properly implemented.
     */
    @Test
    public void moveCircleTest() {
        CircleModel circleModel = circleService.createCircle(10, 10, 3, Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
        logger.log(Level.INFO, circleModel.toString());
        circleService.moveCircle(circleModel, 300, 340);
        assertEquals(new CartesianPoint(300, 340), circleModel.getCartesianPoint());
        logger.log(Level.INFO, circleModel.toString());
    }
}
