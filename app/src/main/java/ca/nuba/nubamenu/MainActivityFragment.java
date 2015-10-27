package ca.nuba.nubamenu;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ImageAdapter mImageAdapted;
    String mFlag;
    ListView listView;
    ImageButton imgViewG, imgViewY, imgViewM, imgViewK;

    static final String[] LOCATIONS = new String[]{
            "g","k","m","y"
    };

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);



        //mImageAdapted = new ImageAdapter(getActivity(), LOCATIONS);

//        listView = (ListView)rootView.findViewById(R.id.list_view_locations);
//        listView.setAdapter(mImageAdapted);
//
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                flag = mImageAdapted.getItem(position);
//                //Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getActivity(), MenuSelectActivity.class).putExtra("movieDetailsArray", flag);
//                startActivity(intent);
//            }
//        });
        imgViewG = (ImageButton) rootView.findViewById(R.id.nubaG);

        imgViewG.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mFlag = "Gastown";
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class).putExtra("EXTRA_LOCATION", mFlag);
                startActivity(intent);
            }
        });

        imgViewK = (ImageButton) rootView.findViewById(R.id.nubaK);

        imgViewK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mFlag = "Kitsilano";
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class).putExtra("EXTRA_LOCATION", mFlag);
                startActivity(intent);
            }
        });

        imgViewM = (ImageButton) rootView.findViewById(R.id.nubaM);

        imgViewM.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mFlag = "Mt. Pleasant";
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class).putExtra("EXTRA_LOCATION", mFlag);
                startActivity(intent);
            }
        });

        imgViewY = (ImageButton) rootView.findViewById(R.id.nubaY);

        imgViewY.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mFlag = "Yaletown";
                Intent intent = new Intent(getActivity(), MenuSelectActivity.class).putExtra("EXTRA_LOCATION", mFlag);
                startActivity(intent);
            }
        });


//        imgViewK = (ImageView) rootView.findViewById(R.id.nubaK);
//        imgViewM = (ImageView) rootView.findViewById(R.id.nubaM);
//        imgViewY = (ImageView) rootView.findViewById(R.id.nubaY);



        return rootView;
    }
}
