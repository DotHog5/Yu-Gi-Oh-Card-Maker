package com.yugioh.cardmaker.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.yugioh.cardmaker.R;
import com.yugioh.cardmaker.session.PrefSettings;

public class ProfileActivity extends AppCompatActivity {

    TextView txtUserName, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("User profile");

        txtUserName = (TextView) findViewById(R.id.userName);
        txtEmail = (TextView) findViewById(R.id.email);

        txtUserName.setText(PrefSettings.userName);
        txtEmail.setText(PrefSettings.email);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}
