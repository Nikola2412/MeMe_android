package com.example.meme.ui.home;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.meme.MainActivity;
import com.example.meme.MyAdapter;
import com.example.meme.R;
import com.example.meme.Videos;
import com.example.meme.databinding.FragmentHomeBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class HomeFragment extends Fragment implements RecycleViewInterface{

    RecycleViewInterface recycleViewInterface;
    private FragmentHomeBinding binding;
    public ArrayList<Videos> videos;
    private RecyclerView rv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videos = new ArrayList<>();
        recycleViewInterface = this;
        callApi(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void callApi(View view) {
        String url = "videos";
        String ip = getString(R.string.ip);
        String url2 = "https://jsonplaceholder.typicode.com/todos/1";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ip + url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject json = new JSONObject();
                //((MainActivity) getActivity()).toast(response.toString());

                for(int i=0; i<response.length(); i++){
                    JSONObject json_data = response.optJSONObject(i);
                    //((MainActivity) getActivity()).toast(json_data.optString("ime"));
                    int id = json_data.optInt("id");
                    String ime = json_data.optString("ime");
                    String name = json_data.optString("name");
                    //String name = json_data.optString("name");
                    Videos video = new Videos(ime,
                            ip+"thubnails/" + id + ".jpg", ip + "video?id=" + id,name);
                    videos.add(video);
                }
                rv = view.findViewById(R.id.videos);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                MyAdapter md = new MyAdapter(getContext(), videos,recycleViewInterface);
                rv.setAdapter(md);
                md.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((MainActivity) getActivity()).toast(error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(((MainActivity)getActivity()).getApplicationContext());
        requestQueue.add(request);

    }

    @Override
    public void onItemClick(int position) {
        ((MainActivity) getActivity()).toast(position);
    }

    @Override
    public void onLink(int position) {
        ((MainActivity) getActivity()).toast(videos.get(position).kanal);
    }


}