
/**
 * Creates a Card object
 *
 * @author Del Huang
 * @version 25/03/2021
 */
public class Card
{
    // instance variables 
    private String name;
    private int atk;
    private int hp;
    private boolean alive = true;
    private boolean taunt = false;
    private boolean poison = false;
    private boolean diShield = false;
    private boolean windFury = false;
    /**
     * Constructor for objects of Card class
     * @param nm card name
     * @param attk card attack value 
     * @param hlth card hp value
     * @param tnt true if card has taunt
     * @param psn true if card has posion
     * @param ds true if card has divine shield
     * @param wf true if card has windfury
     */
    public Card(String nm, int attk, int hlth, 
        boolean tnt, boolean psn, boolean ds, boolean wf)
    {
        // maybe find a way so i can use less parameters?
        name = nm;
        atk = attk;
        hp = hlth;
        taunt = tnt;
        poison = psn;
        diShield = ds;
        windFury = wf;
    }
    /**
     * Gives the card hp value
     * @return hp
     */
    public int getHp() {
        return hp;
    }
    /**
     * Gives the card name
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * checks if card is dead
     */
    public void checkDead() {
        //if hp is less than or 0, return false
        if (hp <= 0) {
            alive = false;
        }
    }
    /**
     * returns true if card is alive
     * @return alive
     */
    public boolean checkAlive() {
        return alive;
    }
    /**
     * returns true if card has taunt
     * @return taunt
     */
    public boolean checkTaunt() {
        return taunt;
    }
    /**
     * returns true if card has poison
     * @return posion
     */
    public boolean checkPoison() {
        return poison;
    }
    /**
     * Instantly kills the card
     */
    public void poison() {
        hp = 0;
    }
    /**
     * returns true if card has Divine Shield
     * @return diShield 
     */
    public boolean checkDivine() {
        return diShield;
    }
    
    /**
     * removes Divine Shield
     */
    public void removeDivine() {
        diShield = false;
    } 
    /**
     * returns true if card has WindFury
     * @return windFury
     */
    public boolean checkWind() {
        return windFury;
    }
    /**
     * Gives the card attack value
     * @return atk
     */
    public int getAtk() {
        return atk;
    }
    /**
     * calculates card damage taken
     * @param dmg attacker atk value
     */
    public void hit(int dmg) {
        hp -= dmg;
    }
    /**
     * Prints card stats
     */
    public void printStats() {
        System.out.print("\n" + name + ":(" + atk + "/" + hp + ")");
        if (taunt) {
            System.out.print(" TAUNT ");
        } 
        if (poison) {
            System.out.print(" POISON ");
        } 
        if (diShield) {
            System.out.print(" DIVINE SHIELD ");
        } 
        if (windFury) {
            System.out.print(" WINDFURY ");
        } 
    }
    
}
