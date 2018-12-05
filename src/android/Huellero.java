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

import com.zy.lib.morpho.ui.BioCapture;
import com.zy.lib.morpho.ui.IBioCapture;
import com.zy.lib.morpho.ui.ZyRequest;
import com.zy.lib.morpho.ui.ZyResponse;

import java.io.ByteArrayOutputStream;
import android.util.Base64;

public class Huellero extends CordovaPlugin {

    private static final String TAG = "ZY_Activity";
    private Bitmap imgFP;
    private byte[] byteArray;
    
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
        if ("capturar".equals(action)) {
            try {
                IBioCapture iBioCapture = new BioCapture(this, new IBioCapture.ICallback() {
                            
                    @Override
                    public void onSuccess(ZyResponse zyResponse) {
                        imgFP.setImageBitmap(zyResponse.bm);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
                        imgFP.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byteArray = byteArrayOutputStream .toByteArray();   
                        
                        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK,"{\"bitmap\":\""+encoded+"\"}}");
                        pluginResult.setKeepCallback(true);
                        callbackContext.sendPluginResult(pluginResult);
                    }
        
                    @Override
                    public void onError(ZyResponse obj) {
                        PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR , obj.deError);
                        callbackContext.sendPluginResult(pluginResult);
                    }
                });

                return true;
            } catch (Exception e) {
                callbackContext.error("Error ejecutando Action: " + e);
                return false;
            }
        }
        callbackContext.error("No existe metodo: " + action);
        return false;
    }

}