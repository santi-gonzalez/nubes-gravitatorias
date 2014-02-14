package cat.santi.nubes.bichos;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import cat.santi.nubes.R;
import cat.santi.nubes.logic.Logic;

public class Target extends AbsBicho {

	private static final int SIDE_SIZE = 25;
	
	private float mVelX = 0f;
	private float mVelY = 3f;
	
	public Target(Context context) {
		mContext = context;
		init();
	}
	
	@Override
	public void init() {
		
		mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.target);
		setPos(new PointF(Logic.screenWidth - 50f, Logic.screenHeight / 2));
	}
	
	public void process() {
		
		setPos(new PointF(mPosX + mVelX, mPosY + mVelY));
	}
	
	private void calculateCollision() {
		
		if(mPosY > Logic.screenHeight - SIDE_SIZE/2 - 200f) {
			mPosY = Logic.screenHeight - SIDE_SIZE/2 - 200f;
			mVelY = -mVelY;
		} else if(mPosY < 0 + SIDE_SIZE/2 + 200f) {
			mPosY = 0 + SIDE_SIZE/2 + 200f;
			mVelY = -mVelY;
		}
	}
	
	public boolean checkImpact(Rect rect) {
		
		return Rect.intersects(mRect, rect);
	}
	
	@Override
	public void setPos(PointF pos) {
		super.setPos(pos);
		
		calculateCollision();
		
		mRect = new Rect((int)mPosX - SIDE_SIZE/2, (int)mPosY - SIDE_SIZE/2, (int)mPosX + SIDE_SIZE/2, (int)mPosY + SIDE_SIZE/2);
	}

	@Override
	public void draw(Canvas canvas) {
		
		canvas.drawBitmap(mBitmap, null, mRect,  null);
	}

}
