package com.ninjarlz.projectroentgen.model.color;

import com.ninjarlz.projectroentgen.utils.logs.FileAndConsoleLoggerFactory;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class used to test ColorModel implementation.
 */
public class ColorModelTest {

    private static final Logger logger = FileAndConsoleLoggerFactory.getConfiguredLogger(ColorModelTest.class.getName());

    /**
     * Tests whether the equals method is properly implemented in the ColorModel class.
     */
    @Test
    public void colorsEqualsTest() {
        ColorModel colorModel1 = new ColorModel(1, 1, 1);
        logger.log(Level.INFO, colorModel1.toString());
        ColorModel colorModel2 = new ColorModel(1, 1, 1);
        logger.log(Level.INFO, colorModel2.toString());
        ColorModel colorModel3 = new ColorModel(11, 22, 33);
        logger.log(Level.INFO, colorModel3.toString());
        assertEquals(colorModel1, colorModel2);
        assertNotEquals(colorModel1, colorModel3);
        assertNotEquals(colorModel1, null);
    }


}
