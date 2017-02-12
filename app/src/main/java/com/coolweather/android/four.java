package com.coolweather.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class four extends AppCompatActivity {

    private RadioButton three;

    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        three= (RadioButton) findViewById(R.id.rb_three);
        bt= (Button) findViewById(R.id.bt_two);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(three.isChecked()){
                    Intent intent=new Intent(four.this,five.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(four.this,wrong.class);
                    startActivity(intent);
                }
            }
        });

    }
}
