package wordGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class Game implements Controller {

	private Rack rack;
	private Board board;
	private HashSet<String> validWords = new HashSet<String>(); 
	
	public static void main(String[] args) {
		Game game = new Game();
		TUI tui = new TUI(game);
		
	}
	
	public Game() {
		rack = new Rack();
		board = new Board();
		
		loadDictionary("dictionary.txt");
	}
	
	public void loadDictionary(String path) {
		//Load dictionary text file into hash set
		try {
		File file = new File(path);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine()) != null)
		{
		    validWords.add(line);
		}
		}  catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public String refillRack() {
		return rack.refillRack();
	}

	@Override
	public String gameState() {
		
		StringBuilder stateString = new StringBuilder();
		
		stateString.append("   A B C D E F G H I J \n");
		
		for (int r = 0; r < 10; r++) {
			
			if(r != 9) {
				stateString.append(" " + (r + 1));
			} else {
				stateString.append(r + 1);
			}
			
			for (int c = 0; c < 10; c++) {
				
				stateString.append("|");
				
				Cell cell = board.getCell(c,r);
				
				if (cell.getValue() != null) {
					stateString.append(cell.getValue());
				}
				
				if(cell.getSpecial() && cell.getValue()==null)
				{
					stateString.append("+");
				}
				else{
					stateString.append(" ");
				}
			}
			
			stateString.append("|");
			stateString.append(r + 1);
			stateString.append("\n");
			
		}
		
		stateString.append("   A B C D E F G H I J \n");
		
		stateString.append("\nThe players rack is: ");
		stateString.append(rack.toString());
		
		return stateString.toString();
	}

	@Override
	public String play(Play play)
	{
		/*
		 * Before checking if word is valid make sure that word can be placed
		 */
		Cell currentCell = board.getCell(play.cell());
		StringBuilder returnString = new StringBuilder();
		
		//Check if selected cells are all empty
		for(int i = 0; i < play.letterPositionsInRack().length(); i++) {
			// Check cell is out of range
			if(currentCell == null) {
				return "Cell is out of board range.";
			}
					
			//If cell isn't empty then this play isn't valid.
			if(!currentCell.isEmpty()) {
				returnString.append("Cell ");
				returnString.append(currentCell.getPosition());
				returnString.append(" is full.");
				return returnString.toString();
			}
					
			if(play.dir() == Direction.ACROSS) {
				currentCell = board.getCellRight(currentCell.getPosition());
			} else {
				currentCell = board.getCellDown(currentCell.getPosition());
			}
		}
		
		checkValidity(play);
		return gameState();
	}

	@Override
	public String calculateScore(Play play) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkValidity(Play play) 
	{
		//The start cell of the play
		String startCell = play.cell();
		
		//The length of the play
		int playLength = play.letterPositionsInRack().length();
		
		//The direction of the play
		Direction dir = play.dir();
		
		//Find the end cell using starting cell, direction and the lenght of the play
		String endCell = board.getEndCell(startCell, playLength, dir).getPosition();
		
		//The current cell being looped over
		String currentCell = play.cell();
		
		
		/*
		 * Loop over for the length of the play scanning different cells based on play direction
		 * and if end / start.
		 */
		for(int i = 0; i < play.letterPositionsInRack().length(); i++) 
		{
			if (currentCell == startCell) {
				
				//up
				//left
				
				if (dir == Direction.ACROSS) {
					//down
				} else {
					//right
				}
			} 
			else if (currentCell == endCell) {
				
				//right
				//down
				
				if (dir == Direction.ACROSS) {
					//up
				} else {
					//left
				}
			} 
			else {
				
				if (dir == Direction.ACROSS) {
					//up
					//down
				} else {
					//left
					//right
				}
				
			}
		}
		
		
		return null;
	}
	
	// Check if all cells are empty 
	//TODO: Check surrounding cells : Return 
	//TODO: Check those pos are valid
	
}
