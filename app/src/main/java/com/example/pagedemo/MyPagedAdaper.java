package com.example.pagedemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MyPagedAdaper extends PagedListAdapter<Task, MyPagedAdaper.MyViewHolder> {


    protected MyPagedAdaper() {
        super(new DiffUtil.ItemCallback<Task>() {
            @Override
            public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
                return oldItem.getTaskName().equals(newItem.getTaskName());
            }
        });
    }

    protected MyPagedAdaper(@NonNull AsyncDifferConfig<Task> config) {
        super(config);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task task = getItem(position);
        if(task == null) {
            holder.textView.setText("loading");
        } else {
            holder.textView.setText("批号:" + task.getTaskName());
            holder.textView2.setText("工序名称:" + task.getTaskName() + "卷号:" + task.getTaskName());
            holder.textView3.setText("成品合金:" + task.getTaskName() + "成品状态:" + task.getTaskName());
            holder.textView4.setText("工序参数:" + task.getTaskName());
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView2, textView3, textView4;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
        }
    }
}
