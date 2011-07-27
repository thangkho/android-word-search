package pk.dl.jk.wordsearch;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.GridView;

public class DragListener implements OnTouchListener {

	private float myPosX;
	private float myPosY;
	private float myFirstTouchX;
	private float myFirstTouchY;
	private float myLastTouchX;
	private float myLastTouchY;
	private String word = "";
	private int index = 0;
	private int lastPosition = -1;
	private GridView myCallingGrid;
	ImageAdapter a;
	int[] positionsCrossed = new int[10];

	private static final int INVALID_POINTER_ID = -1;
	//active ID, @ first it is invalid.
	private int myActivePointerID = INVALID_POINTER_ID;
	
	public DragListener(GridView myGrid)
	{
		myCallingGrid = myGrid;
		a = (ImageAdapter) myCallingGrid.getAdapter();
		for (int i = 0; i < positionsCrossed.length; i++){
			positionsCrossed[i] = -1;
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent ev) {

		final int action = ev.getAction();
		Log.e("IN ON TOUCH", "IN ON TOUCH");
		switch(action & MotionEvent.ACTION_MASK){
			case MotionEvent.ACTION_DOWN:{
				Log.e("IN ON TOUCH","ACTION DOWN");
			    //Use the min and max to make sure we stay on the screen.
				final float x = ev.getX();//Math.min(Math.max(ev.getX(), 0), myCallingGrid.getWidth());
				final float y = ev.getY();//Math.min(Math.max(ev.getY(), 0), myCallingGrid.getHeight());
				//This is to remember where the down took place(where the drag started)
				myFirstTouchX = x;
				myFirstTouchY = y;
				myLastTouchX = x;
				myLastTouchY = y;
				myPosX = x;
				myPosY = y;
				//Get the current (and only allowable) pointer ID
				myActivePointerID = ev.getPointerId(0);
				break;
			}
			case MotionEvent.ACTION_MOVE:{
				Log.e("IN ON TOUCH","ACTION MOVE");
				if(index == 10){
					index = 0;
				}
				//find the index of the current pointer (the orig)
				final int pointerIndex = ev.findPointerIndex(myActivePointerID);
				
				final float x = ev.getX(pointerIndex);//Math.min(Math.max(ev.getX(pointerIndex), 0), myCallingGrid.getWidth());
				final float y = ev.getY(pointerIndex);//Math.min(Math.max(ev.getY(pointerIndex), 0), myCallingGrid.getHeight());
				//Distance moved
				final float distanceX = (x - myLastTouchX);
				final float distanceY = (y - myLastTouchY);
				//Keep track of current pos
				myPosX += distanceX;
				myPosY += distanceY;
				//remember last coords
				myLastTouchX = x;
				myLastTouchY = y;
				break;
			}
			case MotionEvent.ACTION_CANCEL:{
				myActivePointerID = INVALID_POINTER_ID;
				break;
			}//Gesture finished
			case MotionEvent.ACTION_UP:{
				Log.e("IN ON TOUCH","ACTION UP");

				myActivePointerID = INVALID_POINTER_ID;
				//Iterate through the list to see if the word matches 
				//any of the list words.
				for(int i = 0; i < Game.wordList.length; i++){
					if(word.equalsIgnoreCase(Game.wordList[i]))
					{//If it equals one, change the positions crossed
						for(int j = 0; j < positionsCrossed.length; j++){
							//set them all to found
							a.swapToFound(positionsCrossed[j]);
							//set to -1 to flag as unused
							positionsCrossed[j] = -1;							
						}
						//set the position in the word list to an empty string
						Game.wordList[i] = "";						
					}
				}
				//swap them back to not highlighted
				a.swapBack(positionsCrossed);
				//start the index over to replace old values;
				index = 0;
				word = "";
				//Notify the adapter of data change
				a.notifyDataSetChanged();
								
				break;
			}//Pointer was lifted off screen (when more than one exists)
			case MotionEvent.ACTION_POINTER_UP:{
				Log.e("IN ON TOUCH","ACTION POINTER UP");

				//See which one of the pointers left the screen (get the index)
				final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) 
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
	        	//ID of the pointer with the specified index...
	        	final int pointerId = ev.getPointerId(pointerIndex);
	        	//If it is the active pointer
	        	if (pointerId == myActivePointerID) {
	        		// This was our active pointer going up. Choose a new
	        		// active pointer and adjust accordingly.
	        		final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
	        		myLastTouchX = ev.getX(newPointerIndex);
	        		myLastTouchY = ev.getY(newPointerIndex);
	        		myActivePointerID = ev.getPointerId(newPointerIndex);
	        	}
				break;
			}
		}
		
		//pointToPosition returns the index of the object that contains that point
		//Returns -1 if no child contains that point
		int position = 	myCallingGrid.pointToPosition((int)myPosX, (int)myPosY);
		Log.e("DRAG LISTENER", "position" + position);
			
		Log.e("MY LAST TOUCH", "X: " + myLastTouchX + "Y: " + myLastTouchY);
		Log.e("MY CURRENT POS", "X: " + myPosX + "Y: " + myPosY);
		
		Rect outRect = new Rect();
		//Calculates the coords of the drawing rectangle and puts them into outRect
		//Use this to calc the exact center of the specific imageView x,y coords below
		myCallingGrid.getChildAt(position).getDrawingRect(outRect);
		//int rectCenterX = outRect.centerX(); 24
		//int rectCenterY = outRect.centerY(); 48

		//calculate the exact center of the specific view by taking the center 
		//coords of a generic rect with the same dimensions as one of the img views
		//(above) for x: 
		//x value + ((index of the view in the grid % by # columns) * width of one img view
		//have to use mod since the gridview is a one dim array so indexes are 0-# images
		//this makes it so every new row starts over @ 0 instead of continuing upwards
		int theX = outRect.centerX() + ((position%Game.COLS) * PuzzleGridView.width);
		//for y: 
		//y value + ((index of the view in the grid / #of columns) * height of one img view
		int theY = outRect.centerY() + ((position/Game.COLS) * PuzzleGridView.height);
		
		Log.e("CENTER COORDS: ", "X: " + theX + "Y: " + theY);
		//IF the diff of the two x's is less than 20 and the diff of the two y's is also
		//Creates a tolerance so that when selecting diagonally wrong letters do not get
		//selected due to fat finger syndrome =)
		if(Math.abs(myPosX - theX) < 20 && Math.abs(myPosY - theY) < 20){
			//If the point was found
			if(position != -1 && position != lastPosition )
			{
				Log.e("LASTPOSITION: " + lastPosition, "CURRENTPOSITION:" + position);
				lastPosition = position;
				this.addToPositionsCrossed(position);				
				a.swapToHighlight(position);
				a.notifyDataSetChanged();
				//Add directly to a string for comparison with real word
				word += Game.board[(int)position/10][position%10];
				Log.e("WORD IS", ":" + word);				
			}
		}
		return true;
	}
	//Adds the current position into the array of positions
	private void addToPositionsCrossed(int aPosition)
	{
		Log.e("IN ADD TO POS", "index: " + index);
		if(index <= positionsCrossed.length){
			positionsCrossed[index] = aPosition;

			index++;
		}

	}
	

}
