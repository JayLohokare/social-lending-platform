package in.skylinelabs.yhacks;


import com.google.gson.JsonObject;

import org.json.JSONException;

/**
 * Created by Jay Lohokare on 1-Dec-17.
 */
public interface AsyncTaskComplete {
    void handleResult(JsonObject result, String action) throws JSONException;


}
