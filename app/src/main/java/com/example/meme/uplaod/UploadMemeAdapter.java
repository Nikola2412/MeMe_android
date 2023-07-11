package com.example.meme.uplaod;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meme.R;
import com.example.meme.UploadMeme;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class UploadMemeAdapter extends RecyclerView.Adapter<UploadMemeAdapter.MyViewHolder> {

    private UploadMemeInterface uploadMemeInterface;

    static Context context;

    public void setMemes(ArrayList<UploadMeme> memes) {
        this.memes = memes;
    }

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
        View v = LayoutInflater.from(context).inflate(R.layout.upload_meme,parent,false);
        return new UploadMemeAdapter.MyViewHolder(v,uploadMemeInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadMemeAdapter.MyViewHolder holder, int position) {
        UploadMeme meme = memes.get(position);
        holder.setMeme(meme.getPath());
    }

    @Override
    public int getItemCount() {
        return memes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView meme;
        View view;
        public void setMeme(Uri path) {
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
                    Dialog dialog = new Dialog(context,R.style.Dialog);
                    dialog.setContentView(R.layout.delete_dialog);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dialog.show();
                    CropImageView img  = dialog.findViewById(R.id.preview_meme);
                    //img.setImageDrawable(meme.getDrawable());
                    img.setImageUriAsync(memes.get(getAdapterPosition()).getOrg());
                    img.onSaveInstanceState();
                    Button save = dialog.findViewById(R.id.save_meme);
                    Button revert = dialog.findViewById(R.id.revert);
                    revert.setEnabled(memes.get(getAdapterPosition()).Edited());

                    //Nmp kako drugacije
                    img.setCropRect(new Rect(0,0,1000000000,1000000000));

                    img.setOnSetCropOverlayMovedListener(new CropImageView.OnSetCropOverlayMovedListener() {
                        @Override
                        public void onCropOverlayMoved(Rect rect) {
                            if(!save.isEnabled())
                                save.setEnabled(true);
                        }
                    });
                    img.setOnSetCropOverlayReleasedListener(new CropImageView.OnSetCropOverlayReleasedListener() {
                        @Override
                        public void onCropOverlayReleased(Rect rect) {
                            if(rect.top==img.getWholeImageRect().top && rect.right == img.getWholeImageRect().right && rect.bottom == img.getWholeImageRect().bottom && rect.left==img.getWholeImageRect().left)
                                save.setEnabled(false);
                        }
                    });
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
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            String url = MediaStore.Images.Media.insertImage(context.getContentResolver(), img.getCroppedImage(), "Edited", "");
                            memes.get(getAdapterPosition()).setPath(Uri.parse(url));
                            notifyItemChanged(getAdapterPosition());
                        }
                    });
                    revert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            memes.get(getAdapterPosition()).orgPath();
                            notifyItemChanged(getAdapterPosition());
                        }
                    });
                    return true;
                }
            });
        }
    }
}
