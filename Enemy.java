// Name: Steven Houser
// Project: Dungeon Crawler
// Date: 04/27/26

import java.util.*;
import java.io.*;

public class Enemy extends Character implements Serializable {

    String enemyType = "";

    public static void main(String[] args) {
        Enemy e = new Enemy("Goblin", "Goblin", 15, 4);
        System.out.println(e.getInfo());
    } // end main

    // Constructor - calls super and sets enemy type
    public Enemy(String name, String enemyType, int hp, int attackPower) {
        super(name, hp, attackPower);
        this.enemyType = enemyType;
    } // end constructor

    // Return a formatted info string for this enemy
    public String getInfo() {
        return enemyType + " | HP: " + hp + "/" + maxHp;
    } // end getInfo

} // end class def
