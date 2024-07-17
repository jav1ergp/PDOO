/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author javie
 */
public abstract class LabyrinthCharacter {
    String name;
    float intelligence;
    float strength;
    float health;
    int row;
    int col;
    private boolean aux;

    public LabyrinthCharacter(String name, float intelligence, float strength, float health) {
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = health;
    }

    public LabyrinthCharacter(LabyrinthCharacter other){
        this.name = other.name;
        this.intelligence = other.intelligence;
        this.strength = other.strength;
        this.health = other.health;
    }
    
    public boolean dead(){
        if(health <= 0){
            aux = true;
        } else {
            aux = false;
        }
        return aux;
    }
    
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    protected float getHealth() {
        return health;
    }

    protected void setHealth(float health) {
        this.health = health;
    }

    protected float getIntelligence() {
        return intelligence;
    }

    protected float getStrength() {
        return strength;
    }
    
    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    public String toString(){
        return "M name: " + this.name + ", Intelligence: " + this.intelligence +
                ", Strength: " + this.strength + ", Health: " + health + ", Row: " + row + ", Col: " + col;
    }
    
    protected void gotWounded(){
        health--;
    }
    
    abstract public float attack();
    abstract public boolean defend(float attack);
    
}
