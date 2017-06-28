package ca.nuba.nubamenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import static ca.nuba.nubamenu.Utility.ITEM_ID_EXTRA;
import static ca.nuba.nubamenu.Utility.ITEM_WEB_ID_EXTRA;
import static ca.nuba.nubamenu.Utility.NUBA_PREFS;
import static ca.nuba.nubamenu.Utility.WEB_IMAGE_STORAGE;

/**
 * Menu item details
 */

//TODO: refactor names
public class DetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String ANONYMOUS = "anonymous";
    private static final int RC_SIGN_IN = 1;
    private static final int DETAIL_LOADER = 0;

    //Menu details variables
//    private Boolean v, ve, gf;
//    private Double price;
//    private String name, desc, picturePath;

    public static int mWebId;

//    private CursorLoader cursorLoader;
    private String mUsername, mUserId, ownReviewKey, ownReviewText;
    private ReviewsRecyclerAdapter mReviewsRecyclerAdapter;
    private List<Review> mReviewsList;
    private List<String> mReviewsKey;
//    private AlertDialog.Builder alert, editReviewAlertBuilder, deleteConfirmation;
    private float mRating, ownReviewRating;
//    private long numberOfComments;

    //Views
    //TODO: shorten views names
    private ImageView ivPicture, ivV, ivVe, ivGf;
    private TextView nameTextView, priceTextView, descTextView, ratingTextView, tvOwnReviewAuthor, tvOwnReviewText;
    private Button buttonWriteReview, btnOwnEditReview, btnOwnDeleteReview, buttonSignOut;
    private SignInButton buttonSignIn;
    private RatingBar ratingBar, rbOwnReviewRaring;
//    private RecyclerView mRecyclerView;

    //Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReviewsDatabaseReference;
    private DatabaseReference mMenuItemAvgRatingReference;
    private ChildEventListener mChildEventListener;
    private ValueEventListener mValueAvgRatingListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth.AuthStateListener mAuthStateListenerForUserName;
    private RelativeLayout rlOwnReview;

    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUsername = ANONYMOUS;
        mUserId = ANONYMOUS;

        //Initialize firabase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mWebId = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).getInt(ITEM_WEB_ID_EXTRA, 0);
        mReviewsDatabaseReference = mFirebaseDatabase.getReference("nubawebids").child(String.valueOf(mWebId)).child("reviews");
        mMenuItemAvgRatingReference = mFirebaseDatabase.getReference("nubawebids").child(String.valueOf(mWebId)).child("avgRating");

        initializeAuthListener();

        initializeAuthListenerForUserId();
        attachAuthListenerForUserId();

        mReviewsList = new ArrayList<>();
        mReviewsKey = new ArrayList<>();
        mReviewsRecyclerAdapter = new ReviewsRecyclerAdapter(getActivity(), mReviewsList, mUserId);

        initializeDatabaseReadListener();
        attachDatabaseReadListener();

        initializeAvgRatingReadListener();
        attachAvgRatingReadListener();


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
        ivPicture = (ImageView) rootView.findViewById(R.id.imgViewDetailImage);
        ivV = (ImageView) rootView.findViewById(R.id.imgViewDetailVegetarianIcon);
        ivVe = (ImageView) rootView.findViewById(R.id.imgViewDetailVeganIcon);
        ivGf = (ImageView) rootView.findViewById(R.id.imgViewDetailGlutenIcon);

        nameTextView = (TextView) rootView.findViewById(R.id.textViewDetailName);
        priceTextView = (TextView) rootView.findViewById(R.id.textViewDetailPrice);
        descTextView = (TextView) rootView.findViewById(R.id.textViewDetailDesc);
        ratingTextView = (TextView) rootView.findViewById(R.id.detail_rating_bar_textview);

        buttonSignIn = (SignInButton) rootView.findViewById(R.id.btn_sign_in);
        buttonSignOut = (Button) rootView.findViewById(R.id.btn_sign_out);
        buttonWriteReview = (Button) rootView.findViewById(R.id.btn_fragment_detail_write_review);

        //My review views
        rlOwnReview = (RelativeLayout) rootView.findViewById(R.id.rl_own_review);
        tvOwnReviewAuthor = (TextView) rootView.findViewById(R.id.tv_own_author);
        tvOwnReviewText = (TextView) rootView.findViewById(R.id.tv_own_review);
        rbOwnReviewRaring = (RatingBar) rootView.findViewById(R.id.rb_own_rating);
        btnOwnEditReview = (Button) rootView.findViewById(R.id.btn_edit_own_review);
        btnOwnDeleteReview = (Button) rootView.findViewById(R.id.btn_delete_own_review);


        ratingBar = (RatingBar) rootView.findViewById(R.id.detail_rating_bar);




        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_fragment_detail_reviews);

        mRecyclerView.setAdapter(mReviewsRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        buttonWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuthStateListener != null){
                    writeReview();
                }
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(getActivity());
                onSignOutCleanUp();
            }
        });

        btnOwnEditReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ownReviewKey != null && ownReviewText != null) {
                    editReview(ownReviewText, ownReviewRating, ownReviewKey);
                }
            }
        });

        btnOwnDeleteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ownReviewKey != null){
                    deleteReview(ownReviewKey);
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
        if (cursor != null){
            cursor.moveToFirst();

            String picturePath = cursor.getString(Utility.COL_NUBA_MENU_PIC_PATH);
            String name = cursor.getString(Utility.COL_NUBA_MENU_NAME);
            Double price = cursor.getDouble(Utility.COL_NUBA_MENU_PRICE);
            boolean v = Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGETARIAN));
            boolean ve = Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGAN));
            boolean gf = Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_GLUTEN_FREE));
            String desc = cursor.getString(Utility.COL_NUBA_MENU_DESCRIPTION);

/* Assign data to views*/
            File img = new File(getActivity().getFilesDir() + "/" + picturePath);
            if (!img.exists()){
                Utility.imageDownload(getActivity(), WEB_IMAGE_STORAGE + picturePath, picturePath);
                Picasso.with(getActivity()).load(WEB_IMAGE_STORAGE + picturePath).placeholder(R.drawable.progress_animation).into(ivPicture);
            } else {
                Picasso.with(getActivity()).load(img).into(ivPicture);
            }

            nameTextView.setText(name);
            String formatedPrice = "$" +String.format(Locale.CANADA, "%.2f", price);
            priceTextView.setText(formatedPrice);


            if (v){
                Picasso.with(getActivity()).load(R.drawable.v).into(ivV);
            } else{
                ivV.setVisibility(View.GONE);
            }

            if (ve){
                Picasso.with(getActivity()).load(R.drawable.ve).into(ivVe);
            } else{
                ivVe.setVisibility(View.GONE);
            }

            if (gf){
                Picasso.with(getActivity()).load(R.drawable.gf).into(ivGf);
            } else{
                ivGf.setVisibility(View.GONE);
            }

            descTextView.setText(desc);
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
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final View container = getActivity().getLayoutInflater().inflate(R.layout.write_review, null);
        alert.setView(container);

        final RatingBar reviewRatingBar = (RatingBar) container.findViewById(R.id.rb_write_review_rating);
        final EditText reviewEditText = (EditText) container.findViewById(R.id.et_write_review_text);


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String reviewText = reviewEditText.getText().toString();
                float reviewRating = reviewRatingBar.getRating();
                final Review review = new Review(mUsername, reviewText, reviewRating, mUserId);
                buttonWriteReview.setVisibility(View.GONE);
                mReviewsDatabaseReference.push().setValue(review);
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    public void editReview(String reviewText, float reviewRating, String reviewId) {
        Timber.v("EditReview");
        AlertDialog.Builder editReviewAlertBuilder = new AlertDialog.Builder(getActivity());
        final View container = getActivity().getLayoutInflater().inflate(R.layout.write_review, null);
        editReviewAlertBuilder.setView(container);

        final RatingBar reviewRatingBar = (RatingBar) container.findViewById(R.id.rb_write_review_rating);
        final EditText reviewEditText = (EditText) container.findViewById(R.id.et_write_review_text);
        reviewRatingBar.setRating(reviewRating);
        reviewEditText.setText(reviewText);

        final DatabaseReference reviewReference = mFirebaseDatabase.getReference("nubawebids").child(String.valueOf(mWebId)).child("reviews").child(reviewId);

        editReviewAlertBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String reviewText = reviewEditText.getText().toString();
                float reviewRating = reviewRatingBar.getRating();


                Map<String, Object> map = new HashMap<>();
                map.put("author",mUsername);
                map.put("reviewText", reviewText);
                map.put("rating", reviewRating);
                map.put("userId", mUserId);
                reviewReference.updateChildren(map);

                ownReviewText = reviewText;
                ownReviewRating = reviewRating;
                tvOwnReviewText.setText(reviewText);
                rbOwnReviewRaring.setRating(reviewRating);

                dialog.dismiss();

            }
        });

        editReviewAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();

            }
        });

        editReviewAlertBuilder.show();
    }

    public void deleteReview(String reviewId) {
        AlertDialog.Builder deleteConfirmation = new AlertDialog.Builder(getActivity());
        final View container = getActivity().getLayoutInflater().inflate(R.layout.alert_delete_review, null);
        deleteConfirmation.setView(container);
        final DatabaseReference reviewReference = mFirebaseDatabase.getReference("nubawebids").child(String.valueOf(mWebId)).child("reviews").child(reviewId);


        deleteConfirmation.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                rlOwnReview.setVisibility(View.GONE);
                reviewReference.removeValue();
                buttonWriteReview.setVisibility(View.VISIBLE);
                dialog.dismiss();

            }
        });

        deleteConfirmation.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();

            }
        });

        deleteConfirmation.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.v("onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.v("onResume");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){

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
                Timber.v("isThere - "+isThere);

                if (isThere){
                    //If there is a review left before, let it to be edited
                    rlOwnReview.setVisibility(View.VISIBLE);
//                    tvOwnReviewAuthor.setVisibility(View.VISIBLE);
//                    tvOwnReviewText.setVisibility(View.VISIBLE);
//                    rbOwnReviewRaring.setVisibility(View.VISIBLE);
//                    btnOwnDeleteReview.setVisibility(View.VISIBLE);
//                    btnOwnEditReview.setVisibility(View.VISIBLE);

                    buttonWriteReview.setVisibility(View.GONE);
                    tvOwnReviewText.setText(ownReview.getReviewText());
                    rbOwnReviewRaring.setRating(ownReview.getRating());
                    tvOwnReviewAuthor.setText(ownReview.getAuthor());

                    ownReviewKey = ownKey;
                    ownReviewText = ownReview.getReviewText();
                    ownReviewRating = ownReview.getRating();

                } else {
//                    writeReview();
                    buttonWriteReview.setVisibility(View.VISIBLE);
                }
                buttonSignIn.setVisibility(View.GONE);
                buttonSignOut.setVisibility(View.VISIBLE);
//                buttonWriteReview.setVisibility(View.VISIBLE);



            } else if (resultCode == RESULT_CANCELED){

                detachAuthStateListener();
            }
        }
    }




    private void initializeDatabaseReadListener(){
        if (mChildEventListener == null) {

            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Review review = dataSnapshot.getValue(Review.class);
                    if (review.getUserId().equals(mUserId)){
                        Timber.v("Signed in, has review");

                        rlOwnReview.setVisibility(View.VISIBLE);
//                        tvOwnReviewAuthor.setVisibility(View.VISIBLE);
//                        tvOwnReviewText.setVisibility(View.VISIBLE);
//                        rbOwnReviewRaring.setVisibility(View.VISIBLE);
//                        btnOwnEditReview.setVisibility(View.VISIBLE);
//                        btnOwnDeleteReview.setVisibility(View.VISIBLE);


                        ownReviewKey = dataSnapshot.getKey();
                        ownReviewText = review.getReviewText();
                        ownReviewRating = review.getRating();

                        tvOwnReviewText.setText(review.getReviewText());
                        rbOwnReviewRaring.setRating(review.getRating());
                        tvOwnReviewAuthor.setText(review.getAuthor());

                        buttonWriteReview.setVisibility(View.GONE);
                    } else {

                        mReviewsKey.add(0, dataSnapshot.getKey());
                        mReviewsList.add(0, review);
                        mReviewsRecyclerAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Timber.v("--Child changed");
                    Timber.v("--onChildChanged.key - "+dataSnapshot.getKey());
                    Timber.v("--onChildChanged.s - "+s);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Timber.v("--onChildRemoved");
                    Timber.v("--onChildRemoved.key - "+dataSnapshot.getKey());

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

    private void initializeAvgRatingReadListener(){
        if (mValueAvgRatingListener == null){
            mValueAvgRatingListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        AvgRating avgRating = dataSnapshot.getValue(AvgRating.class);
                        if (avgRating != null) {

                            mRating = avgRating.getCurrentAvgRating();
                            ratingTextView.setText(String.valueOf(avgRating.getNumOfRatings()));
                            ratingBar.setRating(mRating);
                        } else {
                            Timber.v("No data in avgRating");
                            ratingTextView.setTextSize(10);
                            ratingTextView.setText(R.string.detail_activity_no_reviews);

                        }
                    } else {
                        Timber.v("No data in dataSnapshot");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        }
    }


    private void initializeAuthListener() {
        if (mAuthStateListener == null){
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


    private void initializeAuthListenerForUserId(){

        mAuthStateListenerForUserName = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){

                    Timber.v("--User - "+user.getDisplayName());
                    onSignedInInitialize(user.getDisplayName(), user.getUid());
                    mReviewsRecyclerAdapter.setUserId(mUserId);

                    buttonSignIn.setVisibility(View.GONE);
                    buttonSignOut.setVisibility(View.VISIBLE);
//                    buttonWriteReview.setVisibility(View.GONE);

                } else {
                    Timber.v("--User - no user");
                    buttonWriteReview.setVisibility(View.GONE);
                }
            }
        };
    }

    private void attachAuthStateListener(){
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private  void attachAvgRatingReadListener(){
        mMenuItemAvgRatingReference.addValueEventListener(mValueAvgRatingListener);
    }

    private void attachDatabaseReadListener(){
        mReviewsDatabaseReference.addChildEventListener(mChildEventListener);
    }

    private void attachAuthListenerForUserId() {
        mFirebaseAuth.addAuthStateListener(mAuthStateListenerForUserName);
    }



    private void detachAuthStateListener(){
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    private void detachDatabaseReadListener(){
        if (mChildEventListener != null){
            mReviewsDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
            mReviewsList.clear();
            mReviewsKey.clear();
        }
    }

    private void detachAvgRatingReadListener(){
        if (mValueAvgRatingListener != null){
            mMenuItemAvgRatingReference.removeEventListener(mValueAvgRatingListener);
            mValueAvgRatingListener = null;
        }
    }

    private void detachAuthStateListenerForUserId(){
        if (mAuthStateListenerForUserName != null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListenerForUserName);
        }
    }

    private void terminateAuthStateListener(){
        if (mAuthStateListener != null) {
            mAuthStateListener = null;
        }
    }


    private void onSignedInInitialize(String username, String userId){
        mUsername = username;
        mUserId = userId;
    }

    private void onSignOutCleanUp(){
        Review myReview = new Review(mUsername, String.valueOf(tvOwnReviewText.getText()), rbOwnReviewRaring.getRating(), mUserId);

        mUsername = ANONYMOUS;
        mUserId = ANONYMOUS;

        rlOwnReview.setVisibility(View.GONE);
//        tvOwnReviewAuthor.setVisibility(View.GONE);
//        tvOwnReviewText.setVisibility(View.GONE);
//        rbOwnReviewRaring.setVisibility(View.GONE);
//        btnOwnEditReview.setVisibility(View.GONE);
//        btnOwnDeleteReview.setVisibility(View.GONE);

        buttonSignOut.setVisibility(View.GONE);
        buttonSignIn.setVisibility(View.VISIBLE);
        buttonWriteReview.setVisibility(View.GONE);

        if (tvOwnReviewText.getText().length() != 0) {
            mReviewsList.add(0, myReview);
            mReviewsKey.add(0, ownReviewKey);
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

//TODO: 1. Combine mReviewsKey and mReviewsList
//TODO: 2. Replace views.visibility to ReletiveLayout.setVisibility
//TODO: 3. Get rid off auth state listener
