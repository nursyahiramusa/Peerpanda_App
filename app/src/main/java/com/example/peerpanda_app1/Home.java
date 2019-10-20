package com.example.peerpanda_app1;

import android.content.Intent;
import android.os.Bundle;

import com.example.peerpanda_app1.Common.Common;
import com.example.peerpanda_app1.Interface.OnItemClickListener;
import com.example.peerpanda_app1.Model.Tutor;
import com.example.peerpanda_app1.ViewHolder.TutorViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference tutor;

    TextView txtFullName, txtProgramme, txtStuid;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Tutor, TutorViewHolder> adapter;

    String TutorId="";

    //Search Functionality
    FirebaseRecyclerAdapter<Tutor, TutorViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        tutor = database.getReference("Tutor");

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
                Intent csrtIntent = new Intent(Home.this, Cart.class);
                startActivity(csrtIntent);
            }
        });
        */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView)headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getName());
        txtProgramme = (TextView)headerView.findViewById(R.id.txtProgramme);
        txtProgramme.setText(Common.currentUser.getCourse());
        txtStuid = (TextView)headerView.findViewById(R.id.txtStuid);
        txtStuid.setText(Common.currentUser.getStuID());

        //load menu
        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();

        //Search
        materialSearchBar = (MaterialSearchBar)findViewById(R.id.searchbar);
        materialSearchBar.setHint("Enter Course Code to Search");
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // When user type their text will change suggest list
                List<String> suggest = new ArrayList<String>();
                for(String search:suggestList)
                {
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //When search bar is close
                //restore original adapter
                if(!enabled)
                    recycler_menu.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //when search finish
                //show result of search adapter
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Tutor, TutorViewHolder>(
                Tutor.class,
                R.layout.tutor_item,
                TutorViewHolder.class,
                tutor.orderByChild("TutorId").equalTo(TutorId)
        ) {
            @Override
            protected void populateViewHolder(TutorViewHolder viewHolder, Tutor model, int i) {
                viewHolder.tutor_name.setText(model.getName());
                viewHolder.courseTeach.setText(model.getCourseTeach());
                Picasso.with(getBaseContext()).load(model.getTutorImage())
                        .into(viewHolder.tutor_image);
                final Tutor local = model;
                viewHolder.setItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent tutorDetail = new Intent(Home.this, TutorDetail.class);
                        tutorDetail.putExtra("tutorID", adapter.getRef(position).getKey());
                        startActivity(tutorDetail);
                    }
                });
            }
        };
        recycler_menu.setAdapter(searchAdapter); //set adapter for Recycler View in search result
    }

    private void loadSuggest() {
        tutor.orderByChild("TutorId").equalTo(TutorId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Tutor item = postSnapshot.getValue(Tutor.class);
                            suggestList.add(item.getName()); //add name of tutor to suggest list
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_other) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {

        } else if (id == R.id.nav_Booked) {

        } else if (id == R.id.nav_Notification) {
            Intent notification = new Intent(Home.this, Notification.class);
            startActivity(notification);
        } else if (id == R.id.nav_Profile) {
            Intent beTutor = new Intent(Home.this, RequestTutor.class);
            startActivity(beTutor);

        } else if (id == R.id.nav_Exit) {
            Intent logout = new Intent(Home.this, MainActivity.class);
            startActivity(logout);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadMenu() {
         adapter = new FirebaseRecyclerAdapter<Tutor, TutorViewHolder>(Tutor.class,R.layout.tutor_item, TutorViewHolder.class, tutor) {
            @Override
            protected void populateViewHolder(TutorViewHolder viewHolder, Tutor model, int position) {
                viewHolder.tutor_name.setText(model.getName());
                viewHolder.courseTeach.setText(model.getCourseTeach());
                Picasso.with(getBaseContext()).load(model.getTutorImage())
                        .into(viewHolder.tutor_image);
                final Tutor clickItem = model;
                viewHolder.setItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(Home.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                        Intent tutorDetail = new Intent(Home.this, TutorDetail.class);
                        tutorDetail.putExtra("TutorId",adapter.getRef(position).getKey());
                        startActivity(tutorDetail);

                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);

    }

}
