package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	Texture projectile;
	Sprite shipSprite;
	Alien tempAlien;
	Alien tempAlien2;
	Music music;
	
	
	
	
	//Sound playerLaser = Gdx.audio.newSound(Gdx.files.internal("laser.wav")); 
	BitmapFont font;
	int score = 0;
	int lives = 3;
	int playerCooldown = 60;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		screenX = Gdx.graphics.getWidth();
		screenY = Gdx.graphics.getHeight();
		
		font = new BitmapFont(); 
		font.getData().setScale(2f); 
		
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 5; y++) {
				Alien tempAlien = new Alien(x * 40, screenY - y * 30 - 55, 4 - y);
				swarm.add(tempAlien);
			}
		}
		music = Gdx.audio.newMusic(Gdx.files.internal("ArcadeLoop.mp3"));
		music.play();
		music.setLooping(true);
		
		player = new PlayerShip();
		
		
	}

	@Override
	public void render () {
		//Moving and acting for Aliens
		for(int i = 0; i < swarm.size(); i++) {
			if(swarm.get(i).changeDir()) {
				for(int j = 0; j < swarm.size(); j++) {
					swarm.get(j).moveDown();
				}
			}
			int[] fireDraws = new int[swarm.get(i).getSpecies()];
			for(int j = 0; j < swarm.get(i).getSpecies(); j++) {
				fireDraws[j] = randint(0, 7000);
			}
			for(int j : fireDraws) {
				if(j == 1) {
					bullets.add(swarm.get(i).shoot());
				}
			}
			if(swarm.get(i).pastY(50)) {
				gameOver();
			}
		}
		for(int i = 0; i < swarm.size(); i++) {
			swarm.get(i).move();
		}
		
		player.move(); //Moving the player
		//Letting the player shoot and create bullets with restrictions
		int pBullets = 0;
		for(int i = 0; i < bullets.size(); i++) {
			if(bullets.get(i).getDir() == Bullet.UP) {
				pBullets++;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && pBullets < 2 && playerCooldown >= 40){
		    playerCooldown = 0;
            Bullet playerBullet = player.shoot();
			bullets.add(playerBullet);
		}
        playerCooldown++;
        //Moving and removing bullets as necessary
        ArrayList<Alien> deadAliens = new ArrayList<Alien>();
        ArrayList<Bullet> usedBullets = new ArrayList<Bullet>();
		for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).move();
            for(int j = 0; j < swarm.size(); j++) {
    			int scoreInc = bullets.get(i).collide(swarm.get(j));
    			if(scoreInc != 0) {
    				usedBullets.add(bullets.get(i));
    				deadAliens.add(swarm.get(j));
    				score += scoreInc;
    			}
            }
    		if(bullets.get(i).collide(player)) {
    			usedBullets.add(bullets.get(i));
    			lives--;
    		}
    		
			if(bullets.get(i).getSprite().getY() > Gdx.graphics.getHeight() || bullets.get(i).getSprite().getY() < 0){
				usedBullets.add(bullets.get(i));
			}
		}
		bullets.removeAll(usedBullets);
		swarm.removeAll(deadAliens);
		
		batch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Displaying relevant text on screen
		font.draw(batch, "Highscore: " + "", screenX - 600, screenY - 10);
		font.draw(batch, "Score: " + score, screenX - 200, screenY - 10); 
		font.draw(batch, "Lives: " + lives, screenX - 150, 35);
		
		//Drawing all aliens
		for(int i = 0; i < swarm.size(); i++) {
			swarm.get(i).getSprite().draw(batch);
		}
		
		//Drawing the player
		player.getSprite().draw(batch);
		
		//Drawing all bullets
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).getSprite().draw(batch);
		}
		
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