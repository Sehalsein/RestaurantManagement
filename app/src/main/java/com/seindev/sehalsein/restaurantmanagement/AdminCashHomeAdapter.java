package com.seindev.sehalsein.restaurantmanagement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sehalsein on 29/11/15.
 */
public class AdminCashHomeAdapter extends RecyclerView.Adapter<AdminCashHomeAdapter.AdminHomeViewHolder> {


    Context context;
    List<AdminCash> adminHomeList;

    public AdminCashHomeAdapter(Context context, List<AdminCash> adminHomeList) {
        this.context = context;
        this.adminHomeList = adminHomeList;
    }

    @Override
    public AdminHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_cash_list, parent, false);

        return new AdminHomeViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(AdminHomeViewHolder holder, int position) {

        AdminCash adminCash = adminHomeList.get(position);

        holder.vLifetime.setText(adminCash.vLifetime);
        holder.vEarning.setText(adminCash.vEarning+"");
        holder.vGrowth.setText(adminCash.vGrowth+"");
        holder.vPreviousLife.setText(adminCash.vPreviousLife);
        holder.vGrowthIcon.setImageResource(adminCash.vGrowthIcon);

    }

    @Override
    public int getItemCount() {
        return adminHomeList.size();
    }

    public static class AdminHomeViewHolder extends RecyclerView.ViewHolder {

        ImageView vGrowthIcon;
        TextView vLifetime, vEarning, vGrowth, vPreviousLife;

        public AdminHomeViewHolder(View itemView) {
            super(itemView);

            vGrowthIcon = (ImageView) itemView.findViewById(R.id.imageGrowthIcon);
            vLifetime = (TextView) itemView.findViewById(R.id.textLifetime);
            vPreviousLife = (TextView) itemView.findViewById(R.id.textPreviousLife);
            vEarning = (TextView) itemView.findViewById(R.id.textEarning);
            vGrowth = (TextView) itemView.findViewById(R.id.textGrowth);
        }
    }
}
