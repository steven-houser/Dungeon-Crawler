// Name: Steven Houser
// Project: Dungeon Crawler
// Date: 04/27/26

import java.util.*;
import java.io.*;

public class Room implements Serializable {

    String description = "";
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    Room nextRoom = null;

    // Constructor - creates an empty room with no enemies and no next room
    public Room(String description) {
        this.description = description;
        enemies = new ArrayList<Enemy>();
        nextRoom = null;
    } // end constructor

    // Add an enemy to this room
    public void addEnemy(Enemy e) {
        enemies.add(e);
    } // end addEnemy

    // Set the next room in the chain
    public void setNextRoom(Room r) {
        this.nextRoom = r;
    } // end setNextRoom

    // Return the next room
    public Room getNextRoom() {
        return this.nextRoom;
    } // end getNextRoom

    // Return true if this room still has enemies
    public boolean hasEnemies() {
        return !enemies.isEmpty();
    } // end hasEnemies

    // Return the room description
    public String getDescription() {
        return this.description;
    } // end getDescription

} // end class def
