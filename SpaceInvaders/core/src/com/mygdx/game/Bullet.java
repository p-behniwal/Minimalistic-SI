package com.mygdx.game;
//Creates and acts out bullet actions from both the player and the enemies
import java.util.*;
public class Bullet {
	private int dir;
	private float x;
	private float y;
	//private Rect hitbox;
	
	public static final int DOWN = 1;
	public static final int UP = -1;
	public static final int SPEED = 5;
	
	public Bullet(int direction, float xPos, float yPos) {
		//Constructor method
		dir = direction;
		x = xPos;
		y = yPos;
		//Rect hitbox = new Rect(x, y, LENGTH, HEIGHT);
	}
	
	public void travel() {
		//Moves the bullet
		y += SPEED * dir;
		//Rect move function
	}
	
	public int collide(ArrayList<Alien> aliens) {
		//Checks if an alien is hit by this bullet, and if so, returns the appropriate score increase
		int scoreIncrease = 0;
		if(dir == UP) {
			for(Alien a : aliens) {
				
			}
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
}
