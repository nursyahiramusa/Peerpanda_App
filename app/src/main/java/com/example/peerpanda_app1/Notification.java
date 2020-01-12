package com.example.peerpanda_app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peerpanda_app1.Common.Common;
import com.example.peerpanda_app1.Interface.OnItemClickListener;
import com.example.peerpanda_app1.Model.Booking;
import com.example.peerpanda_app1.ViewHolder.NotificationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Notification extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference notificationlist;
    DatabaseReference booking, reff;

    Booking currentBook;
    Button btnView;
    TextView tutor_name, coursecode, datetime, location,total_pay, stuID, tutorID, status;
    //SharedPreferences sharedPreferences = getSharedPreferences("user_data",0);
    //String stuid= sharedPreferences.getString("stud_id","");
    String stuid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifiction_layout);

        //Firebase
        database = FirebaseDatabase.getInstance();
        //notificationlist = database.getReference("Booking");
        booking = database.getReference("Booking");

        coursecode = (TextView)findViewById(R.id.coursecode);
        stuID = (TextView)findViewById(R.id.edtStuID);
        status = (TextView)findViewById(R.id.status);
        datetime = (TextView)findViewById(R.id.datetime);
        location = (TextView)findViewById(R.id.location);
        total_pay = (TextView)findViewById(R.id.total_pay);


        //SharedPreferences sharedPreferences = getSharedPreferences("user_data",0);
        //Toast.makeText(Notification.this, sharedPreferences.getString("stud_id",""),Toast.LENGTH_SHORT).show();

        btnView = (Button)findViewById(R.id.btnCancel);
        btnView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                booking.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String cc = dataSnapshot.child("coursecode").getValue().toString();
                        String stat = dataSnapshot.child("status").getValue().toString();
                        String stu = dataSnapshot.child("stuID").getValue().toString();
                        String dt = dataSnapshot.child("datetime").getValue().toString();
                        String loc = dataSnapshot.child("location").getValue().toString();
                        String ttp = dataSnapshot.child("total_pay").getValue().toString();
                        coursecode.setText(cc);
                        stuID.setText(stu);
                        status.setText(stat);
                        datetime.setText(dt);
                        location.setText(loc);
                        total_pay.setText(ttp);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        //Get tutor id from Intent
        if(getIntent() !=null)
        {
            stuid = getIntent().getStringExtra("stuID");
        }
        if(stuid != null){
            getDetailsBooking(Common.currentUser.getStuID());
        }


    }
    private void getDetailsBooking(final String stuid) {
        booking.child(stuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentBook = dataSnapshot.getValue(Booking.class);

                //set image
                //Picasso.with(getBaseContext()).load(currentTutor.getTutorImage()).into(tutor_image);
                //coursecode = dataSnapshot.child("coursecode").getValue().toString();
                coursecode.setText(Common.currentBooking.getCoursecode());
                stuID.setText(Common.currentBooking.getStuID());
                status.setText(Common.currentBooking.getStatus());
                datetime.setText(Common.currentBooking.getDatetime());
                location.setText(Common.currentBooking.getLocation());
                total_pay.setText(Common.currentBooking.getTotal_pay());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String ConvertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "Canceled";
        else
            return "Completed";

    }

    private void getStudentNoti(final String stuId) {
        booking.child(stuId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentBook = dataSnapshot.getValue(Booking.class);

                datetime.setText(currentBook.getDatetime());
                location.setText(currentBook.getLocation());
                total_pay.setText(currentBook.getTotal_pay());
                coursecode.setText(currentBook.getCoursecode());
                stuID.setText(currentBook.getStuID());
                tutorID.setText(currentBook.getTutorID());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
