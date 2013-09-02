package main;

import jgame.*;
import jgame.platform.*;

public class Game extends StdGame{

	private static final long serialVersionUID = 1L;
//	private Spaceship spaceship = null;

	public static void main(String[] args) {new Game(new JGPoint(640,480));}

	public Game(){initEngineApplet(); }
	public Game(JGPoint size){initEngine(size.x,size.y); }

	public void initCanvas() {setCanvasSettings(30, 20, 16, 16, JGColor.black,new JGColor(100,100,100), null);}

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
	
//	public void setProgressBar(double pos){
//		super.setProgressBar(1);
//	}
	
//	public void setProgressMessage(java.lang.String msg){
//		super.setProgressMessage("Space Shooting");
//	}
	
	public void startTitle() {
		removeObjects(null,0);
	}
	
	public void paintFrameTitle() {
		drawString("Title state. Press space to go to InGame",pfWidth()/2,90,0);
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
				public void alarm() {
					removeGameState("StartGame");
				}
			};
		}
	}

	/** The StartGame state is just for displaying a start message. */
	public void paintFrameStartGame() {
		drawString("We are in the StartGame state.",0,90,-1);
	}

	/** Called once when game goes into the InGame state. */
	public void startInGame() {
		// when the game starts, we create a game object
//		new Shooter();
	}
	

	public void paintFrameInGame(){
		super.paintFrame();
		
		drawString("Enemies "+countObjects("enemy",0), 10, 10, -1);
//		drawString("Fire Level "+spaceship.getPowerLevel(), 10, 20, -1);
//		drawImageString("Fire", 20, 20, 1, null, 32, 3);
	}
	
	public void doFrameInGame() {
		moveObjects();
		checkCollision(2,1); // enemies hit player
		checkCollision(4,2); // bullets hit enemies
		
//		if (checkTime(0,(int)(800),(int)((12-level/2))))
//			new Enemy();
//		if (checkTime(0,(int)(800),(int)500))
//			new Bonus();
//		System.out.println(countObjects("enemy", 0));
		
		if (gametime>=800 && countObjects("enemy",0)==0) levelDone();
		
		checkCollision(2+4,1); // enemies, pods hit player
		checkBGCollision(1,1); // bg hits player
//		if (stage%2==1 && countObjects("pod",0)==0) levelDone();
		
//		setViewOffset((int)getObject("player").x,
//			(int)getObject("player").y-100,true);
		setViewOffset((int)getObject("spaceship").x,
					(int)getObject("spaceship").y-100,true);
	}
	

	
	public void defineLevel(){
//		leveldone_ingame=true;
//		setPFSize(40,40);
//		setPFWrap(false,false,0,0);
		
//		new JGObject("enemy",true,
//				tileHeight(), //x
//				tileWidth(), //y
//			2, "baru", 0,0,16,16,
//			-1,0, JGObject.suspend_off_view);
		
		
		
 		removeObjects(null,0);
//		switch (stage%2) {
//		case 0:
//			leveldone_ingame=true;
//			setPFSize(40,150);
//			setPFWrap(false,false,0,0);
//			int tunnelheight = 11-level/2;
//			int tunnelpos = pfTilesY()/2 - tunnelheight/2;
////			fillBG("#");
//			int firstpart=15;
//			int oldpos=0;
//			for (int y=0; y<pfTilesY(); y++) {
//				
//				for (int x=tunnelpos; x<tunnelpos+tunnelheight; x++) {
//					setTile(x,y,"");
//				}
//				
//				if (firstpart>0) {
//					firstpart--;
//				} else {
//					if (random(0,5) < 1)
//						new JGObject("enemy",true,
//								tileHeight()*(oldpos+tunnelheight/2), //x
//								tileHeight()*y, //y
//							2, "baru", 0,0,16,16,
//							-1,0, JGObject.suspend_off_view);
//					
//					if (random(0,5) < 1)
//						new JGObject("pod",true, 
//								tileHeight()*(oldpos+random(2,tunnelheight-3,1)), //x
//								tileWidth()*y, //y
//							4, "plus", 0,0,14,14, 0,0, JGObject.suspend_off_view);
//					
//					oldpos = tunnelpos;
//					tunnelpos += random(-1,1,2);
//					if (tunnelpos < 1) tunnelpos = 1;
//					if (tunnelpos + tunnelheight >= pfTilesY()-1)
//						tunnelpos = pfTilesY()-tunnelheight-1;
//				}
//			}
//		break;
//		case 1:
			leveldone_ingame=false;
			setPFSize(40,30);
			setPFWrap(true,true, 0, 0);
			fillBG("");
			for (int i=0; i<10+level/2; i++) {
//				new BombDropper();
				new Enemy(random(-pfWidth(),pfWidth()), random(-pfHeight(),pfHeight()));
//				new JGObject("pod",true,
//					random(-pfWidth(),pfWidth()), //x
//					random(-pfHeight(),pfHeight()), //y
//					4, "plus", 0,0,14,14, 0,random(0.5,1.2), -1);
			}
			
			for (int i=0; i<10; i++){
				new Bonus(random(-pfWidth(),pfWidth()), random(-pfHeight(),pfHeight()));
			}
			
//		break; }
//		new Player(pfWidth()/2,0,3);
		 new Spaceship(pfWidth()/2,pfHeight(),3);
	}
	
	public void initNewLife() { defineLevel(); }
	
	public void startGameOver() { removeObjects(null,0); }
	
	public void incrementLevel() {
		score += 50;
		if (level < 2) level++;
		stage++;
	}
	JGFont scoring_font = new JGFont("Arial",0,8);

	public class Spaceship extends JGObject {
		int powerLevel = 1;
		
		public Spaceship(double x,double y,double speed) {
			super("spaceship",false,x,y,1,"spaceship",0,0,speed,speed,-1);
		}
		public void move() {
			dbgPrint("x:" + x + " y:" + y);
			
			setDir(0,0);
//			System.out.println("xdir: " + xdir + " ydir: " + ydir + " xspeed: " +xspeed + " yspeed: " + yspeed);
			if (getKey(key_left))               xdir=-1;
			if (getKey(key_right))  xdir=1;
			if (getKey(key_up)) { y -= getGameSpeed()*3*xspeed/2; }
			//else                        { y -= getGameSpeed()*xspeed;   }
//			if (getKey(key_down) && y < pfHeight()-10-xspeed)  ydir=1;
			
			if (getKey(key_fire) && countObjects("bullet",0) < 50) {
				if(powerLevel == 1){
					new Bullet(x, y, 0, -5);
				}else if(powerLevel == 2){
					new Bullet(x, y, 0, -5);
					new Bullet(x, y, 1, -5);
					new Bullet(x, y, -1, -5);
				}else if(powerLevel == 3){
					new Bullet(x, y, 0, -5);
					new Bullet(x, y, 1, -5);
					new Bullet(x, y, 2, -5);
					new Bullet(x, y, -1, -5);
					new Bullet(x, y, -2, -5);
				}
				clearKey(key_fire);
			}
		}
		public void hit(JGObject obj) {
			if(obj.colid == 2) lifeLost();
			else if(obj.colid == 3){
				obj.remove();
				if(powerLevel<3)powerLevel++;
			}
		}
		public int getPowerLevel() {
			return powerLevel;
		}
	}
	
	public class Bullet extends JGObject{
		double startY = 0;
		
		public Bullet(double x, double y, int xdir, int ydir){
			//(java.lang.String name, boolean unique_id, double x, double y,
			//int collisionid, java.lang.String gfxname, 
			//int xdir, int ydir, double xspeed, double yspeed, int expiry) 
//			super("bullet",true,x,y,
//			4, "bullet",xdir,ydir,2,3);
			super("bullet",true,x,y,1,"bullet", xdir, ydir, -2);
			startY = y;
		}
		
		public void move() {
//			System.out.println(startY - y);
			if(startY-y > 250) remove();
		}
		
		public void hit(JGObject obj) {
			if(obj.colid == 2){
				Enemy e = (Enemy)obj;
				e.hurt();
			}
			remove();
			
		}
		
	}
	
	public class Enemy extends JGObject {
		int life = 3;
		double timer=0;
		
		public Enemy(double x, double y) {
			super("enemy",true,x,y,
					2, stage%2==1 ? "box" : "crossturn",
					random(-1,1), (1.0+level/2.0), -2 );
//			super("enemy",true,random(32,pfWidth()-40),-8,
//					2, stage%2==1 ? "box" : "crossturn",
//							0,0,16,16,
//							-1,0, JGObject.suspend_off_view);
		}
		public void move() {
			dbgPrint("life:" + life);
			
			timer += gamespeed;
			x += Math.sin(0.1*timer);
			y += Math.cos(0.1*timer);
			if (y>pfHeight()) y = -8;
		}
		public void hit(JGObject obj) {
//			if(obj.colid == 1){
//				life--;
//				if(life == 0) remove();
//				remove();
//				obj.remove();
//				score += 5;
//			}
		}
		public void hurt(){
			life--;
			if(life == 0) remove();
		}
	}
	
	public class Bonus extends JGObject{
		double timer=0;
		public Bonus(double x, double y){
//			super("bonus", true, random(32, pfWidth()-40), -8, 3, "circle", 
//					random(-1,1), (1.0+level/2.0), -2);
			super("bonus", true, x, y, 3, "circle", 
					0,0,16,16,
					-1,0, JGObject.suspend_off_view);
		}
		public void move() {
			timer += gamespeed;
			x += Math.sin(0.1*timer);
			y += Math.cos(0.1*timer);
			if (y>pfHeight()) y = -8;
		}
		public void hit(Spaceship spaceship) {
//			remove();
//			spaceship.powerLevelUpgrade();
//			score += 5;
		}
	
	}
	
	//////////////////
/*
	public class Player extends JGObject {
		public Player(double x,double y,double speed) {
			super("player",false,x,y,1,"circle",0,0,speed,speed,-1);
		}
		public void move() {
			setDir(0,0);
			if (getKey(key_left))   xdir=-1;
			if (getKey(key_right)) xdir=1;
			if (getKey(key_up)) { y -= getGameSpeed()*3*xspeed/2; }
			else                        { y -= getGameSpeed()*xspeed;   }
			if (!isOnPF(0,0)) levelDone();
		}
		public void hit(JGObject obj) {
			if (and(obj.colid,2)) lifeLost();
			else {
				score += 5;
				obj.remove();
//				new StdScoring("pts",obj.x,obj.y,0,-1.0,40,"5 pts",scoring_font,
//					new JGColor [] { JGColor.red,JGColor.yellow },2);
			}
		}
		public void hit_bg(int tilecid) { lifeLost(); }
	}
	
	class BombDropper extends JGObject {
		public BombDropper() {
			super("enemy",true,Game.this.random(pfWidth()/3,pfWidth()),
				Game.this.random(0,pfHeight()), 2, "baru");
				setSpeed(random(-0.7,-0.3),random(-0.5,0.5));
		}
		public void move() {
			if (random(0,1) < 0.005) {
				JGPoint cen = getCenterTile();
				setTile(cen.x,cen.y,"#");
			}
		}
	}
*/
}
