package ca.nuba.nubamenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ca.nuba.nubamenu.data.NubaContract;
import timber.log.Timber;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static ca.nuba.nubamenu.Utility.COL_NUBA_MODIFIFER;
import static ca.nuba.nubamenu.Utility.ITEM_ID_EXTRA;
import static ca.nuba.nubamenu.Utility.ITEM_WEB_ID_EXTRA;
import static ca.nuba.nubamenu.Utility.NUBA_PREFS;
import static ca.nuba.nubamenu.Utility.WEB_IMAGE_STORAGE;

/**
 * Menu item details
 */

//TODO: refactor names
public class DetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String ANONYMOUS = "anonymous";
    private static final int RC_SIGN_IN = 1;
    private static final int DETAIL_LOADER = 0;

    //Menu item id in MySQL database on a server
    private static int mWebId;

    private String mUsername, mUserId, mOwnReviewKey, mOwnReviewText;
    private ReviewsRecyclerAdapter mReviewsRecyclerAdapter;
    private List<Review> mReviewsList;
    private List<String> mReviewsKey;
    private float mRating, mOwnReviewRating;
    private Bundle extras;

    //Views
    private ImageView ivPicture, ivModifier, ivV, ivVe, ivGf;
    private TextView tvName, tvPrice, tvDescription, tvRating, tvOwnReviewAuthor, tvOwnReviewText;
    private Button btnWriteReview, btnOwnEditReview, btnOwnDeleteReview, btnSignOut;
    private SignInButton btnSignIn;
    private RatingBar rbRating, rbOwnReviewRaring;
    private RelativeLayout rlOwnReview;
    private ProgressBar pbProgress;

    //Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReviewsDatabaseReference;
    private DatabaseReference mMenuItemAvgRatingReference;
    private ChildEventListener mChildEventListener;
    private ValueEventListener mValueAvgRatingListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth.AuthStateListener mAuthStateListenerForUserName;

    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().supportPostponeEnterTransition();
        if (getActivity().getIntent() != null){
            extras = getActivity().getIntent().getExtras();
        }


        setHasOptionsMenu(true);
        mUsername = ANONYMOUS;
        mUserId = ANONYMOUS;

        //Initialize firabase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mWebId = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).getInt(ITEM_WEB_ID_EXTRA, 0);
        //Reference to "reviews" child in FB database
        mReviewsDatabaseReference = mFirebaseDatabase.getReference("nubawebids").child(String.valueOf(mWebId)).child("reviews");
        //Reference to "avgRating" child in FB database
        mMenuItemAvgRatingReference = mFirebaseDatabase.getReference("nubawebids").child(String.valueOf(mWebId)).child("avgRating");

        //Listeners initialization and attachment
        //TODO: Do I need this?
        initializeAuthListener();

        initializeAuthListenerForUserId();
        attachAuthListenerForUserId();

        initializeDatabaseReadListener();
        attachDatabaseReadListener();

        initializeAvgRatingReadListener();
        attachAvgRatingReadListener();

        mReviewsList = new ArrayList<>();
        mReviewsKey = new ArrayList<>();
        mReviewsRecyclerAdapter = new ReviewsRecyclerAdapter(getActivity(), mReviewsList, mUserId);

//        mReviewsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                numberOfComments = dataSnapshot.getChildrenCount();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        //Menu item detail views
        ivPicture = (ImageView) rootView.findViewById(R.id.iv_detail_picture);
        ivModifier = (ImageView) rootView.findViewById(R.id.iv_detail_modifier);
        tvName = (TextView) rootView.findViewById(R.id.tv_detail_name);
        ivV = (ImageView) rootView.findViewById(R.id.iv_detail_v_icon);
        ivVe = (ImageView) rootView.findViewById(R.id.iv_detail_ve_icon);
        ivGf = (ImageView) rootView.findViewById(R.id.iv_detail_gf_icon);
        tvPrice = (TextView) rootView.findViewById(R.id.tv_detail_price);
        rbRating = (RatingBar) rootView.findViewById(R.id.rb_detail_rating);
        tvRating = (TextView) rootView.findViewById(R.id.tv_detail_num_ratings);
        tvDescription = (TextView) rootView.findViewById(R.id.tv_detail_description);
        pbProgress = (ProgressBar) rootView.findViewById(R.id.pb_detail_progress);

        btnSignIn = (SignInButton) rootView.findViewById(R.id.btn_detail_sign_in);
        btnSignOut = (Button) rootView.findViewById(R.id.btn_detail_sign_out);
        btnWriteReview = (Button) rootView.findViewById(R.id.btn_detail_write_review);

        //My review views
        rlOwnReview = (RelativeLayout) rootView.findViewById(R.id.rl_detail_own_review);
        tvOwnReviewAuthor = (TextView) rootView.findViewById(R.id.tv_detail_own_author);
        tvOwnReviewText = (TextView) rootView.findViewById(R.id.tv_detail_own_review);
        rbOwnReviewRaring = (RatingBar) rootView.findViewById(R.id.rb_detail_own_rating);
        btnOwnEditReview = (Button) rootView.findViewById(R.id.btn_detail_edit_own_review);
        btnOwnDeleteReview = (Button) rootView.findViewById(R.id.btn_delete_own_review);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_fragment_detail_reviews);

        mRecyclerView.setAdapter(mReviewsRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && extras != null
                ) {
            ivPicture.setTransitionName(extras.getString("transition_name"));
        }

        btnWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuthStateListener != null) {
                    writeReview();
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize sign in flow using AuthUI
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(true)
                                .setProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                .build(),
                        RC_SIGN_IN);

            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sign out using AuthUI
                AuthUI.getInstance().signOut(getActivity());
                onSignOutCleanUp();
            }
        });

        btnOwnEditReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOwnReviewKey != null && mOwnReviewText != null) {
                    editReview(mOwnReviewText, mOwnReviewRating, mOwnReviewKey);
                }
            }
        });

        btnOwnDeleteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOwnReviewKey != null) {
                    deleteReview(mOwnReviewKey);
                }
            }
        });

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                NubaContract.NubaMenuEntry.buildNubaMenuUriWithID(getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).getInt(ITEM_ID_EXTRA, 0)),
                Utility.NUBA_MENU_PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            cursor.moveToFirst();

            String picturePath = cursor.getString(Utility.COL_NUBA_MENU_PIC_PATH);
            String name = cursor.getString(Utility.COL_NUBA_MENU_NAME);
            Double price = cursor.getDouble(Utility.COL_NUBA_MENU_PRICE);
            boolean v = Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGETARIAN));
            boolean ve = Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGAN));
            boolean gf = Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_GLUTEN_FREE));
            String desc = cursor.getString(Utility.COL_NUBA_MENU_DESCRIPTION);
            String modifier = cursor.getString(COL_NUBA_MODIFIFER);
//            Timber.v("modifier - "+cursor.getString(Utility.COL_NUBA_MODIFIFER));
//            Timber.v("start_date - "+cursor.getString(Utility.COL_NUBA_START_DATE));
//            Timber.v("end_date - "+cursor.getString(Utility.COL_NUBA_END_DATE));


            //Assign data to views

            final File img = new File(getActivity().getFilesDir() + "/" + picturePath);
            if (!img.exists()) {
                //Load image from server and download it
                Utility.imageDownload(getActivity(), WEB_IMAGE_STORAGE + picturePath, picturePath);
//                Picasso.with(getActivity())
//                        .load(WEB_IMAGE_STORAGE + picturePath)
//                        .placeholder(R.drawable.progress_animation)
//                        .into(ivPicture, new Callback() {
//                            @Override
//                            public void onSuccess() {
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                    getActivity().supportStartPostponedEnterTransition();
//                                }
//                            }
//
//                            @Override
//                            public void onError() {
//
//                                Timber.v("Fail to load");
////                                getActivity().supportStartPostponedEnterTransition();
//                            }
//                        });
                Timber.v("---Trying to load image");
                Glide.with(getActivity())
                        .load(WEB_IMAGE_STORAGE + picturePath)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                pbProgress.setVisibility(GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                pbProgress.setVisibility(GONE);
                                Timber.v("onResourceReady");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Timber.v("transition");
                                    getActivity().supportStartPostponedEnterTransition();
                                }
                                return false;
                            }
                        })
                        .into(ivPicture);
            } else {

                Picasso.with(getActivity())
                        .load(img)
                        .noFade()
                        .into(ivPicture, new Callback() {
                            @Override
                            public void onSuccess() {
                                getActivity().supportStartPostponedEnterTransition();
                                Timber.v("img - "+String.valueOf(img));
                            }
                            @Override
                            public void onError() {
                                getActivity().supportStartPostponedEnterTransition();
                            }
                        });
            }


//            tvName.setText(name);
            getActivity().setTitle(name);

            if (modifier.equals("new")){
                ivModifier.setVisibility(View.VISIBLE);
                ivModifier.setImageResource(R.drawable.ic_new);
            } else if (modifier.equals("feature")){
                ivModifier.setVisibility(View.VISIBLE);
                ivModifier.setImageResource(R.drawable.ic_feature);
            } else {
                tvName.setVisibility(GONE);
            }


            String formatedPrice = "$" + String.format(Locale.CANADA, "%.2f", price);
            tvPrice.setText(formatedPrice);

            if (v) {
                Picasso.with(getActivity()).load(R.drawable.v).into(ivV);
            } else {
                ivV.setVisibility(GONE);
            }

            if (ve) {
                Picasso.with(getActivity()).load(R.drawable.ve).into(ivVe);
            } else {
                ivVe.setVisibility(GONE);
            }

            if (gf) {
                Picasso.with(getActivity()).load(R.drawable.gf).into(ivGf);
            } else {
                ivGf.setVisibility(GONE);
            }

            tvDescription.setText(desc);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    public void writeReview() {
        AlertDialog.Builder writeReviewAlert = new AlertDialog.Builder(getActivity());
        final View container = getActivity().getLayoutInflater().inflate(R.layout.write_review, null);

        final RatingBar reviewRatingBar = (RatingBar) container.findViewById(R.id.rb_write_review_rating);
        final EditText reviewEditText = (EditText) container.findViewById(R.id.et_write_review_text);

        writeReviewAlert.setView(container);
        writeReviewAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String reviewText = reviewEditText.getText().toString();
                float reviewRating = reviewRatingBar.getRating();
                final Review review = new Review(mUsername, reviewText, reviewRating, mUserId);
                btnWriteReview.setVisibility(GONE);
                mReviewsDatabaseReference.push().setValue(review);
                dialog.dismiss();
            }
        });

        writeReviewAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        writeReviewAlert.show();
    }

    public void editReview(String reviewText, float reviewRating, String reviewId) {
        AlertDialog.Builder editReviewAlert = new AlertDialog.Builder(getActivity());
        final View container = getActivity().getLayoutInflater().inflate(R.layout.write_review, null);

        final RatingBar reviewRatingBar = (RatingBar) container.findViewById(R.id.rb_write_review_rating);
        final EditText reviewEditText = (EditText) container.findViewById(R.id.et_write_review_text);

        final DatabaseReference reviewReference = mFirebaseDatabase.getReference("nubawebids").child(String.valueOf(mWebId)).child("reviews").child(reviewId);

        reviewRatingBar.setRating(reviewRating);
        reviewEditText.setText(reviewText);

        editReviewAlert.setView(container);
        editReviewAlert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String reviewText = reviewEditText.getText().toString();
                float reviewRating = reviewRatingBar.getRating();

                Map<String, Object> map = new HashMap<>();
                map.put("author", mUsername);
                map.put("reviewText", reviewText);
                map.put("rating", reviewRating);
                map.put("userId", mUserId);
                reviewReference.updateChildren(map);

                mOwnReviewText = reviewText;
                mOwnReviewRating = reviewRating;
                tvOwnReviewText.setText(reviewText);
                rbOwnReviewRaring.setRating(reviewRating);

                dialog.dismiss();
            }
        });

        editReviewAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        editReviewAlert.show();
    }

    public void deleteReview(String reviewId) {
        AlertDialog.Builder deleteReviewConfirmationAlert = new AlertDialog.Builder(getActivity());
        final View container = getActivity().getLayoutInflater().inflate(R.layout.alert_delete_review, null);
        final DatabaseReference reviewReference = mFirebaseDatabase.getReference("nubawebids").child(String.valueOf(mWebId)).child("reviews").child(reviewId);

        deleteReviewConfirmationAlert.setView(container);
        deleteReviewConfirmationAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                rlOwnReview.setVisibility(GONE);
                reviewReference.removeValue();
                btnWriteReview.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });

        deleteReviewConfirmationAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        deleteReviewConfirmationAlert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {

                boolean isThere = false;
                Review ownReview = new Review();
                String ownKey = new String();

                for (int i = 0; i < mReviewsList.size(); i++) {
                    if (mReviewsList.get(i).getUserId().equals(mUserId)) {
                        isThere = true;

                        ownReview = mReviewsList.get(i);
                        ownKey = mReviewsKey.get(i);

                        mReviewsKey.remove(i);
                        mReviewsList.remove(i);
                        mReviewsRecyclerAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                if (isThere) {
                    //If there is a review left before, let it to be edited
                    rlOwnReview.setVisibility(View.VISIBLE);

                    btnWriteReview.setVisibility(GONE);
                    tvOwnReviewText.setText(ownReview.getReviewText());
                    rbOwnReviewRaring.setRating(ownReview.getRating());
                    tvOwnReviewAuthor.setText(ownReview.getAuthor());

                    mOwnReviewKey = ownKey;
                    mOwnReviewText = ownReview.getReviewText();
                    mOwnReviewRating = ownReview.getRating();

                } else {
                    btnWriteReview.setVisibility(View.VISIBLE);
                }
                btnSignIn.setVisibility(GONE);
                btnSignOut.setVisibility(View.VISIBLE);

            } else if (resultCode == RESULT_CANCELED) {
                detachAuthStateListener();
            }
        }
    }

    private void initializeDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Review review = dataSnapshot.getValue(Review.class);
                    if (review.getUserId().equals(mUserId)) {
                        rlOwnReview.setVisibility(View.VISIBLE);

                        mOwnReviewKey = dataSnapshot.getKey();
                        mOwnReviewText = review.getReviewText();
                        mOwnReviewRating = review.getRating();

                        tvOwnReviewText.setText(review.getReviewText());
                        rbOwnReviewRaring.setRating(review.getRating());
                        tvOwnReviewAuthor.setText(review.getAuthor());

                        btnWriteReview.setVisibility(GONE);
                    } else {

                        mReviewsKey.add(0, dataSnapshot.getKey());
                        mReviewsList.add(0, review);
                        mReviewsRecyclerAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Timber.v("--Child changed");
                    Timber.v("--onChildChanged.key - " + dataSnapshot.getKey());
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Timber.v("--onChildRemoved");
                    Timber.v("--onChildRemoved.key - " + dataSnapshot.getKey());
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
        }
    }

    private void initializeAvgRatingReadListener() {
        if (mValueAvgRatingListener == null) {
            mValueAvgRatingListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        AvgRating avgRating = dataSnapshot.getValue(AvgRating.class);
                        if (avgRating != null) {

                            mRating = avgRating.getCurrentAvgRating();
                            tvRating.setText(String.valueOf(avgRating.getNumOfRatings()));
                            tvRating.setTextSize(30);
                            rbRating.setRating(mRating);

                        } else {
                            tvRating.setTextSize(10);
                            tvRating.setText(R.string.detail_activity_no_reviews);

                        }
                    } else {
                        Timber.e("No data in dataSnapshot");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        }
    }


    private void initializeAuthListener() {
        if (mAuthStateListener == null) {
            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        onSignedInInitialize(user.getDisplayName(), user.getUid());
                    }
                }
            };
        }
    }


    private void initializeAuthListenerForUserId() {

        mAuthStateListenerForUserName = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    onSignedInInitialize(user.getDisplayName(), user.getUid());
                    mReviewsRecyclerAdapter.setUserId(mUserId);
                    btnSignIn.setVisibility(GONE);
                    btnSignOut.setVisibility(View.VISIBLE);
                } else {
                    btnWriteReview.setVisibility(GONE);
                }
            }
        };
    }

    private void attachAuthStateListener() {
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void attachAvgRatingReadListener() {
        mMenuItemAvgRatingReference.addValueEventListener(mValueAvgRatingListener);
    }

    private void attachDatabaseReadListener() {
        mReviewsDatabaseReference.addChildEventListener(mChildEventListener);
    }

    private void attachAuthListenerForUserId() {
        mFirebaseAuth.addAuthStateListener(mAuthStateListenerForUserName);
    }


    private void detachAuthStateListener() {
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mReviewsDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
            mReviewsList.clear();
            mReviewsKey.clear();
        }
    }

    private void detachAvgRatingReadListener() {
        if (mValueAvgRatingListener != null) {
            mMenuItemAvgRatingReference.removeEventListener(mValueAvgRatingListener);
            mValueAvgRatingListener = null;
        }
    }

    private void detachAuthStateListenerForUserId() {
        if (mAuthStateListenerForUserName != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListenerForUserName);
        }
    }

    private void terminateAuthStateListener() {
        if (mAuthStateListener != null) {
            mAuthStateListener = null;
        }
    }


    private void onSignedInInitialize(String username, String userId) {
        mUsername = username;
        mUserId = userId;
    }

    private void onSignOutCleanUp() {
        Review myReview = new Review(mUsername, String.valueOf(tvOwnReviewText.getText()), rbOwnReviewRaring.getRating(), mUserId);

        mUsername = ANONYMOUS;
        mUserId = ANONYMOUS;

        rlOwnReview.setVisibility(GONE);

        btnSignOut.setVisibility(GONE);
        btnSignIn.setVisibility(View.VISIBLE);
        btnWriteReview.setVisibility(GONE);

        if (tvOwnReviewText.getText().length() != 0) {
            mReviewsList.add(0, myReview);
            mReviewsKey.add(0, mOwnReviewKey);
            mReviewsRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStop() {
        detachAuthStateListener();
        detachAvgRatingReadListener();
        detachDatabaseReadListener();
        detachAuthStateListenerForUserId();
        super.onStop();
    }
}
