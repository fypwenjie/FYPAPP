package dev.fypwenjie.fypapp.Adapters;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dev.fypwenjie.fypapp.Domain.Cart;
import dev.fypwenjie.fypapp.R;

/**
 * Created by VINTEDGE on 21/2/2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private static LayoutInflater inflater = null;
    ArrayList<Cart> cart;
    Activity activity;
    private Context context;

    public OrderAdapter(Context context, ArrayList<Cart> cart) {
        super();
        this.cart = cart;
        this.activity = (Activity) context;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_order, parent, false);
        OrderViewHolder holder = new OrderViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int position) {
        final Cart carts = cart.get(position);

        holder.store_title.setText(carts.getFood_name() + " Quantity: " + carts.getQuantity());
        holder.store_category.setText("RM " + carts.getPrice());

    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView store_title;
        TextView store_category;
        LinearLayout store_container;
        ImageView store_Img;

        public OrderViewHolder(View itemView) {
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