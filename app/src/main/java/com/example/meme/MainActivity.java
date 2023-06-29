package com.example.meme;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.meme.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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


    private ConstraintLayout mainLayout;
    private FrameLayout PlayerContainer;
    private VideoView videoView;
    private ImageButton expandButton;
    private ImageView canclePlayer;
    private float dX, dY;
    private int W,H;
    private boolean isExpanded = false;

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
    public Toolbar toolbar;
    public ActionBar actionBar;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.mytoolbar);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        setSupportActionBar(findViewById(R.id.mytoolbar));

        navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,R.id.navigation_upload,R.id.navigation_meme)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);



        mainLayout = findViewById(R.id.container);
        PlayerContainer = findViewById(R.id.PlayerContainer);
        videoView = findViewById(R.id.videoView);
        canclePlayer = findViewById(R.id.cancle_player);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        W = displayMetrics.widthPixels;
        H = displayMetrics.heightPixels;

        PlayerContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(PlayerContainer.getY()  > toolbar.getHeight()) {
                            v.animate()
                                    .y(event.getRawY() + dY)
                                    .setDuration(0)
                                    .start();
                        }
                        if(PlayerContainer.getY()<toolbar.getHeight()){
                            v.animate()
                                    .y(toolbar.getHeight())
                                    .setDuration(0)
                                    .start();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        // Provera da li je mini plejer prevučen dovoljno visoko za proširivanje
                        /*
                        if (v.getY() < H / 3) {
                            expandMiniPlayer();
                        }
                        else if (event.getRawY() > H - navView.getHeight()){
                            closePlayer();
                        }
                        else {
                            resetMiniPlayerPosition();
                        }
                         */
                        break;
                }
                return true;
            }
        });
        canclePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetMiniPlayerPosition();
                PlayerContainer.setVisibility(View.INVISIBLE);
                videoView.suspend();
            }
        });
    }

    private void closePlayer() {
        resetMiniPlayerPosition();
        PlayerContainer.setVisibility(View.INVISIBLE);
    }

    private void expandMiniPlayer() {
        isExpanded = true;

    }

    private void resetMiniPlayerPosition() {
        PlayerContainer.animate()
                .y(mainLayout.getHeight() - PlayerContainer.getHeight() - navView.getHeight() - 16)
                .setDuration(200)
                .start();
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
        actionBar.hide();
        navView.setVisibility(View.GONE);
    }
    public void Show(){
        actionBar.show();
        if (navView != null) {
            navView.setVisibility(View.VISIBLE);
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    public void setVideo(String videoUrl){
        currentURL = videoUrl;
        PlayerContainer.setVisibility(View.VISIBLE);
        videoView.setVideoURI(Uri.parse(currentURL));
        videoView.start();
        canclePlayer.setVisibility(View.VISIBLE);
        Show();
    }
    public String URL(){
        return currentURL;
    }
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        return false;
    }
}