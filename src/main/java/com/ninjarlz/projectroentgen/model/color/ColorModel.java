package com.ninjarlz.projectroentgen.model.color;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;
import java.util.Random;

@AllArgsConstructor
@Data
public class ColorModel {

    private double r;
    private double g;
    private double b;

    public static ColorModel getRandomColor() {
        return new ColorModel(Math.random(), Math.random(), Math.random());
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }

}
