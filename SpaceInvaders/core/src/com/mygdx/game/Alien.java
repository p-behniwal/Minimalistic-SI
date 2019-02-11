package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

//Class file for the creating, moving, killing, and acting for the alien enemies in Space Invaders

public class Alien {
    private static int moveDir;

    private int species; //Used to determine which sprite the alien gets, and how aggressive they are
    private Sprite alienSprite;
    private Texture alienTex;

    private Sound alienLaser = Gdx.audio.newSound(Gdx.files.internal("alienLaser.wav"));

    public static final int RIGHT = 1;
    public static final int LEFT = -1;
    public static final int MOVESPEED = 1;

    //Different alien species that determine shot frequency
    public static final int AGGRO = 4;
    public static final int WARRIOR = 3;
    public static final int ROOKIE = 2;
    public static final int CIVILIAN = 1;
    public static final int PACIFIST = 0;


    public Alien(int x, int y, int type) {
        //Constructor method, sets a specified position and starting direction for all aliens
        species = type;
        String alienImage = String.format("alien%d.png", type); //Choosing a texture based on their species
        alienTex = new Texture(alienImage);
        alienSprite = new Sprite(alienTex);
        alienSprite.setPosition(x, y);

        moveDir = RIGHT;
    }

    public void move() {
        //Moves the swarm of aliens
        alienSprite.translateX(MOVESPEED * moveDir);

    }

    public boolean changeDir() {
        //Changes the direction of all the aliens and determines if they need to all move down
        boolean moveDown = false;
        if(alienSprite.getX() + alienSprite.getWidth() + MOVESPEED * moveDir >= Gdx.graphics.getWidth() || alienSprite.getX() + MOVESPEED * moveDir < 0) {
            moveDir *= -1;
            moveDown = true;
        }
        return moveDown;
    }

    public void moveDown() {
        //Moves the aliens down the screen
        alienSprite.translateY(-7);
    }


    public Bullet shoot() {
        //Returns a bullet from a given alien
        alienLaser.play(); //Plays the alien shot sound effect
        Bullet shot = new Bullet(Bullet.DOWN, alienSprite.getX() + alienSprite.getWidth() / 2, alienSprite.getY() + alienSprite.getHeight());
        return shot;
    }

    public int getSpecies() {
        //Returns which species the alien is
        return species;
    }

    public Sprite getSprite() {
        //Returns the alien's sprite
        return alienSprite;
    }

    public boolean pastY(int yPos) {
        //Checks if this alien is past a certain y coordinate
        boolean past = false;
        if(alienSprite.getY() < yPos) {
            past = true;
        }
        return past;
    }
}