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

import dev.fypwenjie.fypapp.Activities.FoodMenuScreen;
import dev.fypwenjie.fypapp.Domain.Category;
import dev.fypwenjie.fypapp.R;
import dev.fypwenjie.fypapp.Util.GlobalValue;

/**
 * Created by VINTEDGE on 21/2/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private static LayoutInflater inflater = null;
    ArrayList<Category> category;
    Activity activity;
    private Context context;

    public CategoryAdapter(Context context, ArrayList<Category> category) {
        super();
        this.category = category;
        this.activity = (Activity) context;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_category, parent, false);
        CategoryViewHolder holder = new CategoryViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, int position) {
        final Category categories = category.get(position);
        String banner_url = GlobalValue.Domain_name+categories.getCategory_banner();
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
        holder.store_title.setText(categories.getCategory_name().toUpperCase());
        holder.store_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FoodMenuScreen.class);
                i.putExtra("category_name", categories.getCategory_name());
                i.putExtra("category_id", categories.getCategory_id());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView store_title;
        TextView store_category;
        LinearLayout store_container;
        ImageView store_Img;

        public CategoryViewHolder(View itemView) {
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