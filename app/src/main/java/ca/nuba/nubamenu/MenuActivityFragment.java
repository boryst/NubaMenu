package ca.nuba.nubamenu;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import ca.nuba.nubamenu.data.NubaContract;

import static android.content.Context.MODE_PRIVATE;


public class MenuActivityFragment extends Fragment {
    public static final String LOG_TAG = MenuActivityFragment.class.getSimpleName();

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_LOCATION = "ARG_LOCATION";
    public static final String ARG_MENU_TYPE = "ARG_MENU_TYPE";

    public static final String ARG_DISH_TYPE = "DISH_TYPE";

    public static String type, location;

    AlertDialog.Builder alert;


    private String mPageName;
    Boolean vCheckBoxBefore, veCheckBoxBefore; //variables for dynamic Checkboxes in filter
    Boolean vFilter, veFilter, gfFilter, mFilter,
    vFilter_onCreate;



    private View rootView;
    private ListView listView;
    private String mLocation;
    private String mMenuType;
    private MenuArrayAdapter mArrayAdapter;
    TextView textView;




    String tabBrunchDesc[] = new String[] {"",""};


    MenuItem[] lunchMezze, lunchPlates, lunchPitas, lunchSalads, lunchSoups, lunchToShare, lunchBeverages,
            brunchAll, brunchBevs, dinnerColdMezze, dinnerHotMezze, dinnerToShare, dinnerSoupsSalads, dinnerMains, features, desserts;

/*    public static MenuActivityFragment newInstance(int page, String location, String menuType) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_LOCATION, location);
        args.putString(ARG_MENU_TYPE, menuType);


        MenuActivityFragment fragment = new MenuActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onResume() {
        super.onResume();
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //Boolean test =  prefs.getBoolean("vFilter", true);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mPageName = getArguments().getString(ARG_PAGE);
        Log.v(LOG_TAG, "mPage - "+mPageName);

/*        mLocation = getArguments().getString(ARG_LOCATION);
        mMenuType = getArguments().getString(ARG_MENU_TYPE);*/


        setHasOptionsMenu(true);
        int resID = getActivity().getResources().getIdentifier("ic_launcher", "mipmap", "ca.nuba.nubamenu");

        SharedPreferences prefs = getActivity().getSharedPreferences(MainActivityFragment.NUBA_PREFS, MODE_PRIVATE);
        location = prefs.getString(MainActivityFragment.LOCATION_EXTRA, null);
        type = prefs.getString(MenuSelectActivityFragment.TYPE_EXTRA, null);
        //Log.v(LOG_TAG, "location from prefs - "+location+", type - "+type);

    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
/*            case android.R.id.home: {
                NavUtils.navigateUpTo(getActivity(), NavUtils.getParentActivityIntent(getActivity()).putExtra("EXTRA_LOCATION", mLocation));
                return true;
            }*/
            case R.id.action_filter: {
                /**filter();*/
                return true;

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        Cursor mCursor = getActivity().getContentResolver().query(
                NubaContract.NubaMenuEntry.CONTENT_URI,
                Utility.NUBA_MENU_PROJECTION,
                Utility.sNubaMenuWithLike,
                new String[]{mPageName},
                null);

/*        Cursor mCursor = getActivity().getContentResolver().query(
                NubaContract.NubaMenuEntry.CONTENT_URI,
                Utility.NUBA_MENU_PROJECTION,
                null,
                null,
                null);*/

        if (mCursor != null) {
            mCursor.moveToPosition(2);
            Log.v(LOG_TAG, "type - "+type);
            Log.v(LOG_TAG, "Cursor - "+String.valueOf(mCursor));
            //Log.v(LOG_TAG, "Cursor - " + mCursor.getString(Utility.COL_NUBA_MENU_NAME));
        }
        //String[] array = {"line 1", "line 2", "line 3", "line 4"};

        //ArrayList list = new ArrayList<>(Arrays.asList(array));

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.menu_recyclerview);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        //RecyclerAdapter recyclerAdapter = new RecyclerAdapter(mCursor);
        //recyclerView.setAdapter(recyclerAdapter);

        MyListCursorAdapter myListCursorAdapter = new MyListCursorAdapter(getActivity(),mCursor);
        recyclerView.setAdapter(myListCursorAdapter);



        //Bundle args = getArguments();
        //textView = ((TextView) rootView.findViewById(R.id.testTextView));
        //textView.setText(Integer.toString(args.getInt(ARG_OBJECT)));
/**



//        Intent intent = getActivity().getIntent();
//        if (intent != null) {
//            Bundle extras = intent.getExtras();
//            pageForTitle = extras.getInt("EXTRA_PAGE",1);
//        }




        ArrayList<MenuItem> arrayOfMenuItems = new ArrayList<MenuItem>();
        mArrayAdapter = new MenuArrayAdapter(getActivity(), arrayOfMenuItems);
        listView = (ListView) rootView.findViewById(R.id.menu_listview);
        listView.setAdapter(mArrayAdapter);
//        listInflater(vFilter, veFilter, gfFilter, mFilter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItem menuItemDetails = mArrayAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("picturePath", menuItemDetails.picturePath);
                intent.putExtra("name", menuItemDetails.name);
                *//**  combine tabTitles arrays into two dimentional array and get rid of this switch?  *//*
                switch (mMenuType){
                    case "Lunch":{
                        intent.putExtra("page", tabLunchTitles[mPage - 1]);
                        break;
                    }
                    case "Dinner":{
                        intent.putExtra("page", tabDinnerTitles[mPage - 1]);
                        break;
                    }
                    case "Brunch":{
                        intent.putExtra("page", tabBrunchTitles[mPage - 1]);
                        break;
                    }
                }

                intent.putExtra("price", menuItemDetails.price);
                intent.putExtra("v", menuItemDetails.v);
                intent.putExtra("ve", menuItemDetails.ve);
                intent.putExtra("gf", menuItemDetails.gf);
                intent.putExtra("desc", menuItemDetails.desc);
                //for up intent
                intent.putExtra("EXTRA_LOCATION", mLocation);
                intent.putExtra("EXTRA_TYPE", mMenuType);
                intent.putExtra("EXTRA_PAGE", mPage);

                startActivity(intent);
            }
        });

        //textViewPageTitle = (TextView) rootView.findViewById(R.id.menu_title_textview);
        //textViewPageDesc = (TextView) rootView.findViewById(R.id.menu_title_desc_textview);

        //TODO: make "Lunch" array and add there all lunch menus, and then change "switch" to loop


        //listInflater(null, null, null, null);



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
// then you use
        if (prefs != null) {
            Boolean test = prefs.getBoolean("vFilter", true);
           // Toast.makeText(getActivity(), String.valueOf(test), Toast.LENGTH_SHORT).show();

        }

        listInflater(vFilter_onCreate, veFilter, gfFilter, mFilter);*/
        if (mCursor != null) {
            //mCursor.close();
        }
        return rootView;
    }

    public void checkFilters(MenuItem[] array, MenuArrayAdapter adapter,
                             Boolean v, Boolean ve, Boolean gf, Boolean m, int page,
                             String[] arrayOfTitles, String[] arrayOfDesc){
        adapter.clear();
        //textViewPageTitle.setText(arrayOfTitles[page - 1]);
        //textViewPageDesc.setText(arrayOfDesc[page - 1]);

        if (v != null || ve != null || gf != null || m != null) {

        //TODO: Add "V but not Ve"

        if (v != null && gf != null) {
            for (MenuItem item:array) {
                if (item.v && item.gf){
                    adapter.add(item);
                }
            }

        } else if (ve != null && gf != null) {
            for (MenuItem item:array) {
                if (item.ve && item.gf){
                    adapter.add(item);
                }
            }

        } else if (m != null && gf != null) {
            for (MenuItem item:array) {
                if (!item.v && item.gf){
                    adapter.add(item);
                }
            }
        } else if (ve != null) {
            for (MenuItem item: array) {
                if (item.ve){
                    adapter.add(item);
                }
            }
        } else if (v != null) {
                for (MenuItem item: array) {
                    if (item.v){
                        adapter.add(item);
                    }
                }


            } else if (gf != null) {
                for (MenuItem item: array) {
                    if (item.gf){
                        adapter.add(item);
                    }
                }

            } else if (m != null) {

                for (MenuItem item : array) {
                    if (!item.v) {
                        adapter.add(item);
                    }
                }
            }



        } else  adapter.addAll(array);
            //    mArrayAdapter.clear();
        //} else mArrayAdapter.addAll(array);
    }




/**    public View listInflater(Boolean vFilter, Boolean veFilter, Boolean gfFilter, Boolean mFilter) {
        switch (mMenuType) {
            case "Lunch": {
//                for (MenuItem[] array: lunchArray) {
//                    checkFilters(array, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter);
//                    return listView;
//
//
//                }




                switch (mPage) {
                    case 1: {


                        checkFilters(lunchMezze, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter, mPage, tabLunchTitles, tabLunchDesc);
                        return listView;
                    }
                    case 2: {
                        //mArrayAdapter.addAll(lunchPlates);
                        checkFilters(lunchPlates, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter, mPage, tabLunchTitles, tabLunchDesc);
                        return listView;
                    }
                    case 3: {
                        //mArrayAdapter.addAll(lunchPitas);
                        checkFilters(lunchPitas, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter, mPage, tabLunchTitles, tabLunchDesc);

                        return listView;
                    }
                    case 4: {
                        //mArrayAdapter.addAll(lunchSalads);
                        checkFilters(lunchSalads, mArrayAdapter, vFilter, veFilter, gfFilter, mFilter, mPage, tabLunchTitles, tabLunchDesc);

                        return listView;
                    }
                    case 5: {
                        //mArrayAdapter.addAll(lunchSoups);
                        checkFilters(lunchSoups, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter, mPage, tabLunchTitles, tabLunchDesc);

                        return listView;
                    }
                    case 6: {
                        //mArrayAdapter.addAll(lunchToShare);
                        checkFilters(lunchToShare, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter, mPage, tabLunchTitles, tabLunchDesc);

                        return listView;
                    }
                    case 7: {
                        //mArrayAdapter.addAll(lunchBeverages);
                        checkFilters(lunchBeverages, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter, mPage, tabLunchTitles, tabLunchDesc);
                        return listView;
                    }
                }
            }
            case "Dinner": {
                switch (mPage) {
                    case 1: {
                        //mArrayAdapter.addAll(dinnerColdMezze);
                        checkFilters(dinnerColdMezze, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter, mPage, tabDinnerTitles, tabDinnerDesc);
                        return listView;
                    }
                    case 2: {
                        //mArrayAdapter.addAll(dinnerHotMezze);
                        checkFilters(dinnerHotMezze, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter, mPage, tabDinnerTitles, tabDinnerDesc);
                        return listView;
                    }
                    case 3: {
//                        mArrayAdapter.addAll(dinnerToShare);
                        checkFilters(dinnerToShare, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter, mPage, tabDinnerTitles, tabDinnerDesc);
                        return listView;
                    }
                    case 4: {
//                        mArrayAdapter.addAll(dinnerSoupsSalads);
                        checkFilters(dinnerSoupsSalads, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter, mPage, tabDinnerTitles, tabDinnerDesc);
                        return listView;
                    }
                    case 5: {
//                        mArrayAdapter.addAll(dinnerMains);
                        checkFilters(dinnerMains, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter, mPage, tabDinnerTitles, tabDinnerDesc);
                        return listView;
                    }

                }
            }
            case "Brunch": {
                switch (mPage) {
                    case 1: {
//                        mArrayAdapter.addAll(brunchAll);
                        checkFilters(brunchAll, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter, mPage, tabBrunchTitles, tabBrunchDesc);
                        return listView;
                    }
                    case 2: {
//                        mArrayAdapter.addAll(brunchBevs);
                        checkFilters(brunchBevs, mArrayAdapter, vFilter, veFilter, gfFilter,mFilter, mPage, tabBrunchTitles, tabBrunchDesc);
                        return listView;
                    }

                }
            }
        }
        return listView;
    }*/


/**    public void filter() {
        alert = new AlertDialog.Builder(getActivity());

        final View container = getActivity().getLayoutInflater().inflate(R.layout.filter, null);

        alert.setView(container);



        final CheckBox vCheckBox = (CheckBox) container.findViewById(R.id.filterVCheckBox);
        final CheckBox veCheckBox = (CheckBox) container.findViewById(R.id.filterVeCheckBox);
        CheckBox gfCheckBox = (CheckBox) container.findViewById(R.id.filterGfCheckBox);
        final CheckBox mCheckBox = (CheckBox) container.findViewById(R.id.filterMCheckBox);


        if (vFilter != null) vCheckBox.setChecked(true); else vCheckBox.setChecked(false);
        if (veFilter != null) veCheckBox.setChecked(true); else veCheckBox.setChecked(false);
        if (gfFilter != null) gfCheckBox.setChecked(true); else gfCheckBox.setChecked(false);
        if (mFilter != null) mCheckBox.setChecked(true); else mCheckBox.setChecked(false);




        vCheckBoxBefore = false;

        vCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    vFilter = true;
                    vCheckBoxBefore = true;
                    mFilter = null;
                    mCheckBox.setChecked(false);
                } else {
                    vFilter = null;
                    vCheckBoxBefore = false;
                }

            }
        });

        veCheckBoxBefore = false;
        veCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    vCheckBox.setChecked(true);
                    vFilter = true;
                    veFilter = true;
                    veCheckBoxBefore = true;
                    mFilter = null;
                    mCheckBox.setChecked(false);
                } else {
                    veFilter = null;
                    if (vCheckBoxBefore) vFilter = true; else vFilter = null;
                    vCheckBox.setChecked(vCheckBoxBefore);
                    veCheckBoxBefore = false;
                }
            }
        });


        gfCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    gfFilter = true;
                } else {
                    gfFilter = null;
                }
            }
        });

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    if (vCheckBox.isChecked() && !vCheckBoxBefore) vCheckBoxBefore = true;
                    vCheckBox.setChecked(false);
                    veCheckBox.setChecked(false);
                    mFilter = true;
                    vFilter = null;
                    veFilter = null;
                } else {
                    mFilter = null;
                    if (vCheckBoxBefore) vFilter = true; else vFilter = null;
                    if (veCheckBoxBefore) veFilter = true; else veFilter = null;
                    veCheckBox.setChecked(veCheckBoxBefore);
                    vCheckBox.setChecked(vCheckBoxBefore);
                }
            }
        });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();

                listInflater(vFilter, veFilter, gfFilter, mFilter);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        alert.show();
    }*/


    @Override
    public void onStart() {
        super.onStart();
/**        Cursor cursor = getActivity().getContentResolver().query(
                NubaContract.NubaMenuEntry.CONTENT_URI,
                Utility.NUBA_MENU_PROJECTION,
                null,
                null,
                null
        );

        if (cursor != null){
            cursor.moveToFirst();
            Log.v(LOG_TAG, "Cursor - "+cursor.getString(Utility.COL_NUBA_MENU_NAME));
            cursor.close();
        }*/

//        FetchNubaMenuTask menuInfoTask = new FetchNubaMenuTask(getActivity());
//        menuInfoTask.execute();
    }


}
