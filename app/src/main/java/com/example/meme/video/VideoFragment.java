package com.example.meme.video;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.meme.MainActivity;
import com.example.meme.R;
import com.example.meme.Videos;
import com.example.meme.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoFragment extends Fragment implements RecycleViewInterface{

    RecycleViewInterface recycleViewInterface;
    private VideosRecyclerViewState recyclerViewState;

    private FragmentHomeBinding binding;
    public ArrayList<Videos> videos;
    private RecyclerView rv;

    VideoAdapter md;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
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
        videos = new ArrayList<>();
        recycleViewInterface = this;
        rv = view.findViewById(R.id.videos);
        if (((MainActivity)getActivity()).isInternetAvailable(getContext())) {
            // Pristup internetu je dostupan
            view.findViewById(R.id.noNet).setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            md = new VideoAdapter(getContext(), videos, recycleViewInterface);
            rv.setAdapter(md);
            if (recyclerViewState != null) {
                videos = recyclerViewState.getVideos();
                md.setVideosArrayList(videos);
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
        }
        else {
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
        String url = "videos";
        String ip = getString(R.string.ip);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ip + url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject json = new JSONObject();
                //((MainActivity) getActivity()).toast(response.toString());

                for (int i = 0; i < response.length(); i++) {
                    JSONObject json_data = response.optJSONObject(i);
                    //((MainActivity) getActivity()).toast(json_data.optString("ime"));
                    int id = json_data.optInt("id");
                    String ime = json_data.optString("ime");
                    String name = json_data.optString("name");
                    //String name = json_data.optString("name");
                    Videos video = new Videos(ime,
                            ip + "thubnails/" + id + ".jpg", ip + "id_videa=" + id, name);
                    videos.add(video);
                    md.notifyItemInserted(videos.size() - 1);
                }
                recyclerViewState = new VideosRecyclerViewState(videos);
                md.notifyDataSetChanged();
                //Toast.makeText(getContext(),String.valueOf(recyclerViewState.getVideos().size()),Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(((MainActivity)getActivity()).getApplicationContext());
        requestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        //((MainActivity)getActivity()).toast(videos.get(position).link);
        ((MainActivity) getActivity()).setVideo(videos.get(position).link);
    }


    @Override
    public void onLink(int position) {
        ((MainActivity) getActivity()).toast(videos.get(position).kanal);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recycler_state", recyclerViewState);
    }
}