/** Program entry, getting arguments from command line and ouputting the results.
 * 
 * This program is used in the game of Cribbage to choose 4 cards from 4-6 cards 
 * to keep in hand so that the chosen cards can maxmise the chances of having a good hand.
 * The choice is been made based on the criterion of choosing the greatest expected score.
 *  
 * Greatest expected score n is the average hand value of the four cards to
be kept taken with each of the possible start cards 
 *      
 * In particular, given a set of string arguments representing all the cards in hand
 * this program output the 4 with greatest expected score in the form of string. 
 * The output string and input string both consist of two characters representing
 * number and suit respectively.  
 * 
 * @author Dongsheng Jiang
 * @login_id DONGSHENGJ
 */

public class SelectHand {
	public static void main(String[] args) {
		
		try {
			
			if (args.length < 4 || args.length > 6)
				throw new IllegalArgumentException("Please input 4-6 cards\n");
			
			// create a hand of cards containing all the input cards.
			Hand startHand = new Hand(args);
			// get all the cards not in hand.
			Card[] restCard = startHand.restCard();
	
			/* create a List data structure to store all the possible 4-cards combination representing
			 * the cards to be kept in hand.
			 */
			List<Hand> list = Calcultor.combination(startHand, 4); 
			
			// initialize object and variable that will be used in the loop.  
			double max = -1;
			Hand maxHand = null;
			
			// loop though all possible combinations of cards that can be kept in hand
			// with each possible start card, find the one with maximum expected score. 
			for (Hand hand : list) {
				// initialize
				int sum = 0;
				
				for (Card startCard : restCard)
					sum += Calcultor.totalPoints(hand, startCard);
				
				double average = (double)sum/restCard.length; // calculate the expected score
				
				if ( average > max) {
					max = average;
					maxHand = hand;
				}
				
			}
			
			System.out.println(maxHand);
			
		} catch (IllegalArgumentException e) {
			System.out.print(e.getMessage());
			System.exit(1);
		}
		
	}
}
