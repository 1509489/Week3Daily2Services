package com.pixelart.week3daily2services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private List<Person> personList;
    private Context context;

    public RecylerViewAdapter(List<Person> personList) {
        this.personList = personList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder");
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_layout, viewGroup, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Person person = personList.get(position);

        viewHolder.tvName.setText(person.getName());
        viewHolder.tvAge.setText(String.valueOf(person.getAge()));
        viewHolder.tvWeight.setText(String.valueOf(person.getWeight()));

        int iconId = context.getResources().getIdentifier(person.getIcon(), "drawable", context.getPackageName());
        viewHolder.icon.setImageResource(iconId);

        viewHolder.itemView.setOnClickListener((view) -> {

        });

    }


    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvAge, tvWeight;
        private ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvWeight = itemView.findViewById(R.id.tvWeight);
            icon = itemView.findViewById(R.id.ivIcon);
        }
    }
}
