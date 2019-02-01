package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.*;

public class SpaceInvaders extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ArrayList<Alien> swarm = new ArrayList<Alien>();
	PlayerShip player;
	int screenX = 1024;
	int screenY = 768;
	ArrayList<Bullet> bullets;
	Texture projectile;
	Sprite shipSprite;

	
	
	
	
	//Music music = Gdx.audio.newMusic(Gdx.files.internal("battle.mp3"));
	//Sound playerLaser = Gdx.audio.newSound(Gdx.files.internal("laser.wav")); 
	BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Gdx.graphics.setWindowedMode(screenX, screenY);
		for(int x = 100; x < screenX - 100; x += 40) {
			for(int y = 100; y < 300; y += 40) {
				Alien tempAlien = new Alien(x, y, Alien.CIVILIAN);
				swarm.add(tempAlien);
			}
		}
		player = new PlayerShip();
		shipSprite = player.getSprite();
		
		
	}

	@Override
	public void render () {
		batch.begin();
		Alien.move(screenX);
		if(Alien.pastY(screenX - 200)) {
			gameOver();
		}
		/*for(int i = 0; i < swarm.size(); i++) {
			if(randint(0, 8) > swarm.get(i).getSpecies()) {
				bullets.add(swarm.get(i).shoot());
			}
		}*/
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shipSprite.draw(batch);
		
		player.move();

//		else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) shoot();

		batch.end();
		
		
		
		
		
		
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	
	public void gameOver() {
		Gdx.app.exit();
	}
	
	public static int randint(int low, int high){ 
		return (int)(Math.random()*(high-low+1) + low); 
	}
}