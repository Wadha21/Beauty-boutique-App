package com.example.myproducts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myproducts.Common.Common;
import com.example.myproducts.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    Button bn_signup,bn_signin;

    SharedPreferences preferences;

    FirebaseDatabase db;
    DatabaseReference reference;

    TextView textView;


    MediaPlayer mediaPlayer;

    ActionBar actionBar;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar=getSupportActionBar();
        actionBar.hide();



        bn_signin=findViewById(R.id.bn_signin);
        bn_signup=findViewById(R.id.bn_signup);
        textView=findViewById(R.id.tv_slogan);


         mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.music);
         mediaPlayer.start();

        bn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SigninActivity.class);
                startActivity(intent);
                mediaPlayer.stop();
            }
        });

        bn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
                mediaPlayer.stop();
            }
        });


        preferences =getSharedPreferences("pref1",MODE_PRIVATE);


        String phone =preferences.getString(Common.USER_KEY,null);
        String pass =preferences.getString(Common.PASS_KEY,null);

        if(phone != null && pass !=null){
            if(!phone.isEmpty() && !pass.isEmpty()){
                loginUser(phone,pass);
            }
        }
    }


    private void loginUser(String username, String pass) {

        db= FirebaseDatabase.getInstance();
        reference=db.getReference("User");

        if(Common.isConnectedToInterner(getBaseContext())) {

            reference.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.child(username).exists()) {
                                User user = snapshot.child(username).getValue(User.class);
                                user.setUsername(username);
                                if (user.getPassword().equals(pass)) {
                                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
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


    @Override
    protected void onPause() {
        mediaPlayer.stop();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mediaPlayer.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        mediaPlayer.start();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        mediaPlayer.start();
        super.onRestart();
    }

    @Override
    public void recreate() {
        mediaPlayer.start();
        super.recreate();
    }

}
