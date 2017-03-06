package ca.nuba.nubamenu;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    final String LOG_TAG = RecyclerAdapter.class.getSimpleName();

    private List<String> myList;
    private int mPosition;
    private String mLine;
    private Cursor mCursor;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView listItem;
        public ImageView list_item_icon, list_item_vegetarian_icon, list_item_vegan_icon, list_item_gluten_icon;
        public TextView list_item_name, list_item_price;

        public MyViewHolder(View view) {
            super(view);

            list_item_icon = (ImageView) view.findViewById(R.id.list_item_icon);
            list_item_vegetarian_icon = (ImageView) view.findViewById(R.id.list_item_vegetarian_icon);
            list_item_vegan_icon = (ImageView) view.findViewById(R.id.list_item_vegan_icon);
            list_item_gluten_icon = (ImageView) view.findViewById(R.id.list_item_gluten_icon);
            list_item_name = (TextView) view.findViewById(R.id.list_item_name);
            list_item_price = (TextView) view.findViewById(R.id.list_item_price);
            //listItem = (TextView) view.findViewById(R.id.list_item_textView);
        }
    }


    public RecyclerAdapter(Cursor cursor) {
        Log.v(LOG_TAG, "RecyclerAdapter started");
        mCursor = cursor;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_menu, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //String line = myList.get(position);
        //mLine = line;
        //holder.listItem.setText(line);
        Log.v(LOG_TAG, "onBindViewHolder");

        mCursor.moveToPosition(position);
        holder.list_item_name.setText(mCursor.getString(Utility.COL_NUBA_MENU_NAME));
        holder.list_item_icon.setImageResource(R.mipmap.ic_launcher);
        holder.list_item_price.setText(mCursor.getString(Utility.COL_NUBA_MENU_PRICE));
        holder.list_item_vegetarian_icon.setImageResource(R.mipmap.ic_launcher);
        holder.list_item_vegan_icon.setImageResource(R.mipmap.ic_launcher);
        holder.list_item_gluten_icon.setImageResource(R.mipmap.ic_launcher);


        holder.listItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(LOG_TAG, "Click ");
//                Intent intent = new Intent(v.getContext(), DetailActivity.class).putExtra("line", myList.get(holder.getAdapterPosition()));
//                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void setResult(List<String> updaedList) {
        myList = updaedList;
        notifyDataSetChanged();
    }
}
