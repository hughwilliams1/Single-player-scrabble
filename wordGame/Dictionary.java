package wordGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Dictionary {
	
	//Hash set for accessing the words in the dictionary
	private HashSet<String> validWords = new HashSet<String>(); 
	
	
	/*
	 * Constructor for the dictionary
	 * Loads file and words into HashSet
	 */
	public Dictionary(String path) {
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
	
	/*
	 * Returns boolean if the word is in the dictionary
	 */
	public boolean checkValidWord(String word) {
		return validWords.contains(word);
	}
	
}
