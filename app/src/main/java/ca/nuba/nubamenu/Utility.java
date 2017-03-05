package ca.nuba.nubamenu;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.ImageButton;

import ca.nuba.nubamenu.data.NubaContract;

/**
 * Created by Borys on 2017-03-04.
 */

public class Utility {

    public static final String[] NUBA_MENU_PROJECTION = {
            NubaContract.NubaMenuEntry.TABLE_NAME + "." + NubaContract.NubaMenuEntry._ID,
            NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE,
            NubaContract.NubaMenuEntry.COLUMN_NAME,
            NubaContract.NubaMenuEntry.COLUMN_PRICE,
            NubaContract.NubaMenuEntry.COLUMN_VEGETARIAN,
            NubaContract.NubaMenuEntry.COLUMN_VEGAN,
            NubaContract.NubaMenuEntry.COLUMN_GLUTEN_FREE,
            NubaContract.NubaMenuEntry.COLUMN_DESCRIPTION,
            NubaContract.NubaMenuEntry.COLUMN_PIC_PATH,
            NubaContract.NubaMenuEntry.COLUMN_ICON_PATH
    };

    public static final int COL_NUBA_MENU_ID = 0;
    public static final int COL_NUBA_MENU_MENU_TYPE = 1;
    public static final int COL_NUBA_MENU_NAME = 2;
    public static final int COL_NUBA_MENU_PRICE = 3;
    public static final int COL_NUBA_MENU_VEGETARIAN = 4;
    public static final int COL_NUBA_MENU_VEGAN = 5;
    public static final int COL_NUBA_MENU_GLUTEN_FREE = 6;
    public static final int COL_NUBA_MENU_DESCRIPTION = 7;
    public static final int COL_NUBA_MENU_PIC_PATH = 8;
    public static final int COL_NUBA_MENU_ICON_PATH = 9;



    /** Add ripple effect as second layer to ImageButtons*/

    public static void imageButtonPressEffect(ImageButton button, int picture, Context context){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            Drawable firstLayer = ContextCompat.getDrawable(context, picture);

            GradientDrawable secondLayer = new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR,
                    new int[]{
                            ContextCompat.getColor(context, R.color.gradientAccent),
                            ContextCompat.getColor(context, R.color.gradientAccent),
                            ContextCompat.getColor(context, R.color.gradientPrimary)
                    }
            );

            Drawable[] layers = new Drawable[]{firstLayer, secondLayer};

            LayerDrawable ld = new LayerDrawable(layers);
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[] {android.R.attr.state_pressed}, ld);
            states.addState(new int[] {android.R.attr.state_focused}, ld);
            states.addState(new int[]{}, firstLayer);

            button.setImageDrawable(states);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ColorStateList csl = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.primary));
            RippleDrawable d = new RippleDrawable(csl, ContextCompat.getDrawable(context, picture), null);
            button.setImageDrawable(d);
        }
    }
}
