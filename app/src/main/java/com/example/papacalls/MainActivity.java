package com.example.papacalls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText emailinput,passinput;

    Button loinbutton,createbutton;
    FirebaseAuth auth;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cuser = FirebaseAuth.getInstance().getCurrentUser();
        if(cuser != null) {
            startActivity(new Intent(MainActivity.this,FindContact.class));
        finish();
        }
        }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        emailinput = findViewById(R.id.emailinput);
        passinput = findViewById(R.id.passinput);
        loinbutton = findViewById(R.id.loinbutton);
        createbutton = findViewById(R.id.createbutton);


        loinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass;
                email = emailinput.getText().toString();
                pass = passinput.getText().toString();

            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful())
                   {
                       Toast.makeText(MainActivity.this, "You are logged in",
                               Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(MainActivity.this,FindContact.class));

                   }
                   else
                   {
                       Toast.makeText(MainActivity.this, "Login Failed",
                               Toast.LENGTH_SHORT).show();
                   }
                }
            });
            }
        });
        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });









    }
}