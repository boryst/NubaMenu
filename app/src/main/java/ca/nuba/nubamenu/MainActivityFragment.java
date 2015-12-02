package ca.nuba.nubamenu;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ImageAdapter mImageAdapted;
    String mFlag;
    ImageButton imgBtnG, imgBtnY, imgBtnM, imgBtnK;

    static final String[] LOCATIONS = new String[]{
            "g","k","m","y"
    };

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        // prepare
/**        int strokeWidth = 5; // 3px not dp
        int roundRadius = 15; // 8px not dp
        int strokeColor = Color.parseColor("#2E3135");
        int fillColor = Color.parseColor("#DFDFE0");

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);*/

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        int nubaGPortrait = getActivity().getResources().getIdentifier("nubag", "drawable", "ca.nuba.nubamenu");
        int nubaKPortrait = getActivity().getResources().getIdentifier("nubak", "drawable", "ca.nuba.nubamenu");
        int nubaMPortrait = getActivity().getResources().getIdentifier("nubam", "drawable", "ca.nuba.nubamenu");
        int nubaYPortrait = getActivity().getResources().getIdentifier("nubay", "drawable", "ca.nuba.nubamenu");
        int nubaGLand = getActivity().getResources().getIdentifier("nubagl", "drawable", "ca.nuba.nubamenu");
        int nubaKLand = getActivity().getResources().getIdentifier("nubakl", "drawable", "ca.nuba.nubamenu");
        int nubaMLand = getActivity().getResources().getIdentifier("nubaml", "drawable", "ca.nuba.nubamenu");
        int nubaYLand = getActivity().getResources().getIdentifier("nubayl", "drawable", "ca.nuba.nubamenu");










        imgBtnG = (ImageButton) rootView.findViewById(R.id.nubaG);

/*        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

           //Drawable firstLayer = getResources().getDrawable(R.drawable.nubag);
            Drawable firstLayer = getResources().getDrawable(nubaGPortrait);


            GradientDrawable secondLayer = new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR, new int[]{
                    getResources().getColor(R.color.gradientAccent),
                    getResources().getColor(R.color.gradientAccent),
                    getResources().getColor(R.color.gradientPrimary)});

            Drawable[] layers = new Drawable[]{firstLayer, secondLayer};


            LayerDrawable ld = new LayerDrawable(layers);
            StateListDrawable states = new StateListDrawable();
                states.addState(new int[] {android.R.attr.state_pressed}, ld);
                states.addState(new int[] {android.R.attr.state_focused}, ld);
                states.addState(new int[]{}, firstLayer);

            imgBtnG.setImageDrawable(states);


        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ColorStateList csl = ColorStateList.valueOf(getResources().getColor(R.color.primary));
            RippleDrawable d = new RippleDrawable(csl, getResources().getDrawable(nubaGPortrait), null);
            imgBtnG.setImageDrawable(d);
        }*/








        imgBtnG.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mFlag = "Gastown";
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class).putExtra("EXTRA_LOCATION", mFlag);
                startActivity(intent);
            }
        });

        imgBtnK = (ImageButton) rootView.findViewById(R.id.nubaK);

        imgBtnK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mFlag = "Kitsilano";
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class).putExtra("EXTRA_LOCATION", mFlag);
                startActivity(intent);
            }
        });

        imgBtnM = (ImageButton) rootView.findViewById(R.id.nubaM);

        imgBtnM.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mFlag = "Main St.";
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class).putExtra("EXTRA_LOCATION", mFlag);
                startActivity(intent);
            }
        });

        imgBtnY = (ImageButton) rootView.findViewById(R.id.nubaY);

        imgBtnY.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mFlag = "Yaletown";
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class).putExtra("EXTRA_LOCATION", mFlag);
                startActivity(intent);
            }
        });





        int orientation = getResources().getConfiguration().orientation;

        if (orientation == 2){
            imageButtonPressEffect(imgBtnG, nubaGLand);
            imageButtonPressEffect(imgBtnK, nubaKLand);
            imageButtonPressEffect(imgBtnM, nubaMLand);
            imageButtonPressEffect(imgBtnY, nubaYLand);
        } else {
            imageButtonPressEffect(imgBtnG, nubaGPortrait);
            imageButtonPressEffect(imgBtnK, nubaKPortrait);
            imageButtonPressEffect(imgBtnM, nubaMPortrait);
            imageButtonPressEffect(imgBtnY, nubaYPortrait);
        }


        return rootView;
    }

    public void imageButtonPressEffect(ImageButton button, int picture){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            //Drawable firstLayer = getResources().getDrawable(R.drawable.nubag);
            Drawable firstLayer = getResources().getDrawable(picture);


            GradientDrawable secondLayer = new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR, new int[]{
                    getResources().getColor(R.color.gradientAccent),
                    getResources().getColor(R.color.gradientAccent),
                    getResources().getColor(R.color.gradientPrimary)});

            Drawable[] layers = new Drawable[]{firstLayer, secondLayer};


            LayerDrawable ld = new LayerDrawable(layers);
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[] {android.R.attr.state_pressed}, ld);
            states.addState(new int[] {android.R.attr.state_focused}, ld);
            states.addState(new int[]{}, firstLayer);

            button.setImageDrawable(states);


        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ColorStateList csl = ColorStateList.valueOf(getResources().getColor(R.color.primary));
            RippleDrawable d = new RippleDrawable(csl, getResources().getDrawable(picture), null);
            button.setImageDrawable(d);
        }
    }
}
