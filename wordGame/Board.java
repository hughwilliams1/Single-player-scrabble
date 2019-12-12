package wordGame;

public class Board {
	
	private int width = 10, height = 10;
	private Cell[][] board = new Cell [width][height];
	
	private String[] letters = {"A","B","C","D","E","F","G","H","I","J"};
	
	public Board()
	{
		//Fill empty 2D array with Cells.
		for (int x = 0; x<board.length;x++){
		    for (int y = 0;y <board.length;y++)
		    {
		    	StringBuilder position = new StringBuilder();
				position.append(letters[x]);
				position.append(y+1);
				
		        board[x][y] = new Cell(position.toString(),false,null);
		    }
		}
		
		//Manually assign special cells.
		board[4][1].setSpecial(true); //E2
		board[5][1].setSpecial(true); //F2
		
		board[3][3].setSpecial(true); //D4
		board[6][3].setSpecial(true); //G4
		
		board[3][6].setSpecial(true); //D7
		board[6][6].setSpecial(true); //G7
		
		board[4][8].setSpecial(true); //E2
		board[5][8].setSpecial(true); //F2
	}
	
	//Takes a cell and inserts it onto the board.
	public void setCell(Cell cell) {
		int[] pos = getPosXY(cell.getPosition());
		
		board[pos[0]][pos[1]] = cell;
	}

	//Gets the cell at a given x,y
	public Cell getCell(int x,int y) {
		return board[x][y];
	}
	//Gets the cell at a given position
	public Cell getCell(String position)
	{		
		int[] pos = getPosXY(position);
	
		try {
			return board[pos[0]][pos[1]];
		} catch (ArrayIndexOutOfBoundsException e) {
			return new Cell("", false,null);
		}
	}
	
	//Takes a position and returns the cell that is in the position to the right of it.
	public Cell getCellRight(String position) {
		
		int[] pos = getPosXY(position);

		try {
			return board[pos[0]+1][pos[1]];
		} catch (ArrayIndexOutOfBoundsException e) {
			return new Cell("", false,null);
		}
	}
	//Takes a position and returns the cell that is in the position to the left of it.
	public Cell getCellLeft(String position) {
		
		int[] pos = getPosXY(position);
		
		try {
			return board[pos[0]-1][pos[1]];
		} catch (ArrayIndexOutOfBoundsException e) {
			return new Cell("", false,null);
		}
		
	}
	//Takes a position and returns the cell that is below it.
	public Cell getCellDown(String position) {
		int[] pos = getPosXY(position);
		
		try {
			return board[pos[0]][pos[1]+1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return new Cell("", false,null);
		}
	}
	//Takes a position and returns the cell that is above it.
	public Cell getCellUp(String position)
	{
		int[] pos = getPosXY(position);
		
		try {
			return board[pos[0]][pos[1]-1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return new Cell("", false,null);
		}
	}
	//Uses a startCell and the length of the play to determine which cell contains the last letter in the word.
	public Cell getEndCell(String startCell, int playLength, Direction dir) {
		
		int[] pos = getPosXY(startCell);
		
		if (dir == Direction.ACROSS) {
			return board[pos[0] + (playLength - 1)][pos[1]];
		} else {
			return board[pos[0]][pos[1] + (playLength - 1)] ;
		}
	}
	//Returns the board as a 2D array of cells.
	public Cell[][] getBoard() {
		Cell[][] copy = new Cell [width][];
		for(int i=0;i<width;i++) {
			copy[i] = board[i].clone();
		}
		return copy;
	}
	//Takes a position (A1 for example) and converts it to xy coordinates.
	private int[] getPosXY(String position) {
		int x = position.charAt(0) - 65;
		int y = position.charAt(1) - 49;
		
		int[] arr = {x,y};
		
		return arr;
	}
	
	public void setBoard(Cell[][] newBoard) {
		board = newBoard;
	}
	
	
}
