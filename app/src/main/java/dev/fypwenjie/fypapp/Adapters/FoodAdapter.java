package dev.fypwenjie.fypapp.Adapters;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dev.fypwenjie.fypapp.Activities.FoodScreen;
import dev.fypwenjie.fypapp.Domain.Food;
import dev.fypwenjie.fypapp.R;
import dev.fypwenjie.fypapp.Util.ImageLoader;

/**
 * Created by VINTEDGE on 21/2/2017.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.CouponViewHolder> {

    private static LayoutInflater inflater = null;
    ArrayList<Food> foods;
    Activity activity;
    ImageLoader imageLoader;
    int resId;
    private Context context;
    private String currencyCode;

    public FoodAdapter(Context context, ArrayList<Food> foods) {
        super();
        this.foods = foods;
        this.activity = (Activity) context;
        this.context = context;
        Log.i("Size", String.valueOf(foods.size()));
        inflater = LayoutInflater.from(context);
        //uiHelper = new UiLifecycleHelper(activity, callback);
    }

    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_coupon, parent, false);
        CouponViewHolder holder = new CouponViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(CouponViewHolder holder, int position) {
        currencyCode = context.getString(R.string.currency);
        final Food food = foods.get(position);
        Log.i("Size", String.valueOf(foods.size()));
        holder.couponTitle.setText(food.getCProductDesc());
        Log.i("UserID", String.valueOf(food.getUserID()));
        Log.i("Coupon", String.valueOf(food.getCouponID()));
        Bitmap couponBmp = BitmapFactory.decodeByteArray(food.getProductimage(), 0, food.getProductimage().length);
        holder.couponImg.setImageBitmap(Bitmap.createScaledBitmap(couponBmp, 160, 140, false));
        holder.discAmt.setText(currencyCode + " " + String.format("%.2f", Double.parseDouble(food.getUnitPrice()) - Double.parseDouble(food.getDiscAmount())));
        holder.price.setText(currencyCode + " " + String.valueOf(food.getUnitPrice()));
        holder.desc.setText("Save " + String.format("%.0f", ((Double.parseDouble(food.getDiscAmount()) / Double.parseDouble(food.getUnitPrice())) * 100)) + "%");
        holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.couponLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Coupon", String.valueOf(food.getCouponID()));
                Intent i = new Intent(context, FoodScreen.class);
                i.putExtra("coupon_id", food.getCouponID());
                i.putExtra("user_id", food.getUserID());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    class CouponViewHolder extends RecyclerView.ViewHolder {
        TextView discAmt;
        TextView price;
        TextView couponTitle;
        TextView desc;
        ImageView couponImg;
        LinearLayout couponLayout;

        public CouponViewHolder(View itemView) {
            super(itemView);
            couponTitle = (TextView) itemView.findViewById(R.id.title_coupon);
            couponLayout = (LinearLayout) itemView.findViewById(R.id.item_coupon);
            desc = (TextView) itemView.findViewById(R.id.desc);
            couponImg = (ImageView) itemView.findViewById(R.id.image_coupon);
            discAmt = (TextView) itemView.findViewById(R.id.coupon_discount);
            price = (TextView) itemView.findViewById(R.id.coupon_price);

        }
    }


}
