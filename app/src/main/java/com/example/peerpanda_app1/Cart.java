package com.example.peerpanda_app1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.peerpanda_app1.Database.Database;
import com.example.peerpanda_app1.Model.Booking;
import com.example.peerpanda_app1.ViewHolder.CartAdapter;
import com.firebase.ui.database.FirebaseIndexListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference request;

    TextView txtTotalPrice;
    Button btnBookNow;

    List<Booking> cart = new ArrayList<>();

    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //firebase
        database = FirebaseDatabase.getInstance();
        request = database.getReference("Booking");

        //init
        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total_pay);
        btnBookNow = (Button)findViewById(R.id.btnBookNow);

        //list of ALL tutor in cart/ but peerpanda should only book 1 right?
        //loadListTutor();
    }

    /*private void loadListTutor() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);

        //Calculate total price
        int total = 0;
        for(Booking booking:cart)
            total+=(Integer.parseInt(booking.getTotal_pay()))*(Integer.parseInt(booking.getHour()));
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
        btnBookNow = (Button)findViewById(R.id.btnBookNow);

        btnBookNow.setOnClickListener(new View.OnClickListener(){


            //address or anything after click BUTTON BOOK NOW
            @Override
            public void onClick(View view) {

            }
        });

    }

     */

}
