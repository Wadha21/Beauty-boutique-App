package com.example.myproducts;




import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {


    TextView tv_face,tv_tele,tv_whats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        getSupportActionBar().setTitle("Contact Us");


        tv_face=findViewById(R.id.con_tv_facebook);
        tv_tele=findViewById(R.id.con_tv_telegram);
        tv_whats=findViewById(R.id.con_tv_whatsapp);



        tv_face.setMovementMethod(LinkMovementMethod.getInstance());
        String link="<a href='https://www.facebook.com'> facebook </a>";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            tv_face.setText(Html.fromHtml(link,Html.FROM_HTML_MODE_LEGACY));
        }else{
            tv_face.setText(Html.fromHtml(link));
        }
        tv_face.setText(Html.fromHtml(link));


        tv_tele.setMovementMethod(LinkMovementMethod.getInstance());
        String link2="<a href='https://web.telegram.org'> Telegram </a>";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            tv_tele.setText(Html.fromHtml(link2,Html.FROM_HTML_MODE_LEGACY));
        }else{
            tv_tele.setText(Html.fromHtml(link2));
        }
        tv_tele.setText(Html.fromHtml(link2));


        tv_whats.setMovementMethod(LinkMovementMethod.getInstance());
        String link3="<a href='https://www.whatsapp.com'> Whatsapp </a>";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            tv_whats.setText(Html.fromHtml(link3,Html.FROM_HTML_MODE_LEGACY));
        }else{
            tv_whats.setText(Html.fromHtml(link3));
        }
        tv_whats.setText(Html.fromHtml(link3));


    }
}