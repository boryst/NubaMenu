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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

        prefs = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE);

        String prefV = prefs.getString(FILTER_VEGETARIAN, null);


        if (prefV != null) {
            Timber.v("============is not Null");
            if (!prefV.equals("null")) {
                vFilter = Boolean.parseBoolean(prefs.getString(FILTER_VEGETARIAN, null));
            }
        } else vFilter = null;
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
        Timber.v("onResume");
        Timber.v("V - "+prefs.getString(FILTER_VEGETARIAN, "**"));
        Timber.v("VE - "+prefs.getString(FILTER_VEGAN, "**"));
        Timber.v("GF - "+prefs.getString(FILTER_GLUTEN_FREE, "**"));
        Timber.v("M - "+prefs.getString(FILTER_MEAT, "**"));

        checkFilters(vFilter, veFilter, gfFilter);


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
        Timber.v("onCreate");
        Timber.v("V - "+prefs.getString(FILTER_VEGETARIAN, "--"));
        Timber.v("VE - "+prefs.getString(FILTER_VEGAN, "--"));
        Timber.v("GF - "+prefs.getString(FILTER_GLUTEN_FREE, "--"));
        Timber.v("M - "+prefs.getString(FILTER_MEAT, "--"));

        checkFilters(vFilter, veFilter, gfFilter);

    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter: {
                filter();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //Log.v(LOG_TAG, mPageName+" is visible now");

            if (mPageName != null) {
//                Log.v(LOG_TAG, "String V - " + prefs.getString(FILTER_VEGETARIAN, null) + ", Boolean V - "+ vFilter);
//                Log.v(LOG_TAG, "String VE - " + prefs.getString(FILTER_VEGAN, null) + ", Boolean VE - "+ veFilter);
//                Log.v(LOG_TAG, "String GF - " + prefs.getString(FILTER_GLUTEN_FREE, null) + ", Boolean GF - "+ gfFilter);


                if (prefs.getString(FILTER_VEGETARIAN, null) != null) {
                    if (!prefs.getString(FILTER_VEGETARIAN, null).equals("null")) {
                        vFilter = Boolean.parseBoolean(prefs.getString(FILTER_VEGETARIAN, null));
                        //Log.v(LOG_TAG, "String V - " + prefs.getString(FILTER_VEGETARIAN, null) + ", Boolean V - " + vFilter);
                    }
                } else vFilter = null;
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


//                Log.v(LOG_TAG, "After: String V - " + prefs.getString(FILTER_VEGETARIAN, null) + ", Boolean V - "+ vFilter);
//                Log.v(LOG_TAG, "After: String VE - " + prefs.getString(FILTER_VEGAN, null) + ", Boolean VE - "+ veFilter);
//                Log.v(LOG_TAG, "After: String GF - " + prefs.getString(FILTER_GLUTEN_FREE, null) + ", Boolean GF - "+ gfFilter);

                checkFilters(vFilter, veFilter, gfFilter);


                Cursor cursor = getContext().getContentResolver().query(
                        NubaContract.NubaMenuEntry.CONTENT_URI,
                        Utility.NUBA_MENU_PROJECTION,
                        selection,
                        selectionArgs,
                        null
                );
                listCursorAdapter.swapCursor(cursor);

//                double seconds = System.currentTimeMillis() / 1000;
//                while (cursor.moveToNext()){
//
//                    Timber.v(cursor.getString(COL_NUBA_MENU_NAME)+"--"+cursor.getString(COL_NUBA_START_DATE)+
//                            " < "+seconds+ " < "+cursor.getString(COL_NUBA_END_DATE));
//                    if (cursor.getDouble(COL_NUBA_START_DATE) < seconds && cursor.getDouble(COL_NUBA_END_DATE) > seconds){
//                        Timber.v("YES");
//                    } else {
//                        Timber.v("NO");
//                    }
//                }

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

    public void checkFilters(Boolean v, Boolean ve, Boolean gf) {

        selection = null;
        selectionArgs = null;


        if (v == null) {
            if (ve == null) {
                if (gf == null) {
//                    selection = null;
//                    selection = Utility.sNubaMenuWithLike;
                    selection = addLocationToSql(sNubaMenuWithLike);
//                    selectionArgs = null;
                    selectionArgs = new String[]{mPageName, location};
//                    Timber.v("Location - "+location);
//                    Log.v(LOG_TAG, "Selection - default");
                } else {
                    selection = addLocationToSql(sNubaMenuWithGfFilter); // selection with gf
                    selectionArgs = new String[]{mPageName, String.valueOf(gf), location};
//                    Log.v(LOG_TAG, "Selection - gf");
                }
            } else if (gf == null) {
                selection = addLocationToSql(sNubaMenuWithVeFilter);//selection with ve
                selectionArgs = new String[]{mPageName, String.valueOf(ve), location};
//                Log.v(LOG_TAG, "Selection - ve");
            } else {
                selection = addLocationToSql(sNubaMenuWithVeGfFilter);//selection with ve and gf
                selectionArgs = new String[]{mPageName, String.valueOf(ve), String.valueOf(gf), location};
//                Log.v(LOG_TAG, "Selection - ve +  gf");
            }
        } else if (!v) {
            if (gf == null) {
                selection = addLocationToSql(sNubaMenuWithVVeFilter); //selection with v and ve
                selectionArgs = new String[]{mPageName, String.valueOf(v), String.valueOf(ve), location};
//                Log.v(LOG_TAG, "Selection - !v + !ve aka Meat");
            } else {
                selection = addLocationToSql(sNubaMenuWithVVeGfFilter); // selection v + ve + gf
                selectionArgs = new String[]{mPageName, String.valueOf(v), String.valueOf(ve), String.valueOf(gf), location};
//                Log.v(LOG_TAG + mPageName, "Selection - !v + !ve + gf aka Meat + gf");
            }

        } else if (ve == null) {
            if (gf == null) {
                selection = addLocationToSql(sNubaMenuWithVFilter); //selection with v
                selectionArgs = new String[]{mPageName, String.valueOf(v), location};
//                Log.v(LOG_TAG, "Selection - v");
            } else {
                selection = addLocationToSql(sNubaMenuWithVGfFilter); //selection with v ang gf
                selectionArgs = new String[]{mPageName, String.valueOf(v), String.valueOf(gf), location};
//                Log.v(LOG_TAG, "Selection - v + gf");
            }

        } else if (gf == null) {
            selection = addLocationToSql(sNubaMenuWithVVeFilter); //selection with v and ve
            selectionArgs = new String[]{mPageName, String.valueOf(v), String.valueOf(ve), location};
//            Log.v(LOG_TAG, "Selection - v + ve");
        } else {
            selection = addLocationToSql(sNubaMenuWithVVeGfFilter); // selection v + ve + gf
            selectionArgs = new String[]{mPageName, String.valueOf(v), String.valueOf(ve), String.valueOf(gf), location};
//            Log.v(LOG_TAG + mPageName, "Selection - v + ve + gf");
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

        final RelativeLayout vRelativeLayout = (RelativeLayout) container.findViewById(R.id.rl_filter_v);
        final RelativeLayout veRelativeLayout = (RelativeLayout) container.findViewById(R.id.rl_filter_ve);
        final RelativeLayout gfRelativeLayout = (RelativeLayout) container.findViewById(R.id.rl_filter_gf);
        final RelativeLayout mRelativeLayout = (RelativeLayout) container.findViewById(R.id.rl_filter_m);

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

        vRelativeLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (vCheckBox.isChecked()) {
                    vCheckBox.setChecked(false);
                    vFilter = null;
                } else {
                    vCheckBox.setChecked(true);
                    vFilter = true;
                    if (veFilter != null && !veFilter) veFilter = null;
                    mCheckBox.setChecked(false);
                    mFilter = null;
                }
            }
        });

        veRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (veCheckBox.isChecked()) {
                    veCheckBox.setChecked(false);
                    veFilter = null;
                } else {
                    veCheckBox.setChecked(true);
                    veFilter = true;
                    vCheckBox.setChecked(true);
                    vFilter = true;
                    mCheckBox.setChecked(false);
                    mFilter = null;
                }
            }
        });

        gfRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gfCheckBox.isChecked()) {
                    gfCheckBox.setChecked(false);
                    gfFilter = null;
                } else {
                    gfCheckBox.setChecked(true);
                    gfFilter = true;
                }
            }
        });

        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckBox.isChecked()) {
                    mCheckBox.setChecked(false);
                    mFilter = null;
                    if (!vFilter) vFilter = null;
                    if (!veFilter) veFilter = null;
                } else {
                    mCheckBox.setChecked(true);
                    mFilter = true;
                    vCheckBox.setChecked(false);
                    vFilter = false;
                    veCheckBox.setChecked(false);
                    veFilter = false;
                }
            }
        });


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
//                Timber.v("Filters: v - "+vFilter+" , veFilter - "+veFilter+", gfFilter - "+gfFilter);

                dialog.dismiss();

                checkFilters(vFilter, veFilter, gfFilter);
                editor = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
                editor.putString(FILTER_VEGETARIAN, String.valueOf(vFilter));
                editor.putString(FILTER_VEGAN, String.valueOf(veFilter));
                editor.putString(FILTER_GLUTEN_FREE, String.valueOf(gfFilter));
                editor.putString(FILTER_MEAT, String.valueOf(mFilter));
                editor.apply();

                Timber.v("inAlert");
                Timber.v("V - "+prefs.getString(FILTER_VEGETARIAN, "--"));
                Timber.v("VE - "+prefs.getString(FILTER_VEGAN, "--"));
                Timber.v("GF - "+prefs.getString(FILTER_GLUTEN_FREE, "--"));
                Timber.v("M - "+prefs.getString(FILTER_MEAT, "--"));


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

//        double seconds = System.currentTimeMillis() / 1000;
//        while (cursor.moveToNext()){
//            Timber.v(cursor.getString(COL_NUBA_MENU_NAME)+"--"+cursor.getString(COL_NUBA_START_DATE)+
//            " < "+seconds+ " < "+cursor.getString(COL_NUBA_END_DATE));
//            if (cursor.getDouble(COL_NUBA_START_DATE) < seconds && cursor.getDouble(COL_NUBA_END_DATE) > seconds){
//                Timber.v("YES");
//            } else {
//                Timber.v("NO");
//            }
//        }

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
//        Timber.v("Transition name - "+ViewCompat.getTransitionName(sharedImageView));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                sharedImageView,
                ViewCompat.getTransitionName(sharedImageView)
        );
        startActivity(intent, options.toBundle());
    }
}
