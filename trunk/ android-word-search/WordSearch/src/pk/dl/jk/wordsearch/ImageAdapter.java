package pk.dl.jk.wordsearch;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ImageAdapter extends BaseAdapter {
	private Context myContext;
	private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	

	public ImageAdapter(Context c)
	{
		myContext = c;		
		this.populateImgArray();
	}

	//populates the array of images so that it can be added to grid
	private void populateImgArray()
	{
		int position = 0;
		int charVal;
		for(int row = 0; row < Game.ROWS; row++)
		{
			for(int col = 0; col < Game.COLS; col++)
			{
				//Get the character at the given board index
				 char character = (Game.board[row][col]).charAt(0);
				 Log.e("IN POP ARRAY", "Char: " + character + " Row: " + row + " Col: " + col);
				 //Find the alpha "value" of the char
				 charVal = this.valueOf(character);
				 String found = Game.foundBoard[row][col];
				 //Figure out if it has already been found
				if(found.equalsIgnoreCase("*")) {
					Log.d("IN IMG ADAPTER", "IS =  TO *");
					myThumbIds[position] = imageRefs[charVal + 52];
				}
				else {
					//The board image array at the position is equal to the imgRefs at the char's value
					 myThumbIds[position] = imageRefs[charVal];		
				}		
				
				 Log.e("IN POP ARRAY", "CHAR VAL: " + charVal);
				 		 
				 position++;
				 
			}
		}
	}
	//Finds the order number of the character in the alphabet array (1 less than it's actually alpha pos)
	private int valueOf(char character)
	{
		int value = 0;
		for(int i = 0; i < alphabet.length() - 1; i++)
		{
			if(Character.toString(character).equalsIgnoreCase(Character.toString(alphabet.charAt(i))))
			{
				value = i;
			}
		}
		return value;
		
	}
	@Override
	public int getCount() {
		//length of the array
		return myThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		return myThumbIds[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Log.e("IN IMG ADAPTER", "GETVIEW");
		ImageView imageView;
		if(convertView == null)
		{			
			imageView = new ImageView(myContext);
//			Log.e("*****IN IMG ADAPTER", "width: " + PuzzleGridView.width + " height: " + PuzzleGridView.height +"******");
			
			imageView.setLayoutParams(new GridView.LayoutParams(PuzzleGridView.width, PuzzleGridView.height));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(0, 0, 0, 0);
			
		}
		else
		{
			imageView = (ImageView) convertView;
			
		}
		imageView.setImageResource(myThumbIds[position]);
		return imageView;
	}
	public void swapToHighlight(int index){
		Log.e("IN SWAP HIGHLIGHT", "INDEX:" + index);
		if(myThumbIds[index] != imageRefs[this.valueOf(Game.board[index/10][index%10].charAt(0)) + 52]){
			myThumbIds[index] = imageRefs[this.valueOf(Game.board[index/10][index%10].charAt(0)) + 26];
		}
				
	}
	public void swapToFound(int index)
	{
		Log.e("IN SWAP FOUND", "INDEX:" + index);
		if(index != -1){
			//Set the data source at the specified index equal to the "value" of the Character at the
			//given indices of the board plus 52(this is what you add to get the value of the found img).
			myThumbIds[index] = imageRefs[this.valueOf(Game.board[index/10][index%10].charAt(0)) + 52];
			Game.foundBoard[index/10][index%10] = "*";
		}
		
	}
	public void swapBack(int[] anArray){
		Log.e("IN IMG ADAPTER", " SWAP BACK");
		for(int i = 0; i < anArray.length; i++){
			int index = anArray[i];
			if(index != -1 && myThumbIds[index] != imageRefs[this.valueOf(Game.board[index/10][index%10].charAt(0)) + 52]){
				myThumbIds[index] = imageRefs[this.valueOf(Game.board[index/10][index%10].charAt(0))];
				anArray[i] = -1;
			}
		}
	}
	// references to our images
    public static Integer[] myThumbIds = new Integer[100];


    private Integer[] imageRefs = {
    		R.drawable.a_reg, R.drawable.b_reg, R.drawable.c_reg,
    		R.drawable.d_reg, R.drawable.e_reg, R.drawable.f_reg,
    		R.drawable.g_reg, R.drawable.h_reg, R.drawable.i_reg,
    		R.drawable.j_reg, R.drawable.k_reg, R.drawable.l_reg,
    		R.drawable.m_reg, R.drawable.n_reg, R.drawable.o_reg,
    		R.drawable.p_reg, R.drawable.q_reg, R.drawable.r_reg,
    		R.drawable.s_reg, R.drawable.t_reg, R.drawable.u_reg,
    		R.drawable.v_reg, R.drawable.w_reg, R.drawable.x_reg,
    		R.drawable.y_reg, R.drawable.z_reg,
    		R.drawable.a_high, R.drawable.b_high, R.drawable.c_high,
    		R.drawable.d_high, R.drawable.e_high, R.drawable.f_high,
    		R.drawable.g_high, R.drawable.h_high, R.drawable.i_high,
    		R.drawable.j_high, R.drawable.k_high, R.drawable.l_high,
    		R.drawable.m_high, R.drawable.n_high, R.drawable.o_high,
    		R.drawable.p_high, R.drawable.q_high, R.drawable.r_high,
    		R.drawable.s_high, R.drawable.t_high, R.drawable.u_high,
    		R.drawable.v_high, R.drawable.w_high, R.drawable.x_high,
    		R.drawable.y_high, R.drawable.z_high,
    		R.drawable.a_found, R.drawable.b_found, R.drawable.c_found,
    		R.drawable.d_found, R.drawable.e_found, R.drawable.f_found,
    		R.drawable.g_found, R.drawable.h_found, R.drawable.i_found,
    		R.drawable.j_found, R.drawable.k_found, R.drawable.l_found,
    		R.drawable.m_found, R.drawable.n_found, R.drawable.o_found,
    		R.drawable.p_found, R.drawable.q_found, R.drawable.r_found,
    		R.drawable.s_found, R.drawable.t_found, R.drawable.u_found,
    		R.drawable.v_found, R.drawable.w_found, R.drawable.x_found,
    		R.drawable.y_found, R.drawable.z_found
    };	
    
}
