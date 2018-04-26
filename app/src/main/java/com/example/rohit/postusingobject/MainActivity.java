package com.example.rohit.postusingobject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    EditText emailId,password;
    Button button;
    StringRequest stringRequest;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailId=findViewById(R.id.emailId);
        password=findViewById(R.id.password);
        button=findViewById(R.id.button);
    }

    public void hit(View view)
    {
        final String email=emailId.getText().toString().trim();
        final String pass=password.getText().toString().trim();
        String add="http://192.168.0.110:9000/UserLogin\n";
        Log.d("emailId:=>",email);
        Log.d("password:=>",pass);

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        stringRequest=new StringRequest(Request.Method.POST, add, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                Log.d("ResponseIs",""+response);
                try
                {
                    jsonObject=new JSONObject(response);
                    String key=jsonObject.getString("token");
                    Log.d("ApiKeyIs",""+key);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.d("newError",""+e);
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_SHORT).show();
                Log.d("ErrorIs",""+error);
            }
        })
        {
            @Override
            protected  Map<String,String> getParams()
            {
                Map<String,String> map=new HashMap<>();
                map.put("emailId",email);
                map.put("password",pass);
                return map;
            }

        };

        requestQueue.add(stringRequest);
    }
}
