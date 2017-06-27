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
 * Adapter for reviews in DetailFragment
 */

public class ReviewsRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>{
    private Context mContext;
    private List<Review> mList;
    private String mUserName;
    private String mUserId;
    private int lengthFilterSize = 100;
    private View itemView;
    private static final int ITEM_TYPE_NORMAL = 1;
    private static final int ITEM_TYPE_OWN = 2;


    public class ViewHolderNormal extends RecyclerView.ViewHolder {
        public TextView author, text;
        public RatingBar ratingBar;

        public ViewHolderNormal (View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.tv_list_item_review_author);
            text     = (TextView) view.findViewById(R.id.tv_list_item_review_text);
            ratingBar = (RatingBar) view.findViewById(R.id.rb_list_item_review_rating);
        }
    }

    public class ViewHolderOwnReview extends RecyclerView.ViewHolder {
        public TextView author, text;
        public RatingBar ratingBar;

        public ViewHolderOwnReview(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.tv_list_item_review_author);
            text     = (TextView) view.findViewById(R.id.tv_list_item_review_text);
            ratingBar = (RatingBar) view.findViewById(R.id.rb_list_item_review_rating);

        }
    }

    public ReviewsRecyclerAdapter(Context context, List<Review> list, String userId){
        this.mContext = context;
        this.mList = list;
        this.mUserId = userId;

    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Review review = mList.get(position);
        switch (holder.getItemViewType()){
            case ITEM_TYPE_NORMAL:{
                final ViewHolderNormal viewHolderNormal = (ViewHolderNormal) holder;

                viewHolderNormal.text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(lengthFilterSize)});
                viewHolderNormal.author.setText(review.getAuthor());
                viewHolderNormal.text.setText(review.getReviewText());
                viewHolderNormal.ratingBar.setRating(review.getRating());

                viewHolderNormal.text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (lengthFilterSize < review.getReviewText().length() && lengthFilterSize == 100){
                            lengthFilterSize = review.getReviewText().length();
                            Timber.v("FilterSize - "+String.valueOf(lengthFilterSize));

                        } else if (lengthFilterSize != 100){
                            lengthFilterSize = 100;
                            Timber.v("FilterSize - "+String.valueOf(lengthFilterSize));
                        }
                        viewHolderNormal.text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(lengthFilterSize)});
                        viewHolderNormal.text.setText(review.getReviewText());
                        Timber.v("FilterSize - "+String.valueOf(lengthFilterSize));

                    }
                });
                break;
            }

            case ITEM_TYPE_OWN:{
                final ViewHolderOwnReview viewHolderOwnReview = (ViewHolderOwnReview) holder;

                viewHolderOwnReview.text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(lengthFilterSize)});
                viewHolderOwnReview.author.setText(review.getAuthor());
                viewHolderOwnReview.text.setText(review.getReviewText());
                viewHolderOwnReview.ratingBar.setRating(review.getRating());

                viewHolderOwnReview.text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (lengthFilterSize < review.getReviewText().length() && lengthFilterSize == 100){
                            lengthFilterSize = review.getReviewText().length();
                        } else if (lengthFilterSize != 100){
                            lengthFilterSize = 100;
                        }
                        viewHolderOwnReview.text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(lengthFilterSize)});
                        viewHolderOwnReview.text.setText(review.getReviewText());
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
                        .inflate(R.layout.list_item_review, parent, false);
                return new ViewHolderNormal(itemView);

            }
            case ITEM_TYPE_OWN: {
//                TODO: Uncomment if need unique view
//                itemView = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.list_item_my_review, parent, false);
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_review, parent, false);
                return new ViewHolderOwnReview(itemView);
            }

            default:{
                itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.list_item_review, parent, false);
                return new ViewHolderNormal(itemView);

            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getUserId().equals(mUserId)){
            return ITEM_TYPE_OWN;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    public void setUserId(String userId){
        this.mUserId = userId;
    }
}
