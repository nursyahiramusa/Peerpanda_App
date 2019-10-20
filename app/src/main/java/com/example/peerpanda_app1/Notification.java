package com.example.peerpanda_app1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peerpanda_app1.Interface.OnItemClickListener;
import com.example.peerpanda_app1.Model.Booking;
import com.example.peerpanda_app1.Model.Tutor;
import com.example.peerpanda_app1.ViewHolder.NotificationViewHolder;
import com.example.peerpanda_app1.ViewHolder.TutorViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Notification extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference notificationlist;

    TextView tutor_name, coursecode, datetime, location;

    String BookId="1";

    FirebaseRecyclerAdapter<Booking, NotificationViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //Firebase
        database = FirebaseDatabase.getInstance();
        notificationlist = database.getReference("Booking");
        recyclerView = (RecyclerView)findViewById(R.id.recycler_noti);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get Intent
        if(getIntent() !=null)
            BookId = getIntent().getStringExtra("BookId");
        if(!BookId.isEmpty() && BookId != null){
            loadListNotification(BookId);
            Toast.makeText(Notification.this, "Hell'o FYP",Toast.LENGTH_SHORT).show();
        }


    }

    private void loadListNotification(String BookId) {
        adapter = new FirebaseRecyclerAdapter<Booking, NotificationViewHolder>(Booking.class,
                R.layout.notification_item,
                NotificationViewHolder.class,
                notificationlist.orderByChild("bookID").equalTo(BookId)
        ) {
            @Override
            protected void populateViewHolder(NotificationViewHolder viewHolder, Booking model, int position) {
                viewHolder.tutor_name.setText(model.getTutorID());
                viewHolder.coursecode.setText(model.getCoursecode());
                viewHolder.datetime.setText(model.getDatetime());
                viewHolder.location.setText(model.getLocation());
                final Booking local = model;
                viewHolder.setItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent noti = new Intent(Notification.this, Notification.class);
                        noti.putExtra("bookID", adapter.getRef(position).getKey());
                        startActivity(noti);
                    }
                });
            }
        };
        //set adapter
        //Log.d("TAG", "" + adapter.getItemCount());
        recyclerView.setAdapter(adapter);

    }


}
