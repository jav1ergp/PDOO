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
public class Labyrinth {
    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 1;
    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    private Monster monsters[][];
    private Player players[][];
    private char labyrinth[][];

    
    /**
     * Constructor de la clase Labyrinth que inicializa un laberinto con dimensiones dadas y una salida específica.
     * 
     * @param nRows Número de filas del laberinto.
     * @param nCols Número de columnas del laberinto.
     * @param exitRow Fila de la salida del laberinto.
     * @param exitCol Columna de la salida del laberinto.
     */
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        labyrinth = new char[nRows][nCols];
        players = new Player[nRows][nCols];
        monsters = new Monster[nRows][nCols];
        for (int i = 0; i < this.nRows; i++) {
            for (int j = 0; j < this.nCols; j++) {
                labyrinth[i][j] = EMPTY_CHAR;
            }
        }
        labyrinth[exitRow][exitCol] = EXIT_CHAR;
    }

    /**
     * Coloca a los jugadores en posiciones aleatorias dentro del laberinto.
     * 
     * @param players Lista de jugadores que se colocarán en el laberinto.
     */
    public void spreadPlayers(ArrayList<Player> players){
        for(Player p : players){
            int[] pos = randomEmptyPos();
            putPlayer2D(-1, -1, pos[ROW], pos[COL], p);
        }
    }
    
     
    /**
     * Verifica si hay un ganador en el laberinto, es decir, si algún jugador ha alcanzado la salida.
     * 
     * @return True si hay un ganador (jugador en la salida), False si no.
     */
    public boolean haveAWinner(){
        boolean aux = false;
        if (players[exitRow][exitCol] != null) {
            aux = true; 
        }
        return aux;
    }
    
    
    /**
     * Retorna una representación en cadena del estado actual del laberinto.
     * 
     * @return Cadena que representa el estado del laberinto.
     */
    public String toString(){
        String s = "L[" + ROW + ", " + COL + ", " + exitRow + ", " + exitCol + "]\n"; 
        for(int i = 0; i < nRows; ++i){
            s += "[";
            for(int j = 0; j < nCols; ++j){
                if(j == nCols - 1){
                    s += labyrinth[i][j] + "]\n";
                } else{
                    s += labyrinth[i][j] + ", ";
                }
            } 
        }
        return s;
    }
    
    
    /**
     * Añade un monstruo en la posición dada del laberinto.
     * 
     * @param row Fila donde se añadirá el monstruo.
     * @param col Columna donde se añadirá el monstruo.
     * @param monster Objeto Monster que representa al monstruo a añadir.
     */
    public void addMonster(int row, int col, Monster monster){
        if(row >= 0 && row < nRows && col >= 0 && col < nCols){
            if(labyrinth[row][col] == EMPTY_CHAR ){
                monster.setPos(row, col);
                monsters[row][col] = monster;
                labyrinth[row][col] = MONSTER_CHAR;
                
            }
        }
    }
    
    
    /**
     * Mueve al jugador en la dirección dada dentro del laberinto y maneja cualquier interacción con monstruos.
     * 
     * @param direction Dirección en la que el jugador desea moverse.
     * @param player Objeto Player que representa al jugador que se moverá.
     * @return Objeto Monster si hay un combate con un monstruo en la nueva posición, {@code null} si no hay combate.
     */
    public Monster putPlayer(Directions direction, Player player){
        int oldRow = player.getRow();
        int oldCol = player.getCol();
        int[] newPos = dir2Pos(oldRow, oldCol, direction);
        Monster monster = putPlayer2D(oldRow, oldCol, newPos[ROW], newPos[COL], player);
        return monster;
    }
    
    
    /**
     * Añade un bloque en el laberinto comenzando desde la posición dada y con la orientación y longitud especificadas.
     * 
     * @param orientation Orientación del bloque (VERTICAL u HORIZONTAL).
     * @param startRow Fila inicial desde donde se añadirá el bloque.
     * @param startCol Columna inicial desde donde se añadirá el bloque.
     * @param length Longitud del bloque que se añadirá.
     */
    public void addBlock(Orientation orientation, int startRow, int startCol,int length){
        int incRow, incCol;
        if(orientation == Orientation.VERTICAL){
            incRow = 1;
            incCol = 0;
        } else {
            incRow = 0;
            incCol = 1;
        }
        int row = startRow;
        int col = startCol;
        
        while(posOk(row,col) && emptyPos(row,col) && length > 0){
            labyrinth[row][col] = BLOCK_CHAR;
            length -= 1;
            row += incRow;
            col += incCol;
        }
    }
    
    
    /**
     * Retorna las direcciones válidas en las que un jugador puede moverse desde la posición dada.
     * 
     * @param row Fila actual del jugador en el laberinto.
     * @param col Columna actual del jugador en el laberinto.
     * @return Lista de direcciones válidas en las que el jugador puede moverse.
     */
    public ArrayList<Directions> validMoves(int row, int col){
         ArrayList<Directions> output = new ArrayList<>();
        if(canStepOn(row+1, col)){
            output.add(Directions.DOWN);
        }
        if(canStepOn(row-1, col)){
            output.add(Directions.UP);
        }
        if(canStepOn(row, col+1)){
            output.add(Directions.RIGHT);
        }
        if(canStepOn(row, col-1)){
            output.add(Directions.LEFT);
        }
        return output;
    }
    
    private boolean posOk(int row, int col){
        return row >= 0 && row < nRows && col >= 0 && col < nCols;
    }
    
    private boolean emptyPos(int row, int col){
        boolean aux = false;
        if(labyrinth[row][col] == EMPTY_CHAR){
            aux = true;
        }
        return aux;
    }
    
    private boolean monsterPos(int row, int col){
        boolean aux = false;
        if(labyrinth[row][col] == MONSTER_CHAR){
            aux = true;
        }
        return aux;
    }
    
    private boolean exitPos(int row, int col){
        boolean aux = false;
        if(labyrinth[row][col] == EXIT_CHAR){
            aux = true;
        }
        return aux;
    }
    
    private boolean combatPos(int row, int col){
        boolean aux = false;
        if(labyrinth[row][col] == COMBAT_CHAR){
            aux = true;
        }
        return aux;
    }
    
    private boolean canStepOn(int row, int col){
        boolean aux = false;
        if(posOk(row,col)){
            if(emptyPos(row, col) || monsterPos(row, col) || exitPos(row, col)){
              aux = true;
            }
        }
        return aux;
    }
    
    private void updateOldPos(int row, int col){
        if(posOk(row,col)){
            if(combatPos(row, col)){
                labyrinth[row][col] = MONSTER_CHAR;
            } else {
                labyrinth[row][col] = EMPTY_CHAR;
            }
        }
    }
    
    private int[] dir2Pos(int row, int col, Directions direction){
        int nPos[] = {0, 0};
        
        switch (direction) {
            case LEFT:
                nPos[0] = row;
                nPos[1] = col - 1;
            break;
            case RIGHT:
                nPos[0] = row;
                nPos[1] = col + 1;
            break;
            case UP:
                nPos[0] = row - 1;
                nPos[1] = col;
            break;
            case DOWN:
                nPos[0] = row + 1;
                nPos[1] = col;
            break;
        }
        return nPos;
    }
    
    private int[] randomEmptyPos() {
        int randomPos[] = new int [2];
        do {
            randomPos[0] = Dice.randomPos(nRows);
            randomPos[1] = Dice.randomPos(nCols);
        } while (!emptyPos(randomPos[0], randomPos[1]));
        return randomPos;
    }
    
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player){
        Monster output = null;
        if(canStepOn(row,col)){
            if(posOk(oldRow, oldCol)){
                Player p = players[oldRow][oldCol];
                if(p == player){
                    updateOldPos(oldRow, oldCol);
                    players[oldRow][oldCol] = null;
                }
            }
            boolean monsterPos = monsterPos(row, col);
            if(monsterPos){
                labyrinth[row][col] = COMBAT_CHAR;              
                output = monsters[row][col];
            } else {
                char number = player.getNumber();
                labyrinth[row][col] = number;
            }
            players[row][col] = player;
            player.setPos(row, col);
        }
        return output;
    }
}
