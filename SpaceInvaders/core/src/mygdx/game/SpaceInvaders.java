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
	Ufo bonusAlien;
	ArrayList<Shield> shields = new ArrayList<Shield>();
	
	
	
	
	//Sound playerLaser = Gdx.audio.newSound(Gdx.files.internal("laser.wav")); 
	BitmapFont font;
	int score = 0;
	int lives = 3;
	int playerCooldown = 60;
	int respawnTimer = 0;
	int invinTimer = 0;
	int level = 1;
	
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
		for(int i = 0; i < 3; i++) {
			Shield tempShield = new Shield(50 + i * 200, 80);
			shields.add(tempShield);
		}
		
		
	}

	@Override
	public void render () {
		//Doing appropriate things to work on respawning a dead player
		if(swarm.isEmpty()) {
			level++;
			for(int x = 0; x < 10; x++) {
				for(int y = 0; y < 5; y++) {
					Alien tempAlien = new Alien(x * 40, screenY - y * 30 - 55, 4 - y);
					swarm.add(tempAlien);
				}
			}
		}
		if(lives == 0) {
			gameOver();
		}
		if(player == null) {
			respawnTimer++;
			if(respawnTimer >= 60) { //respawning the player after 60 frames
				player = new PlayerShip(); //recreating the player
				player.setInvin(true); //Making the player invincible for a while on respawn
				respawnTimer = 0; //resetting the spawn timer to make future respawns possible
			}
		} else { //Doing all player actions only when ensuring the player exists
			if(player.isInvin()) { //doing appropriate actions to make sure the player isn't invincible forever on respawn
				invinTimer++;
				if(invinTimer >= 60) { //Making the player not invincible after 60 frames
					player.setInvin(false);
					invinTimer = 0; //Resetting invincibility timer 
				}
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
		}
		
		//Moving and acting for Aliens 
		for(int i = 0; i < swarm.size(); i++) {
			if(swarm.get(i).changeDir()) {
				for(int j = 0; j < swarm.size(); j++) {
					swarm.get(j).moveDown();
				}
			}
			int[] fireDraws = new int[swarm.get(i).getSpecies() * level];
			for(int j = 0; j < swarm.get(i).getSpecies() * level; j++) {
				fireDraws[j] = randint(0, 10000);
			}
			for(int j : fireDraws) {
				if(j == 0) {
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
		if(bonusAlien == null) {
			if(randint(0, 18000) == 1) {
				bonusAlien = new Ufo(screenY);
			}
		} else {
			bonusAlien.move();
			if(bonusAlien.getSprite().getX() > screenX + 30) {
				bonusAlien = null;
			}
		}
		
		
		
		
        playerCooldown++;
        //Moving and removing bullets as necessary
        ArrayList<Alien> deadAliens = new ArrayList<Alien>();
        ArrayList<Bullet> usedBullets = new ArrayList<Bullet>();
		for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).move();
            for(int j = 0; j < swarm.size(); j++) {
    			int scoreInc = bullets.get(i).collide(swarm.get(j)) * level;
    			if(scoreInc != 0) {
    				usedBullets.add(bullets.get(i));
    				deadAliens.add(swarm.get(j));
    				score += scoreInc;
    			}
            }
            
            if(bonusAlien != null) {
            	if(bullets.get(i).collide(bonusAlien)) {
        			usedBullets.add(bullets.get(i));
        			score += 1000 * level;
        			bonusAlien = null;
        		}
            }
            
            if(player != null) {
            	if(bullets.get(i).collide(player) && !player.isInvin()) {
        			usedBullets.add(bullets.get(i));
        			lives--;
        			player = null;
        		}
            }
            for(int j = 0; j < shields.size(); j++) {
            	int shieldPiece = bullets.get(i).collide(shields.get(j));
            	if(shieldPiece != -1) {
                	shields.get(j).takeDamage(shieldPiece);
                	usedBullets.add(bullets.get(i));
                }
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
		
		if(bonusAlien != null) {
        	bonusAlien.getSprite().draw(batch);
        }
		
		//Drawing the player
		if(player != null && invinTimer % 2 == 0) {
			player.getSprite().draw(batch);
		}
		
		
		//Drawing all bullets
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).getSprite().draw(batch);
		}
		
		//Drawing all shields
		for(int i = 0; i < shields.size(); i++) {
			for(int j = 0; j < shields.get(i).getSprites().length; j++) {
				shields.get(i).getSprites()[j].draw(batch);
			}
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