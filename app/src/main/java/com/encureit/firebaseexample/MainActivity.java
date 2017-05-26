package com.encureit.firebaseexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    Button btnLogout;
    ArrayList<String> list = new ArrayList<>();
    private TextView textName;
    public RecycleAdapter mAdapter;
    public RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        btnLogout=(Button)findViewById(R.id.btnLogout);
        textName=(TextView)findViewById(R.id.textName);
        recyclerView=(RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter=new RecycleAdapter(list,this);
        recyclerView.setAdapter(mAdapter);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        ref.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              /* list.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    users obj = snapshot.getValue(users.class);
                    list.add(obj);
                }
               mAdapter.notifyDataSetChanged();*/


                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }

                list.clear();
                list.addAll(set);

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });




        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();//sign out method
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
    }


}
