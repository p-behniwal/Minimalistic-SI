package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;


public class SpaceInvaders extends Game {

    SpriteBatch batch;
    ArrayList<Alien> swarm = new ArrayList<Alien>();
    PlayerShip player;
    int screenX;
    int screenY;
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    Texture projectile;
    Sprite shipSprite;
    Alien tempAlien;
    Alien tempAlien2;
    Music music;
    Ufo bonusAlien;
    ArrayList<Shield> shields = new ArrayList<Shield>();
    PowerUp power;
    Sound alienDeath;
    Sound playerDeath;
    Sound bossDeath;
    Boss boss;


    BitmapFont font;
    int score = 0;
    int lives = 3;
    int playerCooldown = 60;
    int respawnTimer = 0;
    int invinTimer = 0;
    int level = 1;
    int maxBullets = 1;
    int laserTimer = 0;

    public void create() {
        this.setScreen(new MainMenuScreen(this));
        batch = new SpriteBatch();
        screenX = Gdx.graphics.getWidth();  //setting variables for easy acces to screen dimensions
        screenY = Gdx.graphics.getHeight();
        font = new BitmapFont();   //declaring font for later text drawing
        font.getData().setScale(2f);

        for(int x = 0; x < 10; x++) { //creating a starting 10 by 5 swarm of enemy aliens
            for(int y = 0; y < 5; y++) {
                Alien tempAlien = new Alien(x * 40, screenY - y * 30 - 55, 4 - y);
                swarm.add(tempAlien);
            }
        }
        //Declaring individual sounds for different deaths
        playerDeath = Gdx.audio.newSound(Gdx.files.internal("playerDeath.wav"));
        alienDeath = Gdx.audio.newSound(Gdx.files.internal("alienDeath.wav"));
        bossDeath = Gdx.audio.newSound(Gdx.files.internal("bossDeath.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("ArcadeLoop.mp3"));
        //playing background music
        music.play();
        music.setLooping(true);

        player = new PlayerShip(); //Creating the first instance of the player controlled ship
        for(int i = 0; i < 3; i++) { //Creating 3 bullet blocking shields for the player to hide under
            Shield tempShield = new Shield(70 + i * 200, 80);
            shields.add(tempShield);
        }


    }






    @Override
    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
