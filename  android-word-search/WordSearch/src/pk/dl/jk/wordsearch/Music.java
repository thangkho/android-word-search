package pk.dl.jk.wordsearch;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
	
	private static MediaPlayer musicPlayer = null;
	private static MediaPlayer soundPlayer = null;
	
	//play music (stop old and start new?)
	public static void playMusic(Context context, int resource){
		//Stop the old Music
		stopMusic(context);
		//If get music returns true (music is on)
		if(Prefs.getMusic(context)){
			/*Set the media player to a new mp with the
			 * context and resource passed. Set it to 
			 * looping and start playing.
			 */			
			musicPlayer = MediaPlayer.create(context, resource);
			musicPlayer.setLooping(true);
			musicPlayer.start();			
		}
		
	}
	//stop music
	public static void stopMusic(Context context){
		/*If there is something playing already
		 * stop it, release the resources it is using,
		 * and set it to null.
		 */
		if(musicPlayer != null){			
			musicPlayer.stop();
			musicPlayer.release();
			musicPlayer = null;			
		}		
	}
	//Play sound for correct word selection (called by gameView?)
	public static void playSound(Context context, int resource){
		/*If the music is playing, pause it.
		 * If the Prefs indicates that the user
		 * wants sounds on. Set sound player to a new mp 
		 * with the correct context and resource, make sure
		 * it does not loop (do we need this or is that the default?)
		 * and start playing 
		 */
		if(musicPlayer.isPlaying()){
			musicPlayer.pause();
			if(Prefs.getSound(context)){				
				soundPlayer = MediaPlayer.create(context, resource);
				soundPlayer.setLooping(false);
				soundPlayer.start();
			}
			musicPlayer.start();
		}else if(Prefs.getSound(context)){			
			soundPlayer = MediaPlayer.create(context, resource);
			soundPlayer.setLooping(false);
			soundPlayer.start();
		}
		
	}

}
