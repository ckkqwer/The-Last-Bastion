package com.example.thelastbastion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ControlUI {
    int x = 0, y = 0;
    Bitmap up, down, resumeBtn, pauseBtn;

    ControlUI (int x, int y, Resources res) {

        up = BitmapFactory.decodeResource(res, R.drawable.up_btn);
        down = BitmapFactory.decodeResource(res, R.drawable.down_btn);
        resumeBtn = BitmapFactory.decodeResource(res, R.drawable.resume_btn);
        pauseBtn = BitmapFactory.decodeResource(res, R.drawable.pause_btn);

        up = Bitmap.createScaledBitmap(up, x, y, false);
        down = Bitmap.createScaledBitmap(down, x, y, false);
        resumeBtn = Bitmap.createScaledBitmap(resumeBtn, x, y, false);
        pauseBtn = Bitmap.createScaledBitmap(pauseBtn, x, y, false);
    }
}
