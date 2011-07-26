/**
 * This class will read text files filled with new-line-delimited words.
 *  It will return a randomized set of words, chosen from a list ID
 *  passed into it's getWords() method.
 */
package pk.dl.jk.wordsearch;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import android.app.Activity;

/**
 * @author Patrick Kuykendall
 * @date   Jul 7, 2011
 */
public class TextParser extends Activity {
	
	/*
	 * This method, getWords(), will return an ArrayList of words.
	 * 	The words returned will be based off of the list it is told
	 * 	to choose from, and the number of words it is told to return.
	 * 
	 *  Now able to scan text file, and return 10 words in alphabetical order. 
	 *  Words are <=10 characters long, to make sure they fit in our board. PK
	 */
	public ArrayList<String> getWords(int list, int numWords, String wordListText) throws IOException
	{
		ArrayList<String> wordList = new ArrayList<String>();
		
		/*
		 *  Scanner will parse full String of our text file into an ArrayList, by word.
		 */
		Scanner scan = new Scanner(wordListText);
		
		String chosenWord = "";
		while (scan.hasNextLine() && !chosenWord.matches("END"))
		{
			chosenWord = "";
			chosenWord = scan.nextLine();
			if(chosenWord.length() <= 7)
				wordList.add(chosenWord);
		}
		
		//full list of words
		wordList.remove(wordList.indexOf("END"));
		wordList.trimToSize();
		
		ArrayList<String> finalWordList = new ArrayList<String>();
		Random rand = new Random();
		int temp = rand.nextInt(wordList.size()/10);
		//int temp2 = 0;
		
		// Grabbing ten words, in order, pseudo-randomly.
		for(int i = 0; i < numWords; i++)
		{
			finalWordList.add(wordList.get(temp));
			//temp2 = temp;
			temp += rand.nextInt(wordList.size()/10);
			//if (temp2 == temp)
				//temp++;				
		}
		
		finalWordList.trimToSize();
		
		return finalWordList;		
	}

}
