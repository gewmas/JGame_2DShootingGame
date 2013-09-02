package main;

import jgame.*;
import jgame.platform.*;

public class Game extends StdGame{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH * 16 / 9;
	public static final int CANVAS_WIDTH = WIDTH/10;
	public static final int CANVAS_HEIGHT = HEIGHT/10;
	public static final int CLEAR_ZONE = 30;
//	private Spaceship spaceship = null;

	public static void main(String[] args) {new Game(new JGPoint(WIDTH,HEIGHT));}

	public Game(){initEngineApplet(); }
	public Game(JGPoint size){initEngine(size.x,size.y); }

	@Override
	public void initCanvas() {setCanvasSettings(CANVAS_WIDTH, CANVAS_HEIGHT, 8, 8, JGColor.black,new JGColor(0,0,0), null);}

	@Override
	public void initGame() {
		defineMedia("mygame.tbl");
		if (isMidlet()) {
			setFrameRate(20,1);
			setGameSpeed(2.0);
		} else {
			setFrameRate(45,1);
		}
		
//		setBGImage("mybackground");
		
//		super.setProgressMessage("Space Shooting");
		super.setAuthorMessage("Yuhua Mai");
		for(double i = 0; i < 1; i += 0.01){
			super.setProgressBar(i);
			try{
				Thread.sleep(1);
			}catch (InterruptedException e) {}
		}
		
		setHighscores(10,new Highscore(0,"nobody"),15);
		
		startgame_ingame=true;
		
		lives_img="spaceship";
		
//		dbgShowMessagesInPf(true);
	}
	
	@Override
	public void startTitle() {
		removeObjects(null,0);
	}
	
	@Override
	public void paintFrameTitle() {
		drawString("Space Shooting Guide",pfWidth()/2,70,0, null, JGColor.white);
		drawString("Press space to start the game",pfWidth()/2,90,0, null, JGColor.white);
		
		setFont(new JGFont("arial",0,15));
		drawString("Press N for the next level.",pfWidth()/2,180,0);
		drawString("Press D to lose a life.",pfWidth()/2,200,0);
	}

	public void doFrameTitle() {
		if (getKey(' ')) {
			// ensure the key has to be pressed again to register
			clearKey(' ');
			// Set both StartGame and InGame states simultaneously.
			// When setting a state, the state becomes active only at the
			// beginning of the next frame.
			setGameState("StartGame");
			addGameState("InGame");
			// set a timer to remove the StartGame state after a few seconds,
			// so only the InGame state remains.
			new JGTimer(
				70, // number of frames to tick until alarm
				true, // true means one-shot, false means run again
				      // after triggering alarm
				"StartGame" // remove timer as soon as the StartGame state
				            // is left by some other circumstance.
				            // In particular, if the game ends before
				            // the timer runs out, we don't want the timer to
				            // erroneously trigger its alarm at the next
				            // StartGame.
			) {
				// the alarm method is called when the timer ticks to zero
				@Override
				public void alarm() {
					removeGameState("StartGame");
				}
			};
		}
	}

	/** The StartGame state is just for displaying a start message. */
	@Override
	public void paintFrameStartGame() {
		drawString("We are in the StartGame state.",0,90,-1, null, JGColor.white);
	}

	/** Called once when game goes into the InGame state. */
	@Override
	public void startInGame() {
		// when the game starts, we create a game object
//		new Shooter();
	}
	
	@Override
	public void initNewLife() { defineLevel(); }

	@Override
	public void defineLevel(){
 		removeObjects(null,0);
 		
 		if(stage == 0){
 			leveldone_ingame=true;
			setPFSize(CANVAS_WIDTH,CANVAS_HEIGHT*2);
			setPFWrap(true,true, 0, 0);
			fillBG("");
			
			for (int i=0; i<5+level/2; i++) {
				new Enemy(random(-pfWidth(),pfWidth()),random(0,pfHeight()-CLEAR_ZONE),(int)random(-1,1,2), (int)(1.0+level/2.0));
			}
			
			for (int i=0; i<5; i++){
				new Bonus(random(-pfWidth(),pfWidth()), random(0,pfHeight()-CLEAR_ZONE), 3*(int)random(-1,1,2), 3*(int)random(-1,1,2));
			}
			
			for (int i=0; i<20; i++){
				new JGObject("explosion", true, random(-pfWidth(),pfWidth()), random(0,pfHeight()-CLEAR_ZONE),
					5, "explosion");
			}
				
			new Spaceship(pfWidth()/2,pfHeight(),3);
			
 		}else if(stage == 1){
 			leveldone_ingame=true;
			setPFSize(CANVAS_WIDTH,CANVAS_HEIGHT*10);
			setPFWrap(false,false,0,0);
			
//			int boarderDistance = 3;
			int tunnelWidth = 20-level/2;
			int tunnelpos = pfTilesX()/2 - tunnelWidth/2;
			fillBG("#");
			
			
			int oldPos=0;
			for (int y=0; y<pfTilesY(); y++) {
				
				for (int x=tunnelpos; x<tunnelpos+tunnelWidth; x++) {
					setTile(x,y,"");
				}
				
				
				if (random(0,5) < 1 && y < pfTilesY()-CLEAR_ZONE) 
					new Enemy(tileWidth()*(tunnelpos+tunnelWidth*random(0,10)/10),tileHeight()*y-CLEAR_ZONE, 0, 0);
				if (random(0,100) < 1) 
					new Bonus(tileWidth()*(tunnelpos+tunnelWidth*random(0,10)/10),tileHeight()*y-CLEAR_ZONE, 0, 0);	
				
	
				oldPos = tunnelpos;
				tunnelpos += random(-1,1,2);
				if (tunnelpos < 1) tunnelpos = 1;
				if (tunnelpos + tunnelWidth >= pfTilesX()-3)
					tunnelpos = pfTilesX()-tunnelWidth-3;
			}
			
			new Spaceship(pfWidth()/2,pfHeight()-32,4);
			
 		}else if(stage == 2){
 			leveldone_ingame=true;
 			setPFSize(CANVAS_WIDTH,CANVAS_HEIGHT*2);
			setPFWrap(true,true, 0, 0);
			
 			new Boss(random(-pfWidth(),pfWidth()),random(-pfHeight(),pfHeight()),0, 0);//(int)random(-1,1,2), (int)(1.0+level/2.0));
			
 			for (int i=0; i<5; i++){
				new Bonus(random(-pfWidth(),pfWidth()), random(0,pfHeight()-CLEAR_ZONE), 3*(int)random(-1,1,2), 3*(int)random(-1,1,2));
			}
 			
 			new Spaceship(pfWidth()/2,pfHeight()-32,3);
 		}
 		

		 
	}
	
	public void doFrameInGame() {
		moveObjects();
		checkCollision(1,2);
		checkCollision(2,1); // enemies hit player

		setViewOffset((int)getObject("spaceship").x,
					(int)getObject("spaceship").y-150,true);
		
		// Cheat Keys
		if (getKey('N')) {levelDone();}
		if (getKey('D')) {lifeLost();}
		
		// Win Condition
		if(	
			(stage == 0 && countObjects("enemy",0)==0) ||
			(stage == 1 && getObject("spaceship").y < 10)
			){
			levelDone();
		}
		else if((stage == 2 && countObjects("boss",0)==0)){
			gameOver();
		}
			
	}
	
	public void paintFrameInGame(){
		super.paintFrame();
		
		drawString("Enemies Left: "+countObjects("enemy",0), 10, 10, -1);
	}
	
	
	
	@Override
	public void startGameOver() { removeObjects(null,0); }
	
	@Override
	public void incrementLevel() {
		score += 50;
		if (level < 2){
			level++;
		}
		stage++;
	}
	JGFont scoring_font = new JGFont("Arial",0,8);

	public class Spaceship extends JGObject {
		int powerLevel = 1;
		
		public Spaceship(double x,double y,double speed) {
			/*
			 * (java.lang.String name, boolean unique_id, double x, double y,
				int collisionid, java.lang.String gfxname, 
				int xdir, int ydir, double xspeed, double yspeed, int expiry)
			*/
			 
			super("spaceship",false,x,y,1,"spaceship",0,0,speed,speed,-1);
		}
		@Override
		public void move() {
			dbgPrint("x:" + x + " y:" + y);
			
			setDir(0,0);
			if (getKey(key_left))   xdir=-1;
			if (getKey(key_right))  xdir=1;
			
			if(stage == 1){ y -= yspeed;}
			else if (getKey(key_up)) { y -= getGameSpeed()*3*xspeed/2; }

			if (getKey(key_fire) && countObjects("bullet",0) < 50) {
				if(powerLevel == 1){
					new Bullet(x, y, 0, (int)-yspeed*2);
				}else if(powerLevel == 2){
					new Bullet(x, y, 0, (int)-yspeed*2);
					new Bullet(x, y, 1, -5);
					new Bullet(x, y, -1, -5);
				}else if(powerLevel == 3){
					new Bullet(x, y, 0, -5);
					new Bullet(x, y, 0, 5);
					new Bullet(x, y, 5, 0);
					new Bullet(x, y, -5, 0);
				}else if(powerLevel == 4){
					new Bullet(x, y, 0, -5);
					new Bullet(x, y, 1, -5);
					new Bullet(x, y, 2, -5);
					new Bullet(x, y, -1, -5);
					new Bullet(x, y, -2, -5);
				}else if(powerLevel == 5){
					new Bullet(x, y, 0, -5);
					new Bullet(x, y, 1, -5);
					new Bullet(x, y, 2, -5);
					new Bullet(x, y, -1, -5);
					new Bullet(x, y, -2, -5);
					
					new Bullet(x, y, 0, 5);
					new Bullet(x, y, 5, 0);
					new Bullet(x, y, -5, 0);
				}
				clearKey(key_fire);
			}
		}
		@Override
		public void hit(JGObject obj) {
			if(obj.colid == 1) return;
			else if(obj.colid == 3){
				obj.remove();
				if(powerLevel<5)powerLevel++;
			}else{
				lifeLost();
			}
		}
		
		public void hit_bg(int tilecid) { lifeLost(); }
		
		public int getPowerLevel() {
			return powerLevel;
		}
	}
	
	public class Bullet extends JGObject{
		double startX = 0;
		double startY = 0;
		
		public Bullet(double x, double y, int xdir, int ydir){
			super("bullet",true,x,y,1,"bullet", xdir, ydir, -2);
			startX = x;
			startY = y;
		}
		
		@Override
		public void move() {
			if(Math.abs(startX - x) > CANVAS_WIDTH*2 || Math.abs(startY-y) > CANVAS_HEIGHT*4) remove();
		}
		
		@Override
		public void hit(JGObject obj) {
//			if(obj.colid == 2){
//				Enemy e = (Enemy)obj;
//				e.hurt();
//			}
			remove();
		}
		
		public void hit_bg(int tilecid) { remove(); }
		
	}
	
	public class Enemy extends JGObject {
		private int life = 3;
		private double timer=0;
		
		public Enemy(double x, double y, int xdir, int ydir) {
			super("enemy",true,x,y,
					2, "enemy",
					xdir, ydir, -2 );
		}
		@Override
		public void move() {
			dbgPrint("life:" + life);
			
			timer += gamespeed*2;
			
			x += Math.sin(0.1*timer)*xdir;
			y += Math.cos(0.1*timer)*ydir;
			
			if (y>pfHeight()) y = -8;
		}
		
		public void hurt(){
			life--;
			if(life == 0) remove();
		}
		
		@Override
		public void hit(JGObject obj) {
			if(obj.colid == 1)hurt();
		}
		
		public void hit_bg(int tilecid) { remove(); }
		
		
	}
	
	public class Boss extends JGObject{
		private int life = 20;
		
		public Boss(double x, double y, int xdir, int ydir) {
			super("boss",true,x,y,
					2, "spaceshipC",
					xdir, ydir, -2 );
		}
		
		public void move(){
			if (checkTime(0,(int)(800),(int)((50-level/2)))){
//				new Bullet(x, y, random(-5, 5, 1), random(-5, 5, 1));
				new Enemy(x, y, random(-5, 5, 1), random(-5, 5, 1));
			}
		}
		
		public void hurt(){
			life--;
			if(life == 0) remove();
		}
	
		public void hit(JGObject obj) {
			if(obj.colid == 1)hurt();
		}
	}
	
	public class Bonus extends JGObject{
		double timer=0;
		public Bonus(double x, double y, int xdir, int ydir){
			super("bonus", true, x, y, 3, "bonus", 
					0,0,16,16,
					xdir,ydir, JGObject.suspend_off_view);
		}
		@Override
		public void move() {
			timer += gamespeed*3;
			x += Math.sin(0.1*timer)*xdir;
			y += Math.cos(0.1*timer)*ydir;
			if (y>pfHeight()) y = -8;
		}
		public void hit(Spaceship spaceship) {}
		
		public void hit_bg(int tilecid) { remove(); }
	}
	
}
