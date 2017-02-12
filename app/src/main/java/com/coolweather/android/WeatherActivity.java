package com.coolweather.android;


import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coolweather.android.gson.Forecast;
import com.coolweather.android.gson.Weather;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {


    private ScrollView weatherLayout;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private TextView countDownDayText;

    private LinearLayout forecastLayout;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    private TextView xianshengText;

    private ImageView bingPicImg;

    public SwipeRefreshLayout swipeRefresh;

    public DrawerLayout drawerLayout;

    private Button navButton;

    private ImageView funnyPic;

    private FloatingActionButton floatingButton;

    public LinearLayout countdowndaylayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        weatherLayout= (ScrollView) findViewById(R.id.weather_layout);
        titleCity= (TextView) findViewById(R.id.title_city);
        titleUpdateTime= (TextView) findViewById(R.id.title_update_time);
        degreeText= (TextView) findViewById(R.id.degree_text);
        weatherInfoText= (TextView) findViewById(R.id.weather_info_text);
        countDownDayText= (TextView) findViewById(R.id.countdownday_text);
        forecastLayout= (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText= (TextView) findViewById(R.id.aqi_text);
        pm25Text= (TextView) findViewById(R.id.pm25_text);
        comfortText= (TextView) findViewById(R.id.comfort_text);
        carWashText= (TextView) findViewById(R.id.car_wash_text);
        sportText= (TextView) findViewById(R.id.sport_text);
        xianshengText= (TextView) findViewById(R.id.xiansheng_text);
        bingPicImg= (ImageView) findViewById(R.id.bing_pic_img);
        swipeRefresh= (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        navButton= (Button) findViewById(R.id.nav_button);
        funnyPic= (ImageView) findViewById(R.id.funny_pic);
        countdowndaylayout= (LinearLayout) findViewById(R.id.countdownday_layout);
        floatingButton= (FloatingActionButton) findViewById(R.id.floatingAB);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(WeatherActivity.this,one.class);
                startActivity(intent);
            }
        });
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString =prefs.getString("weather",null);
        final String weatherId;
        if(weatherString!=null){
            Weather weather= Utility.handleWeatherResponse(weatherString);
            weatherId=weather.basic.weatherId;
            showWeatherInfo(weather);
        }else {
            weatherId=getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });
        floatingButton.setVisibility(View.INVISIBLE);



//        String bingPic=prefs.getString("bing_pic",null);
//        if(bingPic!=null){
//            Glide.with(this).load(R.drawable.rain).into(bingPicImg);
//        }else {
//            loadBingPic();
//        }

    }




    public void requestWeather(final String weatherId){

        String weatherUrl="http://guolin.tech/api/weather?cityid="+
                weatherId+"&key=dc78bed0bdba4d47b2f91db87612f2fa";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败",
                                Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                final Weather weather=Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weather!=null && "ok".equals(weather.status)){
                            SharedPreferences.Editor editor=PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this).
                                    edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            showWeatherInfo(weather);


                        }else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });

            }
        });

//        loadBingPic();
    }

//    private void loadBingPic(){
//        String requestBingPic="http://guolin.tech/api/bing_pic";
//        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String bingPic=response.body().string();
//                SharedPreferences.Editor editor=PreferenceManager.
//                        getDefaultSharedPreferences(WeatherActivity.this).edit();
//                editor.putString("bing_pic",bingPic);
//                editor.apply();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Glide.with(WeatherActivity.this).load(R.drawable.rain).into(bingPicImg);
//                    }
//                });
//
//            }
//        });
//    }



    private void showWeatherInfo(Weather weather){
        String cityName=weather.basic.cityName;
        String updateTime=weather.basic.update.updatetime.split(" ")[1];
        String degree=weather.now.temperature;
        String weatherInfo=weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree+"℃");
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for(Forecast forecast:weather.forecastList){
            View view= LayoutInflater.from(this).inflate(R.layout.forecast_item,
                    forecastLayout,false);
            TextView dateText= (TextView) view.findViewById(R.id.date_text);
            TextView infoText= (TextView) view.findViewById(R.id.info_text);
            TextView maxText= (TextView) view.findViewById(R.id.max_text);
            TextView minText= (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }
        if(weather.aqi!=null){
            //aqiText.setText(weather.aqi.city.aqi);
            if(Double.valueOf(weather.aqi.city.aqi)>0&&Double.valueOf(weather.aqi.city.aqi)<=50){
                aqiText.setText("优");
            }else if(Double.valueOf(weather.aqi.city.aqi)>50&&Double.valueOf(weather.aqi.city.aqi)<=100){
                aqiText.setText("良");
            }else if(Double.valueOf(weather.aqi.city.aqi)>100&&Double.valueOf(weather.aqi.city.aqi)<=150){
                aqiText.setText("轻度污染");
            }else if(Double.valueOf(weather.aqi.city.aqi)>150&&Double.valueOf(weather.aqi.city.aqi)<=200) {
                aqiText.setText("中度污染");
            }else if(Double.valueOf(weather.aqi.city.aqi)>200&&Double.valueOf(weather.aqi.city.aqi)<=300) {
                aqiText.setText("重度污染");
            }else if(Double.valueOf(weather.aqi.city.aqi)>300) {
                aqiText.setText("严重污染");
            }
            //pm25Text.setText(weather.aqi.city.pm25);
            if(Double.valueOf(weather.aqi.city.pm25)>0&&Double.valueOf(weather.aqi.city.pm25)<=35){
                pm25Text.setText("优");
            }else if(Double.valueOf(weather.aqi.city.pm25)>35&&Double.valueOf(weather.aqi.city.pm25)<=75){
                pm25Text.setText("良");
            }else if(Double.valueOf(weather.aqi.city.pm25)>75&&Double.valueOf(weather.aqi.city.pm25)<=115){
                pm25Text.setText("轻度污染");
            }else if(Double.valueOf(weather.aqi.city.pm25)>115&&Double.valueOf(weather.aqi.city.pm25)<=150) {
                pm25Text.setText("中度污染");
            }else if(Double.valueOf(weather.aqi.city.pm25)>150&&Double.valueOf(weather.aqi.city.pm25)<=250) {
                pm25Text.setText("重度污染");
            }else if(Double.valueOf(weather.aqi.city.pm25)>250) {
                pm25Text.setText("严重污染");
            }
        }
        String comfort="舒适度："+weather.suggestion.comfort.info;
        String carWash="洗车指数："+weather.suggestion.carWash.info;
        String sport="运动建议："+weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        if(Double.valueOf(degree)>0&&Double.valueOf(degree)<=20){
            xianshengText.setText("先生的建议：天气好冷啊，赶紧躲被窝吧，出门记得穿多点衣服");
        }else if(Double.valueOf(degree)>20&&Double.valueOf(degree)<=30){
            xianshengText.setText("先生的建议：天气比较暖和，是约先生出去游玩的最佳时机，早晚记得多穿衣服哦");
        }else if(Double.valueOf(degree)>30){
            xianshengText.setText("先生的建议：天气好热啊，赶紧打电话约你的先生去吃冰吧");
        }
        weatherLayout.setVisibility(View.VISIBLE);
        Intent intent=new Intent(this,AutoUpdateService.class);
        startService(intent);
        if(weatherInfo.equals("晴")){
            Glide.with(this).load(R.drawable.rain).into(bingPicImg);
        }else if(weatherInfo.equals("多云")){
            Glide.with(this).load(R.drawable.cloud).into(bingPicImg);
        }
        Glide.with(this).load(R.drawable.logo2).into(funnyPic);
        Calendar c=Calendar.getInstance();
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DATE);
        if((month+1)==2&&day==14){
            floatingButton.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.rose).into(bingPicImg);
        }
        int countdownday=20-((month+1)+day);
        if((month+1)==2&&countdownday>0){
            countDownDayText.setText(String.valueOf(countdownday));
        }else{
            countdowndaylayout.setVisibility(View.GONE);
        }


    }

}
