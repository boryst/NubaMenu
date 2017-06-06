package ca.nuba.nubamenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Borys on 2017-06-04.
 */

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.ViewHolder>{
    private Context mContext;
    private List<Comment> mList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView author, text;
        public RatingBar ratingBar;

        public ViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.comment_author);
            text     = (TextView) view.findViewById(R.id.comment_text);
            ratingBar = (RatingBar) view.findViewById(R.id.comments_rating_bar);
        }
    }

    public CommentsRecyclerAdapter(Context context, List<Comment> list){
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public CommentsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_comment, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;    }

    @Override
    public void onBindViewHolder(CommentsRecyclerAdapter.ViewHolder holder, int position) {
        Comment comment = mList.get(position);
        holder.author.setText(comment.getAuthor());
        holder.text.setText(comment.getCommentText());
        holder.ratingBar.setRating(comment.getRating());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
