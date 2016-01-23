package com.seindev.sehalsein.restaurantmanagement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sehalsein on 25/11/15.
 */
public class MenuHomeAdapter extends RecyclerView.Adapter<MenuHomeAdapter.MenuHomeViewHolder> {

    Context context;
    List<Menu> menuHomeList;

    public MenuHomeAdapter(Context context, List<Menu> menuHomeList) {
        this.context = context;
        this.menuHomeList = menuHomeList;


    }


    @Override
    public MenuHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list, parent, false);

        return new MenuHomeViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(MenuHomeViewHolder holder, int position) {

        Menu homeInfo = menuHomeList.get(position);

        holder.vIngredients.setText(homeInfo.getCategory());
        holder.vDishName.setText(homeInfo.getDishName());
        // holder.vDishIcon.setImageResource(homeInfo.vDishIcon);

    }

    @Override
    public int getItemCount() {
        return menuHomeList.size();
    }

    public static class MenuHomeViewHolder extends RecyclerView.ViewHolder {

        ImageView vDishIcon;
        TextView vDishName, vIngredients;
        Button vPlus, vMinus;

        public MenuHomeViewHolder(View itemView) {
            super(itemView);

            vPlus = (Button) itemView.findViewById(R.id.buttonplus);
            vMinus = (Button) itemView.findViewById(R.id.buttonminus);
            vDishIcon = (ImageView) itemView.findViewById(R.id.imageDishIcon);
            vDishName = (TextView) itemView.findViewById(R.id.textDishName);
            vIngredients = (TextView) itemView.findViewById(R.id.textIngredients);
        }
    }
}
