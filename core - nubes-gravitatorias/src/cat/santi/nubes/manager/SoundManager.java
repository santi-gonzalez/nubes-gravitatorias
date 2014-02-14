package cat.santi.nubes.manager;

import android.content.Context;
import android.media.MediaPlayer;
import cat.santi.nubes.R;

public class SoundManager {

	private static MediaPlayer mMediaPlayerBounce;
	private static MediaPlayer mMediaPlayerGoodJob;
	
	public static void playSoundBounce(Context context) {
		
		if(mMediaPlayerBounce != null) {
			mMediaPlayerBounce.release();
			mMediaPlayerBounce = null;
		}
		mMediaPlayerBounce = MediaPlayer.create(context, R.raw.bounce);
		mMediaPlayerBounce.start();
	}
	
	public static void playSoundGoodJob(Context context) {
		
		if(mMediaPlayerGoodJob != null) {
			mMediaPlayerGoodJob.release();
			mMediaPlayerGoodJob = null;
	 	}
		mMediaPlayerGoodJob = MediaPlayer.create(context, R.raw.good_job_riemann);
		mMediaPlayerGoodJob.start();
	}
	
	public static void release() {
		
		if(mMediaPlayerBounce != null) {
			mMediaPlayerBounce.release();
			mMediaPlayerBounce = null;
		}
		if(mMediaPlayerGoodJob != null) {
			mMediaPlayerGoodJob.release();
			mMediaPlayerGoodJob = null;
		}
	}
}
