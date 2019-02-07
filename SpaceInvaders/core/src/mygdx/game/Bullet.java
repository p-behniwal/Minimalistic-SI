//Creates and acts out bullet actions from both the player and the enemies
package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
    private int dir;
    private float x;
    private float y;
    private Sprite bulletSprite;
    private Texture bulletImg;


    public static final int DOWN = -1;
    public static final int UP = 1;
    public static final int SPEED = 5;

    public Bullet(int direction, float xPos, float yPos) {
        //Constructor method
        dir = direction;
        x = xPos;
        y = yPos;
        bulletImg = new Texture("_invaderIMG6.png");
        bulletSprite = new Sprite(bulletImg);
        bulletSprite.setPosition(x,y);
    }

    public void move() {
        //Moves the bullet
        bulletSprite.translateY(SPEED * dir);

    }

    public int collide(Alien alien) {
        //Checks if an alien is hit by this bullet, and if so, returns the appropriate score increase
        int scoreIncrease = 0;
        Rectangle aHurtbox = alien.getSprite().getBoundingRectangle();
        Rectangle bHitbox = bulletSprite.getBoundingRectangle();
        if(bHitbox.overlaps(aHurtbox) && dir == UP) {
        	System.out.println("duck");
        	scoreIncrease = 100 * (alien.getSpecies() + 1);
        }
        return scoreIncrease;
    }

    public boolean collide(PlayerShip player) { 
        //Checks if this bullet has collided with the player, and if so, returns true signifying that the player is dead
        boolean collided = false;
        Rectangle pHurtbox = player.getSprite().getBoundingRectangle();
        Rectangle bHitbox = bulletSprite.getBoundingRectangle();
        if(dir == DOWN && bHitbox.overlaps(pHurtbox)) {
        	collided = true;
        }
        return collided;
    }

    public float getX() {
        return x;
    }
    
    public int getDir() {
    	return dir;
    }
    
    public float getY() {
        return y;
    }
    public Sprite getSprite() {
        return bulletSprite;
    }
}