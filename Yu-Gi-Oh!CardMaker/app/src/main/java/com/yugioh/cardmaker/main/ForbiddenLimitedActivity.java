package com.yugioh.cardmaker.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.yugioh.cardmaker.R;

public class ForbiddenLimitedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forbidden_limited);
        getSupportActionBar().setTitle("Yu-Gi-Oh! Forbidden/Limited list");
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(ForbiddenLimitedActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}