package com.example.papacalls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.papacalls.data.Users;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    FirebaseAuth auth;
    EditText emailinput,passinput,fname;

    Button loinbutton,createbutton;

    private StorageReference userProfile;



    private DatabaseReference userref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userref = FirebaseDatabase.getInstance().getReference().child("Users");



        auth = FirebaseAuth.getInstance();

        emailinput = findViewById(R.id.emailinput);
        fname = findViewById(R.id.fname);
        passinput = findViewById(R.id.passinput);
        loinbutton = findViewById(R.id.loinbutton);
        createbutton = findViewById(R.id.createbutton);


        createbutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {



                 String email,pass,name;
                 email = emailinput.getText().toString();
                 pass = passinput.getText().toString();
                 name = fname.getText().toString();
                final Users Users = new Users();
                 Users.setEmail(email);
                 Users.setName(name);
                 Users.setPass(pass);

                 auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {

                             Toast.makeText(SignUp.this, "Account is Created",
                                     Toast.LENGTH_SHORT).show();
                             saveUserData();
                         }
                     else {Toast.makeText(SignUp.this, "Error Occured In Creating Account",
                                 Toast.LENGTH_SHORT).show();
                                                 }
                     }

                 });
             }
         });

        }
        private void saveUserData()
    {
        final String getUsername = fname.getText().toString();
        final String getemail = emailinput.getText().toString();
        final String getpass = passinput.getText().toString();

        HashMap<String, Object> profilemap = new HashMap<>();
        profilemap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        profilemap.put("name",getUsername );
        profilemap.put("email",getemail );
        profilemap.put("pass",getpass );

        userref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(profilemap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(SignUp.this, MainActivity.class));
                    finish();
                }
            }
        });

    }

    }
