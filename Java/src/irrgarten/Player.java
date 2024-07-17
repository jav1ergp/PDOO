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
public class Player extends LabyrinthCharacter{
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;
    private char number = 0;
    private int consecutiveHits = 0;
    private boolean aux;
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;
    private ArrayList<CardDeck> weaponCardDeck;
    private ArrayList<CardDeck> shieldCardDeck;

    /**
     * Constructor de la clase Player que inicializa las características básicas del jugador.
     * 
     * @param number Número de identificación del jugador.
     * @param intelligence Inteligencia del jugador.
     * @param strength Fuerza del jugador.
     */
    public Player(char number, float intelligence, float strength) {
        super("#Player " + number, intelligence, strength, INITIAL_HEALTH);
        this.number = number;
        health = INITIAL_HEALTH;
        consecutiveHits = 0;
        weapons = new ArrayList<>();
        shields = new ArrayList<>();
    }
    
    /**
     * Constructor de copia que permite crear un nuevo jugador a partir de otro existente.
     * 
     * @param other Otro jugador del cual se copiarán las características.
     */
    public Player(Player other){
        super(other);
        number = other.number;
        
        if(!other.weapons.isEmpty()){
            weapons.addAll(other.weapons);
        }
        
        if(!other.shields.isEmpty()){
            shields.addAll(other.shields);
        }
        consecutiveHits = other.consecutiveHits;
        weaponCardDeck = other.weaponCardDeck;
        shieldCardDeck = other.shieldCardDeck;
    }
    
    /**
     * Método que reinicia el jugador después de ser resucitado, eliminando armas y escudos,
     * restableciendo la salud y reiniciando los contadores.
     */
    public void resurrect(){
        weapons.clear();
        shields.clear();
        health = INITIAL_HEALTH;
        consecutiveHits = 0;
    }

    /**
     * Obtiene la fila actual del jugador en el laberinto.
     * 
     * @return Fila actual del jugador.
     */
    public int getRow() {
        return row;
    }

    /**
     * Obtiene la columna actual del jugador en el laberinto.
     * 
     * @return Columna actual del jugador.
     */
    public int getCol() {
        return col;
    }

    /**
     * Obtiene el número de identificación del jugador.
     * 
     * @return Número de identificación del jugador.
     */
    public char getNumber() {
        return number;
    }
       
    /**
     * Establece la posición del jugador en el laberinto.
     * 
     * @param row Fila donde se colocará al jugador.
     * @param col Columna donde se colocará al jugador.
     */
    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    /**
     * Determina si el jugador está muerto (sin salud restante).
     * 
     * @return {@code true} si el jugador está muerto, {@code false} si no.
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
     * Método que determina la dirección de movimiento preferida del jugador en base a las
     * direcciones válidas disponibles.
     * 
     * @param direction Dirección preferida de movimiento.
     * @param validMoves Lista de direcciones válidas disponibles para el movimiento.
     * @return Dirección de movimiento seleccionada.
     */
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        int size = validMoves.size();
        boolean contained = validMoves.contains(direction);
        
        if(size > 0 && !contained){
            Directions firstElement = validMoves.get(0);
            return firstElement;
        }else{
            return direction;
        }
    }
    
    /**
     * Realiza un ataque basado en la fuerza del jugador y el daño provocado por las armas equipadas.
     * 
     * @return Valor total del ataque realizado por el jugador.
     */
    public float attack(){
        float totalWeaponAttack = strength;
        totalWeaponAttack += sumWeapons();
        return totalWeaponAttack;
    }
    
    /**
     * Método que permite al jugador intentar defenderse de un ataque recibido.
     * 
     * @param receivedAttack Valor del ataque recibido por parte de un monstruo.
     * @return {@code true} si el jugador pierde después de recibir el ataque, {@code false} si sobrevive.
     */
    public boolean defend(float receivedAttack){
        return manageHit(receivedAttack);
    }
    
    /**
     * Recibe una recompensa luego de ganar un combate, incrementando armas, escudos y salud.
     */
    public void receiveReward(){
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();
        
        for(int i = 1; i < wReward; i++){
            Weapon wnew = newWeapon();
            receiveWeapon(wnew);
        }
        for(int i = 1; i < sReward; i++){
            Shield snew = newShield();
            receiveShield(snew);
        }
        int extraHealth = Dice.healthReward();
        health += extraHealth;
    }
    
    /**
     * Retorna una representación en cadena del jugador, incluyendo su nombre, inteligencia, fuerza, salud,
     * armas, escudos y posición.
     * 
     * @return Cadena que representa al jugador.
     */
    public String toString() {
        return "P Name: " + this.name + ", Intelligence: " + this.intelligence + ", Strength: " + this.strength + ", Health: " + 
                health + ", Weapons: " + weapons.toString() + ", Shields: " + shields.toString() + ", Row: " + row + ", Col: " + col;
    }
    
    /**
     * Recibe una nueva arma y gestiona el reemplazo de armas antiguas si es necesario.
     * 
     * @param w Nueva arma recibida por el jugador.
     */
    public void receiveWeapon(Weapon w) {
        for (int i = weapons.size() - 1; i >= 0; i--) {
            Weapon wi = weapons.get(i);
            boolean discard = wi.discard();
            if(discard){
               weapons.remove(wi);
            }
        }
        if (weapons.size() < MAX_WEAPONS) {
            weapons.add(w);
        }
    }

    /**
     * Recibe un nuevo escudo y gestiona el reemplazo de escudos antiguos si es necesario.
     * 
     * @param s Nuevo escudo recibido por el jugador.
     */
    public void receiveShield(Shield s) {
        for (int i = shields.size() - 1; i >= 0; i--) {
            Shield si = shields.get(i);
            boolean discard = si.discard();
            if(discard){
               shields.remove(si);
            }
        }
        if (shields.size() < MAX_SHIELDS) {
            shields.add(s);
        }
    }
    
    /**
     * Crea y devuelve una nueva arma con valores aleatorios de potencia y usos restantes.
     * 
     * @return Nueva arma creada para el jugador.
     */
    public Weapon newWeapon(){
        Weapon weapon = new Weapon(Dice.weaponPower(), Dice.usesLeft());
        return weapon;
    }
    
    /**
     * Crea y devuelve un nuevo escudo con valores aleatorios de protección y usos restantes.
     * 
     * @return Nuevo escudo creado para el jugador.
     */
    public Shield newShield(){
        Shield shield = new Shield(Dice.shieldPower(), Dice.usesLeft());
        return shield;
    }
    
    /**
     * Calcula la suma total del daño que pueden causar todas las armas equipadas por el jugador.
     * 
     * @return Suma total del daño de todas las armas del jugador.
     */
    protected float sumWeapons(){
        float totalDmg = 0;
        for(int i = 0; i < weapons.size(); i++){
            Weapon weapon = weapons.get(i);
            totalDmg += weapon.attack();
        }
        return totalDmg;
    }
    
    /**
     * Calcula la suma total de la protección proporcionada por todos los escudos equipados por el jugador.
     * 
     * @return Suma total de la protección de todos los escudos del jugador.
     */
    protected float sumShields(){
        float totalShield = 0;
        for(int i = 0; i < shields.size(); i++){
            Shield shield = shields.get(i);
            totalShield += shield.protect();
        }
        return totalShield;
    }
    
    /**
     * Calcula la energía defensiva total del jugador, que incluye su inteligencia y la protección de sus escudos.
     * 
     * @return Energía defensiva total del jugador.
     */
    protected float defensiveEnergy(){
        float totalIntelligence = intelligence;
        float totalShield = sumShields();
        return totalIntelligence + totalShield;
    }
    
    /**
     * Gestiona el impacto de un ataque recibido por parte de un monstruo, determinando si el jugador recibe daño,
     * incrementando el contador de golpes consecutivos y verificando si el jugador ha perdido.
     * 
     * @param receivedAttack Valor del ataque recibido por parte de un monstruo.
     * @return {@code true} si el jugador pierde después de recibir el ataque, {@code false} si sobrevive.
     */
    public boolean manageHit(float receivedAttack){
        float defense = defensiveEnergy();
        if(defense < receivedAttack){
            gotWounded();
            incConsecutiveHits();
        } else {
            resetHits();
        }
        
        boolean lose; 
        if (consecutiveHits == HITS2LOSE || dead()) {
            resetHits();
            lose = true;
        } else {
            lose = false;
        }
        return lose;
    }
    
    /**
     * Reinicia el contador de golpes consecutivos del jugador a cero.
     */
    public void resetHits(){
        consecutiveHits = 0;
    }
    
    /**
     * Reduce la salud del jugador cuando recibe un ataque.
     */
    public void gotWounded() {
        health--;
    }
    
    /**
     * Incrementa el contador de golpes consecutivos recibidos por el jugador.
     */
    public void incConsecutiveHits(){
        consecutiveHits++;
    }
}