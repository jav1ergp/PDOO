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
public class WeaponCardDeck extends CardDeck<Weapon> {

    protected void addCards(){
        for(int i = 0; i < 10; i++){
            Weapon card = new Weapon(Dice.randomStrength(), Dice.usesLeft());
            addCard(card);
        }
    }
}
