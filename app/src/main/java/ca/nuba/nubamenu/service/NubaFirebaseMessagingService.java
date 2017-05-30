package ca.nuba.nubamenu.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ca.nuba.nubamenu.MainActivity;
import ca.nuba.nubamenu.R;
import ca.nuba.nubamenu.Utility;
import ca.nuba.nubamenu.data.NubaContract.NubaMenuEntry;

/**
 * Created by Borys on 2017-04-15.
 */

public class NubaFirebaseMessagingService extends FirebaseMessagingService {
    public static final String LOG_TAG = NubaFirebaseMessagingService.class.getSimpleName();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log.v(LOG_TAG, "From: "+remoteMessage.getFrom());


        if (remoteMessage.getData().size() > 0){
            //Log.v(LOG_TAG, "Msg data payload: "+remoteMessage.getData());

            if (remoteMessage.getData().get("title").equals("insert") || remoteMessage.getData().get("title").equals("update")){

                ContentValues contentValues = new ContentValues();

                contentValues.put(NubaMenuEntry.COLUMN_MENU_TYPE, remoteMessage.getData().get("menu_type"));
                contentValues.put(NubaMenuEntry.COLUMN_NAME, remoteMessage.getData().get("name"));
                contentValues.put(NubaMenuEntry.COLUMN_PRICE, remoteMessage.getData().get("price"));
                contentValues.put(NubaMenuEntry.COLUMN_VEGETARIAN, remoteMessage.getData().get("vegetarian"));
                contentValues.put(NubaMenuEntry.COLUMN_VEGAN, remoteMessage.getData().get("vegan"));
                contentValues.put(NubaMenuEntry.COLUMN_GLUTEN_FREE, remoteMessage.getData().get("gluten_free"));
                contentValues.put(NubaMenuEntry.COLUMN_DESCRIPTION, remoteMessage.getData().get("description"));
                contentValues.put(NubaMenuEntry.COLUMN_PIC_PATH, Utility.imageNameCutter(remoteMessage.getData().get("pic_path")));
                contentValues.put(NubaMenuEntry.COLUMN_ICON_PATH, Utility.imageNameCutter(remoteMessage.getData().get("icon_path")));
                contentValues.put(NubaMenuEntry.COLUMN_GLUTEN_FREE, remoteMessage.getData().get("last_update"));
                contentValues.put(NubaMenuEntry.COLUMN_WEB_ID, remoteMessage.getData().get("id"));
                contentValues.put(NubaMenuEntry.COLUMN_LOCATION, remoteMessage.getData().get("location"));

                if (remoteMessage.getData().get("title").equals("insert")) {
                    Log.v(LOG_TAG, "Inserting:  "+remoteMessage.getData());
                    this.getContentResolver().insert(
                            NubaMenuEntry.CONTENT_URI,
                            contentValues
                    );
                } else {
                    Log.v(LOG_TAG, "Updating:  "+remoteMessage.getData());
                    int rowsUpdated = this.getContentResolver().update(
                            NubaMenuEntry.CONTENT_URI,
                            contentValues,
                            Utility.sNubaMenuUpdateWithWebID,
                            new String[]{remoteMessage.getData().get("id")}
                    );
                    Log.v(LOG_TAG, "rowsUpdated - " + rowsUpdated);
                }

            } else {
                Log.v(LOG_TAG, "Default msg: "+remoteMessage.getData().get("title"));

            }

        }

        if (remoteMessage.getNotification() != null){
            Log.v(LOG_TAG, "Msg notification body: "+ remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());

        }



    }

    private void scheduleJob() {
/**        // [START dispatch_job]

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(NubaJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]*/
        Log.v(LOG_TAG, "--Message received");
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(LOG_TAG, "++Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}
