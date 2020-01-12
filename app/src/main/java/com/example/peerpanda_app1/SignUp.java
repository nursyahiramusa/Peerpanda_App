package com.example.peerpanda_app1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.peerpanda_app1.Common.Common;
import com.example.peerpanda_app1.Interface.CustomOnItemSelectedListener;
import com.example.peerpanda_app1.Model.User;
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
import android.widget.Spinner;
import android.widget.Toast;
import com.rengwuxian.materialedittext.MaterialEditText;



public class SignUp extends AppCompatActivity {

    private EditText edtName, edtStuID, edtCourse, edtSem, edtPass, phoneno, gen;
    private Spinner spinner1, spinner2;
    private Button btnSignUp, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        //SignUp button
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent Home = new Intent(SignUp.this, SignIn.class);
                startActivity(Home);
            }
        });



        edtName = (EditText)findViewById(R.id.edtName);
        edtStuID = (EditText)findViewById(R.id.edtStuID);
        edtCourse = (EditText)findViewById(R.id.edtCourse);
        edtSem = (EditText)findViewById(R.id.edtSem);
        edtPass = (EditText)findViewById(R.id.edtPass);
        gen = findViewById(R.id.gender);
        phoneno = (EditText)findViewById(R.id.edtphoneno);

        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user already exist
                        if(dataSnapshot.child(edtStuID.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Student ID already registered",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mDialog.dismiss();

                            User user = new User(edtStuID.getText().toString(),
                                    edtPass.getText().toString(),
                                    edtName.getText().toString(),
                                    edtCourse.getText().toString(),
                                    edtSem.getText().toString(),
                                    spinner1.getSelectedItem().toString(),
                                    phoneno.getText().toString()
                            );

                            table_user.child(edtStuID.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Sign up successful!",Toast.LENGTH_SHORT).show();

                            //To Home page
                            Intent signIn = new Intent(SignUp.this, SignIn.class);
                            startActivity(signIn);
                            Common.currentUser = user;
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
    // add items into spinner dynamically

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
    }


}
