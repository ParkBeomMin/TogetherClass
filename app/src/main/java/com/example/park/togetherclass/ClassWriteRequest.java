package com.example.park.togetherclass;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Park on 2017-06-05.
 */

public class ClassWriteRequest extends StringRequest {
    final static private String URL = "http://pkr10.cafe24.com/ClassWrite.php";

    private Map<String, String> parameters;


    public ClassWriteRequest(String Content, String Nick, String Pw, String Date , Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ClassNick", Nick);
        parameters.put("ClassContent", Content);
        parameters.put("ClassDate", Date);
        parameters.put("ClassPw", Pw);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}

