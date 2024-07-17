/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author javie
 */
public class Monster extends LabyrinthCharacter{
    private static final int INITIAL_HEALTH = 5;
    private boolean aux;

    
    /**
     * Constructor de la clase Monster que inicializa las características básicas del monstruo.
     * 
     * @param name Nombre del monstruo.
     * @param intelligence Inteligencia del monstruo.
     * @param strength Fuerza del monstruo.
     */
    public Monster(String name, float intelligence, float strength ) {
        super(name, intelligence, strength, INITIAL_HEALTH);
        health = INITIAL_HEALTH;
        row = 0;
        col = 0;
    }
    
    /**
     * Determina si el monstruo está muerto (sin salud restante).
     * 
     * @return {@code true} si el monstruo está muerto, {@code false} si no.
     */
    public boolean dead(){
        if(health <= 0){
            aux = true;
        } else {
            aux = false;
        }
        return aux;
    }
    
    /**
     * Realiza un ataque basado en la fuerza del monstruo.
     * 
     * @return Valor del ataque realizado por el monstruo.
     */
    public float attack(){
        return Dice.intensity(strength);
    }
    
    /**
     * Establece la posición del monstruo en el laberinto.
     * 
     * @param row Fila donde se colocará el monstruo.
     * @param col Columna donde se colocará el monstruo.
     */
    public void setPos(int row,int col){
        this.row = row;
        this.col = col;
    }
    
    /**
     * Retorna una representación en cadena del monstruo, incluyendo su nombre, inteligencia, fuerza, salud y posición.
     * 
     * @return Cadena que representa al monstruo.
     */
    public String toString(){
        return "M name: " + this.name + ", Intelligence: " + this.intelligence +
                ", Strength: " + this.strength + ", Health: " + health + ", Row: " + row + ", Col: " + col;
    }
    
    /**
     * Método protegido que disminuye la salud del monstruo cuando recibe un ataque.
     */
    protected void gotWounded(){
        health--;       
    }
    
    
    /**
     * Método que permite al monstruo intentar defenderse de un ataque recibido.
     * 
     * @param receivedAttack Valor del ataque recibido por parte de un jugador.
     * @return True si el monstruo muere después de recibir el ataque, False si sobrevive.
     */
    public boolean defend(float receivedAttack){
        boolean isDead = dead();
        if(!isDead){
            float defensiveEnergy = Dice.intensity(intelligence);
            if(defensiveEnergy < receivedAttack){
                gotWounded();
                isDead = dead();
            }
        }
        return isDead;
    }
    
}
