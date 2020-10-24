package com.example.papacalls;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainScreen extends AppCompatActivity {

    RecyclerView contactslist;
    Button findcontact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        findcontact= findViewById(R.id.button4);
        contactslist=findViewById(R.id.contactlist);
        contactslist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        findcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this,FindContact.class));
            }
        });
    }


private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.navigation_home:
                startActivity(new Intent(MainScreen.this,MainScreen.class));
                break;

            case R.id.navigation_settings:
                startActivity(new Intent(MainScreen.this,settings.class));
                break;

            case R.id.navigation_notifications:
                startActivity(new Intent(MainScreen.this,notification.class));

                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainScreen.this,MainActivity.class));
                finish();
                break;
        }
        return true;
    }
};
}