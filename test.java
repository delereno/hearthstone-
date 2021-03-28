import java.util.ArrayList;
import java.util.Collections;
/**
 * driver class that creates and plays hearthstone battlegrounds (current 127 cards)
 *
 * @author del huang
 * @version 25/03/2021
 */
public class test
{
    // fields (instance variables)
    static final int MIN = 0;
    static final int MAX = 125; //mechano egg banned
    static final boolean RUNNING = true;
    public static void main (String [] args) {
        //creates card pool with all cards, player and enemy decks
        ArrayList<Card> cardPool = createCards();
        ArrayList<Card> plDeck = new ArrayList<Card>();
        ArrayList<Card> enemDeck = new ArrayList<Card>();
        
        //sets counter and index variables
        int plIdx = 0;
        int enemIdx = 0;
        int loopCount = 0;
        
        //adds 7 cards to each deck
        for (int i = 0; i < 7; i++) {
            plDeck.add(cardPool.get((int) (Math.random() * MAX + MIN)));
            enemDeck.add(cardPool.get((int) (Math.random() * MAX + MIN)));
        }
        
        //prints each deck, cards and stats
        System.out.println("Player Deck:");
        for (int i = 0; i < 7; i++) {
            plDeck.get(i).printStats();
        }
        System.out.println("\nOpponent Deck:");
        for (int i = 0; i < 7; i++) {
            enemDeck.get(i).printStats();
        }
        System.out.println("\n\n");
        
        //int shows deck priority: 1 is player, 2 is enemy
        int turnPriority = 0;
        
        // runs till there are no cards in one deck
        while (plDeck.size() > 0||enemDeck.size() > 0) {
            
            //Start of combat phase
            System.out.println("Combat Phase!");
            
            //checks which deck has more cards
            if (plDeck.size() > enemDeck.size()) {
                
                //TEST MESSAGE FOR TESTING
                System.out.println("Player deck has priority");
                turnPriority = 1;
                
            }
            else if (plDeck.size() < enemDeck.size()) {
                
                //TEST MESSAGE FOR TESTING
                System.out.println("Enemy deck has priority");
                turnPriority = 2;
                
            }
            else if (plDeck.size() == enemDeck.size()) {
                //TEST MESSAGE FOR TESTING
                System.out.println("Deck size identical; randomizing priority");
                turnPriority = (int) (Math.random() * 2 + 1);
                
                //TEST MESSAGES FOR TESTING
                if (turnPriority == 1) {
                    System.out.println("Player deck has priority");
                }
                else if (turnPriority == 2) {
                    System.out.println("Enemy deck has priority");
                }
            }
            
            // uses remaining card num to see how many times to loop each turn
            int plCardNum = plDeck.size();
            int enemCardNum = enemDeck.size();
            ArrayList<Card> plDeckActing = new ArrayList<Card>(plDeck);
            Collections.copy(plDeckActing,plDeck);
            ArrayList<Card> enemDeckActing = new ArrayList<Card>(enemDeck);
            Collections.copy(enemDeckActing,enemDeck);
            
            ArrayList<Card> plDeckActed = new ArrayList<Card>();
            ArrayList<Card> enemDeckActed = new ArrayList<Card>();
            
            int plNextIdx = 0;
            int enemNextIdx = 0;
            for (int i = 0; i < 7; i ++) {
                
                System.out.println(plCardNum);
                System.out.println(enemCardNum);
                
                System.out.println("Player Index | " + plNextIdx);
                System.out.println("Enemy Index | " + enemNextIdx);
                //checks to make sure index player is alive
                if (!plDeckActing.get(i).checkAlive()) {
                    while(RUNNING) {
                        plNextIdx++;
                            if (plDeckActing.get(plNextIdx).checkAlive()) {
                            break;
                        }
                    }
                }
                
                // plays until all player cards have acted
                if (plCardNum > 0 ) {
                    
                    // finds a random enemy card that is alive, sets as index
                    while(RUNNING) {
                        enemIdx = (int) (Math.random() * (enemCardNum-1));
                        
                        if (enemDeck.get(enemIdx).checkAlive()){
                            //System.out.println(enemIdx);
                            System.out.println(enemIdx);
                            break;
                        }
                    }
                    
                    // attacks the card
                    System.out.println(plDeckActing.get(plNextIdx).getName() + " attacks " + enemDeck.get(enemIdx).getName() + "!");
                    System.out.println("They do " + plDeckActing.get(plNextIdx).getAtk() + " damage!");
                    enemDeck.get(enemIdx).hit(plDeckActing.get(plNextIdx).getAtk());

                    // checks if the card is dead
                    enemDeck.get(enemIdx).checkDead();
                    if (!enemDeck.get(enemIdx).checkAlive()) {
                        System.out.println(enemDeck.get(enemIdx).getName() + " is defeated!\n");
                        // if the card has not acted yet this turn, removes from cards nums
                        if (!enemDeckActed.contains(enemDeck.get(enemIdx))) {
                            enemCardNum --;
                        }
                        enemDeck.remove(enemIdx);
                    }
                    
                    // if alive, prints new health 
                    else if (enemDeck.get(enemIdx).checkAlive()) {
                        System.out.println(enemDeck.get(enemIdx).getName() + " now has " + enemDeck.get(enemIdx).getHp() + " health!\n");
                    }
                    
                    // Adds card to "already acted list"
                    plDeckActed.add(plDeckActing.get(plNextIdx));
                    
                }
                
                // if card dies mid turn, goes to next card in deck
                
                if (!enemDeckActing.get(i).checkAlive()) {
                    while(RUNNING) {
                        enemNextIdx++;
                        if (enemDeckActing.get(enemNextIdx).checkAlive()) {
                            break;
                        }
                    }
                }
                
                // plays until all enemy cards have acted
                if (enemCardNum > 0) {
                    
                    // finds a random card that is alive, sets as index
                    while(RUNNING) {
                        plIdx = (int) (Math.random() *  (plCardNum-1));
                        
                        if (plDeck.get(plIdx).checkAlive()){
                            //System.out.println(plIdx);
                            System.out.println(plIdx);
                            break;
                        }
                    }
                    
                    // attacks the card
                    System.out.println(enemDeckActing.get(enemNextIdx).getName() + " attacks " + plDeck.get(plIdx).getName() + "!");
                    System.out.println("They do " + enemDeckActing.get(enemNextIdx).getAtk() + " damage!");
                    plDeck.get(plIdx).hit(enemDeckActing.get(enemNextIdx).getAtk());
                    
                    
                    // checks if the card is dead
                    plDeck.get(plIdx).checkDead();
                    if (!plDeck.get(plIdx).checkAlive()) {
                        System.out.println(plDeck.get(plIdx).getName() + " is defeated!\n");
                        
                        // if the card has not acted yet this turn, removes from cards nums
                        if (!plDeckActed.contains(plDeck.get(plIdx))) {
                            plCardNum --;
                        }
                        plDeck.remove(plIdx);
                    }
                    
                    // if alive, prints new health 
                    else if (plDeck.get(plIdx).checkAlive()) {
                        System.out.println(plDeck.get(plIdx).getName() + " now has " + plDeck.get(plIdx).getHp() + " health!\n");
                    }
                    
                    //Adds card to already acted list
                    enemDeckActed.add(enemDeckActing.get(enemNextIdx));
                }
                
                //Adjusts the number of cards that still need to act
                plCardNum --;
                enemCardNum --;
                
                // -1 for dead idx, so they can be skipped next turn
                if (plDeckActing.get(i).checkAlive()) {
                    plNextIdx++;
                }
                
                
                if (enemDeckActing.get(i).checkAlive()) {
                    enemNextIdx++;
                }
                
                
            }
        }
        
        
        System.out.println("Player Deck:");
        for (int i = 0; i < 7; i++) {
            if (plDeck.get(i).checkAlive()){
                plDeck.get(i).printStats();
            }
        }
        System.out.println("\nOpponent Deck:");
        for (int i = 0; i < 7; i++) {
            if (enemDeck.get(i).checkAlive()){
                enemDeck.get(i).printStats();
            }
        }
    }
    
    
    public static ArrayList createCards() {
        ArrayList<Card> cardPool = new ArrayList<Card>();
        cardPool.add(new Card("Alleycat",1,1,false,false,false,false));
        cardPool.add(new Card("Scavenging Hyena",2,2,false,false,false,false));
        cardPool.add(new Card("Kindly Grandmother",1,1,false,false,false,false));
        cardPool.add(new Card("Pack Leader",2,3,false,false,false,false));
        cardPool.add(new Card("Rabid Saurolisk",3,2,false,false,false,false));
        cardPool.add(new Card("Houndmaster",4,3,false,false,false,false));
        cardPool.add(new Card("Infested Wolf",3,3,false,false,false,false));
        cardPool.add(new Card("Monstrous Macaw",4,3,false,false,false,false));
        cardPool.add(new Card("Rat Pack",2,2,false,false,false,false));
        cardPool.add(new Card("Cave Hydra",2,4,false,false,false,false));
        cardPool.add(new Card("Savannah Highmane",6,5,false,false,false,false));
        cardPool.add(new Card("Virmen Sensei",4,5,false,false,false,false));
        cardPool.add(new Card("Ironhide Direhorn",7,7,false,false,false,false));
        cardPool.add(new Card("Mama Bear",4,4,false,false,false,false));
        cardPool.add(new Card("Maexxna",2,8,false,false,false,false));
        cardPool.add(new Card("Ghastcoiler",7,7,false,true,false,false));
        cardPool.add(new Card("Goldrinn, the Great Wolf",4,4,false,false,false,false));
        
        cardPool.add(new Card("Fiendish servant",2,1,false,false,false,false));
        cardPool.add(new Card("Vulgar Homunculus",2,4,true,false,false,false));
        cardPool.add(new Card("Wrath Weaver",1,3,false,false,false,false));
        cardPool.add(new Card("Imprisoner",3,3,true,false,false,false));
        cardPool.add(new Card("Nathrezim Overseer",2,3,false,false,false,false));
        cardPool.add(new Card("Imp Gang Boss",2,4,false,false,false,false));
        cardPool.add(new Card("Soul Devourer",3,3,false,false,false,false));
        cardPool.add(new Card("Crystalweaver",5,4,false,false,false,false));
        cardPool.add(new Card("Soul Juggler",3,3,false,false,false,false));
        cardPool.add(new Card("Bigfernal",4,4,false,false,false,false));
        cardPool.add(new Card("Ring Matron",6,4,true,false,false,false));
        cardPool.add(new Card("Siegebreaker",5,8,true,false,false,false));
        cardPool.add(new Card("Annihilan Battlemaster",3,1,false,false,false,false));
        cardPool.add(new Card("Mal'Ganis",9,7,false,false,false,false));
        cardPool.add(new Card("Voidlord",3,9,true,false,false,false));
        cardPool.add(new Card("Imp Mama",6,10,false,false,false,false));
        
        cardPool.add(new Card("Dragonspawn Lieutenant",2,3,true,false,false,false));
        cardPool.add(new Card("Red Whelp",1,2,false,false,false,false));
        cardPool.add(new Card("Glyph Guardian",2,4,false,false,false,false));
        cardPool.add(new Card("Steward of Time",3,4,false,false,false,false));
        cardPool.add(new Card("Waxrider Togwaggle",1,3,false,false,false,false));
        cardPool.add(new Card("Bronze Warden",2,1,false,false,true,false));
        cardPool.add(new Card("Hangry Dragon",4,4,false,false,false,false));
        cardPool.add(new Card("Twilight Emissary",4,4,true,false,false,false));
        cardPool.add(new Card("Cobalt Scalebane",5,5,false,false,false,false));
        cardPool.add(new Card("Drakonid Enforcer",3,6,false,false,false,false));
        cardPool.add(new Card("Herald of Flame",5,6,false,false,false,false));
        cardPool.add(new Card("Murozond",5,5,false,false,false,false));
        cardPool.add(new Card("Razorgore, the Untamed",2,4,false,false,false,false));
        cardPool.add(new Card("Kalecgos, Arcane Aspect",4,12,false,false,false,false));
        cardPool.add(new Card("Nadina the Red",7,4,false,false,false,false));
        
        cardPool.add(new Card("Refreshing Anomaly",1,3,false,false,false,false));
        cardPool.add(new Card("Sellemental",2,2,false,false,false,false));
        cardPool.add(new Card("Molten Rock",2,4,true,false,false,false));
        cardPool.add(new Card("Party Elemental",3,2,false,false,false,false));
        cardPool.add(new Card("Arcane Assistant",3,3,false,false,false,false));
        cardPool.add(new Card("Crackling Cyclone",4,1,false,false,true,true));
        cardPool.add(new Card("Stasis Elemental",4,4,false,false,false,false));
        cardPool.add(new Card("Majordomo Executus",6,3,false,false,false,false));
        cardPool.add(new Card("Wildfire Elemental",7,3,false,false,false,false));
        cardPool.add(new Card("Nomi, Kitchen Nightmare",4,4,false,false,false,false));
        cardPool.add(new Card("Tavern Tempest",4,4,false,false,false,false));
        cardPool.add(new Card("Gentle Djinni",4,5,true,false,false,false));
        cardPool.add(new Card("Lieutenant Garr",5,1,true,false,false,false));
        cardPool.add(new Card("Lil' Rag",4,4,false,false,false,false));
        
        cardPool.add(new Card("Micro Machine",1,2,false,false,false,false));
        cardPool.add(new Card("Micro Mummy",1,2,false,false,false,false));
        cardPool.add(new Card("Harvest Golem",2,3,false,false,false,false));
        cardPool.add(new Card("Kaboom Bot",2,2,false,false,false,false));
        cardPool.add(new Card("Metaltooth Leaper",3,3,false,false,false,false));
        cardPool.add(new Card("Deflect-o-Bot",3,2,false,false,true,false));
        cardPool.add(new Card("Iron Sensei",2,2,false,false,false,false));
        cardPool.add(new Card("Piloted Shredder",4,3,false,false,false,false));
        cardPool.add(new Card("Screwjank Clunker",2,5,false,false,false,false));
        cardPool.add(new Card("Replicating Menace",3,1,false,false,false,false));
        cardPool.add(new Card("Annoy-o-Module",2,4,true,false,true,false));
        //cardPool.add(new Card("Mechano-Egg",0,5,false,false,false,false));
        cardPool.add(new Card("Security Rover",2,6,false,false,false,false));
        cardPool.add(new Card("Junkbot",1,5,false,false,false,false));
        cardPool.add(new Card("Sneed's Old Shredder",5,7,false,false,false,false));
        cardPool.add(new Card("Foe Reaper 4000",6,9,false,false,false,false));
        cardPool.add(new Card("Kangor's Apprentice",4,8,false,false,false,false));
        
        cardPool.add(new Card("Murloc Tidecaller",1,2,false,false,false,false));
        cardPool.add(new Card("Murloc Tidehunter",2,1,false,false,false,false));
        cardPool.add(new Card("Rockpool Hunter",2,3,false,false,false,false));
        cardPool.add(new Card("Murloc Warleader",2,3,false,false,false,false));
        cardPool.add(new Card("Old Murk-Eye",2,4,false,false,false,false));
        cardPool.add(new Card("Coldlight Seer",2,3,false,false,false,false));
        cardPool.add(new Card("Felfin Navigator",4,4,false,false,false,false));
        cardPool.add(new Card("Primalfin Lookout",3,2,false,false,false,false));
        cardPool.add(new Card("Toxfin",1,2,false,false,false,false));
        cardPool.add(new Card("King Bagurgle",6,3,false,false,false,false));
        
        cardPool.add(new Card("Deck Swabbie",2,2,false,false,false,false));
        cardPool.add(new Card("Scallywag",2,1,false,false,false,false));
        cardPool.add(new Card("Freedealing Gambler",3,3,false,false,false,false));
        cardPool.add(new Card("Southsea Captain",3,3,false,false,false,false));
        cardPool.add(new Card("Yo-Ho-Ogre",2,6,true,false,false,false));
        cardPool.add(new Card("Bloodsail Cannoneer",4,3,false,false,false,false));
        cardPool.add(new Card("Salty Looter",4,4,false,false,false,false));
        cardPool.add(new Card("Southsea Strongarm",4,3,false,false,false,false));
        cardPool.add(new Card("Goldgrubber",2,2,false,false,false,false));
        cardPool.add(new Card("Ripsnarl Captain",4,5,false,false,false,false));
        cardPool.add(new Card("Cap'n Hoggarr",6,6,false,false,false,false));
        cardPool.add(new Card("Nat Pagle, Extreme Angler",8,5,false,false,false,false));
        cardPool.add(new Card("Seabreaker Goliath",6,7,false,false,false,true));
        cardPool.add(new Card("Dread Admiral Eliza",6,7,false,false,false,false));
        cardPool.add(new Card("The Tide Razor",6,4,false,false,false,false));
        
        cardPool.add(new Card("Acolyte of C'Thun",2,2,true,false,false,false));
        cardPool.add(new Card("Menagerie Mug",2,2,false,false,false,false));
        cardPool.add(new Card("Selfless Hero",2,1,false,false,false,false));
        cardPool.add(new Card("Spawn of N'Zoth",2,2,false,false,false,false));
        cardPool.add(new Card("Tormented Ritualist",2,3,true,false,false,false));
        cardPool.add(new Card("Unstable Ghoul",1,3,true,false,false,false));
        cardPool.add(new Card("Arm of the Empire",4,5,false,false,false,false));
        cardPool.add(new Card("Khadgar",2,2,false,false,false,false));
        cardPool.add(new Card("Warden of Old",3,3,false,false,false,false));
        cardPool.add(new Card("Bolvar, Fireblood",1,7,false,false,true,false));
        cardPool.add(new Card("Champion of Y'Shaarj",4,4,false,false,false,false));
        cardPool.add(new Card("Defender of Argus",2,3,false,false,false,false));
        cardPool.add(new Card("Menagerie Jug",3,3,false,false,false,false));
        cardPool.add(new Card("Qiraji Harbinger",5,5,false,false,false,false));
        cardPool.add(new Card("Baron Rivendare",1,7,false,false,false,false));
        cardPool.add(new Card("Brann Bronzebeard",2,4,false,false,false,false));
        cardPool.add(new Card("Deadly Spore",1,1,false,true,false,false));
        cardPool.add(new Card("Faceless Taverngoer",4,4,false,false,false,false));
        cardPool.add(new Card("Lightfang Enforcer",2,2,false,false,false,false));
        cardPool.add(new Card("Mythrax the Unraveler",4,4,false,false,false,false));
        cardPool.add(new Card("Strongshell Scavenger",2,3,false,false,false,false));
        cardPool.add(new Card("Amalgadon",6,6,false,false,false,false));
        cardPool.add(new Card("Zapp Slywick",7,10,false,false,false,true));
        return cardPool;
    }
}
