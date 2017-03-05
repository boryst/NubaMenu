package ca.nuba.nubamenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import static ca.nuba.nubamenu.Utility.imageButtonPressEffect;


public class MainActivityFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        //FetchNubaMenuTask menuInfoTask = new FetchNubaMenuTask(getActivity());
        //menuInfoTask.execute();
    }
    //String mFlag;
    ImageButton imgBtnG, imgBtnY, imgBtnM, imgBtnK;
    public static final String LOCATION_EXTRA = "LOCATION_EXTRA";
    public static final String LOCATION_GASTOWN = "Gastown";
    public static final String LOCATION_KITSILANO = "Kitsilano";
    public static final String LOCATION_MOUNT = "Mount Pleasant";
    public static final String LOCATION_YALETOWN = "Yaletown";

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

        int nubaG = getActivity().getResources().getIdentifier("nubag", "drawable", "ca.nuba.nubamenu");
        int nubaK = getActivity().getResources().getIdentifier("nubak", "drawable", "ca.nuba.nubamenu");
        int nubaM = getActivity().getResources().getIdentifier("nubam", "drawable", "ca.nuba.nubamenu");
        int nubaY = getActivity().getResources().getIdentifier("nubay", "drawable", "ca.nuba.nubamenu");

        imgBtnG = (ImageButton) rootView.findViewById(R.id.nubaG);
        //imgBtnG.setImageResource(nubaG);
        imageButtonPressEffect(imgBtnG, nubaG, getActivity());

        imgBtnG.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class).putExtra(LOCATION_EXTRA, LOCATION_GASTOWN);
                startActivity(intent);
            }
        });

        imgBtnK = (ImageButton) rootView.findViewById(R.id.nubaK);
        //imgBtnK.setImageResource(nubaK);
        imageButtonPressEffect(imgBtnK, nubaK, getActivity());

        imgBtnK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class).putExtra(LOCATION_EXTRA, LOCATION_KITSILANO);
                startActivity(intent);
            }
        });

        imgBtnM = (ImageButton) rootView.findViewById(R.id.nubaM);
        //imgBtnM.setImageResource(nubaM);
        imageButtonPressEffect(imgBtnM, nubaM, getActivity());

        imgBtnM.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class).putExtra(LOCATION_EXTRA, LOCATION_MOUNT);
                startActivity(intent);
            }
        });

        imgBtnY = (ImageButton) rootView.findViewById(R.id.nubaY);
        //imgBtnY.setImageResource(nubaY);
        imageButtonPressEffect(imgBtnY, nubaY, getActivity());

        imgBtnY.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class).putExtra(LOCATION_EXTRA, LOCATION_YALETOWN);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
