package ca.nuba.nubamenu;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    Boolean v, ve, gf;
    String price, name, desc;
    int picturePath, tabPosition;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            picturePath = intent.getIntExtra("picturePath",1);
            name = intent.getStringExtra("name");
            price = intent.getStringExtra("price");
            v = intent.getBooleanExtra("v", false);
            ve = intent.getBooleanExtra("ve", false);
            gf = intent.getBooleanExtra("gf", false);
            desc = intent.getStringExtra("desc");
            tabPosition = intent.getIntExtra("EXTRA_PAGE", 4);

        }

        ImageView imageView = (ImageView)rootView.findViewById(R.id.imgViewDetailImage);
        TextView priceTextView = (TextView)rootView.findViewById(R.id.textViewDetailPrice);
        ImageView vImgView = (ImageView)rootView.findViewById(R.id.imgViewDetailVegetarianIcon);
        ImageView veImageView = (ImageView)rootView.findViewById(R.id.imgViewDetailVeganIcon);
        ImageView gfImageView = (ImageView)rootView.findViewById(R.id.imgViewDetailGlutenIcon);
        TextView descTextView = (TextView)rootView.findViewById(R.id.textViewDetailDesc);
//        TextView nameTextView = (TextView)rootView.findViewById(R.id.textViewDetailName);

        imageView.setImageResource(picturePath);
        priceTextView.setText(price);
//        nameTextView.setText(name);
        descTextView.setText(desc);


        if (v) vImgView.setImageResource(R.drawable.v);
        else vImgView.setImageResource(R.drawable.vg);

        if (ve) veImageView.setImageResource(R.drawable.ve);
        else veImageView.setImageResource(R.drawable.veg);

        if (gf) gfImageView.setImageResource(R.drawable.gf);
        else gfImageView.setImageResource(R.drawable.gfg);

        //Toast.makeText(getActivity(), tabPosition, Toast.LENGTH_LONG).show();
        return rootView;
    }
}
