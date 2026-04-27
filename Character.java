// Name: Steven Houser
// Project: Dungeon Crawler
// Date: 04/27/26

import java.util.*;
import java.io.*;

public abstract class Character implements Serializable {

    String name = "";
    int hp = 0;
    int maxHp = 0;
    int attackPower = 0;

    // Constructor - sets name, hp, maxHp, and attackPower
    public Character(String name, int hp, int attackPower) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
    } // end constructor

    // Return the character's name
    public String getName() {
        return this.name;
    } // end getName

    // Return current HP
    public int getHp() {
        return this.hp;
    } // end getHp

    // Return max HP
    public int getMaxHp() {
        return this.maxHp;
    } // end getMaxHp

    // Return true if character has HP remaining
    public boolean isAlive() {
        return hp > 0;
    } // end isAlive

    // Subtract damage from HP, floor at zero
    public void takeDamage(int amount) {
        hp -= amount;
        if (hp < 0) {
            hp = 0;
        } // end if
    } // end takeDamage

    // Restore HP by amount, cap at maxHp
    public void heal(int amount) {
        hp += amount;
        if (hp > maxHp) {
            hp = maxHp;
        } // end if
    } // end heal

    // Return a formatted info string - subclasses must implement
    public abstract String getInfo();

} // end class def
