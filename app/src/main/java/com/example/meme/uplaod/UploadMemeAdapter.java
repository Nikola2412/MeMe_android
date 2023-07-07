package com.example.meme.uplaod;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meme.Meme;
import com.example.meme.R;
import com.example.meme.UploadMeme;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
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
        Uri path;
        public void setMeme(Uri path) {
            meme.setImageURI(path);
            this.path = path;
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
                    CropImageView img  = dialog.findViewById(R.id.preview_meme);
                    //img.setImageDrawable(meme.getDrawable());
                    img.setImageUriAsync(path);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dialog.findViewById(R.id.delete_meme).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            memes.remove(getAdapterPosition());
                            uploadMemeInterface.onSizeChange();
                            notifyItemRemoved(getAdapterPosition());
                        }
                    });
                    dialog.findViewById(R.id.save_meme).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //Bitmap cropped = img.getCroppedImage();
                            //ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            ///cropped.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                            ///String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),cropped, "Title", null);
                            //setMeme(Uri.parse(path));
                            meme.setImageDrawable(new BitmapDrawable(img.getCroppedImage()));
                            //notifyItemChanged(getAdapterPosition());
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
