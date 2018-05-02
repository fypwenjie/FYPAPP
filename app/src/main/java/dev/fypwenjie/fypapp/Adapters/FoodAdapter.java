package dev.fypwenjie.fypapp.Adapters;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Locale;

import dev.fypwenjie.fypapp.Activities.AddCartScreen;
import dev.fypwenjie.fypapp.Domain.Food;
import dev.fypwenjie.fypapp.R;
import dev.fypwenjie.fypapp.Util.GlobalValue;

/**
 * Created by VINTEDGE on 21/2/2017.
 */
public class FoodAdapter extends ArrayAdapter<Food> implements View.OnClickListener{

    private ArrayList<Food> dataSet;
    Context mContext;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Food food = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_food, parent, false);
            viewHolder.foodTitle = (TextView) convertView.findViewById(R.id.title_food);
            viewHolder.foodDesc = (TextView) convertView.findViewById(R.id.desc_food);
            viewHolder.foodPrice = (TextView) convertView.findViewById(R.id.price_food);
            viewHolder.discountPrice_food = (TextView) convertView.findViewById(R.id.discountPrice_food);
            viewHolder.foodDiscount = (TextView) convertView.findViewById(R.id.discount_food);
            viewHolder.foodLayout = (LinearLayout) convertView.findViewById(R.id.food_layout);
            viewHolder.foodImg = (ImageView) convertView.findViewById(R.id.food_img);
            viewHolder.imgWrapper = (LinearLayout) convertView.findViewById(R.id.img_wrapper);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        lastPosition = position;
        viewHolder.foodTitle.setText((food.getFood_name() != null) ?  food.getFood_name() : null);
        String desc = food.getFood_desc();
        if (desc.equals("") || desc.equals("null") ) {
            viewHolder.foodDesc.setVisibility(View.GONE);
        } else {
            viewHolder.foodDesc.setText((food.getFood_desc() != null) ? food.getFood_desc() : "");
        }
        viewHolder.foodLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, AddCartScreen.class);
                i.putExtra("food_id", food.getFood_id());
                i.putExtra("food_name", food.getFood_name());
                i.putExtra("food_quantity", food.getFood_quantity());
                i.putExtra("food_desc", food.getFood_desc());
                i.putExtra("food_price", food.getFood_price());
                mContext.startActivity(i);
            }
        });
        String discount = food.getFood_discount();
        if (discount.equals("0")) {
            viewHolder.foodPrice.setText((food.getFood_price() != null) ? "RM " + food.getFood_price() : "");
            viewHolder.foodPrice.setPaintFlags(0);
            viewHolder.foodDiscount.setVisibility(View.GONE);
        } else {
            Double discounted = Double.parseDouble(food.getFood_discount());
            Double food_price = Double.parseDouble(food.getFood_price());
            String or_price = String.format(Locale.US, "%.2f", (food_price / (1 - (discounted /100) )) );
            viewHolder.foodPrice.setText("RM " + or_price);
            viewHolder.foodPrice.setPaintFlags(viewHolder.foodPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.foodDiscount.setText((food.getFood_discount() != null) ? String.format(Locale.US, "%.0f", Double.parseDouble( food.getFood_discount())) + "%" : "");
            viewHolder.discountPrice_food.setText("RM " + String.format( Locale.US , "%.2f" , Double.parseDouble( food.getFood_price())));
        }
        if (food.getFood_banner().equals("") || food.getFood_banner().equals("null") ) {

            viewHolder.imgWrapper.setVisibility(View.GONE);
        }
        else {
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.loadImage( GlobalValue.Domain_name+food.getFood_banner(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    // Do whatever you want with Bitmap
                    viewHolder.foodImg.setImageBitmap(loadedImage);
                    //Bitmap.createScaledBitmap(loadedImage,holder.store_Img.getMaxWidth(),holder.store_Img.getMaxHeight(),false)
                }
            });

        }
        // Return the completed view to render on screen
        return convertView;
    }

    public FoodAdapter(ArrayList<Food> data, Context context) {
        super(context, R.layout.list_food, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Food food=(Food) object;

        switch (v.getId())
        {
            case R.id.food_layout:

                break;
        }
    }

    private int lastPosition = -1;

    // View lookup cache
    private static class ViewHolder {
        TextView foodTitle;
        TextView foodDesc;
        TextView foodPrice;
        TextView discountPrice_food;
        TextView foodDiscount;
        LinearLayout foodLayout;
        LinearLayout imgWrapper;
        ImageView foodImg;
    }
}