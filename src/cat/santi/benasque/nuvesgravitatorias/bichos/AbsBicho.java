package cat.santi.benasque.nuvesgravitatorias.bichos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

public abstract class AbsBicho {

	protected float mPosX;
	protected float mPosY;
	
	protected Context mContext;
	protected Bitmap mBitmap;
	protected Rect mRect;
	
	protected Paint mPaint = new Paint();

	public void setPos(PointF pos) {
		mPosX = pos.x;
		mPosY = pos.y;
	}
	
	public PointF getPos() {
		return new PointF(mPosX, mPosY);
	}
	
	public abstract void init();
	
	public abstract void draw(Canvas canvas);
}
