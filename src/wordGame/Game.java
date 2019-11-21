package wordGame;

public class Game implements Controller {

	private Rack rack;
	
	public Game() {
		
		rack = new Rack();
	
	}
	

	@Override
	public String refillRack() {
		return rack.refillRack();
	}

	@Override
	public String gameState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String play(Play play) {
		// TODO Auto-generated method stub
		return null;
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
