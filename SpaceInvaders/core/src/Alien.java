//Class file for the creating, moving, killing, and acting for the alien enemies in Space Invaders

public class Alien {
	private static int moveDir;
	private static int swarmX; //Variables used to keep track of the position of the horde of aliens
	private static int swarmY;
	private static int swarmLen;
	private int xPos; //Variables used to keep track of individual aliens relative to the horde
	private int yPos;
	
	public static final int RIGHT = 1;
	public static final int LEFT = -1;
	public static final int MOVESPEED = 2;
	public static final int ALIENLENGTH = 30;
	public static final int ENEMYHEIGHT = 30;
	
	public Alien(int species, int x, int y) {
		//Constructor method, sets a specified position and starting direction for all aliens
		xPos = x;
		yPos = y;
		
		
		if(xPos > swarmX + swarmLen + ALIENLENGTH) {
			//Adjusting the last pixel that the alien swarm occupies if a new alien is placed farther to the right than any other
			swarmLen = (xPos + ALIENLENGTH) - swarmX;
		}
		if(xPos < swarmX) {
			//Adjusting the x position of the swarm if a new alien is put farther to the left than any other
			swarmX = xPos;
		}
		moveDir = RIGHT;
	}
	
	public static void move(int screenLen) {
		//Moves the swarm of aliens
		if(swarmX + swarmLen + MOVESPEED * moveDir >= screenLen || swarmX + MOVESPEED * moveDir < 0) {
			changeDir();
		}
		swarmX += MOVESPEED * moveDir;
	}
	
	public static void changeDir() {
		//Changes the direction of all the aliens and moves them down
		swarmY += 5;
		moveDir *= -1;
	}
	
	public static int swarmEnd() {
		return swarmX + swarmLen;
	}
	
	public Bullet shoot() {
		Bullet shot = new Bullet(Bullet.DOWN, xPos + ALIENLENGTH / 2, yPos + ENEMYHEIGHT);
		return shot;
	}
	
	public int getX() {
		return swarmX + xPos;
	}
	
	public int getY() {
		return swarmY + yPos;
	}
	
	/*public Rect getHurtbox(){
	 	Rect hurtbox = new Rect(xPos, yPos, ALIENLENGTH, ALIENHEIGHT);
	  	return hurtbox;
	 */
	
	
}