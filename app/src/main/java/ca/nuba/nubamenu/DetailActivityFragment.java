package ca.nuba.nubamenu;

import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.math.BigDecimal;
import java.util.Locale;

import ca.nuba.nubamenu.data.NubaContract;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;
import static ca.nuba.nubamenu.Utility.ITEM_ID_EXTRA;
import static ca.nuba.nubamenu.Utility.NUBA_PREFS;
import static ca.nuba.nubamenu.Utility.WEB_IMAGE_STORAGE;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

    Boolean v, ve, gf;
    String name, desc, page, picturePath;
    Double price;
    int  tabPosition;
    float numStars;
    float numStarsStatic, numStarsStatic2;
    int numRatings;

    ImageView imageView, imageViewV, imageViewVe, imageViewGf;
    TextView nameTextView, priceTextView, descTextView;
    private static final int DETAIL_LOADER = 0;
    CursorLoader cursorLoader;


    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.imgViewDetailImage);
        nameTextView = (TextView) rootView.findViewById(R.id.textViewDetailName);
        priceTextView = (TextView) rootView.findViewById(R.id.textViewDetailPrice);
        imageViewV = (ImageView) rootView.findViewById(R.id.imgViewDetailVegetarianIcon);
        imageViewVe = (ImageView) rootView.findViewById(R.id.imgViewDetailVeganIcon);
        imageViewGf = (ImageView) rootView.findViewById(R.id.imgViewDetailGlutenIcon);
        descTextView = (TextView) rootView.findViewById(R.id.textViewDetailDesc);



/**        SharedPreferences prefs = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE);
        int itemId = prefs.getInt(ITEM_ID_EXTRA, 0);
        Log.v(LOG_TAG, "itemId - "+itemId);
        //int tabNumber = prefs.getInt(TAB_NUMBER_EXTRA, 0);
        //Log.v(LOG_TAG, "position - "+position+", tabNumber - "+tabNumber);

        Cursor cursor = getActivity().getContentResolver().query(
                NubaContract.NubaMenuEntry.buildNubaMenuUriWithID(itemId),
                Utility.NUBA_MENU_PROJECTION,
                null,
                null,
                null);

        if (cursor != null){
            cursor.moveToFirst();
            name = cursor.getString(Utility.COL_NUBA_MENU_NAME);
            picturePath = cursor.getString(Utility.COL_NUBA_MENU_PIC_PATH);


            File img = new File(getActivity().getFilesDir() + "/" + picturePath);
            if (!img.exists()){
                Log.v(LOG_TAG, "Image "+picturePath+" does not exist");
                Utility.imageDownload(getActivity(), WEB_IMAGE_STORAGE + picturePath, picturePath);
                Picasso.with(getActivity()).load(WEB_IMAGE_STORAGE + picturePath).placeholder(R.drawable.progress_animation).into(imageView);
            } else {
                Picasso.with(getActivity()).load(img).into(imageView);
            }

            nameTextView.setText(name);
        }

        Log.v(LOG_TAG, "name - "+name+", picPath - "+picturePath);*/



        //TODO: Add CursorLoaders

/*        Intent intent = getActivity().getIntent();
        if (intent != null) {
            picturePath = intent.getIntExtra("picturePath", 1);
            name = intent.getStringExtra("name");
            price = intent.getStringExtra("price");
            v = intent.getBooleanExtra("v", false);
            ve = intent.getBooleanExtra("ve", false);
            gf = intent.getBooleanExtra("gf", false);
            desc = intent.getStringExtra("desc");
            tabPosition = intent.getIntExtra("EXTRA_PAGE", 4);
            page = intent.getStringExtra("page");


        }*/

/**        ImageView imageView = (ImageView) rootView.findViewById(R.id.imgViewDetailImage);

        imageView.setImageBitmap(decodeSampledBitmapFromResource(getContext().getResources(), picturePath, 100, 100));


        final TextView priceTextView = (TextView) rootView.findViewById(R.id.textViewDetailPrice);
        ImageView vImgView = (ImageView) rootView.findViewById(R.id.imgViewDetailVegetarianIcon);
        ImageView veImageView = (ImageView) rootView.findViewById(R.id.imgViewDetailVeganIcon);
        ImageView gfImageView = (ImageView) rootView.findViewById(R.id.imgViewDetailGlutenIcon);
        final TextView descTextView = (TextView) rootView.findViewById(R.id.textViewDetailDesc);
        TextView nameTextView = (TextView)rootView.findViewById(R.id.textViewDetailName);


        String user = "Borys";
        numStars = 0;
        numStarsStatic = 4f;
        numRatings = 9;*/





        //final RatingBar ratingIndicatorBar = (RatingBar) rootView.findViewById(R.id.detailIndicatorBar);
        //final TextView ratingIndicatorBarTextView = (TextView) rootView.findViewById(R.id.detailIndicatorBarTextView);

       // RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.detailBar);
        //final TextView ratingBarTextView = (TextView) rootView.findViewById(R.id.detailBarTextView);

//        ratingBar.setOnRatingBarChangeListener(
//                new RatingBar.OnRatingBarChangeListener() {
//                    @Override
//                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                        ratingBarTextView.setText(String.valueOf(rating));
//                        numStars = rating;
//                        numStarsStatic2 = ((numStarsStatic * numRatings) + numStars) / (numRatings + 1);
//                        ratingIndicatorBar.setRating(numStarsStatic2);
//                        ratingIndicatorBarTextView.setText(String.valueOf(round(numStarsStatic2, 1)));
//                                            }
//                }
//        );

/**        nameTextView.setText(name +" "+page);

        priceTextView.setText(price);
        descTextView.setText(desc);


        if (v) vImgView.setImageResource(R.drawable.v);
        else vImgView.setImageResource(R.drawable.vg);

        if (ve) veImageView.setImageResource(R.drawable.ve);
        else veImageView.setImageResource(R.drawable.veg);

        if (gf) gfImageView.setImageResource(R.drawable.gf);
        else gfImageView.setImageResource(R.drawable.gfg);*/

        return rootView;
    }

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }



    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        cursorLoader = new CursorLoader(
                getActivity(),
                NubaContract.NubaMenuEntry.buildNubaMenuUriWithID(getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).getInt(ITEM_ID_EXTRA, 0)),
                Utility.NUBA_MENU_PROJECTION,
                null,
                null,
                null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {


        if (cursor != null){
            cursor.moveToFirst();
            Timber.v("location - "+cursor.getString(Utility.COL_NUBA_LOCATION));
            picturePath = cursor.getString(Utility.COL_NUBA_MENU_PIC_PATH);
            name = cursor.getString(Utility.COL_NUBA_MENU_NAME);
            price = cursor.getDouble(Utility.COL_NUBA_MENU_PRICE);
            //Log.v(LOG_TAG, "price - "+String.valueOf(price));
            v = Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGETARIAN));
            //Log.v(LOG_TAG, "v - "+String.valueOf(v));
            ve = Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGAN));
            //Log.v(LOG_TAG, "ve - "+String.valueOf(ve));
            gf = Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_GLUTEN_FREE));
            //Log.v(LOG_TAG, "gf - "+String.valueOf(gf));
            desc = cursor.getString(Utility.COL_NUBA_MENU_DESCRIPTION);

/* Assign data to views*/
            File img = new File(getActivity().getFilesDir() + "/" + picturePath);
            if (!img.exists()){
                Log.v(LOG_TAG, "Image "+picturePath+" does not exist");
                Utility.imageDownload(getActivity(), WEB_IMAGE_STORAGE + picturePath, picturePath);
                Picasso.with(getActivity()).load(WEB_IMAGE_STORAGE + picturePath).placeholder(R.drawable.progress_animation).into(imageView);
            } else {
                Picasso.with(getActivity()).load(img).into(imageView);
            }

            nameTextView.setText(name);
            //priceTextView.setText(String.valueOf(price));
            priceTextView.setText("$" +String.format(Locale.CANADA, "%.2f", price));


            if (v){
                Picasso.with(getActivity()).load(R.drawable.v).into(imageViewV);
            } else{
                imageViewV.setVisibility(View.GONE);
//                Picasso.with(getActivity()).load(R.drawable.vg).into(imageViewV);
            }

            if (ve){
                Picasso.with(getActivity()).load(R.drawable.ve).into(imageViewVe);
            } else{
                imageViewVe.setVisibility(View.GONE);
//                Picasso.with(getActivity()).load(R.drawable.veg).into(imageViewVe);
            }

            if (gf){
                Picasso.with(getActivity()).load(R.drawable.gf).into(imageViewGf);
            } else{
                imageViewGf.setVisibility(View.GONE);
//                Picasso.with(getActivity()).load(R.drawable.gfg).into(imageViewGf);
            }

            descTextView.setText(desc);
            
        }

//        Log.v(LOG_TAG, "name - "+name+", picPath - "+picturePath);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }
}
