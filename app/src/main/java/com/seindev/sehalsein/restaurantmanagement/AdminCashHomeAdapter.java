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
    List<AdminCashHomeInfo> adminHomeList;

    public AdminCashHomeAdapter(Context context, List<AdminCashHomeInfo> adminHomeList) {
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

        AdminCashHomeInfo adminCashHomeInfo = adminHomeList.get(position);

        holder.vLifetime.setText(adminCashHomeInfo.vLifetime);
        holder.vEarning.setText(adminCashHomeInfo.vEarning+"");
        holder.vGrowth.setText(adminCashHomeInfo.vGrowth+"");
        holder.vPreviousLife.setText(adminCashHomeInfo.vPreviousLife);
        holder.vGrowthIcon.setImageResource(adminCashHomeInfo.vGrowthIcon);

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
