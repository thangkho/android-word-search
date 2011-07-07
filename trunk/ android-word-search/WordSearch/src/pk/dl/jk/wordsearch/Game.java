package pk.dl.jk.wordsearch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


/*
 * Need to save the state of the game in this onPause()!!
 * can do this using getPreferences.edit.putString(stuff goes here);
 */
public class Game extends Activity {
	private static final String TAG = "game";
	public static final String KEY_DIFF = "pk.dl.jk.wordsearch.difficulty";
	//public static final String KEY_CATEGORY = "pk.dl.jk.wordsearch.category";
	private static final int DIFF_EASY = 0;
	private static final int DIFF_MEDIUM = 1;
	private static final int DIFF_HARD = 2;
	private static final int CONTINUING_GAME = -1;
	
	//private static final int CAT_RANDOM = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		int diff = getIntent().getIntExtra(KEY_DIFF, DIFF_EASY);
		//int cat = getIntent().getIntExtra(KEY_CATEGORY, CAT_RANDOM);
		Log.d(TAG, "IN GAME DIFF IS " + diff);
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
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		Music.stopMusic(this);		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		Music.playMusic(this, R.raw.game);
	}

}
