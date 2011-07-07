package pk.dl.jk.wordsearch;


import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Prefs extends PreferenceActivity{
	
	private static final String MUSIC_PREF_TITLE = "music";
	private static final boolean MUSIC_DEFAULT_PREF = true;
	//sound played when user selects a correct word?	
	private static final String SOUND_PREF_TITLE = "sound";
	private static final boolean SOUND_DEFAULT_PREF = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
	/*Returns the status of the music preference for the given context
	 * This is called by the music class which is what deals with 
	 * actually turning it on and off by testing to see if this 
	 * comes back true (music is called by the main in onResume
	 * or onStop)
	 */
	public static boolean getMusic(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context)
		.getBoolean(MUSIC_PREF_TITLE, MUSIC_DEFAULT_PREF);		
	}
	/*Returns the status of the sound preference for the given context
	 * This is called by the music class which is what deals with 
	 * actually turning it on and off by testing to see if this 
	 * comes back true
	 */
	public static boolean getSound(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context)
		.getBoolean(SOUND_PREF_TITLE, SOUND_DEFAULT_PREF);
	}
	

}
