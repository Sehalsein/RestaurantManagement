package com.seindev.sehalsein.restaurantmanagement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
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

        switch (position + 1) {
            case 1:
                //DISHNAME
                holder.vItemHint.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                break;
            case 2:
                //PRICE
                holder.vItemHint.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 3:
                //QUANTITY
                holder.vItemHint.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 4:
                //CATEGORY
                ArrayAdapter<String> categories = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, holder.categories);
                holder.vItemHint.setAdapter(categories);
                holder.vItemHint.setThreshold(1);
                holder.vItemHint.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                break;
            case 5:
                //TAGS
                /*holder.vItemHint.setRawInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                        | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
                        | InputType.TYPE_TEXT_FLAG_MULTI_LINE);*/
                ArrayAdapter<String> tags = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, holder.tags);
                holder.vItemHint.setAdapter(tags);
                holder.vItemHint.setThreshold(1);
                holder.vItemHint.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                break;
        }


        holder.vItemHint.setHint(addItemInfo.vItemHint);
        holder.vItemName.setText(addItemInfo.vItemName);

    }

    @Override
    public int getItemCount() {
        return addItemList.size();
    }


    public static class AddItemViewHolder extends RecyclerView.ViewHolder {

        TextView vItemName;
        MultiAutoCompleteTextView vItemHint;
        String[] categories;
        String[] tags;

        public AddItemViewHolder(View itemView) {
            super(itemView);

            categories = itemView.getResources().getStringArray(R.array.categories);
            tags = itemView.getResources().getStringArray(R.array.tags);
            vItemName = (TextView) itemView.findViewById(R.id.textItem);
            vItemHint = (MultiAutoCompleteTextView) itemView.findViewById(R.id.autocompletetextItem);

        }
    }
}
