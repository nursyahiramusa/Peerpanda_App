package com.example.peerpanda_app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnSignUp, btnSignIn;
    TextView txtSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        //txtSlogan = (TextView)findViewById(R.id.txtSlogan);
        //Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Loster.ttf");
        //txtSlogan.setTypeface(face);

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent SignUp = new Intent(MainActivity.this, SignUp.class);
                startActivity(SignUp);

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent SignIn = new Intent(MainActivity.this, SignIn.class);
                startActivity(SignIn);

            }
        });


    }
}
