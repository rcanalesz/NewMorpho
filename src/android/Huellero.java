package cl.entel.plugins.huellero;

// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;

import android.graphics.Bitmap;
import android.util.Log;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;

import java.io.ByteArrayOutputStream;
import android.util.Base64;

public class Huellero extends CordovaPlugin {

    private static final String TAG = "HUELLERO";
    
    private byte[] byteArray;

    private CallbackContext callbackContext = null;
    
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext newCallbackContext) {
        Log.i(TAG, "execute");

        Context context = cordova.getActivity().getApplicationContext();
        if ("capturar".equals(action)) {
            callbackContext = newCallbackContext;
            cordova.setActivityResultCallback (this);

            Intent intent = new Intent(context, NewActivity.class);
            this.cordova.getActivity().startActivityForResult(intent, 1);

            return true;
        }
        callbackContext.error("No existe metodo: " + action);
        Log.i(TAG, "error");
        return false;
    }



    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(TAG, "Activity Result");

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                Log.i(TAG, "Activity Result OK");

                Bitmap resultBm = data.getParcelableExtra("result");

                Log.i(TAG, "converting to B64");               
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
                resultBm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream .toByteArray();   
                    
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                if(encoded != null){
                    Log.i(TAG, "got b64");
                    Log.i(TAG, encoded);
                }

                
                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, encoded);
                
                callbackContext.sendPluginResult(pluginResult);

            }
            if (resultCode == Activity.RESULT_CANCELED) {

                Log.i(TAG, "Activity Result FAILED");

                String error =data.getStringExtra("error");

                Log.i(TAG, error);

                PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, error);
                callbackContext.sendPluginResult(pluginResult);
            }
        }
    }//onActivityResult

}