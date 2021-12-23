package com.yugioh.cardmaker.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.yugioh.cardmaker.R;
import com.yugioh.cardmaker.session.PrefSettings;
import com.yugioh.cardmaker.session.SessionManager;
import com.yugioh.cardmaker.users.LoginActivity;

public class HomeActivity extends AppCompatActivity {

    SessionManager session;
    SharedPreferences prefs;
    PrefSettings prefSetting;
    CardView cardExit, customCards, cardInputData, cardProfile, cardYgo, forbiddenLimited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        prefSetting = new PrefSettings(this);
        prefs = prefSetting.getSharePreferances();

        session = new SessionManager(HomeActivity.this);

        prefSetting.isLogin(session, prefs);

        cardExit = (CardView) findViewById(R.id.cardExit);
        customCards = (CardView) findViewById(R.id.customCards);
        cardInputData = (CardView) findViewById(R.id.cardInputData);
        cardProfile = (CardView) findViewById(R.id.cardProfile);
        cardYgo = (CardView) findViewById(R.id.cardYgo);
        forbiddenLimited = (CardView) findViewById(R.id.forbiddenLimited);

        cardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        customCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ActivityCard.class);
                startActivity(i);
                finish();
            }
        });

        cardInputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, InputCardActivity.class);
                startActivity(i);
                finish();
            }
        });

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });

        cardYgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, CardListActivity.class);
                startActivity(i);
                finish();
            }
        });

        forbiddenLimited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ForbiddenLimitedActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
