package com.example.peerpanda_app1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.peerpanda_app1.Model.Tutor;
import com.example.peerpanda_app1.Model.Tutor_Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.rengwuxian.materialedittext.MaterialEditText;



public class RegisterTutor extends AppCompatActivity {
/*
    private EditText  edtName, edtStuID, edtCourseCode, edtCourseGrade, edtSemTaken;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_tutor);

        //link to HomeNavActivity by signUp button
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent Home = new Intent(RegisterTutor.this, HomeNavActivity.class);
                startActivity(Home);
            }
        });

        edtName = (EditText)findViewById(R.id.edtName);
        edtStuID = (EditText)findViewById(R.id.edtStuID);
        edtCourseCode = (EditText)findViewById(R.id.edtCourseCode);
        edtCourseGrade = (EditText)findViewById(R.id.edtCourseGrade);
        edtSemTaken = (EditText)findViewById(R.id.edtSemTaken);

        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_tutor_req = database.getReference("TutorRequest");

        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final ProgressDialog mDialog = new ProgressDialog(RegisterTutor.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_tutor_req.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user already exist
                        if(dataSnapshot.child(edtStuID.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(RegisterTutor.this, "Student ID already requested",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mDialog.dismiss();

                            Tutor_Request tutor = new Tutor_Request(edtStuID.getText().toString(),
                                    edtCourseCode.getText().toString(),
                                    edtCourseGrade.getText().toString(),
                                    edtName.getText().toString(),
                                    edtSemTaken.getText().toString());

                            table_tutor_req.child(edtStuID.getText().toString()).setValue(tutor);
                            Toast.makeText(RegisterTutor.this, "Submission successful!/nWait for request result",Toast.LENGTH_LONG).show();

                            //To Home page
                            //Intent back = new Intent(RegisterTutor.this, ProfileFragment.class);
                            //startActivity(back);
                            //finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }

 */

}
