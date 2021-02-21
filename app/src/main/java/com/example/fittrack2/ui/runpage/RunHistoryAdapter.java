package com.example.fittrack2.ui.runpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fittrack2.R;

public class RunHistoryAdapter extends RecyclerView.Adapter<RunHistoryAdapter.MyViewHolder> {
    String data1[], data2[];
    Context context;


    public RunHistoryAdapter(Context ct, String date[], String acwr[]){
        context = ct;
        data1 = date;
        data2 = acwr;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.runs_history_item, parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.t1.setText(data2[position]);
        holder.t2.setText(data1[position]);

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView t1, t2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t2 = itemView.findViewById(R.id.date_text);
            t1 = itemView.findViewById(R.id.acwr_text);
        }
    }
}
