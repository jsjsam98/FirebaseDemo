package com.example.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button logout;
    private EditText edit;
    private Button add;
    private ListView listView;
    private Button reset;
    private DatabaseReference mDatabase;
    private String curEmail;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // reset users
        resetUser();
        // get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // get email for test

            curEmail = user.getEmail();

            // print email to logcat
            Log.d("email", curEmail);

        }
        userName = curEmail.substring(0, 5);
        // fetch all components
        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        add = findViewById(R.id.add);
        listView = findViewById(R.id.listView);
        reset = findViewById(R.id.reset);

        // set up onClick Listener
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Log out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_name = edit.getText().toString();
                if (txt_name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No name entered", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(userName).child("connection");
                    ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(task.isSuccessful()){
                                List<String> prev = (List<String>) task.getResult().getValue();
                                prev.add(txt_name);

                                ref.setValue(prev);
                                Log.d("get",prev.toString());
                            }
                        }
                    });
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetUser();

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

    private void resetUser() {
        User user1 = new User("user1@gmail.com", new ArrayList<>(
                Arrays.asList("user2@gmail.com", "user3@gmail.com")
        ));

        User user2 = new User("user2@gmail.com", new ArrayList<>(
                Arrays.asList("user1@gmail.com", "user3@gmail.com")
        ));

        User user3 = new User("user3@gmail.com", new ArrayList<>(
                Arrays.asList("user1@gmail.com", "user2@gmail.com")
        ));


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").removeValue();
        List<User> userset = new ArrayList<>(Arrays.asList(user1, user2, user3));

        for (int i = 0; i < userset.size(); i++) {
            mDatabase.child("users").child("user" + (i + 1)).setValue(userset.get(i));
        }
    }


}