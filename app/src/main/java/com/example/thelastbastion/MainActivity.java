package com.example.thelastbastion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean isMute;
    private boolean usingGyro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        //region Highest score
        TextView highScoreTxt = findViewById(R.id.record);

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        highScoreTxt.setText("Highest Score: " + prefs.getInt("highestScore", 0));
        //endregion

        //region Reset score
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("highestScore", 0);
                editor.apply();

                highScoreTxt.setText("Highest Score: " + prefs.getInt("highestScore", 0));
                Toast toast = Toast.makeText(getApplicationContext(), "Score reset!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        //endregion

        //region Guide
        findViewById(R.id.guide).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1-KJ8lYFerO3L9OqM22jmnsKtsgwvlomY/view?usp=sharing"));
                startActivity(i);
            }
        });
        //endregion

        //region Audio
        isMute = prefs.getBoolean("isMute", false);

        final ImageView imgVolCrtl = findViewById(R.id.volCtrl);

        if (isMute)
            imgVolCrtl.setImageResource(R.drawable.ic_volume_off_black_24dp);
        else
            imgVolCrtl.setImageResource(R.drawable.ic_volume_up_black_24dp);

        imgVolCrtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMute = !isMute;
                Toast toast = Toast.makeText( getApplicationContext()  , "" , Toast.LENGTH_SHORT );

                if (isMute){
                    imgVolCrtl.setImageResource(R.drawable.ic_volume_off_black_24dp);
                    toast.setText( "Audio Muted!" );
                    toast.show();
                }
                else{
                    imgVolCrtl.setImageResource(R.drawable.ic_volume_up_black_24dp);
                    toast.setText( "Audio Unmuted" );
                    toast.show();
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMute", isMute);
                editor.apply();
            }
        });
        //endregion

        //region Accelerometer
        usingGyro = prefs.getBoolean("usingGyro", false);

        final ImageView imgGyro = findViewById(R.id.gyroCrtl);

        if (usingGyro)
            imgGyro.setImageResource(R.drawable.gyro);
        else
            imgGyro.setImageResource(R.drawable.gyro_cancel);

        imgGyro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usingGyro = !usingGyro;
                Toast toast = Toast.makeText( getApplicationContext()  , "" , Toast.LENGTH_SHORT );

                if (usingGyro){
                    imgGyro.setImageResource(R.drawable.gyro);
                    toast.setText( "Gyroscope On!" );
                    toast.show();
                }
                else{
                    imgGyro.setImageResource(R.drawable.gyro_cancel);
                    toast.setText( "Gyroscope Off!" );
                    toast.show();
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("usingGyro", usingGyro);
                editor.apply();
            }
        });
        //endregion
    }
}
