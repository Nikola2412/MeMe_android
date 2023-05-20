package com.example.meme;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MemesFragment extends Fragment implements MemeInterface{

    MemeInterface recycleViewInterface;
    private FragmentMemesBinding binding;

    public ArrayList<Meme> memes;
    private RecyclerView rv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
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
        ((MainActivity)getActivity()).Show();
        callApi(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    public void callApi(View view) {
        String url = "memes";
        String ip = getString(R.string.ip);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ip + url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length(); i++){
                    JSONObject json_data = response.optJSONObject(i);
                    String id = json_data.optString("id");
                    int id_kanala = json_data.optInt("id_kanala");
                    String naziv_kanala = json_data.optString("name");
                    Meme meme = new Meme(ip + "id_memea=" + id,id_kanala,naziv_kanala);
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
        //((MainActivity)getActivity()).toast(position);
    }

    @Override
    public void openImageFullscreen(ImageView imageView, int pos) {
        Dialog dialog = new Dialog(getContext(),R.style.Dialog);
        //Dialog dialog = new Dialog(getContext(),R.style.Theme_AppCompat_Dark_NoActionBar_FullScreen);
        dialog.setContentView(R.layout.image_dialog);
        ImageView fullscreenImageView = dialog.findViewById(R.id.view_meme);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        fullscreenImageView.setImageDrawable(imageView.getDrawable());
        TextView tv = dialog.findViewById(R.id.naziv_kanala);
        tv.setText(memes.get(pos).naziv_kanala);

        fullscreenImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        dialog.show();

    }
}