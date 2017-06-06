package com.encureit.firebaseexample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.List;

/**
 * Created by root on 19/4/17.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private List<String> list_item;
    public Context context;

    public RecycleAdapter(List<String> list_item, Context context) {
        this.list_item = list_item;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {

        myViewHolder.textView.setText(list_item.get(position).toString());
        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext(position);
            }
        });


    }

    public void goToNext(int position) {
        if (list_item != null) {
            if (list_item.size() != 0) {
                //this.context.startActivity(new Intent(this.context, QuickPayActivity.class).putExtra("schoolName", this.list_item.get(position).getSchoolName()));

                this.context.startActivity(new Intent(this.context, LoginActivity.class));
            } else {
                Toast.makeText(this.context, "Service side issue", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.context, "Service side issue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return list_item.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);

        }
    }
}
