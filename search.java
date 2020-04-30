import java.util.Scanner;

class test {

    public static void main(String[] args) {
        /* Names are done row by column */
        Board board = new Board();
        Scanner keyboard = new Scanner(System.in);

        board.createObstacle();
        board.printBoard();
        
        boolean done;
        int startRow;
        int startColumn;
        int endRow;
        int endColumn;

        do { 
            System.out.println("Enter the row you would like to start on, between 1 and 8");
            startRow = keyboard.nextInt() - 1;
            System.out.println("Enter the column you would like to start on, between 1 and 8");
            startColumn = keyboard.nextInt() - 1;
            
            System.out.println("Enter the row you would like to end on, between 1 and 8");
            endRow = keyboard.nextInt() - 1;
            System.out.println("Enter the column you would like to end on, between 1 and 8");
            endColumn = keyboard.nextInt() - 1;

            done = (startRow >= 0) && (startRow < 8) && (startColumn >= 0) && (startColumn < 8)
                && (endRow >= 0) && (endRow < 8) && (endColumn >= 0) && (endColumn < 8);
        }while(!done);
        keyboard.close();

        board.addStartEnd(startColumn, startRow, endColumn, endRow);
        board.search(startColumn, startRow, endColumn, endRow);

        System.out.println();
        board.printBoard();

    }
}
