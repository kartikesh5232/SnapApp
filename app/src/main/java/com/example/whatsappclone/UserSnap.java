package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserSnap extends AppCompatActivity {

    private RecyclerView recyclerView;
    private snapAdapter snapadapter;
    private DatabaseReference databaseReference;
    private List<Upload> uploads;
    private snapAdapter snapadapterview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_snap);
        Bundle bundle=getIntent().getExtras();
        final String userUID=bundle.getString("userUID");


        recyclerView=findViewById(R.id.snaprecycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploads=new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("snap").child(userUID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Upload upload=dataSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                snapadapterview=new snapAdapter(UserSnap.this,uploads);
                recyclerView.setAdapter(snapadapterview);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserSnap.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        Log.i("snapid",userUID);

    }

}
