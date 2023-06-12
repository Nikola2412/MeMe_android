package com.example.meme;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class UploadMemeAdapter extends RecyclerView.Adapter<UploadMemeAdapter.MyViewHolder> {

    private UploadMemeInterface uploadMemeInterface;

    static Context context;

    String ip;
    ArrayList<UploadMeme>memes;

    public UploadMemeAdapter(Context context,ArrayList<UploadMeme> memes,UploadMemeInterface uploadMemeInterface) {
        this.memes = memes;
        this.context = context;
        this.uploadMemeInterface = uploadMemeInterface;
    }

    @NonNull
    @Override
    public UploadMemeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.meme,parent,false);
        return new UploadMemeAdapter.MyViewHolder(v,uploadMemeInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadMemeAdapter.MyViewHolder holder, int position) {
        UploadMeme meme = memes.get(position);
        holder.setMeme(meme.Path);
    }

    @Override
    public int getItemCount() {
        return memes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView meme;
        View view;
        public void setMeme(Uri path) {
            //Toast.makeText(context,path, Toast.LENGTH_LONG).show();
            //Glide.with(context).load(new File(path)).into(meme);
            //Glide.with(context).load("http://192.168.1.4:3001/id_memea="+"ef7341c8-379c-4bc8-87e4-79d573cd64a7").into(meme);
            meme.setImageURI(path);
        }
        public MyViewHolder(@NonNull View itemView, UploadMemeInterface uploadMemeInterface) {
            super(itemView);
            view = itemView;
            meme = view.findViewById(R.id.mim);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(uploadMemeInterface !=null){
                        int pos = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            uploadMemeInterface.onItemClick(pos);
                        }
                    }
                }
            });
            meme.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Toast.makeText(context,Integer.toString(getAdapterPosition()), Toast.LENGTH_LONG).show();
                    Dialog dialog = new Dialog(context,R.style.Dialog);
                    dialog.setContentView(R.layout.delete_dialog);
                    ImageView img  = dialog.findViewById(R.id.preview_meme);
                    img.setImageDrawable(meme.getDrawable());
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dialog.findViewById(R.id.delete_meme).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            memes.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                        }
                    });
                    dialog.findViewById(R.id.edit_meme).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(context,"Jos je u izdardi",Toast.LENGTH_LONG).show();
                        }
                    });
                    dialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    return true;
                }
            });
        }
    }
}
