package com.example.firebasedemo.chatComponent;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedemo.R;

import java.util.ArrayList;

public class MessageAdapter extends  RecyclerView.Adapter<MessageHolder>{
    private final ArrayList<StickMessage> itemList;
    private ItemClickListener listener;
    private Context context;
    private String userName;
    public MessageAdapter(Context context, ArrayList<StickMessage> itemList) {
        this.context=context;
        this.itemList = itemList;
    }
    public MessageAdapter(Context context, ArrayList<StickMessage> itemList,String userName) {
        this.context=context;
        this.itemList = itemList;
        this.userName=userName;
    }
    public void setOnItemClickListener(ItemClickListener listener) {

        this.listener = listener;
    }
    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_card, parent, false);
        return new MessageHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        StickMessage currentItem = itemList.get(position);
        if(currentItem.getAuthor().equals(userName)||currentItem.getAuthor().equals("You")){

            holder.message_card.setCardBackgroundColor(Color.WHITE);
        }else{
            holder.message_card.setCardBackgroundColor(Color.parseColor("#95C9E6"));
        }
        if(currentItem.getisImage()){
            holder.message_content.setVisibility(View.GONE);
            holder.image_message.setVisibility(View.VISIBLE);
           // System.out.println(R.drawable.boring);
            holder.image_message.setImageResource(Integer.parseInt(currentItem.getContent()));

        }else {
            holder.message_content.setVisibility(View.VISIBLE);
            holder.message_content.setText(currentItem.getContent());
            holder.image_message.setVisibility(View.GONE);
        }
        holder.time.setText(currentItem.getTime());

        holder.author_name.setText(currentItem.getAuthor());
        //holder.movieName.setText(currentItem.getMovieName());
       // holder.movieDesc.setText(currentItem.getMoveDesc());
        //holder.itemDesc.setText(currentItem.getItemDesc());

//        holder.itemDesc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(Intent.ACTION_VIEW);
//                String url=currentItem.getItemDesc();
//
//
//                intent.setData(Uri.parse(url));
//                context.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {

        return itemList.size();
    }

}
