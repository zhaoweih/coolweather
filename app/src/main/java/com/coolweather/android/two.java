package com.coolweather.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class two extends AppCompatActivity {

    private RadioButton one;

    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        one= (RadioButton) findViewById(R.id.rb_one);
        bt= (Button) findViewById(R.id.bt_two);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(one.isChecked()){
                    Intent intent=new Intent(two.this,three.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(two.this,wrong.class);
                    startActivity(intent);
                }
            }
        });

    }
}
