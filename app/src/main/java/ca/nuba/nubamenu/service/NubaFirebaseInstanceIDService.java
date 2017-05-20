package ca.nuba.nubamenu;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Borys on 2017-04-14.
 */

public class NubaFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public static final String LOG_TAG = NubaFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        //super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.v(LOG_TAG, "Refreshed token: "+refreshedToken);

        FirebaseMessaging.getInstance().subscribeToTopic("fcm");

        //sendRegistrationToServer(refreshedToken);
    }


    private void sendRegistrationToServer(String token){

    }
}
