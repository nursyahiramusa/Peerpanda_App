package com.example.peerpanda_app1.ViewHolder;


import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.peerpanda_app1.Interface.OnItemClickListener;
import com.example.peerpanda_app1.Model.Booking;
import com.example.peerpanda_app1.Model.Tutor;
import com.example.peerpanda_app1.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView cart_tutor_name, cart_course_code, cart_item_price;

    private OnItemClickListener itemClickListener;

    public void setCart_tutor_name(TextView cart_tutor_name) {
        this.cart_tutor_name = cart_tutor_name;
    }

    public CartViewHolder(View itemView){
        super(itemView);
        cart_tutor_name = (TextView)itemView.findViewById(R.id.cart_tutor_name);
        cart_course_code = (TextView)itemView.findViewById(R.id.cart_course_code);
        cart_item_price = (TextView)itemView.findViewById(R.id.cart_item_price);
    }

    @Override
    public void onClick(View view) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Booking> listData = new ArrayList<>();
    private Context context;

    public  CartAdapter(List<Booking> listData, Context context){
        this.listData = listData;
        this.context = context;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound("", Color.RED);

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(position).getTotal_pay()));
        holder.cart_item_price.setText(fmt.format(price));
        holder.cart_tutor_name.setText(listData.get(position).getTutorID());
        holder.cart_course_code.setText(listData.get(position).getCoursecode());
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }
}
