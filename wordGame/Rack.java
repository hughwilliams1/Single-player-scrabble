package wordGame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Rack {

	private String rack[] ;
	
	private String[] letters;
	
	public Rack() {
		
		rack = new String[5];
		
		letters = new String[] {"A", "A", "B","C","D","E", "E", "F","G","H","I", "I", "J","K","L","M","N","O", "O", "P","Q","R","S","T","U", "U", "V","W","X","Y","Z"};
		
		refillRack();
	}
	
	public String refillRack() {

        Random rand = new Random();
        //Loops through the rack and replaces any empty(null) positions with a random letter.
        for(int i =0;i<rack.length;i++)
        {
            if (rack[i] ==  null) {
                rack[i]=(letters[rand.nextInt(26)]);
            }
        }

        return "Rack refilled! Rack is now: " + toString();
    }

    //Removes at least 1 letter from the rack. letterPos is a string containing the position in the rack which is to be removed.
    public void removeFromRack(String letterPos) {
        for(Character c : letterPos.toCharArray()) {
            rack[Character.getNumericValue(c)-1] = null;
        }
    }
    //Loops through the rack and checks if any positions are empty(null)
    public boolean checkRackIsEmpty(String letterPos)
    {
        for(Character c : letterPos.toCharArray()) {
            if(rack[Character.getNumericValue(c)-1]==null) {
                return true;
            }
        }

        return false;
    }
	
	public String getLetterFromRack(int pos) {
		try {
			return rack[pos];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public char getLetterFromRackAsChar(int pos) {
		return rack[pos].charAt(0);
	}
	
	public String[] getRack() {
		return rack;
	}
	//Creates a visual representation of the rack to be displayed
	public String toString() {
		
		StringBuilder rackString = new StringBuilder();
		
		rackString.append("[ ");
		
		for (String s : rack) {
			if(s != null) {
			rackString.append(s);
			rackString.append(" ");
			} else {
				rackString.append("- ");
			}
		}
		
		rackString.append("]");
		
		return rackString.toString();
	}	
}
