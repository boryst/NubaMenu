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
public class MenuSelectActivityFragment extends Fragment {


    //private String flag;
    private String kflag, upLocation; //location with brunch
    static final String STATE_MENU = "menuState";
    ImageButton imgViewLunch, imgViewDinner, imgViewBrunch;
    Bundle extras = new Bundle();

    public MenuSelectActivityFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        //Toast.makeText(getActivity(), "onSave called", Toast.LENGTH_LONG).show();


        savedInstanceState.putString(STATE_MENU, "Kitsilano");
        super.onSaveInstanceState(savedInstanceState);

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_menu_select, container, false);
        //Toast.makeText(getActivity(), "onCreateViewStarted", Toast.LENGTH_LONG).show();
        int lunchPicPortrait = getActivity().getResources().getIdentifier("nubalunch", "drawable", "ca.nuba.nubamenu");
        int brunchPicPortrait = getActivity().getResources().getIdentifier("nubabrunch", "drawable", "ca.nuba.nubamenu");
        int dinnerPicPortrait = getActivity().getResources().getIdentifier("nubadinner", "drawable", "ca.nuba.nubamenu");

        int lunchPicLand = getActivity().getResources().getIdentifier("nubalunch_land", "drawable", "ca.nuba.nubamenu");
        int brunchPicLand = getActivity().getResources().getIdentifier("nubabrunch_land", "drawable", "ca.nuba.nubamenu");
        int dinnerPicLand = getActivity().getResources().getIdentifier("nubadinner_land", "drawable", "ca.nuba.nubamenu");



        if (savedInstanceState !=null){
            kflag = savedInstanceState.getString(STATE_MENU);
        }


        Intent intent = getActivity().getIntent();

        if (intent != null) {
            Bundle extras = intent.getExtras();

            getActivity().setTitle(extras.getString("EXTRA_LOCATION"));

        }

        View rootView = inflater.inflate(R.layout.fragment_menu_select, container, false);

        //View rootView = inflater.inflate(R.layout.fragment_menu_select, container, false);
        if (intent.getStringExtra("EXTRA_LOCATION") != null) {
            upLocation = intent.getStringExtra("EXTRA_LOCATION");
            //Toast.makeText(getActivity(), upLocation, Toast.LENGTH_LONG).show();


        }
        kflag = intent.getStringExtra("EXTRA_LOCATION");
        if (kflag != null) {
            extras.putString("EXTRA_LOCATION", kflag);
        } else if (upLocation != null) {
            extras.putString("EXTRA_LOCATION", upLocation);

        }
        //Toast.makeText(getActivity(), "FLAG", Toast.LENGTH_LONG).show();


        //Bundle extras = new Bundle();




        if (intent !=null){
            if ((kflag != null) && (kflag.equals("Kitsilano")) || ((upLocation != null) && upLocation.equals("k"))){
//                Toast.makeText(getActivity(), intent.getStringExtra("flag"), Toast.LENGTH_LONG).show();
                rootView = inflater.inflate(R.layout.fragment_menu_select_brunch, container, false);

//                savedInstanceState.putString(STATE_MENU, "k");
//                super.onSaveInstanceState(savedInstanceState);

                imgViewBrunch = (ImageButton) rootView.findViewById(R.id.nubaMenuBrunch);
                //imageButtonPressEffect(imgViewBrunch, brunchPicPortrait);


                int orientation = getResources().getConfiguration().orientation;


                if (orientation == 2){
                    imageButtonPressEffect(imgViewBrunch, brunchPicLand);
                } else {
                    imageButtonPressEffect(imgViewBrunch, brunchPicPortrait);
                }



                imgViewBrunch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        extras.putString("EXTRA_TYPE", "Brunch");

                        //flag = "brunch";
                        Intent intent = new Intent(getActivity(), MenuActivity.class).putExtras(extras);
                        startActivity(intent);
                    }
                });
            } else {
                rootView = inflater.inflate(R.layout.fragment_menu_select, container, false);
                //Toast.makeText(getActivity(), intent.getStringExtra("flag"), Toast.LENGTH_LONG).show();
            }
        }



        imgViewLunch = (ImageButton) rootView.findViewById(R.id.nubaMenuLunch);
        imgViewLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extras.putString("EXTRA_TYPE", "Lunch");
                //flag = "lunch";
                Intent intent = new Intent(getActivity(), MenuActivity.class).putExtras(extras);
                startActivity(intent);
            }
        });

        imgViewDinner = (ImageButton) rootView.findViewById(R.id.nubaMenuDinner);
        imgViewDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extras.putString("EXTRA_TYPE", "Dinner");

                //Toast.makeText(getActivity(), extras.getString("EXTRA_TYPE"), Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(), extras.getString("EXTRA_LOCATION"), Toast.LENGTH_LONG).show();


                //flag = "dinner";
                Intent intent = new Intent(getActivity(), MenuActivity.class).putExtras(extras);
                startActivity(intent);
            }
        });

//        imgViewBrunch = (ImageButton) rootView.findViewById(R.id.nubaMenuBrunch);
//        imgViewBrunch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                flag = "brunch";
//                Intent intent = new Intent(getActivity(), MenuActivity.class).putExtra("flag", flag);
//                startActivity(intent);
//            }
//        });

        int orientation = getResources().getConfiguration().orientation;


        if (orientation == 2){
            imageButtonPressEffect(imgViewLunch, lunchPicLand);
            imageButtonPressEffect(imgViewDinner, dinnerPicLand);
        } else {
            imageButtonPressEffect(imgViewLunch, lunchPicPortrait);
            imageButtonPressEffect(imgViewDinner, dinnerPicPortrait);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
        //Toast.makeText(getActivity(), "created", Toast.LENGTH_LONG).show();

        if (savedInstanceState !=null){

            //Toast.makeText(getActivity(), "something there", Toast.LENGTH_LONG).show();
            //Toast.makeText(getActivity(), savedInstanceState.getString(STATE_MENU), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getActivity(), "resumed", Toast.LENGTH_LONG).show();

        Intent intent = getActivity().getIntent();


        if (intent.getStringExtra("EXTRA_LOCATION") != null) {
            upLocation = intent.getStringExtra("EXTRA_LOCATION");
            //Toast.makeText(getActivity(), "location is here", Toast.LENGTH_LONG).show();

//            if (upLocation.equals("k")) {
////                Toast.makeText(getActivity(), intent.getStringExtra("flag"), Toast.LENGTH_LONG).show();
//                View rootView = new View;
//                rootView = inflater.inflate(R.layout.fragment_menu_select_brunch, container, false);
//            }

        }
        //Toast.makeText(getActivity(), kflag, Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(), extras.getString("EXTRA_LOCATION"), Toast.LENGTH_LONG).show();
        //extras.getString("EXTRA_LOCATION");




    }
}
