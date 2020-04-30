import java.util.LinkedList;


class test {

    public static void main(String[] args) {
        /* Names are done row by column */
        
        Board board = new Board();

        board.createObstacle();
        board.printBoard();
        System.out.println();

        board.addStartEnd(0, 0, 7, 7);
        board.printNames();
        System.out.println(); 

        board.search(0, 0, 7, 7);



        /*
        newBoard board = new newBoard();

        int horizontal = 4;
        int vertical = 4;

        LinkedList<newNode> list = board.getAdjacent(horizontal, vertical);

        System.out.println("Horizontal Original: " + horizontal
                + "\tVertical Original: " + vertical);
        for(int i = 0; i < list.size(); i++) 
            System.out.println("Node : " + i 
                    + "\tHorizontal: " + list.get(i).getHorizontal() 
                    + "\tVertical: " + list.get(i).getVertical());
    */
    }
}
