package com.example.park.togetherclass;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Park on 2017-06-01.
 */

public class FreeDeleteRequest extends StringRequest {
    final static private String URL = "http://pkr10.cafe24.com/Delete.php";

    private Map<String, String> parameters;

    public FreeDeleteRequest(String FreeTitle, String FreeNick, String FreeDate, String FreePw, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("FreeTitle", FreeTitle);
        parameters.put("FreeNick",FreeNick);
        parameters.put("FreeDate",FreeDate);
        parameters.put("FreePw",FreePw);


    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
