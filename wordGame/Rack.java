package wordGame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Rack {

	String rack[] ;
	
	String[] letters;
	
	public Rack() {
		
		rack = new String[5];
		
		letters = new String[] {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		
		refillRack();
	}
	
	public String refillRack() {
		
		Random rand = new Random();
		for(int i =0;i<rack.length;i++)
		{
			
				rack[i]=(letters[rand.nextInt(26)]);
			
		}
		rack[0] = "J";
		rack[1] = "O";
		rack[2] = "G";
		
		return "Rack refilled! Rack is now: " + toString();
	}
	
	public void removeFromRack(String letterPos) {
		for(Character c : letterPos.toCharArray()) {
			rack[Character.getNumericValue(c)-1] = null;	
		}
	}
	
	public String getLetterFromRack(int pos) {
		return rack[pos];
	}
	
	public char getLetterFromRackAsChar(int pos) {
		return rack[pos].charAt(0);
	}
	
	public String[] getRack() {
		return rack;
	}
	
	public String toString() {
		
		StringBuilder rackString = new StringBuilder();
		
		rackString.append("[ ");
		
		for (String s : rack) {
			if(s != null) {
			rackString.append(s);
			rackString.append(" ");
			}
		}
		
		rackString.append("]");
		
		return rackString.toString();
	}
	
}
