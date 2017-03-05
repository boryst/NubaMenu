package ca.nuba.nubamenu;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;

import org.w3c.dom.Text;

import java.math.BigDecimal;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    Boolean v, ve, gf;
    String price, name, desc, page;
    int picturePath, tabPosition;
    float numStars;
    float numStarsStatic, numStarsStatic2;
    int numRatings;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
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


        }

        ImageView imageView = (ImageView) rootView.findViewById(R.id.imgViewDetailImage);

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
        numRatings = 9;





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

        nameTextView.setText(name +" "+page);
/**
        imageView.setImageResource(picturePath);

 */
        priceTextView.setText(price);
        descTextView.setText(desc);


        if (v) vImgView.setImageResource(R.drawable.v);
        else vImgView.setImageResource(R.drawable.vg);

        if (ve) veImageView.setImageResource(R.drawable.ve);
        else veImageView.setImageResource(R.drawable.veg);

        if (gf) gfImageView.setImageResource(R.drawable.gf);
        else gfImageView.setImageResource(R.drawable.gfg);

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
}
