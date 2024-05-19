package com.kursovaya;

import android.annotation.SuppressLint;
import android.app.Activity;
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

public class CustomAdapter  extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList run_id, run_title, run_distance, run_time;


    CustomAdapter(Activity activity, Context context, ArrayList run_id, ArrayList run_title, ArrayList run_distance, ArrayList run_time) {
        this.activity = activity;
        this.context = context;
        this.run_id = run_id;
        this.run_title = run_title;
        this.run_distance = run_distance;
        this.run_time = run_time;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.run_id_txt.setText(String.valueOf(run_id.get(position)));
        holder.run_title_txt.setText(String.valueOf(run_title.get(position)));
        holder.run_distance_txt.setText(String.valueOf(run_distance.get(position)));
        holder.run_time_txt.setText(String.valueOf(run_time.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(run_id.get(position)));
                intent.putExtra("title", String.valueOf(run_title.get(position)));
                intent.putExtra("distance", String.valueOf(run_distance.get(position)));
                intent.putExtra("time", String.valueOf(run_time.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {

        return run_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView run_id_txt, run_title_txt, run_distance_txt, run_time_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            run_id_txt = itemView.findViewById(R.id.run_id_txt);
            run_title_txt = itemView.findViewById(R.id.run_title_txt);
            run_distance_txt = itemView.findViewById(R.id.run_distance_txt);
            run_time_txt = itemView.findViewById(R.id.run_time_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
