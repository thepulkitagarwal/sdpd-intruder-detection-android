package com.sdpd.sdpd_intruder_detection_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {
    private String url = "10.2.2.15/status/intruderDetected";
    private String intruderPresentString = "Intruder present";
    private String intruderAbsentString = "Intruder Absent";

    private TextView status_text_field;
    private EditText url_field;
    private Button submit_button;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status_text_field = (TextView) findViewById(R.id.status_text);
        url_field = (EditText) findViewById(R.id.url);
        url_field.setText(url);
        submit_button = (Button) findViewById(R.id.submit);

        mRequestQueue = Volley.newRequestQueue(this);
        refresh();
    }

    protected void setStatus(String response) {
        status_text_field.setText(response);
    }

    protected void refresh() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        setStatus(response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setStatus("Error!");
                    }
                });
        mRequestQueue.add(stringRequest);
    }

    public void submit_clicked(View view) {
        url = url_field.getText().toString();
        refresh();
    }
}
