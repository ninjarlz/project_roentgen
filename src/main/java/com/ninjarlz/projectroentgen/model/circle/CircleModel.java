package com.ninjarlz.projectroentgen.model.circle;

import com.ninjarlz.projectroentgen.model.color.ColorModel;
import com.ninjarlz.projectroentgen.model.point.CartesianPoint;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class CircleModel implements Comparable<CircleModel> {

    private CartesianPoint cartesianPoint;
    private ColorModel color;
    private double radius;

    public CircleModel(double x, double y, double radius) {
        cartesianPoint = new CartesianPoint(x, y);
        this.radius = radius;
        color = ColorModel.getRandomColor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CircleModel circle = (CircleModel) o;
        return radius == circle.radius &&
                Objects.equals(cartesianPoint, circle.cartesianPoint) &&
                Objects.equals(color, circle.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartesianPoint, color, radius);
    }

    @Override
    public int compareTo(CircleModel o) {
        if (o == null) {
            return 1;
        } else {
            return Double.compare(radius, o.radius);
        }
    }
}
