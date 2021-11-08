package com.example.firebasedemo.chatComponent.image;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedemo.R;
import com.example.firebasedemo.chatComponent.ItemClickListener;

public class ImageHolder extends RecyclerView.ViewHolder {




    public ImageView image;

    public ImageHolder(View itemView, final ItemClickListener listener) {
        super(itemView);


        image=itemView.findViewById(R.id.emoji_image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        listener.onItemClick(position);
                    }
                }



            }
        });


    }

}