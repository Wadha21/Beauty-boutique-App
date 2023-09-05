package com.example.myproducts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myproducts.Common.Common;
import com.example.myproducts.Model.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    ImageView iv_back;


    TextInputLayout et_name, et_username, et_pass;
    Button bn_signup;

    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();


        iv_back=findViewById(R.id.iv_back);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        et_name = findViewById(R.id.signup_et_name);
        et_username = findViewById(R.id.signup_et_username);
        et_pass = findViewById(R.id.signup_et_pass);
        bn_signup = findViewById(R.id.signup_bn_signup);


        db = FirebaseDatabase.getInstance();
        reference = db.getReference("User");


        bn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (et_name.getEditText().getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                }
                else if (et_username.getEditText().getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                }
                else if (et_pass.getEditText().getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                }
                else {
                    if(Common.isConnectedToInterner(getBaseContext())) {
                        reference.addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        if (snapshot.child(et_username.getEditText().getText().toString().trim()).exists()) {
                                            Toast.makeText(getApplicationContext(), "Username Alredy Registered", Toast.LENGTH_LONG).show();
                                        } else {
                                            User user = new User(et_name.getEditText().getText().toString(),
                                                    et_pass.getEditText().getText().toString());
                                            reference.child(et_username.getEditText().getText().toString().trim()).setValue(user);
                                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }


                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                }
                        );
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Check your connecing to internet !", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });

    }

}