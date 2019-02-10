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
	//Declaring variables that are changed later in code
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
	PowerUp power;
	Sound alienDeath;
	Sound playerDeath;
	Sound bossDeath;
	Boss boss;
	BitmapFont font;
	//Declaring starting values for counter variables
	int score = 0;
	int lives = 3;
	int playerCooldown = 60;
	int respawnTimer = 0;
	int invinTimer = 0;
	int level = 1;
	int maxBullets = 1;
	int laserTimer = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		//setting variables for easy acces to screen dimensions
		screenX = Gdx.graphics.getWidth();
		screenY = Gdx.graphics.getHeight();
		//declaring font for later text drawing
		font = new BitmapFont(); 
		font.getData().setScale(2f); 
		
		for(int x = 0; x < 10; x++) { //creating a starting 10 by 5 swarm of enemy aliens
			for(int y = 0; y < 5; y++) {
				Alien tempAlien = new Alien(x * 40, screenY - y * 30 - 55, 4 - y);
				swarm.add(tempAlien);
			}
		}
		//Declaring individual sounds for different deaths
		playerDeath = Gdx.audio.newSound(Gdx.files.internal("playerDeath.wav"));
		alienDeath = Gdx.audio.newSound(Gdx.files.internal("alienDeath.wav")); 
		bossDeath = Gdx.audio.newSound(Gdx.files.internal("bossDeath.wav")); 
		music = Gdx.audio.newMusic(Gdx.files.internal("ArcadeLoop.mp3"));
		//playing background music
		music.play();
		music.setLooping(true);
		
		player = new PlayerShip(); //Creating the first instance of the player controlled ship
		for(int i = 0; i < 3; i++) { //Creating 3 bullet blocking shields for the player to hide under
			Shield tempShield = new Shield(70 + i * 200, 80);
			shields.add(tempShield);
		}
		
		
	}

	@Override
	public void render () {
		if(swarm.isEmpty() && boss == null) {//Checking if a level has been cleared
			level++; //Increasing the level
			int randPower = randint(1, 10); //Using a random number to determine a powerup to give to the player
			int powerType;
			//Determining probability of each item spawn
			if(randPower < 5) {
				powerType = PowerUp.FIREUP;
			} else if(randPower < 8) {
				powerType = PowerUp.INVIN;
			} else if(randPower < 10) {
				powerType = PowerUp.LASER;
			} else {
				powerType = PowerUp.LIFE;
			}
			power = new PowerUp(screenX / 2, screenY, powerType); //Creating the powerup to fall from the top of the screen
			if(level % 5 == 0) { //Creating a boss enemy at every 5th level
				boss = new Boss(level, screenY);
			} else { //Creating a new 10 by 5 swarm of aliens at every other level
				for(int x = 0; x < 10; x++) {
					for(int y = 0; y < 5; y++) {
						Alien tempAlien = new Alien(x * 40, screenY - y * 30 - 55, 4 - y);
						swarm.add(tempAlien);
					}
				}
			}
		}
		if(lives == 0) { //checking if the player has lost all of their lives
			gameOver();
		}
		if(player == null) { //Checking if the player is dead
			respawnTimer++;
			if(respawnTimer >= 60) { //respawning the player after 60 frames
				player = new PlayerShip(); //recreating the player
				invinTimer = 60; //Giving the player 60 frames of invincibility upon respawn
				respawnTimer = 0; //resetting the spawn timer to make future respawns possible
			}
		} else { //Doing all player actions only when ensuring the player exists
			if(invinTimer > 0) { //reducing the invincibility timer if it is not 0 to prevent infinite invincibiity
				invinTimer--;
			}
			if(power != null) { //Checking if the player has collided with an item if there is an item on screen
				int powerCollected = player.collect(power); //variable that checks which powerup has been collected, return 0 if none have been collected
				if(powerCollected != 0) {
					power = null; //Getting rid of the powerup upon collection
					if(powerCollected == PowerUp.FIREUP) {
						maxBullets++; //Increaing the amount of player bullets allowed on screen for a fireup powerup
					} else if(powerCollected == PowerUp.INVIN) {
						invinTimer = 300; //giving 300 frames of invincibility for an invincibility powerup
					} else if(powerCollected == PowerUp.LASER) {
						laserTimer = 60; //Giving 60 frames of unrestricted fire power for a laser powerup
					} else if(powerCollected == PowerUp.LIFE) {
						lives++; //Giving the player an extra life for an extra life powerup
					}
				}
				
			}
			player.move(); //Moving the player
			//Letting the player shoot and create bullets with restrictions
			int pBullets = 0; //Counter for amount of player bullets on screen
			for(int i = 0; i < bullets.size(); i++) { 
				if(bullets.get(i).getDir() == Bullet.UP) { //Checks the amount of player shot bullets on screen
					pBullets++;
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && ((pBullets < maxBullets && playerCooldown >= 10) || laserTimer > 0)){
				//Shoots a bullet from the player if they press space while not letting them shoot if they have reached their max number of bullets on screen (determined by powerups) and making them wait 10 frames between shots
				//These restrictions can be bypassed by the laser powerup
			    playerCooldown = 0;
				bullets.add(player.shoot()); //Adding a player bullet to all bullets
			    if(laserTimer > 0) { //preventing infinite laser
					laserTimer--;
				}
			}
		}
		playerCooldown++;//increasing the player cooldown timer so that they can shoot again later
		
		//Moving and acting for Aliens 
		for(int i = 0; i < swarm.size(); i++) {
			if(swarm.get(i).changeDir()) { //Checking if one alien has reached an edge of the screen, which changes their direction
				for(int j = 0; j < swarm.size(); j++) {
					swarm.get(j).moveDown(); //Moving all of the aliens down a step
				}
			}
			int[] fireDraws = new int[swarm.get(i).getSpecies() * level]; //Array used to hold all random numbers for determing the fire rate of aliens
			//The number of numbers generated per alien depends on their aggressiveness or species, and the level of the game
			for(int j = 0; j < swarm.get(i).getSpecies() * level; j++) {
				fireDraws[j] = randint(0, 10000); //Generating the random number draws
			}
			for(int j : fireDraws) {
				if(j == 0) { //Firing a bullet from this alien if one of their draws is 0
					bullets.add(swarm.get(i).shoot());
				}
			}
			if(swarm.get(i).pastY(50)) { //Giving the player a game over if the aliens reach too far down on the screen
				gameOver();
			}
			swarm.get(i).move(); //Moving all of the aliens
		}
		
		if(bonusAlien == null) { //Giving a random chance for a bonus alien to spawn if there is already none on the screen
			if(randint(0, 6000) == 1) {
				bonusAlien = new Ufo(screenY);
			}
		} else { 
			bonusAlien.move(); //Moving the bonus alien if it exists
			if(bonusAlien.getSprite().getX() > screenX + 30) { //Destroying the alien if it moves too far 
				bonusAlien = null;
			}
		}
		
		if(boss != null) {//Doing boss actions if there is one on screen
			boss.move(); //Moves the boss back and forth
			if(randint(1, 800 / level) == 1) { //Random chance of shooting that gets more common each level
				bullets.addAll(boss.shoot(level));
			}
		}
		
		if(power != null) {//Doing actions to the power up if it is on screen
			power.move(); //Moving the poweup
			if(power.getSprite().getY() < 0) { //Destroying the powerup if it moves offscreen
				power = null;
			}
		}
		
        //Moving and removing bullets as necessary
		//ArrayLists sed to remove dead aliens and used bullets from the main aliens and bullets lists
        ArrayList<Alien> deadAliens = new ArrayList<Alien>();
        ArrayList<Bullet> usedBullets = new ArrayList<Bullet>();
		for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).move(); //Moving all bullets
            for(int j = 0; j < swarm.size(); j++) { //Checking for collision with each alien
    			int scoreInc = bullets.get(i).collide(swarm.get(j)) * level; //Getting a score increase determined by the aliens species and the game's level, returns 0 if no aliens are shot
    			if(scoreInc != 0) { //Checking if this bullet collided with this alien
    				//Adding this alien and this bullet to the removal lists
    				usedBullets.add(bullets.get(i));
    				deadAliens.add(swarm.get(j));
    				alienDeath.play(); //Playing the alien death sound
    				score += scoreInc; //Increasing the total score
    			}
            }
            
            if(bonusAlien != null) { //Checking for collision with the bonus alien if it is on screen
            	if(bullets.get(i).collide(bonusAlien)) {
        			usedBullets.add(bullets.get(i)); //Adding the bullet to the removal list
        			score += 1000 * level; //Increasing the score
        			if(power == null) { //Making sure no other powerups are already on screen before creating the powerup
        				int randPower = randint(1, 10); //Giving the player a powerup for killing the bonus alien
            			int powerType;
            			if(randPower < 5) {
            				powerType = PowerUp.FIREUP;
            			} else if(randPower < 8) {
            				powerType = PowerUp.INVIN;
            			} else if(randPower < 10) {
            				powerType = PowerUp.LASER;
            			} else {
            				powerType = PowerUp.LIFE;
            			}
        				power = new PowerUp(bonusAlien.getSprite().getX(), bonusAlien.getSprite().getY(), powerType);
        			}
        			bonusAlien = null; //Killing the bonusalien
        			
        		}
            }
            
            if(boss != null) {//Checking for bullet collisions on the boss only if there is one on screen
            	if(bullets.get(i).collide(boss)) {
            		usedBullets.add(bullets.get(i)); //removing bullet
            		boss.takeDamage(level); //Taking damage for the boss
        			if(boss.isDead()) { //Killing the boss if it has no health left
        				bossDeath.play();
        				boss = null;
        			}

            	}
            }
            
            if(player != null) { //Checking for bullet collisions with the player if they are not dead
            	if(bullets.get(i).collide(player) && invinTimer <= 0) { //Making sure the bullet is actually hitting the player and that they are not invincible
            		playerDeath.play(); //Plays the player death sound
            		maxBullets = 1; //Resetting the player's maximum bullet count
        			usedBullets.add(bullets.get(i)); //Removing the bullet that shot the player
        			lives--;
        			player = null;
        		}
            }
            for(int j = 0; j < shields.size(); j++) { //Checking collisions on each shield
            	int shieldPiece = bullets.get(i).collide(shields.get(j)); //Checking which shield piece got hit, returns -1 if none got hit
            	if(shieldPiece != -1) { //Taking damage on a given shield piece and removing the bullet if it does hit a given shield piece
                	shields.get(j).takeDamage(shieldPiece);
                	usedBullets.add(bullets.get(i));
                }
            }
    		
			if(bullets.get(i).getSprite().getY() > Gdx.graphics.getHeight() || bullets.get(i).getSprite().getY() < 0){ //Remoing the bullets that go offscreen
				usedBullets.add(bullets.get(i));
			}
		}
		//Disposing of the appropriate aliens and bullets
		bullets.removeAll(usedBullets);
		swarm.removeAll(deadAliens);
		
		batch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Displaying relevant text on screen
		font.draw(batch, "Highscore: " + "", 50, screenY - 10);
		font.draw(batch, "Score: " + score, screenX - 200, screenY - 10); 
		font.draw(batch, "Lives: " + lives, screenX - 150, 35);
		font.draw(batch, "Level: " + level, 50, 35);
		
		//Drawing all aliens
		for(int i = 0; i < swarm.size(); i++) {
			swarm.get(i).getSprite().draw(batch);
		}
		
		if(bonusAlien != null) {
        	bonusAlien.getSprite().draw(batch);
        }
		
		if(boss != null) {
			boss.getSprite().draw(batch);
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
		
		//Drawing powerups
		if(power != null) {
        	power.getSprite().draw(batch);
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