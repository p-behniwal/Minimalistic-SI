//Creates, moves, an acts out player ship actions based on input received in SpaceInvaders.java
public class PlayerShip {
	private int x;
	private int y;
	
	
	public static final int SPEED = 5;
	public static final int RIGHT = 1;
	public static final int LEFT = -1;
	public static final int SHIPLEN = 20;
	public static final int SHIPHEIGHT = 20;
	
	public PlayerShip(int xPos, int yPos) {
		//Constructor class for the ship to determine it's initial position
		x = xPos;
		y = yPos;
	}
	
	public void move(int dir) {
		//Moves the player left or right based on input taken from SpaceInvaders.java
		x += SPEED * dir;
	}
	
	public Bullet shoot() {
		//Creates a Bullet object belonging to the player and shoots it from their location
		Bullet shot = new Bullet(Bullet.UP, x + SHIPLEN / 2, y);
		return shot;
	}
	
	/*public Rect hurtbox(){
	 	Rect hurtbox = new Rect(x, y, SHIPLEN, SHIPHEIGHT);
	 	return hurtbox;
	 */
}
