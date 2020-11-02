package com.example.papacalls;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsersList extends AppCompatActivity {


    private RecyclerView findFriendsL;
    private EditText searc;
    private String str = "";
    private DatabaseReference usersref;

    ImageButton logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_contact);

    usersref = FirebaseDatabase.getInstance().getReference().child("Users");
    logout = findViewById(R.id.logout);
    searc = findViewById(R.id.searctext);
    findFriendsL= findViewById(R.id.findlist);
    findFriendsL.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UsersList.this,MainActivity.class));
                finish();
            }
        });


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

            holder.messfriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String visit_user_id = getRef(position).getKey();


                    Intent intent = new Intent(UsersList.this, Chatting.class);
                    intent.putExtra("visit_user_id", visit_user_id);
                    intent.putExtra("profile_name",model.getName());
                    startActivity(intent);
                }


            });

            holder.callfriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String visit_user_id = getRef(position).getKey();



                    startActivity(new Intent(UsersList.this, CallActivity.class));
                }


            });






         }
            @NonNull
            @Override
         public Findfriendview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactdes,parent,false);
          Findfriendview viewHolder = new Findfriendview(view);
          return viewHolder;
         }
        };

        findFriendsL.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();


    }


    public  static class Findfriendview extends RecyclerView.ViewHolder
    {
        TextView username;
        ImageButton callfriend;
        ImageButton messfriend;



        RelativeLayout cardview;

        public Findfriendview(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.Namecont);
            callfriend = itemView.findViewById(R.id.callbtn);
            messfriend = itemView.findViewById(R.id.messbtn);

            cardview = itemView.findViewById(R.id.cardview);


        }
    }


}