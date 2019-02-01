package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

import static com.mygdx.game.Ship.SPEED;

public class SpaceInvaders extends ApplicationAdapter {
	SpriteBatch batch;
	Texture shipPic;
	Texture bulletImg;
	Sprite shipSprite;
	Sprite bulletSprite;
	//screen x and y

	
	@Override
	public void create () {
		batch = new SpriteBatch();

        Ship mainShip = new Ship();
		shipPic = new Texture("_invaderIMG7.png");
		shipSprite = new Sprite(shipPic);				// create a Sprite from your shipPic texture
		shipSprite.setPosition(Gdx.graphics.getWidth()/2, 10);				// set initial position

        bulletImg = new Texture("_invaderIMG6.png");
        bulletSprite = new Sprite(bulletImg);
        bulletSprite.setPosition(shipSprite.getX()+shipSprite.getWidth()/2,shipSprite.getY());

//        bulletSprite = new Sprite(projectile);


//		for(int i = 0; i<=10; i++){
//			for(int j = 0; j<=5; j++){
//
//			}
//		}

	}

	@Override
	public void render () {
		System.out.println(shipSprite.getX() + ", " + shipSprite.getY());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



		batch.begin();
		shipSprite.draw(batch);
		Ship.move(shipSprite);

//		else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) shoot();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shipPic.dispose();
	}
}
