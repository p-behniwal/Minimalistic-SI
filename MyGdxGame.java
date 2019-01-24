package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

import static com.mygdx.game.Ship.SPEED;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture shipPic;
	Texture projectile;
	Sprite shipSprite;
	Sprite bulletSprite;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		shipPic = new Texture("_invaderIMG7.png");
		Ship mainShip = new Ship();
		shipSprite = new Sprite(shipPic);				// create a Sprite from your shipPic texture
		shipSprite.setPosition(Gdx.graphics.getWidth()/2, 10);				// set initial position

//        projectile = new Texture("lollipop_pink.png");
//        bulletSprite = new Sprite(projectile);
//        bulletSprite.setPosition(mainShip.getX()+);


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
