package ca.nuba.nubamenu;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
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

import ca.nuba.nubamenu.data.NubaContract.NubaMenuEntry;


public class FetchNubaMenuTask extends AsyncTask<String, Void, Void> {
    private final String LOG_TAG = FetchNubaMenuTask.class.getSimpleName();
    private final Context mContext;

    FetchNubaMenuTask(Context context){
        mContext = context;
    }

    @Override
    protected Void doInBackground(String... params){

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
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0){
                return null;
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
        return null;
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


            JSONObject menuDataJason = new JSONObject(nubaMenuJsonStr);

            for (int i=0; i<menuDataJason.length(); i++){
                JSONArray menuDataArray = menuDataJason.getJSONArray(menuDataJason.names().getString(i));

                for (int j =0; j<menuDataArray.length(); j++){

                    JSONObject menuInfo = menuDataArray.getJSONObject(j);
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(NubaMenuEntry.COLUMN_MENU_TYPE, menuDataJason.names().getString(i));
                    contentValues.put(NubaMenuEntry.COLUMN_NAME, menuInfo.getString(NUBA_NAME));
                    contentValues.put(NubaMenuEntry.COLUMN_PRICE, menuInfo.getDouble(NUBA_PRICE));
                    contentValues.put(NubaMenuEntry.COLUMN_VEGETARIAN, menuInfo.getBoolean(NUBA_V));
                    contentValues.put(NubaMenuEntry.COLUMN_VEGAN, menuInfo.getBoolean(NUBA_VE));
                    contentValues.put(NubaMenuEntry.COLUMN_GLUTEN_FREE, menuInfo.getBoolean(NUBA_GF));
                    contentValues.put(NubaMenuEntry.COLUMN_DESCRIPTION, menuInfo.getString(NUBA_DESC));
                    contentValues.put(NubaMenuEntry.COLUMN_PIC_PATH, menuInfo.getString(NUBA_PIC_PATH));
                    contentValues.put(NubaMenuEntry.COLUMN_ICON_PATH, menuInfo.getString(NUBA_ICON_PATH));

                    mContext.getContentResolver().insert(NubaMenuEntry.CONTENT_URI, contentValues);
                    Log.v(LOG_TAG, "Inserting");
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
