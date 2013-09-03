package main;

import jgame.*;
import jgame.platform.*;

public class Game extends StdGame implements Commons{

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {new Game(new JGPoint(Commons.WIDTH,Commons.HEIGHT));}

	public Game(){initEngineApplet(); }
	public Game(JGPoint size){initEngine(size.x,size.y); }

	private Boss boss = null;
	
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
//		super.setAuthorMessage("Yuhua Mai");
//		for(double i = 0; i < 1; i += 0.01){
//			super.setProgressBar(i);
//			try{
//				Thread.sleep(10);
//			}catch (InterruptedException e) {}
//		}
		
		setHighscores(10,new Highscore(0,"nobody"),15);
		highscore_showtime = 100;
		
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
		
		drawString("Space Shooting Guide",pfWidth()/2,80,0, getZoomingFont(title_font,seqtimer,0.8,1/40.0), JGColor.white);
		
		setFont(new JGFont("arial",0,15));
		drawString("Press space to start the game",pfWidth()/2,120,0, null, JGColor.white);

		drawString("Press N for the next level.",pfWidth()/2,180,0);
		drawString("Press D to lose a life.",pfWidth()/2,200,0);
	}

	public void doFrameTitle() {
		if (getKey(' ')) {
			clearKey(' ');

			setGameState("StartGame");
			addGameState("InGame");

			new JGTimer(70,true, "StartGame" ) {
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
		if(stage == BASIC_LEVEL){
			drawString("KILL ALL ENEMIES",pfWidth()/2,90,0, null, JGColor.red);
		}else if(stage == RACE_LEVEL){
			drawString("KEEP FLYING TILL THE END",pfWidth()/2,90,0, new JGFont("arial",0,15), JGColor.red);
		}else if(stage == BOSS_LEVEL){
			drawString("KILL THE BOSS!!!",pfWidth()/2,90,0, null, JGColor.red);
		}
		
	}

	/** Called once when game goes into the InGame state. */
	@Override
	public void startInGame() {}
	
	@Override
	public void initNewLife() {
		defineLevel(); 
	}

	@Override
	public void defineLevel(){
 		removeObjects(null,0);
 		
 		if(stage == BASIC_LEVEL){
 			leveldone_ingame=true;
			setPFSize(CANVAS_WIDTH,CANVAS_HEIGHT*2);
			setPFWrap(true,true, 0, 0);
			fillBG("");
			
			for (int i=0; i<15+level/2; i++) {
				new Enemy(random(-pfWidth(),pfWidth()),random(0,pfHeight()-CLEAR_ZONE),random(-1,1,2), (int)(1.0+level/2.0));
			}
			
			for (int i=0; i<5; i++){
				new Bonus(random(-pfWidth(),pfWidth()), random(0,pfHeight()-CLEAR_ZONE), 3*random(-1,1,2), 3*random(-1,1,2));
			}
			
			for (int i=0; i<20; i++){
				new JGObject("explosion", true, random(-pfWidth(),pfWidth()), random(0,pfHeight()-CLEAR_ZONE),
						BLOCK_CID, "explosion");
			}
				
			new Spaceship(pfWidth()/2,pfHeight(),3);
			
 		}else if(stage == RACE_LEVEL){
 			leveldone_ingame=true;
			setPFSize(CANVAS_WIDTH,CANVAS_HEIGHT*10);
			setPFWrap(false,false,0,0);
			
			int boarderDistance = 3;
			int tunnelWidth = 20-level/2;
			int tunnelpos = pfTilesX()/2 - tunnelWidth/2;
			fillBG("#");
			
			for (int y=0; y<pfTilesY(); y++) {
				
				for (int x=tunnelpos; x<tunnelpos+tunnelWidth; x++) {
					setTile(x,y,"");
				}
				
				
				if (random(0,5) < 1 && y < pfTilesY()-CLEAR_ZONE) 
					new Enemy(tileWidth()*(tunnelpos+tunnelWidth*random(0,10)/10),tileHeight()*y-CLEAR_ZONE, 0, 0);
				if (random(0,100) < 1) 
					new Bonus(tileWidth()*(tunnelpos+tunnelWidth*random(0,10)/10),tileHeight()*y-CLEAR_ZONE, 0, 0);	
			
				tunnelpos += random(-1,1,2);
				if (tunnelpos < 1) tunnelpos = 1;
				if (tunnelpos + tunnelWidth >= pfTilesX()-boarderDistance)
					tunnelpos = pfTilesX()-tunnelWidth-boarderDistance;
			}
			
			new Spaceship(pfWidth()/2,pfHeight()-32,4);
			
 		}else if(stage == BOSS_LEVEL){
 			leveldone_ingame=true;
 			setPFSize(CANVAS_WIDTH,CANVAS_HEIGHT*2);
			setPFWrap(true,true, 0, 0);
			
 			boss  = new Boss(random(-pfWidth(),pfWidth()),random(-pfHeight(),pfHeight()), random(-1,1,2), random(-1,1,2));
 			
 			for (int i=0; i<5; i++){
				new Bonus(random(-pfWidth(),pfWidth()), random(0,pfHeight()-CLEAR_ZONE), 3*random(-1,1,2), 3*random(-1,1,2));
			}
 			
 			new Spaceship(pfWidth()/2,pfHeight()-32,3);
 		}
 		

		 
	}
	
	public void doFrameInGame() {
		moveObjects();
		
		//1 spaceship 2 bullet 3 enemy 4 boss 5 bonus 6 block 7 wall
		checkCollision(BONUS_CID, SPACESHIP_CID);
		checkCollision(BLOCK_CID, SPACESHIP_CID);
		checkCollision(ENEMY_CID, SPACESHIP_CID);
		checkCollision(BOSS_CID, SPACESHIP_CID);
		
		checkCollision(BULLET_CID, ENEMY_CID);
		checkCollision(BULLET_CID, BOSS_CID);
		checkCollision(BLOCK_CID, BULLET_CID);
		
		checkBGCollision(7,SPACESHIP_CID);
		
		// Scrolling view
		setViewOffset((int)getObject("spaceship").x,(int)getObject("spaceship").y-150,true);
		
		// Cheat Keys
		if(getKey('N')) {
			if(stage != 2){
				levelDone();
			}else{
				gameOver();
			}
		}
		if(getKey('D')) {lifeLost();}
		
		// Win Condition
		if(	
			(stage == BASIC_LEVEL && countObjects("enemy",0)==0) ||
			(stage == RACE_LEVEL && getObject("spaceship").y < 10)
			){
			levelDone();
		}
		else if((stage == BOSS_LEVEL && countObjects("boss",0)==0)){
			gameOver();
		}
			
	}
	
	public void paintFrameInGame(){
		super.paintFrame();
		
		if(stage == BASIC_LEVEL){
			drawString("Enemies Left: "+countObjects("enemy",0), 10, 10, -1);
		}else if(stage == BOSS_LEVEL){
			String str = "";
			
			for(int i = 0; i < boss.getLife(); i++){
				str+="* ";
			}
			
			drawString("Boss Lives Left:", CANVAS_WIDTH/2, 50, -1);
			drawString(str, CANVAS_WIDTH/2, 70, -1, null, JGColor.red);
		}
	}
	

	@Override
	public void startGameOver() { removeObjects(null,0); }
	
	@Override
	public void incrementLevel() {
		score += 50;
//		if (level < 2){
//			level++;
//		}
		if(stage < 2){
			stage++;
		}
	}
	JGFont scoring_font = new JGFont("Arial",0,8);

	public class Spaceship extends JGObject {
		int powerLevel = 1;
		
		public Spaceship(double x,double y,double speed) {
			super("spaceship",false,x,y,SPACESHIP_CID,"spaceship",0,0,speed,speed,-1);
		}
		@Override
		public void move() {
//			dbgPrint("x:" + x + " y:" + y);
			
			setDir(0,0);
			if (getKey(key_left))   xdir=-1;
			if (getKey(key_right))  xdir=1;
			
			if(stage == RACE_LEVEL){ y -= yspeed;}
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
			if(obj.colid == ENEMY_CID || obj.colid == BOSS_CID || obj.colid == BLOCK_CID) lifeLost();
			else if(obj.colid == BONUS_CID){
				if(powerLevel<5) powerLevel++;
			}
		}
		
		@Override
		public void hit_bg(int tilecid) {
			lifeLost();
		}
		
		public int getPowerLevel() {
			return powerLevel;
		}
	}
	
	public class Bullet extends JGObject{
		double startX = 0;
		double startY = 0;
		
		public Bullet(double x, double y, int xdir, int ydir){
			super("bullet",true,x,y,BULLET_CID,"bullet", xdir, ydir, -2);
			startX = x;
			startY = y;
		}
		
		@Override
		public void move() {
			if(Math.abs(startX - x) > CANVAS_WIDTH*2 || Math.abs(startY-y) > CANVAS_HEIGHT*4) remove();
		}
		
		@Override
		public void hit(JGObject obj) {
			if(obj.colid == ENEMY_CID || obj.colid == BOSS_CID || obj.colid == BLOCK_CID)
				remove();
		}
		
		@Override
		public void hit_bg(int tilecid) { remove(); }
		
	}
	
	public class Enemy extends JGObject {
		private int life;
		private double timer=0;
		
		public Enemy(double x, double y, int xdir, int ydir) {
			super("enemy",true,x,y, ENEMY_CID, "enemy", xdir, ydir, -2 );
			life = ENEMY_LIFE;
		}
		@Override
		public void move() {
//			dbgPrint("life:" + life);
			
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
			if(obj.colid == BULLET_CID){
				hurt();
				obj.remove();
			}
		}
		
		@Override
		public void hit_bg(int tilecid) { remove(); }
		
		
	}
	
	public class Boss extends JGObject{
		private int life;
		
		public Boss(double x, double y, int xdir, int ydir) {
			super("boss",true,x,y, BOSS_CID, "spaceshipC", xdir, ydir, -2 );
			life = BOSS_LIFE;
		}
		
		@Override
		public void move(){
			if (checkTime(0,(800),((50-level/2)))){
				new Enemy(x, y, random(-5, 5, 1), random(-5, 5, 1));
			}
		}
		
		public void hurt(){
			life--;
			if(life == 0) remove();
		}
	
		@Override
		public void hit(JGObject obj) {
			if(obj.colid == BULLET_CID) hurt();
		}

		public int getLife() {
			return life;
		}
	}
	
	public class Bonus extends JGObject{
		double timer=0;
		public Bonus(double x, double y, int xdir, int ydir){
			super("bonus", true, x, y, BONUS_CID, "bonus", xdir,ydir, JGObject.suspend_off_view);
		}
		@Override
		public void move() {
			timer += gamespeed*3;
			x += Math.sin(0.1*timer)*xdir;
			y += Math.cos(0.1*timer)*ydir;
			if (y>pfHeight()) y = -8;
		}
		@Override
		public void hit(JGObject obj) { 
			if(obj.colid == SPACESHIP_CID) remove(); 
		}
		
		@Override
		public void hit_bg(int tilecid) { remove(); }
	}
	
}
