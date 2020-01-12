package com.example.peerpanda_app1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.peerpanda_app1.Common.Common;
import com.example.peerpanda_app1.Model.User;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText edtStuID, edtPass;
    Button btnSignIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //link to HomeNavActivity by signIn button
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        edtPass = (EditText)findViewById(R.id.edtPass);
        edtStuID = (EditText)findViewById(R.id.edtStuID);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        //Init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                final SharedPreferences sharedPreferences = getSharedPreferences("user_data",0);
                final SharedPreferences.Editor editor = sharedPreferences.edit();

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //check if user not exist in database
                        if(dataSnapshot.child(edtStuID.getText().toString()).exists()) {

                            //get user info
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtStuID.getText().toString()).getValue(User.class);
                            user.setStuID(edtStuID.getText().toString()); //set stuid

                            if (user.getPass().equals(edtPass.getText().toString())) {
                                editor.putString("stud_id",edtStuID.getText().toString());
                                editor.commit();
                                Toast.makeText(SignIn.this, "Sign in successfully ! \n"+sharedPreferences.getString("stud_id",""), Toast.LENGTH_SHORT).show();
                                //To Home page
                                Intent Home = new Intent(SignIn.this, Home.class);
                                startActivity(Home);
                                Common.currentUser = user;
                                finish();

                            } else {
                                Toast.makeText(SignIn.this, "Sign In failed !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
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
