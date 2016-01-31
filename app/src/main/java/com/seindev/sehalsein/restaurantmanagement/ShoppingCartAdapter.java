package com.seindev.sehalsein.restaurantmanagement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sehalsein on 31/01/16.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder> {

    Context context;
    List<ShoppingCart> shoppingCarts;

    public ShoppingCartAdapter(Context context, List<ShoppingCart> shoppingCarts) {
        this.context = context;
        this.shoppingCarts = shoppingCarts;
    }

    public ShoppingCartAdapter() {
    }

    @Override
    public ShoppingCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list, parent, false);

        return new ShoppingCartViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ShoppingCartViewHolder holder, int position) {

        ShoppingCart adminCashHomeInfo = shoppingCarts.get(position);

        holder.vname.setText(adminCashHomeInfo.name);
        holder.vprice.setText(adminCashHomeInfo.price + "");

    }

    @Override
    public int getItemCount() {
        return shoppingCarts.size();
    }

    public static class ShoppingCartViewHolder extends RecyclerView.ViewHolder {


        TextView vname, vprice;

        public ShoppingCartViewHolder(View itemView) {
            super(itemView);
            vname = (TextView) itemView.findViewById(R.id.name);
            vprice = (TextView) itemView.findViewById(R.id.price);
        }
    }
}
