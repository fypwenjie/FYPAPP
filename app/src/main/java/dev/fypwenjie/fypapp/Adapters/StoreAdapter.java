package dev.fypwenjie.fypapp.Adapters;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import dev.fypwenjie.fypapp.Activities.StoreScreen;
import dev.fypwenjie.fypapp.Domain.Store;
import dev.fypwenjie.fypapp.R;
import dev.fypwenjie.fypapp.Util.GlobalValue;

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
    public void onBindViewHolder(final StoreViewHolder holder, int position) {
        final Store stores = store.get(position);
        String banner_url = GlobalValue.Domain_name+stores.getStore_banner();
        Log.i("banner url", banner_url);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage(banner_url, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // Do whatever you want with Bitmap
                holder.store_Img.setImageBitmap(loadedImage);
                //Bitmap.createScaledBitmap(loadedImage,holder.store_Img.getMaxWidth(),holder.store_Img.getMaxHeight(),false)
            }
        });
        holder.store_title.setText(stores.getStore_name().toUpperCase());
        holder.store_category.setText(stores.getStore_category().toUpperCase());
        holder.store_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, StoreScreen.class);
                i.putExtra("store_name", stores.getStore_name());
                i.putExtra("store_cat", stores.getStore_category());
                i.putExtra("store_id", stores.getStore_id());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return store.size();
    }

    class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView store_title;
        TextView store_category;
        LinearLayout store_container;
        ImageView store_Img;

        public StoreViewHolder(View itemView) {
            super(itemView);
            store_Img = (ImageView) itemView.findViewById(R.id.image_store);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                store_Img.setImageDrawable(context.getDrawable( R.drawable.no_image));
            }
            store_title = (TextView) itemView.findViewById(R.id.title_store);
            store_container = (LinearLayout) itemView.findViewById(R.id.item_store);
            store_category = (TextView) itemView.findViewById(R.id.title_category);
        }
    }


}