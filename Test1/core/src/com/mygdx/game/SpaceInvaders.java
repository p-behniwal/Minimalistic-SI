package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

import java.util.ArrayList;

import static com.mygdx.game.Ship.SPEED;

public class SpaceInvaders extends ApplicationAdapter {
	SpriteBatch batch;
	Texture shipPic;
	Texture bulletImg;
	Sprite shipSprite;
	Sprite bulletSprite;
	Ship mainShip;
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	//screen x and y

	int playerCooldown = 60;
	@Override
	public void create () {
		batch = new SpriteBatch();

        mainShip = new Ship();



//        bulletSprite = new Sprite(projectile);


//		for(int i = 0; i<=10; i++){
//			for(int j = 0; j<=5; j++){
//
//			}
//		}

	}

	@Override
	public void render () {
//		System.out.println(shipSprite.getX() + ", " + shipSprite.getY());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mainShip.move();
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && bullets.size() < 2 && playerCooldown >= 40){
		    playerCooldown = 0;
            Bullet playerBullet = mainShip.shoot();
			bullets.add(playerBullet);
		}
        playerCooldown++;
		for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).move();

			if(bullets.get(i).getSprite().getY() > Gdx.graphics.getHeight()){
				bullets.remove(i);
			}
		}


		batch.begin();
		mainShip.getSprite().draw(batch);

		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).getSprite().draw(batch);
		}




		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		mainShip.getTexture().dispose();
	}
}
