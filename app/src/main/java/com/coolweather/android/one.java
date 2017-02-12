package com.coolweather.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class one extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        final EditText editText= (EditText) findViewById(R.id.ed_one);
        Button button= (Button) findViewById(R.id.bt_one);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=editText.getText().toString();
                if(text.equals("10月23日")){
                    Intent intent=new Intent(one.this,two.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(one.this,wrong.class);
                    startActivity(intent);
                }
            }
        });
    }
}
