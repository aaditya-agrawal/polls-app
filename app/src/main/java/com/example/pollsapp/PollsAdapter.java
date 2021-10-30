package com.example.pollsapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.MyViewHolder> {
    ArrayList<Polls> pollsArrayList;
    Context context;
    onCLickInterface onCLickInterface;

    public PollsAdapter(Context context,ArrayList<Polls> pollsArrayList, onCLickInterface onclickinterface) {
        this.context = context;
        this.pollsArrayList = pollsArrayList;
        this.onCLickInterface = onclickinterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_data,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Polls poll = pollsArrayList.get(position);
        holder.topic.setText(poll.gettopic());
        holder.question.setText(poll.getquestion());
        holder.option1.setText(poll.getoption1());
        holder.option2.setText(poll.getoption2());
        holder.option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCLickInterface.setClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return pollsArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView topic , question;
        private Button option1,option2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.topic_name);
            question = itemView.findViewById(R.id.poll_question);
            option1 = itemView.findViewById(R.id.option_1);
            option2 = itemView.findViewById(R.id.option_2);
        }
    }
}
