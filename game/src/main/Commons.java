package main;

public interface Commons {

	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH * 16 / 9;
	public static final int CANVAS_WIDTH = WIDTH / 10;
	public static final int CANVAS_HEIGHT = HEIGHT / 10;
	
	public static final int CLEAR_ZONE = 30;
	
	public static final int ENEMY_LIFE = 1;
	public static final int BOSS_LIFE = 10;
	
	public static final int BASIC_LEVEL = 0;
	public static final int RACE_LEVEL = 1;
	public static final int BOSS_LEVEL = 2;
	
	public static final int SPACESHIP_CID = 1;
	public static final int BULLET_CID = 2;
	public static final int ENEMY_CID = 3;
	public static final int BOSS_CID = 4;
	public static final int BONUS_CID = 5;
	public static final int BLOCK_CID =6;

}