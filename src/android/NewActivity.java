package cl.entel.plugins.huellero;


import org.apache.cordova.CallbackContext;

import android.util.Log;

import android.app.Activity;
import android.os.Bundle;



import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.graphics.Bitmap;
import android.util.Log;

import com.zy.lib.morpho.ui.BioCapture;
import com.zy.lib.morpho.ui.IBioCapture;
import com.zy.lib.morpho.ui.ZyRequest;
import com.zy.lib.morpho.ui.ZyResponse;

import android.content.Context;
import android.content.Intent;

import java.io.ByteArrayOutputStream;
import android.util.Base64;

public class NewActivity extends Activity {

    private byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String package_name = getApplication().getPackageName();

        //Bundle bundle = getIntent().getExtras();
        //CallbackContext callbackContext;

        /*if(bundle.getString("callbackContext")!= null)
        {
            callbackContext = bundle.getString("callbackContext");
            Log.i("NEW_ACTIVITY","Got callback context");
        }*/

        Log.i("NEW_ACTIVITY",package_name);
        setContentView(getApplication().getResources().getIdentifier("activity_new", "layout", package_name));

        capturar();

    }


    private void capturar() {
        Log.i("HUELLERO", "Starting capture");
        try {
            IBioCapture iBioCapture = new BioCapture(this, new IBioCapture.ICallback() {
                        
                @Override
                public void onSuccess(ZyResponse zyResponse) {
                    Log.i("HUELLERO", "success");
                    Bitmap imgFP;

                    imgFP = zyResponse.bm;

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
                    imgFP.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream .toByteArray();   
                    
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);


                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",encoded);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }

                @Override
                public void onComplete() {
                    Log.i("HUELLERO", "complete");
                    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK,"{\"bitmap\":\""+"on complete"+"\"}}");
                    pluginResult.setKeepCallback(true);
                    //callbackContext.sendPluginResult(pluginResult);
                }

                @Override
                public void onStart() {
                    Log.i("HUELLERO", "start");
                    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK,"{\"bitmap\":\""+"onstart"+"\"}}");
                    pluginResult.setKeepCallback(true);
                    //callbackContext.sendPluginResult(pluginResult);
                }
    
                @Override
                public void onError(ZyResponse obj) {
                
                    Log.i("HUELLERO", "error");
                    Log.i("HUELLERO", obj.deError);

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("error",obj.deError);
                    setResult(Activity.RESULT_CANCELED,returnIntent);
                    finish();

                    
                }

            });

            ZyRequest zyRequest = new ZyRequest();
            iBioCapture.capturar(zyRequest);


            //return true;
        } catch (Exception e) {
            Log.i("HUELLERO", "catch");
            //callbackContext.error("Error ejecutando Action: " + e);
            //return false;
        }
        
    }
}
























