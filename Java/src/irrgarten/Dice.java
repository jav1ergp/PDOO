    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author javie
 */
public class Dice {
    //son con static
    private static final int MAX_USES = 5;
    private static final double MAX_INTELLIGENCE = 10.0;
    private static final double MAX_STRENGTH = 10.0;
    private static final double RESURRECT_PROB = 0.3;
    private static final int WEAPONS_REWARDS = 2;
    private static final int SHIELDS_REWARD = 3;
    private static final int HEALTH_REWARD =5;
    private static final int MAX_ATTACK = 3;
    private static final int MAX_SHIELD = 2;
    private static final Random generator = new Random();
    
    
    /**
     * Genera un número aleatorio entre 0 (inclusive) y max (exclusivo).
     * 
     * @param max El valor máximo (exclusivo) para el número aleatorio.
     * @return Un número aleatorio entre 0 (inclusive) y max (exclusivo).
     */
    public static int randomPos(int max) {
        return generator.nextInt(max);
    }

    /**
     * Genera un número aleatorio entre 0 (inclusive) y nplayers (exclusivo).
     * 
     * @param nplayers El número de jugadores.
     * @return Un número aleatorio entre 0 (inclusive) y nplayers (exclusivo).
     */
    public static int whoStars(int nplayers) {
        return generator.nextInt(nplayers);
    }

    /**
     * Genera un valor aleatorio de inteligencia entre 0 (inclusive) y MAX_INTELLIGENCE (exclusivo).
     * 
     * @return Un valor aleatorio de inteligencia.
     */
    public static float randomIntelligence() {
        return generator.nextFloat() * (float) MAX_INTELLIGENCE;
    }
    
    /**
     * Genera un valor aleatorio de fuerza entre 0 (inclusive) y MAX_STRENGTH (exclusivo).
     * 
     * @return Un valor aleatorio de fuerza.
     */
    public static float randomStrength() {
        return generator.nextFloat() * (float) MAX_STRENGTH;
    }
    
    /**
     * Determina si se resucita un jugador basado en una probabilidad RESURRECT_PROB.
     * 
     * @return True si se resucita al jugador, False en caso contrario.
     */
    public static boolean resurrectPlayer() {
        return generator.nextDouble() > RESURRECT_PROB;
    }
    
    /**
     * Genera un valor aleatorio de recompensa de armas entre 0 y WEAPONS_REWARDS.
     * 
     * @return Un valor aleatorio de recompensa de armas.
     */
    public static int weaponsReward() {
        return generator.nextInt(WEAPONS_REWARDS + 1);
    }
    
    /**
     * Genera un valor aleatorio de recompensa de escudos entre 0 y SHIELDS_REWARD.
     * 
     * @return Un valor aleatorio de recompensa de escudos.
     */
    public static int shieldsReward() {
        return generator.nextInt(SHIELDS_REWARD + 1);
    }
    
    /**
     * Genera un valor aleatorio de recompensa de salud entre 0 y HEALTH_REWARD.
     * 
     * @return Un valor aleatorio de recompensa de salud.
     */
    public static int healthReward() {
        return generator.nextInt(HEALTH_REWARD + 1);
    }
    
    /**
     * Genera un valor aleatorio de poder de arma entre 0 y MAX_ATTACK.
     * 
     * @return Un valor aleatorio de poder de arma.
     */
    public static float weaponPower() {
        return generator.nextFloat() * MAX_ATTACK;
    }
    
    /**
     * Genera un valor aleatorio de poder de escudo entre 0 y MAX_SHIELD.
     * 
     * @return Un valor aleatorio de poder de escudo.
     */
    public static float shieldPower() {
        return generator.nextFloat() * MAX_SHIELD;
    }
    
    /**
     * Genera un número aleatorio de usos restantes entre 0 y MAX_USES.
     * 
     * @return Un número aleatorio de usos restantes.
     */
    public static int usesLeft() {
        return generator.nextInt(MAX_USES + 1);
    }
    
    /**
     * Genera una intensidad aleatoria basada en la competencia dada.
     * 
     * @param competence La competencia que determina el rango de la intensidad.
     * @return Una intensidad aleatoria.
     */
    public static float intensity(float competence) {
        return generator.nextFloat() * competence;
    }
    
    /**
     * Decide el siguiente paso basado en una preferencia, una lista de movimientos válidos y la inteligencia.
     * 
     * @param preference La preferencia de dirección.
     * @param validMoves La lista de movimientos válidos.
     * @param intelligence La inteligencia que influye en la probabilidad del movimiento.
     * @return La siguiente dirección a tomar.
     */
    public static Directions nextStep(Directions preference, ArrayList<Directions> validMoves, float intelligence) {
        float prob = generator.nextFloat() * intelligence;
        Directions next;
        
        if (prob > 1 / intelligence) {
            next = preference;
        } else {
            int pos = generator.nextInt(validMoves.size());
            next = validMoves.get(pos);
        }
        return next;
    }
    
    /**
     * Determina si se descarta un elemento basado en los usos restantes y una tirada de dados.
     * 
     * @param usesLeft Los usos restantes del elemento.
     * @return True si el elemento debe ser descartado, false en caso contrario.
     */
    public static boolean discardElement(int usesLeft) {
        boolean discard = false;
        
        if (usesLeft == MAX_USES) {
            discard = false;
        } else if (usesLeft == 0) {
            discard = true;
        } else if (generator.nextInt(MAX_USES + 1) > usesLeft) {
            discard = true;
        }
        
        return discard;
    }
}