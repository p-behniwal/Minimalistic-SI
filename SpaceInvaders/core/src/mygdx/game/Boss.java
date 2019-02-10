package com.mygdx.game;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Boss {
	private Texture bossTex = new Texture("boss.png");
	private Sprite bossSprite;
	private int moveDir = 1;
	private int health;
	
	private static final int MOVESPEED = 1;
	
	public Boss(int level, float screenY) {
		bossSprite = new Sprite(bossTex);
		bossSprite.setSize(500, 200);
		bossSprite.setPosition(10, screenY - bossSprite.getHeight() - 50);
		health = 3 * level;
	}
	
	public void move() {
		bossSprite.translateX(MOVESPEED * moveDir);
		if(bossSprite.getX() + bossSprite.getWidth() + MOVESPEED * moveDir >= Gdx.graphics.getWidth() || bossSprite.getX() + MOVESPEED * moveDir < 0) {
			moveDir *= -1;
		}
	}
	
	public ArrayList<Bullet> shoot(int level){
		ArrayList<Bullet> newBullets = new ArrayList<Bullet>();
		if(level % 10 == 0) {
			newBullets.add(new Bullet(Bullet.DOWN, bossSprite.getX() + bossSprite.getWidth() / 2, bossSprite.getY() + bossSprite.getHeight()));
		} 
		newBullets.add(new Bullet(Bullet.DOWN, bossSprite.getX(), bossSprite.getY() + bossSprite.getHeight()));
		newBullets.add(new Bullet(Bullet.DOWN, bossSprite.getX() + bossSprite.getWidth(), bossSprite.getY() + bossSprite.getHeight()));
		newBullets.add(new Bullet(Bullet.DOWN, bossSprite.getX() + randint(bossSprite.getX(), bossSprite.getX() + bossSprite.getWidth()), bossSprite.getY() + bossSprite.getHeight()));
		
		return newBullets;
	}
	
	public void takeDamage(int level) {
		health--;
		if(health > 0) {
			bossSprite.setColor(1f, 1f - (3f * level - health) / (3f * level), 1f - (3f * level - health) / (3f * level), 1f);
		}
		
	}
	
	public boolean isDead() {
		boolean isDead = false;
		if(health <= 0) {
			isDead = true;
		}
		return isDead;
	}
	
	public Sprite getSprite() {
		return bossSprite;
	}
	
	public static int randint(float low, float high){ 
		return (int)(Math.random()*(high-low+1) + low); 
	}
}
