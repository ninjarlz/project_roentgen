package com.ninjarlz.projectroentgen.model.color;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;


/**
 * Class representing a rgb color.
 */
@AllArgsConstructor
@Data
public class ColorModel {

    /**
     * Stores a r coordinate of the color.
     */
    private double r;
    /**
     * Stores a g coordinate of the color.
     */
    private double g;
    /**
     * Stores a b coordinate of the color.
     */
    private double b;


    /**
     * Creates a new instance of ColorModel with random values of coordinates.
     */
    public static ColorModel getRandomColor() {
        return new ColorModel(Math.random(), Math.random(), Math.random());
    }

    /**
     * Determines whether two colors are equal.
     *
     * @param o the other color
     * @return boolean determining whether colors are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ColorModel color = (ColorModel) o;
        return Double.compare(color.r, r) == 0 &&
                Double.compare(color.g, g) == 0 &&
                Double.compare(color.b, b) == 0;
    }

    /**
     * Gets the hashCode of color.
     *
     * @return the hashCode of color.
     */
    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }

    /**
     * Transforms a color into printable string of characters. Uses
     * apache commons-langs3.
     *
     * @return returns string containing the color's values.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("r: " + r).append(" g: " + g).append(" b: " + b).
                toString();
    }

}
