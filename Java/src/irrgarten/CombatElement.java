/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author javie
 */
public abstract class CombatElement {
    private float effect;
    private int uses;

    /**
     * Constructor que inicializa el efecto y el número de usos del elemento de combate.
     * 
     * @param effect El efecto del elemento de combate.
     * @param uses El número de usos del elemento de combate.
     */
    public CombatElement(float effect, int uses) {
        this.effect = effect;
        this.uses = uses;
    }
    
    /**
     * Produce el efecto del elemento de combate si tiene usos restantes.
     * Si no tiene usos restantes, devuelve un efecto de 0.
     * 
     * @return El efecto producido por el elemento de combate.
     */
    protected float produceEffect() {
        float effect;
        
        if (this.uses > 0) {
            effect = this.effect;
            uses -= 1;
        } else {
            effect = 0;
        }
        return effect;
    }
    
    /**
     * Intenta descartar el elemento de combate utilizando una tirada de dados.
     * 
     * @return {true} si el elemento fue descartado, {false} en caso contrario.
     */
    public boolean Discard() {
        return Dice.discardElement(uses);
    }
    
    /**
     * Devuelve una representación en cadena del elemento de combate.
     * 
     * @return La representación en cadena del elemento de combate.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
