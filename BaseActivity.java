package com.example.mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

    }
    public void task(View view)
    {
        Intent in = new Intent(this, HomeActivity.class);
        startActivity(in);

    }
    public void reminder(View view)
    {
        Intent in = new Intent(this, Main1Activity.class);
        startActivity(in);

    }


}