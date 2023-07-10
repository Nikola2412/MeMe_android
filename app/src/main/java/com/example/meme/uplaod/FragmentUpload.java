package com.example.meme.uplaod;

import static android.app.Activity.RESULT_OK;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.meme.MainActivity;
import com.example.meme.R;
import com.example.meme.UploadMeme;
import com.example.meme.databinding.FragmentUploadBinding;
import com.example.meme.video.VideoAdapter;
import com.example.meme.video.VideosRecyclerViewState;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class FragmentUpload extends Fragment implements UploadMemeInterface{

    private UploadRecyclerViewState recyclerViewState;

    private FragmentUploadBinding binding;
    public ArrayList<UploadMeme>memes;
    private static final int FILE_SELECT_CODE = 1;

    UploadMemeInterface uploadMemeInterface;
    View view;
    private RecyclerView recyclerView;
    UploadMemeAdapter umd;
    Button upload;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUploadBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable("recycler_state");
        }
        memes = new ArrayList<>();
        uploadMemeInterface = this;
        this.view = view;
        ((MainActivity)getActivity()).actionBar.show();
        upload = view.findViewById(R.id.upload_meme);
        recyclerView = view.findViewById(R.id.lista_mimova);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        umd = new UploadMemeAdapter(getContext(),memes,uploadMemeInterface);
        recyclerView.setAdapter(umd);
        if(recyclerViewState != null){
            memes = recyclerViewState.getMemes();
            umd.setMemes(memes);
        }
        umd.notifyDataSetChanged();
        changeButton();


        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity)getActivity()).logged) {
                    openFileExplorer();
                }
                else {
                    ((MainActivity)getActivity()).login_function();
                }
            }
        });
        view.findViewById(R.id.upload_meme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(memes.size()>0){
                    //Toast.makeText(getContext(),String.valueOf(memes.size()),Toast.LENGTH_SHORT).show();
                    /*
                    Uri uri = memes.get(0).Path;
                    try {
                        byte[] bytes = convertImageToByteArray(uri,getContext());

                        String url = "upload_android";
                        String ip = "http://192.168.1.6:3001/";

                        JSONObject data = new JSONObject();
                        data.put("meme",bytes);
                        data.put("id_kanala",((MainActivity)getActivity()).id_user);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ip + url, data, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        requestQueue.add(request);

                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }

                     */
                }
            }
        });
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }
    private void openFileExplorer() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");  // Postavite željeni tip datoteke ili "*" za sve vrste datoteka
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        try {
            startActivityForResult(Intent.createChooser(intent, "Izaberite datoteku"), FILE_SELECT_CODE);
        } catch (ActivityNotFoundException ex) {
            ((MainActivity)getActivity()).toast("Nema aplikacije za upravljanje datotekama");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri fileUri = data.getData();
                if (fileUri != null) {
                    UploadMeme meme = new UploadMeme(fileUri);
                    memes.add(0,meme);
                    umd.notifyItemInserted(0);
                    changeButton();
                    recyclerViewState = new UploadRecyclerViewState(memes);
                }
            }
        }
    }
    public void changeButton(){
        upload.setEnabled(memes.size()>0);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onItemClick(int position) {
        //((MainActivity)getActivity()).toast(position);
    }
    @Override
    public void onSizeChange() {
        //((MainActivity)getActivity()).toast("dasdasd");
        changeButton();
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            //Snackbar snackbar = Snackbar.make(R.id.uplaod,"Item Deleted",Snackbar.LENGTH_LONG);
            //snackbar.show();
            memes.remove(viewHolder.getAdapterPosition());
            umd.notifyItemRemoved(viewHolder.getAdapterPosition());;
            changeButton();
        }
    };
    public static byte[] convertImageToByteArray(Uri imageUri, Context context) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteStream.write(buffer, 0, bytesRead);
        }

        byte[] byteArray = byteStream.toByteArray();
        inputStream.close();
        byteStream.close();

        return byteArray;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recycler_state", recyclerViewState);
    }
}