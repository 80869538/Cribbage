/** This class is used to do the calculation of total points.
 * 
 * @author Dongsheng Jiang
 * @login_id DONGSHENGJ
 */

public class Calcultor {
	
	// constant declaration
	private static final char RANK_FOR_NOB = 'J';
	private static final int POINTS_FOR_NOB = 1;
	private static final int POINTS_FOR_FLUSHES_CASE_1 = 4;
	private static final int POINTS_FOR_FLUSHES_CASE_2 = 5;
	private static final int POINTS_FOR_15S = 2;
	private static final int POINTS_FOR_PAIRS = 2;
	private static final int POINTS_FOR_PAIRS_CASE_1 = 5;
	private static final int POINTS_FOR_PAIRS_CASE_2 = 4;
	private static final int POINTS_FOR_PAIRS_CASE_3 = 3;
	
	
	/** calculate the points contributed by "One for his nob" rule.
	 * 
	 * @param handCard a Hand type pramater representing a set of cards in hand.
	 * @param startCard a start card of Card type.
	 * @return 1, if there exists a "One for his nob"; 0, otherwise.
	 */
	private static int pointsForNob(Hand handCard, Card startCard) {
		for (Card card : handCard.getHand()) {
			if (card.equals(new Card(RANK_FOR_NOB, startCard.getSuit())))
				return POINTS_FOR_NOB;
		}
		
		return 0;
	}
	
	
	/** calculate the points contributed by "Flushes" rule
	 * 
	 * @param handCard a Hand type pramater representing a set of cards in hand.
	 * @param startCard a start card of Card type.
	 * @return 4 if handCard are of the same suit, 5 if startCard also the same suit.
	 */
	private static int pointsForFlushes(Hand handCard, Card startCard) {
		Card[] cards = handCard.getHand();
		
		// test wether all cards in hand have the same suit
		for (int i = 1; i < cards.length; i++) {
			if (!cards[i].suitEquals(cards[0]))
				return 0;
		}
		// test wether start card also has the same suit
		return startCard.suitEquals(cards[0]) ? POINTS_FOR_FLUSHES_CASE_2 : POINTS_FOR_FLUSHES_CASE_1;
	}

	
	/** calculate the points contributed by "15s" rule
	 * 
	 * @param handCard a Hand type pramater representing a set of cards in hand.
	 * @return a int type representing the points contributed by "15s"
	 */
	private static int pointsFor15S(Hand hand) {
		// create a list to store all possible combination of cards
		List<Hand> list = Calcultor.combination(hand);
		
		// count the points for 15S from all the combintions 
		int points = 0;
		for (Hand combination : list) {
			if (combination.value() == 15) {
				points += POINTS_FOR_15S;
			}
		}
		return points;
	}

	
	/**Calculate the points contributed by "Pairs" rule
	 * 
	 * @param handCard a Hand type pramater representing a set of cards in hand.
	 * @return a int type representing the points contributed by "Pairs"
	 */
	private static int pointsForPairs(Hand hand) {
		// create a list to store all possible combination of cards
		List<Hand> list = Calcultor.combination(hand, 2);
		
		// count the points for Pairs from all the combintions
		int points = 0;
		for (Hand combination : list) {
			if (combination.getHand()[0].rankEquals(combination.getHand()[1])) {
				points += POINTS_FOR_PAIRS;
			}
		}
		return points;
	}

	
	/**Calculate the points contributed by "Runs" rule
	 * 
	 * @param handCard a Hand type pramater representing a set of cards in hand.
	 * @return a int type representing the points contributed by "Runs"
	 */
	private static int pointsForRuns(Hand hand) {
		// start with testing wether all cards in hand plus start card
		// is a Run. If so, this is the longest run and there is no need 
		// to test other cases
		if (hand.allInOrder()) return POINTS_FOR_PAIRS_CASE_1;
		
		// create a list to store all possible combination with 4 cards each.
		List<Hand> list = Calcultor.combination(hand, 4);
		
		// testing wether any four of all the cards can form a Run
		int points = 0;
		for (Hand combination : list) {
			if (combination.allInOrder()) {
				points += POINTS_FOR_PAIRS_CASE_2;
			}
		}
		if (points != 0) return points;
		
		// testing wether any three of all the cards can form a Run
		list = Calcultor.combination(hand, 3);
		for (Hand combination : list) {
			if (combination.allInOrder()) {
				points += POINTS_FOR_PAIRS_CASE_3;
			}
		}
		
		return points;
	}

	
	/**This method computes all the possible combinations of cards from the
	 * input array. 
	 * 
	 * @param hand an array of the elements to compute the combinations of.
	 * @return a set of the subarrays of the input.
	 */
	public static List<Hand> combination(Hand hand){
		List<Hand> list = new List<Hand>(); //create a list data structure to store combinations.
		match(list, hand.clone());
		return list;
	}
	
	
	/**This method computes all the possible combinations of a certain number of cards from the 
	 * input array. 
	 * 
	 * @param hand an array of the elements to compute the combinations of.
	 * @param num the number of card in each combinations.
	 * @return a set of the subarrays of the input.
	 */
	public static List<Hand> combination(Hand hand, int num){
		//create a List data structure to store combinations.
		List<Hand> tempList = new List<Hand>(); 
		
		match(tempList, hand.clone());
		
		//create a List data structure to store the final results.
		List<Hand> list = new List<Hand>();
		
		//find all the combinations with 4 elements in it.
		for (Hand item : tempList) {
			if (item.getNumCard() == num)
				list.add(item);
		}
		return list;
	}
	
	
	/** This method computes all the possible combinations from an array of cards.
	 * 
	 * @param list a list data structure used to store all generated possible combinations.
	 * @param hand a hand of cards that need to get the combinations of.
	 */
	private static void match(List<Hand> list, Hand hand) {
		
		//if the hand is empty, add this empty hand to list and return.
		if (hand.isEmpty()) {
			list.add(new Hand());
			return;
		}
		
		//remove one card from hand and record the card.
		Card discardedCard = hand.discardOneCard();
		
		// recursively invoke itself until the hand is empty
		match(list, hand);
		
		//add the removed card to all the hands in list to for new combinations and 
		//add these newly generated combinations back to list.
		for (Hand item : list) {
			list.add(item.addCard(discardedCard));
		}	
	}	

	
	/** This method is used to sum up all the points a hand of cards can get */
	public static int totalPoints(Hand hand, Card startCard) {
		// when counting points for 15S, Pairs and Runs start card is deemed as one of card in hand.
		Hand temp = hand.addCard(startCard);
		
		return pointsForNob(hand, startCard) +
			   pointsForFlushes(hand, startCard) +
			   pointsFor15S(temp) +
			   pointsForPairs(temp) +
			   pointsForRuns(temp);
	}
}
