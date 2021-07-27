package com.example.pharmacyonphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

public class edit_product extends AppCompatActivity {

    MaterialButton TakePhotoButton,UploadItemButton;
    TextInputLayout NameTV,TypeTV,PriceTV,FormulaTV;
    private String pictureFilePath;
    FirebaseFirestore fs;
    FirebaseAuth firebaseAuth;
    String documentReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        fs=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        NameTV=findViewById(R.id.productNameText);
        TypeTV=findViewById(R.id.category);
        PriceTV=findViewById(R.id.priceText);
        FormulaTV=findViewById(R.id.formulaText);
        UploadItemButton=findViewById(R.id.UploadItemButton);
        documentReference=getIntent().getStringExtra("DOC_REF");

        setDetails();

        UploadItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map=new HashMap<>();
                map.put("product_name",NameTV.getEditText().getText().toString());
                map.put("product_type",TypeTV.getEditText().getText().toString());
                map.put("product_price",PriceTV.getEditText().getText().toString());
                map.put("product_formula",FormulaTV.getEditText().getText().toString());

                ProgressDialog wait = ProgressDialog.show(edit_product.this, "Processing", "Updating Product", true);

                fs.collection("products").document(documentReference).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Done","Uploaded");
                        new AlertDialog.Builder(edit_product.this)
                                .setTitle("Done!")
                                .setMessage("Item Updated")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                        wait.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        Log.d("Failed","Upload Failed");

                        wait.dismiss();

                    }
                });


            }
        });


    }
    void setDetails(){
        ProgressDialog wait = ProgressDialog.show(edit_product.this, "Processing", "Getting Information from Inventory", true);

        fs.collection("products").document(documentReference).get(Source.SERVER).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                NameTV.getEditText().setText(documentSnapshot.getString("product_name"));
                TypeTV.getEditText().setText(documentSnapshot.getString("product_type"));
                PriceTV.getEditText().setText(documentSnapshot.getString("product_price"));
                FormulaTV.getEditText().setText(documentSnapshot.getString("product_formula"));
                wait.dismiss();
            }
        });



    }
}