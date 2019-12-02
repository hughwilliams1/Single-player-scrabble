package wordGame;

import java.util.ArrayList;
import java.util.Random;

public class Rack {

	ArrayList<String> rack = new ArrayList<String>(10);
	
	String[] letters;
	
	public Rack() {
		
		rack = new ArrayList<String>(10);
		
		letters = new String[] {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	}
	
	public String refillRack() {
		Random rand = new Random();
		while (rack.size() != 10) {
			rack.add(letters[rand.nextInt(26)]);
		}
		
		return "Rack refilled! Rack is now: " + toString();
	}
	
	public void removeFromRack(int index) {
		rack.remove(index);
	}
	
	public ArrayList<String> getRack() {
		return rack;
	}
	
	public String toString() {
		
		StringBuilder rackString = new StringBuilder();
		
		rackString.append("[ ");
		
		for (String s : rack) {
			rackString.append(s);
			rackString.append(" ");
		}
		
		rackString.append("]");
		
		return rackString.toString();
	}
	
}
