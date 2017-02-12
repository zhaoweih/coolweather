package com.coolweather.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class password extends AppCompatActivity {

    private EditText passText;

    private Button passButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        passText= (EditText) findViewById(R.id.pass_ed_text);
        passButton= (Button) findViewById(R.id.pass_bt);
        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passtext=passText.getText().toString();
                if(passtext.equals("MEANDYOU")){
                    AlertDialog.Builder dialog=new AlertDialog.Builder(password.this);
                    dialog.setTitle("恭喜你，答对了");
                    dialog.setMessage("你得到一份惊喜情人节礼物，将在开学后揭晓，快截图给先生吧");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(password.this,WeatherActivity.class);
                            startActivity(intent);
                        }
                    });
                    dialog.show();
                }else{
                    AlertDialog.Builder dialog=new AlertDialog.Builder(password.this);
                    dialog.setTitle("答错了");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
}
