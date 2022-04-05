package com.example.thelastbastion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.thelastbastion.GameView.screenRatioX;
import static com.example.thelastbastion.GameView.screenRatioY;

public class Projectile {

    int x, y, width, height;
    Bitmap bullet;

    Projectile(Resources res) {

        bullet = BitmapFactory.decodeResource(res, R.drawable.projectile);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width /= 1;
        height /= 1;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}

