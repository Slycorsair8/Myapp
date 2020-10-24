package com.example.papacalls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FindContact extends AppCompatActivity {


    private RecyclerView findFriendsL;
    private EditText searc;
    private String str = "";
    private DatabaseReference usersref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_contact);

        usersref = FirebaseDatabase.getInstance().getReference().child("Users");

    searc = findViewById(R.id.searctext);
    findFriendsL= findViewById(R.id.findlist);
    findFriendsL.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    searc.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(searc.getText().toString().equals(""))
        {

        }
        else
        {
           str = s.toString();
           onStart();
        }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });

    }







    @Override
    protected void onStart() {
        super.onStart();




        FirebaseRecyclerOptions<Contacts> options = null;
        if(str.equals(""))
        {
            options= new FirebaseRecyclerOptions.Builder<Contacts>()
                    .setQuery(usersref,Contacts.class).build();


        }
        else
            options = new FirebaseRecyclerOptions.Builder<Contacts>()
                    .setQuery(usersref.orderByChild("name").startAt(str).endAt(str + "\uf8ff")
                            ,Contacts.class).build();

        FirebaseRecyclerAdapter<Contacts, Findfriendview> firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<Contacts, Findfriendview>(options) {
        @Override
         protected void onBindViewHolder(@NonNull Findfriendview holder, final int position, @NonNull final Contacts model) {
            holder.username.setText(model.getName());
            holder.acceptfriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String visit_user_id = getRef(position).getKey();

                    startActivity(new Intent(FindContact.this,Main.class));

                }

            });
         }
            @NonNull
            @Override
         public Findfriendview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactdes,parent,false);
          Findfriendview viewolder = new Findfriendview(view);
          return viewolder;
         }
        };

        findFriendsL.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();


    }


    public  static class Findfriendview extends RecyclerView.ViewHolder
    {
        TextView username;
        Button acceptfriend;

        RelativeLayout cardview;

        public Findfriendview(@NonNull View itemview) {
            super(itemview);

            username = itemview.findViewById(R.id.Namecont);
            acceptfriend = itemview.findViewById(R.id.callacc);
            cardview = itemview.findViewById(R.id.Card);


        }
    }


}