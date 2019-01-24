package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Ship {

    public Sprite shipSprite;

    public static final int SPEED = 5;
    public static final int RIGHT = 1;
    public static final int LEFT = -1;

    public Ship(){

    }

    public static void move(Sprite sprite) {

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && sprite.getX()+sprite.getWidth() < Gdx.graphics.getWidth()){
            sprite.translateX(SPEED * RIGHT);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && sprite.getX() > 0) {
            sprite.translateX(SPEED * LEFT);
        }
    }

    public Bullet shoot() {
        //Creates a Bullet object belonging to the player and shoots it from their location
        Bullet shot = new Bullet(Bullet.UP, shipSprite.getX() + shipSprite.getWidth() / 2, shipSprite.getY());
        return shot;
    }
}
