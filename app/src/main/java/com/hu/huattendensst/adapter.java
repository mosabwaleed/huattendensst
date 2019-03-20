package com.hu.huattendensst;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {
    ArrayList<material_data> dataset;
    Context context;

    public adapter(ArrayList<material_data> dataset) {
        this.dataset = dataset;
    }

    public ArrayList<material_data> getDataset() {
        return dataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_card,parent,false);
        view.setOnClickListener(data.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        context = parent.getContext();
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        TextView name = myViewHolder.name;
        TextView hall = myViewHolder.hall;
        TextView time = myViewHolder.time;
        TextView sec = myViewHolder.sec;
        TextView days = myViewHolder.days;
        CardView cardView = myViewHolder.card;
        name.setText(dataset.get(i).getName());
        hall.setText("hall number : "+dataset.get(i).getHall());
        time.setText("time : "+dataset.get(i).getTime());
        sec.setText("section number : "+dataset.get(i).getSec());
        days.setText("days : "+dataset.get(i).getDays());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,choose.class);
                intent.putExtra("name",dataset.get(i).getName());
                intent.putExtra("hall",dataset.get(i).getHall());
                intent.putExtra("time",dataset.get(i).getTime());
                intent.putExtra("sec",dataset.get(i).getSec());
                intent.putExtra("days",dataset.get(i).getDays());
                intent.putExtra("doctorid",dataset.get(i).getDoctorid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (dataset == null) ? 0 : dataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,hall,time,sec,days;
        CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.hall = itemView.findViewById(R.id.hall_num);
            this.time = itemView.findViewById(R.id.time);
            this.sec = itemView.findViewById(R.id.sec_num);
            this.card = itemView.findViewById(R.id.card);
            this.days = itemView.findViewById(R.id.days);
        }

    }
}
