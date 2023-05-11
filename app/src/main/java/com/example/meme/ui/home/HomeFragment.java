package com.example.meme.ui.home;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
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

import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public ArrayList<Videos> videos;
    private RecyclerView rv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callApi();
        data();

        rv = view.findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter md = new MyAdapter(getContext(), videos);
        /*
        md.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ((MainActivity)getActivity()).toast(98);
                //Context context = rv.getContext();
                //Toast.makeText(context, md.getItemCount(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, "Item clicked at position " + position, Toast.LENGTH_SHORT).show();
            }
        });
        */
        rv.setAdapter(md);
        md.notifyDataSetChanged();

    }

    private void data() {
        videos = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Videos video = new Videos(Integer.toString(i), R.drawable.ic_launcher_background, "jdsaasdj");
            videos.add(video);
        }
        //((MainActivity)getActivity()).toast(69);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void callApi() {
        String url = "https://10.0.2.2/videos";
        String url2 = "https://jsonplaceholder.typicode.com/todos/1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ((MainActivity)getActivity()).toast(response.toString());
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
}