package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

//Creates, moves, an acts out player ship actions based on input received in SpaceInvaders.java
public class PlayerShip {
	private Sprite shipSprite;
	private Texture shipPic;
	
	
	public static final int SPEED = 5;
	public static final int RIGHT = 1;
	public static final int LEFT = -1;
	
	public PlayerShip() {
		//Constructor class for the ship to determine it's initial position
		shipPic = new Texture("badlogic.jpg");
		shipSprite = new Sprite(shipPic);				// create a Sprite from your shipPic texture
		shipSprite.setPosition(Gdx.graphics.getWidth()/2, 10);				// set initial position
		
	}
	
	public void move() {
		//Moves the player left or right based on input taken from SpaceInvaders.java
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			shipSprite.translateX(SPEED * RIGHT);
		} else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			shipSprite.translateX(SPEED * LEFT);
		}
	}
	
	//public Bullet shoot() {
		//Creates a Bullet object belonging to the player and shoots it from their location
		//Bullet shot = new Bullet(Bullet.UP, x + SHIPLEN / 2, y);
		//return shot;
	//}
	
	public Sprite getSprite() {
		return shipSprite;
	}
}
