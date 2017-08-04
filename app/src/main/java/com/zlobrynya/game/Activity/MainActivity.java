package com.zlobrynya.game.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zlobrynya.game.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent newInten = new Intent(MainActivity.this,Map.class);
        //startActivity(newInten);
    }

    public void click(View view) {
        Intent newInten = new Intent(MainActivity.this,Battle.class);
        startActivity(newInten);
    }
}
