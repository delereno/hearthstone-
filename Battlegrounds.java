import java.util.ArrayList;
import java.util.Collections;
/**
 * driver class that creates and plays hearthstone battlegrounds 
 * (current 127 cards)
 * @author del huang
 * @version 25/03/2021
 */
public class Battlegrounds
{
    // fields (instance variables)
    static final boolean RUNNING = true;
    static final int MIN = 0;
    static final int MAX = 125; //mechano egg card banned
    static final int CARDNUM = 7;
    static final int NOCARDS = 0;
    static final int MAXTURN = 2;
    static final int MINTURN = 1;
    static int outcome; //outcome, 0 = draw, 1 = p1 win, 2 = p2 win
    /**
     * simulates one battleground game
     * @param args all command line arguments given by the user
     */
    public static void main (String [] args) {
        //creates card pool with all cards, player and enemy decks
        ArrayList<Card> cardPool = createCards();
        ArrayList<Card> plDeck = new ArrayList<Card>();
        ArrayList<Card> enemDeck = new ArrayList<Card>();
        
        //sets deck indexs and counter variables
        int plIdx = 0;
        int enemIdx = 0;
        int loopCount = 0;
        
        //adds 7 cards to each deck
        for (int i = 0; i < CARDNUM; i++) {
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

        
        
        // runs till there are no cards in one deck
        while (plDeck.size() > NOCARDS && enemDeck.size() > NOCARDS) {
            
            //Start of combat phase
            System.out.println("Combat Phase!");
            
            //int shows deck priority: 1 is player, 2 is enemy
            int turnPriority = 0;
            
            //checks which deck has more cards, determines turn order
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
            
            //if deck sizes are equal, randomises turn order
            else if (plDeck.size() == enemDeck.size()) {
                //TEST MESSAGE FOR TESTING
                System.out.println("Deck size identical; randomizing priority");
                turnPriority = (int) (Math.random() * MAXTURN + MINTURN);
                
                //TEST MESSAGES FOR TESTING
                if (turnPriority == 1) {
                    turnPriority = 1;
                    System.out.println("Player deck has priority");
                }
                else if (turnPriority == 2) {
                    turnPriority = 2;
                    System.out.println("Enemy deck has priority");
                }
            }
            
            // resets indexes every loop
            plIdx = 0;
            enemIdx = 0;
            
            // deck 1 goes first
            if (turnPriority == 1) {
                //runs till all cards in one deck have been played
                do {
                    //runs first attack
                    plIdx = battle(plDeck, enemDeck, plIdx);
                    //checks if either deck has been fully played
                    if (checkEnd(plIdx, plDeck) 
                        || checkEnd(enemIdx, enemDeck) ) {
                        break;
                    }
                    //runs second attack
                    enemIdx = battle(enemDeck, plDeck, enemIdx);
                } 
                while (plIdx < plDeck.size() && enemIdx < enemDeck.size());
            }
            
            // deck 2 goes first
            if (turnPriority == 2) {
                //runs till all cards in one deck have played                
                do {
                    //runs first attack
                    enemIdx = battle(enemDeck, plDeck, enemIdx);
                    //checks if either deck has been fully played
                    if (checkEnd(plIdx, plDeck) 
                        || checkEnd(enemIdx, enemDeck) ) {
                        break;
                    }
                    //runs second attack
                    plIdx = battle(plDeck, enemDeck, plIdx);
                } 
                while (plIdx < plDeck.size() && enemIdx < enemDeck.size());
            }
            
            //removes dead cards from both decks
            removeDead(plDeck);
            removeDead(enemDeck);
            
            //prints both updated decks
            System.out.println("Player Deck:");
            for (int i = 0; i < plDeck.size(); i++) {
                if (plDeck.get(i).checkAlive()) {
                    plDeck.get(i).printStats();
                }
            }
            System.out.println("\nOpponent Deck:");
            for (int i = 0; i < enemDeck.size(); i++) {
                if (enemDeck.get(i).checkAlive()) {
                    enemDeck.get(i).printStats();
                }
            }
        }
        //checks winning outcome
        checkWinner(plDeck, enemDeck);
    }
    /**
     * Simluates the battle phase for one card
     * @return idx new index value
     * @param attacker attacking deck
     * @param defender defending deck
     * @param idx attacker index
     */
    public static int battle(ArrayList<Card> attacker, 
        ArrayList<Card> defender, int idx) {
        //runs if there are still cards to be played
        if (idx < attacker.size()) {
            // checks if there are any card targets left,if not, returns 
            for (int i = 0; i < defender.size(); i++) {
                if (defender.get(i).checkAlive()) {
                    break;
                }
                if (i == (defender.size() - 1)) {
                    return idx;
                }       
            }
            
            int i = 0; // index for the defending card
            if (hasTaunt(defender)) {
                i = taunt(defender);
            }
            else {
                while (RUNNING) {
                    // randomly selects a target card to defend, checks if alive
                    i = (int) (Math.random() * (defender.size()));
                    if (defender.get(i).checkAlive()) {
                        break;
                    }
                }
            }

            
            //if attacker is alive, attacks, defender counterattacks
            if (attacker.get(idx).checkAlive()) {
                attack(attacker.get(idx), defender.get(i));
                attack(defender.get(i), attacker.get(idx));
            }
            //if attacker is dead, adds to index to check next card
            else {
                while (RUNNING) {
                    idx++;
                    // breaks if no playable cards remaining
                    if (checkEnd(idx, attacker)) {
                        break;
                    }
                    //if alive, card attacks, defender counterattacks
                    if (attacker.get(idx).checkAlive()) {
                        attack(attacker.get(idx), defender.get(i));
                        attack(defender.get(i), attacker.get(idx));
                        break;
                    }
                }
            }
            idx++; // adds to index
        }
        return idx; //returns new deck index value
    }
    /**
     * checks if a deck has taunt
     * @param deck defending deck
     * @return true if taunt card present
     */
    public static boolean hasTaunt(ArrayList<Card> deck) {
        //checks if any card in the defending deck are alive and have taunt
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).checkAlive() && deck.get(i).checkTaunt()) {
                return true; // if so, return true
            }
        }
        return false;
    }
    /**
     * randomsly selects a taunt card from deck
     * @param deck defending deck
     * @return index of taunt card selected
     */
    public static int taunt(ArrayList<Card> deck) {
        //Picks a random card that has taunt, sets as target index
        while (RUNNING) {
            int i = (int) (Math.random() * (deck.size()));
            if (deck.get(i).checkTaunt()) {
                return i;
            }
        }
    }
    /**
     * Simulates a card's attack
     * @param attacker attacking card
     * @param defender defending card
     */
    public static void attack(Card attacker, Card defender) {
        // prints damage output
        System.out.println(attacker.getName() + 
            " attacks " + defender.getName() + "!");
        System.out.println("They do " + attacker.getAtk() + " damage!");

        if (attacker.checkPoison()) {
            System.out.println("Poison'd!");
            defender.poison(); //if attacker has poison, instakills
        }
        //if card has windfury attribute, calculates double damage
        else if (attacker.checkWind()) {
            System.out.println("Wind'd!");
            defender.hit(attacker.getAtk() * 2); //calculates double damage
        }
        //if card has divine shield attribute avoids attack (one-time use)
        else if (defender.checkDivine()) {
            System.out.println("Shield'd!");
            defender.removeDivine(); //removes attribute
        }
        else {
            defender.hit(attacker.getAtk()); //calculates damage
        }
        // checks if the card is dead, if so changes alive to false
        defender.checkDead();
        if (!defender.checkAlive()) {
            System.out.println(defender.getName() + " is defeated!\n"); 
        }
    }
    /**
     * Removes dead cards from deck
     * @param deck target deck
     */
    public static void removeDead(ArrayList<Card> deck) {
        int idx = 0; // deck index
        //travels the array, removes cards if dead
        while (RUNNING) {
            if (!deck.get(idx).checkAlive()) {
                deck.remove(deck.get(idx));
            }
            else if (deck.get(idx).checkAlive()) {
                idx++; // adds to index
            }
            if (idx == deck.size()) {
                break; // breaks when fully travelled array
            }
        }
    }
    /**
     * checks if the index has exceeded the deck card number
     * @param idx index
     * @param deck target card deck
     * @return true if has exceeded, false if not
     */
    public static boolean checkEnd(int idx, ArrayList<Card> deck) {
        //if the index is greater than array size, returns true
        if (idx >= deck.size()) {
            return true;
        }
        return false;
    }
    /**
     * checks the battlegrounds winning deck
     * @param plDeck player deck
     * @param enemDeck enemy deck
     * @return outcome winning outcome
     */
    public static int checkWinner(ArrayList<Card> plDeck,
        ArrayList<Card> enemDeck) {
        //declare winner/draw
        if (plDeck.size() == NOCARDS && enemDeck.size() == NOCARDS) {
            System.out.println("It's a draw!");
            outcome = 0;
        }
        else if (plDeck.size() != NOCARDS && enemDeck.size() == NOCARDS) {
            System.out.println("Player 1 wins!");
            outcome = 1;
        }
        else if (plDeck.size() == NOCARDS && enemDeck.size() != NOCARDS) {
            System.out.println("Player 2 wins!");
            outcome = 2;
        }
        return outcome; //returns winner
    }
    /**
     * Creates data for card pool
     * @return cardpool card pool array
     */
    public static ArrayList createCards() {
        //creates a card pool array
        ArrayList<Card> cardPool = new ArrayList<Card>();
        
        //adds cards and their data to card pool array
        cardPool.add(new Card("Alleycat", 1, 1, false,
            false, false, false));
        cardPool.add(new Card("Scavenging Hyena", 2, 2, false,
            false, false, false));
        cardPool.add(new Card("Kindly Grandmother", 1, 1, false,
            false, false, false));
        cardPool.add(new Card("Pack Leader", 2, 3, false, 
            false, false, false));
        cardPool.add(new Card("Rabid Saurolisk", 3, 2, false,
            false, false, false));
        cardPool.add(new Card("Houndmaster", 4, 3, false,
            false, false, false));
        cardPool.add(new Card("Infested Wolf", 3, 3, false,
            false, false, false));
        cardPool.add(new Card("Monstrous Macaw", 4, 3, false,
            false, false, false));
        cardPool.add(new Card("Rat Pack", 2, 2, false,
            false, false, false));
        cardPool.add(new Card("Cave Hydra", 2, 4, false,
            false, false, false));
        cardPool.add(new Card("Savannah Highmane", 6, 5, false,
            false, false, false));
        cardPool.add(new Card("Virmen Sensei", 4, 5, false,
            false, false, false));
        cardPool.add(new Card("Ironhide Direhorn", 7, 7, false,
            false, false, false));
        cardPool.add(new Card("Mama Bear", 4, 4, false,
            false, false, false));
        cardPool.add(new Card("Maexxna", 2, 8, false,
            false, false, false));
        cardPool.add(new Card("Ghastcoiler", 7, 7, false,
            true, false, false));
        cardPool.add(new Card("Goldrinn, the Great Wolf", 4, 4, false,
            false, false, false));
        
        cardPool.add(new Card("Fiendish servant", 2, 1, false,
            false, false, false));
        cardPool.add(new Card("Vulgar Homunculus", 2, 4, true,
            false, false, false));
        cardPool.add(new Card("Wrath Weaver", 1, 3, false,
            false, false, false));
        cardPool.add(new Card("Imprisoner", 3, 3, true,
            false, false, false));
        cardPool.add(new Card("Nathrezim Overseer", 2, 3, false,
            false, false, false));
        cardPool.add(new Card("Imp Gang Boss", 2, 4, false,
            false, false, false));
        cardPool.add(new Card("Soul Devourer", 3, 3, false,
            false, false, false));
        cardPool.add(new Card("Crystalweaver", 5, 4, false,
            false, false, false));
        cardPool.add(new Card("Soul Juggler", 3, 3, false,
            false, false, false));
        cardPool.add(new Card("Bigfernal", 4, 4, false,
            false, false, false));
        cardPool.add(new Card("Ring Matron", 6, 4, true,
            false, false, false));
        cardPool.add(new Card("Siegebreaker", 5, 8, true,
            false, false, false));
        cardPool.add(new Card("Annihilan Battlemaster", 3, 1, false,
            false, false, false));
        cardPool.add(new Card("Mal'Ganis", 9, 7, false,
            false, false, false));
        cardPool.add(new Card("Voidlord", 3, 9, true,
            false, false, false));
        cardPool.add(new Card("Imp Mama", 6, 10, false,
            false, false, false));
        
        cardPool.add(new Card("Dragonspawn Lieutenant", 2, 3, true,
            false, false, false));
        cardPool.add(new Card("Red Whelp", 1, 2, false,
            false, false, false));
        cardPool.add(new Card("Glyph Guardian", 2, 4, false,
            false, false, false));
        cardPool.add(new Card("Steward of Time", 3, 4, false,
            false, false, false));
        cardPool.add(new Card("Waxrider Togwaggle", 1, 3, false,
            false, false, false));
        cardPool.add(new Card("Bronze Warden", 2, 1, false,
            false, true, false));
        cardPool.add(new Card("Hangry Dragon", 4, 4, false,
            false, false, false));
        cardPool.add(new Card("Twilight Emissary", 4, 4, true,
            false, false, false));
        cardPool.add(new Card("Cobalt Scalebane", 5, 5, false,
            false, false, false));
        cardPool.add(new Card("Drakonid Enforcer", 3, 6, false,
            false, false, false));
        cardPool.add(new Card("Herald of Flame", 5, 6, false,
            false, false, false));
        cardPool.add(new Card("Murozond", 5, 5, false,
            false, false, false));
        cardPool.add(new Card("Razorgore, the Untamed", 2, 4, false,
            false, false, false));
        cardPool.add(new Card("Kalecgos, Arcane Aspect", 4, 12, false,
            false, false, false));
        cardPool.add(new Card("Nadina the Red", 7, 4, false,
            false, false, false));
        
        cardPool.add(new Card("Refreshing Anomaly", 1, 3, false,
            false, false, false));
        cardPool.add(new Card("Sellemental", 2, 2, false,
            false, false, false));
        cardPool.add(new Card("Molten Rock", 2, 4, true,
            false, false, false));
        cardPool.add(new Card("Party Elemental", 3, 2, false,
            false, false, false));
        cardPool.add(new Card("Arcane Assistant", 3, 3, false,
            false, false, false));
        cardPool.add(new Card("Crackling Cyclone", 4, 1, false,
            false, true, true));
        cardPool.add(new Card("Stasis Elemental", 4, 4, false,
            false, false, false));
        cardPool.add(new Card("Majordomo Executus", 6, 3, false,
            false, false, false));
        cardPool.add(new Card("Wildfire Elemental", 7, 3, false,
            false, false, false));
        cardPool.add(new Card("Nomi, Kitchen Nightmare", 4, 4, false,
            false, false, false));
        cardPool.add(new Card("Tavern Tempest", 4, 4, false,
            false, false, false));
        cardPool.add(new Card("Gentle Djinni", 4, 5, true,
            false, false, false));
        cardPool.add(new Card("Lieutenant Garr", 5, 1, true,
            false, false, false));
        cardPool.add(new Card("Lil' Rag", 4, 4, false,
            false, false, false));
        
        cardPool.add(new Card("Micro Machine", 1, 2, false,
            false, false, false));
        cardPool.add(new Card("Micro Mummy", 1, 2, false,
            false, false, false));
        cardPool.add(new Card("Harvest Golem", 2, 3, false,
            false, false, false));
        cardPool.add(new Card("Kaboom Bot", 2, 2, false,
            false, false, false));
        cardPool.add(new Card("Metaltooth Leaper", 3, 3, false,
            false, false, false));
        cardPool.add(new Card("Deflect-o-Bot", 3, 2, false,
            false, true, false));
        cardPool.add(new Card("Iron Sensei", 2, 2, false,
            false, false, false));
        cardPool.add(new Card("Piloted Shredder", 4, 3, false,
            false, false, false));
        cardPool.add(new Card("Screwjank Clunker", 2, 5, false,
            false, false, false));
        cardPool.add(new Card("Replicating Menace", 3, 1, false,
            false, false, false));
        cardPool.add(new Card("Annoy-o-Module", 2, 4, true,
            false, true, false));
        //cardPool.add(new Card("Mechano-Egg", 0, 5, false,
        //false, false, false));
        cardPool.add(new Card("Security Rover", 2, 6, false,
            false, false, false));
        cardPool.add(new Card("Junkbot", 1, 5, false,
            false, false, false));
        cardPool.add(new Card("Sneed's Old Shredder", 5, 7, false,
            false, false, false));
        cardPool.add(new Card("Foe Reaper 4000", 6, 9, false,
            false, false, false));
        cardPool.add(new Card("Kangor's Apprentice", 4, 8, false,
            false, false, false));
        
        cardPool.add(new Card("Murloc Tidecaller", 1, 2, false,
            false, false, false));
        cardPool.add(new Card("Murloc Tidehunter", 2, 1, false,
            false, false, false));
        cardPool.add(new Card("Rockpool Hunter", 2, 3, false,
            false, false, false));
        cardPool.add(new Card("Murloc Warleader", 2, 3, false,
            false, false, false));
        cardPool.add(new Card("Old Murk-Eye", 2, 4, false,
            false, false, false));
        cardPool.add(new Card("Coldlight Seer", 2, 3, false,
            false, false, false));
        cardPool.add(new Card("Felfin Navigator", 4, 4, false,
            false, false, false));
        cardPool.add(new Card("Primalfin Lookout", 3, 2, false,
            false, false, false));
        cardPool.add(new Card("Toxfin", 1, 2, false,
            false, false, false));
        cardPool.add(new Card("King Bagurgle", 6, 3, false,
            false, false, false));
        
        cardPool.add(new Card("Deck Swabbie", 2, 2, false,
            false, false, false));
        cardPool.add(new Card("Scallywag", 2, 1, false,
            false, false, false));
        cardPool.add(new Card("Freedealing Gambler", 3, 3, false,
            false, false, false));
        cardPool.add(new Card("Southsea Captain", 3, 3, false,
            false, false, false));
        cardPool.add(new Card("Yo-Ho-Ogre", 2, 6, true,
            false, false, false));
        cardPool.add(new Card("Bloodsail Cannoneer", 4, 3, false,
            false, false, false));
        cardPool.add(new Card("Salty Looter", 4, 4, false,
            false, false, false));
        cardPool.add(new Card("Southsea Strongarm", 4, 3, false,
            false,  false, false));
        cardPool.add(new Card("Goldgrubber", 2, 2, false,
            false, false, false));
        cardPool.add(new Card("Ripsnarl Captain", 4, 5, false,
            false, false, false));
        cardPool.add(new Card("Cap'n Hoggarr", 6, 6, false,
            false, false, false));
        cardPool.add(new Card("Nat Pagle, Extreme Angler", 8, 5, false,
            false, false, false));
        cardPool.add(new Card("Seabreaker Goliath", 6, 7, false,
            false, false, true));
        cardPool.add(new Card("Dread Admiral Eliza", 6, 7, false,
            false, false, false));
        cardPool.add(new Card("The Tide Razor", 6, 4, false,
            false, false, false));
        
        cardPool.add(new Card("Acolyte of C'Thun", 2, 2, true,
            false, false, false));
        cardPool.add(new Card("Menagerie Mug", 2, 2, false,
            false, false, false));
        cardPool.add(new Card("Selfless Hero", 2, 1, false,
            false, false, false));
        cardPool.add(new Card("Spawn of N'Zoth", 2, 2, false,
            false, false, false));
        cardPool.add(new Card("Tormented Ritualist", 2, 3, true,
            false, false, false));
        cardPool.add(new Card("Unstable Ghoul", 1, 3, true,
            false, false, false));
        cardPool.add(new Card("Arm of the Empire", 4, 5, false,
            false, false, false));
        cardPool.add(new Card("Khadgar", 2, 2, false,
            false, false, false));
        cardPool.add(new Card("Warden of Old", 3, 3, false,
            false, false, false));
        cardPool.add(new Card("Bolvar, Fireblood", 1, 7, false,
            false, true, false));
        cardPool.add(new Card("Champion of Y'Shaarj", 4, 4, false,
            false, false, false));
        cardPool.add(new Card("Defender of Argus", 2, 3, false,
            false, false, false));
        cardPool.add(new Card("Menagerie Jug", 3, 3, false,
            false, false, false));
        cardPool.add(new Card("Qiraji Harbinger", 5, 5, false,
            false, false, false));
        cardPool.add(new Card("Baron Rivendare", 1, 7, false,
            false, false, false));
        cardPool.add(new Card("Brann Bronzebeard", 2, 4, false,
            false, false, false));
        cardPool.add(new Card("Deadly Spore", 1, 1, false,
            true, false, false));
        cardPool.add(new Card("Faceless Taverngoer", 4, 4, false,
            false, false, false));
        cardPool.add(new Card("Lightfang Enforcer", 2, 2, false,
            false, false, false));
        cardPool.add(new Card("Mythrax the Unraveler", 4, 4, false,
            false, false, false));
        cardPool.add(new Card("Strongshell Scavenger", 2, 3, false,
            false, false, false));
        cardPool.add(new Card("Amalgadon", 6, 6, false,
            false, false, false));
        cardPool.add(new Card("Zapp Slywick", 7, 10, false,
            false, false, true));
        return cardPool;
    }
}

