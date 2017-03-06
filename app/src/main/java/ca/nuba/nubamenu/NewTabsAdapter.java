package ca.nuba.nubamenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import ca.nuba.nubamenu.data.NubaContract;

import static android.content.Context.MODE_PRIVATE;


public class NewTabsAdapter extends FragmentStatePagerAdapter {
    static final String LOG_TAG = NewTabsAdapter.class.getSimpleName();
    Context mContext;
    Cursor mCursor;
    String type;

    public NewTabsAdapter(FragmentManager fm){
        super(fm);
    }

    public NewTabsAdapter(FragmentManager fm, Context context){
        super(fm);
        mContext = context;
        SharedPreferences prefs = mContext.getSharedPreferences(MainActivityFragment.NUBA_PREFS, MODE_PRIVATE);
        type = prefs.getString(MenuSelectActivityFragment.TYPE_EXTRA, null);
        Log.v(LOG_TAG, "type - " + type);
        mCursor = context.getContentResolver().query(
                NubaContract.NubaMenuEntry.CONTENT_URI,
                new String[]{"DISTINCT " + NubaContract.NubaMenuEntry.COLUMN_MENU_TYPE},
                Utility.sNubaMenuWithLike,
                new String[]{type+"%"},
                null);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new MenuActivityFragment();

        Bundle args = new Bundle();
        mCursor.moveToPosition(position);

        args.putString(MenuActivityFragment.ARG_PAGE, mCursor.getString(0));
        Log.v(LOG_TAG, "Page name - "+mCursor.getString(0));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
        //return 10;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return "OBJECT "+ (position +1);
        mCursor.moveToPosition(position);
        return Utility.formatMenuType(mCursor.getString(0));
    }
}
