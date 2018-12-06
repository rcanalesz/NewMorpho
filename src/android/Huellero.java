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
        if ("capturar".equals(action)) {
            try {
                IBioCapture iBioCapture = new BioCapture(this.cordova.getActivity().getApplicationContext(), new IBioCapture.ICallback() {
                            
                    @Override
                    public void onSuccess(ZyResponse zyResponse) {
                        Log.i("HUELLERO", "success");
                        Bitmap imgFP;

                        imgFP = zyResponse.bm;

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
                        imgFP.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byteArray = byteArrayOutputStream .toByteArray();   
                        
                        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK,"{\"bitmap\":\""+encoded+"\"}}");
                        pluginResult.setKeepCallback(true);
                        callbackContext.sendPluginResult(pluginResult);
                    }

                    @Override
                    public void onComplete() {
                        Log.i("HUELLERO", "complete");
                        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK,"{\"bitmap\":\""+"on complete"+"\"}}");
                        pluginResult.setKeepCallback(true);
                        callbackContext.sendPluginResult(pluginResult);
                    }

                    @Override
                    public void onStart() {
                        Log.i("HUELLERO", "start");
                        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK,"{\"bitmap\":\""+"onstart"+"\"}}");
                        pluginResult.setKeepCallback(true);
                        callbackContext.sendPluginResult(pluginResult);
                    }
        
                    @Override
                    public void onError(ZyResponse obj) {
                        Log.i("HUELLERO", "error");
                        PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR , obj.deError);
                        callbackContext.sendPluginResult(pluginResult);
                    }
                });

                return true;
            } catch (Exception e) {
                Log.i("HUELLERO", "catch");
                callbackContext.error("Error ejecutando Action: " + e);
                return false;
            }
        }
        callbackContext.error("No existe metodo: " + action);
        Log.i("HUELLERO", "error");
        return false;
    }

}