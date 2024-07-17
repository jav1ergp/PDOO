/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.Random;

/**
 *
 * @author javie
 */
public class Weapon extends CombatElement{
    
    private float power;
    private int uses;
    private float aux = 0;

    /**
     * Constructor de la clase Weapon que inicializa el poder de ataque y los usos disponibles del arma.
     * 
     * @param power Poder de ataque del arma.
     * @param uses Número de usos disponibles del arma.
     */
    public Weapon(float power, int uses){
        super(power, uses);
    }
    
    /**
     * Método que realiza un ataque con el arma, reduciendo un uso y devolviendo el poder de ataque.
     * Si el arma no tiene usos disponibles, devuelve 0.
     * 
     * @return Poder de ataque del arma usado.
     */
    public float attack(){
        if(this.uses > 0){
            this.uses--;
            aux = power;
        } else {
            aux = 0;
        }
        return aux;
    }
    
    /**
     * Retorna una representación en cadena del arma, mostrando su poder de ataque y el número de usos restantes.
     * 
     * @return Cadena que representa el arma.
     */
    public String toString(){
        return "W[" + this.power + "," + this.uses + "]";
    }
    
    /**
     * Método que verifica si el arma debe ser descartada según un dado aleatorio.
     * 
     * @return True si el arma debe ser descartada, False si no.
     */
    public boolean discard(){
        return Dice.discardElement(uses);
    }
}