package com.selfproject.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList budget_id, budget_item, budget_description, budget_price, budget_date, budget_category;

    Animation translate_anim;

    CustomAdapter(Context context, ArrayList budget_id, ArrayList budget_item, ArrayList budget_description, ArrayList budget_price, Activity activity, ArrayList budget_date, ArrayList budget_category){
        this.activity = activity;
        this.context = context;
        this.budget_id = budget_id;
        this.budget_item = budget_item;
        this.budget_description = budget_description;
        this.budget_price = budget_price;
        this.budget_date = budget_date;
        this.budget_category = budget_category;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id, tv_item, tv_description, tv_price, tv_date;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_item = itemView.findViewById(R.id.tv_item);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_date = itemView.findViewById(R.id.tv_date);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.tv_id.setText(String.valueOf(budget_id.get(position)));
        holder.tv_item.setText(String.valueOf(budget_item.get(position)));
        holder.tv_description.setText(String.valueOf(budget_description.get(position)));
        holder.tv_price.setText("RM " + String.valueOf(budget_price.get(position)));
        holder.tv_date.setText(String.valueOf(budget_date.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("budget_id", String.valueOf(budget_id.get(position)));
                intent.putExtra("budget_item", String.valueOf(budget_item.get(position)));
                intent.putExtra("budget_description", String.valueOf(budget_description.get(position)));
                intent.putExtra("budget_price", String.valueOf(budget_price.get(position)));
                intent.putExtra("budget_date", String.valueOf(budget_date.get(position)));
                intent.putExtra("budget_category", String.valueOf(budget_category.get(position)));

                activity.startActivityForResult(intent, 1);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return budget_id.size();
    }
}
