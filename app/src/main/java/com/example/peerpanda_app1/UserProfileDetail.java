package com.example.peerpanda_app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.peerpanda_app1.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserProfileDetail extends AppCompatActivity {

    TextView Name, Course, StuID, Sem, Gen;
    Button btnTutorReq;

    String stuid="2017175569";

    FirebaseDatabase database;
    DatabaseReference user;

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile);

        btnTutorReq = (Button)findViewById(R.id.btnTutorReq);
        btnTutorReq.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent Submit = new Intent(UserProfileDetail.this, RequestTutor.class);
                startActivity(Submit);
            }
        });

        //Firebase
        database = FirebaseDatabase.getInstance();
        user = database.getReference("User");

        Name = (TextView)findViewById(R.id.Name);
        Course = (TextView)findViewById(R.id.Course);
        StuID = (TextView)findViewById(R.id.StuID);
        Sem = (TextView)findViewById(R.id.Sem);
        //Gen = (TextView)findViewById(R.id.Gen);

        //Get user id from Intent
        //if(getIntent() !=null)
        //    stuid = getIntent().getStringExtra("stuID");
        //if(!stuid.isEmpty()){
            getDetailsUser(stuid);
        //}

    }

    private void getDetailsUser(String stuid) {
        user.child(stuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                Name.setText(currentUser.getName());
                Course.setText(currentUser.getCourse());
                StuID.setText(currentUser.getStuID());
                Sem.setText(currentUser.getSem());
                //Gen.setText(currentUser.getGen());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
