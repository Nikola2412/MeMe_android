package com.example.meme;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.meme.databinding.FragmentPlayerBinding;

import org.json.JSONArray;

public class playerFragment extends Fragment implements playerInterface{

    //playerInterface playerInterface;
    //public ArrayList<Videos> videos;
    //private RecyclerView rv;
    private FragmentPlayerBinding binding;
    VideoView videoView;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlayerBinding.inflate(inflater,container,false);
        View root= binding.getRoot();
        return  root;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        ((MainActivity)getActivity()).Hide();
        setVideo();
    }
    public void setVideo(){
        String videoUrl = ((MainActivity)getActivity()).URL();
        videoView= view.findViewById(R.id.videoView);
        //Button back = view.findViewById(R.id.back_button);
        Uri uri = Uri.parse(videoUrl);

        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);

        mediaController.setMediaPlayer(videoView);

        // sets the media controller to the videoView
        videoView.setMediaController(mediaController);

        videoView.requestFocus();

        videoView.start();
    };
    private void hideSystemUI() {
        if (getActivity() != null) {
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    // Metoda za prikaz sistemskih traka
    private void showSystemUI() {
        if (getActivity() != null) {
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            if (getActivity().getActionBar() != null) {
                getActivity().getActionBar().show();
            }
        }
    }
    private void toggleFullScreen() {
        if (getActivity() != null) {
            Window window = getActivity().getWindow();
            View decorView = window.getDecorView();
            if (isFullScreen()) {
                // Ako je već u punom zaslonu, prebacite se na običan prikaz
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                if (getActivity().getActionBar() != null) {
                    getActivity().getActionBar().show();
                }
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            } else {
                // Ako je u običnom prikazu, prebacite se na puni zaslon
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                if (getActivity().getActionBar() != null) {
                    getActivity().getActionBar().hide();
                }
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        }
    }
    private boolean isFullScreen() {
        if (getActivity() != null) {
            int flags = getActivity().getWindow().getAttributes().flags;
            return (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0;
        }
        return false;
    }

    public  void callApi(View view){
        String url = "videos";
        String ip = getString(R.string.ip);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ip + url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity)getActivity()).Show();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {

    }
    public void test2(String videoUrl){

    }
}