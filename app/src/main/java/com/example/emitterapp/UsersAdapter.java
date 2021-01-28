package com.example.emitterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emitterapp.pojo.Users;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
    private ArrayList<Users> usersList = new ArrayList<>();
    private OnUsersListener mOnUsersListener;

    public UsersAdapter(OnUsersListener mOnUsersListener) {
        this.mOnUsersListener = mOnUsersListener;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_recycler_item, parent, false), mOnUsersListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.UserTV.setText(usersList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void setList(ArrayList<Users> usersList) {
        this.usersList = usersList;
        notifyDataSetChanged();
    }




    public class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView UserTV;
        OnUsersListener onUsersListener;


        public UsersViewHolder(@NonNull View itemView, OnUsersListener onUsersListener) {
            super(itemView);

            UserTV = itemView.findViewById(R.id.name_id);
            this.onUsersListener = onUsersListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onUsersListener.onUserClicked(getAdapterPosition(), usersList);
        }

    }

    public interface OnUsersListener {
        void onUserClicked(int position , ArrayList<Users> usersList);
    }
}
