package pk.dl.jk.wordsearch;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/*
 * Need to save the state of the game in this onPause()!!
 * can do this using getPreferences.edit.putString(stuff goes here);
 */
public class Game extends Activity {
	private static final String TAG = "game";
	public static final String KEY_DIFF = "pk.dl.jk.wordsearch.difficulty";
	public static final String KEY_CAT = "pk.dl.jk.wordsearch.category";
	//ACCESSED BY OTHER CLASSES DO NOT DELETE
	public static final int ROWS = 10;
	public static final int COLS = 10;

	//public static final String KEY_CATEGORY = "pk.dl.jk.wordsearch.category";
	private static final String ORIG_PUZZLE = "opuzzle";
	private static final String FOUND_PUZZLE = "fpuzzle";

	private static final int DIFF_EASY = 0;
	private static final int DIFF_MEDIUM = 1;
	private static final int DIFF_HARD = 2;
	public static final int CONTINUING_GAME = -1;
	
	private static final int CAT_RANDOM = 0;
	private static final int CAT_PEOPLE = 1;
	private static final int CAT_ANIMALS = 2;
	private static final int CAT_VEHICLES = 3;
	private static final int CAT_PLACES = 4;
	
	private PuzzleGridView puzz;
	public static String[][] board = new String[ROWS][COLS];
	public static String[][] foundBoard = new String[ROWS][COLS];
	//IDK IF THIS IS THE AMOUNT OF WORDS WE WILL HAVE OR NOT< JUST SET FOR NOW.
	public static ArrayList<String> aWordList = new ArrayList<String>();
	
	//2D array of the word search
	public String[][] myGrid = new String[10][10];          
    //Random generator
    private Random random = new Random();
    //Creates a LinkedList of the connected words that will be placed in the grid
    private LinkedList linkedWords = new LinkedList();
    //Holds the words that failed to be put into the grid
    private String[] failedWords;
    //used to iterate through failed words
    private int count;
    
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		//IF ERROR IS THROWN, IT IS CAUGHT AND PRINTED TO THE LOGCAT IN E. 
		try{
		Log.e(TAG, "IN GAME");
		super.onCreate(savedInstanceState);		

		//Get the extras put onto the intent
		int diff = getIntent().getIntExtra(KEY_DIFF, DIFF_EASY);
		int cat = getIntent().getIntExtra(KEY_CAT, CAT_RANDOM);
		Log.e(TAG, " ***ORIG DIFF: " + diff);
		Log.e(TAG, " **ORIG CAT: " + cat);

		//if the diff is -1 (continue) need to get the stuff saved from onPause()
		if(diff == CONTINUING_GAME){
			board = fromPuzzleString(getPreferences(MODE_PRIVATE).getString(ORIG_PUZZLE, toPuzzleString(board)));
			foundBoard = fromPuzzleString(getPreferences(MODE_PRIVATE).getString(FOUND_PUZZLE, toPuzzleString(foundBoard)));

		}else {
//			//JUST FOR TESTING
//			String names = "jessiasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfg";
//			int position = 0;
//			for(int i = 0; i < ROWS; i++){
//				for(int j = 0; j < COLS; j++){
//				
//					char character = names.charAt(position);
//					Game.board[i][j] = Character.toString(character);
//					foundBoard[i][j] = Character.toString(character);
//					position++;
//				}
//			
//
//			}
//			aWordList.add("jess");
//			aWordList.add("gas");
//			aWordList.add("sag");
//		}
		
//		
//		//CHANGED
//		puzz = new PuzzleGridView(this);
//		setContentView(puzz);
		
		/*
		 *  Read a file, convert it to a string. Choose file based off of client's
		 *   category selection. PK
		 */
		String fileName = "";
		switch(cat){
			case 0:
				fileName = "basicEnglish.txt"; 
				break;
			case 1:
				fileName = "people.txt"; 
				break;
			case 2:
				fileName = "animals.txt";
				break;
			case 3:
				fileName = "vehicles.txt";
				break;
			case 4:
				fileName = "places.txt";
				break;
		}
		
		InputStream is = this.getAssets().open(fileName);
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
		wordList = t.getWords(14,wordListText);
		aWordList = wordList;
		count = 0;
		failedWords = new String[wordList.toArray().length];
		String rand2 = "";
		for(int i = 0; i < aWordList.size(); i++){
			rand2 += " " + aWordList.get(i);
		}
		Log.e(TAG, " WordListBEFORE: " + rand2);
		
		WordPlacer placer = new WordPlacer();
		//***THROWING ERROR HERE DOES NOT LIKE CASTING TO STRING[]

		linkedWords = placer.placeInList(wordList.toArray(), diff + 1);
		
		//Fills the grid with * so that the words can be placed
		for(int i = 0; i < 10; i++){
            for(int k = 0; k < 10; k++){
                 myGrid[i][k] = "*";
             }
         }
		
		 WordNode temp;
         
         if((diff + 1) == 1){
              temp = linkedWords.allTheOtherKids(4);          
         
              if(temp != null){
                  while(temp != null){
                      
                	  int x = random.nextInt(10);
                	  int y = random.nextInt(10);
                    
                      placer.placeInGrid(temp.word, temp.getLetter(), myGrid, x, y, 1, true);
                      
                      if(placer.getNotIntheGrid() == true){
                          //places any words that didn't go in the faildWords String array
                    	  for(int i = 0; i < temp.word.length; i++){
                              failedWords[count] = temp.word[i];
                              count++;
                          }
                          placer.setBackFalse();
                      }
                      
                      temp = linkedWords.allTheOtherKids(4);
                  }   
              }
          }
              
          if((diff + 1) == 1 || (diff + 1) == 2){
              temp = linkedWords.allTheOtherKids(3);          
         
              if(temp != null){
                  while(temp != null){

                	  int x = random.nextInt(10);
                	  int y = random.nextInt(10); 
                      
                	  placer.placeInGrid(temp.word, temp.getLetter(), myGrid, 0, 0, (diff + 1), true);
                      
                      if(placer.getNotIntheGrid() == true){
                    	//places any words that didn't go in the faildWords String array
                    	  for(int i = 0; i < temp.word.length; i++){
                              failedWords[count] = temp.word[i];
                              count++;
                          }
                          placer.setBackFalse();
                      }
                      
                      temp = linkedWords.allTheOtherKids(3);
                  }   
              }
          }
              
              temp = linkedWords.allTheOtherKids(2);          
         
              if(temp != null){
                  while(temp != null){
                      
                	  int x = random.nextInt(10);
                	  int y = random.nextInt(10);
                      
                	  placer.placeInGrid(temp.word, temp.getLetter(), myGrid, 0, 0, (diff + 1), true);
                      
                      if(placer.getNotIntheGrid() == true){
                    	//places any words that didn't go in the faildWords String array
                    	  for(int i = 0; i < temp.word.length; i++){
                              failedWords[count] = temp.word[i];
                              count++;
                          }
                          placer.setBackFalse();
                      }
                      
                      temp = linkedWords.allTheOtherKids(2);
                  }   
              }
 
              
              temp = linkedWords.allTheOtherKids(1);          
         
              if(temp != null){
                  while(temp != null){
                      
                	  int x = random.nextInt(10);
                	  int y = random.nextInt(10);
                      
                	  placer.placeInGrid(temp.word, temp.getLetter(), myGrid, 0, 0, (diff + 1), true);
                      
                      if(placer.getNotIntheGrid() == true){
                    	//places any words that didn't go in the faildWords String array
                    	  for(int i = 0; i < temp.word.length; i++){
                              failedWords[count] = temp.word[i];
                              count++;
                          }
                          placer.setBackFalse();
                      }
                      
                      temp = linkedWords.allTheOtherKids(1);
                  }   
              }
        
        //Removing words from list that could not be placed...
              /*
		for(int i = 0; i < failedWords.length; i++) {
			if(aWordList.contains(failedWords[i])) {
				aWordList.remove(failedWords[i]);
			}
		}
		*/
		/*
		int plus = 0;
		while(failedWords[plus] != null){
			aWordList.remove(failedWords[plus]);
			plus++;
		}
		*/
		
		String rand = "";
		String failed = "";
		for(int i = 0; i < aWordList.size(); i++){
			rand += " " + aWordList.get(i);
			failed += " " + failedWords[i];
		}
		Log.e(TAG, " WordListAFTER: " + rand + "\n FAILED WORDS " + failed);
		
		placer.addRandomChars(myGrid);
		
		//TEST Printing Grid String
		String asd = "";
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				asd += myGrid[i][j]; 
			}
			
		}
		Log.e(TAG, " board: " + asd);
		board = myGrid;
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j <COLS; j++){
				foundBoard[i][j] = myGrid[i][j];
			}
		}
		
		
		
		}
		puzz = new PuzzleGridView(this);
		setContentView(puzz);
		Log.e(TAG, "************IN GAME DIFF IS " + diff);
		Log.e(TAG, "********IN GAME CAT IS " + cat);
		

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
	    		break;
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
		Log.e(TAG, "IN GAME ON PAUSE");
		super.onPause();
		WordSearchActivity.isContinuing = true;
		Music.stopMusic(this);
		getPreferences(MODE_PRIVATE).edit().putString(ORIG_PUZZLE,
	            toPuzzleString(board)).commit();//board
		getPreferences(MODE_PRIVATE).edit().putString(FOUND_PUZZLE,
	            toPuzzleString(foundBoard)).commit();//foundboard
				
	}
	
	@Override
	protected void onResume(){
		
		super.onResume();
		Music.playMusic(this, R.raw.game);
	}
	
	/** Convert an array into a puzzle string */
	   static private String toPuzzleString(String[][] puzzle) {
	      StringBuilder buf = new StringBuilder();
	      for (int i = 0; i < ROWS; i++) {
	    	  for(int j = 0; j < COLS; j++){
	 	         buf.append(puzzle[i][j]);

	    	  }
	      }
	      Log.e(TAG, " IN TO PUZZ STRING\n, PUZZ STRING: " + buf.toString());
	      return buf.toString();
	   }

	   /** Convert a puzzle string into an array */
	   static protected String[][] fromPuzzleString(String string) {
		   
	      String[][] puz = new String[ROWS][COLS];
	      int position = 0;
	      for (int i = 0; i < ROWS; i++) {
	    	  for(int j = 0; j < COLS; j++){
	    		  
		 	        puz[i][j] = Character.toString(string.charAt(position));
		 	        position++;
	    	  }
	    	 
	      }
	      return puz;
	   }
	//Returns the character @ specified index's
	public String retrieveChar(int row, int col){
		
		return board[row][col];
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
