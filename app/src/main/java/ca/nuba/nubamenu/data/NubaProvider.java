package ca.nuba.nubamenu.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ca.nuba.nubamenu.data.NubaContract.NubaMenuEntry;

public class NubaProvider extends ContentProvider {




    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private NubaDbHelper mOpenHelper;

    static final int NUBA_MENU = 100;
    static final int NUBA_MENU_WITH_ID = 101;


    //Selections
    private static final String sNubaMenuWithID =
            NubaMenuEntry.TABLE_NAME+ "." + NubaMenuEntry._ID + " = ? ";



//    private static final String sNubaMenuWith --- =
//            MoviesEntry.TABLE_NAME+ "." + MoviesEntry.COLUMN_FAV_MOVIE + " = ? ";


    static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(NubaContract.CONTENT_AUTHORITY, NubaContract.PATH_NUBA_MENU, NUBA_MENU);
        uriMatcher.addURI(NubaContract.CONTENT_AUTHORITY, NubaContract.PATH_NUBA_MENU+"/#", NUBA_MENU_WITH_ID);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new NubaDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case NUBA_MENU:
                return NubaMenuEntry.CONTENT_TYPE;
            case NUBA_MENU_WITH_ID:
                return NubaMenuEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NUBA_MENU: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        NubaMenuEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case NUBA_MENU_WITH_ID: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        NubaMenuEntry.TABLE_NAME,
                        projection,
                        sNubaMenuWithID,
                        new String[]{NubaMenuEntry.getIDFromURI(uri)},
                        null,
                        null,
                        null
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (getContext() != null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }



    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case NUBA_MENU: {
                long _id = db.insert(NubaMenuEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = NubaMenuEntry.buildNubaMenuUriWithID(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (getContext() != null){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        if (selection == null) selection = "1";
        int rowsDeleted;
        switch (match){
            case NUBA_MENU: {
                rowsDeleted = db.delete(NubaMenuEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        if (rowsDeleted !=0  && getContext() != null){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {

            SQLiteDatabase db = mOpenHelper.getReadableDatabase();
            final int match = sUriMatcher.match(uri);
            int  rowsUpdated;

            switch (match){
                case NUBA_MENU:{
                    rowsUpdated = db.update(NubaMenuEntry.TABLE_NAME, values, selection, selectionArgs);
                    break;
                }
                case NUBA_MENU_WITH_ID:{
                    rowsUpdated = db.update(
                            NubaMenuEntry.TABLE_NAME,
                            values,
                            sNubaMenuWithID,
                            new String[]{NubaMenuEntry.getIDFromURI(uri)});
                    break;
                }
                default: {
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
                }

            }

            if (rowsUpdated !=0 && getContext() != null){
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return rowsUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NUBA_MENU:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(NubaMenuEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (getContext() != null){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
