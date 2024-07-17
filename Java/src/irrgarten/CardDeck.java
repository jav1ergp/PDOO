/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author javie
 */
public abstract class CardDeck<T>{
    ArrayList<T> cardDeck;

    public CardDeck() {
        this.cardDeck = new ArrayList();
    }
    
    protected void addCard(T card) {
        cardDeck.add(card);
    }
    
    protected abstract void addCards();
    
    public T nextCard() {
        if (cardDeck.isEmpty()) {
            addCards();
        }

        T next = cardDeck.get(0);
        cardDeck.remove(0);
        return next;
    }

}
