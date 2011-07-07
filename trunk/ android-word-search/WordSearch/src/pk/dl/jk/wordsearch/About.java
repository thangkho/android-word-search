package pk.dl.jk.wordsearch;

import android.app.Activity;
import android.os.Bundle;

public class About extends Activity {
	@Override
	/*has to override the onCreate of Activity.
	 *sets the content view to the about view.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}

}
