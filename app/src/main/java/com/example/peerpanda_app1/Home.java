package com.example.peerpanda_app1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private List<Tutor> tutolist;
    LinearLayoutManager mLayoutManagerl; //for sort
    SharedPreferences mSharedPref;

    FirebaseDatabase database;
    DatabaseReference tutor;

    TextView txtFullName, txtProgramme, txtStuid, txtPhoneno, txtSem, txtGen;

    private CheckBox chkFemale, chkMale;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Tutor, TutorViewHolder> adapter;

    String TutorId="";

    //Search Functionality
    FirebaseRecyclerAdapter<Tutor, TutorViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    MaterialSearchView searchView;

    private AppBarConfiguration mAppBarConfiguration;
    //SharedPreferences sharedPreferences = getSharedPreferences("user_data",0);
    //SharedPreferences sharedPreferences2 = getSharedPreferences("user_data",0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tutolist = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        searchView = (MaterialSearchView)findViewById(R.id.searchla);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        tutor = database.getReference("Tutor");

        mSharedPref = getSharedPreferences("SortSettings", MODE_PRIVATE);
        String mSorting = mSharedPref.getString("Sort", "Female");

        if(mSorting.equals("Female")){
            mLayoutManagerl = new LinearLayoutManager(this);
            mLayoutManagerl.setReverseLayout(true);
            mLayoutManagerl.setStackFromEnd(true);
        }
        else if(mSorting.equals("Male")){
            mLayoutManagerl = new LinearLayoutManager(this);
            mLayoutManagerl.setReverseLayout(false);
            mLayoutManagerl.setStackFromEnd(false);
        }
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
        txtPhoneno = (TextView)headerView.findViewById(R.id.phoneno);
        txtPhoneno.setText(Common.currentUser.getPhoneNo());
        txtSem = (TextView)headerView.findViewById(R.id.sem);
        txtSem.setText(Common.currentUser.getSem());
        txtGen = (TextView)headerView.findViewById(R.id.gen);
        txtGen.setText(Common.currentUser.getGen());

        //load menu
        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        //recycler_menu.setLayoutManager(layoutManager);
        mLayoutManagerl = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(mLayoutManagerl);

        loadMenu();

        //CHECKBOX
        //chkFemale = (CheckBox)findViewById(R.id.chkFemale);
        //chkMale = (CheckBox)findViewById(R.id.chkMale);
        //addListenerOnChkIos();
        //addListenerOnButton();

        //Search
        /*searchView = (MaterialSearchView)findViewById(R.id.searchla);
        searchView.setHint("Enter Course Code");
        //searchView.setSpeechMode(false);
        recycler_menu.setAdapter(searchAdapter);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                searchView = (MaterialSearchView)findViewById(R.id.searchla);
                recycler_menu.setAdapter(searchAdapter);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    List<String> lstFound = new ArrayList<String>();
                    for(String item:suggestList){
                        if(item.contains(newText))
                            lstFound.add(item);
                    }
                }
                else{

                }
                return true;
            }
        });

         */
        //loadSuggest();


        /*materialSearchBar.setLastSuggestions(suggestList);
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
                    recycler_menu.setAdapter(searchAdapter);
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

         */
    }



    private void loadSuggest() {
        tutor.orderByChild("tutorID").equalTo(TutorId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Tutor item = postSnapshot.getValue(Tutor.class);
                            suggestList.add(item.getName()); //add name of tutor to suggest list
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

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

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort) {
            //display alert to choose sorting
            showSortDialog();
            return true;
        }if(id == R.id.action_loc){
            Intent loc = new Intent(Home.this, MapsActivity.class);
            startActivity(loc);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private void showSortDialog() {
        //option to display
        String[] sortOptions = {"Female","Male"};
        //create alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by")
                .setIcon(R.drawable.sort)
                .setItems(sortOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         //"which" = argument of index position of selected item
                        //0 means "Newest" mad 1 means "Oldest"
                        if(which==0){
                            //Sort by newest
                            Query female= FirebaseDatabase.getInstance().getReference("Tutor")
                                    .child("tutorID").orderByChild("gender")
                                    .equalTo("female");
                            female.addListenerForSingleValueEvent(valueEventListener);
                            //SharedPreferences.Editor editor = mSharedPref.edit();
                            //editor.putString("Sort", "newest"); //sort is key & newest is value
                            //editor.apply(); //apply/save value in shared pref
                            //recreate();
                        } else if (which==1){
                            //sort by older
                            Query female= FirebaseDatabase.getInstance().getReference("Tutor")
                                    .child("stuID").orderByChild("gender")
                                    .equalTo("male");
                            female.addListenerForSingleValueEvent(valueEventListener);
                            //SharedPreferences.Editor editor = mSharedPref.edit();
                            //editor.putString("Sort", "oldest"); //sort is key & newest is value
                            //editor.apply(); //apply/save value in shared pref
                            //recreate();
                        }
                    }
                });
        builder.show();

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            tutolist.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tutor tutor = snapshot.getValue(Tutor.class);
                    tutolist.add(tutor);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {
            Intent home = new Intent(Home.this, Home.class);
            startActivity(home);
        } else if (id == R.id.nav_Booked) {
            Intent stat = new Intent(Home.this, BookingStatus.class);
            startActivity(stat);
        } else if (id == R.id.nav_BeTutor) {
            Intent beTutor = new Intent(Home.this, RequestTutor.class);
            startActivity(beTutor);
        } else if (id == R.id.nav_Tutoring) {
            Intent Tutoring = new Intent(Home.this, Tutoring.class);
            startActivity(Tutoring);
        } else if (id == R.id.nav_Exit) {
            Intent SignIn = new Intent(Home.this, MainActivity.class);
            SignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(SignIn);
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
                Picasso.with(getBaseContext()).load(R.drawable.noprofile)
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


    public void addListenerOnChkIos() {

        chkFemale.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(Home.this,
                            "Bro, try Android :)", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    public void addListenerOnButton() {

        chkFemale.setOnClickListener(new View.OnClickListener() {
            //Run when chk is clicked
            @Override
            public void onClick(View v) {

                StringBuffer result = new StringBuffer();
                result.append("IPhone check : ").append(chkFemale.isChecked());
                result.append("\nAndroid check : ").append(chkMale.isChecked());

                Toast.makeText(Home.this, result.toString(),
                        Toast.LENGTH_LONG).show();

            }
        });

    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Tutor, TutorViewHolder>(
                Tutor.class,
                R.layout.tutor_item,
                TutorViewHolder.class,
                tutor.orderByChild("tutorID").equalTo(TutorId)
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

}
