package ca.nuba.nubamenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import static android.content.Context.MODE_PRIVATE;
import static ca.nuba.nubamenu.Utility.LOCATION_EXTRA;
import static ca.nuba.nubamenu.Utility.LOCATION_KITSILANO;
import static ca.nuba.nubamenu.Utility.NUBA_PREFS;
import static ca.nuba.nubamenu.Utility.TYPE_BRUNCH;
import static ca.nuba.nubamenu.Utility.TYPE_DINNER;
import static ca.nuba.nubamenu.Utility.TYPE_EXTRA;
import static ca.nuba.nubamenu.Utility.TYPE_LUNCH;


/**
 * Fragment with choice of type of menu (Brunch, Lunch, Dinner)
 */
public class MenuSelectActivityFragment extends Fragment {
    final String LOG_TAG = MenuSelectActivityFragment.class.getSimpleName();

    private String location; //location with brunch

    ImageButton imgBtnLunch, imgBtnDinner, imgBtnBrunch;
    Bundle extras = new Bundle();
    FragmentManager fm;

    public MenuSelectActivityFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Log.v(LOG_TAG, "onSaveInstanceState");
        //savedInstanceState.putString(STATE_MENU, "Kitsilano");
        savedInstanceState.putString(LOCATION_EXTRA, location);
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView;

        int nubaLunchID = getActivity().getResources().getIdentifier("nubal", "drawable", "ca.nuba.nubamenu");
        int nubaBrunchID = getActivity().getResources().getIdentifier("nubab", "drawable", "ca.nuba.nubamenu");
        int nubaDinnerID = getActivity().getResources().getIdentifier("nubad", "drawable", "ca.nuba.nubamenu");

//        if (savedInstanceState !=null){
//            kflag = savedInstanceState.getString(STATE_MENU);
//        }

        //Get location either from MainActivity or from MenuActivity and set page title
        Intent intent = getActivity().getIntent();

        SharedPreferences prefs = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE);
        location = prefs.getString(LOCATION_EXTRA, null);



/*        if (intent != null && intent.hasExtra(MainActivityFragment.LOCATION_EXTRA)) {
            //true only if we came from MainActivity
            Log.v(LOG_TAG, "onCreateView.intent != null");
            Log.v(LOG_TAG, "Intent - "+String.valueOf(intent));

            //Bundle extras = intent.getExtras();
            //location = extras.getString(MainActivityFragment.LOCATION_EXTRA);
            location = intent.getStringExtra(MainActivityFragment.LOCATION_EXTRA);
        } else {
            //if we came from MenuSelect or MainActvity?
            Log.v(LOG_TAG, "onCreateView.else");

            if (savedInstanceState != null){
                location = savedInstanceState.getString(LOCATION_EXTRA);
                Log.v(LOG_TAG, "savedInstanceState is not null");
            }
            Log.v(LOG_TAG, "location - "+location);

        }*/
        getActivity().setTitle(location);

        extras.putString(LOCATION_EXTRA, location);


/**        if (intent.getStringExtra(MainActivityFragment.LOCATION_EXTRA) != null) {
            upLocation = intent.getStringExtra(MainActivityFragment.LOCATION_EXTRA);
        }

        kflag = intent.getStringExtra(MainActivityFragment.LOCATION_EXTRA);

        if (kflag != null) {
            extras.putString(MainActivityFragment.LOCATION_EXTRA, kflag);
        } else if (upLocation != null) {
            extras.putString(MainActivityFragment.LOCATION_EXTRA, upLocation);

        }*/

        // if flag = brunch - load brunch layout
/**            if ((kflag != null) && (kflag.equals("Kitsilano")) || ((upLocation != null) && upLocation.equals("k"))){
                rootView = inflater.inflate(R.layout.fragment_menu_select_brunch, container, false);
                imgBtnBrunch = (ImageButton) rootView.findViewById(R.id.nubaMenuBrunch);
                Utility.imageButtonPressEffect(imgBtnBrunch, nubaBrunchID, getActivity());

                imgBtnBrunch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        extras.putString(TYPE_EXTRA, "Brunch");
                        Intent intent = new Intent(getActivity(), MenuActivity.class).putExtras(extras);
                        startActivity(intent);
                    }
                });
            } else {
                rootView = inflater.inflate(R.layout.fragment_menu_select, container, false);
            }*/

        if (location !=null && location.equals(LOCATION_KITSILANO)){
            rootView = inflater.inflate(R.layout.fragment_menu_select_brunch, container, false);
            imgBtnBrunch = (ImageButton) rootView.findViewById(R.id.nubaMenuBrunch);
            Utility.imageButtonPressEffect(imgBtnBrunch, nubaBrunchID, getActivity());

            imgBtnBrunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    extras.putString(TYPE_EXTRA, TYPE_BRUNCH);
                    Intent intent = new Intent(getActivity(), MenuActivity.class);
//                    intent.putExtras(extras);
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
                    editor.putString(TYPE_EXTRA, TYPE_BRUNCH);
                    editor.apply();
                    //Intent intent1 = new Intent(getActivity(), MenuActivity.class).putExtra(TYPE_EXTRA, TYPE_BRUNCH);
                    startActivity(intent);
                }
            });
        } else {
            rootView = inflater.inflate(R.layout.fragment_menu_select, container, false);
        }




        imgBtnLunch = (ImageButton) rootView.findViewById(R.id.nubaMenuLunch);
        Utility.imageButtonPressEffect(imgBtnLunch, nubaLunchID, getActivity());
        imgBtnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extras.putString(TYPE_EXTRA, TYPE_LUNCH);
                //flag = "lunch";
                Intent intent = new Intent(getActivity(), MenuActivity.class);
//                intent.putExtras(extras);
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
                editor.putString(TYPE_EXTRA, TYPE_LUNCH);
                editor.apply();
                startActivity(intent);
            }
        });

        imgBtnDinner = (ImageButton) rootView.findViewById(R.id.nubaMenuDinner);
        Utility.imageButtonPressEffect(imgBtnDinner, nubaDinnerID, getActivity());
        imgBtnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extras.putString(TYPE_EXTRA, TYPE_DINNER);
                //flag = "dinner";
                Intent intent = new Intent(getActivity(), MenuActivity.class);
//                intent.putExtras(extras);
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
                editor.putString(TYPE_EXTRA, TYPE_DINNER);
                editor.apply();
                startActivity(intent);
            }
        });

        return rootView;
    }


/**    public void imageButtonPressEffect(ImageButton button, int picture){
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
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {

        fm = getFragmentManager();
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        SharedPreferences prefs = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE);
        location = prefs.getString(LOCATION_EXTRA, null);
        getActivity().setTitle(location);

        if (fm.getBackStackEntryCount() > 0){
            ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (ab != null){
                ab.setDisplayHomeAsUpEnabled(true);
            }
        }

        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                if (fm.getBackStackEntryCount() > 0){
                    fm.popBackStack();
                }
            }
            default: return super.onOptionsItemSelected(item);
        }
    }
}
