package com.mygdx.game;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Boss {
    private Texture bossTex = new Texture("boss.png");
    private Sprite bossSprite;
    private int moveDir = 1;
    private int health;

    private static final int MOVESPEED = 1;

    public Boss(int level, float screenY) {
        //Constructor method for bosses
        bossSprite = new Sprite(bossTex);
        bossSprite.setSize(500, 200);
        bossSprite.setPosition(10, screenY - bossSprite.getHeight() - 50);
        health = 3 * level; //Setting the boss's health based on the game's level
    }

    public void move() {
        //Moving the boss and checking for collision with a screen edge, which changes it's direction
        bossSprite.translateX(MOVESPEED * moveDir);
        if(bossSprite.getX() + bossSprite.getWidth() + MOVESPEED * moveDir >= Gdx.graphics.getWidth() || bossSprite.getX() + MOVESPEED * moveDir < 0) {
            moveDir *= -1;
        }
    }

    public ArrayList<Bullet> shoot(int level){
        //Shoots bullets from the boss
        ArrayList<Bullet> newBullets = new ArrayList<Bullet>(); //creates an arraylist to return to be added to main list
        if(level % 10 == 0) { //creates an aditional middle bullet for every other boss to make them more challenging
            newBullets.add(new Bullet(Bullet.DOWN, bossSprite.getX() + bossSprite.getWidth() / 2, bossSprite.getY() + bossSprite.getHeight()));
        }
        //Creating a bullet at each edge of the boss, and one more randomnly in between
        newBullets.add(new Bullet(Bullet.DOWN, bossSprite.getX(), bossSprite.getY() + bossSprite.getHeight()));
        newBullets.add(new Bullet(Bullet.DOWN, bossSprite.getX() + bossSprite.getWidth(), bossSprite.getY() + bossSprite.getHeight()));
        newBullets.add(new Bullet(Bullet.DOWN, bossSprite.getX() + randint(bossSprite.getX(), bossSprite.getX() + bossSprite.getWidth()), bossSprite.getY() + bossSprite.getHeight()));

        return newBullets;
    }

    public void takeDamage(int level) {
        //Taking damage for the boss
        health--;
        if(health > 0) {
            bossSprite.setColor(1f, 1f - (3f * level - health) / (3f * level), 1f - (3f * level - health) / (3f * level), 1f); //Formula to make the boss redder as it takes more damage
        }

    }

    public boolean isDead() {
        //Checks if the boss is dead by checking his health
        boolean isDead = false;
        if(health <= 0) {
            isDead = true;
        }
        return isDead;
    }

    public Sprite getSprite() {
        //returns the boss' sprite
        return bossSprite;
    }

    public static int randint(float low, float high){
        return (int)(Math.random()*(high-low+1) + low);
    }
}