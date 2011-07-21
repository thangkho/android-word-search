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
	 */
	public ArrayList<String> getWords(int list, int numWords, String wordListText) throws IOException
	{
		ArrayList<String> wordList = new ArrayList<String>();
		//wordList.add(wordListText);
		
		Scanner scan = new Scanner(wordListText);
		Integer listWords = 0;
		
		/*
		 * Create Randomizer to help parse through the words
		 *	(should take into account total number of words in list,
		 * 	and how many words are needed). Assuming file is in alphabetical
		 * 	order, make sure we can fill the order without stepping over
		 * 	the bounds of the list. This will help keep things nice and clean,
		 *	without having to re-alphabetize later. 
		 */
		
		// °loop here. -_- 
		String chosenWord = "";
		while (scan.hasNextLine() && !chosenWord.matches("END"))
		{
			chosenWord = "";
			chosenWord = scan.nextLine();
			if(chosenWord.length() <= 10)
				wordList.add(chosenWord);
			listWords++;
		}
		//full list of words
		wordList.remove(wordList.indexOf("END"));
		wordList.trimToSize();
		
		ArrayList<String> finalWordList = new ArrayList<String>();
		Random rand = new Random();
		int temp = rand.nextInt(wordList.size()/10);
		
		for(int i = 0; i < numWords; i++)
		{
			finalWordList.add(wordList.get(temp));
			temp += rand.nextInt(wordList.size()/10);
		}
		
		finalWordList.trimToSize();
		
		// Use loop to parse through file, adding words to the ArrayList.
		return finalWordList;		
	}

}
