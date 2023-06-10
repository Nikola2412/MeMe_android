package com.example.meme;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.meme.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public boolean logged = false;
    public MenuItem login,logout;
    NavController navController;

    String currentURL;

    BottomNavigationView navView;

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

        findViewById(R.id.navigation_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.upload_dialog);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.BOTTOM);

                Button meme = dialog.findViewById(R.id.upload_meme);
                Button video = dialog.findViewById(R.id.upload_video);

                meme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast("Meme");
                        dialog.dismiss();
                    }
                });
                video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast("Video");
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }


    public void setLogged(){
        if(!logged){
            logout.setVisible(true);
            logout.setVisible(false);
        }
        else {
            login.setVisible(false);
            logout.setVisible(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        login = menu.findItem(R.id.login);
        logout = menu.findItem(R.id.logout);
        setLogged();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String name = item.getTitle().toString();
        return super.onOptionsItemSelected(item);
    }

    public void Portrait() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
    public void All(){
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
    }
    public void test(View view){

    }
    public void toast(int k)
    {
        Toast.makeText(this.getApplicationContext(), Integer.toString(k), Toast.LENGTH_SHORT).show();
    }
    public void toast(String k)
    {
        Toast.makeText(this.getApplicationContext(), k, Toast.LENGTH_SHORT).show();
    }
    public void Hide(){
        getSupportActionBar().hide();
        navView.setVisibility(View.INVISIBLE);
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
        Hide();
    }
    public String URL(){
        return currentURL;
    }

}