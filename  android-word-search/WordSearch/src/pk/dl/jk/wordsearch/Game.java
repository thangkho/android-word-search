package pk.dl.jk.wordsearch;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


/*
 * Need to save the state of the game in this onPause()!!
 * can do this using getPreferences.edit.putString(stuff goes here);
 */
public class Game extends Activity {
	private static final String TAG = "game";
	public static final String KEY_DIFF = "pk.dl.jk.wordsearch.difficulty";
	public static final String KEY_CAT = "pk.dl.jk.wordsearch.difficulty";

	//public static final String KEY_CATEGORY = "pk.dl.jk.wordsearch.category";
	private static final int DIFF_EASY = 0;
	private static final int DIFF_MEDIUM = 1;
	private static final int DIFF_HARD = 2;
	public static final int CONTINUING_GAME = -1;
	
	private static final int CAT_RANDOM = 0;
	private static final int CAT_PEOPLE = 1;
	private static final int CAT_ANIMALS = 2;
	private static final int CAT_VEHICLES = 3;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		//IF ERROR IS THROWN, IT IS CAUGHT AND PRINTED TO THE LOGCAT IN E. 
		try{
		Log.e(TAG, "IN GAME");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		//Get the extras put onto the intent
		int diff = getIntent().getIntExtra(KEY_DIFF, DIFF_EASY);
		int cat = getIntent().getIntExtra(KEY_CAT, CAT_RANDOM);
		
		/*
		 *  Read a file, convert it to a string. Choose file based off of client's
		 *   category selection. PK
		 */
			// TODO: work on logic to select proper word file
			InputStream is = this.getAssets().open("basicEnglish.txt");
			int size = is.available();
	
	        // Read the entire asset into a local byte buffer.
	        byte[] buffer = new byte[size];
	        is.read(buffer);
	        is.close();
	        String wordListText = new String(buffer);
	        
	        // Create parser, to parse and return word list.
	        ArrayList<String> wordList = new ArrayList<String>();
			TextParser t = new TextParser();
			
			//this is the ArrayList filled with our random words (in alphabetical order)
			wordList = t.getWords(cat,10,wordListText);
			
		
		Log.e(TAG, "************IN GAME DIFF IS " + diff);
		Log.e(TAG, "********IN GAME CAT IS " + cat);
		
		
		//String[] listWords = selectWords(cat);

		/*
		 * need to put here a way to get the puzzle with the 
		 * selected difficulty and category. Create a method 
		 * that takes an int for the category then randomly 
		 * selects the words from the chosen category. Also
		 * create a getPuzzle method that takes an int indicating
		 * difficulty, and a string array (the list of words returned
		 * by the getCategory method). Also need to create a
		 * method that then populates
		 * the 2D array with the words in the array of chosen
		 * words.
		 */
		
		//createBoard(diff, listWords);
		}
		catch(Exception e){
			Log.e("ERROR", "ERROR IN CODE: " + e.toString());
			e.printStackTrace();
		}
	}
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu){
	    	/* Creates the options menu
	    	 * call the superclass method passing it the menu
	    	 * use a menu inflater to inflate the menu so that
	    	 * it reads from the menu.xml set up in the res
	    	 */
	    	super.onCreateOptionsMenu(menu);
	    	MenuInflater infl = getMenuInflater();
	    	infl.inflate(R.menu.menu, menu);
			return true;    	
	    }
	 
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item){
	    	/* Listener for menu item selected
	    	 * if the settings button is selected from
	    	 * the menu, a new activity is launched with
	    	 * the new intent to fire up prefs class 
	    	 */
	    	switch(item.getItemId()){
	    	case R.id.settings:
	    		startActivity(new Intent(this, Prefs.class));
	    		return true;
	    	case R.id.about:
	    		startActivity(new Intent(this, About.class));
	    		break;
	    	case R.id.hints:
	    		//Highlight all of the occurrences of selected letter
	    		break;
	    	
	    	}
	    	return false;
	    }
	    
	
	@Override
	protected void onPause(){
		/*
		 * 	NEED TO SAVE BOARD STATE IN HERE
		 */
		super.onPause();
		Music.stopMusic(this);		
	}
	
	@Override
	protected void onResume(){
		/*
		 * NEED TO REINSTATE THE BOARD
		 */
		super.onResume();
		Music.playMusic(this, R.raw.game);
	}
	/*private String[] selectWords(int cat){
		String[] wordArray = new String[10];
		switch(cat){
		//Random
		case CAT_RANDOM:
			
			// go into the file or db? and choose from any
			 // of the lists tagged as "random"
			 //
			break;
		//People
		case CAT_PEOPLE:
			break;
		//Animals
		case CAT_ANIMALS:
			break;
		//Vehicles
		case CAT_VEHICLES:
			break;
		//CHANGE OR ADD MORE HERE IF NECESSARY
		}
		
		return wordArray;
		
	}*/
	/*private void createBoard(int diff, String[] array){
		
		 // Takes the diff and array of words and 
		 // performs an algorithm to add to a 2D
		 // array which represents the board.
		 //
		//NEED TO ACTUALLY CALC THE BOARD HEIGHT AND WIDTH AND PUT IN TO ARRAY CONSTRUCTOR!!
		int height = 30;
		int width = 20;
		String[][] board = new String[width][height];
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				board[i][j] = "*";
			}
		}
		
		
	}*/

}
