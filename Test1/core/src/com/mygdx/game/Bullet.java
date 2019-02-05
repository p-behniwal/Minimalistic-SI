//Creates and acts out bullet actions from both the player and the enemies
package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.*;

public class Bullet {
    private int dir;
    private float x;
    private float y;
    private Sprite bulletSprite;
    private Texture bulletImg;
    //private Rect hitbox;

    public static final int DOWN = 1;
    public static final int UP = -1;
    public static final int SPEED = 5;
    private static final int LENGTH = 3;
    private static final int HEIGHT = 10;

    public Bullet(int direction, float xPos, float yPos) {
        //Constructor method
        dir = direction;
        x = xPos;
        y = yPos;
        //Rect hitbox = new Rect(x, y, LENGTH, HEIGHT);
        bulletImg = new Texture("_invaderIMG6.png");
        bulletSprite = new Sprite(bulletImg);
        bulletSprite.setPosition(x,y);
    }

    public void move() {
        //Moves the bullet
        bulletSprite.translateY(SPEED);

    }

    public int collide(ArrayList<Alien> aliens) {
        //Checks if an alien is hit by this bullet, and if so, returns the appropriate score increase
        int scoreIncrease = 0;
        for(Alien a : aliens) {
            if(dir == UP) {

            }
            //Rectangle collision code
        }
        return scoreIncrease;
    }

    public boolean collide() { //Place rect object in there (player hurtbox)
        //Checks if this bullet has collided with the player, and if so, returns true signifying that the player is dead
        boolean collided = false;
        if(dir == DOWN) {
            //Rect collision code
        }
        return collided;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public Sprite getSprite() {

        return bulletSprite;
    }
}