package ca.nuba.nubamenu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Borys on 15-10-13.
 */
public class MenuArrayAdapter extends ArrayAdapter<MenuItem> {

//    String[][] result;
MenuItem[] result;

    public MenuArrayAdapter(Context context, ArrayList<MenuItem> menuItems) {
        super(context, 0, menuItems);
//        result = new String[0][0];
        //result = new MenuItem[0];
    }

    public class Holder{
        ImageView imageViewVIcon;
        ImageView imageViewVEIcon;
        ImageView imageViewGFIcon;
        ImageView iconImageView;

        TextView textViewName;
        TextView textViewPrice;
        TextView textViewDesc;

        LinearLayout linearLayout;
    }

//    public void setResult(String[][] menuInfo){
public void setResult(MenuItem[] menuInfo){
//        result = menuInfo;
        result = menuInfo;
        notifyDataSetChanged();
    }


/*    @Override
    public int getCount(){

        return result.length;
    }*/

    /***/

/**   @Override
    public MenuItem getItem(int position){

        MenuItem res = result[position];
        return res;
    }*/


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();
        // Get the data item for this position
        MenuItem menuItem = getItem(position);

        //result[position] = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_menu, parent, false);
        }
        // Lookup view for data population
        //ImageView imageViewIcon = (ImageView) convertView.findViewById(R.id.imgView_icon);
        holder.imageViewVIcon = (ImageView) convertView.findViewById(R.id.imgViewVegetarianIcon);
        holder.imageViewVEIcon = (ImageView) convertView.findViewById(R.id.imgViewVeganIcon);
        holder.imageViewGFIcon = (ImageView) convertView.findViewById(R.id.imgViewGlutenIcon);
        holder.iconImageView = (ImageView) convertView.findViewById(R.id.imgView_icon);

        holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.nameLayout);


        holder.textViewName = (TextView) convertView.findViewById(R.id.textView_name);
        holder.textViewPrice = (TextView) convertView.findViewById(R.id.textView_price);
        holder.textViewDesc = (TextView) convertView.findViewById(R.id.textView_shortDesc);
        // Populate the data into the template view using the data object

/**      */
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getContext().getResources(), R.drawable.v, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;

        int sampleSize = calculateInSampleSize(options, 20, 20);
/**      */


//        if (menuItem.v) holder.imageViewVIcon.setImageResource(R.drawable.v);
//            else holder.imageViewVIcon.setImageResource(R.drawable.vg);
//
//        if (menuItem.gf) holder.imageViewGFIcon.setImageResource(R.drawable.gf);
//            else holder.imageViewGFIcon.setImageResource(R.drawable.gfg);
//
//        if (menuItem.ve) holder.imageViewVEIcon.setImageResource(R.drawable.ve);
//            else holder.imageViewVEIcon.setImageResource(R.drawable.veg);


//        if (holder.textViewDesc != null){
//            if (menuItem.desc.length() <= 53){
//                holder.textViewDesc.setText(menuItem.desc);
//            } else {
//                String desc = menuItem.desc.substring(0, 53)+"...";
//                holder.textViewDesc.setText(desc);
//            }
//
//
//        }

        holder.textViewName.setText(menuItem.name);
        holder.textViewPrice.setText(menuItem.price);

              /**  holder.textViewName.setText(result[position].name);
                holder.textViewPrice.setText(result[position].price);*/



//        holder.textViewName.setText("SampleSize = "+sampleSize);
//        holder.textViewPrice.setText("Image width = " + imageWidth);
        //holder.textViewDesc.setText("Image type = "+imageType);


//        holder.iconImageView.setImageResource(menuItem.iconPath);

        holder.iconImageView.setImageBitmap(decodeSampledBitmapFromResource(getContext().getResources(),menuItem.iconPath, 100, 100));
        //holder.iconImageView.setImageBitmap(decodeSampledBitmapFromResource(getContext().getResources(),result[position].iconPath, 100, 100));

        return convertView;
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