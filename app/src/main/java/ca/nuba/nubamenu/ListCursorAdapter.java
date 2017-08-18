package ca.nuba.nubamenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Locale;

import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ca.nuba.nubamenu.Utility.ITEM_ID_EXTRA;
import static ca.nuba.nubamenu.Utility.ITEM_WEB_ID_EXTRA;
import static ca.nuba.nubamenu.Utility.NUBA_PREFS;
import static ca.nuba.nubamenu.Utility.POSITION_EXTRA;
import static ca.nuba.nubamenu.Utility.TAB_NUMBER_EXTRA;
import static ca.nuba.nubamenu.Utility.WEB_IMAGE_STORAGE;

/**
 * Created by Borys on 2017-03-05.
 */

public class ListCursorAdapter extends CursorRecyclerViewAdapter<ListCursorAdapter.ViewHolder>{
    public static final String LOG_TAG = ListCursorAdapter.class.getSimpleName();


    Context mContext;
    int tabNumber;
    private final MenuItemClickListener menuItemClickListener;

    public ListCursorAdapter(Context context, Cursor cursor, int tabNumber, MenuItemClickListener menuItemClickListener){
        super(context,cursor);
        this.mContext = context;
        this.tabNumber = tabNumber;
        this.menuItemClickListener = menuItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView list_item_icon, list_item_vegetarian_icon, list_item_vegan_icon, list_item_gluten_icon, list_item_modifier;
        public TextView list_item_name, list_item_price;
        public View basicView;
        public ProgressBar pbLoading;

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
            list_item_modifier = (ImageView) view.findViewById(R.id.list_item_modifier);
            pbLoading = (ProgressBar) view.findViewById(R.id.pb_list_item_progress);
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

        final ListItem listItem = ListItem.fromCursor(cursor);


        File img = new File(mContext.getFilesDir() + "/" + listItem.getIconPath());

        ViewCompat.setTransitionName(viewHolder.list_item_icon, listItem.getName() + listItem.getId());

//        File img = new File(mContext.getFilesDir() + "/" + listItem.getPicPath());
        if (!img.exists()){
//            Log.v(LOG_TAG, "Image "+ listItem.getIconPath()+" does not exist");
            Utility.imageDownload(mContext, WEB_IMAGE_STORAGE + listItem.getIconPath(), listItem.getIconPath());
//            Utility.imageDownload(mContext, WEB_IMAGE_STORAGE + listItem.getIconPath(), listItem.getPicPath());
//            Picasso.with(mContext).load(WEB_IMAGE_STORAGE + listItem.getIconPath()).placeholder(R.drawable.progress_animation).into(viewHolder.list_item_icon);
//            Picasso.with(mContext).load(WEB_IMAGE_STORAGE + listItem.getPicPath()).placeholder(R.drawable.progress_animation).into(viewHolder.list_item_icon);
            Glide.with(mContext)
                    .load(WEB_IMAGE_STORAGE + listItem.getIconPath())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            viewHolder.pbLoading.setVisibility(GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            viewHolder.pbLoading.setVisibility(GONE);
                            return false;
                        }
                    })
                    .into(viewHolder.list_item_icon);
        } else {
//            Timber.v("Loading");
            Picasso.with(mContext).load(img).into(viewHolder.list_item_icon);
        }


        if (listItem.getVegetarian()){
            viewHolder.list_item_vegetarian_icon.setVisibility(VISIBLE);
            Picasso.with(mContext).load(R.drawable.v).into(viewHolder.list_item_vegetarian_icon);
        } else {
            viewHolder.list_item_vegetarian_icon.setVisibility(GONE);
        }

        if (listItem.getVegan()){
            viewHolder.list_item_vegan_icon.setVisibility(VISIBLE);
            Picasso.with(mContext).load(R.drawable.ve).into(viewHolder.list_item_vegan_icon);
        } else {
            viewHolder.list_item_vegan_icon.setVisibility(GONE);
        }

        if (listItem.getGlutenFree()){
            viewHolder.list_item_gluten_icon.setVisibility(VISIBLE);
            Picasso.with(mContext).load(R.drawable.gf).into(viewHolder.list_item_gluten_icon);
        } else {
            viewHolder.list_item_gluten_icon.setVisibility(GONE);
        }



        viewHolder.list_item_name.setText(listItem.getName());
        //Picasso.with(mContext).load("http://boryst.com/"+myListItem.getIconPath()).into(viewHolder.list_item_icon);
//        viewHolder.list_item_price.setText(String.valueOf(myListItem.getPrice()));
        viewHolder.list_item_price.setText("$" +String.format(Locale.CANADA, "%.2f", listItem.getPrice()));

        viewHolder.basicView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Log.v(LOG_TAG, "Click on "+ viewHolder.getAdapterPosition());
                SharedPreferences.Editor editor = mContext.getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).edit();
                editor.putInt(POSITION_EXTRA, viewHolder.getAdapterPosition());
                editor.putInt(TAB_NUMBER_EXTRA, tabNumber);
                editor.putInt(ITEM_ID_EXTRA, listItem.getId());
                editor.putInt(ITEM_WEB_ID_EXTRA, listItem.getWebId());
                editor.apply();

                final File img = new File(mContext.getFilesDir() + "/" + listItem.getPicPath());
//                if (!img.exists()) {
//                    Timber.v("-------------------------File does not exist, "+mContext.getFilesDir() + "/" + listItem.getPicPath());
//                } else {
//                    Timber.v("-------------------------File exists, "+mContext.getFilesDir() + "/" + listItem.getPicPath());
//                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && img.exists()){

                    Timber.v("-------------------------File exists and API is higher");


                    //Use transition
//                    menuItemClickListener.onMenuItemClick(viewHolder.list_item_icon);

                    menuItemClickListener.onMenuItemClick(viewHolder.list_item_icon);
                } else {
                    Timber.v("-------------------------File does not exist, or API is lower");

                    Intent intent = new Intent(mContext, DetailActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });

        if (listItem.getModifier().equals("new")){
            viewHolder.list_item_modifier.setImageResource(R.drawable.ic_new);
            viewHolder.list_item_modifier.setVisibility(VISIBLE);
        } else  if (listItem.getModifier().equals("feature")){
            viewHolder.list_item_modifier.setImageResource(R.drawable.ic_feature);
            viewHolder.list_item_modifier.setVisibility(VISIBLE);
        } else  {
            viewHolder.list_item_modifier.setVisibility(GONE);
        }

    }
}
