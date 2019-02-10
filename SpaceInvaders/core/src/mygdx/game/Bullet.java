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
    public static final int SPEED = 8;

    public Bullet(int direction, float xPos, float yPos) {
        //Constructor method
        dir = direction;
        x = xPos;
        y = yPos;
        bulletImg = new Texture("bullet.png");
        bulletSprite = new Sprite(bulletImg);
        bulletSprite.setSize(3, 10);
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
        if(bHitbox.overlaps(aHurtbox) && dir == UP) { //making sure to only check for player bullets
        	scoreIncrease = 100 * (alien.getSpecies() + 1); //score based on the alien's species
        }
        return scoreIncrease;
    }

    public boolean collide(PlayerShip player) { 
        //Checks if this bullet has collided with the player, and if so, returns true signifying that the player is dead
        boolean collided = false;
        Rectangle pHurtbox = player.getSprite().getBoundingRectangle();
        Rectangle bHitbox = bulletSprite.getBoundingRectangle();
        if(dir == DOWN && bHitbox.overlaps(pHurtbox)) { //making sure to only check for alien bullets
        	collided = true;
        }
        return collided;
    }
    
    public boolean collide(Ufo bonusAlien) {
    	//Checks if this bullet has collided with a bonus alien, if so, returns true
    	boolean collided = false;
    	Rectangle aHurtbox = bonusAlien.getSprite().getBoundingRectangle();
        Rectangle bHitbox = bulletSprite.getBoundingRectangle();
        if(bHitbox.overlaps(aHurtbox) && dir == UP) { //making sure to only check for player bullets
        	collided = true;
        }
        return collided;
    }
    
    public int collide(Shield shield) {
    	//Checks if this bullet has collided with a shield, and if so, returns which piece, if not, returns -1
    	int shieldPos = -1;
    	Rectangle bHitbox = bulletSprite.getBoundingRectangle();
    	for(int i = 0; i < shield.getSprites().length; i++) {
    		Rectangle sHurtbox = shield.getSprites()[i].getBoundingRectangle();
    		if(bHitbox.overlaps(sHurtbox) && shield.getHealths()[i] != 0) { //Making sure the shield is still healthy before collision
            	shieldPos = i;
            }
    	}
    	return shieldPos;
    }
    
    public boolean collide(Boss boss) {
    	//Checking if this bullet collided with a boss, and if so, returns true
    	boolean collided = false;
    	Rectangle boHurtbox = boss.getSprite().getBoundingRectangle();
        Rectangle bHitbox = bulletSprite.getBoundingRectangle();
        if(bHitbox.overlaps(boHurtbox) && dir == UP) { //making sure to only check for player bullets
        	collided = true;
        }
        return collided;
    }

    public int getDir() {
    	//Returns the direction of this bullet
    	return dir;
    }
    
    public Sprite getSprite() {
    	//Returns this bullet's sprite
        return bulletSprite;
    }
}