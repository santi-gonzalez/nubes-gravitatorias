package cat.santi.benasque.nuvesgravitatorias.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import cat.santi.benasque.nuvesgravitatorias.bichos.Ball;
import cat.santi.benasque.nuvesgravitatorias.bichos.Cloud;
import cat.santi.benasque.nuvesgravitatorias.bichos.ElasticBand;
import cat.santi.benasque.nuvesgravitatorias.bichos.Slingshot;
import cat.santi.benasque.nuvesgravitatorias.bichos.Target;
import cat.santi.benasque.nuvesgravitatorias.interfaces.OnResetBallListener;
import cat.santi.benasque.nuvesgravitatorias.interfaces.OnScoreChangeListener;
import cat.santi.benasque.nuvesgravitatorias.logic.Logic;
import cat.santi.benasque.nuvesgravitatorias.manager.SoundManager;

public class GameView extends View implements OnResetBallListener {

	public static final int STATE_IDDLE = 0;
	public static final int STATE_STRETCHING = 1;
	public static final int STATE_FLYING = 2;
	
	private boolean isInitialized = false;
	private int mState;
	
	private Slingshot mSlingshot;
	private ElasticBand mElasticBand;
	private Ball mBall;
	private Target mTarget;
	private Cloud mCloudGreater;
	private Cloud mCloudLesser;
	
	public GameView(Context context) {
		this(context, null, 0);
	}
	public GameView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	private synchronized void init() {

		if(!isInitialized) {
			
			mSlingshot = new Slingshot();
			mElasticBand = new ElasticBand();
			mTarget = new Target(getContext());
			mBall = new Ball(getContext(), mOnScoreChangeListener, this);
			mCloudGreater = new Cloud(getContext(), 3.0f, new PointF(450f, 250f), -1);
			mCloudLesser = new Cloud(getContext(), -3.0f, new PointF(750f, 500f),  1);
			
			mHandler.post(mRunnable);
			isInitialized = true;
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		
		Logic.screenWidth = right;
		Logic.screenHeight = bottom;
		
		init();
	}

	private int mScoreCount = 0;
	private void process() {
		
		PointF touchPoint = new PointF(mTouchX, mTouchY);
		
		switch(mState) {
		case STATE_IDDLE:
			mBall.setPos(mSlingshot.getPos());
			break;
		case STATE_STRETCHING:
			mBall.setPos(touchPoint);
			break;
		case STATE_FLYING:
			mBall.process();
			break;
		}
			
		mTarget.process();
		mCloudGreater.process();
		mCloudLesser.process();
		mElasticBand.setPoints(mSlingshot.getPos(), mBall.getPos());
		
		float modificator = 1.0f;
		if(modificator == 1.0f)
			modificator = mCloudGreater.checkModificator(mBall.getRect());
		if(modificator == 1.0f)
			modificator = mCloudLesser.checkModificator(mBall.getRect());
		mBall.setModificator(modificator);
		
		if(mTarget.checkImpact(mBall.getRect()) && mState == STATE_FLYING && mOnScoreChangeListener != null) {
			mState = STATE_IDDLE;
			mScoreCount++;
			mOnScoreChangeListener.onMainScoreChange(mScoreCount);
			SoundManager.playSoundGoodJob(getContext());
		}
	}
	
	private void render(){
		invalidate();
	}
	
	private float mTouchX, mTouchY;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			break;
		case MotionEvent.ACTION_MOVE:
			
			mTouchX = event.getX();
			mTouchY = event.getY();
			
			if(mTouchX > mSlingshot.getPos().x)
				mTouchX = mSlingshot.getPos().x;
			mBall.setVelocity((mSlingshot.getPos().x - mTouchX) * 0.5f, (mSlingshot.getPos().y - mTouchY) * 0.5f);
			mState = STATE_STRETCHING;
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			
			mState = STATE_FLYING;
			break;
		}
		
		return true;
	}
	
	public static int sDrawCount = 0;
	public static int sTickCount = 0;
	
	Handler mHandler = new Handler();
	Runnable mRunnable = new Runnable() {
		
		@Override
		public void run() {
			sTickCount++;
			
			process();
			render();
			
			mHandler.postDelayed(mRunnable, 1000 / 60);
		}
	};
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		sDrawCount++;
		
		if(mState == STATE_STRETCHING)
			mElasticBand.draw(canvas);
		mCloudGreater.draw(canvas);
		mCloudLesser.draw(canvas);
		mTarget.draw(canvas);
		mSlingshot.draw(canvas);
		mBall.draw(canvas);
	}

	private OnScoreChangeListener mOnScoreChangeListener;
	public void setOnScoreChangeListener(OnScoreChangeListener onScoreChangeListener) {
		mOnScoreChangeListener = onScoreChangeListener;
	}
	@Override
	public void resetBall() {

		mState = STATE_IDDLE;
	}
	
}
