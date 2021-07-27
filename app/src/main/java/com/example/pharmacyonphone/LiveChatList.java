package com.example.pharmacyonphone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class LiveChatList extends AppCompatActivity implements View.OnClickListener {
    private CardView users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat_list);
        users = (CardView) findViewById(R.id.user);

        // adding click listener
        users.setOnClickListener((this));

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.user:
                i = new Intent(this, LiveChat.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }


}
