package com.example.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button logout;
    private EditText edit;
    private Button add;
    private ListView listView;

    private TextView textUsername;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        userName = globalClass.getUsername();


        // fetch all components
        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        add = findViewById(R.id.add);
        listView = findViewById(R.id.listView);
        textUsername = findViewById(R.id.textView_username);

        textUsername.setText(userName);
        // set up onClick Listener
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Log out!", Toast.LENGTH_SHORT).show();
                globalClass.setUsername("");
                userName = globalClass.getUsername();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_name = edit.getText().toString();
                if (TextUtils.isEmpty(txt_name)) {
                    Toast.makeText(MainActivity.this, "No name entered", Toast.LENGTH_SHORT).show();
                } else {

                    // check exist
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("users").child(txt_name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                // Exist!
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(userName).child("connection");
                                ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if(task.isSuccessful()){
                                            List<String> prev = (List<String>) task.getResult().getValue();

                                            // check if the connection already exist
                                            if(prev.contains(txt_name)){
                                                Toast.makeText(MainActivity.this, txt_name+" is alreay connected!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                prev.add(txt_name);
                                                ref.setValue(prev);
                                            }

                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(MainActivity.this, "The user you want to connect not exist!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });


        // show the connection of current user
        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, list);
        listView.setAdapter(adapter);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(userName);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                DataSnapshot snapshot_connection = snapshot.child("connection");

                List<String> connections = (List<String>) snapshot_connection.getValue();

                for (int i = 0; i < connections.size(); i++) {
                    list.add(connections.get(i));
                }
                // cast to object
                // GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                // List<String> connectionOne = snapshot.getValue(t);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




}