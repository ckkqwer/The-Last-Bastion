package com.example.thelastbastion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.thelastbastion.GameView.screenRatioX;
import static com.example.thelastbastion.GameView.screenRatioY;

public class Enemy {

    public int speed = 10;//ori 20
    public boolean wasShot = true;
    int x = 0, y, width, height, birdCounter = 1;
    int skeleCount = 1;
    Bitmap skele1, skele2, skele3, skele4, skele5, skele6,
            skele7, skele8, skele9, skele10, skele11, skele12;

    Enemy(Resources res) {

        skele1 = BitmapFactory.decodeResource(res, R.drawable.skele_001);
        skele2 = BitmapFactory.decodeResource(res, R.drawable.skele_003);
        skele3 = BitmapFactory.decodeResource(res, R.drawable.skele_005);
        skele4 = BitmapFactory.decodeResource(res, R.drawable.skele_007);
        skele5 = BitmapFactory.decodeResource(res, R.drawable.skele_009);
        skele6 = BitmapFactory.decodeResource(res, R.drawable.skele_011);
        skele7 = BitmapFactory.decodeResource(res, R.drawable.skele_013);
        skele8 = BitmapFactory.decodeResource(res, R.drawable.skele_015);
        skele9 = BitmapFactory.decodeResource(res, R.drawable.skele_017);
        skele10 = BitmapFactory.decodeResource(res, R.drawable.skele_019);
        skele11 = BitmapFactory.decodeResource(res, R.drawable.skele_021);
        skele12 = BitmapFactory.decodeResource(res, R.drawable.skele_023);


        width = skele1.getWidth();
        height = skele1.getHeight();

        width /= 1.2;
        height /= 1.2;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        skele1 = Bitmap.createScaledBitmap(skele1, width, height, false);
        skele2 = Bitmap.createScaledBitmap(skele2, width, height, false);
        skele3 = Bitmap.createScaledBitmap(skele3, width, height, false);
        skele4 = Bitmap.createScaledBitmap(skele4, width, height, false);
        skele5 = Bitmap.createScaledBitmap(skele5, width, height, false);
        skele6 = Bitmap.createScaledBitmap(skele6, width, height, false);
        skele7 = Bitmap.createScaledBitmap(skele7, width, height, false);
        skele8 = Bitmap.createScaledBitmap(skele8, width, height, false);
        skele9 = Bitmap.createScaledBitmap(skele9, width, height, false);
        skele10 = Bitmap.createScaledBitmap(skele10, width, height, false);
        skele11 = Bitmap.createScaledBitmap(skele11, width, height, false);
        skele12 = Bitmap.createScaledBitmap(skele12, width, height, false);

        y = -height;
    }

    Bitmap getSkeleton(){
        if(skeleCount == 1){
            skeleCount++;
            return skele1;
        }
        if(skeleCount == 2){
            skeleCount++;
            return skele2;
        }
        if(skeleCount == 3){
            skeleCount++;
            return skele3;
        }
        if(skeleCount == 4){
            skeleCount++;
            return skele4;
        }
        if(skeleCount == 5){
            skeleCount++;
            return skele5;
        }
        if(skeleCount == 6){
            skeleCount++;
            return skele6;
        }
        if(skeleCount == 7){
            skeleCount++;
            return skele7;
        }
        if(skeleCount == 8){
            skeleCount++;
            return skele8;
        }
        if(skeleCount == 9){
            skeleCount++;
            return skele9;
        }
        if(skeleCount == 10){
            skeleCount++;
            return skele10;
        }
        if(skeleCount == 11){
            skeleCount++;
            return skele11;
        }
        skeleCount = 1;
        return skele12;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}

