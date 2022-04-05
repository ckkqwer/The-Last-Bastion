package com.example.thelastbastion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    //region Default
    private Thread thread;
    private SharedPreferences prefs;
    private GameActivity gameActivity;
    private Paint paint;
    private Canvas canvas;

    private List<Projectile> projectiles;
    private Enemy[] enemies;
    private Player player;
    //endregion
    //region Asset
    private Background background1, background2;
    private ControlUI up, down, resume, pause;

    private SoundPool soundPool;
    private MediaPlayer mediaPlayer;
    private int currentMusicPos;
    private int shootSound, hurtSound, gameOverSound;
    //endregion
    //region Var
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;

    private Random random;
    private int spdLevel = 1;
    private int countUp = 0;
    private int timeBeforeExit = 4400;
    protected float senX, senY, senZ;
    //endregion

    //show sen, invincible
    private boolean debug = false;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.gameActivity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        shootSound = soundPool.load(activity, R.raw.shoot, 1);
        hurtSound = soundPool.load(activity, R.raw.hurt, 1);
        gameOverSound = soundPool.load(activity, R.raw.rr, 1);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        up = new ControlUI(200, 200, getResources());
        down = new ControlUI(200, 200, getResources());
        resume = new ControlUI(200, 200, getResources());
        pause = new ControlUI(200, 200, getResources());

        player = new Player(this, screenY, getResources());
        projectiles = new ArrayList<>();
        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        enemies = new Enemy[4];

        for (int i = 0;i < 4;i++) {
            Enemy enemy = new Enemy(getResources());
            enemies[i] = enemy;
        }
        random = new Random();
    }

    //ini
    @Override
    public void run() {
        //Music
        if (!prefs.getBoolean("isMute", false)){
            mediaPlayer = MediaPlayer.create(gameActivity, R.raw.music);
            mediaPlayer.setLooping(true);
            mediaPlayer.seekTo(currentMusicPos);
            mediaPlayer.start();
        }

        //timer.start();

        while (isPlaying) {
            update ();
            draw ();
            sleep ();
        }

    }

    private void sleep () {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {
        isPlaying = true;
/*
        if (!prefs.getBoolean("isMute", false)){
            mediaPlayer.seekTo(currentMusicPos);
            mediaPlayer.start();
        }*/

        thread = new Thread(this);
        thread.start();

    }

    public void pause () {

        try {
            isPlaying = false;
            //draw one frame to swap button
            draw();

            if (!prefs.getBoolean("isMute", false)){
                mediaPlayer.pause();
                currentMusicPos = mediaPlayer.getCurrentPosition();
            }

            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //main
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //region Movement
                if (event.getX() < 200 && event.getY() > screenY-200) {
                    player.isGoingUp = true;
                }
                else if(event.getX() < 400 && event.getX() > 200 && event.getY() > screenY-200){
                    player.isGoingDown = true;
                }
                //endregion

                //region Pause
                if (event.getX()> screenX-250 && event.getY() < 180){
                    if(isPlaying){
                        pause();
                    }
                    else{
                        resume();
                    }
                }
                //endregion

                //region Shooting
                else if(event.getX() > screenX / 2)
                    player.shoot = true;
                //endregion

                break;

            case MotionEvent.ACTION_UP:
                player.isGoingUp = false;
                player.isGoingDown = false;
                break;
        }

        return true;
    }

    private void update () {
        //region Time event
        countUp++;
        if(countUp == 600 || countUp == 1200 || countUp == 1800){
            spdLevel += 1;
        }
        //endregion

        //region Player movement
        //Accelerometer
        //up = 0<x<6.5 / 7.5<z<10
        //down = 7.5<x<10 / 0<z<6.5
        if (prefs.getBoolean("usingGyro", false)){
            if(senX<7 || senZ > 7){
                player.isGoingDown = false;
                player.isGoingUp = true;
            }
            else if(senX>7 || senZ < 7){
                player.isGoingUp = false;
                player.isGoingDown = true;
            }
        }

        if (player.isGoingUp)
            player.y -= player.speed * screenRatioY;
        else if(player.isGoingDown)
            player.y += player.speed * screenRatioY;

        if (player.y < 0)
            player.y = 0;

        if (player.y >= screenY - player.height)
            player.y = screenY - player.height;
        //endregion

        //region Projectile
        List<Projectile> trash = new ArrayList<>();

        for (Projectile projectile : projectiles) {

            if (projectile.x > screenX)
                trash.add(projectile);

            projectile.x += 50 * screenRatioX;

            for (Enemy enemy : enemies) {

                if (Rect.intersects(enemy.getCollisionShape(),
                        projectile.getCollisionShape())) {

                    score++;
                    enemy.x = -500;
                    projectile.x = screenX + 500;
                    //sfx
                    if (!prefs.getBoolean("isMute", false))
                        soundPool.play(hurtSound, 1, 1, 0, 0, 1);
                    enemy.wasShot = true;
                }

            }

        }

        for (Projectile projectile : trash)
            projectiles.remove(projectile);
        //endregion

        //Enemy
        for (Enemy enemy : enemies) {
            enemy.x -= enemy.speed + (5 * spdLevel);

            if (enemy.x + enemy.width < 0) {

                //Out of bound
                if(!debug){
                    if(!enemy.wasShot){
                        isGameOver = true;
                        return;
                    }
                }

                int bound = (int) (30 * screenRatioX);
                enemy.speed = (int) 0.5 * bound;
                //enemy.speed = random.nextInt(bound);

                if (enemy.speed < 10 * screenRatioX)
                    enemy.speed = (int) (10 * screenRatioX);

                enemy.x = screenX;
                enemy.y = random.nextInt(screenY - enemy.height);

                enemy.wasShot = false;
            }

            //Contact
            /*
            if (Rect.intersects(enemy.getCollisionShape(), player.getCollisionShape())) {

                isGameOver = true;
                return;
            }*/

        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()) {

            canvas = getHolder().lockCanvas();
            //region Background
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
            //endregion

            //Enemy
            for (Enemy enemy : enemies)
                canvas.drawBitmap(enemy.getSkeleton(), enemy.x, enemy.y, paint);

            //region Text
            //score
            canvas.drawText("Score: " + score + "", screenX / 2f - 300, 164, paint);
            //timer
            paint.setTextSize(96);
            canvas.drawText("Time passed: " + countUp + "", screenX / 2f + 200, screenY-40, paint);
            canvas.drawText("Lv: " + showLevel() + "", screenX / 2f - 600, screenY-40, paint);

            //show sen
            if(debug){
                canvas.drawText("x: " + senX, screenX / 2f - 600, screenY-280, paint);
                canvas.drawText("y: " + senY, screenX / 2f - 600, screenY-200, paint);
                canvas.drawText("z: " + senZ, screenX / 2f - 600, screenY-120, paint);
            }

            paint.setTextSize(128);
            //endregion

            //Gameover
            if (isGameOver) {
                gameOver();
            }

            //Player
            canvas.drawBitmap(player.getCannon(), player.x, player.y, paint);

            //Projectile
            for (Projectile projectile : projectiles)
                canvas.drawBitmap(projectile.bullet, projectile.x, projectile.y, paint);

            //region Control UI
            canvas.drawBitmap(up.up, 0, screenY-200, paint);
            canvas.drawBitmap(down.down, 200, screenY-200, paint);

            if(isPlaying)
                canvas.drawBitmap(pause.pauseBtn, screenX-250, 0, paint);
            else
                canvas.drawBitmap(resume.resumeBtn, screenX-250, 0, paint);
            //endregion

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    //custom func
    private void waitBeforeExiting() {

        try {
            gameActivity.sensorManager.unregisterListener(gameActivity);

            Thread.sleep(timeBeforeExit);
            gameActivity.startActivity(new Intent(gameActivity, MainActivity.class));
            gameActivity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveIfHighScore() {

        if (prefs.getInt("highestScore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highestScore", score);
            editor.apply();
        }

    }

    public void newProjectile() {

        if (!prefs.getBoolean("isMute", false))
            soundPool.play(shootSound, 1, 1, 0, 0, 1);

        Projectile projectile = new Projectile(getResources());
        projectile.x = player.x + player.width;
        projectile.y = player.y + (player.height / 2);
        projectiles.add(projectile);

    }

    private String showLevel(){
        String s = Integer.toString(spdLevel);
        if(spdLevel >= 4)
            return "Max";
        else
            return s;
    }

    private void gameOver(){
        isPlaying = false;
        canvas.drawText("Game Over" + "", screenX/2f - 300, screenY/2f, paint);
        getHolder().unlockCanvasAndPost(canvas);

        if (!prefs.getBoolean("isMute", false)){
            mediaPlayer.pause();
            soundPool.play(gameOverSound, 1, 1, 0, 0, 1);
        }

        currentMusicPos = 0;
        saveIfHighScore();
        waitBeforeExiting ();
        return;
    }
}

