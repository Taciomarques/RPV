package com.example.rp4_padel.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.rp4_padel.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void inscreverDupla(View view) {
        Intent intent = new Intent(this, Inscricao.class);
        startActivity(intent);
        //Inscricao i = new Inscricao();
       // i.startActivity(intent);
    }

    public void verInscritos(View view) {
        Intent intent = new Intent(this, Inscritos.class);
        startActivity(intent);
    }
}
