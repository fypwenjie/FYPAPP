package dev.fypwenjie.fypapp.Adapters;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dev.fypwenjie.fypapp.Domain.Food;
import dev.fypwenjie.fypapp.Domain.Store;
import dev.fypwenjie.fypapp.R;

/**
 * Created by VINTEDGE on 21/2/2017.
 */
public class FoodAdapter extends ArrayAdapter<Store> implements View.OnClickListener{

    private ArrayList<Store> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView foodTitle;
        TextView foodDesc;
        TextView foodPrice;
        LinearLayout foodLayout;
        ImageView foodImg;
    }

    public FoodAdapter(ArrayList<Store> data, Context context) {
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
        Store store = getItem(position);
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

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        lastPosition = position;
        viewHolder.foodTitle.setText(store.getStore_name());
        viewHolder.foodDesc.setText(store.getStore_category());

        // Return the completed view to render on screen
        return convertView;
    }
}