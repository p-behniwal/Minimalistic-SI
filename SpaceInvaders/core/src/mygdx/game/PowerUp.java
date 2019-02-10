package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PowerUp {
	private Texture invinTex = new Texture("invin.png");
	private Texture fireUpTex = new Texture("fireUp.png");
	private Texture laserTex = new Texture("laser.png");
	private Texture lifeTex = new Texture("playerShip.png");
	private Sprite powerSprite;
	private int type;
	
	public static final int MOVESPEED = -3;
	//Different variables for easy access to the powerup types
	public static final int INVIN = 1;
	public static final int FIREUP = 2;
	public static final int LASER = 3;
	public static final int LIFE = 4;
	
	public PowerUp(float x, float y, int type) {
		//Constructor method for powerups
		this.type = type;
		if(type == INVIN) { //changing the sprite based on which powerup this is
			powerSprite = new Sprite(invinTex);
		} else if(type == FIREUP) {
			powerSprite = new Sprite(fireUpTex);
		} else if(type == LASER) {
			powerSprite = new Sprite(laserTex);
		} else {
			powerSprite = new Sprite(lifeTex);
		}
		
		powerSprite.setSize(30, 30);
		powerSprite.setPosition(x, y);
	}
	
	public void move() {
		//Moving the powerup
		powerSprite.translateY(MOVESPEED);
	}
	
	public int getType() {
		//returns the type of the powerup
		return type;
	}
	
	public Sprite getSprite() {
		//returns the powerup's sprite
		return powerSprite;
	}
}
