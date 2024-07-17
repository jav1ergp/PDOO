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
public class Game {
    private static final int MAX_ROUNDS = 10;
    private int currentPlayerIndex;
    private String log;
    
    private Labyrinth labyrinth;
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    private Player currentPlayer;    

    
    /**
     * Constructor de la clase Game que inicializa el juego con un número dado de jugadores.
     * Crea un laberinto, jugadores y monstruos aleatorios, y configura el estado inicial del juego.
     * 
     * @param nplayers Número de jugadores que participarán en el juego.
     */
    public Game(int nplayers) {
        
        log = "";
        labyrinth = new Labyrinth(4, 4, 3, 3);
        players = new ArrayList<>();
        monsters = new ArrayList<>();

        for (int i = 0; i < nplayers; i++) {
            char number = (char)('0' + i);
            Player player = new Player(number, Dice.randomIntelligence(), Dice.randomStrength());
            players.add(player);
        }
        
        configureLabyrinth();
        currentPlayerIndex = Dice.whoStars(nplayers); 
        currentPlayer = players.get(currentPlayerIndex);
              
        labyrinth.spreadPlayers(players);
    }
    
    public boolean finished(){
        return labyrinth.haveAWinner();
    }
    
    
    /**
     * Realiza el siguiente paso del juego para el jugador actual.
     * Maneja el movimiento del jugador, los combates con monstruos y la gestión de recompensas y resurrecciones.
     * 
     * @param preferredDirection La dirección preferida para que el jugador se mueva.
     * @return True si el juego ha terminado después del paso, False si no.
     */
    public boolean nextStep(Directions preferredDirection){
        String log = "";
        boolean dead = currentPlayer.dead();
        if(!dead){
            Directions direction = actualDirection(preferredDirection);
            if(direction != preferredDirection){
                logPlayerNoOrders();
            }
            Monster monster = labyrinth.putPlayer(direction, currentPlayer);
            
            if(monster == null){
                logNoMonster();
            } else {
                GameCharacter winner = combat(monster);
                manageReward(winner);
            }
        } else {
            manageResurrection();
        }
        
        boolean endGame = finished();
        
        if(!endGame){
            nextPlayer();
        }
        
        return endGame;
    }
    
    /**
     * Obtiene el estado actual del juego incluyendo el estado del laberinto, jugadores y monstruos.
     * 
     * @return El estado actual del juego encapsulado en un objeto GameState.
     */
    public GameState getGameState(){
        String labyrinthState = labyrinth.toString();
        String playerState = "";
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            playerState += player.toString() + "\n";
        }
        String monsterState = "";
        for (int i = 0; i < monsters.size(); i++) {
            Monster monster = monsters.get(i);
            monsterState += monster.toString() + "\n";
        }
        return new GameState(labyrinthState, playerState, monsterState, currentPlayerIndex, finished(), log);
    }
    
    private void configureLabyrinth(){
        labyrinth.addBlock(Orientation.VERTICAL, 3, 2, 1);
        labyrinth.addBlock(Orientation.HORIZONTAL, 1, 2, 2);
        String nombre1 = "M1";
        String nombre2 = "M2";
        
        Monster monster1 = new Monster(nombre1, Dice.randomIntelligence(), 
                            Dice.randomStrength());
        Monster monster2 = new Monster(nombre2, Dice.randomIntelligence(), 
                            Dice.randomStrength());
    
        labyrinth.addMonster(Dice.randomPos(4), Dice.randomPos(4), monster1);
        labyrinth.addMonster(Dice.randomPos(4), Dice.randomPos(4), monster2);
        
        monsters.add(monster1);
        monsters.add(monster2);
    }
    
    private void nextPlayer(){
        if(currentPlayerIndex < players.size() -1){
            currentPlayerIndex += 1;
            currentPlayer = players.get(currentPlayerIndex);
        } else {
            currentPlayerIndex = 0;
            currentPlayer = players.get(currentPlayerIndex);
        }
    }
    
    
    Directions actualDirection(Directions preferredDirection){
        int currentRow = currentPlayer.getRow();
        int currentCol = currentPlayer.getCol();
        ArrayList<Directions> validMoves = new ArrayList<>();
        validMoves = labyrinth.validMoves(currentRow, currentCol);
        Directions output = currentPlayer.move(preferredDirection, validMoves);
        return output;
    }
    
    private GameCharacter combat(Monster monster){
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        float playerAttack = currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);
        
        while(!lose && rounds < MAX_ROUNDS){
            winner = GameCharacter.MONSTER;
            rounds++;
            float monsterAttack = monster.attack();
            lose = currentPlayer.defend(monsterAttack);
            if(!lose){
                playerAttack = currentPlayer.attack();
                lose = monster.defend(playerAttack);
                winner = GameCharacter.PLAYER;
            }
        }
        
        logRounds(rounds, MAX_ROUNDS);
        return winner;
    }
    
    private void manageReward(GameCharacter winner){
        if(winner == GameCharacter.PLAYER){
            currentPlayer.receiveReward();
            logPlayerWon();
        } else {
            logMonsterWon();
        }
    }
    
    private void manageResurrection(){
        boolean resurrect = Dice.resurrectPlayer();
        
        if(resurrect){
            currentPlayer.resurrect();
            logResurrected();
        } else {
            logPlayerSkipTurn();
        }
    }
    
    private void logPlayerWon() {
        log = "Player " + currentPlayerIndex + " has won the combat.\n";
    }

    private void logMonsterWon() {
        log = "The monster has won the combat.\n";
    }

    private void logResurrected() {
        log = "Player " + currentPlayerIndex + " has resurrected.\n";
    }

    private void logPlayerSkipTurn() {
        log = "Player " + currentPlayerIndex + " has lost the turn for being dead.\n";
    }

    private void logPlayerNoOrders() {
        log = "Player " + currentPlayerIndex + " do not follow the human instructions (not posible).\n";
    }

    private void logNoMonster() {
        log = "Player " + currentPlayerIndex + " has moved to an empty cell or was unable to move.\n";
    }

    private void logRounds(int rounds, int max) {
        log = rounds + " rounds of " + max + " passed.\n";
    }
}
