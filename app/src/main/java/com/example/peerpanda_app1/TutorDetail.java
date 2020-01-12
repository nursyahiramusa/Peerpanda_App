package com.example.peerpanda_app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.peerpanda_app1.Common.Common;
import com.example.peerpanda_app1.Database.Database;
import com.example.peerpanda_app1.Model.Booking;
import com.example.peerpanda_app1.Model.Rating;
import com.example.peerpanda_app1.Model.Tutor;
import com.example.peerpanda_app1.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;
import java.util.Calendar;


public class TutorDetail extends AppCompatActivity implements RatingDialogListener {

    TextView tutor_name, tutor_price, tutor_sem, tutor_programme, tutor_gender, course_code, course_name, total_pay, ratecount;
    TextView bookID, datetime, location , coursecode, stuID, tutorID, phoneno,ratingValue;
    ImageView tutor_image;
    ImageButton btnLoc;
    FloatingActionButton btnRating;
    Button btnBookNow;
    RatingBar ratingBar;
    String tutorId="";
    String status = "0";

    FirebaseDatabase database;
    DatabaseReference tutors;
    DatabaseReference ratingTbl;
    DatabaseReference bookingTbl, userTbl;

    Tutor currentTutor;
    User currentUser;



    public static EditText DateEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_detail2);

        //Firebase
        database = FirebaseDatabase.getInstance();
        tutors = database.getReference("Tutor");
        ratingTbl = database.getReference("Rating");
        bookingTbl = database.getReference("Booking");
        userTbl = database.getReference("User");

        //FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //String uid = currentUser.getUid();

        tutor_name = (TextView)findViewById(R.id.tutor_name);
        tutor_price = (TextView)findViewById(R.id.tutor_price);
        tutor_sem = (TextView)findViewById(R.id.tutor_sem);
        tutor_programme = (TextView)findViewById(R.id.tutor_programme);
        tutor_gender = (TextView)findViewById(R.id.tutor_gender);
        course_code = (TextView)findViewById(R.id.course_code);
        course_name = (TextView)findViewById(R.id.course_name);
        tutor_image = (ImageView)findViewById(R.id.img_tutor);
        total_pay = (TextView)findViewById(R.id.total_pay);

        datetime = (TextView)findViewById(R.id.datetime);
        datetime.setInputType(0);
        location = (TextView)findViewById(R.id.location);
        total_pay = (TextView)findViewById(R.id.total_pay);
        coursecode = (TextView)findViewById(R.id.course_code);
        ratecount = findViewById(R.id.ratecount);
        stuID = (TextView)findViewById(R.id.edtStuID);
        tutor_name = findViewById(R.id.tutor_name);
        //phoneno = findViewById(R.id.phoneno);

        tutorID = (TextView)findViewById(R.id.tutor_stuid);

        ratingValue = (TextView)findViewById(R.id.ratecount);

        //Get tutor id from Intent
        if(getIntent() !=null)
            tutorId = getIntent().getStringExtra("TutorId");
        if(tutorId != null){
                getDetailsTutor(tutorId);
                getRatingTutor(tutorId);
        }

        //rating
        btnRating = (FloatingActionButton)findViewById(R.id.btn_rating);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTheme(R.style.RatingDialogStyle);
                showRatingDialog();
            }

        });

        //ImageButton Location
        btnLoc = (ImageButton)findViewById(R.id.btnLoc);
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startLoc = new Intent(TutorDetail.this, MapsActivity.class);
                startActivity(startLoc);
            }
        });

        //booking
        btnBookNow = (Button)findViewById(R.id.btnBookNow);
        btnBookNow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                bookingTbl.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        SharedPreferences sharedPreferences = getSharedPreferences("user_data",0);
                        SharedPreferences sharedPreferences2 = getSharedPreferences("user_data",0);
                        Toast.makeText(TutorDetail.this, sharedPreferences.getString("stud_id",""),Toast.LENGTH_SHORT).show();
                            Booking book = new Booking(
                                    Common.currentUser.getPhoneNo(),
                                    datetime.getText().toString(),
                                    location.getText().toString(),
                                    status,
                                    total_pay.getText().toString(),
                                    course_code.getText().toString(),
                                    sharedPreferences.getString("stud_id",""),
                                    tutorID.getText().toString(),
                                    tutor_name.getText().toString()
                            );

                            bookingTbl.child(sharedPreferences.getString("stud_id", "")).setValue(book);
                            Toast.makeText(TutorDetail.this, "Booking submitted!",Toast.LENGTH_SHORT).show();
                            finish();
                            //Intent detail = new Intent(TutorDetail.this, TutorDetail.class);
                            //startActivity(detail);
                            finish();
                        }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        //date and time
        DateEdit = (EditText) findViewById(R.id.datetime);
        DateEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
                showDatePickerDialog(v);

            }
        });

    }

    private void getRatingTutor(String tutorId) {
        com.google.firebase.database.Query tutorRating = ratingTbl.orderByChild("stuID").equalTo(tutorId);

        tutorRating.addValueEventListener(new ValueEventListener() {
            int count=0, sum=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Rating item = postSnapshot.getValue(Rating.class);
                    sum+=Integer.parseInt(item.getRatingValue());
                    count++;
                }
                if(count != 0){
                    float average = sum/count;
                    ratingBar.setRating(average);
                    ratingValue.setText(Float.toString(average));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setNegativeButtonText("Cancel")
                .setNeutralButtonText("Later")
                .setPositiveButtonText("Submit")
                .setTitleTextColor(R.color.black)
                .setCommentTextColor(R.color.black)
                .setStarColor(R.color.yello)
                .setNoteDescriptionTextColor(R.color.black)
                .setTitleTextColor(R.color.black)
                .setDescriptionTextColor(R.color.black)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.black)
                .setCommentTextColor(R.color.black)
                .setCommentBackgroundColor(R.color.lightgray)
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent!"))
                .setPositiveButtonText("sub")
                .setDefaultRating(1)
                .setTitle("Rate this Tutor")
                .setDescription("Please select some stars and give your feedback")
                .setCommentInputEnabled(true)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .setCancelable(false)
                .setCanceledOnTouchOutside(true)
                .create(TutorDetail.this)
                .show();

    }

    private void getDetailsTutor(final String tutorId) {
        tutors.child(tutorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentTutor = dataSnapshot.getValue(Tutor.class);

                //set image
                //Picasso.with(getBaseContext()).load(model.getTutorImage())
                //        .into(viewHolder.tutor_image);
                Picasso.with(getBaseContext()).load(R.drawable.noprofile)
                        .into(tutor_image);

                tutor_name.setText(currentTutor.getName());
                tutorID.setText(currentTutor.getTutorID());
                tutor_price.setText(currentTutor.getPrice());
                tutor_sem.setText(currentTutor.getSem());
                tutor_programme.setText(currentTutor.getCourse());
                tutor_gender.setText(currentTutor.getGender());
                course_code.setText(currentTutor.getCourseTeach());
                course_name.setText(currentTutor.getCoursename());
                total_pay.setText(currentTutor.getPrice());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {
    }

    @Override
    public void onNeutralButtonClicked() {
    }

    @Override
    public void onPositiveButtonClicked(int value, String comments) {
        //get Rating and upload to firebase
        final Rating rating = new Rating(Common.currentUser.getStuID(),
                tutorId,
                String.valueOf(value),
                comments);
        ratingTbl.child(Common.currentUser.getStuID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Common.currentUser.getStuID()).exists()) {
                    //remove old value
                    ratingTbl.child(Common.currentUser.getStuID()).removeValue();
                    //update value
                    ratingTbl.child(Common.currentUser.getStuID()).setValue(rating);
                }
                else{
                    //update new value
                    ratingTbl.child(Common.currentUser.getStuID()).setValue(rating);
                }
                Toast.makeText(TutorDetail.this, "Thank you for your Rate", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new TutorDetail.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            DateEdit.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TutorDetail.TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            DateEdit.setText(DateEdit.getText() + " -" + hourOfDay + ":"	+ minute);
        }
    }
}