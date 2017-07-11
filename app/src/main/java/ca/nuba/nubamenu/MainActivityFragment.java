package ca.nuba.nubamenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ca.nuba.nubamenu.data.NubaContract;

import static android.content.Context.MODE_PRIVATE;
import static ca.nuba.nubamenu.Utility.LOCATION_EXTRA;
import static ca.nuba.nubamenu.Utility.LOCATION_GASTOWN;
import static ca.nuba.nubamenu.Utility.LOCATION_KITSILANO;
import static ca.nuba.nubamenu.Utility.LOCATION_MOUNT;
import static ca.nuba.nubamenu.Utility.LOCATION_YALETOWN;
import static ca.nuba.nubamenu.Utility.NUBA_PREFS;
import static ca.nuba.nubamenu.Utility.imageButtonPressEffect;


public class MainActivityFragment extends Fragment {

    ImageButton imgBtnG, imgBtnY, imgBtnM, imgBtnK;
    FragmentManager fm;
    MenuSelectActivityFragment menuSelectActivityFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getFragmentManager();
        menuSelectActivityFragment = new MenuSelectActivityFragment();

        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        Cursor cursor = getActivity().getContentResolver().query(NubaContract.NubaMenuEntry.CONTENT_URI, null,null,null,null);
        if (cursor == null || !cursor.moveToFirst()) {
            FetchNubaMenuTask menuInfoTask = new FetchNubaMenuTask(getActivity());
            Log.v("MAF", "Starting menuInfoTask");
            menuInfoTask.execute();
        } else {
            cursor.close();
        }
    }

    @Override
    public void onResume() {
        getActivity().setTitle(getString(R.string.main_activity_title));
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(false);
        }
        super.onResume();
    }

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
//                Intent intent = new Intent(getActivity(), MenuSelectActivity.class);
                //intent.putExtra(LOCATION_EXTRA, LOCATION_GASTOWN);
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
                editor.putString(LOCATION_EXTRA, LOCATION_GASTOWN);
                editor.apply();
//                startActivity(intent);
                fm.beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, android.R.anim.slide_out_right )
                        .replace(R.id.fragment_container, menuSelectActivityFragment)
                        .addToBackStack("MenuSelectFragment")
                        .commit();
            }
        });

        imgBtnK = (ImageButton) rootView.findViewById(R.id.nubaK);
        //imgBtnK.setImageResource(nubaK);
        imageButtonPressEffect(imgBtnK, nubaK, getActivity());

        imgBtnK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class);
                //intent.putExtra(LOCATION_EXTRA, LOCATION_KITSILANO);

                SharedPreferences.Editor editor = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
                editor.putString(LOCATION_EXTRA, LOCATION_KITSILANO);
                editor.apply();
                startActivity(intent);
            }
        });

        imgBtnM = (ImageButton) rootView.findViewById(R.id.nubaM);
        //imgBtnM.setImageResource(nubaM);
        imageButtonPressEffect(imgBtnM, nubaM, getActivity());

        imgBtnM.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class);
//                intent.putExtra(LOCATION_EXTRA, LOCATION_MOUNT);
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
                editor.putString(LOCATION_EXTRA, LOCATION_MOUNT);
                editor.apply();
                startActivity(intent);
            }
        });

        imgBtnY = (ImageButton) rootView.findViewById(R.id.nubaY);
        //imgBtnY.setImageResource(nubaY);
        imageButtonPressEffect(imgBtnY, nubaY, getActivity());

        imgBtnY.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class);
                //intent.putExtra(LOCATION_EXTRA, LOCATION_YALETOWN);
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
                editor.putString(LOCATION_EXTRA, LOCATION_YALETOWN);
                editor.apply();
                startActivity(intent);
            }
        });

        return rootView;
    }

}
