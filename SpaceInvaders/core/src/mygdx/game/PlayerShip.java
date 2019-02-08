package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

//Creates, moves, an acts out player ship actions based on input received in SpaceInvaders.java
public class PlayerShip {
	private Sprite shipSprite;
	private Texture shipPic;
	private boolean invin;
	
	
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
		//Moves the player left or right based on input taken from SpaceInvaders.java
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && shipSprite.getX() + shipSprite.getWidth() < Gdx.graphics.getWidth()) {
			shipSprite.translateX(SPEED * RIGHT);
		} else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && shipSprite.getX() > 0) {
			shipSprite.translateX(SPEED * LEFT);
		}
	}
	
	public Bullet shoot() {
        //Creates a Bullet object belonging to the player and shoots it from their location
        Bullet bullet = new Bullet(Bullet.UP,shipSprite.getX(),shipSprite.getY());
        return bullet;
    }
	
	public void setInvin(boolean state) {
		invin = state;
	}
	
	public boolean isInvin() {
		return invin;
	}
	
	public Sprite getSprite() {
		return shipSprite;
	}
}
