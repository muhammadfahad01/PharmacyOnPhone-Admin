package com.example.pharmacyonphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class homePage extends AppCompatActivity implements View.OnClickListener {

    private CardView inventoryBT, newItemBT, ordersBT, profileBT , LivechatBT ,LogoutBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        inventoryBT = (CardView) findViewById(R.id.BTViewInventory);
        newItemBT = (CardView) findViewById(R.id.BTNewItem);
        ordersBT = (CardView) findViewById(R.id.BTViewOrders);
        profileBT = (CardView) findViewById(R.id.BTprofile);
        LivechatBT = (CardView) findViewById(R.id.BTliveChat);
        LogoutBT = (CardView) findViewById(R.id.BTlogout);

        // adding click listener
        inventoryBT.setOnClickListener(this);
        newItemBT.setOnClickListener(this);
        ordersBT.setOnClickListener(this);
        profileBT.setOnClickListener(this);
        LivechatBT.setOnClickListener(this);
        LogoutBT.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent i;

        switch (v.getId()) {
            case R.id.BTViewInventory:
                i = new Intent(this, Inventory.class);
                startActivity(i);
                break;
            case R.id.BTNewItem:
                i = new Intent(this, Add_New_Product.class);
                startActivity(i);
                break;
            case R.id.BTViewOrders:
                i = new Intent(this, ViewOrders.class);
                startActivity(i);
                break;
            case R.id.BTprofile:
                i = new Intent(this, ShowProfile.class);
                startActivity(i);
                break;

            case R.id.BTliveChat:
                i = new Intent(this, LiveChatList.class);
                startActivity(i);
                break;

            case R.id.BTlogout:
                i = new Intent(this, Login.class);
                startActivity(i);
                break;

            default: break;

        }

    }


}