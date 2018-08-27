/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minesweeper;

import java.util.Scanner;
import test.mines.game.Game;

/**
 *
 * @author User
 */
public class Minesweeper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Welcome!");
        Scanner scanIn = new Scanner(System.in);
        
        String gameInitData = askInfo(scanIn, "Board’s height, width, and number of mines");

        String[] initData = gameInitData.split(" ");
        
        Game game = new Game(
                Integer.parseInt(initData[1]), 
                Integer.parseInt(initData[0]), 
                Integer.parseInt(initData[2])
        );        
        game.printBoard();
        
        while(game.playing) {
           String userActionData = askInfo(scanIn, "X, Y and Action");
           String[] data = userActionData.split(" ");
           game.executeAction(
                   Integer.parseInt(data[0]),
                   Integer.parseInt(data[1]),
                   data[2]
           );
        }
        
    }
    
    public static String askInfo(Scanner scanIn, String question) {
        System.out.println(question + ": ");   
        String inputString;
        inputString = scanIn.nextLine(); 
        System.out.println("User wrote: " + inputString);
        return inputString;        
    }
    
}
