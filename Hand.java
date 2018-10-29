/** This class is used to build a hand object which represents a set of cards
 *  that can be held on hand.
 * 
 *  It contains all the attributes of a hand of cards required in this program. 
 *  the maximum cards in hand should be 6.
 *  It also provides a static method to generate all cards from deck except those
 *  already in hand. 
 * 
 * @author Dongsheng Jiang
 * @login_id DONGSHENGJ
 */

public class Hand {
	
	// maximum number of cards that can be held in hand
	private static final int MAX_CARD = 6;
	
	
	// record all the cards in hand
	private final Card[] handCard = new Card[MAX_CARD];
	// record the number of cards in hand
	private int length = 0;
	
	
	/**Construct a Hand object.
	 * 
	 * @param args A String array with each element representing a card input by user. 
	 */
	public Hand(String[] args) {
		
		length = args.length;
		
		// make sure input arguments are valid
		verify(args);
		
		int i = 0;
		for (String s : args) {
			handCard[i] = new Card(s.charAt(0), s.charAt(1));
			i++;
		}
	}
	
	
	/** defult constructor */	
	public Hand() {};
	
	
	/** @return an array of Card objects representing all the cards in hand*/
	public Card[] getHand() {
		
		Card[] newHandCard = new Card[length];
		
		for (int i = 0; i < length; i++) {
			newHandCard[i] = handCard[i].clone();
		}
		
		return newHandCard;
	}
	
	
	/**Check to see if input array satisfy the condition that an input 
	 * should not contain any two identical cards and also check 
	 * that every string elements should contain exactly two characters.
	 */
	private void verify(String[] args) {
		
		for (int i = 0; i < length; i++) {
			if (args[i].length() != 2) {
				throw new IllegalArgumentException("Invalid input, make sure every arguments "
												+ "contain two characters\n");
			}
			for (int j = i+1; j < length; j++) {
				if (args[i].equals(args[j])) {
					throw new IllegalArgumentException("Input arguments contain same cards.\n"
														+ "Please check and try again.\n");
				}
			}
		}
	}

	
	/** add new card to a hand, and return it as a new hand.
	 *  this method is used by combination function to.generate
	 *  new combinations based on previous combinations.
	 * 
	 * @param newCard a Card type pramater representing a card that need to be added to the hand.
	 * @return a new Hand type object with newCard been added.
	 */
	public Hand addCard(Card newCard) {
		Hand newHand = this.clone();
		newHand.insertCard(newCard);
		return newHand;
	}
	
	
	/** insert a card to currrent hand*/ 
	private void insertCard(Card newCard) {
		handCard[length] = newCard.clone();
		length += 1;
	}
	
	
	/** discard one card from hand. this method is used for combination function
	 *  to help it invoke itself recursively and reduce problem size
	 */
	public Card discardOneCard() {
		Card oldCard = handCard[length-1];
		length -= 1;
		return oldCard;
	}
	
	
	/** @return hand is empty*/
	public boolean isEmpty() {
		return length == 0;
	}
	
	
	/** @return the number of cards in hand */
	public int getNumCard() {
		return length;
	}
	
	
	/** calculate total value of the cards in hand. This method is used for
	 *  counting the points for 15S;
	 * 
	 * @return a int value representing the total value.
	 */
	public int value() {
		int sum = 0;
		for (int i = 0; i < length; i++) {
			sum += handCard[i].value();
		}
		return sum;
	}
	
	
	/** sort the cards in hand according to their order of rank.
	 *  this method is used for pre-processing purpose in counting
	 *  points for Runs.
	 */
	private void sort() {
		
		int N = length;
		
		for (int i = 0; i < N; i++) {
			int min =i;
			for (int j = i+1; j<N; j++)
				if(handCard[j].order() < handCard[min].order()) {
					min = j;
				}
			Card temp = handCard[i];
			handCard[i] = handCard[min];
			handCard[min] = temp;	
		}
	}
	
	
	/** test whether all cards in hand can form a Runs.*/
	public boolean allInOrder() {
		
		sort();
		
		for (int i = 0; i < length - 1; i++) {
			if (handCard[i].order() + 1 != handCard[i + 1].order())
				return false;
		}
		
		return true;
	}
	
	
	/** get a set of possible start cards that can be choose from the deck after
	 *  a hand of cards have already been dealed.
	 *  
	 *  @return a set of cards representing all the cards left.
	 */
	public Card[] restCard() {
		
		// initilize
		Card[] restCard = new Card[52 - this.length];
		int i = 0;
		
		// check to see if the start card from deck has already been dealed to player	
		for (Card cardFromDeck : Card.fullDeck()) {
			
			boolean isValid = true;
			
			for (Card cardFromHand : this.getHand()) {
				if (cardFromDeck.equals(cardFromHand))
					isValid = false;
			}
			
			// if the start card is valid add it to the pile of rest cards
			if (isValid) {
				restCard[i] = cardFromDeck.clone();
				i++;
			}
		}
		return restCard;
	}
	
	
	@Override
	public String toString() {
		String cards = "";
		for (int i = 0; i < length - 1; i++) {
			cards += handCard[i] + " ";
		}
		cards += handCard[length-1];
		return cards;
	}
	
	
	@Override
	public Hand clone() {
		Hand newHand = new Hand();
		for (int i = 0; i < length; i++) {
			newHand.insertCard(handCard[i]);
		}
		return newHand;
	}
	
}
