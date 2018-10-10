package com.example.sumi.serverexample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button login, signup;
    String url = "http://silptech.com.np/phpscripts/loginRoutine.php";
    RequestQueue requestQueue;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Registering message...Please wait");
        dialog.setCancelable(false);

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                requestQueue = Volley.newRequestQueue(MainActivity.this);
                SharedPreferences sp = getSharedPreferences("myfile", MODE_PRIVATE);
                StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //7th ma
                            JSONObject js1 = new JSONObject(response);
                            int status = js1.getInt("success");
                            if (status == 1) {
                                //13 ma
                                dialog.cancel();
                                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                                SharedPreferences sp1 = getSharedPreferences("yourfile", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp1.edit();//shared pref lai edit garna editor banako
                                editor.putBoolean("state", true);//login state lai true gareko
                                editor.commit();


                                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(i);
                            } else {
                                //14th ma
                                dialog.cancel();
                                Toast.makeText(MainActivity.this, "Login failure", Toast.LENGTH_SHORT).show();
                            }

                        }//8th
                        catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();


                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.cancel();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> myMap = new HashMap<>();
                        //6th ma map use gareko key ra value pair ma data pathauna
                        myMap.put("username", username.getText().toString());
                        myMap.put("password", password.getText().toString());
                        return myMap;
                    }
                };
                requestQueue.add(sr);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //app suru hune bela login state true cha bhane direct home activity ma jane banako
        SharedPreferences sp = getSharedPreferences("your file", Context.MODE_PRIVATE);
        boolean state = sp.getBoolean("state", false);
        if (state) {
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
        }
    }
}
