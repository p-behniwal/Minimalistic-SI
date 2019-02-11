package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

//Creates, moves, an acts out player ship actions based on input received in SpaceInvaders.java
public class PlayerShip {
    private Sprite shipSprite;
    private Texture shipPic;


    private Sound playerLaser = Gdx.audio.newSound(Gdx.files.internal("playerLaser.wav"));
    private Sound powerUp = Gdx.audio.newSound(Gdx.files.internal("powerup.wav"));


    public static final int SPEED = 5;
    public static final int RIGHT = 1;
    public static final int LEFT = -1;

    public PlayerShip() {
        //Constructor class for the ship to determine it's initial position
        shipPic = new Texture("playerShip.png");
        shipSprite = new Sprite(shipPic);				// create a Sprite from the shipPic texture
        shipSprite.setPosition(Gdx.graphics.getWidth()/2, 10);				// set initial position

    }

    public void move() {
        //Moves the player left or right based on player input, limited to edges of the screen
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && shipSprite.getX() + shipSprite.getWidth() < Gdx.graphics.getWidth()) {
            shipSprite.translateX(SPEED * RIGHT);
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && shipSprite.getX() > 0) {
            shipSprite.translateX(SPEED * LEFT);
        }
    }

    public Bullet shoot() {
        //Creates a Bullet object belonging to the player and shoots it from their location
        playerLaser.play(); //Plays the player shot sound effect
        Bullet bullet = new Bullet(Bullet.UP, shipSprite.getX() + shipSprite.getWidth() / 2, shipSprite.getY());
        return bullet;
    }

    public int collect(PowerUp power) {
        //Checks if a powerup has been collected, and returns which type it was. Returns 0 if none have been collected
        int type = 0;
        //Getting the rectangles for the player and given powerup
        Rectangle plHitbox = shipSprite.getBoundingRectangle();
        Rectangle poHitbox = power.getSprite().getBoundingRectangle();
        if(plHitbox.overlaps(poHitbox)) { //Checks if the rectangles collide with each other
            type = power.getType(); //Gets the powerup's type
            powerUp.play(); //Plays the powerup sound effect
        }
        return type;
    }

    public Sprite getSprite() {
        //Returns the ship's sprite
        return shipSprite;
    }
}