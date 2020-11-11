package com.example.festivalawardtracker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final int RC_SIGN_IN = 123;

    // FRAGMENT HOME RECYCLERVIEW variables
    private RecyclerView recyclerView;
    studentAdapter mAdapter;
    DatabaseReference database;
//    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button save = (Button) findViewById(R.id.button2);


        //TODO: Floating action bar to new_student_fragment
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ViewPager viewPager = (ViewPager) findViewById(R.id.nav_new_student);
//                startActivity(new Intent(MainActivity.this, DatabaseTest.class));
//            }
//        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        // https://developer.android.com/guide/navigation/navigation-navigate?authuser=1
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // TODO: FRAGMENT HOME RECYCLERVIEW
//        StudentDisplay studentDisplay = new StudentDisplay();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.nav_host_fragment, studentDisplay);
//        fragmentTransaction.commit();
//        StudentDisplay sd = new DatabaseViewHolder();
//        Thread t1 = new Thread(sd,"students");
//        t1.start();
//        recyclerView = (RecyclerView) findViewById(R.id.testRecycle);
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);
//        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(this);
////        recyclerView.setLayoutManager(layoutManager);
//        database = FirebaseDatabase.getInstance().getReference();
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        FirebaseRecyclerOptions<studentDatabase> options
//                = new FirebaseRecyclerOptions.Builder<studentDatabase>().setQuery(database,studentDatabase.class).build();
//        mAdapter = new studentAdapter(options);
//
//        recyclerView.setAdapter(mAdapter);
        // TODO: specify an adapter (see also next example) using information from the links
//        mAdapter = new MyAdapter(myDataset);
        /*
          https://medium.com/@relferreira/goodbye-listview-recyclerview-f83dc1133850
          https://developer.android.com/guide/topics/ui/layout/recyclerview?authuser=1
          */
//        recyclerView.setAdapter(mAdapter);
    }


//      TODO: delete this if new implementation works.

//    @Override
//    protected  void onStart() {
//        super.onStart();
//        FirebaseRecyclerAdapter<studentDatabase,DatabaseViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerOptions<studentDatabase, DatabaseViewHolder>
//                (studentDatabase.class, R.layout.fragment_home_recyclerview_row_studentminiawards, DatabaseViewHolder.class,database) {
//
//
//            @Override
//            protected void onBindViewHolder(DatabaseViewHolder holder, int position,studentDatabase model) {
//                holder.setName(model.getFname() + model.getLname());
//                holder.setGender(model.getGender());
//                holder.setBirthday(model.getBdate());
//            }
//
//        };
//
//        recyclerView.setAdapter(firebaseRecyclerAdapter);
//        firebaseRecyclerAdapter.startListening();
//
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void createSignInIntent(View view) {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
    // [END auth_fui_result]

    public void signOut(View view) {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_signout]
    }

    public void questionIsItClickable(View view) {
        Toast.makeText(this, "It is toasting, yeah.", Toast.LENGTH_SHORT).show();
    }

// TODO: Works Here but I think it should have its own class?

    public void saveStudent(View view) {
        Toast.makeText(this, "Student Saved", Toast.LENGTH_SHORT).show();

        database = FirebaseDatabase.getInstance().getReference("student");
        EditText editFirstName = (EditText) findViewById(R.id.editTextStudentName);
        EditText editLastName = (EditText) findViewById(R.id.editTextStudentLastName);
        EditText editBirthDate = (EditText) findViewById(R.id.editTextBirthDate);
        Spinner editGender = (Spinner) findViewById(R.id.spinnerGender);
        EditText editPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        Spinner editInstruments = (Spinner) findViewById(R.id.spinnerInstruments);
        EditText editTeacher = (EditText) findViewById(R.id.editTextTeachersName);

        String FirstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String birthDate = editBirthDate.getText().toString().trim();
        String gender = editGender.getSelectedItem().toString();
        String phoneNumber = editPhoneNumber.getText().toString().trim();
        String instrument = editInstruments.getSelectedItem().toString();
        String teacher = editTeacher.getText().toString().trim();

        studentDatabase student = new studentDatabase(FirstName, lastName, birthDate, gender, phoneNumber, instrument, teacher);
        String id = database.push().getKey();

        database.child(id).setValue(student);


    }


}