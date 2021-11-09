package com.usth.wordpress.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.usth.wordpress.MainActivity;
import com.usth.wordpress.R;
import com.usth.wordpress.other.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String clientId = "77703";
    private static final String clientSecret = "fDOe1HBWEiNiOl8WZ3rt1ABbTKcShBOFEd1orsZEj1NuWTNtMf02lNqJ3sI3c7MyReset ";
    private static final String redirectUri = "wordpresslite://callback";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                int state = getRandomNumber(100000, 9999999);
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://public-api.wordpress.com/oauth2/authorize" +
                                "?client_id=" + clientId +
                                "&response_type=code" +
//                                "&state=" + String.valueOf(state) +
                                "&redirect_uri=" + redirectUri));
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(redirectUri)) {
            String code = uri.getQueryParameter("code");
            getAccessToken(code);
            Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void getAccessToken(final String code) {
        StringRequest sr = new StringRequest(Request.Method.POST,"https://public-api.wordpress.com/oauth2/token", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String access_token = null;

                try {
                    JSONObject json = new JSONObject(response);
                    access_token = json.getString("access_token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (access_token != null) {
                    SharedPreferences.Editor editor = getSharedPreferences(MySingleton.PREF_LOGIN_INFO, MODE_PRIVATE).edit();
                    editor.putString(MySingleton.KEY_ACCESS_TOKEN, access_token);
                    editor.apply();

                    Intent intent = MainActivity.newIntent(LoginActivity.this);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Null token", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Can not get access token", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("client_id", clientId);
                params.put("redirect_uri",redirectUri);
                params.put("client_secret", clientSecret);
                params.put("code", code);
                params.put("grant_type", "authorization_code");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(sr);
    }
}