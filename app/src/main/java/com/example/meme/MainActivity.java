package com.example.meme;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.meme.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public String username = "",password = "";
    public String id_user="";
    public boolean logged = false;
    public MenuItem login,logout,user;
    NavController navController;

    String currentURL;

    BottomNavigationView navView;

    SharedPreferences settings;

    public void setLoginData(String user,String password,String id_user,Boolean logged){
        this.username = user;
        this.password = password;
        this.id_user = id_user;
        this.logged = logged;
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username",user);
        editor.putString("password",password);
        editor.putString("id",id_user);
        editor.putBoolean("logged",logged);
        editor.apply();
        setLogged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,R.id.navigation_upload,R.id.navigation_meme)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    public void checkUser(){
        String url = "android_auth";
        String ip = getString(R.string.ip);
        try {
            JSONObject data = new JSONObject();
            data.put("username",username);
            data.put("password",password);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ip + url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String id = response.optString("id");
                    if(!id_user.equals(id)){
                        toast("dasd");
                        setLoginData("","","",false);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    toast(error.toString());
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }


    public void setLogged(){
        if(!logged){
            logout.setVisible(true);
            logout.setVisible(false);
            user.setTitle("User");
        }
        else {
            user.setTitle(username);
            login.setVisible(false);
            logout.setVisible(true);
        }
    }

    public void login_function() {
        String url = "android_auth";
        String ip = getString(R.string.ip);
        Dialog dialog = new Dialog(this,R.style.Dialog);
        dialog.setContentView(R.layout.login);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user = dialog.findViewById(R.id.Username);
                String username = user.getText().toString();
                EditText pass = dialog.findViewById(R.id.Password);
                String password = pass.getText().toString();
                try {
                    JSONObject data = new JSONObject();
                    data.put("username",username);
                    data.put("password",password);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ip + url, data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String id = response.optString("id");
                            //toast(id);
                            if(!id.equals("-1")){
                                setLoginData(username,password,id,true);
                                dialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            toast(error.toString());
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        dialog.show();
    }

    public void logout(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        login = menu.findItem(R.id.login);
        login.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                login_function();
                return false;
            }
        });
        logout = menu.findItem(R.id.logout);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                logout();
                return false;
            }
        });
        user = menu.findItem(R.id.username);
        setLogged();
        settings = getApplicationContext().getSharedPreferences("Podaci",0);

        logged = settings.getBoolean("logged",false);
        if(logged) {
            username = settings.getString("username","");
            password = settings.getString("password","");
            id_user = settings.getString("id","");
            checkUser();
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String name = item.getTitle().toString();
        return super.onOptionsItemSelected(item);
    }
    public void toast(int k)
    {
        Toast.makeText(this.getApplicationContext(), Integer.toString(k), Toast.LENGTH_SHORT).show();
    }
    public void toast(Boolean k)
    {
        Toast.makeText(this.getApplicationContext(), Boolean.toString(k), Toast.LENGTH_LONG).show();
    }
    public void toast(String k)
    {
        Toast.makeText(this.getApplicationContext(), k, Toast.LENGTH_LONG).show();
    }
    public void Hide(){
        getSupportActionBar().hide();
        navView.setVisibility(View.GONE);
    }
    public void Show(){
        getSupportActionBar().show();
        if (navView != null) {
            navView.setVisibility(View.VISIBLE);
        }

    }
    public void test2(String videoUrl){
        navController.navigate(R.id.videos);
        currentURL = videoUrl;
    }
    public String URL(){
        return currentURL;
    }
}