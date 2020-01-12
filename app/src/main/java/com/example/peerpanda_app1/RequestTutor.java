package com.example.peerpanda_app1;

import android.animation.PropertyValuesHolder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.peerpanda_app1.Common.Common;
import com.example.peerpanda_app1.Model.Tutor_Request;
import com.example.peerpanda_app1.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestTutor extends AppCompatActivity{

    public EditText name, campus, course, sem, gender, phoneno, courseTeach, courseGrade, coursename, price, tutorID ;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_tutor);

        name = (EditText)findViewById(R.id.name);
        campus = (EditText)findViewById(R.id.campus);
        course = (EditText)findViewById(R.id.course);
        sem = (EditText)findViewById(R.id.sem);
        gender = (EditText)findViewById(R.id.gender);
        phoneno = (EditText)findViewById(R.id.phoneno);
        courseTeach = (EditText)findViewById(R.id.courseTch);
        courseGrade = (EditText)findViewById(R.id.courseGrade);
        coursename = (EditText)findViewById(R.id.coursename);
        price = (EditText)findViewById(R.id.price);
        //tutorID = (EditText)findViewById(R.id.price);

        //set default USER INFO
        name.setText(Common.currentUser.getName());
        course.setText(Common.currentUser.getCourse());
        sem.setText(Common.currentUser.getSem());
        //campus.setText(Common.currentUser.getCampus());
        gender.setText(Common.currentUser.getGen());
        phoneno.setText(Common.currentUser.getPhoneNo());

        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference req = database.getReference("TutorRequest");

        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final ProgressDialog mDialog = new ProgressDialog(RequestTutor.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                req.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        SharedPreferences sharedPreferences = getSharedPreferences("user_data",0);
                        //Toast.makeText(RequestTutor.this, sharedPreferences.getString("stud_id",""),Toast.LENGTH_SHORT).show();

                        //check if user already exist
                        if(dataSnapshot.child( sharedPreferences.getString("stud_id","")).exists()){
                            mDialog.dismiss();
                            Toast.makeText(RequestTutor.this, "Request Unsuccessful\nYou have already requested",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            mDialog.dismiss();

                            Tutor_Request reqTutor = new Tutor_Request(

                                    course.getText().toString(),
                                    courseTeach.getText().toString(),
                                    name.getText().toString(),
                                    sem.getText().toString(),
                                    sharedPreferences.getString("stud_id",""),
                                    sharedPreferences.getString("stud_id",""),
                                    gender.getText().toString(),
                                    price.getText().toString(),
                                    campus.getText().toString(),
                                    phoneno.getText().toString(),
                                    coursename.getText().toString(),
                                    courseGrade.getText().toString()
                            );
                                    //12

                            req.child(sharedPreferences.getString("stud_id","")).setValue(reqTutor);
                            Toast.makeText(RequestTutor.this, "Request has been submit!",Toast.LENGTH_SHORT).show();

                            //To Home page
                            Intent signIn = new Intent(RequestTutor.this, Home.class);
                            startActivity(signIn);
                            Common.currentTutor = reqTutor;
                            finish();

                        }
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }


}
