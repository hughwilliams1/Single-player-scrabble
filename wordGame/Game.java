package wordGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Game implements Controller {

	private Rack rack;
	private Board board;
	private Dictionary dictionary;
	private int score;
	
	public static void main(String[] args) {
		Game game = new Game();
		TUI tui = new TUI(game);
	}
	
	
	public Game() {
		rack = new Rack();
		board = new Board();
		score = 0;
		dictionary = new Dictionary("dictionary.txt");
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
				else if(cell.getValue()==null){
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
		
		String isValid  = checkValidity(play);
		if (isValid.equals("VALID")) {
			score += (calculateScore(play).charAt(14) - 49);
			
			insertLetters(play);	
			rack.removeFromRack(play.letterPositionsInRack());
		}
		
		return "\nThe play was: " + isValid + ", The Score was: " + score + "\n\n" + gameState();
	}

	@Override
	public String calculateScore(Play play)
	{
		int letterScore=0;
		int tempScore=0;
		String current = play.cell();
		
		for(int i=0;i<play.letterPositionsInRack().length();i++)
		{
			String c = rack.getLetterFromRack(play.letterPositionsInRack().charAt(i)-49);
			switch (c) {
				case "Q":
				case "X":
				case "Y":
				case "Z":{
					letterScore = 3;
					break;
				}
					
				case "B":
				case "G":
				case "J":
				case "K":
				case "M":
				case "N":{
					letterScore = 2;
					break;	
				}
				default:
					letterScore = 1;
					break;
			}
			
			//checks if cell is special and changes the score if it is
			if(board.getCell(current).isSpecial) {
				tempScore += letterScore*2;
			} else {
				tempScore += letterScore;
			}
			
			if(play.dir() == Direction.DOWN) {
				current = board.getCellDown(current).getPosition();
			}
			else {	
				current = board.getCellRight(current).getPosition();
			}
		}
		return "The Score is: "  + Integer.toString(tempScore);
	}

	private Board insertLetters(Play p) {
		
		Cell newCell = board.getCell(p.cell());
		
		for (char letter: p.letterPositionsInRack().toCharArray()) {
		
			newCell.setValue(rack.getLetterFromRackAsChar(Character.getNumericValue(letter-1)));
			board.setCell(newCell);
			
			if(p.dir() == Direction.ACROSS)
			{
				newCell = board.getCellRight(newCell.getPosition());
			} else
			{
				newCell = board.getCellDown(newCell.getPosition());
			}	
		}
		return board;
	}
	@Override
	public String checkValidity(Play play) 
	{
		//Make a backup of the board before the new play is added
		
		//Adds the new word to the board
		board=insertLetters(play);
		
		//The start cell of the play
		String startCellPosition = play.cell();
		
		//The length of the play
		int playLength = play.letterPositionsInRack().length();
		
		//The direction of the play
		Direction dir = play.dir();
		
		//Find the end cell using starting cell, direction and the length of the play
		String endCellPosition = board.getEndCell(startCellPosition, playLength, dir).getPosition();
		
		//The current cell being looped over
		String currentCellPosition = play.cell();
		
		//An arraylist containing all of the words which need to be checked in order for this play to be valid. Ensures that placing a new letter doesnt break existing words.
		ArrayList<String> wordsToCheck = new ArrayList<String>();
		
		if(play.letterPositionsInRack().length() > 1) {
			//Builds the new word from letters in rack and letters on the board.
			wordsToCheck.add(getWordFromStart(startCellPosition, endCellPosition, dir));
		}
		
		/*
		 * Loop over for the length of the play scanning different cells based on play direction
		 * and if end / start.
		 */
		for(int i = 0; i < play.letterPositionsInRack().length(); i++) 
		{	
			//For each new letter check cells above and below for any collisions.
			if (dir == Direction.ACROSS) {
				wordsToCheck.add(getWordUpAndDown(currentCellPosition));
				currentCellPosition = board.getCellRight(currentCellPosition).getPosition();
			} 
			//For each new letter check cells right and left for any collisions.
			else {
				wordsToCheck.add(getWordLeftAndRight(currentCellPosition));
				currentCellPosition = board.getCellDown(currentCellPosition).getPosition();
			}
		}
		
		for (String word : wordsToCheck) {
			if (!dictionary.checkValidWord(word.toLowerCase())) {
				deleteWord(startCellPosition, endCellPosition, dir);
				return "INVALID";
			}
		}
		deleteWord(startCellPosition, endCellPosition, dir);
		return "VALID";
	}
	
	private void deleteWord(String currentPos, String endCellPos, Direction dir) {
		
		if(dir == Direction.ACROSS) {
			String cellAfterEnd = board.getCellRight(endCellPos).getPosition();
		while (!currentPos.equals(cellAfterEnd)) {
				board.getCell(currentPos).setValue(null);
				currentPos = board.getCellRight(currentPos).getPosition();
			}
		} else {
			String cellAfterEnd = board.getCellDown(endCellPos).getPosition();	
			while (!currentPos.equals(cellAfterEnd)) {
				board.getCell(currentPos).setValue(null);
				currentPos = board.getCellDown(currentPos).getPosition();
			}
		}
	}
	
	
	/*
	 * Add the initial letter, to the word
	 * Scan Up and add before
	 * Scan Down and append
	 */
	private String getWordUpAndDown(String position) {
		
		StringBuilder fullWord = new StringBuilder(); 
		
		//Add starting letter
		fullWord.append(board.getCell(position).getValue());
		
		Cell upCell = board.getCellUp(position);
		while(upCell.isEmpty() == false)
		{
			fullWord.insert(0, upCell.getValue());
			upCell = board.getCellUp(upCell.getPosition());
		}
		
		Cell downCell = board.getCellDown(position);
		while(downCell.isEmpty() == false) {
			fullWord.append(downCell.getValue());
			downCell = board.getCellDown(downCell.getPosition());
		}
		return fullWord.toString();
	}
	
	/*
	 * Add the initial letter, to the word
	 * Scan Left and add before
	 * Scan Right and append
	 */
	private String getWordLeftAndRight(String position) {
		
		StringBuilder fullWord = new StringBuilder(); 
		
		fullWord.append(board.getCell(position).getValue());
		
		Cell leftCell = board.getCellLeft(position);
		while(leftCell.isEmpty() == false)
		{
			fullWord.insert(0, leftCell.getValue());
			leftCell = board.getCellLeft(leftCell.getPosition());
		}
		
		Cell rightCell = board.getCellRight(position);
		while(rightCell.isEmpty() == false) {
			fullWord.append(rightCell.getValue());
			rightCell = board.getCellRight(rightCell.getPosition());
		}
		return fullWord.toString();
	}
	
	
	/*
	 * Add the initial word by looping until you reach the end position
	 * Scan Left / Up and add before
	 * Scan Right / Down and append to word
	 */
	private String getWordFromStart(String startCellPos, String endCellPos, Direction dir) {
		
		StringBuilder fullWord = new StringBuilder(); 
		String currentPos = startCellPos;
		
		if(dir == Direction.ACROSS) {
			
			//Add letters which aren't on the board yet into the word.
			String cellAfterEnd = board.getCellRight(endCellPos).getPosition();
			while (!currentPos.equals(cellAfterEnd)) {
				fullWord.append(board.getCell(currentPos).getValue());
				currentPos = board.getCellRight(currentPos).getPosition();
			}
			//Add letters which are to the left of the new letters
			Cell leftCell = board.getCellLeft(startCellPos);
			while(leftCell.isEmpty() == false) {
				fullWord.insert(0, leftCell.getValue());
				leftCell = board.getCellLeft(leftCell.getPosition());
				
			}
			//Add letters which are to the right of the new letters
			Cell rightCell = board.getCellRight(endCellPos);
			while(rightCell.isEmpty() == false) {
				fullWord.append(rightCell.getValue());
				rightCell = board.getCellRight(rightCell.getPosition());
				
			}
			
			
		} else {
			
			//Add letters which aren't on the board yet into the word.
			String cellAfterEnd = board.getCellDown(endCellPos).getPosition();	
			while (!currentPos.equals(cellAfterEnd)) {
				fullWord.append(board.getCell(currentPos).getValue());
				currentPos = board.getCellDown(currentPos).getPosition();
			}
			//Add letters which are above the new letters
			Cell upCell = board.getCellUp(startCellPos);
			while(upCell.isEmpty() == false) {
				fullWord.insert(0, upCell.getValue());
				upCell = board.getCellLeft(upCell.getPosition());
				
			}
			//Add letters which are below the new letters
			Cell downCell = board.getCellDown(endCellPos);
			while(downCell.isEmpty() == false) {
				fullWord.append(downCell.getValue());
				downCell = board.getCellRight(downCell.getPosition());
				
			}
			
		}
		return fullWord.toString();
	}
	
}
