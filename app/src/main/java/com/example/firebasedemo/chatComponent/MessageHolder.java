package com.example.firebasedemo.chatComponent;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedemo.R;

public class MessageHolder extends RecyclerView.ViewHolder {


    public TextView time;
    public TextView message_content;
    public TextView author_name;
    public ImageView image_message;
    public CardView message_card;
    public MessageHolder(View itemView, final ItemClickListener listener) {
        super(itemView);

       time= itemView.findViewById(R.id.time);
        message_content= itemView.findViewById(R.id.message_content);
        author_name=itemView.findViewById(R.id.author_name);
        image_message=itemView.findViewById(R.id.image_message);
        message_card=itemView.findViewById(R.id.message_card);
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