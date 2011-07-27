package pk.dl.jk.wordsearch;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class PuzzleGridView extends LinearLayout{
	//It is a view that contains a gridview and a label for the words @ the top
	private static Game THEGAME;
	private static final int ID = 32; //game ID
	
	public static int width;
	public static int height;
	
	private float colWidth; //width of column
	
	private GridView myGrid;
	private TextSwitcher myTxtSwitch;

	
	public PuzzleGridView(Context context) {
		super(context);
		this.setOrientation(VERTICAL);
		width = (getWidth()/Game.COLS);
		height = (getHeight()/Game.ROWS);
		THEGAME = (Game) context;
		//setFocusable(true);
		//setFocusableInTouchMode(true);
		myTxtSwitch = new TextSwitcher(THEGAME);
		LinearLayout textLayout = new LinearLayout(THEGAME);
		myTxtSwitch.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		textLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		Button btnForward = new Button(THEGAME);
		Button btnBackward = new Button(THEGAME);
		btnForward.setText("F");
		btnBackward.setText("B");
		
		btnForward.setGravity(RelativeLayout.ALIGN_PARENT_RIGHT);
		btnBackward.setGravity(RelativeLayout.ALIGN_PARENT_LEFT);
		
		textLayout.setGravity(Gravity.TOP);
		//myTxtSwitch.addView(new TextView(THEGAME), new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		//myTxtSwitch.setCurrentText(Game.wordList[0]);
		TextView txt = new TextView(THEGAME);
		txt.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		txt.setGravity(RelativeLayout.CENTER_IN_PARENT);
		txt.setText(Game.wordList[1]);		
		txt.setTextSize(25);
		myTxtSwitch.addView(txt);
		
		myGrid = new GridView(THEGAME);
		colWidth = (getWidth()/Game.COLS);
		myGrid.setColumnWidth((int) colWidth);
		myGrid.setNumColumns(Game.COLS);
		myGrid.setGravity(Gravity.BOTTOM);

		myGrid.setAdapter(new ImageAdapter(context));		
		myGrid.setOnTouchListener(new DragListener(myGrid));
		//Add the views
		//this.addView(myTxtSwitch);
		textLayout.addView(btnForward);
		textLayout.addView(myTxtSwitch);
		textLayout.addView(btnBackward);
		this.addView(textLayout);
		this.addView(myGrid);
		//set puzzleview id
		setId(ID);

	}
	/*@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec){
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
	}*/
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		width = (getWidth()/Game.COLS); //getWidth();
		height = (getHeight()/Game.ROWS);//getHeight();
		
		 int childCount = getChildCount();
		 Log.e("In onLayout", "Child Count is:" + childCount);
		 LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		 
	        for (int childIndex = 0; childIndex < childCount; childIndex++) {
	        	
	                getChildAt(childIndex).setLayoutParams(params);//new LinearLayout.LayoutParams(width, height));
	        }
		
		super.onLayout(changed, l, t, r, b);
	}
	
//

}
