package ca.nuba.nubamenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import ca.nuba.nubamenu.data.NubaContract;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;
import static ca.nuba.nubamenu.Utility.ARG_PAGE;
import static ca.nuba.nubamenu.Utility.ARG_PAGE_NUMBER;
import static ca.nuba.nubamenu.Utility.FILTER_GLUTEN_FREE;
import static ca.nuba.nubamenu.Utility.FILTER_MEAT;
import static ca.nuba.nubamenu.Utility.FILTER_VEGAN;
import static ca.nuba.nubamenu.Utility.FILTER_VEGETARIAN;
import static ca.nuba.nubamenu.Utility.LOCATION_EXTRA;
import static ca.nuba.nubamenu.Utility.NUBA_PREFS;
import static ca.nuba.nubamenu.Utility.TYPE_EXTRA;
import static ca.nuba.nubamenu.Utility.addLocationToSql;
import static ca.nuba.nubamenu.Utility.sNubaMenuWithGfFilter;
import static ca.nuba.nubamenu.Utility.sNubaMenuWithLike;
import static ca.nuba.nubamenu.Utility.sNubaMenuWithVFilter;
import static ca.nuba.nubamenu.Utility.sNubaMenuWithVGfFilter;
import static ca.nuba.nubamenu.Utility.sNubaMenuWithVVeFilter;
import static ca.nuba.nubamenu.Utility.sNubaMenuWithVVeGfFilter;
import static ca.nuba.nubamenu.Utility.sNubaMenuWithVeFilter;
import static ca.nuba.nubamenu.Utility.sNubaMenuWithVeGfFilter;


public class MenuActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, MenuItemClickListener {
    public static String LOG_TAG = MenuActivityFragment.class.getSimpleName();

//    public static final String ARG_LOCATION = "ARG_LOCATION";
//    public static final String ARG_MENU_TYPE = "ARG_MENU_TYPE";
//
//    public static final String ARG_DISH_TYPE = "DISH_TYPE";

    public static String type, location;
    private FragmentManager fm;
    CursorLoader cursorLoader;
    private static final int MENU_LOADER = 0;
    ListCursorAdapter listCursorAdapter;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    AlertDialog.Builder alert;


    private String mPageName;
    int mPageNumber;
    Boolean vCheckBoxBefore, veCheckBoxBefore; //variables for dynamic Checkboxes in filter
    Boolean vFilter, veFilter, gfFilter, mFilter;

    String selection;
    String[] selectionArgs;

    private View rootView;

    @Override
    public void onResume() {

/*        if (prefs.getString(FILTER_VEGETARIAN, null) != null) {
            vFilter = Boolean.parseBoolean(prefs.getString(FILTER_VEGETARIAN, null));
        } else veFilter = null;
        if (prefs.getString(FILTER_VEGAN, null) != null) {
            veFilter = Boolean.parseBoolean(prefs.getString(FILTER_VEGAN, null));
        } else veFilter = null;
        if (prefs.getString(FILTER_GLUTEN_FREE, null) != null) {
            gfFilter = Boolean.parseBoolean(prefs.getString(FILTER_GLUTEN_FREE, null));
        } else gfFilter = null;
        checkFilters(vFilter, veFilter, gfFilter);*/


        //getLoaderManager().restartLoader(MENU_LOADER, null, this);
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPageName = getArguments().getString(ARG_PAGE);
        mPageNumber = getArguments().getInt(ARG_PAGE_NUMBER);
        //Log.v(LOG_TAG, "onCreate - "+mPageName);
        fm = getActivity().getSupportFragmentManager();

        setHasOptionsMenu(true);
        //int resID = getActivity().getResources().getIdentifier("ic_launcher", "mipmap", "ca.nuba.nubamenu");

        prefs = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE);
        location = prefs.getString(LOCATION_EXTRA, null);
        type = prefs.getString(TYPE_EXTRA, null);

        if (prefs.getString(FILTER_VEGETARIAN, null) != null) {
            if (!prefs.getString(FILTER_VEGETARIAN, null).equals("null")) {
                vFilter = Boolean.parseBoolean(prefs.getString(FILTER_VEGETARIAN, null));
            }
        } else veFilter = null;
        if (prefs.getString(FILTER_VEGAN, null) != null) {
            if (!prefs.getString(FILTER_VEGAN, null).equals("null")) {
                veFilter = Boolean.parseBoolean(prefs.getString(FILTER_VEGAN, null));
            }
        } else veFilter = null;
        if (prefs.getString(FILTER_GLUTEN_FREE, null) != null) {
            if (!prefs.getString(FILTER_GLUTEN_FREE, null).equals("null")) {
                gfFilter = Boolean.parseBoolean(prefs.getString(FILTER_GLUTEN_FREE, null));
            }
        } else gfFilter = null;

        checkFilters(vFilter, veFilter, gfFilter);

    }




    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter: {
                filter();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            //Log.v(LOG_TAG, mPageName+" is visible now");

            if (mPageName != null){
                Log.v(LOG_TAG, "String V - " + prefs.getString(FILTER_VEGETARIAN, null) + ", Boolean V - "+ vFilter);
                Log.v(LOG_TAG, "String VE - " + prefs.getString(FILTER_VEGAN, null) + ", Boolean VE - "+ veFilter);
                Log.v(LOG_TAG, "String GF - " + prefs.getString(FILTER_GLUTEN_FREE, null) + ", Boolean GF - "+ gfFilter);


                if (prefs.getString(FILTER_VEGETARIAN, null) != null) {
                    if (!prefs.getString(FILTER_VEGETARIAN, null).equals("null")) {
                        vFilter = Boolean.parseBoolean(prefs.getString(FILTER_VEGETARIAN, null));
                        //Log.v(LOG_TAG, "String V - " + prefs.getString(FILTER_VEGETARIAN, null) + ", Boolean V - " + vFilter);
                    }
                } else veFilter = null;
                if (prefs.getString(FILTER_VEGAN, null) != null) {
                    if (!prefs.getString(FILTER_VEGAN, null).equals("null")) {
                        veFilter = Boolean.parseBoolean(prefs.getString(FILTER_VEGAN, null));
                        //Log.v(LOG_TAG, "String VE - " + prefs.getString(FILTER_VEGAN, null) + ", Boolean VE - " + veFilter);
                    }
                } else veFilter = null;
                if (prefs.getString(FILTER_GLUTEN_FREE, null) != null) {
                    if (!prefs.getString(FILTER_GLUTEN_FREE, null).equals("null")) {
                        gfFilter = Boolean.parseBoolean(prefs.getString(FILTER_GLUTEN_FREE, null));
                        //Log.v(LOG_TAG, "String GF - " + prefs.getString(FILTER_GLUTEN_FREE, null) + ", Boolean GF - " + gfFilter);
                    }
                } else gfFilter = null;

                if (prefs.getString(FILTER_MEAT, null) != null) {
                    if (!prefs.getString(FILTER_MEAT, null).equals("null")) {
                        mFilter = Boolean.parseBoolean(prefs.getString(FILTER_MEAT, null));
                    }
                } else mFilter = null;


                Log.v(LOG_TAG, "After: String V - " + prefs.getString(FILTER_VEGETARIAN, null) + ", Boolean V - "+ vFilter);
                Log.v(LOG_TAG, "After: String VE - " + prefs.getString(FILTER_VEGAN, null) + ", Boolean VE - "+ veFilter);
                Log.v(LOG_TAG, "After: String GF - " + prefs.getString(FILTER_GLUTEN_FREE, null) + ", Boolean GF - "+ gfFilter);

                checkFilters(vFilter, veFilter, gfFilter);


                Cursor cursor = getContext().getContentResolver().query(
                        NubaContract.NubaMenuEntry.CONTENT_URI,
                        Utility.NUBA_MENU_PROJECTION,
                        selection,
                        selectionArgs,
                        null
                );
                listCursorAdapter.swapCursor(cursor);
            }

        } else {
//            Log.v(LOG_TAG, mPageName+" is invisible");
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.v(LOG_TAG, "onCreateView");
//        Log.v(LOG_TAG+ "|"+mPageName, "onCreateView");

        rootView = inflater.inflate(R.layout.fragment_menu, container, false);

/*        Cursor mCursor = getActivity().getContentResolver().query(
                NubaContract.NubaMenuEntry.CONTENT_URI,
                Utility.NUBA_MENU_PROJECTION,
                Utility.sNubaMenuWithLike,
                new String[]{mPageName},
                null);*/

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.menu_recyclerview);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);


//        myListCursorAdapter = new MyListCursorAdapter(getActivity(),mCursor, mPageNumber);
        listCursorAdapter = new ListCursorAdapter(getActivity(), null, mPageNumber, this);
        recyclerView.setAdapter(listCursorAdapter);


//        if (mCursor != null) {
//            mCursor.close();
//        }
        return rootView;
    }

    public void checkFilters(Boolean v, Boolean ve, Boolean gf){

        selection = null;
        selectionArgs = null;


        if (v == null){
            if (ve == null) {
                if (gf == null){
//                    selection = null;
//                    selection = Utility.sNubaMenuWithLike;
                    selection = addLocationToSql(sNubaMenuWithLike);
//                    selectionArgs = null;
                    selectionArgs = new String[]{mPageName, location};
                    Timber.v("Location - "+location);
                    Log.v(LOG_TAG, "Selection - default");
                } else {
                    selection = sNubaMenuWithGfFilter; // selection with gf
                    selectionArgs = new String[]{mPageName, String.valueOf(gf)};
                    Log.v(LOG_TAG, "Selection - gf");
                }
            } else if (gf == null){
                selection = sNubaMenuWithVeFilter;//selection with ve
                selectionArgs = new String[]{mPageName, String.valueOf(ve)};
                Log.v(LOG_TAG, "Selection - ve");
            } else {
                selection = sNubaMenuWithVeGfFilter;//selection with ve and gf
                selectionArgs = new String[]{mPageName, String.valueOf(ve), String.valueOf(gf)};
                Log.v(LOG_TAG, "Selection - ve +  gf");
            }
        }

        else if (!v) {
            if (gf == null){
                selection = sNubaMenuWithVVeFilter; //selection with v and ve
                selectionArgs = new String[]{mPageName, String.valueOf(v),String.valueOf(ve)};
                Log.v(LOG_TAG, "Selection - !v + !ve aka Meat");
            } else {
                selection = sNubaMenuWithVVeGfFilter; // selection v + ve + gf
                selectionArgs = new String[]{mPageName, String.valueOf(v),String.valueOf(ve),String.valueOf(gf)};
                Log.v(LOG_TAG + mPageName, "Selection - !v + !ve + gf aka Meat + gf");
            }

        }


        else if (ve == null){
            if (gf == null){
                selection = sNubaMenuWithVFilter; //selection with v
                selectionArgs = new String[]{mPageName, String.valueOf(v)};
                Log.v(LOG_TAG, "Selection - v");
            } else {
                selection = sNubaMenuWithVGfFilter; //selection with v ang gf
                selectionArgs = new String[]{mPageName, String.valueOf(v), String.valueOf(gf)};
                Log.v(LOG_TAG, "Selection - v + gf");
            }

        }


        else if (gf == null){
            selection = sNubaMenuWithVVeFilter; //selection with v and ve
            selectionArgs = new String[]{mPageName, String.valueOf(v),String.valueOf(ve)};
            Log.v(LOG_TAG, "Selection - v + ve");
        } else {
            selection = sNubaMenuWithVVeGfFilter; // selection v + ve + gf
            selectionArgs = new String[]{mPageName, String.valueOf(v),String.valueOf(ve),String.valueOf(gf)};
            Log.v(LOG_TAG + mPageName, "Selection - v + ve + gf");
        }
    }


    public void filter() {
        alert = new AlertDialog.Builder(getActivity());
        final View container = getActivity().getLayoutInflater().inflate(R.layout.filter, null);
        alert.setView(container);

        final CheckBox vCheckBox = (CheckBox) container.findViewById(R.id.filterVCheckBox);
        final CheckBox veCheckBox = (CheckBox) container.findViewById(R.id.filterVeCheckBox);
        final CheckBox gfCheckBox = (CheckBox) container.findViewById(R.id.filterGfCheckBox);
        final CheckBox mCheckBox = (CheckBox) container.findViewById(R.id.filterMCheckBox);

//        if (vFilter != null) vCheckBox.setChecked(true); else vCheckBox.setChecked(false);
//        if (veFilter != null) veCheckBox.setChecked(true); else veCheckBox.setChecked(false);
//        if (gfFilter != null) gfCheckBox.setChecked(true); else gfCheckBox.setChecked(false);
//        if (mFilter != null) mCheckBox.setChecked(true); else mCheckBox.setChecked(false);

        if (vFilter != null) {
            if (vFilter) {
                vCheckBox.setChecked(true);
            } else {
                vCheckBox.setChecked(false);
            }
        } else {
            vCheckBox.setChecked(false);
        }

        if (veFilter != null) {
            if (veFilter) {
                veCheckBox.setChecked(true);
            } else {
                veCheckBox.setChecked(false);
            }
        } else {
            veCheckBox.setChecked(false);
        }

        if (gfFilter != null) {
            if (gfFilter) {
                gfCheckBox.setChecked(true);
            } else {
                gfCheckBox.setChecked(false);
            }
        } else {
            gfCheckBox.setChecked(false);
        }

        if (mFilter != null) {
            if (mFilter) {
                mCheckBox.setChecked(true);
            } else {
                mCheckBox.setChecked(false);
            }
        } else {
            mCheckBox.setChecked(false);
        }


        vCheckBoxBefore = false;

        vCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
//
                    if (veFilter != null && !veFilter) veFilter = null;
//

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
                    vFilter = false;
                    veFilter = false;
                    Log.v(LOG_TAG, "here");
/*                    vFilter = null;
                    veFilter = null;*/
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

                checkFilters(vFilter, veFilter, gfFilter);
                editor = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
                editor.putString(FILTER_VEGETARIAN, String.valueOf(vFilter));
                editor.putString(FILTER_VEGAN, String.valueOf(veFilter));
                editor.putString(FILTER_GLUTEN_FREE, String.valueOf(gfFilter));
                editor.putString(FILTER_MEAT, String.valueOf(mFilter));
                Log.v(LOG_TAG + "-InAlert", "FILTER_VEGETARIAN - "+ String.valueOf(vFilter));
                Log.v(LOG_TAG + "-InAlert", "FILTER_VEGAN - "+ String.valueOf(veFilter));
                Log.v(LOG_TAG + "-InAlert", "FILTER_GLUTEN_FREE - "+ String.valueOf(gfFilter));

                editor.apply();


                    Cursor cursor = getActivity().getContentResolver().query(
                            NubaContract.NubaMenuEntry.CONTENT_URI,
                            Utility.NUBA_MENU_PROJECTION,
                            selection,
                            selectionArgs,
                            null
                    );
                    listCursorAdapter.swapCursor(cursor);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        alert.show();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        cursorLoader = new CursorLoader(
                getActivity(),
                NubaContract.NubaMenuEntry.CONTENT_URI,
                Utility.NUBA_MENU_PROJECTION,
                selection,
                selectionArgs,
                null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        listCursorAdapter.swapCursor(cursor);
        while (cursor.moveToNext()){
            if (cursor.getString(Utility.COL_NUBA_MODIFIFER).equals("feature")){

            }
            Timber.v("--        cursor.getPosition() - "+       cursor.getPosition());
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        listCursorAdapter.swapCursor(null);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(MENU_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void onMenuItemClick(ImageView sharedImageView) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("transition_name", ViewCompat.getTransitionName(sharedImageView));
        Timber.v("Transition name - "+ViewCompat.getTransitionName(sharedImageView));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                sharedImageView,
                ViewCompat.getTransitionName(sharedImageView)
        );
        startActivity(intent, options.toBundle());
    }
}
