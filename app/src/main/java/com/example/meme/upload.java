package com.example.meme;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meme.databinding.FragmentUploadBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;


public class upload extends Fragment implements UploadMemeInterface{

    private static final int PICK_IMAGE_REQUEST = 1;

    private FragmentUploadBinding binding;
    public ArrayList<UploadMeme>memes;
    private static final int FILE_SELECT_CODE = 1;

    UploadMemeInterface uploadMemeInterface;
    View view;
    private RecyclerView recyclerView;
    UploadMemeAdapter umd;

    ActivityResultLauncher<Intent> imageResulLauncher;


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
        memes = new ArrayList<>();
        uploadMemeInterface = this;
        this.view = view;
        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileExplorer();
            }
        });
        view.findViewById(R.id.upload_meme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity)getActivity()).toast(umd.getItemCount());
                //((MainActivity)getActivity()).toast(memes.get(umd.getItemCount() - 1).Path);
                ((MainActivity)getActivity()).toast(umd.getItemCount());
                ((MainActivity)getActivity()).toast(memes.size());
            }
        });
        recyclerView = view.findViewById(R.id.lista_mimova);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        umd = new UploadMemeAdapter(getContext(), memes,uploadMemeInterface);
        recyclerView.setAdapter(umd);
        umd.notifyDataSetChanged();

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
                    memes.add(meme);
                    umd.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        ((MainActivity)getActivity()).toast(position);
    }
}