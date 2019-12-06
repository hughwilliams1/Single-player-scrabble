package wordGame;

public class Game implements Controller {

	private Rack rack;
	private Board board;
	
	public Game() {
		rack = new Rack();
		board = new Board();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		TUI tui = new TUI(game);
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
		Cell currentCell = board.getCell(play.cell());
		
		StringBuilder returnString = new StringBuilder();
		
		for(int i = 0; i < play.letterPositionsInRack().length(); i++) {
			if(!currentCell.isEmpty()) {
				returnString.append("Cell ");
				returnString.append(currentCell.getPosition());
				returnString.append(" is full.");
				return returnString.toString();
			} 
			
			if(play.dir().toString().equals("ACROSS")) {
				currentCell = board.getCellAcross(currentCell.getPosition());
			} else {
				currentCell = board.getCellDown(currentCell.getPosition());
		}
			
			
			
			currentCell = board.getCell(play.cell());
		}
		
		return "VALID";
	}

	@Override
	public String calculateScore(Play play) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkValidity(Play play) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
