package wordGame;

public class Cell {
	
	boolean isSpecial = false;
	//Since the grid uses letters for the x this may need to change from an int.
	//A = 0, B = 1 etc.
	private int x,y;
	private Character value;
	
	public Cell (int x_,int y_,boolean isSpecial_) {
		x = x_;
		y = y_;
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
}
