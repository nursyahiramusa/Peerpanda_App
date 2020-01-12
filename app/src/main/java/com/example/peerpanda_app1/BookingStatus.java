package com.example.peerpanda_app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peerpanda_app1.Common.Common;
import com.example.peerpanda_app1.Interface.OnItemClickListener;
import com.example.peerpanda_app1.Model.Booking;
import com.example.peerpanda_app1.ViewHolder.BookingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    Button btnCancel;
    TextView status;

    FirebaseRecyclerAdapter<Booking, BookingViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_status);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Booking Status");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Booking");

        recyclerView = (RecyclerView)findViewById(R.id.listBooking);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        status = (TextView)findViewById(R.id.status);

        loadBooking(Common.currentUser.getStuID());

        //LayoutInflater inflater = this.getLayoutInflater();
        //View book_layout = inflater.inflate(R.layout.book_layout,null);

        //btnCancel= book_layout.findViewById(R.id.btnCancel);
        //btnCancel.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Intent v = new Intent(BookingStatus.this, BookingStatus.class);
        //        startActivity(v);
        //        ref.child(Common.currentUser.getStuID()).removeValue();
        //        finish();
        //    }
        //});
    }

    private void loadBooking(String stuID) {

        adapter = new FirebaseRecyclerAdapter<Booking, BookingViewHolder>(
                Booking.class,
                R.layout.book_layout,
                BookingViewHolder.class,
                ref.orderByChild("stuID") //.orderByChild("list")
                        .equalTo(stuID)
        ) {
            @Override
            protected void populateViewHolder(BookingViewHolder viewHolder, Booking model, final int position) {
                viewHolder.txtBookingId.setText(adapter.getRef(position).getKey());
                viewHolder.txtCoursecode.setText(model.getCoursecode());
                viewHolder.txtDatetime.setText(model.getDatetime());
                viewHolder.txtLoc.setText(model.getLocation());
                viewHolder.txtBookingStatus.setText(convertCodeStatus(model.getStatus()));
                viewHolder.txtBookingPhone.setText(model.getPhoneno());
                viewHolder.txtTotPay.setText(model.getTotal_pay());
                viewHolder.txtTutorName.setText(model.getTutor_name());

                viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(BookingStatus.this, "btnAccept clicked", Toast.LENGTH_SHORT).show();
                        //loadStatus();
                        showDeleteDialog(adapter.getRef(position).getKey());
                        adapter.notifyDataSetChanged();
                    }
                });

            }

        };

        recyclerView.setAdapter(adapter);
    }

    private void loadStatus() {
        status.setText("2");
    }

    private String convertCodeStatus(String status) {
        if(status.equals("0"))
            return "Booking in progress";
        else if(status.equals("1"))
            return "Tutor Accept the Booking";
        else if(status.equals("2"))
            return "Tutor Reject the Booking";
        else
            return "Complete";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_other) {
        //    return true;
        //}
        return super.onOptionsItemSelected(item);
    }

    //update and delete
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals(Common.CANCEL))
        {
            showDeleteDialog(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void showDeleteDialog(String key) {
        ref.child(key).removeValue();
        Toast.makeText(this, "Tutor deleted!", Toast.LENGTH_SHORT).show();
    }
}