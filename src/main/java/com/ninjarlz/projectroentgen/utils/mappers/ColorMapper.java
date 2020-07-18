package com.ninjarlz.projectroentgen.utils.mappers;
import com.ninjarlz.projectroentgen.model.color.ColorModel;
import javafx.scene.paint.Color;

public class ColorMapper {

    public static Color mapColorModelToColor(ColorModel colorModel) {
        return new Color(colorModel.getR(), colorModel.getB(), colorModel.getB(), 1);
    }
}
