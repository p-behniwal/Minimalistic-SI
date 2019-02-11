package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.math.Rectangle;

public class MainMenuScreen implements Screen {
    SpriteBatch batch;
    private Texture spaceTexture;
    private Texture invadersTexture;
    private Sprite spaceSprite;
    private Sprite invadersSprite;
    private Texture newgametex;
    private Sprite newgamespr;

    private Texture[] alienTextures =  new Texture[4];
    private Sprite[] alienSprites = new Sprite[4];

    final SpaceInvaders game;

    OrthographicCamera camera;

    public MainMenuScreen(final SpaceInvaders game) {
        this.game = game;
        batch = new SpriteBatch();

        spaceTexture = new Texture("space.PNG");
        invadersTexture = new Texture("invaders.PNG");
        spaceSprite = new Sprite(spaceTexture);
        spaceSprite.setPosition(Gdx.graphics.getWidth()/2 - spaceSprite.getWidth()/2, 4*Gdx.graphics.getHeight()/5);
        invadersSprite = new Sprite(invadersTexture);
        invadersSprite.setPosition(Gdx.graphics.getWidth()/2-invadersSprite.getWidth()/2, 4*Gdx.graphics.getHeight()/5 - 2*spaceSprite.getHeight()/3);
        newgametex = new Texture("newgame.PNG");
        newgamespr = new Sprite(newgametex);
        newgamespr.setPosition(Gdx.graphics.getWidth()/2 - newgamespr.getWidth()/2, Gdx.graphics.getHeight()/7);

        for(int i = 0; i < 4; i++){
            alienTextures[i] = new Texture(String.format("alien%d.png",i));
            alienSprites[i] = new Sprite(alienTextures[i]);
            alienSprites[i].setPosition(Gdx.graphics.getWidth()/2 - alienSprites[i].getWidth()/2, (6-i)*Gdx.graphics.getHeight()/10);
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.end();

        batch.begin();
        spaceSprite.draw(batch);
        invadersSprite.draw(batch);
        newgamespr.draw(batch);
        Rectangle newgameRect = newgamespr.getBoundingRectangle();
        for(Sprite s: alienSprites){
            s.draw(batch);
        }
        if (Gdx.input.isTouched()) {
//            if(newgameRect.contains(Gdx.input.getX(),Gdx.input.getY()) ){
                game.setScreen(new GameScreen(game));
                dispose();
//            }

        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
//        batch.dispose();

    }

}
