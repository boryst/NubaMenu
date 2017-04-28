package ca.nuba.nubamenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ca.nuba.nubamenu.Utility.ITEM_ID_EXTRA;
import static ca.nuba.nubamenu.Utility.NUBA_PREFS;
import static ca.nuba.nubamenu.Utility.POSITION_EXTRA;
import static ca.nuba.nubamenu.Utility.TAB_NUMBER_EXTRA;
import static ca.nuba.nubamenu.Utility.WEB_IMAGE_STORAGE;

/**
 * Created by Borys on 2017-03-05.
 */

public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder>{
    public static final String LOG_TAG = MyListCursorAdapter.class.getSimpleName();

    Context mContext;
    int tabNumber;
    public MyListCursorAdapter(Context context, Cursor cursor, int tabNumber){
        super(context,cursor);
        this.mContext = context;
        this.tabNumber = tabNumber;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView list_item_icon, list_item_vegetarian_icon, list_item_vegan_icon, list_item_gluten_icon;
        public TextView list_item_name, list_item_price;
        public View basicView;
        public ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
            basicView = view;
            //mTextView = (TextView) view.findViewById(R.id.list_item_name);
            list_item_icon = (ImageView) view.findViewById(R.id.list_item_icon);
            list_item_vegetarian_icon = (ImageView) view.findViewById(R.id.list_item_vegetarian_icon);
            list_item_vegan_icon = (ImageView) view.findViewById(R.id.list_item_vegan_icon);
            list_item_gluten_icon = (ImageView) view.findViewById(R.id.list_item_gluten_icon);
            list_item_name = (TextView) view.findViewById(R.id.list_item_name);
            list_item_price = (TextView) view.findViewById(R.id.list_item_price);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_menu, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, Cursor cursor) {

        final MyListItem myListItem = MyListItem.fromCursor(cursor);


        //File img = new File(mContext.getFilesDir() + "/" + imageNameCutter(myListItem.getIconPath()));
        File img = new File(mContext.getFilesDir() + "/" + myListItem.getIconPath());
        if (!img.exists()){
            Log.v(LOG_TAG, "Image "+myListItem.getIconPath()+" does not exist");
            //Picasso.with(mContext).load("http:/boryst.com/"+myListItem.getPicPath()).into(viewHolder.list_item_icon);
            Utility.imageDownload(mContext, WEB_IMAGE_STORAGE + myListItem.getIconPath(), myListItem.getIconPath());



            Picasso.with(mContext).load(WEB_IMAGE_STORAGE + myListItem.getIconPath()).placeholder(R.drawable.progress_animation).into(viewHolder.list_item_icon);


        } else {
            //Log.v(LOG_TAG, "File exists");
            //Picasso.with(mContext).load(img).into(viewHolder.list_item_icon);
            //Log.v(LOG_TAG, "image name - "+imageNameCutter(myListItem.getIconPath()));

            Picasso.with(mContext).load(img).into(viewHolder.list_item_icon);
        }

        if (myListItem.getVegetarian()){
            viewHolder.list_item_vegetarian_icon.setVisibility(VISIBLE);
            Picasso.with(mContext).load(R.drawable.v).into(viewHolder.list_item_vegetarian_icon);
        } else {
            viewHolder.list_item_vegetarian_icon.setVisibility(GONE);
        }
//        else {
//            Picasso.with(mContext).load(R.drawable.vg).into(viewHolder.list_item_vegetarian_icon);
//        }

        if (myListItem.getVegan()){
            viewHolder.list_item_vegan_icon.setVisibility(VISIBLE);
            Picasso.with(mContext).load(R.drawable.ve).into(viewHolder.list_item_vegan_icon);
        } else {
            viewHolder.list_item_vegan_icon.setVisibility(GONE);
        }
//        else {
//            Picasso.with(mContext).load(R.drawable.veg).into(viewHolder.list_item_vegan_icon);
//        }

        if (myListItem.getGlutenFree()){
            viewHolder.list_item_gluten_icon.setVisibility(VISIBLE);
            Picasso.with(mContext).load(R.drawable.gf).into(viewHolder.list_item_gluten_icon);
        } else {
            viewHolder.list_item_gluten_icon.setVisibility(GONE);
        }
//        else {
//            Picasso.with(mContext).load(R.drawable.gfg).into(viewHolder.list_item_gluten_icon);
//        }


        viewHolder.list_item_name.setText(myListItem.getName());
        //Picasso.with(mContext).load("http://boryst.com/"+myListItem.getIconPath()).into(viewHolder.list_item_icon);
//        viewHolder.list_item_price.setText(String.valueOf(myListItem.getPrice()));
        viewHolder.list_item_price.setText("$" +String.format(Locale.CANADA, "%.2f", myListItem.getPrice()));

        viewHolder.basicView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Log.v(LOG_TAG, "Click on "+ viewHolder.getAdapterPosition());
                SharedPreferences.Editor editor = mContext.getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
                editor.putInt(POSITION_EXTRA, viewHolder.getAdapterPosition());
                editor.putInt(TAB_NUMBER_EXTRA, tabNumber);
                editor.putInt(ITEM_ID_EXTRA, myListItem.getId());
                editor.apply();

                Intent intent = new Intent(mContext, DetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
