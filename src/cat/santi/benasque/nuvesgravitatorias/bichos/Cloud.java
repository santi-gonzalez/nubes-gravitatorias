package cat.santi.benasque.nuvesgravitatorias.bichos;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import cat.santi.benasque.nuvesgravitatorias.R;
import cat.santi.benasque.nuvesgravitatorias.logic.Logic;

public class Cloud extends AbsBicho {
	
	private static final int SIDE_SIZE = 300;
	private float mGravityMod = 1f;
	
	private float mVelX = 1f;
	private float mVelY = 0f;
	
	public Cloud(Context context, float gravityMod, PointF startPos, int startDisplacement) {
		mContext = context;
		mGravityMod = gravityMod;
		mPosX = startPos.x;
		mPosY = startPos.y;
		mVelX *= startDisplacement;
		init();
	}
	
	@Override
	public void init() {
		
		mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.cloud);
		setPos(new PointF(mPosX, mPosY));
	}

	@Override
	public void draw(Canvas canvas) {
		
		canvas.drawBitmap(mBitmap, null, mRect,  null);
	}
	
	public void process() {
		
		setPos(new PointF(mPosX + mVelX, mPosY + mVelY));
	}
	
	private void calculateCollision() {
		
		if(mPosX > Logic.screenWidth - SIDE_SIZE/2 - 100f) {
			mPosX = Logic.screenWidth - SIDE_SIZE/2 - 100f;
			mVelX = -mVelX;
		} else if(mPosX < 0 + SIDE_SIZE/2 + 100f) {
			mPosX = 0 + SIDE_SIZE/2 + 100f;
			mVelX = -mVelX;
		}
	}
	
	@Override
	public void setPos(PointF pos) {
		super.setPos(pos);
		
		calculateCollision();
		
		mRect = new Rect((int)mPosX - SIDE_SIZE/2, (int)mPosY - SIDE_SIZE/2, (int)mPosX + SIDE_SIZE/2, (int)mPosY + SIDE_SIZE/2);
	}
	
	public float checkModificator(Rect rect) {
		
		if(Rect.intersects(mRect, rect))
			return mGravityMod;
		else
			return 1.0f;
	}
}
