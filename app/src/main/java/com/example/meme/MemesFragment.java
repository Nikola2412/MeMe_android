package com.example.meme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.meme.databinding.FragmentMemesBinding;
import com.example.meme.MemeInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MemesFragment extends Fragment implements MemeInterface{

    MemeInterface recycleViewInterface;
    private FragmentMemesBinding binding;

    public ArrayList<Meme> memes;
    private RecyclerView rv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMemesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_memes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        memes = new ArrayList<>();
        recycleViewInterface = this;
        callApi(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void callApi(View view) {
        String url = "memes";
        String ip = "http://192.168.1.7:3001/";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ip + url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject json = new JSONObject();
                for(int i=0; i<response.length(); i++){
                    JSONObject json_data = response.optJSONObject(i);
                    String id = json_data.optString("id");

                    int id_kanala = json_data.optInt("id_kanala");
                    //((MainActivity)getActivity()).toast(ip + "see?meme=" + id);
                    Meme meme = new Meme(ip + "id_memea=" + id,id_kanala);
                    memes.add(meme);
                }
                rv = view.findViewById(R.id.memes);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                MemeAdapter md = new MemeAdapter(getContext(),memes, recycleViewInterface);
                rv.setAdapter(md);
                md.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(((MainActivity)getActivity()).getApplicationContext());
        requestQueue.add(request);

    }

    @Override
    public void onItemClick(int position) {
        ((MainActivity)getActivity()).toast(position);

    }

}