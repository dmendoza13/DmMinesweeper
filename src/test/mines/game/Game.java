/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.mines.game;

import java.util.Random;
import test.mines.models.Square;

/**
 *
 * @author User
 */
public class Game {

    int width;
    int height;
    int nMines;

    public boolean playing;

    Square board[][];

    public Game(int width, int height, int nMines) {
        this.width = width;
        this.height = height;
        this.nMines = nMines;
        this.playing = true;
        this.initBoard();
        this.addRandomMines();
        this.addAdjacentNumberOfMines();
    }

    private void initBoard() {
        board = new Square[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Square sq = new Square();
                sq.covered = true;
                sq.hasFlag = false;
                sq.hasMinesAround = false;
                sq.nMinesAround = 0;
                board[x][y] = sq;
            }
        }
    }

    private void addRandomMines() {
        int addedMines = 0;
        while (addedMines < nMines) {
            Random rand = new Random();

            int x = rand.nextInt(width);
            int y = rand.nextInt(height);

            if (!board[x][y].hasMine) {
                board[x][y].hasMine = true;
                addedMines++;
            }

        }
    }

    private void addAdjacentNumberOfMines() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y].nMinesAround = numAdjacentMines(x, y);
                if (board[x][y].nMinesAround > 0) {
                    board[x][y].hasMinesAround = true;
                }
            }
        }
    }

    private int numAdjacentMines(int row, int col) {
        int numOfMines = 0;
        for (int currentCol = Math.max(0, col - 1); currentCol <= Math.min(col + 1, width - 1); currentCol++) {
            for (int currentRow = Math.max(0, row - 1); currentRow <= Math.min(row + 1, height - 1); currentRow++) {
                numOfMines += board[currentRow][currentCol].hasMine == true ? 1 : 0;
            }
        }
        return numOfMines;
    }

    public void printBoard() {
        System.out.println("-------------------------------------");
        for (Square[] rows : board) {
            String srow = "";
            for (Square sq : rows) {
                if (sq.hasFlag) {
                    srow += "P";
                } else if (sq.covered) {
                    srow += ".";
                } else {
                    if (sq.hasMine) {
                        srow += "*";
                    } else if (sq.hasMinesAround) {
                        srow += sq.nMinesAround;
                    } else {
                        srow += "-";
                    }
                }
                srow += " ";
            }
            System.out.println(srow);
        }
        System.out.println("-------------------------------------");
    }

    public void executeAction(int x, int y, String action) {
        if ("U".equalsIgnoreCase(action) && board[x][y].covered) {
            validateUnconveredSquare(x, y);
        } else if ("M".equalsIgnoreCase(action) && board[x][y].covered) {
            board[x][y].hasFlag = !board[x][y].hasFlag;
            validateIfWon();
        }
        printBoard();
    }

    private void validateUnconveredSquare(int x, int y) {
        board[x][y].covered = false;
        if (board[x][y].hasMine) {
            playing = false;
            uncoverBoard();
        } else {
            // uncover Adjacent squares
            if (!board[x][y].hasMinesAround) {
                uncoverAdjacent(x, y);
            }
        }
    }

    public int uncoverAdjacent(int row, int col) {
        int numOfMines = 0;
        for (int currentCol = Math.max(0, col - 1); currentCol <= Math.min(col + 1, width - 1); currentCol++) {
            for (int currentRow = Math.max(0, row - 1); currentRow <= Math.min(row + 1, height - 1); currentRow++) {
                Square sq = board[currentRow][currentCol];
                if (!sq.hasFlag && sq.covered && !sq.hasMine) {
                    sq.covered = false;
                    if (!sq.hasMinesAround) {
                        uncoverAdjacent(currentRow, currentCol);
                    }
                }
            }
        }
        return numOfMines;
    }

    private void uncoverBoard() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y].covered = false;
                board[x][y].hasFlag = false;
            }
        }
    }
    
    private void validateIfWon() {
        int nMarkMines = 0;
        for (Square[] rows : board) {
            for (Square sq : rows) {
                nMarkMines += (sq.hasMine && sq.hasFlag ? 1 : 0);
            }
        }
        if (nMarkMines == nMines) {
            playing = false;
            System.out.println("You Won!!");
        }
    }

}
