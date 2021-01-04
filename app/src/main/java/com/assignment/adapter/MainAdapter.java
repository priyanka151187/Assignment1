package com.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.assignment.LocationModel;
import com.assignment.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private List<LocationModel> modelList;

    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName_txt,material_txt,closetime_txt,rating_txt;
        ImageView imageView;

        public MyViewHolder(View view) {

            super(view);
            itemName_txt = (TextView) view.findViewById(R.id.itemName_txt);
            material_txt = (TextView) view.findViewById(R.id.material_txt);
            closetime_txt = (TextView) view.findViewById(R.id.closetime_txt);
            rating_txt = (TextView) view.findViewById(R.id.rating_txt);
            imageView = (ImageView) view.findViewById(R.id.imageView);

        }
    }


    public MainAdapter(List<LocationModel> modelList) {
        this.modelList = modelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activitymain, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        LocationModel mList = modelList.get(position);

        holder.itemName_txt.setText(mList.getName());
      //  holder.material_txt.setText(mList.get());
       // holder.closetime_txt.setText(mList.getPlan_end_date());
        holder.rating_txt.setText(mList.getRating());

        Glide.with(context)
                .load(modelList.get(position).getIcon())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


}
