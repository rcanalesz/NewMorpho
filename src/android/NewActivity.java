package cl.entel.plugins.huellero;


import android.util.Log;

import android.app.Activity;
import android.os.Bundle;

public class NewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String package_name = getApplication().getPackageName();
        
        Log.i("NEW_ACTIVITY",package_name);
        setContentView(getApplication().getResources().getIdentifier("activity_new", "layout", package_name));
    }
}

