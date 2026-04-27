// Name: Steven Houser
// Project: Dungeon Crawler
// Date: 04/27/26

import java.util.*;
import java.io.*;

public class Player extends Character implements Serializable {

    public static void main(String[] args) {
        Player p = new Player("Steve");
        System.out.println(p.getInfo());
    } // end main

    // Constructor - default stats: 20 HP, 8 attack power
    public Player(String name) {
        super(name, 20, 8);
    } // end constructor

    // Return a formatted info string for this player
    public String getInfo() {
        return "--- " + name + " ---\n" +
               "HP: " + hp + " / " + maxHp + "\n" +
               "Attack Power: " + attackPower;
    } // end getInfo

} // end class def
