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

                ZyRequest zyRequest = new ZyRequest();
                iBioCapture.capturar(zyRequest);


                return true;
            } catch (Exception e) {
                Log.i("HUELLERO", "catch");
                callbackContext.error("Error ejecutando Action: " + e);
                return false;
            }