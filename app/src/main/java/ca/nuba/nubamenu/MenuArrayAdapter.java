package ca.nuba.nubamenu;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imgView_icon);

        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.nameLayout);


        TextView textViewName = (TextView) convertView.findViewById(R.id.textView_name);
        TextView textViewPrice = (TextView) convertView.findViewById(R.id.textView_price);
        TextView textViewDesc = (TextView) convertView.findViewById(R.id.textView_shortDesc);
        // Populate the data into the template view using the data object





        if (menuItem.v) imageViewVIcon.setImageResource(R.drawable.v);
            else imageViewVIcon.setImageResource(R.drawable.vg);

        if (menuItem.gf) imageViewGFIcon.setImageResource(R.drawable.gf);
            else imageViewGFIcon.setImageResource(R.drawable.gfg);

        if (menuItem.ve) imageViewVEIcon.setImageResource(R.drawable.ve);
            else imageViewVEIcon.setImageResource(R.drawable.veg);


        if (textViewDesc != null){
            if (menuItem.desc.length() <= 53){
                textViewDesc.setText(menuItem.desc);
            } else {
                String desc = menuItem.desc.substring(0, 53)+"...";
                textViewDesc.setText(desc);
                //desc = desc.substring(0, desc.length()-1);



            }
/*
            //textViewDesc.setText(menuItem.desc);
            String desc = menuItem.desc;
                            //textViewDesc.setText(subString(desc, textViewDesc) );

            TextPaint paint = textViewDesc.getPaint();
            Rect rect = new Rect();
            String text = String.valueOf(textViewDesc.getText());
            paint.getTextBounds(text, 0, text.length(), rect);

            if(rect.height() > textViewDesc.getHeight() || rect.width() > textViewDesc.getWidth()) {

//                if (desc.length() > 0) {
//                    desc = desc.substring(0, desc.length()-1);
//                }

                textViewDesc.setText("...");

            } else
                textViewDesc.setText(menuItem.desc);
*/


        //textViewDesc.

        }
        textViewName.setText(menuItem.name);
        textViewPrice.setText(menuItem.price);


        iconImageView.setImageResource(menuItem.iconPath);
        //textViewPrice.setText(String.valueOf(linearLayout.getWidth()));


        return convertView;
    }

//    public String subString(String str, TextView tv){
//
//        TextPaint paint = tv.getPaint();
//        Rect rect = new Rect();
//        String text = String.valueOf(tv.getText());
//        paint.getTextBounds(text, 0, text.length(), rect);
//
//        if(rect.height() > tv.getHeight() || rect.width() > tv.getWidth()) {
//
//            str = str.substring(0, str.length() - 1);
//            //subString(str, tv);
//        } else {
//            str = str.substring(0, str.length() - 3);
//            str = str + "...";
//
//
//        }
//        return str;
//
//    }
}