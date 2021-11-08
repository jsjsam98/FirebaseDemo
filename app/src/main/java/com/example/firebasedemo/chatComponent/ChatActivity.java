package com.example.firebasedemo.chatComponent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedemo.GlobalClass;
import com.example.firebasedemo.R;
import com.example.firebasedemo.chatComponent.image.Image;
import com.example.firebasedemo.chatComponent.image.ImageAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private Button image;
    private Button send;
    private EditText message;
    private TextView textView;
    private String userName,roomName;
    private DatabaseReference root ;
    private String temp;
    private MessageAdapter messageAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<StickMessage> messageList =new ArrayList<>();
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    private ImageAdapter imageAdapter;
    private ArrayList<Image> imageList=new ArrayList<>();
    private RecyclerView recyclerView_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // get current user
        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        userName = globalClass.getUsername();

        for(StickMessage s:messageList){
            System.out.println(s.toString());
        }

        userName = userName;
        // userName=getIntent().getExtras().getString("user_name").toString();
        roomName=getIntent().getExtras().getString("room_name").toString();
        System.out.println(userName);
        System.out.println(roomName);
        setTitle("Room-"+roomName);
        root=FirebaseDatabase.getInstance().getReference().child("chatRoom/"+roomName);
        init(savedInstanceState);
        initImageData(savedInstanceState);
        send=findViewById(R.id.btn_send);
        image=findViewById(R.id.btn_image);
        message=findViewById(R.id.send_bar);
       // textView=findViewById(R.id.textView);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearList();
                Map<String ,Object> map=new HashMap<>();
                StickMessage m=new StickMessage();
                temp=root.push().getKey();
                DatabaseReference m1=root.child(temp);
                System.out.println(m1.toString());
                m1.setValue(new StickMessage(userName,Calendar.getInstance().getTime().toString(),message.getText().toString(),false));
                recyclerView.scrollToPosition(messageAdapter.getItemCount()-1);
                System.out.println(messageList.size()+"size");
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerView_image.getVisibility()==View.VISIBLE){
                    recyclerView_image.setVisibility(View.GONE);
                }else{
                    recyclerView_image.setVisibility(View.VISIBLE);
                }

            }
        });


    }

    private void clearList(){
        int size = messageList.size();
        messageList.clear();
        messageAdapter.notifyItemRangeRemoved(0, size);
    }

    private void addItem(int position,String author,String time,String content,boolean isImage) {
        messageList.add(position, new StickMessage(author, time,content,isImage));

        messageAdapter.notifyItemInserted(position);
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {


        int size = messageList == null ? 0 : messageList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);


        for (int i = 0; i < size; i++) {


            outState.putString(KEY_OF_INSTANCE + i + "0", messageList.get(i).getAuthor());
            outState.putString(KEY_OF_INSTANCE + i + "1", messageList.get(i).getTime());
            outState.putString(KEY_OF_INSTANCE + i + "2", messageList.get(i).getContent());
            outState.putBoolean(KEY_OF_INSTANCE + i + "3", messageList.get(i).getisImage());

        }
        super.onSaveInstanceState(outState);

    }
    private void initImageData(Bundle savedInstanceState){
        recyclerLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView_image=findViewById(R.id.image_view);
        recyclerView_image.setHasFixedSize(true);
        imageAdapter=new ImageAdapter(this,imageList);
        recyclerView_image.setAdapter(imageAdapter);
        recyclerView_image.setLayoutManager(recyclerLayoutManager);
        imageList.add(new Image(R.drawable.happy));
        imageList.add(new Image(R.drawable.boring));
        imageList.add(new Image(R.drawable.no));
        imageList.add(new Image(R.drawable.scary));
        imageList.add(new Image(R.drawable.sleep));
        ItemClickListener itemClickListener=new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                clearList();
                Map<String ,Object> map=new HashMap<>();
                StickMessage m=new StickMessage();
                temp=root.push().getKey();
                DatabaseReference m1=root.child(temp);
                System.out.println(m1.toString());
                m1.setValue(new StickMessage(userName,Calendar.getInstance().getTime().toString(),imageList.get(position).getPicture()+"",true));
                recyclerView_image.setVisibility(View.GONE);

              // System.out.println(messageList.size()+"size");
            }
        };
        imageAdapter.setOnItemClickListener(itemClickListener);
    }
    private void init(Bundle savedInstanceState){
        initialMessageData(savedInstanceState);
        createRecyclerView();
    }
    private void initialMessageData(Bundle savedInstanceState){

        if(savedInstanceState!=null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)){
            if(messageList==null||messageList.size()==0){
                int size=savedInstanceState.getInt(NUMBER_OF_ITEMS);
                for(int i=0;i<size;i++){
                    String author=savedInstanceState.getString(KEY_OF_INSTANCE+i+"0");
                    String time=savedInstanceState.getString(KEY_OF_INSTANCE+i+"1");
                    String content=savedInstanceState.getString(KEY_OF_INSTANCE+i+"2");
                    boolean isImage=savedInstanceState.getBoolean(KEY_OF_INSTANCE+i+"3");
                    StickMessage message_card=new StickMessage(author,time,content,isImage);
                    messageList.add(message_card);
                }
            }
        }else{
            //initial messageList

            DatabaseReference database=FirebaseDatabase.getInstance().getReference("chatRoom/"+getIntent().getExtras().getString("room_name").toString());
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    clearList();
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        StickMessage m1=dataSnapshot.getValue(StickMessage.class);
                        System.out.println(m1.toString());
                        if(m1.getAuthor().equals(userName)){
                            m1.setAuthor("You");
                        }
                        messageList.add(m1);


                        messageAdapter.notifyItemInserted(messageList.size());
                        recyclerView.scrollToPosition(messageList.size()-1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        }
    }

    private void createRecyclerView(){
        recyclerLayoutManager=new LinearLayoutManager(this);
        recyclerView=findViewById(R.id.message_view);
        recyclerView.setHasFixedSize(true);
        messageAdapter=new MessageAdapter(this,messageList,userName);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(recyclerLayoutManager);

    }
}