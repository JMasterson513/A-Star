/* Main Method
 *
 * Lena STolz - 17210577
 * Joseph Masterson - 19308132
 */

import java.util.Scanner;

class search {

    public static void main(String[] args) {
        /* Names are done row by column */
        Board board = new Board();
        Scanner keyboard = new Scanner(System.in);
        
        // Creates obstcales and prints the board
        System.out.println("Key: \nX -> Obstacle\nO -> Normal Block\nS -> Start Block\nE -> End Block\n* -> Path\n");
        board.createObstacle();
        board.printBoard();
        
        boolean done;
        int startRow;
        int startColumn;
        int endRow;
        int endColumn;

        // User input
        do { 
            System.out.println("Enter the row you would like to start on, between 1 and 8");
            startRow = keyboard.nextInt() - 1;
            System.out.println("Enter the column you would like to start on, between 1 and 8");
            startColumn = keyboard.nextInt() - 1;
            
            System.out.println("Enter the row you would like to end on, between 1 and 8");
            endRow = keyboard.nextInt() - 1;
            System.out.println("Enter the column you would like to end on, between 1 and 8");
            endColumn = keyboard.nextInt() - 1;

            // Checks to make sure values are in bounds and attempts to set them
            done = board.addStartEnd(startColumn, startRow, endColumn, endRow);
        }while(!done);
        keyboard.close();

        // Prints the board with start and end
        board.printBoard();
        System.out.println();

        // Does the search and prints the board
        board.search(startColumn, startRow, endColumn, endRow);
        board.printBoard();

    }
}
