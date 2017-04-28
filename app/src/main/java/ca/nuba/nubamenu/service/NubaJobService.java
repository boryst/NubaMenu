package ca.nuba.nubamenu.service;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Borys on 2017-04-15.
 */

public class NubaJobService extends JobService {
    public static final String LOG_TAG = NubaJobService.class.getSimpleName();
    @Override
    public boolean onStartJob(JobParameters job) {
        Log.v(LOG_TAG, "Performing long running task in scheduled job");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
