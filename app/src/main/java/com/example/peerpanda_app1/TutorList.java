package com.example.peerpanda_app1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peerpanda_app1.Interface.OnItemClickListener;
import com.example.peerpanda_app1.Model.Rating;
import com.example.peerpanda_app1.Model.Tutor;
import com.example.peerpanda_app1.ViewHolder.TutorViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TutorList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference tutorList, ratingTbl;
    Tutor currentTutor;

    TextView tutor_name, tutor_image, courseTeach;
    RatingBar ratingBar;

    String TutorId="";

    FirebaseRecyclerAdapter<Tutor, TutorViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        tutorList = database.getReference("Tutor");

        ratingTbl = database.getReference("Rating");
        ratingBar = findViewById(R.id.ratingBar);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_tutor);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get Intent
        if(getIntent() !=null)
            TutorId = getIntent().getStringExtra("TutorId");
        if(!TutorId.isEmpty() && TutorId != null){
            loadListTutor(TutorId);
            getRatingTutor(TutorId);
            //Float rtg = ratingBar.getRating();
            //ratingBar.setRating(rtg);
        }
    }
    private void getRatingTutor(String tutorId) {
        com.google.firebase.database.Query tutorRating = ratingTbl.orderByChild("tutorID").equalTo(tutorId);

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
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void loadListTutor(String tutorId) {
        Query query = tutorList.orderByChild("courseTeach");
        adapter = new FirebaseRecyclerAdapter<Tutor, TutorViewHolder>(Tutor.class,
                R.layout.tutor_item,
                TutorViewHolder.class,
                //tutorList.orderByChild("courseTeach").equalTo(courseTeach)
                query
                ) {
            @Override
            protected void populateViewHolder(TutorViewHolder viewHolder, Tutor model, int position) {
                viewHolder.tutor_name.setText(model.getName());
                viewHolder.courseTeach.setText(model.getCourseTeach());
                //Picasso.with(getBaseContext()).load(model.getTutorImage())
                //        .into(viewHolder.tutor_image);
                Picasso.with(getBaseContext()).load(R.drawable.noprofile)
                        .into(viewHolder.tutor_image);
                Float rtg = ratingBar.getRating();
                ratingBar.setRating(rtg);
                viewHolder.ratingBar.setRating(rtg);

                final Tutor local = model;
                viewHolder.setItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent tutorDetail = new Intent(TutorList.this, TutorDetail.class);
                        tutorDetail.putExtra("tutorID", adapter.getRef(position).getKey());
                        startActivity(tutorDetail);
                    }
                });
            }
            };
            //set adapter
        //Log.d("TAG", "" + adapter.getItemCount());
            recyclerView.setAdapter(adapter);

        }
    }
