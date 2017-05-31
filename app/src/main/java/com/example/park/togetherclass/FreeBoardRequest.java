package com.example.park.togetherclass;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Park on 2017-05-30.
 */

public class FreeBoardRequest extends StringRequest {
    final static private String URL = "http://pkr10.cafe24.com/writeRegister.php";

    private Map<String, String> parameters;


    public FreeBoardRequest(String Nick, String Title, String Content, String Date, String Pw, String Subject, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("FreeNick", Nick);
        parameters.put("FreeTitle", Title);
        parameters.put("FreeContent", Content);
        parameters.put("FreeDate", Date);
        parameters.put("FreePw", Pw);
        parameters.put("FreeSubject", Subject);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
