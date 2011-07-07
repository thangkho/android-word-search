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
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Set up onClick Listeners for all of the btns
        View newGameBtn = findViewById(R.id.btnNewGame);
        newGameBtn.setOnClickListener(this);
        View aboutBtn = findViewById(R.id.btnAbout);
        aboutBtn.setOnClickListener(this);
        View exitBtn = findViewById(R.id.btnExit);
        exitBtn.setOnClickListener(this);
    }
    
    @Override
    public void onPause(){
    	/*
    	 * just needs to call the super onPause b/c in here we 
    	 * do not need to save much except for maybe the status 
    	 * of music/sounds? The Music class should handle checking
    	 * in the preferences to see if the music is enabled or not
    	 */
    	super.onPause();
    	Music.stopMusic(this);
    	
    }
    @Override
    public void onResume(){
    	/*
    	 * same as above, may need to add in more 
    	 * when we get the music/sounds implemented.
    	 */
    	super.onResume();
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
		case R.id.btnAbout:
			//display the about info, set out a new intent and start the about activity.
			Intent i = new Intent(this, About.class);
			startActivity(i);
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
					public void onClick(DialogInterface dialog, int which) {
						startGame(which);				
					}
				}
		).show();
	}
	
	private void startGame(int i){
		//Need to create a new intent to fire off a new game
		//can use intent.putExtra(passing it the str name of the diff, and the value)
		//to put in the difficulty (have to code this into 
		//the game activity)
		Log.d(TAG, "clicked on " + i);
		Intent newI = new Intent(this, Game.class);
		newI.putExtra(Game.KEY_DIFF, i);
	}
	
}