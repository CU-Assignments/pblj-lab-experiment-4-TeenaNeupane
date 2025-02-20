Experiment 4.2: Card Collection System

Objective:
Develop a Java program that collects and stores playing cards to help users find all the cards of a given symbol (suit).
The program should utilize the Collection interface (such as ArrayList, HashSet, or HashMap) to manage the card data efficiently.

Understanding the Problem Statement

1. Card Structure:
Each card consists of a symbol (suit) and a value (rank).

Example card representations:
Ace of Spades
King of Hearts
10 of Diamonds
5 of Clubs

2. Operations Required:
Add Cards → Store card details in a collection.
Find Cards by Symbol (Suit) → Retrieve all cards belonging to a specific suit (e.g., all "Hearts").
Display All Cards → Show all stored cards.

3. Collections Usage:
ArrayList: To store cards in an ordered manner.
HashSet: To prevent duplicate cards.
HashMap<String, List<Card>>: To organize cards based on suits for faster lookup.

Implementation:
  import java.util.*;

class Card {
    private String suit;
    private String rank;

    public Card(String rank, String suit) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    //@Override
    public String toString() {
        return rank + " of " + suit;
    }

    //@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return suit.equals(card.suit) && rank.equals(card.rank);
    }

    //@Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }
}

public class CardCollectionSystem {
    private Map<String, Set<Card>> cardCollection;

    public CardCollectionSystem() {
        cardCollection = new HashMap<>();
    }

    public void addCard(String rank, String suit) {
        Card card = new Card(rank, suit);
        cardCollection.putIfAbsent(suit, new HashSet<>());
        
        if (cardCollection.get(suit).contains(card)) {
            System.out.println("Error: Card \"" + card + "\" already exists.");
        } else {
            cardCollection.get(suit).add(card);
            System.out.println("Card added: " + card);
        }
    }

    public void findCardsBySuit(String suit) {
        if (cardCollection.containsKey(suit) && !cardCollection.get(suit).isEmpty()) {
            cardCollection.get(suit).forEach(card -> System.out.println(card));
        } else {
            System.out.println("No cards found for " + suit + ".");
        }
    }

    public void displayAllCards() {
        if (cardCollection.isEmpty() || cardCollection.values().stream().allMatch(Set::isEmpty)) {
            System.out.println("No cards found.");
            return;
        }
        
        cardCollection.values().forEach(set -> set.forEach(System.out::println));
    }

    public void removeCard(String rank, String suit) {
        Card card = new Card(rank, suit);
        if (cardCollection.containsKey(suit) && cardCollection.get(suit).remove(card)) {
            System.out.println("Card removed: " + card);
            if (cardCollection.get(suit).isEmpty()) {
                cardCollection.remove(suit);
            }
        } else {
            System.out.println("Error: Card \"" + card + "\" not found.");
        }
    }

    public static void main(String[] args) {
        CardCollectionSystem system = new CardCollectionSystem();
        
        // Test Case 1: Display when empty
        system.displayAllCards();

        // Test Case 2: Adding Cards
        system.addCard("Ace", "Spades");
        system.addCard("King", "Hearts");
        system.addCard("10", "Diamonds");
        system.addCard("5", "Clubs");

        // Test Case 3: Find all Hearts
        system.findCardsBySuit("Hearts");
        
        // Test Case 4: Find all Diamonds (if none exists)
        system.findCardsBySuit("Diamonds");

        // Test Case 5: Display all cards
        system.displayAllCards();

        // Test Case 6: Prevent duplicates
        system.addCard("King", "Hearts");

        // Test Case 7: Remove a card
        system.removeCard("10", "Diamonds");
    }
}
