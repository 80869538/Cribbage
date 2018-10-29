/** This class is used to build a card object and provide necessary 
 *  function related to card in this program.
 * 
 *  It contains all the attributes of a card required in this program 
 *  and also checks whteher input rank and suit are valid for a card.
 *  It also provide a static method to generate all the 52 cards can 
 *  be used in a game.  
 * 
 * @author Dongsheng Jiang
 * @login_id DONGSHENGJ
 */

public class Card {
	// a single character representing a possible rank.
	private char rank;	
	// a single character representing a possible suit.
	private char suit;
	
	
	// enumerate all the possible ranks and provide some function
	// needed by outer class.
	private static enum Rank{
		ACE('A'),
	    TWO('2'),
	    THREE('3'),
	    FOUR('4'),
	    FIVE('5'),
	    SIX('6'),
	    SEVEN('7'),
	    EIGHT('8'),
	    NINE('9'),
	    TEN('T'),
	    JACK('J'),
	    QUEEN('Q'),
	    KING('K');
			
		// Single character used to represent a rank. 
		private final char rank;
			
		/** Construct a rank.
	     *  @param rank a single-character representing the rank.
	     */
		Rank(char rank){
			this.rank = rank;
		}
			
		/** @return the rank */
		private char rank() {
			return rank;
		}
			
		/** decide whether a certain character can be represented by Rank or not.
		 * 
		 *  @param name a single character that need to be desided whether it's valid.
		 *  @return true if name is a valid rank, false if not.
		 */
		public static boolean contains(char name) {
			char[] ranks = allRank();
			for(char r : ranks) {
				if(r == name) {
					return true;
				}
			}
			return false;
		}
			
		/** @return all the possible ranks */
		public static char[] allRank() {
			char[] allRank = new char[13];
			Rank[] ranks = values();
			for (int i = 0; i < 13; i++) {
				allRank[i] = ranks[i].rank();
			}
			return allRank;
		}
	}

	
	// enumerate all the possible suits and provide some function
	// needed by outer class.
	private static enum Suit{
		C, D, H, S;
			
		// decide whether a certain character can be represented by Suit or not.
		public static boolean contains(char name) {
			char[] symbols = allSuit();
			for(char s : symbols) {
				if(s == name) {
					return true;
				}
			}
			return false;
		}
			
		/** @return all the possible suits */
		public static char[] allSuit() {
			char[] allSuit = new char[4];
			Suit[] symbols = values();
			for (int i = 0; i < 4; i++) {
				allSuit[i] = symbols[i].name().charAt(0);
			}
			return allSuit;
		}
	}

	
	/** Construct a card. */
	public Card(char rank, char symbol) {
		// check if input rank and suit is valid.
		if(Rank.contains(rank) && Suit.contains(symbol)) {
			this.rank = rank;
			this.suit = symbol;
		}
		else {
			throw new IllegalArgumentException(""+ rank + symbol + " is an invalid input.\n"
											+ "Please check and try again\n");
		}
	}
	
	
	/** find the value of a specfic rank used to count 15s in a game.
	  * 
	  * @return the numeric value for a rank.
	  */
	public int value() {
		if (rank == 'A') return 1;
		else if (rank == 'T' || rank == 'J' || rank == 'Q' || rank == 'K') 
			return 10;
		else 
			return rank - '0'; 
	}
	
	
	/**	compare to see wether two cards have the same rank.
	  * 
	  * @param otherCard an Card type object that need to be compared.
	  * @return true, if the two card have the same rank; false, oterwise. 
	  */
	public boolean rankEquals(Card otherCard) {
		if(otherCard == null)
			return false;
		else {
			return (rank == otherCard.rank);
		}
	}
	
	
	/** compare to see wether two cards have the same suit.
	  * 
	  * @param otherCard an Card type object that need to be compared.
	  * @return true, if the two card have the same symbol; false, oterwise. 
	  */
	public boolean suitEquals(Card otherCard){
		if(otherCard == null)
			return false;
		else {
			return (suit == otherCard.suit);
		}
	}

	
	/** @return the rank */
	public char getRank() {
		return rank;
	}
	
	
	/** @return the suit */
	public char getSuit() {
		return suit;
	}
	
	
	/** @return the rank order used for counting points of the rule of Runs*/
	public int order() {
		if (rank == 'A') return 1;
		else if (rank == 'T') return 10;
		else if (rank == 'J') return 11;
		else if (rank == 'Q') return 12;
		else if (rank == 'K') return 13;
		else return rank - '0'; 
	}
	
	
	/** @return all the 52 cards can be used in a game*/
	public static Card[] fullDeck() {
		int numCard = Rank.allRank().length * Suit.allSuit().length;
		Card[] deck = new Card[numCard];
		int h = 0;
		for (char i : Rank.allRank()) {
			for (char j : Suit.allSuit()) {
				deck[h] = new Card(i, j);
				h ++;
			}
		}
		return deck;
	}
	
	
	@Override
	public String toString() {
		return "" + rank + suit;
	}
	
	
	@Override
	public Card clone() {
		return new Card(rank, suit);
	}
	
	
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		else if (getClass() != other.getClass()) {
			return false;
		}
		else {
			Card otherCard = (Card) other;
			return (rankEquals(otherCard) && suitEquals(otherCard));
		}
	}

}
