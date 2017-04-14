package ca.nuba.nubamenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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
/*        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                //TODO: send back the number of tab so we can return to the tab we left from
                NavUtils.navigateUpTo(this, NavUtils.getParentActivityIntent(this)
                        .putExtra("EXTRA_LOCATION", mLocation)
                        .putExtra("EXTRA_TYPE", mType)
                        .putExtra("EXTRA_PAGE", mPage));


                return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
}
