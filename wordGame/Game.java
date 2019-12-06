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
		
		StringBuilder returnString = new StringBuilder();
		StringBuilder fullWord = new StringBuilder();
		
		//Set current cell to start cell of new word
		Cell currentCell = board.getCell(play.cell());
		
		//Check word collisions with starting cell.
		if(play.dir()==Direction.ACROSS)
		{
			Cell leftCell = board.getCellLeft(currentCell.getPosition());
			while(leftCell.isEmpty() == false) 
			{				
				fullWord.insert(0, leftCell.getValue());
				leftCell = board.getCellLeft(currentCell.getPosition());
			}
		}
		
		else {
			Cell upCell = board.getCellUp(currentCell.getPosition());
			while(upCell.isEmpty() == false)
			{
				fullWord.insert(0, upCell.getValue());
				upCell = board.getCellUp(currentCell.getPosition());
			}
		}
		
		//Check if selected cells are all empty
		for(int i = 0; i < play.letterPositionsInRack().length(); i++) 
		{
			if(currentCell == null) {
				return "Cell is out of board range.";
			}
			//If cell isnt empty then this play isnt valid.
			if(!currentCell.isEmpty()) {
				returnString.append("Cell ");
				returnString.append(currentCell.getPosition());
				returnString.append(" is full.");
				return returnString.toString();
			}
			
			if(play.dir() == Direction.ACROSS) 
			{
				currentCell = board.getCellRight(currentCell.getPosition());
			} else {
				currentCell = board.getCellDown(currentCell.getPosition());
		}
			
			//Check word collisions with end cell.
			if(play.dir()==Direction.ACROSS)
			{
				Cell rightCell = board.getCellRight(currentCell.getPosition());
				while(rightCell.isEmpty() == false) 
				{				
					fullWord.insert(0, rightCell.getValue());
					rightCell = board.getCellRight(currentCell.getPosition());
				}
			}
			
			else {
				Cell downCell = board.getCellDown(currentCell.getPosition());
				while(downCell.isEmpty() == false)
				{
					fullWord.insert(0, downCell.getValue());
					downCell = board.getCellDown(currentCell.getPosition());
				}
			}	
			//Check if selected cells are all empty
			
			currentCell = board.getCell(play.cell());
			
			for(int j = 0; j < play.letterPositionsInRack().length(); j++) 
			{
				StringBuilder tempWord = new StringBuilder();
				currentCell = board.getCell(play.cell());
				if(play.dir() == Direction.ACROSS) 
				{
					Cell upCell = board.getCellUp(currentCell.getPosition());
					Cell downCell = board.getCellDown(currentCell.getPosition());
					while(upCell.isEmpty() == false)
					{
						tempWord.insert(0, downCell.getValue());
						downCell = board.getCellDown(currentCell.getPosition());
					}
					while(downCell.isEmpty() == false)
					{
						tempWord.append(downCell.getValue());
						downCell = board.getCellDown(currentCell.getPosition());
					}
					currentCell = board.getCellRight(currentCell.getPosition());
					//CHECK IF TEMPWORD IS IN DICTIONARY!!!
				} else 
				{
					Cell rightCell = board.getCellRight(currentCell.getPosition());
					Cell leftCell = board.getCellLeft(currentCell.getPosition());
					while(rightCell.isEmpty() == false)
					{
						tempWord.insert(0, rightCell.getValue());
						rightCell = board.getCellRight(currentCell.getPosition());
					}
					while(leftCell.isEmpty() == false)
					{
						tempWord.append(leftCell.getValue());
						leftCell = board.getCellLeft(currentCell.getPosition());
					}
					currentCell = board.getCellRight(currentCell.getPosition());
			}	
			}
			
		} 
		//Check left or up of start cell !
		//Check cells are empty !
		//Check right of end or down
		
		//If cell isnt empty add to board
		
		return null;
	}
	
}
