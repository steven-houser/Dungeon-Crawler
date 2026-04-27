Character.class: Character.java
	javac -g Character.java

Enemy.class: Enemy.java Character.class
	javac -g Enemy.java

Player.class: Player.java Character.class
	javac -g Player.java

Room.class: Room.java Enemy.class
	javac -g Room.java

Game.class: Game.java Player.class Room.class Enemy.class Character.class
	javac -g Game.java

run: Game.class
	java Game

testEnemy: Enemy.class
	java Enemy

testPlayer: Player.class
	java Player

clean:
	rm -f *.class savegame.dat

debug: Game.class
	jdb Game
