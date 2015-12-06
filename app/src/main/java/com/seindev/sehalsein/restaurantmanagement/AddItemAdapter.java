package com.seindev.sehalsein.restaurantmanagement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by sehalsein on 02/12/15.
 */
public class AddItemAdapter extends RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder> {

    Context context;
    List<AddItemInfo> addItemList;

    public AddItemAdapter(Context context, List<AddItemInfo> addItemList) {
        this.context = context;
        this.addItemList = addItemList;
    }


    @Override
    public AddItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_item_list, parent, false);

        return new AddItemViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(AddItemViewHolder holder, int position) {

        AddItemInfo addItemInfo = addItemList.get(position);

        holder.vItemHint.setHint(addItemInfo.vItemHint);
        holder.vItemName.setText(addItemInfo.vItemName);

    }

    @Override
    public int getItemCount() {
        return addItemList.size();
    }

    public static class AddItemViewHolder extends RecyclerView.ViewHolder {

        TextView vItemName;
        EditText vItemHint;

        public AddItemViewHolder(View itemView) {
            super(itemView);


            vItemName = (TextView) itemView.findViewById(R.id.textItem);
            vItemHint = (EditText) itemView.findViewById(R.id.editItem);

        }
    }
}
