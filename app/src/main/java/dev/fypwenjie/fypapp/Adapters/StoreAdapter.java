package dev.fypwenjie.fypapp.Adapters;

/**
 * Created by VINTEDGE on 9/4/2018.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dev.fypwenjie.fypapp.Activities.StoreScreen;
import dev.fypwenjie.fypapp.Domain.Store;
import dev.fypwenjie.fypapp.R;

/**
 * Created by VINTEDGE on 21/2/2017.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private static LayoutInflater inflater = null;
    ArrayList<Store> store;
    Activity activity;
    private Context context;

    public StoreAdapter(Context context, ArrayList<Store> store) {
        super();
        this.store = store;
        this.activity = (Activity) context;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_category, parent, false);
        StoreViewHolder holder = new StoreViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(StoreViewHolder holder, int position) {
        final Store stores = store.get(position);
        holder.categoryTitle.setText(stores.getStore_name().toUpperCase());
        holder.categoryTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, StoreScreen.class);
                i.putExtra("categoryDesc", stores.getStore_name());
                i.putExtra("categoryID", stores.getStore_id());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return store.size();
    }

    class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTitle;

        public StoreViewHolder(View itemView) {
            super(itemView);
            categoryTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }


}