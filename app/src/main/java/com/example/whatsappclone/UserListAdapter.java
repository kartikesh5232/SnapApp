package com.example.whatsappclone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListRecycleViewHolder> {
    ArrayList<UserObject> userlistobjects;
    Context context;

    public UserListAdapter(ArrayList<UserObject> userlistobjects) {
        this.userlistobjects = userlistobjects;
        this.context = context;
    }

    @NonNull
    @Override
    public UserListRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutview.setLayoutParams(lp);
        return new UserListRecycleViewHolder(layoutview);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListRecycleViewHolder holder, final int position) {
        holder.name.setText(userlistobjects.get(position).getName());
        holder.phone.setText(userlistobjects.get(position).getPhone());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserSnap.class);
                intent.putExtra("userUID",userlistobjects.get(position).getUid());
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userlistobjects.size();
    }


    public class UserListRecycleViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;
        public LinearLayout layout;

        public UserListRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
