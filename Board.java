import java.util.Random;
import java.lang.Math;
import java.util.LinkedList;

public class Board {

	static final int BOARD_SIZE = 8;
	static final int MAX_RAND = 3; // Maximum int for shape and block
	
    private LinkedList<newNode> open;
    private LinkedList<newNode> closed;

	private newNode[][] board; // row by column
	
	Random rand = new Random();
	
	public Board() {
		board = new newNode[BOARD_SIZE][BOARD_SIZE];
		
		for(int row = 0; row < BOARD_SIZE; row++)
			for(int column = 0; column < BOARD_SIZE; column++)
				board[row][column] = new newNode(row, column, "" + row + column);
    }


    public void printNames() {
        for(int row = 0; row < BOARD_SIZE; row++) {
			for(int column = 0; column < BOARD_SIZE; column++)
                System.out.print(board[row][column].getName() + " ");
		System.out.println();
		}
    }

	/* Method to print out the board
	 * "0" -> Normal Space
	 * "X" -> Obstacle
	 * "S" -> Start space
	 * "E" -> End Space
	 */
	public void printBoard() {
		for(int row = 0; row < BOARD_SIZE; row++) {
			for(int column = 0; column < BOARD_SIZE; column++) {
				Status status = board[row][column].getStatus();
				switch(status) {
					case NORMAL:
						System.out.print("O ");
						break;
					case START:
						System.out.print("S ");
						break;
					case END:
						System.out.print("E ");
						break;
					case OBSTACLE:
						System.out.print("X ");
						break;
                    case PATH:
                        System.out.print("* ");
                        break;
				}
			}
			System.out.println();
		}
	}

	/* Method to clear all of the obstacles on the board
	* Used when the obstacles do not fit
	*/
	public void clearObstacles() {
		for(int row = 0; row < BOARD_SIZE; row++)
			for(int column = 0; column < BOARD_SIZE; column++)
				if(board[row][column].getStatus() == Status.OBSTACLE)
					board[row][column].setNormal();
	}

	/* Returns the start point of the obstacle in an array
	* Index 0 -> Vertical Position
	* Index 1 -> Horizontal Position
	*/
	public int[] getObstacleStart() {
		int columnStart = rand.nextInt(MAX_RAND);
		int rowStart = rand.nextInt(MAX_RAND);
		
		int[] ret = {rowStart, columnStart};
		return ret;
	}

	/* Method to build a I-Shaped obstacle
	* All blocks go vertically
	*/
	public boolean iShape(int[] start, int blocks) {
		int rowStart = start[0];
		int columnStart = start[1];
			
		for(int row = 0; row < blocks; row++) {
			if((rowStart + row) >= BOARD_SIZE || (rowStart + row) < 0) {
				clearObstacles();
				return false;
			}
			board[rowStart + row][columnStart].setObstacle();
		}
		return true;
	}

	/* Method to build an L-Shaped Obstacle
	 * Half of the blocks go to the stem
	 * The other half of the blocks, floored, go to the bottom to finish the shape
	 */
	public boolean lShape(int[] start, int blocks) {
		int rowStart = start[0];
		int columnStart = start[1];
		int rowEnd = 0;
			
		int bottomBlocks = blocks / 2;
		int stemBlocks = blocks - bottomBlocks;
			
		for(int row = 0; row < stemBlocks; row++) {
			if((rowStart + row) >= BOARD_SIZE || (rowStart + row) < 0) {
				clearObstacles();
				return false;
			}
			board[rowStart + row][columnStart].setObstacle();
			rowEnd = rowStart + row;
		}
		
		for(int column = 0; column <= bottomBlocks; column++) {
			if((columnStart + column) >= BOARD_SIZE || (columnStart + column) < 0) {
				clearObstacles();
				return false;
			}
			board[rowEnd][columnStart + column].setObstacle();
		}
		return true;
	}

	/* Method to build a T-Shaped Obstacle
	 * Half of the blocks go to the stem
	 * The other half, floored go to the top
	 * Start Position is where the stem starts
	 */
	public boolean tShape(int[] start, int blocks) {
		int rowStart = start[0];
		int columnStart = start[1];

		int topBlocks = blocks / 2;
		int stemBlocks = blocks - topBlocks;
			
		for(int row = 0; row < stemBlocks; row++) {
			if((rowStart + row) >= BOARD_SIZE || (rowStart + row) < 0 ||
					board[rowStart + row][columnStart].getStatus() != Status.NORMAL) {
				clearObstacles();
				return false;
			}
			board[rowStart + row][columnStart].setObstacle();
		}
			
		// Start one behind the initial block
		for(int column = -1; column < topBlocks; column++) {
			if((columnStart + column) >= BOARD_SIZE || (columnStart + column) < 0 ||
					board[rowStart][columnStart + column].getStatus() == Status.START ||
					board[rowStart][columnStart + column].getStatus() == Status.END) {
				clearObstacles();
				return false;
			}
			board[rowStart][columnStart + column].setObstacle();
		}
		return true;
	}

	/* Creates the obstacle 
	* Randomly determine how many blocks the obstacle is {3, 5}
	* Get start point from getShapeStart method
	* Randomly determine the shape of the obstacle
	* 0 -> T
	* 1 -> L
	* 2 -> I
	*/
	public void createObstacle() {
		int[] start = getObstacleStart();
		int blocks = rand.nextInt(MAX_RAND) + 3;
		int shape = rand.nextInt(MAX_RAND);
		boolean obstacleCreated = false;
			
		switch(shape) {
			case 0:
				//System.out.println(blocks + " T\n");
				obstacleCreated = tShape(start, blocks);
				break;
			case 1:
				//System.out.println(blocks + " L\n");
				obstacleCreated = lShape(start, blocks);
				break;
			case 2:
				//System.out.println(blocks + " I\n");
				obstacleCreated = iShape(start, blocks);
				break;
			default:
				System.out.println("No idea how this got here");
				break;
		}	
		if(obstacleCreated)
			return;
		else
			createObstacle();
	}

	/* Method to add the start and end points
	*/
    public void addStartEnd(int startColumn, int startRow, int endColumn, int endRow) {
        board[startRow][startColumn].setStart();
		board[endRow][endColumn].setEnd();
    }

	/* Method to search through the grid
	* Starts at the given start and ends at the given end
	*/
	public void search(int startColumn, int startRow, int endColumn, int endRow) {
		/* 1 - Declare three parameters, the source, the destination and the current point, S, E, C */

		/* 2 - Declare a function called mem(n) for each single point n */

		/* 3 - C := S */
		newNode current = board[startRow][startColumn];	

		/* 4 - Initiate two lists call them closed and open */
		closed = new LinkedList<newNode>();
		open = new LinkedList<newNode>();
		closed.clear();
        open.clear();
        closed.add(current);

		boolean search = true;
		LinkedList<newNode> adjList;

		while(search) {
		    /* 5 - Identify all the points (m points) that are directly/simply (without any intermediate point) reachable from C: X = {X1, X2, ..., Xm} */
		    adjList = getAdjacent(current);

		    /* 6 - Those members of X that are not in closed are added into the open list */
		    for(int i = 0; i < adjList.size(); i++) {
			    newNode adjCurrent = adjList.get(i);

			    if(!closed.contains(adjCurrent)) {
			    	adjCurrent.setParent(current);
				    open.add(adjCurrent);
			    }
		    }

		    /* 7 - Add C into closed */

		    /* 8 - Calculate g(n) for all the members of open */
		    /* 9 - Estimate h(n) for all the members of open */
		    /* 10 - Estimate the value of f(n) for all the members of open */
		    estimateValues(open, endColumn, endRow);

		    /* 11 - Select a point from open, Oi, where Oi !∈ closed and f(Oi) is minimum; then assign Oi to C; C := Oi */
		    /* 12 - mem(Oi) = C */
		    current = findMin(open);

		    /* 13 - Add Oi into closed and remove Oi from open */
		    closed.add(current);
		    open.remove(current);

		    /* 14 - If C = E, terminate the algorithm otherwise go back to step 4 */
		    if(current.getStatus() == Status.END) {
			    printParents(current);
                return;
		    }
		}
        return;
	}

	public newNode findMin(LinkedList<newNode> list) {
		newNode ret = list.getFirst();

		for(int i = 0; i < list.size(); i++)
			if(list.get(i).getfValue() < ret.getfValue())
				ret = list.get(i);
		return ret;
	} 


	public void estimateValues(LinkedList<newNode> list, int endColumn, int endRow) {
		for(int i = 0; i < list.size(); i++) {
			newNode node = list.get(i);
			calculateG(node);
			estimateH(node, endColumn, endRow);
			estimateF(node);
		}
	}

	public void calculateG(newNode node) {
		int parentG = node.getParent().getgValue();
		node.setgValue(parentG + 1);
	}

	public void estimateH(newNode node, int endColumn, int endRow) {
		int rowTerm = Math.abs(node.getRow() - endRow);
		int columnTerm = Math.abs(node.getColumn() - endColumn);
		node.sethValue(rowTerm + columnTerm);
	}

	public void estimateF(newNode node) {
		int gValue = node.getgValue();
		int hValue = node.gethValue();
		node.setfValue(gValue + hValue);
	}

	public LinkedList<newNode> getAdjacent(newNode node) {
		LinkedList<newNode> ret = new LinkedList<newNode>();
		int rowIndex = node.getRow();
		int columnIndex = node.getColumn();

		/* Loop through the directly adjacent indices */
        for(int row = -1; row <= 1; row++) {
            for(int column = -1; column <= 1; column++) {
               
        
                /* If one of them is not zero then it it diagonal 
                 * If they are the same then it is either diagonal or starting point
                 * If we are out of bounds
                 * If it is an obstacle
                 */
                if((row == 0 || column == 0) && (row != column) 
                        && (rowIndex + row >= 0) && (rowIndex + row < BOARD_SIZE)
                        && (columnIndex + column >= 0) && (columnIndex + column < BOARD_SIZE)
                        && (board[rowIndex + row][columnIndex + column].getStatus() != Status.OBSTACLE)) {
                    ret.add(board[rowIndex + row][columnIndex + column]);
                }
            }
        }
        return ret;
	}

    public void printParents(newNode node) {
    
        while(node != null) {
            node.setPath();
            System.out.println(node.getName());
            node = node.getParent();
        }

    }

}
