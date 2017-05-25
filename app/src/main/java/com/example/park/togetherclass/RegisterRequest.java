package com.example.park.togetherclass;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Park on 2017-05-25.
 */

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://pkr10.cafe24.com/Register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String  ID, String Password, String Name, String StudentNumber, String Major ,String Code, String Subject, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ID", ID);
        parameters.put("PW", Password);
        parameters.put("NAME", Name);
        parameters.put("STUDENT_NUM", StudentNumber + "");
        parameters.put("MAJOR", Major);
        parameters.put("CODE", Code);
        parameters.put("SUBJECT", Subject);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
