package com.example.peerpanda_app1;

import android.app.ProgressDialog;
import android.content.Intent;
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

    private EditText tutor_name, tutor_stuid, tutor_courseTeach, tutor_CourseGrade, tutor_SemTaken ;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_tutor);

        //link to Home by req button
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent Home = new Intent(RequestTutor.this, Home.class);
                startActivity(Home);
            }
        });


        tutor_name = (EditText)findViewById(R.id.tutor_name);
        tutor_stuid = (EditText)findViewById(R.id.tutor_stuid);
        tutor_courseTeach = (EditText)findViewById(R.id.tutor_courseTeach);
        tutor_CourseGrade = (EditText)findViewById(R.id.tutor_CourseGrade);
        tutor_SemTaken = (EditText)findViewById(R.id.tutor_SemTaken);

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
                        //check if user already exist
                        if(dataSnapshot.child(tutor_stuid.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(RequestTutor.this, "Student ID already requested",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mDialog.dismiss();

                            Tutor_Request reqTutor = new Tutor_Request(
                                    tutor_stuid.getText().toString(),
                                    tutor_courseTeach.getText().toString(),
                                    tutor_CourseGrade.getText().toString(),
                                    tutor_name.getText().toString(),
                                    tutor_SemTaken.getText().toString());

                            req.child(tutor_stuid.getText().toString()).setValue(reqTutor);
                            Toast.makeText(RequestTutor.this, "Request has been submit!",Toast.LENGTH_SHORT).show();

                            //To Home page
                            Intent signIn = new Intent(RequestTutor.this, Home.class);
                            startActivity(signIn);
                            Common.currentTutor = reqTutor;
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }


}
