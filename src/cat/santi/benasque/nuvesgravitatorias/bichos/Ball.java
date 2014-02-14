package cat.santi.benasque.nuvesgravitatorias.bichos;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import cat.santi.benasque.nuvesgravitatorias.R;
import cat.santi.benasque.nuvesgravitatorias.interfaces.OnResetBallListener;
import cat.santi.benasque.nuvesgravitatorias.interfaces.OnScoreChangeListener;
import cat.santi.benasque.nuvesgravitatorias.logic.Logic;
import cat.santi.benasque.nuvesgravitatorias.manager.SoundManager;

public class Ball extends AbsBicho {

	private static final int SIDE_SIZE = 50;
	
	private int mRiemannScoreCount = 0;
	
	private float mVelX = 0f;
	private float mVelY = 0f;
	
	public Ball(Context context, OnScoreChangeListener onScoreChangeListener, OnResetBallListener onResetBallListener) {
		mContext = context;
		mOnScoreChangeListener = onScoreChangeListener;
		mOnResetBallListener = onResetBallListener;
		init();
	}
	
	@Override
	public void init() {
	
		mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.riemann);
	}
	
	public void process() {
		
		mVelY += 0.25 * mModificator;
		
		setPos(new PointF(mPosX + mVelX, mPosY + mVelY));
	}
	
	@Override
	public void draw(Canvas canvas) {
		
		canvas.drawBitmap(mBitmap, null, mRect,  null);
	}
	
	@Override
	public void setPos(PointF pos) {
		super.setPos(pos);
		
		calculateCollision();
		
		mRect = new Rect((int)mPosX - SIDE_SIZE/2, (int)mPosY - SIDE_SIZE/2, (int)mPosX + SIDE_SIZE/2, (int)mPosY + SIDE_SIZE/2);
	}
	
	private void calculateCollision() {
		
		if(mPosY > Logic.screenHeight - SIDE_SIZE/2) {
			mPosY = Logic.screenHeight - SIDE_SIZE/2;
			mVelY = -mVelY;
			mVelY /= 1.3f;
			mVelX /= 1.5f;
			
			if(Math.abs(mVelY) >= 0.15) {
				mRiemannScoreCount++;
				if(mOnScoreChangeListener != null)
					mOnScoreChangeListener.onRiemannScoreChange(mRiemannScoreCount);
			} else {
				if(mOnResetBallListener != null)
					mOnResetBallListener.resetBall();
			}
			SoundManager.playSoundBounce(mContext);
		} else if(mPosY < 0 + SIDE_SIZE/2) {
			mPosY = 0 + SIDE_SIZE/2;
			mVelY = -mVelY;
			SoundManager.playSoundBounce(mContext);
		} 
		if(mPosX > Logic.screenWidth - SIDE_SIZE/2) {
			mPosX = Logic.screenWidth - SIDE_SIZE/2;
			mVelX = -mVelX;
			SoundManager.playSoundBounce(mContext);
		} else if(mPosX < 0 + SIDE_SIZE/2) {
			mPosX = 0 + SIDE_SIZE/2;
			mVelX = -mVelX;
			SoundManager.playSoundBounce(mContext);
		}
	}
	
	public void setVelocity(float velX, float velY) {
		mVelX = velX / 5;
		mVelY = velY / 5;
	}
	
	private OnResetBallListener mOnResetBallListener;
	public void setOnResetBallListener(OnResetBallListener onResetBallListener) {
		mOnResetBallListener = onResetBallListener;
	}
	
	private OnScoreChangeListener mOnScoreChangeListener;
	public void setOnScoreChangeListener(OnScoreChangeListener onScoreChangeListener) {
		mOnScoreChangeListener = onScoreChangeListener;
	}
	
	public Rect getRect() {
		return mRect;
	}
	
	private float mModificator = 1.0f;
	public void setModificator(float modificator) {
		mModificator = modificator;
	}
}
