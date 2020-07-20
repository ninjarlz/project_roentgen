package com.ninjarlz.projectroentgen.utils.mappers;
import com.ninjarlz.projectroentgen.model.color.ColorModel;
import javafx.scene.paint.Color;

/**
 * Class used to map instances of ColorModels to Colors.
 */
public class ColorMapper {

    /**
     * Maps an instance of ColorModel to a Color instance.
     *
     * @param colorModel mapped instance of ColorModel.
     * @return Color instance created from an instance of ColorModel.
     */
    public static Color mapColorModelToColor(ColorModel colorModel) {
        return new Color(colorModel.getR(), colorModel.getB(), colorModel.getB(), 1);
    }
}
