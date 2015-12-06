package com.seindev.sehalsein.restaurantmanagement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by sehalsein on 25/11/15.
 */
public class MenuHomeAdapter extends RecyclerView.Adapter<MenuHomeAdapter.MenuHomeViewHolder>{

    Context context;
    List<MenuHomeInfo> menuHomeList;

    public MenuHomeAdapter(Context context,List<MenuHomeInfo> menuHomeList){
        this.context=context;
        this.menuHomeList=menuHomeList;


    }


    @Override
    public MenuHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list,parent,false);

        return new MenuHomeViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(MenuHomeViewHolder holder, int position) {

        MenuHomeInfo homeInfo=menuHomeList.get(position);

        holder.vIngredients.setText(homeInfo.vIngredients);
        holder.vDishName.setText(homeInfo.vDishName);
        holder.vDishIcon.setImageResource(homeInfo.vDishIcon);

    }

    @Override
    public int getItemCount() {
        return menuHomeList.size();
    }

    public static class MenuHomeViewHolder extends RecyclerView.ViewHolder {

        ImageView vDishIcon;
        TextView vDishName,vIngredients;

        public MenuHomeViewHolder(View itemView) {
            super(itemView);


            vDishIcon= (ImageView) itemView.findViewById(R.id.imageDishIcon);
            vDishName= (TextView) itemView.findViewById(R.id.textDishName);
            vIngredients= (TextView) itemView.findViewById(R.id.textIngredients);
        }
    }
}
