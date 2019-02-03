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
	ArrayList<Alien> swarm = new ArrayList<Alien>();
	PlayerShip player;
	int screenX;
	int screenY;
	ArrayList<Bullet> bullets;
	Texture projectile;
	Sprite shipSprite;
	Alien tempAlien;
	Alien tempAlien2;
	Music music;
	
	
	
	
	//Sound playerLaser = Gdx.audio.newSound(Gdx.files.internal("laser.wav")); 
	BitmapFont font;
	int score = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		screenX = Gdx.graphics.getWidth();
		screenY = Gdx.graphics.getHeight();
		
		font = new BitmapFont(); 
		font.getData().setScale(2f); 
		
		/*for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 5; y++) {
				Alien tempAlien = new Alien(x * 20, screenY - y * 20, y);
				swarm.add(tempAlien);
			}
		}*/
		music = Gdx.audio.newMusic(Gdx.files.internal("ArcadeLoop.mp3"));
		music.play();
		music.setLooping(true);
		tempAlien = new Alien(20, screenY - 80, 3);
		tempAlien2 = new Alien(100, screenY - 80, 1);
		
		player = new PlayerShip();
		
		
	}

	@Override
	public void render () {
		batch.begin();
		/*for(int i = 0; i < swarm.size(); i++) {
			System.out.println(swarm.get(i).getSprite().getX() + " " + swarm.get(i).getSprite().getY());
			swarm.get(i).getSprite().draw(batch);
			swarm.get(i).move();
			if(swarm.get(i).pastY(10)) {
				gameOver();
			}
		}*/
		
		tempAlien.getSprite().draw(batch);
		tempAlien.move();
		tempAlien2.getSprite().draw(batch);
		tempAlien2.move();	
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		font.draw(batch, "Score: " + score, screenX - 200, screenY - 20); 
		player.getSprite().draw(batch);
		player.move();
		
		batch.end();
		
		
		
		
		
		
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		music.dispose();
	}
	
	public void gameOver() {
		Gdx.app.exit();
	}
	
	public static int randint(int low, int high){ 
		return (int)(Math.random()*(high-low+1) + low); 
	}
}