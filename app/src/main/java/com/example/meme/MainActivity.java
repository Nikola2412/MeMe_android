package com.example.meme;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.meme.databinding.ActivityMainBinding;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public boolean logged = false;
    public MenuItem login,logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,R.id.navigation_upload,R.id.navigation_meme)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        Portrait();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

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

        toast(item.getTitle().toString());

        return super.onOptionsItemSelected(item);
    }

    public void Portrait() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
    @SuppressLint("ClickableViewAccessibility")
    public void test2(String videoUrl){

        setContentView(R.layout.dialog_video);
        VideoView videoView = findViewById(R.id.videoView);
        Uri uri = Uri.parse(videoUrl);

        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        mediaController.setMediaPlayer(videoView);

        // sets the media controller to the videoView
        videoView.setMediaController(mediaController);

        // starts the video
        videoView.start();

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                setContentView(binding.getRoot());
                return false;
            }
        });


        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setContentView(binding.getRoot());
                return false;
            }
        });
    }
}