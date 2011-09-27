package pk.dl.jk.wordsearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PuzzleGridView extends LinearLayout{
	
	private static final int ID = 32; 
	
	public static int width;
	public static int height;
	
	
	private GridView gv;
	private LayoutInflater inflater;
	
	private static TextView txt;
	public static Button btnFwd;
	private static int wordListPosition = 0;
	private static Context myContext;
	
	public PuzzleGridView(Context context) {
		super(context);
		myContext = context;
		inflater = LayoutInflater.from(getContext());
        View game = inflater.inflate(R.layout.game, this, false);
        addView(game);
        txt = (TextView) findViewById(R.id.txtWordList);
        txt.setText(Game.aWordList.get(0).toUpperCase());
        
        btnFwd = (Button) findViewById(R.id.btnFwd);
        btnFwd.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// move the txt to the next word
				if(wordListPosition < Game.aWordList.size() - 1){
					wordListPosition++;					
				}
				else {
					wordListPosition = 0;					
				}
				txt.setText(Game.aWordList.get(wordListPosition).toUpperCase());
			}
        	
        });
        View btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// move the txt to the prev word
				if(wordListPosition == 0) {
					wordListPosition = Game.aWordList.size() - 1;
				}
				else {
					wordListPosition--;
				}
				txt.setText(Game.aWordList.get(wordListPosition).toUpperCase());
				
			}
        	
        });
		setId(ID);

	}
	protected static void update(int wordIndex)
	{
		//check to make sure it's not the last word
		if(Game.aWordList.size() - 1 > 0){
			//If it's less than the last index value
			if(wordIndex < Game.aWordList.size() - 1){
				txt.setText(Game.aWordList.get(wordIndex + 1).toUpperCase());	
			}
			else {
				txt.setText(Game.aWordList.get(wordIndex - 1).toUpperCase());
			}
		}
		//remove the word
		Game.aWordList.remove(wordIndex);
		//Check if the size is 0
		if(Game.aWordList.size() == 0) {
			WordSearchActivity.isContinuing = false;
			WordSearchActivity.continBtn.setEnabled(false);
			
			//Create an alert dialog to say the player won and then exit the game activity
			//which takes them to main screen where they can start another or press the exit btn to quit
			AlertDialog alert = new AlertDialog.Builder(myContext).create();
			alert.setTitle("You Won");
			alert.setMessage("Congratulations, You Found All of The Words!!!");
			
			alert.setButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//finish the game					
					((Activity) myContext).finish();									
					
				}
			});
			alert.show();
		}
	}
	
	/*@Override
	public void onDraw(Canvas canvas){
		Log.e("***IN PUZZ VIEW ON DRAW", "on draw");
		Point start = DragListener.startPoint;
		Point end = DragListener.endPoint;
		Paint paint  = new Paint();
		paint.setColor(Color.BLACK);
		canvas.drawLine(start.x, start.y, end.x, end.y, paint);
	}*/
  
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		View v = findViewById(R.id.relLayoutGame);
		//height of the relative layout that holds the words and btns
		int relLayHeight = v.getMeasuredHeight();
		width = (getWidth()/Game.COLS);
		height = (getHeight() - relLayHeight)/Game.ROWS;
		
	      
//	    Log.d("***Puzz Grid View", "onSizeChanged: width " + width + ", height "
//	            + height + "***");
	    gv = (GridView)findViewById(R.id.boardGrid);
	    gv.setNumColumns(Game.COLS);
	    gv.setAdapter(new ImageAdapter(getContext()));
	    gv.setOnTouchListener(new DragListener(gv));
	    super.onSizeChanged(w, h, oldw, oldh);
	} 
	 
	


}
