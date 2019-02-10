package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ufo {
	private Sprite ufoSprite;
	private Texture ufoTex;
	
	final public static int MOVESPEED = 2;
	
	public Ufo(int screenY) {
		//Constructor methos for the bonus alien
		ufoTex = new Texture("ufo.png");
		ufoSprite = new Sprite(ufoTex);
		ufoSprite.setPosition(-30, screenY - 40);
	}
	
	public void move() {
		//Moves the ufo
		ufoSprite.translateX(MOVESPEED);
	}
	
	public Sprite getSprite() {
		//Returns the ufo's sprite
		return ufoSprite;
	}
}
