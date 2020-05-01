/* newNode.java
 * Node element for the board
 *
 * Lena Stolz - 17210577
 * Joseph Masterson - 19308132
 */


public class newNode {

    private String name;
	private int column; // column coordinate of the node
	private int row; // row coordindate of the node
	private int hValue; // heuristic value for the node
	private int gValue; // cost to reach the node
	private int fValue; // sum of h and g values
	private newNode parent; // the node previous to this one
	Status status; // is this node the start, end or an obstacle
	
	// Constructor to set the coordinates and set it to a normal node
	public newNode(int aRow, int aColumn, String name) {
		this.setColumn(aColumn);
		this.setRow(aRow);
		this.setNormal();
        this.setName(name);
	}

	// Methods to return all of the instance variables
	
    public String getName() {
        return this.name;
    }

	public int getColumn() {
		return this.column;
	}

	public int getRow() {
		return this.row;
	}

	public int gethValue() {
		return this.hValue;
	}

	public int getgValue() {
		return this.gValue;
	}

	public int getfValue() {
		return this.fValue;
	}

	public newNode getParent() {
		return this.parent;
	}

	public Status getStatus() {
		return this.status;
	}

	// Methods to set all of the instance variables
	
    public void setName(String name) {
        this.name = name;
    }


	public void setColumn(int aColumn) {
		if(aColumn >= 0 && aColumn < 8)
			this.column = aColumn;
	}

	public void setRow(int aRow) {
		if(aRow >= 0 && aRow < 8)
			this.row = aRow;
	}

	public void sethValue(int hValue) {
		this.hValue = hValue;
	}

	public void setgValue(int gValue) {
		this.gValue = gValue;
	}

	public void setfValue(int fValue) {
		this.fValue = fValue;
	}

	public void setParent(newNode parent) {
		this.parent = parent;
	}

	public void setNormal() {
		this.status = Status.NORMAL;
	}
	
	public void setStart() {
		this.status = Status.START;
	}
	
	public void setEnd() {
		this.status = Status.END;
	}
	
	public void setObstacle() {
		this.status = Status.OBSTACLE;
	}

    public void setPath() {
        this.status = Status.PATH;
    }
}
