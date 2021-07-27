package com.example.pharmacyonphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.MoreObjects;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Inventory extends AppCompatActivity {

    FirebaseFirestore db;
    int i = 0;
    public static final String NAME_TEXT_VIEW_CONSTANT = "100";
    public static final String PRICE_TEXT_VIEW_CONSTANT = "101";
    public static final String EDIT_BUTTON_CONSTANT = "102";
    public static final String DELETE_BUTTON_CONSTANT = "103";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        LinearLayout layout = findViewById(R.id.inflateLinearLayout);
        LayoutInflater l = LayoutInflater.from(this);
        View v = l.inflate(R.layout.inventory_item, null);
        //layout.addView(v);

        db = FirebaseFirestore.getInstance();

        db.collection("products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                          @Override
                                                          public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                                                              if (e != null) {
                                                                  return;
                                                              }
                                                              for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                                                  if (documentSnapshot.getId().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                                                      i++;
                                                                      View v = l.inflate(R.layout.inventory_item, null);
                                                                      layout.addView(v);
                                                                      TextView nameTV = findViewById(R.id.nameMed);
                                                                      TextView priceTV = findViewById(R.id.priceMed);
                                                                      Button editBT = findViewById(R.id.edit_button);
                                                                      Button deleteBT = findViewById(R.id.delete_button);
                                                                      nameTV.setText("Name: " + documentSnapshot.getString("product_name"));
                                                                      priceTV.setText("Price: " + documentSnapshot.getString("product_price"));
                                                                      nameTV.setId(Integer.parseInt(i + NAME_TEXT_VIEW_CONSTANT));
                                                                      priceTV.setId(Integer.parseInt(i + PRICE_TEXT_VIEW_CONSTANT));
                                                                      editBT.setId(Integer.parseInt(i + EDIT_BUTTON_CONSTANT));
                                                                      deleteBT.setId(Integer.parseInt(i + DELETE_BUTTON_CONSTANT));
                                                                      editBT.setOnClickListener(new View.OnClickListener() {
                                                                          @Override
                                                                          public void onClick(View v) {
                                                                              startActivity(new Intent(Inventory.this, edit_product.class).putExtra("DOC_REF", documentSnapshot.getId()));
                                                                              finish();
                                                                              Toast.makeText(Inventory.this, "Edit for " + documentSnapshot.getString("product_name"), Toast.LENGTH_LONG).show();
                                                                          }
                                                                      });
                                                                      deleteBT.setOnClickListener(new View.OnClickListener() {
                                                                          @Override
                                                                          public void onClick(View v) {
                                                                              deleteDocumentFromFirebase(documentSnapshot.getId());
                                                                              Toast.makeText(Inventory.this, "Delete for " + documentSnapshot.getString("product_name"), Toast.LENGTH_LONG).show();
                                                            ;;              }
                                                                      });
                                                                  }
                                                              }
                                                          }
                                                      }
        );


    }

    void deleteDocumentFromFirebase(String docref) {
        ProgressDialog wait=ProgressDialog.show(Inventory.this,"Deleting","Deleting The product From your Inventory",true);
        db.collection("products").document(docref).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                wait.dismiss();
                finish();
                startActivity(new Intent(Inventory.this,Inventory.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {
                wait.dismiss();
                Toast.makeText(Inventory.this,"Delete Failed, Try Again Later",Toast.LENGTH_LONG).show();
            }
        });


    }
}