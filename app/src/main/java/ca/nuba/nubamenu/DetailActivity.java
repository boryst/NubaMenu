package ca.nuba.nubamenu;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DetailActivity extends AppCompatActivity {

    private String mLocation, mType, mName;
    private int mPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = this.getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            mLocation = extras.getString("EXTRA_LOCATION");
            mType = extras.getString("EXTRA_TYPE");
            mPage = extras.getInt("EXTRA_PAGE");
            mName = extras.getString("name");
            setTitle(mName);


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                //TODO: send back the number of tab so we can return to the tab we left from
                NavUtils.navigateUpTo(this, NavUtils.getParentActivityIntent(this)
                        .putExtra("EXTRA_LOCATION", mLocation)
                        .putExtra("EXTRA_TYPE", mType)
                        .putExtra("EXTRA_PAGE", mPage));
                //Toast.makeText(this, "UpPressed-1", Toast.LENGTH_LONG).show();
                //Toast.makeText(this, mLocation, Toast.LENGTH_LONG).show();


                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
