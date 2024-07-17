/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author javie
 */
public class Shield extends CombatElement{
    private float protection;
    private int uses;
    private float aux = 0;
    
    /**
     * Constructor de la clase Shield que inicializa el poder de protección y los usos disponibles del escudo.
     * 
     * @param power Poder de protección del escudo.
     * @param uses Número de usos disponibles del escudo.
     */
    public Shield(float power, int uses){
        super(power, uses);
    }
    
    /**
     * Método que proporciona protección al jugador al usar el escudo, reduciendo un uso y devolviendo el poder de protección.
     * Si el escudo no tiene usos disponibles, devuelve 0.
     * 
     * @return Poder de protección del escudo usado.
     */
    public float protect(){
        if(uses > 0){
            this.uses--;
            aux = this.protection;
        }   else {
            aux = 0;
        }
        return aux;
    }
    
    /**
     * Retorna una representación en cadena del escudo, mostrando su poder de protección y el número de usos restantes.
     * 
     * @return Cadena que representa el escudo.
     */
    public String toString(){
        return "S[" + this.protection + "," + this.uses + "]";
    }
    
    /**
     * Método que verifica si el escudo debe ser descartado según un dado aleatorio.
     * 
     * @return True si el escudo debe ser descartado, False  si no.
     */
    public boolean discard(){
        return Dice.discardElement(uses);
    }
}