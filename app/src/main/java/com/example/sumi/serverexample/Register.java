package com.example.sumi.serverexample;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class Register extends AppCompatActivity{
    EditText signupusername,signuppassword;
    Button register;
    //api url ho jun chai database jastai kaam garcha ra yesma username
    // ra password rakhne ra fetch garne uta login garda
    String url="http://silptech.com.np/phpscripts/registerRoutine.php";


    //1st ma requestqueue define garne
    RequestQueue requestQueue;
    //10 ma dialog use garna process dialog define gareko
    ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        signupusername=findViewById(R.id.username);
        signuppassword=findViewById(R.id.password);
        //11
        dialog=new ProgressDialog(Register.this);//dialog kaha dekhaune define gareko

        dialog.setMessage("Registering message...Please wait");// dialog ma k message display garaune msg deko
        dialog.setCancelable(false);

        register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //12 ma
                dialog.show();
                requestQueue= Volley.newRequestQueue(Register.this);
                //2nd ma
                StringRequest sr=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try{
                            //7th ma
                            JSONObject js1=new JSONObject(response);//response bhaneko chai json ko object name
                            int status=js1.getInt("success"); //success  bhaneko keyvalue ho jun anusar data compare garna use garcha
                            if (status==1) { //1 chai int value ho
                                //13 ma
                                dialog.cancel();
                                Toast.makeText(Register.this, "Register successful", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(Register.this, MainActivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                //14 ma
                                dialog.cancel();
                                Toast.makeText(Register.this,"register failure",Toast.LENGTH_SHORT).show();
                            }

                        }//8th ma
                        catch(Exception e){
                            Toast.makeText(Register.this,"Error",Toast.LENGTH_SHORT).show();


                        }
                    }

                }//3rd
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //15 ma
                        dialog.cancel();

                    }
                })//4th ma curly barcaes kholera method getparams kholene jun volley kai method ho
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //5th
                        Map<String,String> myMap=new HashMap<>();// mymap ma new hashmap define gareko
                        //6th
                        myMap.put("username",signupusername.getText().toString());//map use gareko key ra value pair ma data pathauna
                        myMap.put("password",signuppassword.getText().toString());
                        return myMap;
                    }
                } ;
                //9th
                requestQueue.add(sr);

            }
        }


        );



    }
}
