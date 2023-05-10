package com.example.meme.ui.home;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        //callApi();
        data();

        rv = view.findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        MyAdapter md = new MyAdapter(getContext(),videos);

        md.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MainActivity mainActivity = new MainActivity();
                Context context = mainActivity.getApplicationContext();
                Toast.makeText(context, "Item clicked at position " + position, Toast.LENGTH_SHORT).show();
            }
        });

        rv.setAdapter(md);
        //md.notifyDataSetChanged();

    }

    private void data() {
        videos = new ArrayList<>();

        for (int i = 0;i< 10;i++){
            Videos video = new Videos(Integer.toString(i),R.drawable.ic_launcher_background);
            videos.add(video);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void callApi() {
        try {
            // Create a URL object with the API endpoint
            URL url = new URL("http://localhost:3001/videos");


            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method
            connection.setRequestMethod("GET");


            // Get the response code
            int responseCode = 200;

            // Read the response

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
                Toast.makeText(this.getContext(),line, Toast.LENGTH_SHORT).show();
            }
            reader.close();

            // Handle the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Process the response data
                System.out.println("Response: " + response.toString());
                Toast.makeText(this.getContext(),response.toString(), Toast.LENGTH_SHORT).show();
            } else {
                // Handle error cases
                System.out.println("Error: " + responseCode);
            }

            // Disconnect the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}