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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    public static final String FRIENDLY_MSG_LENGTH_KEY = "friendly_msg_length";
    public static final int RC_SIGN_IN = 1;
    private static final int RC_PHOTO_PICKER = 2;

    Boolean v, ve, gf;
    String name, desc, page, picturePath;
    Double price;
    int  tabPosition;
    float numStars;
    float numStarsStatic, numStarsStatic2;
    int numRatings;
    public static int mWebId;

    ImageView imageView, imageViewV, imageViewVe, imageViewGf;
    TextView nameTextView, priceTextView, descTextView;
    Button leaveCommentButton;

    private RecyclerView mRecyclerView;
    private static final int DETAIL_LOADER = 0;
    private CursorLoader cursorLoader;
    private String mUsername;
    private CommentsRecyclerAdapter mCommentsRecyclerAdapter;
    private List<Comment> mCommentsList;
    private AlertDialog.Builder alert;
    private float avgRating;
    private long numberOfComments;







    //Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCommentsDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUsername = ANONYMOUS;

        //Initialize firabase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        mWebId = getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).getInt(ITEM_WEB_ID_EXTRA, 0);
        //mCommentsDatabaseReference = mFirebaseDatabase.getReference().child(String.valueOf(mWebId));
        mCommentsDatabaseReference = mFirebaseDatabase.getReference("nubawebids").child(String.valueOf(mWebId));
        Timber.v("mCommentsDatabaseReference - "+mCommentsDatabaseReference.getRef());


        //mChatPhotosStorageReference = mFirebaseStorage.getReference().child("");

        mCommentsList = new ArrayList<>();
        mCommentsRecyclerAdapter = new CommentsRecyclerAdapter(getActivity(), mCommentsList);
        attachDatabaseReadListener();


//TODO: Count average rating on server via Firabase Functions
        mCommentsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numberOfComments = dataSnapshot.getChildrenCount();

                Timber.v("numberOfComments - "+numberOfComments+", sum of ratings - "+avgRating+"\n AvgRating - "+avgRating/numberOfComments);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){

                    writeComment();
                    onSignedInInitialize(user.getDisplayName());
                    Timber.v("You're signed in");
                } else {
                    onSigneOutCleanUp();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(true)
                                    .setProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.imgViewDetailImage);
        nameTextView = (TextView) rootView.findViewById(R.id.textViewDetailName);
        priceTextView = (TextView) rootView.findViewById(R.id.textViewDetailPrice);
        imageViewV = (ImageView) rootView.findViewById(R.id.imgViewDetailVegetarianIcon);
        imageViewVe = (ImageView) rootView.findViewById(R.id.imgViewDetailVeganIcon);
        imageViewGf = (ImageView) rootView.findViewById(R.id.imgViewDetailGlutenIcon);
        descTextView = (TextView) rootView.findViewById(R.id.textViewDetailDesc);
        leaveCommentButton = (Button) rootView.findViewById(R.id.btn_comment);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.comments_recyclerview);

        mRecyclerView.setAdapter(mCommentsRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        leaveCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFirebaseAuth.addAuthStateListener(mAuthStateListener);

//                writeComment();
            }
        });
        return rootView;
    }

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }




    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        cursorLoader = new CursorLoader(
                getActivity(),
                NubaContract.NubaMenuEntry.buildNubaMenuUriWithID(getActivity().getSharedPreferences(NUBA_PREFS, MODE_PRIVATE).getInt(ITEM_ID_EXTRA, 0)),
                Utility.NUBA_MENU_PROJECTION,
                null,
                null,
                null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null){
            cursor.moveToFirst();
//            mCommentsDatabaseReference = mFirebaseDatabase.getReference().child(String.valueOf(cursor.getInt(Utility.COL_NUBA_WEB_ID)));

            picturePath = cursor.getString(Utility.COL_NUBA_MENU_PIC_PATH);
            name = cursor.getString(Utility.COL_NUBA_MENU_NAME);
            price = cursor.getDouble(Utility.COL_NUBA_MENU_PRICE);
            v = Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGETARIAN));
            ve = Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_VEGAN));
            gf = Boolean.parseBoolean(cursor.getString(Utility.COL_NUBA_MENU_GLUTEN_FREE));
            desc = cursor.getString(Utility.COL_NUBA_MENU_DESCRIPTION);

/* Assign data to views*/
            File img = new File(getActivity().getFilesDir() + "/" + picturePath);
            if (!img.exists()){
                Log.v(LOG_TAG, "Image "+picturePath+" does not exist");
                Utility.imageDownload(getActivity(), WEB_IMAGE_STORAGE + picturePath, picturePath);
                Picasso.with(getActivity()).load(WEB_IMAGE_STORAGE + picturePath).placeholder(R.drawable.progress_animation).into(imageView);
            } else {
                Picasso.with(getActivity()).load(img).into(imageView);
            }

            nameTextView.setText(name);
            //priceTextView.setText(String.valueOf(price));
            priceTextView.setText("$" +String.format(Locale.CANADA, "%.2f", price));


            if (v){
                Picasso.with(getActivity()).load(R.drawable.v).into(imageViewV);
            } else{
                imageViewV.setVisibility(View.GONE);
            }

            if (ve){
                Picasso.with(getActivity()).load(R.drawable.ve).into(imageViewVe);
            } else{
                imageViewVe.setVisibility(View.GONE);
            }

            if (gf){
                Picasso.with(getActivity()).load(R.drawable.gf).into(imageViewGf);
            } else{
                imageViewGf.setVisibility(View.GONE);
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

    public void writeComment() {
        alert = new AlertDialog.Builder(getActivity());
        final View container = getActivity().getLayoutInflater().inflate(R.layout.leave_comment, null);
        alert.setView(container);

        final RatingBar commentRatingBar = (RatingBar) container.findViewById(R.id.rb_comment);
        final EditText commentEditText = (EditText) container.findViewById(R.id.et_comment);


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String commentText = commentEditText.getText().toString();
                float commentRating = commentRatingBar.getRating();

                final Comment comment = new Comment(mUsername, commentText, commentRating);
                Timber.v("commentText - "+commentText);
                Timber.v("commentRating - "+commentRating);
                mCommentsDatabaseReference.push().setValue(comment);

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

    @Override
    public void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                writeComment();
            } else if (resultCode == RESULT_CANCELED){
                getActivity().finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out: {
                AuthUI.getInstance().signOut(getActivity());
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail_fragment, menu);
//        super.onCreateOptionsMenu(menu, inflater);
    }

    private void onSignedInInitialize(String username){
        mUsername = username;
    }

    private void onSigneOutCleanUp(){
        mUsername = ANONYMOUS;
    }

    private void attachDatabaseReadListener(){
//        Timber.v("Inside attachDatabaseReadListener");
        if (mChildEventListener == null) {

            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Comment comment = dataSnapshot.getValue(Comment.class);
//                    Timber.v("key --"+dataSnapshot.getKey());
                    mCommentsList.add(0,comment);
                    avgRating += mCommentsList.get(0).getRating();
                    mCommentsRecyclerAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mCommentsDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener(){
        if (mChildEventListener != null){
            mCommentsDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }
    //TODO: if user left a comment - hide button "leavea comment" and add "edit"
}
