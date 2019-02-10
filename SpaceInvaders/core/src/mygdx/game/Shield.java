package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Shield {
	private Texture bodyTex = new Texture("shieldB.png");
	private Texture cornerTex = new Texture("shieldC.png ");
	private Sprite[] shieldPieces = new Sprite[10];
	private int[] healths = new int[10];
	
	private float SIDELENGTH;
	
	public Shield(float x, float y) {
		//Constructor method for a shield
		for(int i = 0; i < 6; i++) { //Creating 6 body shield pieces
			shieldPieces[i] = new Sprite(bodyTex);
			shieldPieces[i].setSize(25, 25);
		}
		
		for(int i = 6; i < 10; i++) { //creating 4 corner pieces
			shieldPieces[i] = new Sprite(cornerTex);
			shieldPieces[i].setSize(25, 25);
		}
		SIDELENGTH = shieldPieces[0].getWidth(); //getting the side length of a shield piece
		//Setting all shield pieces to the proper relative location, with some minor adjustments for visual appearances
		shieldPieces[0].setPosition(x + SIDELENGTH, y + SIDELENGTH * 2);
		shieldPieces[1].setPosition(x + SIDELENGTH * 2, y + SIDELENGTH * 2);
		shieldPieces[2].setPosition(x, y + SIDELENGTH );
		shieldPieces[3].setPosition(x, y);
		shieldPieces[4].setPosition(x + SIDELENGTH * 3, y + SIDELENGTH);
		shieldPieces[5].setPosition(x + SIDELENGTH * 3, y);
		shieldPieces[6].setPosition(x + 1, y + SIDELENGTH * 2);
		shieldPieces[7].setPosition(x + SIDELENGTH * 3, y + SIDELENGTH * 2 - 1);
		shieldPieces[8].setPosition(x + SIDELENGTH - 1, y + SIDELENGTH);
		shieldPieces[9].setPosition(x + SIDELENGTH * 2, y + SIDELENGTH + 1);
		//Rotating the corner pieces as necessary
		shieldPieces[6].rotate90(false);
		shieldPieces[8].rotate90(true);
		shieldPieces[9].rotate90(true);//Rotating 90 twice since other methods pivot around a corner, thus changing location
		shieldPieces[9].rotate90(true);
		
		for(int i = 0; i < 10; i++) { //Setting all of the shield pieces' healths
			healths[i] = 5;
		}
	}
	
	
	public void takeDamage(int piece) {
		//Taking damage for a given piece
		healths[piece]--;
		shieldPieces[piece].setColor(0f, 1f, 0f, healths[piece] / 5f); //Changing the opacity of a shield piece based on hw much health remains
	}
	
	public Sprite[] getSprites() {
		//returns all pieces' sprites
		return shieldPieces;
	}
	
	public int[] getHealths() {
		//returns all pieces' healths
		return healths;
	}
}
