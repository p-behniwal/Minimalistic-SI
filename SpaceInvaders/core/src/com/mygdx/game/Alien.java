package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

//Class file for the creating, moving, killing, and acting for the alien enemies in Space Invaders

public class Alien {
	private static int moveDir;
	private int species;
	private Sprite alienSprite;
	private Texture alienTex;
	
	public static final int RIGHT = 1;
	public static final int LEFT = -1;
	public static final int MOVESPEED = 5;
	
	//Different alien species that determine shot frequency
	public static final int AGGRO = 4;
	public static final int WARRIOR = 3;
	public static final int ROOKIE = 2;
	public static final int CIVILIAN = 1;
	public static final int PACIFIST = 0;
	
	
	public Alien(int x, int y, int type) {
		//Constructor method, sets a specified position and starting direction for all aliens
		species = type;
		
		String alienImage = String.format("alien%d.png", type);
		alienTex = new Texture(alienImage);
		alienSprite = new Sprite(alienTex);
		alienSprite.setPosition(x, y);
		
		moveDir = RIGHT;
	}
	
	public void move() {
		//Moves the swarm of aliens
		if(alienSprite.getX() + alienSprite.getWidth() + MOVESPEED * moveDir>= Gdx.graphics.getWidth() || alienSprite.getX() + MOVESPEED * moveDir < 0) {
			alienSprite.translateY(-5);
			changeDir();
		}
		alienSprite.translateX(MOVESPEED * moveDir);
	}
	
	public static void changeDir() {
		//Changes the direction of all the aliens and moves them down
		moveDir *= -1;
	}

	public Bullet shoot() {
		Bullet shot = new Bullet(Bullet.DOWN, alienSprite.getX() + alienSprite.getWidth() / 2, alienSprite.getY() + alienSprite.getHeight());
		return shot;
	}
	
	public int getSpecies() {
		return species;
	}
	
	public Sprite getSprite() {
		return alienSprite;
	}
	
	public boolean pastY(int yPos) {
		boolean past = false;
		if(alienSprite.getY() < yPos) {
			past = true;
		}
		return past;
	}
}