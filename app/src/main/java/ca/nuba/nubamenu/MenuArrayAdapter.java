package ca.nuba.nubamenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Borys on 15-10-13.
 */
public class MenuArrayAdapter extends ArrayAdapter<MenuItem> {
    public MenuArrayAdapter(Context context, ArrayList<MenuItem> menuItems) {
        super(context, 0, menuItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MenuItem menuItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_menu, parent, false);
        }
        // Lookup view for data population
        //ImageView imageViewIcon = (ImageView) convertView.findViewById(R.id.imgView_icon);
        ImageView imageViewVIcon = (ImageView) convertView.findViewById(R.id.imgViewVegetarianIcon);

        ImageView imageViewVEIcon = (ImageView) convertView.findViewById(R.id.imgViewVeganIcon);
        ImageView imageViewGFIcon = (ImageView) convertView.findViewById(R.id.imgViewGlutenIcon);


        TextView textViewName = (TextView) convertView.findViewById(R.id.textView_name);
        TextView textViewPrice = (TextView) convertView.findViewById(R.id.textView_price);
        // Populate the data into the template view using the data object

//
//        String imageName = "picture";
//        int resID = getContext().getResources().getIdentifier(imageName, "drawable", "package.name");
//        ImageView image;
//        imageViewIcon.setImageResource(resID );



        //imageViewIcon.setImageResource(menuItem.picturePath);
        if (menuItem.v) imageViewVIcon.setImageResource(R.drawable.v);
            else imageViewVIcon.setImageResource(R.drawable.vg);

        if (menuItem.gf) imageViewGFIcon.setImageResource(R.drawable.gf);
            else imageViewGFIcon.setImageResource(R.drawable.gfg);

        if (menuItem.ve) imageViewVEIcon.setImageResource(R.drawable.ve);
            else imageViewVEIcon.setImageResource(R.drawable.veg);

        textViewName.setText(menuItem.name);
        textViewPrice.setText(menuItem.price);

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imgView_icon);
        iconImageView.setImageResource(menuItem.iconPath);
        // Return the completed view to render on screen
        return convertView;
    }
}