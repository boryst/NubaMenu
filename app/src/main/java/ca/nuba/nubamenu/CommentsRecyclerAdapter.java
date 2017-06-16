package ca.nuba.nubamenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Borys on 2017-06-04.
 */

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>{
    private Context mContext;
    private List<Comment> mList;
    private String mUserName;
    private int lengthFilterSize = 100;
    private View itemView;
    private static final int ITEM_TYPE_NORMAL = 1;
    private static final int ITEM_TYPE_OWN = 2;


    public class ViewHolderNormal extends RecyclerView.ViewHolder {
        public TextView author, text;
        public RatingBar ratingBar;

        public ViewHolderNormal (View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.comment_author);
            text     = (TextView) view.findViewById(R.id.comment_text);
            ratingBar = (RatingBar) view.findViewById(R.id.comments_rating_bar);
        }
    }

    public class ViewHolderOwnReview extends RecyclerView.ViewHolder {
        public TextView author, text;
        public RatingBar ratingBar;

        public ViewHolderOwnReview(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.comment_author);
            text     = (TextView) view.findViewById(R.id.comment_text);
            ratingBar = (RatingBar) view.findViewById(R.id.comments_rating_bar);

        }
    }

    public CommentsRecyclerAdapter(Context context, List<Comment> list, String userName){
        this.mContext = context;
        this.mList = list;
        this.mUserName = userName;
    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Comment comment = mList.get(position);
        switch (holder.getItemViewType()){
            case ITEM_TYPE_NORMAL:{
                final ViewHolderNormal viewHolderNormal = (ViewHolderNormal) holder;

                viewHolderNormal.text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(lengthFilterSize)});
                viewHolderNormal.author.setText(comment.getAuthor());
                viewHolderNormal.text.setText(comment.getCommentText());
                viewHolderNormal.ratingBar.setRating(comment.getRating());

                viewHolderNormal.text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (lengthFilterSize < comment.getCommentText().length() && lengthFilterSize == 100){
                            lengthFilterSize = comment.getCommentText().length();
                            Timber.v("FilterSize - "+String.valueOf(lengthFilterSize));

                        } else if (lengthFilterSize != 100){
                            lengthFilterSize = 100;
                            Timber.v("FilterSize - "+String.valueOf(lengthFilterSize));
                        }
                        viewHolderNormal.text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(lengthFilterSize)});
                        viewHolderNormal.text.setText(comment.getCommentText());
                        Timber.v("FilterSize - "+String.valueOf(lengthFilterSize));

                    }
                });
                break;
            }

            case ITEM_TYPE_OWN:{
                final ViewHolderOwnReview viewHolderOwnReview = (ViewHolderOwnReview) holder;

                viewHolderOwnReview.text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(lengthFilterSize)});
                viewHolderOwnReview.author.setText(comment.getAuthor());
                viewHolderOwnReview.text.setText(comment.getCommentText());
                viewHolderOwnReview.ratingBar.setRating(comment.getRating());

                viewHolderOwnReview.text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (lengthFilterSize < comment.getCommentText().length() && lengthFilterSize == 100){
                            lengthFilterSize = comment.getCommentText().length();
                        } else if (lengthFilterSize != 100){
                            lengthFilterSize = 100;
                        }
                        viewHolderOwnReview.text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(lengthFilterSize)});
                        viewHolderOwnReview.text.setText(comment.getCommentText());
                    }
                });
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_NORMAL: {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_comment, parent, false);
                return new ViewHolderNormal(itemView);

            }
            case ITEM_TYPE_OWN: {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_comment_my, parent, false);
                return new ViewHolderOwnReview(itemView);
            }

            default:{
                itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.list_item_comment, parent, false);
                return new ViewHolderNormal(itemView);

            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getAuthor().equals(mUserName)){
            return ITEM_TYPE_OWN;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    public void setUserName(String username){
        this.mUserName = username;
    }
}
