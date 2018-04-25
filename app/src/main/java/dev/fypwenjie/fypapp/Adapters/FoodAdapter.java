package dev.fypwenjie.fypapp.Adapters;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dev.fypwenjie.fypapp.Activities.AddCartScreen;
import dev.fypwenjie.fypapp.Domain.Food;
import dev.fypwenjie.fypapp.R;

/**
 * Created by VINTEDGE on 21/2/2017.
 */
public class FoodAdapter extends ArrayAdapter<Food> implements View.OnClickListener{

    private ArrayList<Food> dataSet;
    Context mContext;


    // View lookup cache
    private static class ViewHolder {
        TextView foodTitle;
        TextView foodDesc;
        TextView foodPrice;
        LinearLayout foodLayout;
        LinearLayout imgWrapper;
        ImageView foodImg;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Food food = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_food, parent, false);
            viewHolder.foodTitle = (TextView) convertView.findViewById(R.id.title_food);
            viewHolder.foodDesc = (TextView) convertView.findViewById(R.id.desc_food);
            viewHolder.foodPrice = (TextView) convertView.findViewById(R.id.price_food);
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
        viewHolder.foodDesc.setText((food.getFood_desc() != null) ?   food.getFood_desc() : null);
        viewHolder.foodLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, AddCartScreen.class);
                i.putExtra("food_id", food.getFood_id());
                i.putExtra("food_name", food.getFood_name());
                i.putExtra("food_desc", food.getFood_desc());
                i.putExtra("food_price", food.getFood_price());
                mContext.startActivity(i);
            }
        });
        viewHolder.foodPrice.setText((food.getFood_price() != null) ?  "RM " + food.getFood_price() : "");
        if(food.getFood_banner() == null) {
            LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) viewHolder.imgWrapper.getLayoutParams(); //or create new LayoutParams...
            lParams.weight = 0.0f;
            viewHolder.imgWrapper.setLayoutParams(lParams);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}