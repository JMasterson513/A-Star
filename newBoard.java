import java.util.Random;
import java.lang.Math;
import java.util.LinkedList;

public class newBoard {

	static final int BOARD_SIZE = 8;
	static final int MAX_RAND = 3; // Maximum int for shape and block
	
    private LinkedList<newNode> openList;
    private LinkedList<newNode> closedList;

	private newNode[][] board; // vertical by horizontal
	
	Random rand = new Random();
	
	public newBoard() {
		board = new newNode[BOARD_SIZE][BOARD_SIZE];
		
		for(int row = 0; row < BOARD_SIZE; row++)
			for(int column = 0; column < BOARD_SIZE; column++)
				board[row][column] = new newNode(row, column, "" + row + column);
    }
	

	/* Returns the start point of the obstacle in an array
	 * Index 0 -> Vertical Position
	 * Index 1 -> Horizontal Position
	 */
	public int[] getObstacleStart() {
		int horizontalStart = rand.nextInt(MAX_RAND);
		int verticalStart = rand.nextInt(MAX_RAND);
		
		if(board[verticalStart][horizontalStart].getStatus() != Status.NORMAL)
			getObstacleStart();
		
		int[] ret = {verticalStart, horizontalStart};
		return ret;
	}
	
	/* Method to print out the board
	 * "0" -> Normal Space
	 * "X" -> Obstacle
	 * "*" -> Start and end spaces
	 */
	public void printBoard() {
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				Status status = board[i][j].getStatus();
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
		for(int i = 0; i < BOARD_SIZE; i++)
			for(int j = 0; j < BOARD_SIZE; j++)
				if(board[i][j].getStatus() == Status.OBSTACLE)
					board[i][j].setNormal();
	}
	
	
	/* Method to build a I-Shaped obstacle
	* All blocks go vertically
	*/
	public boolean iShape(int[] start, int blocks) {
		int verticalStart = start[0];
		int horizontalStart = start[1];
			
		for(int i = 0; i < blocks; i++) {
			if((verticalStart + i) >= BOARD_SIZE || (verticalStart + i) < 0 ||
					board[verticalStart + i][horizontalStart].getStatus() != Status.NORMAL) {
				clearObstacles();
				return false;
			}
			board[verticalStart + i][horizontalStart].setObstacle();
		}
		return true;
	}
		
	/* Method to build an L-Shaped Obstacle
	 * Half of the blocks go to the stem
	 * The other half of the blocks, floored, go to the bottom to finish the shape
	 */
	public boolean lShape(int[] start, int blocks) {
		int verticalStart = start[0];
		int horizontalStart = start[1];
		int verticalEnd = 0;
			
		int bottomBlocks = blocks / 2;
		int stemBlocks = blocks - bottomBlocks;
			
		for(int i = 0; i < stemBlocks; i++) {
			if((verticalStart + i) >= BOARD_SIZE || (verticalStart + i) < 0 ||
					board[verticalStart + i][horizontalStart].getStatus() != Status.NORMAL) {
				clearObstacles();
				return false;
			}
			board[verticalStart + i][horizontalStart].setObstacle();
			verticalEnd = verticalStart + i;
		}
		
		for(int i = 0; i <= bottomBlocks; i++) {
			if((horizontalStart + i) >= BOARD_SIZE || (horizontalStart + i) < 0 ||
					board[verticalEnd][horizontalStart + i].getStatus() == Status.START ||
					board[verticalEnd][horizontalStart + i].getStatus() == Status.END) {
				clearObstacles();
				return false;
			}
			board[verticalEnd][horizontalStart + i].setObstacle();
		}
		return true;
	}
		
	/* Method to build a T-Shaped Obstacle
	 * Half of the blocks go to the stem
	 * The other half, floored go to the top
	 * Start Position is where the stem starts
	 */
	public boolean tShape(int[] start, int blocks) {
		int verticalStart = start[0];
		int horizontalStart = start[1];

		int topBlocks = blocks / 2;
		int stemBlocks = blocks - topBlocks;
			
		for(int i = 0; i < stemBlocks; i++) {
			if((verticalStart + i) >= BOARD_SIZE || (verticalStart + i) < 0 ||
					board[verticalStart + i][horizontalStart].getStatus() != Status.NORMAL) {
				clearObstacles();
				return false;
			}
			board[verticalStart + i][horizontalStart].setObstacle();
		}
			
		// Start one behind the initial block
		for(int i = -1; i < topBlocks; i++) {
			if((horizontalStart + i) >= BOARD_SIZE || (horizontalStart + i) < 0 ||
					board[verticalStart][horizontalStart + i].getStatus() == Status.START ||
					board[verticalStart][horizontalStart + i].getStatus() == Status.END) {
				clearObstacles();
				return false;
			}
			board[verticalStart][horizontalStart + i].setObstacle();
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
	*
	*/
    public void addStartEnd(int startHorizontal, int startVertical, int endHorizontal, int endVertical) {
        board[startVertical][startHorizontal].setStart();
		board[endVertical][endHorizontal].setEnd();
    }


	/* Method to find the optimal root between two points
	* Returns a list of the optimal path
	*/
    public boolean search(int startHorizontal, int startVertical, int endHorizontal, int endVertical) {
        newNode currentNode = board[startVertical][startHorizontal]; // current point C, initially the start point
        currentNode.setgValue(0);

        openList = new LinkedList<newNode>(); // collection of all generated nodes
        closedList = new LinkedList<newNode>(); // collection of all expanded nodes

        LinkedList<newNode> adjacencyList;

        closedList.add(currentNode); // Add the start node to the closed list

        while(true) {
            adjacencyList = getAdjacent(currentNode.getHorizontal(), currentNode.getVertical());
                
            for(int i = 0; i < adjacencyList.size(); i++) {
                newNode adjCurrent = adjacencyList.get(i);
                
                /* The members that of X that are not in closed are added into the open list */
                if(!closedList.contains(adjCurrent)) {
                    //System.out.println("Added Member to Open: " + adjCurrent);
                    openList.add(adjCurrent);
                    adjCurrent.setParent(currentNode);
                }
            }
            closedList.add(currentNode); /* Add C into closed */
            
            for(int i = 0; i < openList.size(); i++) {
                //System.out.println("Size: " + openList.size() + " Current Value: " + i);
                newNode openCurrent = openList.get(i);

                /* Calculate g(n) for all the members of open */
                calculateG(openCurrent); 

                /* Calculate h(n) for all the members of open */
                calculateH(openCurrent, endHorizontal, endVertical);

                /* Estimate the value of f(n) for all the members of open */
                estimateF(openCurrent);
            }
           
            /* Select a point from Open where it is not in closed and the f-value is minimum
             * Assign that value to C 
             */
            currentNode = findNewCurrent(openList);
            System.out.println("Current Node is: " + currentNode.getName());
            closedList.add(currentNode); /* Add Oi into closed */
            openList.remove(currentNode); /* Remove Oi from open */

            if(currentNode.getStatus() == Status.END) {
                System.out.println("Got to the end");
                markPath(currentNode);
                return true;
            }
        }
    }

    /* Method to generate all adjacent nodes, m
     * +/- 1 value in each direction
     */

    // ????
    public LinkedList<newNode> getAdjacent(int horizontal, int vertical) {
        LinkedList<newNode> ret = new LinkedList<newNode>();

        /* Loop through the directly adjacent indices */
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
               
        
                /* If one of them is not zero then it it diagonal 
                 * If they are the same then it is either diagonal or starting point
                 * If we are out of bounds
                 * If it is an obstacle
                 */
                if((i == 0 || j == 0) && (i != j) 
                        && (vertical + i >= 0) && (vertical + i < BOARD_SIZE)
                        && (horizontal + j >= 0) && (horizontal + j < BOARD_SIZE)
                        && (board[vertical + i][horizontal + j].getStatus() != Status.OBSTACLE)) {
                    ret.add(board[vertical + i][horizontal + j]);
                }
            }
        }
        return ret;
    }

    /* Method to calculate the G-Value of the given node
     * Gets the G-value of the parent node and adds 1
     */ 
    public void calculateG(newNode node) {
       node.sethValue(node.getParent().getgValue() + 1); 
    }

    /* Method to calculate H-Value of the given node
     * Manhattan distance calculation
     */
    public void calculateH(newNode node, int endHorizontal, int endVertical) {
        int distance = Math.abs(node.getHorizontal() - endHorizontal) + Math.abs(node.getVertical() - endVertical);
        node.sethValue(distance);
    }

    /* Method to calculate the F-Value of the given node
     *  Adds the G and H values together
     */
    public void estimateF(newNode node) {
        node.setfValue(node.gethValue() + node.getgValue());
    }

    /* Method to find the Node with the minimum F-value
     * Loops through all elements in the open list
     * Returns the one with the smallest F-value
     */

    // Somehow only ever getting two nodes
    public newNode findNewCurrent(LinkedList<newNode> list) {
        newNode ret = list.getFirst();

        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getfValue() < ret.getfValue()) {
                ret = list.get(i);
            }
        }
        return ret;
    }

    /* Method to set the path
     * Works backwards from end to start with parents
     */
    public void markPath(newNode node) {
        System.out.println("Horizontal: " + node.getHorizontal() + "\tVertical: " + node.getVertical());

        if(node.getStatus() == Status.START) {
            System.out.println("Found Start Parent");
            return;
        }
        else if(node.getStatus() == Status.END)
            markPath(node.getParent());
        else
            node.setPath();
            markPath(node.getParent());



        /*
        newNode parent = board[vertical][horizontal].getParent();
        
        System.out.println("Horizontal : " + parent.getHorizontal() + "\tVertical: " + parent.getVertical());
        
        if(board[vertical][horizontal].getStatus() == Status.START)
            return;
        markPath(parent.getHorizontal(), parent.getVertical());
        
        if(board[vertical][horizontal].getStatus() == Status.END)
            markPath(parent.getHorizontal(), parent.getVertical());
        board[vertical][horizontal].setPath();
        markPath(parent.getHorizontal(), parent.getVertical());
        */
    }

    public void checkParent(LinkedList<newNode> list) {
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getParent() != null)
                System.out.println("Node: " + i + " has a parent");
        }
    }
	*/
}
