package com.example.myproducts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class SigninActivity extends AppCompatActivity {

    ImageView iv_back;


    TextInputLayout et_username, et_pass;
    Button bn_signin;
    FirebaseDatabase db;
    DatabaseReference table_user;

    CheckBox checkBox;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        getSupportActionBar().hide();


        iv_back=findViewById(R.id.signin_iv_back);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        et_username = findViewById(R.id.signin_et_username);
        et_pass = findViewById(R.id.signin_et_pass);
        bn_signin = findViewById(R.id.signin_bn_signin);
        checkBox=findViewById(R.id.signin_chbox);

        db = FirebaseDatabase.getInstance();
        table_user = db.getReference("User");




        bn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (et_username.getEditText().getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                } else if (et_pass.getEditText().getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                } else  {

                    if(Common.isConnectedToInterner(getBaseContext())) {

                        if(checkBox.isChecked()){
                            preferences=getSharedPreferences("pref1",MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();

                            editor.putString(Common.PASS_KEY,et_pass.getEditText().getText().toString());
                            editor.putString(Common.USER_KEY,et_username.getEditText().getText().toString());

                            editor.apply();
                            editor.commit();

                        }

                        table_user.addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        if (snapshot.child(et_username.getEditText().getText().toString().trim()).exists()) {
                                            User user = snapshot.child(et_username.getEditText().getText().toString()).getValue(User.class);
                                            user.setUsername(et_username.getEditText().getText().toString());
                                            if (user.getPassword().equals(et_pass.getEditText().getText().toString().trim())) {
                                                startActivity(new Intent(SigninActivity.this, HomeActivity.class));
                                                Common.currentUser = user;
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Sign in failed ", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "User is not exists ", Toast.LENGTH_LONG).show();
                                        }
                                    }


                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                }
                        );
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Check your connecing to internet !", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });

    }
}