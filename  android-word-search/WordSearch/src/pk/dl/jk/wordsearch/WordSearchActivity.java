package pk.dl.jk.wordsearch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;


public class WordSearchActivity extends Activity implements OnClickListener {
	private static final String TAG = "WordSearch";
	private static int difficulty;
	private static View continBtn;
	public static boolean isContinuing = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.e(TAG, "IN ON CREATE isContinuing: " + Boolean.toString(isContinuing));
        //Set up onClick Listeners for all of the btns
        continBtn = findViewById(R.id.btnContinue);
        continBtn.setOnClickListener(this);
        continBtn.setEnabled(false);
        /*if(!isContinuing){
        	continBtn.setEnabled(false);
        }
        else {
        	continBtn.setEnabled(true);
        }*/
        View newGameBtn = findViewById(R.id.btnNewGame);
        newGameBtn.setOnClickListener(this);       
        View exitBtn = findViewById(R.id.btnExit);
        exitBtn.setOnClickListener(this);
    }
    
    @Override
    public void onPause(){
    	/*
    	 * just needs to call the super onPause b/c in here we 
    	 * do not need to save much.
    	 */
    	super.onPause();
    	Music.stopMusic(this);
    	
    }
    @Override
    public void onResume(){
    	//same as above    	 
    	super.onResume();
    	Log.e(TAG, "IN ON RESUME **");
    	if(isContinuing){
    		Log.e(TAG, "In CONTIN  TRUE");
    		continBtn.setEnabled(true);
    	}
    	else{
    		continBtn.setEnabled(false);
    	}
    	Music.playMusic(this, R.raw.main);
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
    	
    	}
    
    	/*
    	 * put more here if we add any other menu options
    	 */
    	return false;    	
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case R.id.btnNewGame:
			//Ask for the difficulty and start a new game of that diff.
			openDiffSelection();
			break;
		case R.id.btnContinue:
			startGame(Game.CONTINUING_GAME, Game.CONTINUING_GAME);
			break;	
		case R.id.btnExit:
			//exit/finish
			finish();
			break;		
		}
	}
	
	private void openDiffSelection() {
		//Create new alert dialog and set the title, set the items to the 
		//array of strings called "difficultyArray" and pass a new onClickListener
		//code the onClick for said listener to start the game passing it the value
		//of which diff level was selected(which item in the array, 0-2)
		new AlertDialog.Builder(this).setTitle(R.string.strDifficulty)
		.setItems(R.array.difficultyArray,
				new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int diff) {
						//startGame(diff);
						difficulty = diff;
						openCatSelection();
					}
				}
		).show();
	}
	//Opens an alert dialog for user to select category.
	//Called by openDiffSelection(). Once user selects cat
	//it calls startGame(difficulty, category)
	private void openCatSelection()	{
		new AlertDialog.Builder(this).setTitle(R.string.strCategory)
		.setItems(R.array.categoryArray,
				new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int cat) {
						startGame(difficulty, cat);
						
					}
				}
		).show();
	}


	
	private void startGame(int diff, int cat){
		//Need to create a new intent to fire off a new game
		//can use intent.putExtra(passing it the str name of the diff, and the value)
		//to put in the difficulty (have to code this into 
		//the game activity)new AlertDialog.Builder(this).setTitle(R.string.strDifficulty)
	
		Log.e(TAG, "clicked on " + diff);
		Log.e(TAG, "clicked on" + cat);
		
		Intent newI = new Intent(this, Game.class);
		newI.putExtra(Game.KEY_DIFF, diff);
		newI.putExtra(Game.KEY_CAT, cat);
		startActivity(newI);
	}
	
}