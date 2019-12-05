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
				position.append(y);
				
		        board[x][y] = new Cell(position.toString(),false);
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

	public Cell getCell(int x,int y) {
		return board[x][y];
	}
	
	
	
	public Cell[][] getBoard() {
		return board;
	}

	public Cell getCell(String position)//b4
	{
		int x = letters.includes(position.charAt(0));
		int y = position.indexOf(1);
		return board[x][y];
	}
	
	
	
}
