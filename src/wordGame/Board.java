package wordGame;

public class Board {
	
	private int width = 10, height = 10;
	private Cell[][] board = new Cell [width][height];
	
	
	public Board()
	{
		//Fill empty 2D array with Cells.
		for (int x = 0; x<board.length;x++){
		    for (int y = 0;y <board.length;y++)
		    {
		        board[x][y] = new Cell(x,y,false);
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

	public Cell getCell(int x, int y) {
		return board[x][y];
	}
	
	public Cell[][] getBoard() {
		return board;
	}
}
