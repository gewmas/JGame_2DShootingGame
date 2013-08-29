First project for CompSci 308 Fall 2013
======

The project is to implement a 2-D Top Down Scrolling Shooting Game calld "SpaceShooting". Players can control a space ship using keyboard to fire the enemies with different modes. The players can win the game after killing all the enemies, while they may fail the game after suffering too much harm from the enemy.

The project will implement different classes for spaceship, bullet and enemy in Java to control their behaviors and how they interact with each other. Multiple functions would be added to each character. The objective of the project is to how to implement a game in an object oriented way.

##GAME STATUS
There are two game status of the game, MENU and PLAYING. MENU shows options including START, EXIT and CHEAT KEYS. PLAYING is when the players actually play the game.

##Spaceship
Players can control the spaceship by keyboards. To move the spaceship, press UP, DOWN, LEFT, RIGHT. To fire, press SPACE and bullets will be fired in the way according to what bullet type the players have. When the spaceship touches an enemy, a life of totally three lives will be lost. When all three lives are lost, the game will be lost and return to MENU.

##Bullet
There are three different bullets for the spaceship. The bullet can be ungraded by gaining power bonus, which will show up randomly on the screen. Bullet 1 will be single bullet that goes straight up, Bullet

##Enemy
There are only two kinds of enemies, drone fighters and a boss. Drone fighters and the boss will have multiple lives and have to be killed by hitting couple times. The enemies have different path and speed falling down the screen.

##Level
There are 2 levels of the game, drone fighters level and boss level. When the players kill all drone fighters in level 1, the game would move forward to level 2 where a boss with stronger ability will show up.

##Cheat keys
Cheat keys can be used to change bullet mode, skip between level 1 and level 2, switch to menu or exit game.
