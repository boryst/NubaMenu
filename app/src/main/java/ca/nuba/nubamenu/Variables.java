package ca.nuba.nubamenu;

import android.app.Application;

/**
 * Created by Borys on 15-10-08.
 */
public class Variables extends Application {


        private String mFlag;

        public String getmFlag() {
            return mFlag;
        }

        public void setmFlag(String someVariable) {
            this.mFlag = someVariable;
        }

}
