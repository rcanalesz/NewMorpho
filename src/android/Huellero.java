package cl.entel.plugins.huellero;

// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Log;
import android.app.Activity;
import android.os.Bundle;

import com.zy.lib.morpho.ui.BioCapture;
import com.zy.lib.morpho.ui.IBioCapture;
import com.zy.lib.morpho.ui.ZyRequest;
import com.zy.lib.morpho.ui.ZyResponse;

import android.content.Context;
import android.content.Intent;

import java.io.ByteArrayOutputStream;
import android.util.Base64;

public class Huellero extends CordovaPlugin {

    private static final String TAG = "ZY_Activity";
    
    private byte[] byteArray;

    private CallbackContext callbackContext = null;

    private booleanResult = false;
    
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext newCallbackContext) {
        Log.i("HUELLERO", "execute");

        Context context = cordova.getActivity().getApplicationContext();
        if ("capturar".equals(action)) {


            callbackContext = newCallbackContext;
            cordova.setActivityResultCallback (this);

            Intent intent = new Intent(context, NewActivity.class);
            this.cordova.getActivity().startActivityForResult(intent, 1);

            return booleanResult;
        }
        callbackContext.error("No existe metodo: " + action);
        Log.i("HUELLERO", "error");
        return false;
    }



    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("HUELLERO", "Activity Result");

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                Log.i("HUELLERO", "Activity Result OK");

                Bitmap resultBm = data.getParcelableExtra("result");

                Log.i("HUELLERO", "converting to b64");
               
                //convert bitmap
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
                resultBm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream .toByteArray();   
                    
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                if(encoded != null){
                    Log.i("HUELLERO", "got b64");
                }
                Log.i("HUELLERO", encoded);

                booleanResult = true;

                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, encoded);
                pluginResult.setKeepCallback(true);
                callbackContext.sendPluginResult(pluginResult);

            }
            if (resultCode == Activity.RESULT_CANCELED) {

                Log.i("HUELLERO", "Activity Result FAIL");

                String error =data.getStringExtra("error");

                Log.i("HUELLERO", error);

                booleanResult = false;

                PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, error);
                callbackContext.sendPluginResult(pluginResult);
            }
        }
    }//onActivityResult

}