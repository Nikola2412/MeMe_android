package com.example.meme.memes;

import android.app.Dialog;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.meme.MainActivity;
import com.example.meme.Meme;
import com.example.meme.R;
import com.example.meme.databinding.FragmentMemesBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MemesFragment extends Fragment implements MemesInterface {

    MemesInterface recycleViewInterface;
    private MemesRecyclerViewState recyclerViewState;

    private FragmentMemesBinding binding;

    public ArrayList<Meme> memes;
    private RecyclerView rv;
    MemesAdapter md;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMemesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    static boolean scroll_down;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable("recycler_state");
        }
        memes = new ArrayList<>();
        recycleViewInterface = this;
        rv = view.findViewById(R.id.memes);
        if (((MainActivity)getActivity()).isInternetAvailable(getContext())) {
            // Pristup internetu je dostupan
            view.findViewById(R.id.noNet).setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            md = new MemesAdapter(getContext(), memes, recycleViewInterface);
            rv.setAdapter(md);
            if (recyclerViewState != null) {
                memes = recyclerViewState.getMemes();
                md.setMemeArrayList(memes);
            } else {
                callApi();
            }

            rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (scroll_down) {
                        //((MainActivity)getActivity()).actionBar.hide();
                    } else {
                        //((MainActivity)getActivity()).actionBar.show();
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        scroll_down = true;
                    } else if (dy < 0) {
                        scroll_down = false;
                    }
                }
            });
        } else {
            // Pristup internetu nije dostupan
            rv.setVisibility(View.INVISIBLE);
            view.findViewById(R.id.noNet).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        ((MainActivity)getActivity()).actionBar.show();

    }

    public void callApi() {
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
                    Meme meme = new Meme(id,id_kanala,naziv_kanala);
                    memes.add(meme);
                }
                md.notifyDataSetChanged();
                recyclerViewState = new MemesRecyclerViewState(memes);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recycler_state", recyclerViewState);
    }
}