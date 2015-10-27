package ca.nuba.nubamenu;

import android.content.Intent;
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


        if (savedInstanceState !=null){
            kflag = savedInstanceState.getString(STATE_MENU);
            //Toast.makeText(getActivity(), "STATE_MENU", Toast.LENGTH_LONG).show();
        }


        Intent intent = getActivity().getIntent();

        if (intent != null) {
            Bundle extras = intent.getExtras();

            getActivity().setTitle(extras.getString("EXTRA_LOCATION"));

        }



//        if (intent.getStringExtra("location") != null) {
//            upLocation = intent.getStringExtra("location");
//            //Toast.makeText(getActivity(), "location is in createView", Toast.LENGTH_LONG).show();
//            //Toast.makeText(getActivity(), upLocation, Toast.LENGTH_LONG).show();
//
//        }

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

        return rootView;
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
