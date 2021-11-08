package com.example.firebasedemo.chatComponent.image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedemo.R;
import com.example.firebasedemo.chatComponent.ItemClickListener;

import java.util.ArrayList;

public class ImageAdapter extends  RecyclerView.Adapter<ImageHolder>{
    private final ArrayList<Image> itemList;
    private ItemClickListener listener;
    private Context context;

    public ImageAdapter(Context context, ArrayList<Image> itemList) {
        this.context=context;
        this.itemList = itemList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {

        this.listener = listener;
    }
    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card, parent, false);
        return new ImageHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        Image currentItem = itemList.get(position);
        holder.image.setImageResource(currentItem.getPicture());


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
