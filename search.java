import java.util.Scanner;

public class search
{
	public static void main(String[] args)
	{
        newBoard board = new newBoard();
		Scanner keyboard = new Scanner(System.in);
    
        // Prints the board prior to obstacles
        board.createObstacle();
        board.printBoard();

        System.out.println("Enter Start Point Horizontally, between [0, 7]");
        int startH = keyboard.nextInt();
        System.out.println("Enter Start Point Vertically, between [0, 7]");
        int startV = keyboard.nextInt();
        System.out.println("Enter End Point Horizontally, between [0, 7]");
        int endH = keyboard.nextInt();
        System.out.println("Enter Start Point Horizontally, between [0, 7]");
        int endV = keyboard.nextInt();

        board.addStartEnd(startH, startV, endH, endV);
        board.printBoard();
        
        keyboard.close();
        
        System.out.println();

        board.search(startH, startV, endH, endV);
        board.printBoard();
	}
}
