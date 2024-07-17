/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author javie
 */
public class ShieldCardDeck extends CardDeck<Shield>{
    protected void addCards() {
        for(int i = 0; i < 10; i++){
            Shield card = new Shield(Dice.randomIntelligence(), Dice.usesLeft());
            addCard(card);
        }
    }
}
