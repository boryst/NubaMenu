package ca.nuba.nubamenu.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ca.nuba.nubamenu.Utility;
import ca.nuba.nubamenu.data.NubaContract.NubaMenuEntry;

/**
 * Created by Borys on 2017-04-20.
 */

public class FetchNubaMenuService extends IntentService {
public static final String LOG_TAG = FetchNubaMenuService.class.getSimpleName();

    public FetchNubaMenuService(){
        super("Nuba");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        HttpURLConnection urlCOnnection = null;
        BufferedReader reader = null;

        String nubaMenuJsonStr = null;

        try {
            final String MOVIE_BASE_URL = "http://boryst.com/get_nuba_json.php?pass=1234";

            URL url = new URL(MOVIE_BASE_URL);
            urlCOnnection = (HttpURLConnection) url.openConnection();
            urlCOnnection.setRequestMethod("GET");
            urlCOnnection.connect();

            InputStream inputStream = urlCOnnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null){
                return;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0){
                return;
            }

            nubaMenuJsonStr = buffer.toString();
            getNubaMenuDataFromJason(nubaMenuJsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        } catch (JSONException e){
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlCOnnection !=null){
                urlCOnnection.disconnect();
            }
            if (reader !=null){
                try {
                    reader.close();
                } catch (final IOException e){
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return;
    }

    private void getNubaMenuDataFromJason (String nubaMenuJsonStr) throws JSONException {
        try {
            final String NUBA_NAME = "name";
            final String NUBA_PRICE = "price";
            final String NUBA_V = "v";
            final String NUBA_VE = "ve";
            final String NUBA_GF = "gf";
            final String NUBA_DESC = "desc";
            final String NUBA_PIC_PATH = "pic_path";
            final String NUBA_ICON_PATH = "icon_path";
            final String NUBA_WED_ID = "web_id";



            JSONObject menuDataJason = new JSONObject(nubaMenuJsonStr);

            for (int i=0; i<menuDataJason.length(); i++){
                JSONArray menuDataArray = menuDataJason.getJSONArray(menuDataJason.names().getString(i));

                for (int j =0; j<menuDataArray.length(); j++){

                    JSONObject menuInfo = menuDataArray.getJSONObject(j);
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(NubaMenuEntry.COLUMN_MENU_TYPE, menuDataJason.names().getString(i));
                    contentValues.put(NubaMenuEntry.COLUMN_NAME, menuInfo.getString(NUBA_NAME));
                    contentValues.put(NubaMenuEntry.COLUMN_PRICE, menuInfo.getDouble(NUBA_PRICE));
                    contentValues.put(NubaMenuEntry.COLUMN_VEGETARIAN, menuInfo.getString(NUBA_V));
                    contentValues.put(NubaMenuEntry.COLUMN_VEGAN, menuInfo.getString(NUBA_VE));
                    contentValues.put(NubaMenuEntry.COLUMN_GLUTEN_FREE, menuInfo.getString(NUBA_GF));
                    contentValues.put(NubaMenuEntry.COLUMN_DESCRIPTION, menuInfo.getString(NUBA_DESC));
                    contentValues.put(NubaMenuEntry.COLUMN_PIC_PATH, Utility.imageNameCutter(menuInfo.getString(NUBA_PIC_PATH)));
                    contentValues.put(NubaMenuEntry.COLUMN_ICON_PATH, Utility.imageNameCutter(menuInfo.getString(NUBA_ICON_PATH)));
                    contentValues.put(NubaMenuEntry.COLUMN_GLUTEN_FREE, menuInfo.getString(NUBA_GF));
                    contentValues.put(NubaMenuEntry.COLUMN_WEB_ID, menuInfo.getInt(NUBA_WED_ID));




                    //this.getContentResolver().insert(NubaMenuEntry.CONTENT_URI, contentValues);
                    //this.getContentResolver().update(NubaMenuEntry.CONTENT_URI, contentValues);
                    Log.v(LOG_TAG, "Inserting");
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }
}