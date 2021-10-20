package com.kang.customviewproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.kang.customviewproject.R;
import com.kang.customviewproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.bt58Anim.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Imitate58LoadingAnimActivity.class));
        });
    }
}