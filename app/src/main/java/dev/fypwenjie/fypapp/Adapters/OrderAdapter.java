package dev.fypwenjie.fypapp.Adapters;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        holder.food_title.setText(carts.getFood_name());
        holder.food_price.setText("RM " + carts.getPrice());
        holder.food_quantity.setText(carts.getQuantity());

    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView food_title;
        TextView food_price;
        TextView food_quantity;
        LinearLayout order_wrapper;

        public OrderViewHolder(View itemView) {
            super(itemView);

            food_title = (TextView) itemView.findViewById(R.id.food_title);
            food_quantity = (TextView) itemView.findViewById(R.id.food_quantity);
            food_price = (TextView) itemView.findViewById(R.id.food_price);
        }
    }


}