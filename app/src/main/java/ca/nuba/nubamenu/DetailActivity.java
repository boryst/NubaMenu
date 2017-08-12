package ca.nuba.nubamenu;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {
    public static final String LOG_TAG = DetailActivity.class.getSimpleName();

    private String mLocation, mType, mName;
    private int mPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

/*        Intent intent = this.getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            mLocation = extras.getString("EXTRA_LOCATION");
            mType = extras.getString("EXTRA_TYPE");
            mPage = extras.getInt("EXTRA_PAGE");
            mName = extras.getString("name");
            setTitle(mName);


        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:{
                Timber.v("home pressed");
                onBackPressed();
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Timber.v("back pressed");
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            
        }
//        slideOutTransition(this);
    }
}
