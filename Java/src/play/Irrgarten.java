/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package play;

import irrgarten.Game;
import irrgarten.UI.GraphicsUI;
import irrgarten.UI.TextUI;
import irrgarten.UI.UI;
import irrgarten.controller.Controller;

public class Irrgarten {
    public static void main(String[] args) {

        Game game = new Game(2);
        GraphicsUI Gui = new GraphicsUI();
        Controller controller = new Controller(game, Gui);
        controller.play();
   }
}