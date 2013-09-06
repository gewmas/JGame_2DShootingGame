Project Analysis
======

#Time Review
I spent first two days getting familiar with the JGame engine and last three days to code the game according to the plan – move objects, collision check, scrolling control and levels design. On average, I spent four hours every day, 20% of the total time to learn the features of the engine, 40% to code, 30% to debug and testing and 10% to refactor the code.

#Planning
In general, the plan describes quite well how the project will be and serves a good guidance how the project will develop. 
The actual time spent is actually less than the expected time mostly because the JGame engine save the time to move objects, check collision and scrolling the play field. The flying level in level 2 is added after the plan thanks to the extra time and the object oriented design which enabling new features without much modifications.

In the future, plan should also consider the estimation of how long one feature will take. Possible architecture of classes could be designed beforehand.

#Status
The code reflects the design in the way that most of the desired functions are fulfilled.

##Readability
The order of method call shows dependencies because the state of JGame switches from TITLE, INGAME, GAMEOVER.

I generate get methods for the class Boss for others to get the life of the boss instead of exposing the life.

The naming of variables and classes could make the code more clear. The structure of the code like subclass could show the relationship between classes.

##Extensibility
For the objects in the game, a generic sprite should be made to implement the common features like collision, movement.

How the class Spaceship shoots could be too specific in terms of the shooting method and the number of bullets.

More considerations could be paid to subclass the class MovingObject because not all the subclass like Bullet and Bonus behave the same way.

##Testability
For the states of the JGame engine, I could test according different states. For the class and the subclasses, I could first test the function of the class and make sure it is general and suitable for its subclasses. Then the subclasses could be tested individually.

Those parts in a small function and can be shown on the screen will be easier to test while those in need of interaction with other classes like create Bullet in the Spaceship class could be more difficult.

The easy parts would be more independent and focus on just one specific function while hard ones try  to squeeze too many features in just one class or function. 

#Conclusions
I find it the collision check difficult to code and debug because I don’t like the idea that assign every object a collision ID which is confusing and often duplicated. At last, I assigned every object ID a static final variable to help to distinguish the difference.

The defineLevel() part which highly depends on the different value of levels. Those different levels could be separate and be called individually be defineLevel().

Designing and planning beforehand thoroughly with clear structure of the code could reduce the defects and time to refactor in the future projects.

#Future work
I would like to refactor the defineLevel() part and the messy states in the JGame engine if I could work on the project for higher grade.

The incomplete part of cheat keys could be implemented by adding if statement in the incrementLevel() and doFrameInGame() part.

#Sample Code
```java
public class MovingObject extends JGObject{
		private double timer=0;
		
		public MovingObject(String name, boolean unique_id, double x, double y,
				int collisionid, String gfxname, double xspeed, double yspeed) {
			super(name, unique_id, x, y, collisionid, gfxname, xspeed, yspeed);
		}
		
		public void move(){
			timer += gamespeed*2;
			
			x += Math.sin(0.1*timer)*xdir;
			y += Math.cos(0.1*timer)*ydir;
			
			if (y>pfHeight()) y = -8;
		}
		
		public void hit_bg(int tilecid) { 
			remove(); 
		}
		
	}

public class Enemy extends MovingObject {
		private int life;

		public Enemy(double x, double y, int xdir, int ydir) {
			super("enemy",true,x,y, ENEMY_CID, "enemy", xdir, ydir);
			life = ENEMY_LIFE;
		}
		
		public void hurt(){
			life--;
			if(life == 0){
				remove();
				score += 20;
			}
		}
		
		@Override
		public void hit(JGObject obj) {
			if(obj.colid == BULLET_CID){
				hurt();
				obj.remove();
				score += 1;
			}
		}
```
This piece of code was done after refactoring the code from original one. I observed that there were some common features between the objects so I made it a MovingObject class to include the common features to be shared across all the subclasses.
