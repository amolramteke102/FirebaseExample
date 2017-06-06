package com.encureit.firebaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textName)
    TextView textName;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    @BindView(R.id.btnCreate)
    Button btnCreate;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    private FirebaseAuth auth;
    ArrayList<String> list = new ArrayList<>();
    public RecycleAdapter mAdapter;
    public RecyclerView recyclerView;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new RecycleAdapter(list, this);
        recyclerView.setAdapter(mAdapter);

        mFirebaseInstance=FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        final String userId = mFirebaseDatabase.push().getKey();

        mFirebaseDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=editText.getText().toString();
                String value="1";
                if (!TextUtils.isEmpty(data)){
                    mFirebaseDatabase.child(userId).setValue(data);
                    mFirebaseDatabase.child(userId).setValue(value);
                }

            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();//sign out method
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=editText.getText().toString();
                String value="1";
                if (TextUtils.isEmpty(userId)){
                    createUser(data,value);
                }
            }
        });
    }


    public void createUser(String name, String value) {
        if (TextUtils.isEmpty(userId)){
            userId = mFirebaseDatabase.push().getKey();
        }
        users user = new users(name, value);

        mFirebaseDatabase.child(userId).setValue(user);

        addUserChangeListener();
    }

    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users user = dataSnapshot.getValue(users.class);

                // Check for null
                if (user == null) {
                   // Log.e(TAG, "User data is null!");
                    return;
                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e( "Failed to read user", String.valueOf(error.toException()));
            }
        });
    }




}
