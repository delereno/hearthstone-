
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
     * Constructor for objects of class Card
     */
    public Card(String nm,int attk, int hlth, boolean tnt, boolean psn, boolean ds, boolean wf)
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
    public int getHp() {
        return hp;
    }
    public String getName() {
        return name;
    }
    public void checkDead(){
        if (hp <= 0) {
            alive = false;
        }
    }
    public boolean checkAlive() {
        return alive;
    }
    public int getAtk() {
        return atk;
    }
    public void hit(int dmg){
        hp -= dmg;
    }
    public void printStats()
    {
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
