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
	public ArrayList<String> getWords(int numWords, String wordListText) throws IOException
	{
		ArrayList<String> wordList = new ArrayList<String>();
		//change this variable to adjust the amount of words chosen.
		int words = numWords;
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
		
		//Start of the new Code I added
		int temp = rand.nextInt(wordList.size());
		
		for(int i = 0; i < numWords; i++)
		{
			//For the first word, just add it the word to the list
			if(finalWordList == null){
				finalWordList.add(wordList.get(temp));
			
			//For the rest of the words, choose a random number, if the word at that index equals 
			//a word that has already been chosen then choose a new word
			}else{
				
				temp = rand.nextInt(wordList.size());
				int count = 0;
				
				while(count < finalWordList.size()){
					//if the words match then it goes back to the beginning of the loop and sees if the new word also matches
					if(wordList.get(temp).equalsIgnoreCase(finalWordList.get(count))){
						temp = rand.nextInt(wordList.size());
						count = -1;
					}
					
					count++;
				}
				finalWordList.add(wordList.get(temp));
			}//End of Nested if statement
			
		}//End of for loop
		///End of the Code that I added///
		
		
		//Previous code Patrick had in place to select words randomly
		/*
		int tenth = (wordList.size()/words);
		int temp = rand.nextInt(wordList.size()/words);
		
		// Grabbing ten words, in order, pseudo-randomly.
		for(int i = 0; i < numWords; i++)
		{
			finalWordList.add(wordList.get(temp));
			temp = (tenth * i) + rand.nextInt(wordList.size()/words);
		}
		*/
		///End of Previous code///
		
		
		finalWordList.trimToSize();
		
		return finalWordList;		
	}

}
