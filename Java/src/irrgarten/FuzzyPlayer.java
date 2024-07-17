/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;

/**
 *
 * @author javie
 */
public class FuzzyPlayer extends Player{
    
    public FuzzyPlayer(Player other){
        super(other);
    }
    
    
    /**
     * Sobrescribe el método de movimiento para realizar movimientos basados en preferencias y decisiones difusas.
     * 
     * @param direction La dirección preferida para moverse.
     * @param validMoves Lista de movimientos válidos.
     * @return La siguiente dirección a tomar por el jugador Fuzzy.
     */
    @Override
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        Directions preferred = super.move(direction, validMoves);
        return Dice.nextStep(preferred, validMoves, getIntelligence());
    }
    
    /**
     * Sobrescribe el método de ataque para calcular el daño de ataque del jugador Fuzzy.
     * Incluye el daño de armas y el daño de fuerza.
     * 
     * @return El daño total de ataque del jugador Fuzzy.
     */
    @Override
    public float attack(){
        float WeaponsDmg = sumWeapons();
        float StrengthDmg = Dice.intensity(getStrength());
        return WeaponsDmg + StrengthDmg;
    }
    
    /**
     * Sobrescribe el método de energía defensiva para calcular la energía defensiva del jugador Fuzzy.
     * Incluye el daño de escudos y el daño de inteligencia.
     * 
     * @return La energía defensiva total del jugador Fuzzy.
     */
    @Override
    protected float defensiveEnergy(){
        float ShieldsDmg = sumShields();
        float IntelligenceDmg = Dice.intensity(getIntelligence());
        return ShieldsDmg + IntelligenceDmg;
    }
    
    /**
     * Devuelve una representación en cadena del jugador Fuzzy.
     * 
     * @return Una cadena que representa al jugador Fuzzy.
     */
    @Override
    public String toString(){
       return "Fuzzy: " + super.toString();
    }
}
