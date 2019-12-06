package wordGame;

public class Cell {
	
	boolean isSpecial = false;
	//Since the grid uses letters for the x this may need to change from an int.
	//A = 0, B = 1 etc.
	private String position;
	private Character value;
	
	public Cell (String position_,boolean isSpecial_) {
		position = position_;
		isSpecial = isSpecial_;
		value = null;
	}
	
	public void setValue(Character c) {
		value = c;
	}

	public Character getValue() {
		return value;
	}

	public void setSpecial(boolean b) {
		isSpecial = b;
		
	}
	
	public boolean getSpecial() {
		return isSpecial;
	}
	
	public String getPosition() {
		return position;
	}
	
	public boolean isEmpty() {
		if(value == null) {
			return true;
		}
		else{
			return false;
		}
	}
	
}
