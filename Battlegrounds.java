import java.util.ArrayList;

/**
 * driver class that creates and plays hearthstone battlegrounds (current 127 cards)
 *
 * @author del huang
 * @version 25/03/2021
 */
public class Battlegrounds
{
    static final int MIN = 0;
    static final int MAX = 125; //mechano egg banned
    static final boolean RUNNING = true;
    public static void main (String[] args) {
        ArrayList<Card> cardPool = createCards();
        ArrayList<Card> plDeck = new ArrayList<Card>();
        ArrayList<Card> enemDeck = new ArrayList<Card>();
        int plIdx = 0;
        int enemIdx = 0;
        for (int i = 0; i < 7; i++) {
            plDeck.add(cardPool.get((int) (Math.random() * MAX + MIN)));
            enemDeck.add(cardPool.get((int) (Math.random() * MAX + MIN)));
        }
        System.out.println("Player Deck:");
        for (int i = 0; i < 7; i++) {
            plDeck.get(i).printStats();
        }
        System.out.println("\nOpponent Deck:");
        for (int i = 0; i < 7; i++) {
            enemDeck.get(i).printStats();
        }
        System.out.println("\n\n");
        
        if ((plDeck.size()) == (enemDeck.size())) {
            int coinflip = (int) (Math.random() * + 1);
            if (coinflip == 0) {
                for (int i = 0; i < 7; i++){
                    System.out.println("\nTurn " + (i+1));
                    while(RUNNING) {
                        plIdx = (int) (Math.random() * + 7);
                        if (plDeck.get(plIdx).checkAlive()){
                            break;
                        }
                    }
                    while(RUNNING) {
                        enemIdx = (int) (Math.random() * + 7);
                        if (enemDeck.get(enemIdx).checkAlive()){
                            break;
                        }
                    }
                    if (plDeck.get(i).checkAlive()) {
                        System.out.println(plDeck.get(i).getName() + " attacks " + enemDeck.get(enemIdx).getName() + "!");
                        enemDeck.get(enemIdx).hit(plDeck.get(i).getAtk());
                    }
                    enemDeck.get(enemIdx).checkDead();
                    if (!enemDeck.get(enemIdx).checkAlive()) {
                        System.out.println(enemDeck.get(enemIdx).getName() + " dies!");
                    }
                    if(enemDeck.get(i).checkAlive()) {
                        if (plDeck.get(i).checkAlive()) {
                            System.out.println(enemDeck.get(i).getName() + " attacks " + plDeck.get(plIdx).getName() + "!");
                            plDeck.get(plIdx).hit(enemDeck.get(i).getAtk());
                        }
                        plDeck.get(plIdx).checkDead();
                        if (!plDeck.get(plIdx).checkAlive()) {
                            System.out.println(plDeck.get(plIdx).getName() + " dies!");
                        }
                    }
                }
            }
            else if (coinflip == 1) {
                for (int i = 0; i < 7; i++){
                    plDeck.get(i).hit(enemDeck.get(i).getAtk());
                    enemDeck.get(i).hit(plDeck.get(i).getAtk());
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
        ArrayList<Card> cardPool = new ArrayList<>();
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
