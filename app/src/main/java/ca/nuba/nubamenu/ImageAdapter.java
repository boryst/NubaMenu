package ca.nuba.nubamenu;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;

/**
 * Created by Someone on 2015-09-15.
 */
public class ImageAdapter extends BaseAdapter {

    Context context;
    private String[] locations;
    private String flag = null;
    //private static LayoutInflater inflater = null;

    public ImageAdapter(Context context, String[] locations){
        this.context = context;
        this.locations = locations;
    }

    @Override
    public int getCount(){
        return locations.length;
    }

    @Override
    public String getItem(int position){
        return flag;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        //Holder holder = new Holder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View listView;
        ImageView imageView;

        if (convertView == null){
            //listView = new View(context);
            imageView = new ImageView(context);
            //listView = inflater.inflate(R.layout.list_item_img, null);
            //imageView = inflater.inflate();

            //ImageView imageView = (ImageView) listView.findViewById(R.id.list_item_img);

            String location = locations[position];


            String IdAsString = imageView.getResources().getResourceName(imageView.getId());

            if (IdAsString == "nubaG"){
                imageView.setImageResource(R.drawable.nubag);
                flag = "g";
            } else if (IdAsString == "nubaK"){
                imageView.setImageResource(R.drawable.nubak);
                flag = "k";
            } else if (IdAsString == "nubaM"){
                imageView.setImageResource(R.drawable.nubam);
                flag = "m";
            } else {
                imageView.setImageResource(R.drawable.nubay);
                flag = "y";
            }
        } else {
            imageView = (ImageView) convertView;
        }

        return imageView;
    }

}
