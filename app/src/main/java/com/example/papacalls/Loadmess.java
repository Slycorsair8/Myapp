package com.example.papacalls;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Loadmess extends RecyclerView.Adapter<Loadmess.MessageViewHolder>

{
    private List<Messages> userMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersref;


    public Loadmess(List<Messages> userMessagesList)
    {
        this.userMessagesList = userMessagesList;
    }



    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView senderMessageText, receiverMessageText;

        public MessageViewHolder(@NonNull View itemView)
        {
            super(itemView);

            senderMessageText = itemView.findViewById(R.id.sendermesstext);
            receiverMessageText = itemView.findViewById(R.id.recmesstext);
        }
    }




    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custmesslayout, viewGroup, false);

        mAuth = FirebaseAuth.getInstance();

        return new MessageViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder messageViewHolder, int i)
    {
        String messageSenderId = mAuth.getCurrentUser().getUid();
        Messages messages = userMessagesList.get(i);

        String fromUserID = messages.getFrom();
        String fromMessageType = messages.getType();

        usersref = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserID);








        if (fromMessageType.equals("text"))
        {

            if (fromUserID.equals(messageSenderId))
            {
                messageViewHolder.senderMessageText.setBackgroundResource(R.drawable.sendermess);
                messageViewHolder.receiverMessageText.setVisibility(View.INVISIBLE);

                messageViewHolder.senderMessageText.setTextColor(Color.BLACK);
                messageViewHolder.senderMessageText.setText(messages.getMessage());
            }
            else
            {

                messageViewHolder.senderMessageText.setVisibility(View.INVISIBLE);
                messageViewHolder.receiverMessageText.setVisibility(View.VISIBLE);
                messageViewHolder.receiverMessageText.setBackgroundResource(R.drawable.recievermess);
                messageViewHolder.receiverMessageText.setTextColor(Color.BLACK);
                messageViewHolder.receiverMessageText.setText(messages.getMessage());
            }
        }
    }




    @Override
    public int getItemCount()
    {
        return userMessagesList.size();
    }

}