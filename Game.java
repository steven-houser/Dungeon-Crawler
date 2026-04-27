// Name: Steven Houser
// Project: Dungeon Crawler
// Date: 04/27/26

import java.util.*;
import java.io.*;

public class Game {

    Player player = null;
    Room currentRoom = null;

    public static void main(String[] args) {
        new Game();
    } // end main

    // Constructor - load save or start fresh, then run and save on exit
    public Game() {
        loadGame();
        if (player == null) {
            Scanner input = new Scanner(System.in);
            System.out.print("What is your name? ");
            String name = input.nextLine();
            player = new Player(name);
            buildRooms();
            System.out.println("Welcome, " + player.getName() + ". Your adventure begins...");
        } // end if
        start();
        saveGame();
    } // end constructor

    // Build the 5-room chain and set starting room
    public void buildRooms() {
        Room r1 = new Room("Dark Entrance");
        Room r2 = new Room("Goblin Camp");
        Room r3 = new Room("Old Armory");
        Room r4 = new Room("Throne Room");
        Room r5 = new Room("Final Gate");

        r1.addEnemy(new Enemy("Goblin", "Goblin", 15, 4));
        r2.addEnemy(new Enemy("Orc Scout", "Orc", 20, 5));
        r2.addEnemy(new Enemy("Orc Guard", "Orc", 18, 5));
        r3.addEnemy(new Enemy("Skeleton", "Skeleton", 22, 6));
        r4.addEnemy(new Enemy("Dark Knight", "Knight", 30, 8));
        r5.addEnemy(new Enemy("Dragon Whelp", "Dragon", 40, 10));

        r1.setNextRoom(r2);
        r2.setNextRoom(r3);
        r3.setNextRoom(r4);
        r4.setNextRoom(r5);

        currentRoom = r1;
    } // end buildRooms

    // Display the room menu and return user choice
    public String menu() {
        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.println("--- " + currentRoom.getDescription() + " ---");
        System.out.println();
        System.out.println("0) Check status");
        if (currentRoom.hasEnemies()) {
            System.out.println("1) Fight");
        } // end if
        if (currentRoom.getNextRoom() != null) {
            System.out.println("2) Move forward");
        } // end if
        System.out.println("3) Save and quit");
        System.out.println();
        System.out.print("Action: ");
        String response = input.nextLine();
        return response;
    } // end menu

    // Main game loop
    public void start() {
        boolean keepGoing = true;
        while (keepGoing) {
            String response = menu();
            if (response.equals("0")) {
                checkStatus();
            } else if (response.equals("1")) {
                battle();
            } else if (response.equals("2")) {
                moveForward();
            } else if (response.equals("3")) {
                keepGoing = false;
            } else {
                System.out.println("Not a valid option.");
            } // end if
        } // end while
    } // end start

    // Print the player's current status
    public void checkStatus() {
        System.out.println(player.getInfo());
    } // end checkStatus

    // Run a combat loop against the first enemy in the room
    public void battle() {
        if (!currentRoom.hasEnemies()) {
            System.out.println("No enemies here.");
            return;
        } // end if
        Enemy enemy = currentRoom.enemies.get(0);
        System.out.println("A " + enemy.getName() + " appears!");
        System.out.println(enemy.getInfo());
        Scanner input = new Scanner(System.in);
        boolean fightGoing = true;
        boolean concentrated = false;
        while (fightGoing) {
            System.out.println();
            System.out.println("0) Attack");
            System.out.println("1) Concentrate (next attack deals double damage)");
            System.out.println("2) Heal (restore 5 HP)");
            System.out.print("Action: ");
            String choice = input.nextLine();
            if (choice.equals("0")) {
                int damage = player.attackPower;
                if (concentrated) {
                    damage *= 2;
                    concentrated = false;
                    System.out.println(player.getName() + " unleashes a focused strike!");
                } // end if
                enemy.takeDamage(damage);
                System.out.println(player.getName() + " attacks for " + damage +
                                   " damage. " + enemy.getName() + " HP: " + enemy.getHp());
                if (!enemy.isAlive()) {
                    System.out.println("You defeated the " + enemy.getName() + "!");
                    currentRoom.enemies.remove(0);
                    fightGoing = false;
                } else {
                    player.takeDamage(enemy.attackPower);
                    System.out.println(enemy.getName() + " attacks for " + enemy.attackPower +
                                       " damage. " + player.getName() + " HP: " + player.getHp());
                    if (!player.isAlive()) {
                        System.out.println("You were defeated. Game over.");
                        System.exit(0);
                    } // end if
                } // end if
            } else if (choice.equals("1")) {
                concentrated = true;
                System.out.println(player.getName() + " concentrates...");
                player.takeDamage(enemy.attackPower);
                System.out.println(enemy.getName() + " attacks for " + enemy.attackPower +
                                   " damage. " + player.getName() + " HP: " + player.getHp());
                if (!player.isAlive()) {
                    System.out.println("You were defeated. Game over.");
                    System.exit(0);
                } // end if
            } else if (choice.equals("2")) {
                player.heal(5);
                System.out.println(player.getName() + " heals for 5 HP. HP: " +
                                   player.getHp() + "/" + player.getMaxHp());
                player.takeDamage(enemy.attackPower);
                System.out.println(enemy.getName() + " attacks for " + enemy.attackPower +
                                   " damage. " + player.getName() + " HP: " + player.getHp());
                if (!player.isAlive()) {
                    System.out.println("You were defeated. Game over.");
                    System.exit(0);
                } // end if
            } else {
                System.out.println("Not a valid option.");
            } // end if
        } // end while
    } // end battle

    // Move to the next room in the chain
    public void moveForward() {
        if (currentRoom.hasEnemies()) {
            System.out.println("You must defeat all enemies before moving on.");
            return;
        } // end if
        if (currentRoom.getNextRoom() == null) {
            System.out.println("There are no more rooms. You win!");
            System.exit(0);
        } // end if
        currentRoom = currentRoom.getNextRoom();
        System.out.println("You move into the next room.");
    } // end moveForward

    // Save player and current room to file
    public void saveGame() {
        try {
            FileOutputStream fo = new FileOutputStream("savegame.dat");
            ObjectOutputStream obOut = new ObjectOutputStream(fo);
            obOut.writeObject(player);
            obOut.writeObject(currentRoom);
            obOut.close();
            fo.close();
            System.out.println("Game saved. Goodbye, " + player.getName() + ".");
        } catch (Exception e) {
            System.out.println("Could not save game: " + e.getMessage());
        } // end try
    } // end saveGame

    // Load player and current room from file
    public void loadGame() {
        try {
            FileInputStream fIn = new FileInputStream("savegame.dat");
            ObjectInputStream obIn = new ObjectInputStream(fIn);
            player = (Player) obIn.readObject();
            currentRoom = (Room) obIn.readObject();
            obIn.close();
            fIn.close();
            System.out.println("Loading saved game...");
            System.out.println("Welcome back, " + player.getName() + "!");
        } catch (Exception e) {
            // No save file found - will start a new game
        } // end try
    } // end loadGame

} // end class def
