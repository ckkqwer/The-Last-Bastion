package com.example.thelastbastion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.example.thelastbastion.GameView.screenRatioX;
import static com.example.thelastbastion.GameView.screenRatioY;

public class Player {

    boolean shoot = false, isGoingUp = false, isGoingDown = false;
    int x, y, width, height;
    int speed = 30;
    Bitmap cannon, proj;
    private GameView gameView;

    Player(GameView gameView, int screenY, Resources res) {

        this.gameView = gameView;

        cannon = BitmapFactory.decodeResource(res, R.drawable.cannon);
        proj = BitmapFactory.decodeResource(res, R.drawable.projectile);

        width = cannon.getWidth();
        height = cannon.getHeight();

        width /= 1;
        height /= 1;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        cannon = Bitmap.createScaledBitmap(cannon, width, height, false);
        proj = Bitmap.createScaledBitmap(proj, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);

    }
    Bitmap getCannon() {

        if (shoot) {
            gameView.newProjectile();
            shoot = false;
            return proj;
        }
        return cannon;
    }
    /*
    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }*/
}

