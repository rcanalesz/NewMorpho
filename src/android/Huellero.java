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
    
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
        Log.i("HUELLERO", "execute");
        Context context = cordova.getActivity().getApplicationContext();
        if ("capturar".equals(action)) {
            this.openNewActivity(context, callbackContext);
            return true;
        }
        callbackContext.error("No existe metodo: " + action);
        Log.i("HUELLERO", "error");
        return false;
    }

    private void openNewActivity(Context context, CallbackContext callbackContext) {
        Intent intent = new Intent(context, NewActivity.class);
        //intent.putExtra("callbackContext", callbackContext);

        this.cordova.getActivity().startActivityForResult(intent, 1);
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");

                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK,"{\"bitmap\":\""+result+"\"}}");
                pluginResult.setKeepCallback(true);
                callbackContext.sendPluginResult(pluginResult);


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                String error =data.getStringExtra("error");

                PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, error);
                callbackContext.sendPluginResult(pluginResult);
            }
        }
    }//onActivityResult

}